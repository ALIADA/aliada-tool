// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.shared.log.Log;
import eu.aliada.gui.log.MessageCatalog;

/**
 * This class provides information of the link discovery
 * @author iosa
 * @version $Revision: 1.1 $
 * @since 1.0
 */
public class LinkingInfoAction extends ActionSupport {

    private String linkingFile;
    private String status;
    private String startDate;
    private String endDate;
    private String numLinks;
    private HashMap<String, String> datasets;
    
    private int state;

    private final Log logger = new Log(LinkingInfoAction.class);

    
    public String execute() {
        setLinkingFile((String) ServletActionContext.getRequest().getSession()
                .getAttribute("linkingFile"));
        setState((int) ServletActionContext.getRequest().getSession().getAttribute("state"));
        try {
            getInfo();
        } catch (IOException e) {
            logger.error(MessageCatalog._00012_IO_EXCEPTION,e);
            return ERROR;
        } 
        return SUCCESS;
    }
    /**
     * Get the information from the REST service of linking
     * @throws IOException
     * @see
     * @since 1.0
     */
    private void getInfo() throws IOException {
        HttpSession session = ServletActionContext.getRequest().getSession();
        setState((int) session.getAttribute("state"));
        Locale locale = (Locale) session.getAttribute("WW_TRANS_I18N_LOCALE");
        if (locale == null) {
            locale = Locale.ROOT;
        }
        SimpleDateFormat dateFormatIn = new SimpleDateFormat(
                "YYYY-MM-dd'T'HH:mm:ss");
        SimpleDateFormat dateFormatOut = new SimpleDateFormat(
                "d MMMM yyyy',' HH:mm:ss",locale);
        int linkingJobId = (int) session.getAttribute("linkingJobId");
        URL url = new URL("http://aliada:8080/aliada-links-discovery-1.0/jobs/"+linkingJobId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        if (conn.getResponseCode() != 202) {
            logger.error(MessageCatalog._00015_HTTP_ERROR_CODE
                    + conn.getResponseCode());
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray subjobs = (JSONArray) jsonObject.get("subjobs");
            Iterator i = subjobs.iterator();
            HashMap<String, String> datasets = new HashMap<String,String>();
            while (i.hasNext()) {
                JSONObject innerObj = (JSONObject) i.next();
                String name = (String) innerObj.get("name");
                Long numLinksDataset = (Long) innerObj.get("numLinks");
                datasets.put(name, numLinksDataset.toString());
            }
            this.setDatasets(datasets);
            Long numLinks = (Long) jsonObject.get("numLinks");
            setNumLinks(numLinks.toString());
            String startDate = (String) jsonObject.get("startDate");
            if(startDate!=null){
                this.setStartDate(dateFormatOut.format(dateFormatIn.parse(startDate)));  
            }
            String endDate = (String) jsonObject.get("endDate");
            if(endDate!=null){
                this.setEndDate(dateFormatOut.format(dateFormatIn
                        .parse(endDate))); 
            }
            String status = (String) jsonObject.get("status");
            if(status.equals("idle")){
                setStatus(getText("linkingInfo.idle"));
            }
            else if(status.equals("running")){
                setStatus(getText("linkingInfo.running"));
            }
            else if(status.equals("completed")){
                setStatus(getText("linkingInfo.completed"));
            }
          } catch (Exception e) {
            logger.error(MessageCatalog._00016_ERROR_READING_XML,e);
            conn.disconnect();
        }
        conn.disconnect();
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
     * @param status
     *            The status to set.
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
     * @param startDate
     *            The startDate to set.
     * @exception
     * @since 1.0
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return Returns the numLinks.
     * @exception
     * @since 1.0
     */
    public String getNumLinks() {
        return numLinks;
    }

    /**
     * @param numLinks
     *            The numLinks to set.
     * @exception
     * @since 1.0
     */
    public void setNumLinks(String numLinks) {
        this.numLinks = numLinks;
    }

    /**
     * @return Returns the datasets.
     * @exception
     * @since 1.0
     */
    public HashMap<String, String> getDatasets() {
        return datasets;
    }

    /**
     * @param datasets
     *            The datasets to set.
     * @exception
     * @since 1.0
     */
    public void setDatasets(HashMap<String, String> datasets) {
        this.datasets = datasets;
    }

    /**
     * @return Returns the linkingFile.
     * @exception
     * @since 1.0
     */
    public String getLinkingFile() {
        return linkingFile;
    }

    /**
     * @param linkingFile The linkingFile to set.
     * @exception
     * @since 1.0
     */
    public void setLinkingFile(String linkingFile) {
        this.linkingFile = linkingFile;
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
