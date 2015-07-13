// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.model.FileWork;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**
 * @author iosa
 * @since 1.0
 */
public class LinkingAction extends ActionSupport {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<Integer, String> datasets;
	private List<String> dataset;
    private FileWork fileToLink;
	private String title;
	private String seconds;
	private String datasetUrl;
	private final int NODATASETSELECTED = -1; 

    private final Log logger = new Log(LinkingAction.class);
    private ResourceBundle defaults = ResourceBundle.getBundle("defaultValues", getLocale());
    
    /**
     * Calls to the rdfizerStatus process.
     * @return String
     * @see
     * @since 1.0
     */
    public String execute() {
    	
    	if ((int)ServletActionContext.getRequest().getSession().getAttribute("type") == 1) {
    		
    		ServletActionContext.getRequest().getSession().setAttribute("action", defaults.getString("lang.linking"));
        	
            HttpSession session = ServletActionContext.getRequest().getSession();
            session.setAttribute("rdfizerStatus", defaults.getString("status.finishedRdfizer"));
            setFileToLink((FileWork) session.getAttribute("importedFile"));
    		
            //If it's properly rdfizered => change STATUS FINISHEDRDFIZER
            Connection connection = null;
            Statement updateStatement = null;
            try {
            	connection = new DBConnectionManager().getConnection();
            	updateStatement = connection.createStatement();
            	updateStatement.executeUpdate("UPDATE aliada.user_session set status='finishedRdfizer' where file_name='" + fileToLink.getFilename() + "'");
            	updateStatement.close();
        		connection.close();
            } catch (SQLException e) {
        		logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
        	} 

            // Get the datasets to link
            return getDatasetsDb();
    		
    	} else {
    		
    		ServletActionContext.getRequest().getSession().setAttribute("action", defaults.getString("lang.linking"));
        	
            HttpSession session = ServletActionContext.getRequest().getSession();
            session.setAttribute("rdfizerStatus", defaults.getString("status.runningLinking"));
            setFileToLink((FileWork) session.getAttribute("importedFile"));
    		
            //If it's properly rdfizered => change STATUS RUNNINGLINKING
            Connection connection = null;
            Statement updateStatement = null;
            try {
            	connection = new DBConnectionManager().getConnection();
            	updateStatement = connection.createStatement();
            	updateStatement.executeUpdate("UPDATE aliada.user_session set status='runningLinking' where file_name='" + fileToLink.getFilename() + "'");
            	updateStatement.close();
        		connection.close();
            } catch (SQLException e) {
        		logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
        	} 

            // Get '1' external datasets to show
            return getDatasetDb();
    		
    	}
    }
    /**
     * Calls to the loadPending process.
     * @return String
     * @see
     * @since 1.0
     */
    public String loadPending() {
    	
        HttpSession session = ServletActionContext.getRequest().getSession();
        setFileToLink((FileWork) session.getAttribute("importedFile"));
        
        if (this.fileToLink.getStatus().equals(defaults.getString("status.finishedRdfizer"))) {
        	
        	ServletActionContext.getRequest().getSession().setAttribute("action", defaults.getString("lang.linking"));
        	
        } else if (this.fileToLink.getStatus().equals(defaults.getString("status.runningLinking"))) {
        	
        	ServletActionContext.getRequest().getSession().setAttribute("action", defaults.getString("lang.default"));
        	
        } else if (this.fileToLink.getStatus().equals(defaults.getString("status.finishedLinking"))) {
        	
        	ServletActionContext.getRequest().getSession().setAttribute("action", defaults.getString("lang.checkLinking"));
        	
        }
        
        // Load some data
        if (this.fileToLink.getStatus().equals(defaults.getString("status.runningLinking")) 
        		|| this.fileToLink.getStatus().equals(defaults.getString("status.finishedLinking"))) {
             Connection con;
             dataset = new LinkedList<String>();
             try {
                 con = new DBConnectionManager().getConnection();
                 Statement st = con.createStatement();
                 ResultSet rs = st
                         .executeQuery("select name from aliada.linksdiscovery_subjob_instances where job_id=" + getFileToLink().getLdJobId());
                 while (rs.next()) {
                     String name = rs.getString("name");
                     // Quit String "ALIADA_" 
                     dataset.add(name.substring(7));
                 }
                 
                 rs = st
                         .executeQuery("select d.domain_name from aliada.dataset d inner join aliada.subset s "
                         		+ "on d.datasetId = s.datasetId where s.graph_uri='" + getFileToLink().getGraph() + "'");
                 while (rs.next()) {
                     String datasetWeb = defaults.getString("web") + rs.getString("domain_name");
                     setDatasetUrl(datasetWeb);
                 }
                 
                 rs.close();
                 st.close();
                 con.close();
                 
                 title = getText("dialog.publish.title");
                 seconds = getText("seconds");
                 if (dataset.size() == 0) {
                	 session.setAttribute("linkingJobId", NODATASETSELECTED);
                 } else {
                	 session.setAttribute("linkingJobId", getFileToLink().getLdJobId()); 
                 }
                 session.setAttribute("ldsJobId", getFileToLink().getLdsJobId());
                 
             } catch (SQLException e) {
                 logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
                 return ERROR;
             }
             
        }               
        return getDatasetsDb();
    }
    /**
     * Calls to the finishFileWork process.
     * @return String
     * @see
     * @since 1.0
     */
    public String addAnotherFileWork() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        List<FileWork> importedFiles = (List<FileWork>) session.getAttribute("importedFiles");
        FileWork importedFile = (FileWork) session.getAttribute("importedFile");
        if (importedFiles != null) {
        	for (FileWork file : importedFiles) {
                if (file.equals(importedFile)) {
                    file.setStatus(defaults.getString("status.finishedLinking"));
                }
            }
            session.setAttribute("importedFiles", importedFiles);
           
        	setFileToLink((FileWork) session.getAttribute("importedFile"));
        }
        
