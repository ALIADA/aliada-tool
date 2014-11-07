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
import java.util.HashMap;

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
    private FileWork importedFile;
    private HashMap<Integer, String> templates;
    private HashMap<Integer, String> graphs;
    private String selectedTemplate;
    private int showCheckButton;
    private int showRdfizerButton;
    private String selectedGraph;
    
    private final Log logger = new Log(ConversionAction.class);
    
    public String execute() {
        String rdfizerStatus = "idle";
        HttpSession session = ServletActionContext.getRequest().getSession();
        setImportedFile((FileWork) session.getAttribute("importedFile")); 
        if(session.getAttribute("rdfizerStatus") != null){
            rdfizerStatus = (String) session.getAttribute("rdfizerStatus");
        }
        if (rdfizerStatus.equals("running")) {
            setShowCheckButton(1);
            setShowRdfizerButton(0);
        } else {
            setShowCheckButton(0);
            setShowRdfizerButton(1); 
        }
        getGraphsDb();
        return getTemplatesDb();
    }
    /**
     * Calls to the rdfizer process
     * @return
     * @see
     * @since 1.0
     */
    public String rdfize() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        if(session.getAttribute("importedFile")!=null){
            setImportedFile((FileWork) session.getAttribute("importedFile"));
            importedFile.setTemplate(getTemplateNameFromCode(getSelectedTemplate()));
            importedFile.setGraph(getGraphUri(getSelectedGraph()));
            String format = null;
            try {
                format = getFormat(importedFile.getProfile());
                Connection connection = null;
                connection = new DBConnectionManager().getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement
                        .executeQuery("select aliada_ontology,sparql_endpoint_uri,sparql_endpoint_login,sparql_endpoint_password,graph_uri,dataset_base from organisation o INNER JOIN graph g ON o.organisationId=g.organisationId WHERE g.graph_uri='"+importedFile.getGraph()+"'");
                if (rs.next()) {
                    PreparedStatement preparedStatement = connection
                            .prepareStatement(
                                    "INSERT INTO aliada.rdfizer_job_instances (datafile,format,namespace,graph_name,aliada_ontology,sparql_endpoint_uri,sparql_endpoint_login,sparql_endpoint_password) VALUES(?,?,?,?,?,?,?,?)",
                                    PreparedStatement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, importedFile.getFile().getAbsolutePath());
                    preparedStatement.setString(2, format);
                    preparedStatement.setString(3, rs.getString("dataset_base"));
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
                    } catch (IOException e) {
                        logger.error(MessageCatalog._00012_IO_EXCEPTION,e);
                        getTemplatesDb();
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
                    getGraphsDb();
                    return getTemplatesDb();                
                }
            } catch (SQLException e) {
                logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
                getGraphsDb();
                getTemplatesDb();
                return ERROR;
            }
            getGraphsDb();
            return getTemplatesDb();
        }
        else{
            logger.error(MessageCatalog._00033_CONVERSION_ERROR_NO_FILE_IMPORTED);
            getGraphsDb();
            getTemplatesDb();
            return ERROR;
        }
    }
    /**
     * Clear the graph
     * @return
     * @see
     * @since 1.0
     */
    public String cleanGraph(){
        Connection connection = null;
        connection = new DBConnectionManager().getConnection();
        Statement statement;
        String graphToCleanId = ServletActionContext.getRequest().getParameter("graphToCleanId");
        try {
            statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select sparql_endpoint_uri,sparql_endpoint_login,sparql_endpoint_password,graph_uri from organisation o INNER JOIN graph g ON o.organisationId=g.organisationId WHERE g.graphId='"+graphToCleanId+"'");
            if (rs.next()) {
                RDFStoreDAO store = new RDFStoreDAO();
                if(!store.clearGraphBySparql(rs.getString("sparql_endpoint_uri"), rs.getString("sparql_endpoint_login"), rs.getString("sparql_endpoint_password"), rs.getString("graph_uri"))){
                    logger.error(MessageCatalog._00032_CONVERSION_ERROR_CLEARING_GRAPH);
                    rs.close();
                    statement.close();
                    connection.close();
                    execute();
                    return ERROR;
                }
                logger.error(MessageCatalog._00034_CONVERSION_GRAPH_CLEANED);
                addActionMessage(getText("conversion.graphCleaned")+rs.getString("graph_uri"));
            }
            rs.close();
            statement.close();
            connection.close();
            return execute(); 
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
            execute();
            return ERROR;
        }
        
    }
    /**
     * Get the name of the the template from a give template code
     * @param selectedTemplate
     * @return
     * @see
     * @since 1.0
     */
    private String getTemplateNameFromCode(String selectedTemplate){
        Connection connection = null;
        String templateName = "";
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement;
            statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select template_name from aliada.template where template_id="+selectedTemplate);
            if(rs.next()){
                templateName = rs.getString("template_name");
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
            return "";
        }
        return templateName;
    }
    /**
     * Gets the format of the file
     * @return
     * @throws SQLException
     * @see
     * @since 1.0
     */
    private String getFormat(String profile) throws SQLException {
        Connection connection = null;
        String format = null;
        connection = new DBConnectionManager().getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement
                .executeQuery("select metadata_name from t_metadata_scheme JOIN profile ON t_metadata_scheme.metadata_code=profile.metadata_scheme_code WHERE profile.profile_name= '"
                        + profile+"'");
        if (rs.next()) {
            format = rs.getString(1);
        }
        rs.close();
        statement.close();
        connection.close();
        return format;
    }

    /**
     * Enables the RDFizer
     * @throws IOException
     * @see
     * @since 1.0
     */
    private void enableRdfizer() throws IOException {
        URL url = new URL("http://aliada:8080/aliada-rdfizer-1.0/enable");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("PUT");
        if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
            setShowRdfizerButton(1);
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
     * @param addedId
     * @throws IOException
     * @see
     * @since 1.0
     */
    private void createJob(int addedId) throws IOException {
        URL url = new URL("http://aliada:8080/aliada-rdfizer-1.0/jobs/" + addedId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("PUT");
        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            setShowRdfizerButton(1);
            getGraphsDb();
            getTemplatesDb(); 
            String errorMessage = "Failed : HTTP error code : "+ conn.getResponseCode();
            conn.disconnect();
            throw new ConnectException(errorMessage);
        }
        else{
            logger.debug(MessageCatalog._00031_CONVERSION_RDFIZE_JOB);
            setShowRdfizerButton(0);
            conn.disconnect();            
        }
    }
    
    /**
     * Gets the available templates from the DB
     * @return
     * @see
     * @since 1.0
     */
    public String getTemplatesDb() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select * from aliada.template");
            templates = new HashMap<Integer, String>();
            while (rs.next()) {
                templates.put(rs.getInt("template_id"),
                        rs.getString("template_name"));
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
            return ERROR;
        }
        return SUCCESS;
    }
    /**
     * Gets the available graphs from the DB
     * @return
     * @see
     * @since 1.0
     */
    public String getGraphsDb() {
        String username = (String) ServletActionContext.getRequest().getSession().getAttribute("logedUser");
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT graphId, graph_uri FROM graph g INNER JOIN user u ON g.organisationId = u.organisationId WHERE u.user_name='"+username+"';");
            graphs = new HashMap<Integer, String>();
            while (rs.next()) {
                graphs.put(rs.getInt("graphId"),
                        rs.getString("graph_uri"));
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
            return ERROR;
        }
        return SUCCESS;
    }
    
    /**
     * Gets the graph uri from a graph code
     * @return
     * @see
     * @since 1.0
     */
    public String getGraphUri(String graphCode) {
        Connection connection = null;
        String graphUri = "";
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT graph_uri FROM graph WHERE graphId='"+graphCode+"';");
            if (rs.next()) {
                graphUri = rs.getString("graph_uri");
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
            return "";
        }
        return graphUri;
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
    public void setTemplates(HashMap<Integer, String> templates) {
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
    public void setImportedFile(FileWork importedFile) {
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
    public void setSelectedTemplate(String selectedTemplate) {
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
    public void setShowCheckButton(int showCheckButton) {
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
    public void setShowRdfizerButton(int showRdfizerButton) {
        this.showRdfizerButton = showRdfizerButton;
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
    public void setGraphs(HashMap<Integer, String> graphs) {
        this.graphs = graphs;
    }
    /**
     * @return Returns the selectedGraph.
     * @exception
     * @since 1.0
     */
    public String getSelectedGraph() {
        return selectedGraph;
    }
    /**
     * @param selectedGraph The selectedGraph to set.
     * @exception
     * @since 1.0
     */
    public void setSelectedGraph(String selectedGraph) {
        this.selectedGraph = selectedGraph;
    }
}
