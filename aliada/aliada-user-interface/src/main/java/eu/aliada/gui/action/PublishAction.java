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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.model.FileWork;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**
 * This class is the CKAN publication.
 * @author xabier
 * @version $Revision: 1.1 $, $Date: 2015/03/05 15:20:54 $
 * @since 1.0
 */
public class PublishAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ckanDatasetUrl;
	
	private final Log logger = new Log(LinkingAction.class);
	/**
     * Call to the execute method.
     * @return String
     * @see
     * @since 1.0
     */
	public String execute() {
		
    	//Delete file/s from session
    	HttpSession session = ServletActionContext.getRequest().getSession();
    	
    	List<FileWork> importedFiles = new ArrayList<FileWork>();
    	session.setAttribute("importedFiles", importedFiles);
		
		Connection connection = new DBConnectionManager().getConnection();
        Statement statement;
        ResultSet rs;
        try {
        	//Delete file/s from database
//        	connection = new DBConnectionManager().getConnection();
//		    statement = connection.createStatement();
//		    statement.executeUpdate("DELETE FROM aliada.user_session where user_name='" + user + "'AND status='finishedLinking';");
        	
			statement = connection.createStatement();
			rs = statement
			        .executeQuery("select org_name, ckan_org_url, ckan_dataset_url from aliada.ckancreation_job_instances ORDER BY job_id DESC LIMIT 1");
			if (rs.next()) {
				String orgName = rs.getString("org_name");
				String ckanOrgUrl = rs.getString("ckan_org_url");
				ckanDatasetUrl = rs.getString("ckan_dataset_url");
				statement = connection.createStatement();
				statement.executeUpdate("UPDATE aliada.organisation SET ckan_org_url='" + ckanOrgUrl + "' where org_name='" + orgName + "'");
			}
			rs.close();
	        statement.close();
	        connection.close();
		} catch (SQLException e) {
			logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
		}
		return SUCCESS;
	}
	
	/**
     * Call to the publication module.
     * @return String
     * @see
     * @since 1.0
     */
    public String publish() {

        int addedId = 0;
    	HttpSession session = ServletActionContext.getRequest().getSession();
    	String user = (String) session.getAttribute("logedUser");
            Connection connection = null;
            connection = new DBConnectionManager().getConnection();
            Statement statement;
            try {
                statement = connection.createStatement();
                
                //Delete file/s from database
    		    statement.executeUpdate("DELETE FROM aliada.user_session where user_name='" + user + "'AND status='finishedLinking';");
    		    
                ResultSet rs = statement
                        .executeQuery("select ckan_api_url, ckan_api_key, o.tmp_dir, store_ip, store_sql_port, "
                        		+ "sql_login, sql_password,isql_command_path, virtuoso_http_server_root, aliada_ontology, "
                        		+ "org_name, org_description,org_home_page, d.datasetId, l.job_id, o.organisationId, o.isql_commands_file_graph_dump "
                        		+ "from aliada.organisation o "
                        		+ "INNER JOIN aliada.dataset d ON o.organisationId = d.organisationId "
                        		+ "INNER JOIN aliada.subset s ON d.datasetId = s.datasetId "
                        		+ "INNER JOIN aliada.linksdiscovery_job_instances l ON l.input_graph = s.graph_uri "
                        		+ "ORDER BY l.job_id DESC LIMIT 1;");
                if (rs.next()) {
                    PreparedStatement preparedStatement = connection
                            .prepareStatement(
                                    "INSERT INTO aliada.ckancreation_job_instances (ckan_api_url, ckan_api_key, tmp_dir, store_ip,"
                                    + " store_sql_port, sql_login, sql_password, isql_command_path, virtuoso_http_server_root,"
                                    + " aliada_ontology, org_name, org_description, org_home_page, datasetId, job_id, organisationId, isql_commands_file_graph_dump) "
                                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                    PreparedStatement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, rs.getString("ckan_api_url"));
                    preparedStatement.setString(2, rs.getString("ckan_api_key"));
                    preparedStatement.setString(3, rs.getString("tmp_dir"));
                    preparedStatement.setString(4, rs.getString("store_ip"));
                    preparedStatement.setInt(5, rs.getInt("store_sql_port"));
                    preparedStatement.setString(6, rs.getString("sql_login"));
                    preparedStatement.setString(7, rs.getString("sql_password"));
                    preparedStatement.setString(8, rs.getString("isql_command_path"));
                    preparedStatement.setString(9, rs.getString("virtuoso_http_server_root"));
                    preparedStatement.setString(10, rs.getString("aliada_ontology"));
                    preparedStatement.setString(11, rs.getString("org_name"));
                    preparedStatement.setString(12, rs.getString("org_description"));
                    preparedStatement.setString(13, rs.getString("org_home_page"));
                    preparedStatement.setInt(14, rs.getInt("datasetId"));
                    preparedStatement.setInt(15, rs.getInt("job_id"));
                    preparedStatement.setInt(16, rs.getInt("organisationId"));
                    preparedStatement.setString(17, rs.getString("isql_commands_file_graph_dump"));
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
					    url = new URL("http://localhost:8080/aliada-ckan-datahub-page-creation-2.0/jobs/");
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
                        } 
                        conn.disconnect();
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
            
        execute();
		    
        return SUCCESS;
    }
    /**
     * @return Returns the ckanDatasetUrl.
     * @exception
     * @since 1.0
     */
	public String getCkanDatasetUrl() {
		return ckanDatasetUrl;
	}
	/**
     * @param ckanDatasetUrl
     *            The ckanDatasetUrl to set.
     * @exception
     * @since 1.0
     */
	public void setCkanDatasetUrl(final String ckanDatasetUrl) {
		this.ckanDatasetUrl = ckanDatasetUrl;
	}
	
}
