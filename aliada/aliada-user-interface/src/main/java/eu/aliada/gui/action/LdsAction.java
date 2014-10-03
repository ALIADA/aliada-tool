// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
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
    private boolean ldsStarted;
    
    private int state;
    
    private final Log logger = new Log(LdsAction.class);
    
    /**
     * Does the call to the linked data server service
     * @return String
     * @see
     * @since 1.0
     */
    public String startLDS() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        Integer rdfizerJob = (Integer) session.getAttribute("fileToLink");
        setState((int) session.getAttribute("state"));
        if(rdfizerJob!=null) {
            getFile(rdfizerJob);
            createJobLDS();
            try {
                logger.debug(MessageCatalog._00040_LDS_STARTED);
                return getInfoLDS();
            } catch (IOException e) {
                logger.error(MessageCatalog._00012_IO_EXCEPTION,e);
                return ERROR;
            }
        } else {
            return ERROR;
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
                    url = new URL("http://aliada:8080/aliada-linked-data-server-1.0/jobs/");
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
                    setLdsStarted(true);
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
     * Gets the information of the linked data server process
     * @return String
     * @throws IOException
     * @see
     * @since 1.0
     */
    public String getInfoLDS() throws IOException {
        HttpSession session = ServletActionContext.getRequest().getSession();
        Integer rdfizerJob = (Integer) session.getAttribute("fileToLink");
        setState((int) session.getAttribute("state"));
        if(rdfizerJob!=null) {
            getFile(rdfizerJob);
        }
        if(session.getAttribute("ldsStarted") != null){
            setLdsStarted(true);
            int ldsJobId = (int) session.getAttribute("ldsJobId");
            URL url = new URL("http://aliada:8080/aliada-linked-data-server-1.0/jobs/" + ldsJobId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 202) {
                logger.error(MessageCatalog._00015_HTTP_ERROR_CODE + conn.getResponseCode());
            }
            try {
                Locale locale = (Locale) session.getAttribute("WW_TRANS_I18N_LOCALE");
                if (locale == null) {
                    locale = Locale.ROOT;
                }
                SimpleDateFormat dateFormatIn = new SimpleDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ss");
                SimpleDateFormat dateFormatOut = new SimpleDateFormat(
                        "d MMMM yyyy',' HH:mm:ss",locale);
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(reader);
                String startDate = (String) jsonObject.get("startDate");
                if(startDate!=null){
                    setStartDate(dateFormatOut.format(dateFormatIn.parse(startDate)));  
                }
                String endDate = (String) jsonObject.get("endDate");
                if(endDate!=null){
                    setEndDate(dateFormatOut.format(dateFormatIn
                            .parse(endDate))); 
                }
                String status = (String) jsonObject.get("status");
                if(status.equals("idle")){
                    setStatus(getText("linkingInfo.idle"));
                }
                else if(status.equals("running")){
                    setStatus(getText("linkingInfo.running"));
                }
                else if(status.equals("finished")){
                    if(state==3){
                        session.setAttribute("state", 5);                        
                    }
                    else if(state!=5){
                        session.setAttribute("state", 4);                        
                    }
                    setStatus(getText("linkingInfo.completed"));
                }
                conn.disconnect();
                setState((int) session.getAttribute("state"));
                return SUCCESS;
            } catch (Exception e) {
                logger.error(MessageCatalog._00016_ERROR_READING_XML,e);
                conn.disconnect();
                return ERROR;
            }
        }
        else{
            setLdsStarted(false);
            setStatus(getText("ldsInfo.not.started"));
            return SUCCESS;
        }       
    }
    
    private void getFile(int rdfizerJob) {
        Connection con;
        try {
            con = new DBConnectionManager().getConnection();
            Statement st;
            st = con.createStatement();
            ResultSet rs = st
                    .executeQuery("select datafile from aliada.rdfizer_job_instances WHERE job_id="
                            + rdfizerJob);
            if (rs.next()) {
                setImportFile(rs.getString(1));
            }
            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
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

    /**
     * @return Returns the ldsStarted.
     * @exception
     * @since 1.0
     */
    public boolean isLdsStarted() {
        return ldsStarted;
    }

    /**
     * @param ldsStarted The ldsStarted to set.
     * @exception
     * @since 1.0
     */
    public void setLdsStarted(boolean ldsStarted) {
        this.ldsStarted = ldsStarted;
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