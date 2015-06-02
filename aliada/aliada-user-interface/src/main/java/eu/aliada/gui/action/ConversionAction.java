// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.model.FileWork;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;
import eu.aliada.shared.rdfstore.RDFStoreDAO;

/**
 * This class help in the process of the xml files importation.
 * @author iosa
 * @version $Revision: 1.1 $
 * @since 1.0
 */
public class ConversionAction extends ActionSupport {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FileWork importedFile;
    private HashMap<Integer, String> templates;
    private HashMap<Integer, String> datasets;
    private HashMap<Integer, String> graphs;
    private String selectedTemplate;
    private int showCheckButton;
    private int showRdfizerButton;
    private Map datasetMap;
    private String dat;
    private String sub;
    
    private final Log logger = new Log(ConversionAction.class);
    /**
     * Calls to the file session save process.
     * @return String
     * @see
     * @since 1.0
     */
    public String execute() {
    	//STATUS IDLE
        String rdfizerStatus = "idle";
        HttpSession session = ServletActionContext.getRequest().getSession();
        setImportedFile((FileWork) session.getAttribute("importedFile")); 
        if (session.getAttribute("rdfizerStatus") != null) {
            rdfizerStatus = (String) session.getAttribute("rdfizerStatus");
        }
        if (rdfizerStatus.equals("running")) {
            setShowCheckButton(1);
            setShowRdfizerButton(0);
        } else {
            setShowCheckButton(0);
            setShowRdfizerButton(1); 
        }
        getDatAndSubsets();
        getGraphsDb();
        return getTemplatesDb();
    }
    /**
     * Calls to the rdfizer process.
     * @return String
     * @see
     * @since 1.0
     */
    public String rdfize() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        if (session.getAttribute("importedFile") != null) {
            setImportedFile((FileWork) session.getAttribute("importedFile"));
            importedFile.setTemplate(getTemplateNameFromCode(getSelectedTemplate()));
            importedFile.setDataset(getDat());
            importedFile.setGraph(getSub());
            String format = null;
            String namespace = "http://";
            try {
                format = getFormat(importedFile.getProfile());
                Connection connection = null;
                connection = new DBConnectionManager().getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT d.domain_name, d.uri_id_part, d.uri_concept_part, s.uri_concept_part "
                		+ "FROM aliada.dataset d INNER JOIN aliada.subset s ON d.datasetId=s.datasetId WHERE s.graph_uri='" + importedFile.getGraph() + "';");
                if (rs.next()) {
                	// The field namespace
                	String domain_name = rs.getNString(1);
                	String uri_id_part = rs.getNString(2);
                	String d_uri_concept_part = rs.getNString(3);
                	String s_uri_concept_part = rs.getNString(4);
      
                	if (!domain_name.isEmpty()) {
                	namespace = namespace.concat(domain_name + "/");
                	}
                	if (!uri_id_part.isEmpty()) {
                	namespace = namespace.concat(uri_id_part + "/");
                	}
                	if (!d_uri_concept_part.isEmpty()) {
                	namespace = namespace.concat(d_uri_concept_part + "/");
                	}
                	if (!s_uri_concept_part.isEmpty()) {
                	namespace = namespace.concat(s_uri_concept_part);
                	}

                }
                rs.close();
                statement.close();
                
                statement = connection.createStatement();
                rs = statement
                        .executeQuery("select aliada_ontology,sparql_endpoint_uri,sparql_endpoint_login,sparql_endpoint_password,graph_uri"
                        		+ " from aliada.organisation o INNER JOIN aliada.dataset d ON o.organisationId=d.organisationId "
                        		+ "INNER JOIN aliada.subset s ON d.datasetId=s.datasetId WHERE s.graph_uri='" + importedFile.getGraph() + "'");
                if (rs.next()) {
                    PreparedStatement preparedStatement = connection
                            .prepareStatement(
                                    "INSERT INTO aliada.rdfizer_job_instances (datafile,format,namespace,graph_name,aliada_ontology,sparql_endpoint_uri,"
                                    + "sparql_endpoint_login,sparql_endpoint_password) VALUES(?,?,?,?,?,?,?,?)",
                                    PreparedStatement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, importedFile.getFile().getAbsolutePath());
                    preparedStatement.setString(2, format);
                    preparedStatement.setString(3, namespace);
                    preparedStatement.setString(4, rs.getString("graph_uri"));
                    preparedStatement.setString(5, rs.getString("aliada_ontology"));
                    preparedStatement.setString(6, rs.getString("sparql_endpoint_uri"));
                    preparedStatement.setString(7, rs.getString("sparql_endpoint_login"));
                    preparedStatement.setString(8, rs.getString("sparql_endpoint_password"));
                    preparedStatement.executeUpdate();
                    ResultSet rs2 = preparedStatement.getGeneratedKeys();
                    int addedId = 0;
                    if (rs2.next()) {
                        addedId = (int) rs2.getInt(1);
                    }
                    try {
                        enableRdfizer();
                        session.setAttribute("importedFile", importedFile);
                        createJob(addedId);
                        session.setAttribute("rdfizerStatus", "running");
					
                        //If it's properly created  => change STATUS RUNNINGRDFIZER
 
                        Statement updateStatement = connection.createStatement();
                        updateStatement.executeUpdate("UPDATE aliada.user_session set status='runningRdfizer', job_id=" + addedId + ", "
                        		+ "template=" + getSelectedTemplate() + ", graph=" + getSubsetId(getSub()) + " where file_name='" + importedFile.getFilename() + "'");
                        updateStatement.close();
						
                    } catch (IOException e) {
                        logger.error(MessageCatalog._00012_IO_EXCEPTION, e);
                        getTemplatesDb();
                        getDatAndSubsets();
                        getGraphsDb();
                        rs2.close();
                        preparedStatement.close();
                        connection.close();
                        return ERROR;
                    }
                    rs2.close();
                    preparedStatement.close();
                    rs.close();
                    statement.close();
                    connection.close();
                    session.setAttribute("rdfizerJobId", addedId);
                    setShowCheckButton(1);
                    getDatAndSubsets();
                    getGraphsDb();
                    return getTemplatesDb();                
                }
            } catch (SQLException e) {
                logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
                getDatAndSubsets();
                getGraphsDb();
                getTemplatesDb();
                return ERROR;
            }
            getDatAndSubsets();
            getGraphsDb();
            return getTemplatesDb();
        } else {
            logger.error(MessageCatalog._00033_CONVERSION_ERROR_NO_FILE_IMPORTED);
            getDatAndSubsets();
            getGraphsDb();
            getTemplatesDb();
            return ERROR;
        }
    }
    /**
     * Clear the graph.
     * @return String
     * @see
     * @since 1.0
     */
    public String cleanGraph() {
        Connection connection = null;
        connection = new DBConnectionManager().getConnection();
        Statement statement;
        String graphToCleanId = ServletActionContext.getRequest().getParameter("graphToCleanId");
        String seu, sel, sep , graphUri , linksGraphUri = "";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select sparql_endpoint_uri,sparql_endpoint_login,sparql_endpoint_password,graph_uri, links_graph_uri "
                    		+ "from aliada.dataset d INNER JOIN aliada.subset s ON d.datasetId=s.datasetId WHERE s.subsetId='" + graphToCleanId + "'");
            if (rs.next()) {
            	seu = rs.getString("sparql_endpoint_uri");
            	sel = rs.getString("sparql_endpoint_login");
            	sep = rs.getString("sparql_endpoint_password");
            	graphUri = rs.getString("graph_uri");
            	linksGraphUri = rs.getString("links_graph_uri");
                RDFStoreDAO store = new RDFStoreDAO();
                if (!store.clearGraphBySparql(seu, sel, sep, graphUri)) {
                    logger.error(MessageCatalog._00032_CONVERSION_ERROR_CLEARING_GRAPH);
                    rs.close();
                    statement.close();
                    connection.close();
                    execute();
                    return ERROR;
                }
                if (!store.clearGraphBySparql(seu, sel, sep, linksGraphUri)) {
                    logger.error(MessageCatalog._00032_CONVERSION_ERROR_CLEARING_GRAPH);
                    rs.close();
                    statement.close();
                    connection.close();
                    execute();
                    return ERROR;
                }
                logger.debug(MessageCatalog._00034_CONVERSION_GRAPH_CLEANED);
                addActionMessage(getText("conversion.graphCleaned") + rs.getString("graph_uri"));
            }
            rs.close();
            statement.close();
            connection.close();
            return execute(); 
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            execute();
            return ERROR;
        }
        
    }
    /**
     * Get the name of the the template from a give template code.
     * @param selectedTemplate The selected template
     * @return String
     * @see
     * @since 1.0
     */
    private String getTemplateNameFromCode(final String selectedTemplate) {
        Connection connection = null;
        String templateName = "";
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement;
            statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select template_name from aliada.template where template_id=" + selectedTemplate);
            if (rs.next()) {
                templateName = rs.getString("template_name");
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return "";
        }
        return templateName;
    }
    /**
     * Gets the format of the file.
     * @return String
     * @param profile The profile selected
     * @throws SQLException Throws a SQLException
     * @see
     * @since 1.0
     */
    private String getFormat(final String profile) throws SQLException {
        Connection connection = null;
        String format = null;
        connection = new DBConnectionManager().getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement
                .executeQuery("select metadata_name from aliada.t_metadata_scheme JOIN aliada.profile ON t_metadata_scheme.metadata_code=profile.metadata_scheme_code "
                		+ "WHERE profile.profile_name= '" + profile + "'");
        if (rs.next()) {
            format = rs.getString(1);
        }
        rs.close();
        statement.close();
        connection.close();
        return format;
    }

    /**
     * Enables the RDFizer.
     * @throws IOException Throws a IOException 
     * @see
     * @since 1.0
     */
    private void enableRdfizer() throws IOException {
    	URL url = new URL("http://localhost:8080/aliada-rdfizer-2.0/enable");
//        URL url = new URL("http://localhost:8891/rdfizer/enable");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("PUT");
        if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
            setShowRdfizerButton(1);
            getDatAndSubsets();
            getGraphsDb();
            getTemplatesDb();  
            throw new ConnectException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        logger.debug(MessageCatalog._00030_CONVERSION_RDFIZE_ENABLE);
        conn.disconnect();
    }

    /**
     * Creates a job for the RDFizer
     * @param addedId Add the ID
     * @throws IOException  Throws a IOException
     * @see
     * @since 1.0
     */
    private void createJob(final int addedId) throws IOException {
	    URL url = new URL("http://localhost:8080/aliada-rdfizer-2.0/jobs/" + addedId);
//        URL url = new URL("http://localhost:8891/rdfizer/jobs/" + addedId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("PUT");
        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            setShowRdfizerButton(1);
            getDatAndSubsets();
            getGraphsDb();
            getTemplatesDb();  
            throw new ConnectException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        logger.debug(MessageCatalog._00031_CONVERSION_RDFIZE_JOB);
        setShowRdfizerButton(0);
        conn.disconnect();
    }
    
    /**
     * Gets the templates from the DB available for this type of file.
     * @return String
     * @see
     * @since 1.0
     */
    public String getTemplatesDb() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select file_type_code from aliada.profile WHERE profile_name='" + importedFile.getProfile() + "'");
            rs.next();
            int fileTypeCode = rs.getInt("file_type_code");   
            rs.close();
            statement.close();
            statement = connection.createStatement();
            rs = statement
                    .executeQuery("select * from aliada.template WHERE file_type_code=" + fileTypeCode);
            templates = new HashMap<Integer, String>();
            while (rs.next()) {
                templates.put(rs.getInt("template_id"),
                        rs.getString("template_name"));
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        return SUCCESS;
    }
    /**
     * Gets the available datasets and subsets from the DB.
     * @return String
     * @see
     * @since 1.0
     */
    public String getDatAndSubsets() {
    	datasetMap = new HashMap();
    	String username = (String) ServletActionContext.getRequest().getSession().getAttribute("logedUser");
        Connection connection = null;
        try {
        	connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT datasetId, dataset_desc FROM aliada.organisation o INNER JOIN aliada.dataset d ON o.organisationId"
            		+ "=d.organisationId INNER JOIN aliada.user u ON o.organisationId=u.organisationId where u.user_name='" + username + "';");
            ResultSet res;
            while (rs.next()) {
            	int datId = rs.getInt("datasetId");
            	String datDesc = rs.getString("dataset_desc");
            	
            	res = statement1.executeQuery("SELECT subsetId, graph_uri FROM aliada.subset s "
            			+ "INNER JOIN aliada.dataset d ON s.datasetId=d.datasetId where s.datasetId='" + datId + "';");
            	
            	ArrayList<String> l = new ArrayList<String>();
            	
            	while (res.next()) {
            		int subId = res.getInt("subsetId");
            		String graphUri = res.getString("graph_uri");
            		l.add(graphUri);
            	}
            	datasetMap.put(datDesc, l);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }  	
    	return SUCCESS;
    }
    /**
     * Gets the available graphs from the DB.
     * @return String
     * @see
     * @since 1.0
     */
    public String getGraphsDb() {
        String username = (String) ServletActionContext.getRequest().getSession().getAttribute("logedUser");
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            
//            if (getSelectedDataset() == null) {
//            	setSelectedDataset("1");
//            }
//            ResultSet rs = statement.executeQuery("SELECT subsetId, graph_uri FROM aliada.dataset d INNER JOIN aliada.subset s "
//            		+ "ON d.datasetId=s.datasetId where d.datasetId ='" + getSelectedDataset() + "';");
            
            ResultSet rs = statement.executeQuery("SELECT subsetId, graph_uri FROM aliada.organisation o INNER JOIN aliada.dataset d "
            		+ "ON o.organisationId=d.organisationId INNER JOIN aliada.subset s ON d.datasetId=s.datasetId INNER JOIN aliada.user "
            		+ "u ON u.organisationId=d.organisationId where u.user_name='" + username + "';");
            graphs = new HashMap<Integer, String>();
            while (rs.next()) {
                graphs.put(rs.getInt("subsetId"),
                        rs.getString("graph_uri"));
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        return SUCCESS;
    }
    /**
     * Gets the graph Uri from a graph code.
     * @param graphUri The graph Uri
     * @return String
     * @see
     * @since 1.0
     */
    public String getSubsetId(final String graphUri) {
        Connection connection = null;
        String graphId = "";
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT subsetId FROM aliada.subset WHERE graph_uri='" + graphUri + "';");
            if (rs.next()) {
                graphId = rs.getString("subsetId");
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return "";
        }
        return graphId;
    }

    /**
     * @return Returns the templates.
     * @exception
     * @since 1.0
     */
    public HashMap<Integer, String> getTemplates() {
        return templates;
    }

    /**
     * @param templates
     *            The templates to set.
     * @exception
     * @since 1.0
     */
    public void setTemplates(final HashMap<Integer, String> templates) {
        this.templates = templates;
    }

    /**
     * @return Returns the importedFile.
     * @exception
     * @since 1.0
     */
    public FileWork getImportedFile() {
        return importedFile;
    }

    /**
     * @param importedFile
     *            The importedFile to set.
     * @exception
     * @since 1.0
     */
    public void setImportedFile(final FileWork importedFile) {
        this.importedFile = importedFile;
    }
    /**
     * @return Returns the selectedTemplate.
     * @exception
     * @since 1.0
     */
    public String getSelectedTemplate() {
        return selectedTemplate;
    }

    /**
     * @param selectedTemplate
     *            The selectedTemplate to set.
     * @exception
     * @since 1.0
     */
    public void setSelectedTemplate(final String selectedTemplate) {
        this.selectedTemplate = selectedTemplate;
    }

    /**
     * @return Returns the showCheckButton.
     * @exception
     * @since 1.0
     */
    public int getShowCheckButton() {
        return showCheckButton;
    }

    /**
     * @param showCheckButton
     *            The showCheckButton to set.
     * @exception
     * @since 1.0
     */
    public void setShowCheckButton(final int showCheckButton) {
        this.showCheckButton = showCheckButton;
    }

    /**
     * @return Returns the showRdfizerButton.
     * @exception
     * @since 1.0
     */
    public int getShowRdfizerButton() {
        return showRdfizerButton;
    }

    /**
     * @param showRdfizerButton The showRdfizerButton to set.
     * @exception
     * @since 1.0
     */
    public void setShowRdfizerButton(final int showRdfizerButton) {
        this.showRdfizerButton = showRdfizerButton;
    }
    /**
     * @return Returns the datasets.
     * @exception
     * @since 1.0
     */
    public HashMap<Integer, String> getDatasets() {
		return datasets;
	}
    /**
     * @param datasets The datasets to set.
     * @exception
     * @since 1.0
     */
	public void setDatasets(final HashMap<Integer, String> datasets) {
		this.datasets = datasets;
	}
	/**
     * @return Returns the graphs.
     * @exception
     * @since 1.0
     */
    public HashMap<Integer, String> getGraphs() {
        return graphs;
    }
    /**
     * @param graphs The graphs to set.
     * @exception
     * @since 1.0
     */
    public void setGraphs(final HashMap<Integer, String> graphs) {
        this.graphs = graphs;
    }
	/**
     * @return Returns the dataset-subsets.
     * @exception
     * @since 1.0
     */
    public Map getDatasetMap() {
		return datasetMap;
	}
    /**
     * @param datasetMap The dataset-subsets to set.
     * @exception
     * @since 1.0
     */
	public void setDatasetMap(final Map datasetMap) {
		this.datasetMap = datasetMap;
	}
    /**
     * @return Returns the selectedDataset.
     * @exception
     * @since 1.0
     */
	public String getDat() {
		return dat;
	}
    /**
     * @param dat The selectedDataset to set.
     * @exception
     * @since 1.0
     */
	public void setDat(final String dat) {
		this.dat = dat;
	}
	/**
     * @return Returns the selectedSubset.
     * @exception
     * @since 1.0
     */
	public String getSub() {
		return sub;
	}
    /**
     * @param sub The selectedSubset to set.
     * @exception
     * @since 1.0
     */
	public void setSub(final String sub) {
		this.sub = sub;
	}    
}
