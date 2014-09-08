// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.shared.log.Log;
import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.parser.XmlParser;

/**
 * This class provides information of the link discovery
 * @author iosa
 * @version $Revision: 1.1 $
 * @since 1.0
 */
public class LinkingInfoAction extends ActionSupport {

    private String importFile;
    private String status;
    private String startDate;
    private String endDate;
    private String numLinks;
    private HashMap<String, String> datasets;

    private final Log logger = new Log(LinkingInfoAction.class);

    
    public String execute() {
        setImportFile((String) ServletActionContext.getRequest().getSession()
                .getAttribute("fileToLink"));
        try {
            getInfo();
        } catch (IOException e) {
            logger.error(MessageCatalog._00012_IO_EXCEPTION);
            e.printStackTrace();
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
        int fileToLinkId = (int) session.getAttribute("fileToLinkId");
        URL url = new URL("http://localhost:8890/links-discovery/jobs/"+fileToLinkId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/xml");
        if (conn.getResponseCode() != 202) {
            logger.debug(MessageCatalog._00015_HTTP_ERROR_CODE
                    + conn.getResponseCode());
        }
        try {
            XmlParser parser = new XmlParser();
            Document doc = parser.parseXML(conn.getInputStream());
            NodeList readNode = doc.getElementsByTagName("numLinks");
            setNumLinks(readNode.item(0).getTextContent());
            readNode = doc.getElementsByTagName("startDate");
            String startDate = readNode.item(0).getTextContent();
            Locale locale = (Locale) session.getAttribute("WW_TRANS_I18N_LOCALE");
            if (locale == null) {
                locale = Locale.ROOT;
            }
            SimpleDateFormat dateFormatIn = new SimpleDateFormat(
                    "YYYY-MM-dd'T'HH:mm:ss");
            SimpleDateFormat dateFormatOut = new SimpleDateFormat(
                    "d MMMM yyyy',' HH:mm:ss",locale);
            this.setStartDate(dateFormatOut.format(dateFormatIn
                    .parse(startDate)));  
            readNode = doc.getElementsByTagName("startDate");
            String endDate = readNode.item(0).getTextContent();
            this.setEndDate(dateFormatOut.format(dateFormatIn
                    .parse(endDate))); 
            readNode = doc.getElementsByTagName("status");
            if(readNode.item(0).getTextContent().equals("running")){
                setStatus(getText("linkingInfo.running"));
            }
            else if(readNode.item(0).getTextContent().equals("completed")){
                setStatus(getText("linkingInfo.completed"));
            }
            readNode = doc.getElementsByTagName("subjobs");
            if (readNode != null && readNode.getLength() > 0) {
                for (int i = 0; i < readNode.getLength(); i++) {
                    HashMap<String, String> datasets = new HashMap<String,String>();
                    Node node = readNode.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element e = (Element) node;
                        NodeList nodeList = e.getElementsByTagName("name");
                        String name = nodeList.item(0).getTextContent();
                        nodeList = e.getElementsByTagName("numLinks");
                        String numLinksDataset = nodeList.item(0).getTextContent();
                        datasets.put(name, numLinksDataset);
                    }
                }
                this.setDatasets(datasets);
            }           
          } catch (Exception e) {
            logger.debug(MessageCatalog._00016_ERROR_READING_XML);
            e.printStackTrace();
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
