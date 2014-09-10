// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.parser.XmlParser;
import eu.aliada.shared.log.Log;

/**
 * Gets the information of the RDFize process
 * @author iosa
 * @version $Revision: 1.1 $
 * @since 1.0
 */
public class CheckRDFizerAction extends ActionSupport {

    private String importFile;
    private String status;
    private String format;
    private String recordNum;
    private String processedNum;
    private String statementsNum;
    private String processingThroughput;
    private String triplesThroughput;

    private final Log logger = new Log(CheckRDFizerAction.class);

    public String execute() {
        try {
            getInfo();
        } catch (IOException ex) {
            logger.debug(MessageCatalog._00012_IO_EXCEPTION,ex);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * Obtain the information from the REST service of RDFizer
     * @throws IOException
     * @see
     * @since 1.0
     */
    public void getInfo() throws IOException {
        HttpSession session = ServletActionContext.getRequest().getSession();
        if(session.getAttribute("rdfizerJobId") != null){
            Integer rdfizerJobId = (int) session.getAttribute("rdfizerJobId");
            URL url = new URL("http://aliada:8080/aliada-rdfizer-1.0/jobs/"+rdfizerJobId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/xml");
            if (conn.getResponseCode() != 200) {
                logger.debug(MessageCatalog._00015_HTTP_ERROR_CODE
                        + conn.getResponseCode());
            }
            try {
                XmlParser parser = new XmlParser();
                Document doc = parser.parseXML(conn.getInputStream());
                NodeList readNode = doc.getElementsByTagName("format");
                setFormat(readNode.item(0).getTextContent());
                readNode = doc.getElementsByTagName("output-statements-count");
                setStatementsNum(readNode.item(0).getTextContent());
                readNode = doc.getElementsByTagName("processed-records-count");
                setProcessedNum(readNode.item(0).getTextContent());
                readNode = doc.getElementsByTagName("total-records-count");
                setRecordNum(readNode.item(0).getTextContent());
                readNode = doc.getElementsByTagName("records-throughput");
                setProcessingThroughput(readNode.item(0).getTextContent());
                readNode = doc.getElementsByTagName("triples-throughput");
                setTriplesThroughput(readNode.item(0).getTextContent());
                readNode = doc.getElementsByTagName("running");
                if(readNode.item(0).getTextContent().equals("true")){
                    setStatus(getText("checkRDF.running"));
                }
                readNode = doc.getElementsByTagName("completed");
                if(readNode.item(0).getTextContent().equals("true")){
                    setStatus(getText("checkRDF.completed"));
                    List<Integer> filesToLink = (List<Integer>)session.getAttribute("filesToLink");
                     if(filesToLink == null){
                         filesToLink = new ArrayList<Integer>();
                         filesToLink.add(rdfizerJobId);
                         session.setAttribute("filesToLink", filesToLink);
                     }
                     else{
                         if(!filesToLink.contains(rdfizerJobId)){
                             filesToLink.add(rdfizerJobId);                         
                         }
                         session.setAttribute("filesToLink", filesToLink);
                     }
                }            
          } catch (Exception e) {
              logger.debug(MessageCatalog._00016_ERROR_READING_XML,e);
              conn.disconnect();
          }
            conn.disconnect();
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
     * @return Returns the format.
     * @exception
     * @since 1.0
     */
    public String getFormat() {
        return format;
    }

    /**
     * @param format The format to set.
     * @exception
     * @since 1.0
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @return Returns the recordNum.
     * @exception
     * @since 1.0
     */
    public String getRecordNum() {
        return recordNum;
    }

    /**
     * @param recordNum The recordNum to set.
     * @exception
     * @since 1.0
     */
    public void setRecordNum(String recordNum) {
        this.recordNum = recordNum;
    }

    /**
     * @return Returns the processedNum.
     * @exception
     * @since 1.0
     */
    public String getProcessedNum() {
        return processedNum;
    }

    /**
     * @param processedNum The processedNum to set.
     * @exception
     * @since 1.0
     */
    public void setProcessedNum(String processedNum) {
        this.processedNum = processedNum;
    }

    /**
     * @return Returns the statementsNum.
     * @exception
     * @since 1.0
     */
    public String getStatementsNum() {
        return statementsNum;
    }

    /**
     * @param statementsNum The statementsNum to set.
     * @exception
     * @since 1.0
     */
    public void setStatementsNum(String statementsNum) {
        this.statementsNum = statementsNum;
    }

    /**
     * @return Returns the processingThroughput.
     * @exception
     * @since 1.0
     */
    public String getProcessingThroughput() {
        return processingThroughput;
    }

    /**
     * @param processingThroughput The processingThroughput to set.
     * @exception
     * @since 1.0
     */
    public void setProcessingThroughput(String processingThroughput) {
        this.processingThroughput = processingThroughput;
    }

    /**
     * @return Returns the triplesThroughput.
     * @exception
     * @since 1.0
     */
    public String getTriplesThroughput() {
        return triplesThroughput;
    }

    /**
     * @param triplesThroughput The triplesThroughput to set.
     * @exception
     * @since 1.0
     */
    public void setTriplesThroughput(String triplesThroughput) {
        this.triplesThroughput = triplesThroughput;
    }

//    private void readJson() throws IOException, ParseException, SQLException {
//        Connection connection = null;
//        FileReader reader = new FileReader(
//                "target/classes/filesJSON/rdfizerTest.json");
//        JSONParser jsonParser = new JSONParser();
//        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
//        long jobid = (long) jsonObject.get("id");
//        connection = new DBConnectionManager().getConnection();
//        Statement statement = connection.createStatement();
//        ResultSet rs = statement
//                .executeQuery("select datafile from aliada.rdfizer_job_instances where job_id="
//                        + jobid);
//        if (rs.next()) {
//            setImportFile(rs.getString("datafile"));
//        }
//        rs.close();
//        statement.close();
//        connection.close();
//        if ((boolean) jsonObject.get("running")) {
//            setStatus("Running");
//        }
//        if ((boolean) jsonObject.get("completed")) {
//            setStatus("Completed");
//        }
//        setFormat((String) jsonObject.get("format"));
//        setRecordNum((long) jsonObject.get("total-records-count"));
//        setProcessedNum((long) jsonObject.get("processed-records-count"));
//        setStatementsNum((long) jsonObject.get("output-statements-count"));
//        setProcessingThroughput((double) jsonObject.get("records-throughput"));
//        setTriplesThroughput((double) jsonObject.get("records-throughput"));
//    }

}