        return SUCCESS;
    }
    
    /**
     * Loads the datasets from the database.
     * @return String
     * @see
     * @since 1.0
     */
    private String getDatasetsDb() {
        datasets = new HashMap<Integer, String>();
        Connection con;
        try {
            con = new DBConnectionManager().getConnection();
            Statement st = con.createStatement();
            
            ResultSet rs = st
                    .executeQuery("select * from aliada.t_external_dataset where language ='" + getLocale().getISO3Language() + "'");

            while (rs.next()) {
                int code = rs.getInt("external_dataset_code");
                String name = rs.getString("external_dataset_name");
                this.datasets.put(code, name);
            }
            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        return SUCCESS;
    }
    
    /**
     * Loads the dataset from the database.
     * @return String
     * @see
     * @since 1.0
     */
    private String getDatasetDb() {
    	
        dataset = new LinkedList<String>();
        Connection con;
        try {
            con = new DBConnectionManager().getConnection();
            Statement st = con.createStatement();
            
            ResultSet rs = st
                    .executeQuery("select external_dataset_name from aliada.t_external_dataset where language ='" 
                    		+ getLocale().getISO3Language() + "' AND external_dataset_linkingreloadsource ='1'");
            
            while (rs.next()) {
                String name = rs.getString("external_dataset_name");
                dataset.add(name);
            }
            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        
        createJobLinking();
        createJobLDS();
        
        title = getText("dialog.publish.title");
        seconds = getText("seconds");
        
        return SUCCESS;
    }
    
    /**
     * Calls to the job deleting process.
     * @return String
     * @see
     * @since 1.0
     */
    public String checkLinking() {
    	
    	ServletActionContext.getRequest().getSession().setAttribute("action", defaults.getString("lang.checkLinking"));
    	
    	HttpSession session = ServletActionContext.getRequest().getSession();
    	setFileToLink((FileWork) session.getAttribute("importedFile"));
    	
    	title = getText("dialog.publish.title");
        seconds = getText("seconds");
    	
    	Connection connection = null;
	    Statement statement = null;
    	try {
        	connection = new DBConnectionManager().getConnection();
        	statement = connection.createStatement();
        	ResultSet rs = statement
                    .executeQuery("select d.domain_name from aliada.dataset d inner join aliada.subset s "
                    		+ "on d.datasetId = s.datasetId where s.graph_uri='" + getFileToLink().getGraph() + "'");
            while (rs.next()) {
                String datasetWeb = defaults.getString("web") + rs.getString("domain_name");
                setDatasetUrl(datasetWeb);
            }
            
          //If it's properly created  => change STATUS FINISHEDLINKING
        	statement = connection.createStatement();
        	statement.executeUpdate("UPDATE aliada.user_session set status='finishedLinking' where file_name='" + fileToLink.getFilename() + "'");
        	statement.close();
    		connection.close();
        } catch (SQLException e) {
    		logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
    	}
        
        session.setAttribute("rdfizerStatus", defaults.getString("status.finishedLinking"));
        
    	return getDatasetsDb();
    }
    
    /**
     * Calls to the link-discovery process.
     * @return String
     * @see
     * @since 1.0
     */
    public String startLinking() {
    	
    	ServletActionContext.getRequest().getSession().setAttribute("action", defaults.getString("lang.default"));
    	
    	HttpSession session = ServletActionContext.getRequest().getSession();
        setFileToLink((FileWork) session.getAttribute("importedFile"));
        createJobLinking();
        createJobLDS();
        
        title = getText("dialog.publish.title");
        seconds = getText("seconds");
        
        Connection connection = null;
        Statement updateStatement = null;
        
        try {
        	connection = new DBConnectionManager().getConnection();
        	updateStatement = connection.createStatement();
        	//If it's properly created  => change STATUS RUNNINGLINKING
        	updateStatement = connection.createStatement();
        	updateStatement.executeUpdate("UPDATE aliada.user_session set status='runningLinking' where file_name='" + fileToLink.getFilename() + "'");
        	updateStatement.close();
    		connection.close();
        } catch (SQLException e) {
    		logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
    	}
        
        session.setAttribute("rdfizerStatus", defaults.getString("status.runningLinking"));
        
        return getDatasetsDb();
    }
    /**
     * Creates the job that does the link-discovery
     * @param fileToLink
     * @see
     * @since 1.0
     */
    private void createJobLinking() {
        int addedId = 0;
        String ou, ol, op, og, td = "";
        if (fileToLink.getGraph() != null) {
            Connection connection = null;
            connection = new DBConnectionManager().getConnection();
            Statement statement;
            try {
                statement = connection.createStatement();
                ResultSet rs = statement
                        .executeQuery("select sparql_endpoint_uri, sparql_endpoint_login, sparql_endpoint_password, graph_uri, "
                        		+ "links_graph_uri, tmp_dir, linking_client_app_bin_dir,linking_client_app_user from aliada.organisation o "
                        		+ "INNER JOIN aliada.dataset d ON o.organisationId=d.organisationId INNER JOIN aliada.subset s ON d.datasetId=s.datasetId "
                        		+ "WHERE s.graph_uri='" + fileToLink.getGraph() + "'");
                if (rs.next()) {
                	ou = rs.getString("sparql_endpoint_uri");
                	ol = rs.getString("sparql_endpoint_login");
                	op = rs.getString("sparql_endpoint_password");
                	og = rs.getString("links_graph_uri");
                	td = rs.getString("tmp_dir");
                    PreparedStatement preparedStatement;
                    preparedStatement = connection
                            .prepareStatement(
                                    "INSERT INTO aliada.linksdiscovery_job_instances (input_uri, input_login, input_password, input_graph, output_uri, output_login,"
                                    + " output_password, output_graph, tmp_dir, client_app_bin_dir,client_app_user) VALUES(?,?,?,?,?,?,?,?,?,?,?)",
                                    PreparedStatement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, rs.getString("sparql_endpoint_uri"));
                    preparedStatement.setString(2, rs.getString("sparql_endpoint_login"));
                    preparedStatement.setString(3, rs.getString("sparql_endpoint_password"));
                    preparedStatement.setString(4, rs.getString("graph_uri"));
                    preparedStatement.setString(5, ou);
                    preparedStatement.setString(6, ol);
                    preparedStatement.setString(7, op);
                    preparedStatement.setString(8, og);
                    preparedStatement.setString(9, td);
                    preparedStatement.setString(10, rs.getString("linking_client_app_bin_dir"));
                    preparedStatement.setString(11, rs.getString("linking_client_app_user"));
                    preparedStatement.executeUpdate();
                    ResultSet rs2 = preparedStatement.getGeneratedKeys();
                    if (rs2.next()) {
                        addedId = (int) rs2.getInt(1);
                    }
                    rs2.close();
                    preparedStatement.close();
                    
                    statement = connection.createStatement();
                    String name;
                    if (dataset != null) {
                    	
                    	getFileToLink().setLdJobId(addedId);
                    	statement.executeUpdate("UPDATE aliada.user_session set links_disc_job_id=" + addedId + " where file_name='" + fileToLink.getFilename() + "'");
                    	
                    	if ((int)ServletActionContext.getRequest().getSession().getAttribute("type") == 1) {
                    		statement.executeUpdate("UPDATE aliada.t_external_dataset set external_dataset_linkingreloadsource='0' ");
                    	}
                    	
            			for (int i = 0; i < dataset.size(); i++) {            				
            				name = "ALIADA_" + dataset.get(i);
            				
            				if ((int)ServletActionContext.getRequest().getSession().getAttribute("type") == 1) {
            					statement.executeUpdate("UPDATE aliada.t_external_dataset set external_dataset_linkingreloadsource='1' "
            						+ "where external_dataset_name='" + dataset.get(i) + "'");
            				}
            				
            				rs = statement
		                            .executeQuery("select external_dataset_linkingfile, external_dataset_linkingnumthreads,"
		                            		+ " external_dataset_linkingreloadsource, external_dataset_linkingreloadtarget "
		                            		+ "from aliada.t_external_dataset where external_dataset_name='" + dataset.get(i)
		                            		+ "' AND language='" + getLocale().getISO3Language() + "'");
            				
		                    while (rs.next()) {
		                        preparedStatement = connection
		                                .prepareStatement(
		                                        "INSERT INTO aliada.linksdiscovery_subjob_instances (job_id, name, config_file, "
		                                        + "num_threads, reload_source, reload_target, output_uri, output_login, output_password, "
		                                        + "output_graph, tmp_dir) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
		                        preparedStatement.setString(1, String.valueOf(addedId));
		                        preparedStatement.setString(2, name);
		                        preparedStatement.setString(3, rs.getString("external_dataset_linkingfile"));
		                        preparedStatement.setString(4, rs.getString("external_dataset_linkingnumthreads"));
		                        preparedStatement.setString(5, rs.getString("external_dataset_linkingreloadsource"));
		                        preparedStatement.setString(6, rs.getString("external_dataset_linkingreloadtarget"));
		                        preparedStatement.setString(7, ou);
		                        preparedStatement.setString(8, ol);
		                        preparedStatement.setString(9, op);
		                        preparedStatement.setString(10, og);
		                        preparedStatement.setString(11, td);
		                        preparedStatement.executeUpdate();
		                    }
            			}
                    } else {
                    	
                    	if ((int)ServletActionContext.getRequest().getSession().getAttribute("type") == 1) {
                    		statement.executeUpdate("UPDATE aliada.t_external_dataset set external_dataset_linkingreloadsource='0' ");
                    	}
                    	
                    	dataset = new LinkedList<String>();
                    }
                    
                    rs.close();
                    preparedStatement.close();
                    statement.close();
                    connection.close();
                    
                    URL url;
                    HttpURLConnection conn = null;
                    try {
					    url = new URL(defaults.getString("createJobLinking"));
						conn = (HttpURLConnection) url.openConnection();
                        conn.setDoOutput(true);
                        conn.setRequestMethod(defaults.getString("requestMethod.post"));
                        conn.setRequestProperty("Content-Type",
                                "application/x-www-form-urlencoded");
                        String param = "jobid=" + addedId;
                        conn.setDoOutput(true);
                        DataOutputStream wr = new DataOutputStream(
                                conn.getOutputStream());
                        wr.writeBytes(param);
                        wr.flush();
                        wr.close();
                        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                            throw new ConnectException(MessageCatalog._00015_HTTP_ERROR_CODE
                                            + conn.getResponseCode());
                        } else {
                            logger.debug(MessageCatalog._00050_LINKING_JOB);
                            ServletActionContext.getRequest().getSession()
                                    .setAttribute("linkingJobId", addedId);
                        }
                        conn.disconnect();
                    } catch (MalformedURLException e) {
                        logger.error(MessageCatalog._00014_MALFORMED_URL_EXCEPTION, e);
                    } catch (IOException e) {
                        logger.error(MessageCatalog._00012_IO_EXCEPTION, e);
                    }
                }
            } catch (SQLException e) {
                logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            }
        } else {
           logger.debug(MessageCatalog._00041_NOT_FILE_TO_LINK); 
        }        
    }
    /**
     * Creates a job for the linked data server.
     * @see
     * @since 1.0
     */
    private void createJobLDS() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        int addedId = 0;
        String datasetWeb = defaults.getString("web");
        if (fileToLink.getGraph() != null) {
            Connection connection = null;
            connection = new DBConnectionManager().getConnection();
            Statement statement;
            try {
                statement = connection.createStatement();
                ResultSet rs = statement
                        .executeQuery("select store_ip,store_sql_port,sql_login, sql_password, isql_command_path, "
                        		+ "o.virtuoso_http_server_root, o.aliada_ontology, s.datasetId, o.isql_commands_file_dataset_default, "
                        		+ "o.isql_commands_file_subset_default, o.organisationId, o.tmp_dir, d.domain_name from aliada.organisation o "
                        		+ "INNER JOIN aliada.dataset d ON o.organisationId=d.organisationId "
                        		+ "INNER JOIN aliada.subset s ON d.datasetId=s.datasetId INNER JOIN aliada.rdfizer_job_instances r "
                        		+ "WHERE s.graph_uri='" + fileToLink.getGraph() + "' ORDER BY r.job_id DESC LIMIT 1");
                if (rs.next()) {
                    PreparedStatement preparedStatement = connection
                            .prepareStatement(
                                    "INSERT INTO aliada.linkeddataserver_job_instances (store_ip,store_sql_port,sql_login,sql_password,"
                                    + "isql_command_path,virtuoso_http_server_root,aliada_ontology,datasetId,isql_commands_file_dataset_default,"
                                    + "isql_commands_file_subset_default,organisationId,tmp_dir)"
                                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)",
                                    PreparedStatement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, rs.getString("store_ip"));
                    preparedStatement.setInt(2, rs.getInt("store_sql_port"));
                    preparedStatement.setString(3, rs.getString("sql_login"));
                    preparedStatement.setString(4, rs.getString("sql_password"));
                    preparedStatement.setString(5, rs.getString("isql_command_path"));
                    preparedStatement.setString(6, rs.getString("virtuoso_http_server_root"));
                    preparedStatement.setString(7, rs.getString("aliada_ontology"));
                    preparedStatement.setInt(8, rs.getInt("datasetId"));
                    preparedStatement.setString(9, rs.getString("isql_commands_file_dataset_default"));
                    preparedStatement.setString(10, rs.getString("isql_commands_file_subset_default"));
                    preparedStatement.setInt(11, rs.getInt("organisationId"));
                    preparedStatement.setString(12, rs.getString("tmp_dir"));
                    preparedStatement.executeUpdate();
                    
                    datasetWeb = datasetWeb + rs.getString("domain_name");
                    setDatasetUrl(datasetWeb);
                    
                    ResultSet rs2 = preparedStatement.getGeneratedKeys();
                    if (rs2.next()) {
                        addedId = (int) rs2.getInt(1);
                    }
                    rs2.close();
                    
                    getFileToLink().setLdsJobId(addedId);
                	statement.executeUpdate("UPDATE aliada.user_session set linked_data_server_job_id=" + addedId + " where file_name='" + fileToLink.getFilename() + "'");
                    
                    preparedStatement.close();          
                    URL url;
                    HttpURLConnection conn = null;
                    try {
					    url = new URL(defaults.getString("createJobLDS"));
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoOutput(true);
                        conn.setRequestMethod(defaults.getString("requestMethod.post"));
                        conn.setRequestProperty("Content-Type",
                                "application/x-www-form-urlencoded");
                        String param = "jobid=" + addedId;
                        conn.setDoOutput(true);
                        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                        wr.writeBytes(param);
                        wr.flush();
                        wr.close();
                        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                            throw new ConnectException(MessageCatalog._00015_HTTP_ERROR_CODE
                                    + conn.getResponseCode());
                        } else {
                            session.setAttribute("ldsJobId", addedId);
                        }
                        conn.disconnect();
                        session.setAttribute("ldsStarted", true);
                    } catch (MalformedURLException e) {
                        logger.error(MessageCatalog._00014_MALFORMED_URL_EXCEPTION, e);
                    } catch (IOException e) {
                        logger.error(MessageCatalog._00012_IO_EXCEPTION, e);
                    }
                }
                rs.close();
                statement.close();
                connection.close();  
                } catch (SQLException e) {
                    logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            }
        } else {
            logger.debug(MessageCatalog._00041_NOT_FILE_TO_LINK);             
        }
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
     * @param datasets
     *            The datasets to set.
     * @exception
     * @since 1.0
     */
    public void setDatasets(final HashMap<Integer, String> datasets) {
        this.datasets = datasets;
    }
    /**
     * @return Returns the dataset.
     * @exception
     * @since 1.0
     */
    public List<String> getDataset() {
		return dataset;
	}
    /**
     * @param dataset
     *            The dataset to set.
     * @exception
     * @since 1.0
     */
	public void setDataset(final List<String> dataset) {
		this.dataset = dataset;
	}
	/**
     * @return Returns the fileToLink.
     * @exception
     * @since 1.0
     */
    public FileWork getFileToLink() {
        return fileToLink;
    }

    /**
     * @param fileToLink
     *            The fileToLink to set.
     * @exception
     * @since 1.0
     */
    public void setFileToLink(final FileWork fileToLink) {
        this.fileToLink = fileToLink;
    }
    
    /**
     * @return Returns the title.
     * @exception
     * @since 1.0
     */
	public String getTitle() {
		return title;
	}
	/**
     * @param title
     *            The title to set.
     * @exception
     * @since 1.0
     */
	public void setTitle(final String title) {
		this.title = title;
	}
	 /**
     * @return Returns the seconds.
     * @exception
     * @since 1.0
     */
	 public String getSeconds() {
		return seconds;
	}
	 /**
	     * @param seconds
	     *            The seconds to set.
	     * @exception
	     * @since 1.0
	     */
	public void setSeconds(final String seconds) {
		this.seconds = seconds;
	}
	/**
     * @return Returns the datasetUrl.
     * @exception
     * @since 1.0
     */
	public String getDatasetUrl() {
		return datasetUrl;
	}
	/**
     * @param datasetUrl
     *            The datasetUrl to set.
     * @exception
     * @since 1.0
     */
	public void setDatasetUrl(final String datasetUrl) {
		this.datasetUrl = datasetUrl;
	}

}
