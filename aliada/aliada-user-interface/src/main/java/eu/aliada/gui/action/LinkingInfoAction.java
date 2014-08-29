// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts2.ServletActionContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.shared.log.Log;
import eu.aliada.gui.parser.XmlParser;

/**
 * @author iosa
 * @since 1.0
 */
public class LinkingInfoAction extends ActionSupport {

    private String importFile;
    private String status;
    private String startDate;
    private String numLinks;
    private HashMap<String, String> datasets;

    private final Log logger = new Log(LinkingInfoAction.class);

    
    public String execute() {
        setImportFile((String) ServletActionContext.getRequest().getSession()
                .getAttribute("fileToLink"));
        try {
            getInfo();
        } catch (IOException e) {
            logger.error("Error getting linking info"+e);
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
        int fileToLinkId = (int) ServletActionContext.getRequest().getSession()
                .getAttribute("fileToLinkId");
        URL url = new URL("http://localhost:8890/links-discovery/jobs/"+fileToLinkId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/xml");
        if (conn.getResponseCode() != 202) {
            logger.debug("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        try {
            XmlParser parser = new XmlParser();
            Document doc = parser.parseXML(conn.getInputStream());
            NodeList readNode = doc.getElementsByTagName("numLinks");
            setNumLinks(readNode.item(0).getTextContent());
            readNode = doc.getElementsByTagName("startDate");
            String startDate = readNode.item(0).getTextContent();
            SimpleDateFormat dateFormatIn = new SimpleDateFormat(
                    "YYYY-MM-dd'T'HH:mm:ss");
            SimpleDateFormat dateFormatOut = new SimpleDateFormat(
                    "d MMMM yyyy',' HH:mm:ss");
            this.setStartDate(dateFormatOut.format(dateFormatIn
                    .parse(startDate)));          
            readNode = doc.getElementsByTagName("status");
            setStatus(readNode.item(0).getTextContent());  
            readNode = doc.getElementsByTagName("subjobs");
            if (readNode != null && readNode.getLength() > 0) {
                for (int i = 0; i < readNode.getLength(); i++) {
                    HashMap<String, String> datasets = new HashMap();
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
            } else {
                logger.debug("Not subjobs created");
            }            
          } catch (Exception e) {
            logger.debug("Failed reading xml"+e);
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

}
