// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.parser.XmlParser;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**
 * This class contains methods related to the creation of URIs
 * @author iosa
 * @version $Revision: 1.1 $
 * @since 1.0
 */
public class LdsAction extends ActionSupport {
    
    private String importFile;
    private String status;
    private String startDate;
    private String endDate;
    private final Log logger = new Log(LdsAction.class);
    
    /**
     * Does the call to the linked data server service
     * @return String
     * @see
     * @since 1.0
     */
    public String startLDS() {
        importFile = (String) ServletActionContext.getRequest().getSession()
                .getAttribute("fileToLink");
        if (importFile != null) {
            createJobLDS(importFile);
            try {
                return getInfoLDS();
            } catch (IOException e) {
                logger.debug(MessageCatalog._00012_IO_EXCEPTION);
                e.printStackTrace();
                return ERROR;
            }
        } else {
            return ERROR;
        }
    }
    
    /**
     * Creates a job for the linked data server
     * @param fileToLink the path of file from where is going to create the URIs
     * @see
     * @since 1.0
     */
    private void createJobLDS(String fileToLink) {
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
                    url = new URL("http://localhost:8889/lds/jobs/");
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
                        session.setAttribute("fileToLink", fileToLink);
                        session.setAttribute("fileToLinkId", addedId);
                    }
                    conn.disconnect();
                    session.setAttribute("ldsStarted", true);
                    getInfoLDS();
                } catch (MalformedURLException e) {
                    logger.debug(MessageCatalog._00014_MALFORMED_URL_EXCEPTION);                    
                    e.printStackTrace();
                } catch (IOException e) {
                    logger.debug(MessageCatalog._00012_IO_EXCEPTION);
                    e.printStackTrace();
                }
            }
            rs.close();
            statement.close();
            connection.close();  
            } catch (SQLException e) {
                logger.debug(MessageCatalog._00011_SQL_EXCEPTION);
                e.printStackTrace();
        }
    }

    /**
     * Gets the information of the linked data server process
     * @return String
     * @throws IOException
     * @see
     * @since 1.0
     */
    public String getInfoLDS() throws IOException {
        HttpSession session = ServletActionContext.getRequest().getSession();
        if(session.getAttribute("ldsStarted") != null){
        importFile = (String) session.getAttribute("fileToLink");
        int fileToLinkId = (int) session.getAttribute("fileToLinkId");
        URL url = new URL("http://localhost:8889/lds/jobs/" + fileToLinkId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/xml");
        if (conn.getResponseCode() != 202) {
            logger.debug(MessageCatalog._00015_HTTP_ERROR_CODE + conn.getResponseCode());
        }
        try {
            XmlParser parser = new XmlParser();
            Document doc = parser.parseXML(conn.getInputStream());
            NodeList readNode = doc.getElementsByTagName("startDate");
            String startDate = readNode.item(0).getTextContent();
            Locale locale = (Locale) session.getAttribute("WW_TRANS_I18N_LOCALE");
            if (locale == null) {
                locale = Locale.ROOT;
            }
            SimpleDateFormat dateFormatIn = new SimpleDateFormat(
                    "YYYY-MM-dd'T'HH:mm:ss");
            SimpleDateFormat dateFormatOut = new SimpleDateFormat(
                    "d MMMM yyyy',' HH:mm:ss",locale);
            setStartDate(dateFormatOut.format(dateFormatIn
                    .parse(startDate)));
            readNode = doc.getElementsByTagName("startDate");
            String endDate = readNode.item(0).getTextContent();
            setEndDate(dateFormatOut.format(dateFormatIn
                    .parse(endDate)));
            readNode = doc.getElementsByTagName("status");
            if(readNode.item(0).getTextContent().equals("running")){
                setStatus(getText("linkingInfo.running"));
            }
            else if(readNode.item(0).getTextContent().equals("finished")){
                setStatus(getText("linkingInfo.completed"));
            }
            conn.disconnect();
            return SUCCESS;
        } catch (Exception e) {
            logger.error(MessageCatalog._00016_ERROR_READING_XML);
            e.printStackTrace();
            conn.disconnect();
            return ERROR;
        }
        }
        else{
            setStatus(getText("ldsInfo.not.started"));
            return SUCCESS;
        }       
    }

    /**
     * @return Returns the importFile.
     * @exception
     * @since 1.0
     */
    public String getImportFile() {
        return importFile;
    }

    /**
     * @param importFile The importFile to set.
     * @exception
     * @since 1.0
     */
    public void setImportFile(String importFile) {
        this.importFile = importFile;
    }

    /**
     * @return Returns the status.
     * @exception
     * @since 1.0
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status to set.
     * @exception
     * @since 1.0
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return Returns the startDate.
     * @exception
     * @since 1.0
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate The startDate to set.
     * @exception
     * @since 1.0
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return Returns the endDate.
     * @exception
     * @since 1.0
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate The endDate to set.
     * @exception
     * @since 1.0
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
