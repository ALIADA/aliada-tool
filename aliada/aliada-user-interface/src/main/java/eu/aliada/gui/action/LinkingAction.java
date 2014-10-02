// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**
 * @author iosa
 * @since 1.0
 */
public class LinkingAction extends ActionSupport {

    private boolean linkingStarted;
    private boolean notFiles;
    private HashMap<Integer, String> datasets;
    private String fileToLink;
    private Integer rdfizerJob;
    
    private int state;

    private final Log logger = new Log(LinkingAction.class);

    public String execute() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        File importFile = (File) session.getAttribute("importFile");
        if (importFile == null) {
            setNotFiles(true);
        } else {
            setNotFiles(false);
            setFileToLink(importFile.getAbsolutePath());
        }
        return getDatasetsDb();
    }

    /**
     * Loads the datasets from the database
     * @return
     * @see
     * @since 1.0
     */
    private String getDatasetsDb() {
        datasets = new HashMap();
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
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * Calls to the link-discovery process
     * @return
     * @see
     * @since 1.0
     */
    public String startLinking() {
        File importFile = (File) ServletActionContext.getRequest()
                .getSession().getAttribute("importFile");
        if (importFile == null) {
            setNotFiles(true);
            return getDatasetsDb();
        } else {
            setNotFiles(false);
            setFileToLink(importFile.getAbsolutePath());
            createJobLinking();
            createJobLDS();
            setLinkingStarted(true);
            return getDatasetsDb();
        }
    }
    /**
     * Creates the job that does the link-discovery
     * @param fileToLink
     * @see
     * @since 1.0
     */
    private void createJobLinking() {
        int addedId = 0;
        Connection connection = null;
        connection = new DBConnectionManager().getConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select sparql_endpoint_uri, sparql_endpoint_login, sparql_endpoint_password, graph_uri, linking_config_file, tmp_dir, linking_client_app_bin_dir, rdf_sink_folder, rdf_sink_login, rdf_sink_password from organisation");
            if (rs.next()) {
                PreparedStatement preparedStatement;
                preparedStatement = connection
                        .prepareStatement(
                                "INSERT INTO linksdiscovery_job_instances (input_uri, input_login, input_password, input_graph, output_uri, output_login, output_password, output_graph, config_file, rdf_sink_folder, rdf_sink_login, rdf_sink_password, tmp_dir, client_app_bin_dir) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1,rs.getString("sparql_endpoint_uri"));
                preparedStatement.setString(2, rs.getString("sparql_endpoint_login"));
                preparedStatement.setString(3, rs.getString("sparql_endpoint_login"));
                preparedStatement.setString(4, rs.getString("graph_uri"));
                preparedStatement.setString(5, rs.getString("sparql_endpoint_uri"));
                preparedStatement.setString(6, rs.getString("sparql_endpoint_login"));
                preparedStatement.setString(7, rs.getString("sparql_endpoint_password"));
                preparedStatement.setString(8, rs.getString("graph_uri"));
                preparedStatement.setString(9, rs.getString("linking_config_file"));
                preparedStatement.setString(10, rs.getString("rdf_sink_folder"));
                preparedStatement.setString(11, rs.getString("rdf_sink_login"));
                preparedStatement.setString(12, rs.getString("rdf_sink_password"));
                preparedStatement.setString(13, rs.getString("tmp_dir"));
                preparedStatement.setString(14, rs.getString("linking_client_app_bin_dir"));
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
                    url = new URL("http://aliada.scanbit.net:8080/aliada-links-discovery-1.0/jobs/");
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
                        throw new RuntimeException(
                                "Failed : HTTP error code : "
                                        + conn.getResponseCode());
                    } else {
                        logger.debug(MessageCatalog._00050_LINKING_JOB);
                        ServletActionContext.getRequest().getSession()
                                .setAttribute("linkingJobId", addedId);
                    }
                    conn.disconnect();
                } catch (MalformedURLException e) {
                    logger.error(MessageCatalog._00014_MALFORMED_URL_EXCEPTION,e);
                } catch (IOException e) {
                    logger.error(MessageCatalog._00012_IO_EXCEPTION,e);
                }
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
        }

    }
    /**
     * Creates a job for the linked data server
     * @see
     * @since 1.0
     */
    private void createJobLDS() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        int addedId = 0;
        Connection connection = null;
        connection = new DBConnectionManager().getConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select store_ip,store_sql_port,sql_login, sql_password, graph_uri, dataset_base, isql_command_path, isql_commands_file, isql_commands_file_default from organisation");
            if (rs.next()) {
                PreparedStatement preparedStatement = connection
                        .prepareStatement(
                                "INSERT INTO linkeddataserver_job_instances (store_ip,store_sql_port,sql_login,sql_password,graph,dataset_base,isql_command_path,isql_commands_file,isql_commands_file_default) VALUES(?,?,?,?,?,?,?,?,?)",
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
                    url = new URL("http://aliada.scanbit.net:8080/aliada-linked-data-server-1.0/jobs/");
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
                        throw new RuntimeException("Failed : HTTP error code : "
                                + conn.getResponseCode());
                    } else {
                        session.setAttribute("ldsJobId", addedId);
                    }
                    conn.disconnect();
                    session.setAttribute("ldsStarted", true);
                } catch (MalformedURLException e) {
                    logger.error(MessageCatalog._00014_MALFORMED_URL_EXCEPTION,e);
                } catch (IOException e) {
                    logger.error(MessageCatalog._00012_IO_EXCEPTION,e);
                }
            }
            rs.close();
            statement.close();
            connection.close();  
            } catch (SQLException e) {
                logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
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
    public void setDatasets(HashMap<Integer, String> datasets) {
        this.datasets = datasets;
    }

    /**
     * @return Returns the notFiles.
     * @exception
     * @since 1.0
     */
    public boolean isNotFiles() {
        return notFiles;
    }

    /**
     * @param notFiles
     *            The notFiles to set.
     * @exception
     * @since 1.0
     */
    public void setNotFiles(boolean notFiles) {
        this.notFiles = notFiles;
    }

    /**
     * @return Returns the fileToLink.
     * @exception
     * @since 1.0
     */
    public String getFileToLink() {
        return fileToLink;
    }

    /**
     * @param fileToLink
     *            The fileToLink to set.
     * @exception
     * @since 1.0
     */
    public void setFileToLink(String fileToLink) {
        this.fileToLink = fileToLink;
    }

    /**
     * @return Returns the linkingStarted.
     * @exception
     * @since 1.0
     */
    public boolean isLinkingStarted() {
        return linkingStarted;
    }

    /**
     * @param linkingStarted
     *            The linkingStarted to set.
     * @exception
     * @since 1.0
     */
    public void setLinkingStarted(boolean linkingStarted) {
        this.linkingStarted = linkingStarted;
    }

    /**
     * @return Returns the state.
     * @exception
     * @since 1.0
     */
    public int getState() {
        return state;
    }

    /**
     * @param state The state to set.
     * @exception
     * @since 1.0
     */
    public void setState(int state) {
        this.state = state;
    }

}
