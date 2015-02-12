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
import java.util.List;

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

    private HashMap<Integer, String> datasets;
    private FileWork fileToLink;
    private Integer rdfizerJob;

    private final Log logger = new Log(LinkingAction.class);
    
    /**
     * Calls to the rdfizerStatus process.
     * @return String
     * @see
     * @since 1.0
     */
    public String execute() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute("rdfizerStatus", "finishedRdfizer");
        setFileToLink((FileWork) session.getAttribute("importedFile"));
        
        //logger.info("STATUS FINISHEDRDFIZER", null);
		
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

        
        return getDatasetsDb();
    }
    /**
     * Calls to the loadPending process.
     * @return String
     * @see
     * @since 1.0
     */
    public String loadPending() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        //session.setAttribute("rdfizerStatus", "finished");
        setFileToLink((FileWork) session.getAttribute("importedFile"));
        
        //If it's properly rdfizered => change status depends on which is current state
        if (this.fileToLink.getStatus() == "finishedRdfizer") { //Pending link and check
         //Makes the same if we enter normally
        } else if (this.fileToLink.getStatus() == "runningLinking") { //Pending check
              createJobLinking();
              createJobLDS();
        } else if (this.fileToLink.getStatus() == "finishedLinking") { //Finish link and check
            
        }
        
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
                
        return getDatasetsDb();
    }
    /**
     * Calls to the finishFileWork process.
     * @return String
     * @see
     * @since 1.0
     */
    public String finishFileWork() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        List<FileWork> importedFiles = (List<FileWork>) session.getAttribute("importedFiles");
        FileWork importedFile = (FileWork) session.getAttribute("importedFile");
        for (FileWork file : importedFiles) {
            if (file.equals(importedFile)) {
                file.setStatus("finishedLinked");
            }
        }
        session.setAttribute("importedFiles", importedFiles);
       
    	setFileToLink((FileWork) session.getAttribute("importedFile"));
    	
    	//Delete file
    	Connection connection = null;
	    Statement statement = null;
		    try {
		        connection = new DBConnectionManager().getConnection();
		        statement = connection.createStatement();
		        statement.executeUpdate("DELETE FROM aliada.user_session where file_name='" + fileToLink.getFilename() + "'");
		        statement.close();
		        connection.close();
		    } catch (SQLException e) {
		        logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
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
                    .executeQuery("select * from aliada.t_external_dataset");
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
     * Calls to the job deleting process.
     * @return String
     * @see
     * @since 1.0
     */
    public String checkLinking() {
    	HttpSession session = ServletActionContext.getRequest().getSession();
    	setFileToLink((FileWork) session.getAttribute("importedFile"));
    	
    	//Delete file
    	Connection connection = null;
	    Statement statement = null;
		    try {
		        connection = new DBConnectionManager().getConnection();
		        statement = connection.createStatement();
		        statement.executeUpdate("DELETE FROM aliada.user_session where file_name='" + fileToLink.getFilename() + "'");
		        statement.close();
		        connection.close();
		    } catch (SQLException e) {
		        logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
		      }
    	return getDatasetsDb();
    }
    
    /**
     * Calls to the link-discovery process.
     * @return String
     * @see
     * @since 1.0
     */
    public String startLinking() {
    	HttpSession session = ServletActionContext.getRequest().getSession();
        setFileToLink((FileWork) session.getAttribute("importedFile"));
        createJobLinking();
        createJobLDS();
		
        //If it's properly rdfizered => change status
        Connection connection = null;
        Statement updateStatement = null;
        try {
        	connection = new DBConnectionManager().getConnection();
        	updateStatement = connection.createStatement();
        	updateStatement.executeUpdate("UPDATE aliada.user_session set status='finishedLinking' where file_name='" + fileToLink.getFilename() + "'");
        	updateStatement.close();
    		connection.close();
        } catch (SQLException e) {
    		logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
    	}
        
        session.setAttribute("rdfizerStatus", "finishedLinking");
        
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
        if (fileToLink.getGraph() != null) {
            Connection connection = null;
            connection = new DBConnectionManager().getConnection();
            Statement statement;
            try {
                statement = connection.createStatement();
                ResultSet rs = statement
                        .executeQuery("select sparql_endpoint_uri, sparql_endpoint_login, sparql_endpoint_password, graph_uri, tmp_dir, "
                        		+ "linking_client_app_bin_dir,linking_client_app_user from organisation "
                        		+ "o INNER JOIN graph g ON o.organisationId=g.organisationId WHERE g.graph_uri='" + fileToLink.getGraph() + "'");
                if (rs.next()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = connection
                            .prepareStatement(
                                    "INSERT INTO linksdiscovery_job_instances (input_uri, input_login, input_password, input_graph, output_uri, output_login,"
                                    + " output_password, output_graph, tmp_dir, client_app_bin_dir,client_app_user) VALUES(?,?,?,?,?,?,?,?,?,?,?)",
                                    PreparedStatement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, rs.getString("sparql_endpoint_uri"));
                    preparedStatement.setString(2, rs.getString("sparql_endpoint_login"));
                    preparedStatement.setString(3, rs.getString("sparql_endpoint_login"));
                    preparedStatement.setString(4, rs.getString("graph_uri"));
                    preparedStatement.setString(5, rs.getString("sparql_endpoint_uri"));
                    preparedStatement.setString(6, rs.getString("sparql_endpoint_login"));
                    preparedStatement.setString(7, rs.getString("sparql_endpoint_password"));
                    preparedStatement.setString(8, rs.getString("graph_uri"));
                    preparedStatement.setString(9, rs.getString("tmp_dir"));
                    preparedStatement.setString(10, rs.getString("linking_client_app_bin_dir"));
                    preparedStatement.setString(11, rs.getString("linking_client_app_user"));
                    preparedStatement.executeUpdate();
                    ResultSet rs2 = preparedStatement.getGeneratedKeys();
                    if (rs2.next()) {
                        addedId = (int) rs2.getInt(1);
                    }
                    rs2.close();
                    preparedStatement.close();
                    connection.close();
                    URL url;
                    HttpURLConnection conn = null;
                    try {
					    url = new URL("http://aliada:8080/aliada-links-discovery-1.0/jobs/");
//                        url = new URL("http://localhost:8890/links-discovery");
                        
						conn = (HttpURLConnection) url.openConnection();
                        conn.setDoOutput(true);
                        conn.setRequestMethod("POST");
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
                            throw new ConnectException(
                                    "Failed : HTTP error code : "
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
        if (fileToLink.getGraph() != null) {
            Connection connection = null;
            connection = new DBConnectionManager().getConnection();
            Statement statement;
            try {
                statement = connection.createStatement();
                ResultSet rs = statement
                        .executeQuery("select store_ip,store_sql_port,sql_login, sql_password, graph_uri, dataset_base, "
                        		+ "isql_command_path, isql_commands_file, isql_commands_file_default, listening_host, virtual_host from organisation "
                        		+ "o INNER JOIN graph g ON o.organisationId=g.organisationId WHERE g.graph_uri='" + fileToLink.getGraph() + "'");
                if (rs.next()) {
                    PreparedStatement preparedStatement = connection
                            .prepareStatement(
                                    "INSERT INTO linkeddataserver_job_instances (store_ip,store_sql_port,sql_login,sql_password,graph,dataset_base,"
                                    + "isql_command_path,isql_commands_file,isql_commands_file_default,listening_host,virtual_host) VALUES(?,?,?,?,?,?,?,?,?,?,?)",
                                    PreparedStatement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, rs.getString("store_ip"));
                    preparedStatement.setInt(2, rs.getInt("store_sql_port"));
                    preparedStatement.setString(3, rs.getString("sql_login"));
                    preparedStatement.setString(4, rs.getString("sql_password"));
                    preparedStatement.setString(5, rs.getString("graph_uri"));
                    preparedStatement.setString(6, rs.getString("dataset_base"));
                    preparedStatement.setString(7, rs.getString("isql_command_path"));
                    preparedStatement.setString(8, rs.getString("isql_commands_file"));
                    preparedStatement.setString(9, rs.getString("isql_commands_file_default"));
                    preparedStatement.setString(10, rs.getString("listening_host"));
                    preparedStatement.setString(11, rs.getString("virtual_host"));
                    preparedStatement.executeUpdate();
                    ResultSet rs2 = preparedStatement.getGeneratedKeys();
                    if (rs2.next()) {
                        addedId = (int) rs2.getInt(1);
                    }
                    rs2.close();
                    preparedStatement.close();          
                    URL url;
                    HttpURLConnection conn = null;
                    try {
					    url = new URL("http://aliada:8080/aliada-linked-data-server-1.0/jobs/");
//                        url = new URL("http://localhost:8889/lds");
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoOutput(true);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type",
                                "application/x-www-form-urlencoded");
                        String param = "jobid=" + addedId;
                        conn.setDoOutput(true);
                        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                        wr.writeBytes(param);
                        wr.flush();
                        wr.close();
                        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                            throw new ConnectException("Failed : HTTP error code : "
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

}
