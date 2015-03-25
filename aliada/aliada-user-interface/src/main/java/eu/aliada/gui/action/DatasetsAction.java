// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
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
 * This class is the datasets administration.
 * @author xabier
 * @version $Revision: 1.1 $, $Date: 2015/02/19 15:20:54 $
 * @since 1.0
 */
public class DatasetsAction  extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HashMap<Integer, String> datasets;
	
	private String selectedDataset;
	private int datasetIdForm;
	private int organisationIdForm;
	private String datasetDescForm;
	private String domainNameForm;
	private String uriIdPartForm;
	private String uriDocPartForm;
	private String uriDefPartForm;
	private String uriConceptPartForm;
	private String uriSetPartForm;
	private String listeningHostForm;
	private String virtualHostForm;
	private String sparqlEndpointURIForm;
	private String sparqlEndpointLoginForm;
	private String sparqlEndpointPasswordForm;
	private String publicSparqlEndpointURIForm;
	private String datasetAuthorForm;
	private String ckanDatasetNameForm;
	private String datasetLongDescForm;
	private String datasetSourceURLForm;
	private String licenseCkanIdForm;
	private String licenseURLForm;
	private String isqlCommandsFileDatasetForm;
	
	private String title;
	private String message1;
	private String message2;
	private String message3;
	private String message4;
	private String message5;
	private String message6;
	private String message7;
	private String message8;
	
    private boolean showAddDatasetForm;
    private boolean showEditDatasetForm;
    private boolean showTheDataset;
    private boolean areDatasets;
	
	private final Log logger = new Log(DatasetsAction.class);

    /**
     * The method to show the datasets list.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String showDatasets() {
    	
    	ServletActionContext.getRequest().getSession().setAttribute("ConfOpc", 4);
    	
    	title = getText("dialog.title");
    	message1 = getText("dataset.message1");
    	message2 = getText("dataset.message2");
    	message3 = getText("dataset.message3");
    	message4 = getText("dataset.message4");
    	message5 = getText("dataset.message5");
    	message6 = getText("dataset.message6");
    	message7 = getText("dataset.message7");
    	message8 = getText("dataset.message8");
    	
    	String usernameLogged = (String) ServletActionContext.getRequest().getSession().getAttribute("logedUser");
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select * from aliada.dataset d "
                    		+ "INNER JOIN aliada.organisation o ON d.organisationId = o.organisationId "
                    		+ "INNER JOIN aliada.user u ON o.organisationId = u.organisationId "
                    		+ "where u.user_name = '" + usernameLogged + "'");
            datasets = new HashMap<Integer, String>();
            while (rs.next()) {
                datasets.put(rs.getInt("datasetId"),
                        rs.getString("dataset_desc"));
            }
            if (datasets.isEmpty()) {
                setAreDatasets(false);
            } else {
                setAreDatasets(true);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        setShowAddDatasetForm(false);
        setShowEditDatasetForm(false);
        setShowTheDataset(false);
        return SUCCESS;
    }
    /**
     * The method to see the dataset selected.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String getTheDataset() {
    	HttpSession session = ServletActionContext.getRequest().getSession();
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select * from aliada.dataset where dataset_desc='" + this.selectedDataset + "'");
            if (rs.next()) {
                this.datasetIdForm = rs.getInt("datasetId");
                this.organisationIdForm = rs.getInt("organisationId");
                this.datasetDescForm = rs.getString("dataset_desc");
                this.domainNameForm = rs.getString("domain_name");
                this.uriIdPartForm = rs.getString("uri_id_part");
                this.uriDocPartForm = rs.getString("uri_doc_part");
                this.uriDefPartForm = rs.getString("uri_def_part");
                this.uriConceptPartForm = rs.getString("uri_concept_part");
                this.uriSetPartForm = rs.getString("uri_set_part");
                this.listeningHostForm = rs.getString("listening_host");
                this.virtualHostForm = rs.getString("virtual_host");
                this.sparqlEndpointURIForm = rs.getString("sparql_endpoint_uri");
                this.sparqlEndpointLoginForm = rs.getString("sparql_endpoint_login");
                this.sparqlEndpointPasswordForm = rs.getString("sparql_endpoint_password");
                this.publicSparqlEndpointURIForm = rs.getString("public_sparql_endpoint_uri");
                this.datasetAuthorForm = rs.getString("dataset_author");
                this.ckanDatasetNameForm = rs.getString("ckan_dataset_name");
                this.datasetLongDescForm = rs.getString("dataset_long_desc");
                this.datasetSourceURLForm = rs.getString("dataset_source_url");
                this.licenseCkanIdForm = rs.getString("license_ckan_id");
                this.licenseURLForm = rs.getString("license_url");             
                this.isqlCommandsFileDatasetForm = rs.getString("isql_commands_file_dataset");
                statement.close();
                rs.close();
                connection.close();
                session.setAttribute("DatasetId", this.datasetIdForm);
                showDatasets();
                setShowTheDataset(true);
                return SUCCESS;
            } else {
                addActionError(getText("dataset.not.selected"));
                statement.close();
                rs.close();
                connection.close();
                showDatasets();
                return ERROR;
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            showDatasets();
            return ERROR;
        }
    }
    /**
     * The method to show the dataset form with the dataset selected.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String showEditDataset() {
    	HttpSession session = ServletActionContext.getRequest().getSession();
        if (this.selectedDataset != null) {
        	Connection connection = null;
	        try {
	            connection = new DBConnectionManager().getConnection();
	            Statement statement = connection.createStatement();
	            ResultSet rs = statement
	                    .executeQuery("select * from aliada.dataset where dataset_desc='" + this.selectedDataset + "'");
	            if (rs.next()) {
	            	int datasetId = rs.getInt("datasetId");
	            	this.datasetDescForm = rs.getString("dataset_desc");
	                this.domainNameForm = rs.getString("domain_name");
	                this.uriIdPartForm = rs.getString("uri_id_part");
	                this.uriDocPartForm = rs.getString("uri_doc_part");
	                this.uriDefPartForm = rs.getString("uri_def_part");
	                this.uriConceptPartForm = rs.getString("uri_concept_part");
	                this.uriSetPartForm = rs.getString("uri_set_part");
	                this.listeningHostForm = rs.getString("listening_host");
	                this.virtualHostForm = rs.getString("virtual_host");
	                this.sparqlEndpointLoginForm = rs.getString("sparql_endpoint_login");
	                this.sparqlEndpointPasswordForm = rs.getString("sparql_endpoint_password");
	                this.ckanDatasetNameForm = rs.getString("ckan_dataset_name");
	                this.datasetLongDescForm = rs.getString("dataset_long_desc");
	                this.datasetSourceURLForm = rs.getString("dataset_source_url");
	                this.licenseCkanIdForm = rs.getString("license_ckan_id");
	                this.licenseURLForm = rs.getString("license_url");
	                this.isqlCommandsFileDatasetForm = rs.getString("isql_commands_file_dataset");
	                statement.close();
	                rs.close();
	                connection.close();
	                session.setAttribute("DatasetId", datasetId);
	                showDatasets();
	                this.showEditDatasetForm = true;
	                return SUCCESS;
	            } else {
	                addActionError(getText("dataset.not.selected"));
	                statement.close();
	                rs.close();
	                connection.close();
	                showDatasets();
	                return ERROR;
	            }
	        } catch (SQLException e) {
	            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
	            showDatasets();
	            return ERROR;
	        }
        } else if (getActionErrors().size() > 0) {
        	int DatId = (int) session.getAttribute("DatasetId");
        	Connection connection = null;
        	try {
        		connection = new DBConnectionManager().getConnection();
	            Statement statement = connection.createStatement();
	            ResultSet rs = statement
	                    .executeQuery("select * from aliada.dataset where datasetId='" + DatId + "'");
	            if (rs.next()) {
	                this.datasetDescForm = rs.getString("dataset_desc");
	                this.domainNameForm = rs.getString("domain_name");
	                this.uriIdPartForm = rs.getString("uri_id_part");
	                this.uriDocPartForm = rs.getString("uri_doc_part");
	                this.uriDefPartForm = rs.getString("uri_def_part");
	                this.uriConceptPartForm = rs.getString("uri_concept_part");
	                this.uriSetPartForm = rs.getString("uri_set_part");
	                this.listeningHostForm = rs.getString("listening_host");
	                this.virtualHostForm = rs.getString("virtual_host");
	                this.sparqlEndpointLoginForm = rs.getString("sparql_endpoint_login");
	                this.sparqlEndpointPasswordForm = rs.getString("sparql_endpoint_password");
	                this.ckanDatasetNameForm = rs.getString("ckan_dataset_name");
	                this.datasetLongDescForm = rs.getString("dataset_long_desc");
	                this.datasetSourceURLForm = rs.getString("dataset_source_url");
	                this.licenseCkanIdForm = rs.getString("license_ckan_id");
	                this.licenseURLForm = rs.getString("license_url");
	                this.isqlCommandsFileDatasetForm = rs.getString("isql_commands_file_dataset");
	                statement.close();
	                rs.close();
	                connection.close();
	            }
        	 } catch (SQLException e) {
 	            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
 	            showDatasets();
 	            return ERROR;
 	        }
            showDatasets();
            setShowEditDatasetForm(true);
        	return SUCCESS;
        } else {
        	clearErrorsAndMessages();
        	addActionError(getText("dataset.not.selected"));
        	showDatasets();
            return ERROR;
        }
    }
    /**
     * The method to edit the dataset selected.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String editDataset() {
    	HttpSession session = ServletActionContext.getRequest().getSession();
    	int datasetId = (int) session.getAttribute("DatasetId");
    	
    	clearErrorsAndMessages();
        
        if (this.datasetDescForm.trim().isEmpty()) {
       	 	addActionError(getText("desc.not.null"));
            showDatasets();
            return ERROR;            	 
        }
        
        if (this.uriIdPartForm.indexOf('/') != -1) {
       	 	addActionError(getText("id.not.with"));
            showDatasets();
            return ERROR; 
        }
        if (this.uriIdPartForm.trim().isEmpty()) {
       	 	addActionError(getText("id.not.null"));
            showDatasets();
            return ERROR; 
        }
        if (this.uriDocPartForm.indexOf('/') != -1) {
       	 	addActionError(getText("doc.not.with"));
            showDatasets();
            return ERROR;  
        }
        if (this.uriDefPartForm.indexOf('/') != -1) {
       	 	addActionError(getText("def.not.with"));
            showDatasets();
            return ERROR;  
        }
        if (this.uriDefPartForm.trim().isEmpty()) {
       	 	addActionError(getText("def.not.null"));
            showDatasets();
            return ERROR; 
        }
        if (this.uriSetPartForm.indexOf('/') != -1) {
       	 	addActionError(getText("set.not.with"));
            showDatasets();
            return ERROR;  
        }
        if (this.uriSetPartForm.trim().isEmpty()) {
       	 	addActionError(getText("set.not.null"));
            showDatasets();
            return ERROR; 
        }
        if (this.uriDocPartForm.trim().isEmpty() && this.uriConceptPartForm.trim().isEmpty()) {
       	 	addActionError(getText("doc.and.concept.not.null"));
            showDatasets();
            return ERROR;  
        }
        if (this.ckanDatasetNameForm.indexOf(' ') != -1) {
       	 	if (haveCapitalLetters(this.ckanDatasetNameForm)) {
       	 		addActionError(getText("ckan.not.both"));
       	 	} else {
       	 		addActionError(getText("ckan.not.blank"));
       	 	}
            showDatasets();
            return ERROR;
        } else if (haveCapitalLetters(this.ckanDatasetNameForm)) {
       	 	addActionError(getText("ckan.not.cap"));
            showDatasets();
            return ERROR;
        }
        
        try {
        	Connection connection = null;
            connection = new DBConnectionManager().getConnection();
            String dd = "", dn = "", uip = "", udp = "", udep = "",
            		ucp = "", usp = "", lh = "", vh = "", sel = "", sep = "",
            		cdn = "", dld = "", dsu = "", lci = "", lu = "", icfd = "";
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select * from aliada.dataset where datasetId='" + datasetId + "'");
            if (rs.next()) {
            	dd = rs.getString("dataset_desc");
            	dn = rs.getString("domain_name");
            	uip = rs.getString("uri_id_part");
            	udp = rs.getString("uri_doc_part");
            	udep = rs.getString("uri_def_part");
            	ucp = rs.getString("uri_concept_part");
            	usp = rs.getString("uri_set_part");
            	lh = rs.getString("listening_host");
            	vh = rs.getString("virtual_host");
            	sel = rs.getString("sparql_endpoint_login");
            	sep = rs.getString("sparql_endpoint_password");
            	cdn = rs.getString("ckan_dataset_name");
            	dld = rs.getString("dataset_long_desc");
            	dsu = rs.getString("dataset_source_url");
            	lci = rs.getString("license_ckan_id");
            	lu = rs.getString("license_url");
            	icfd = rs.getString("isql_commands_file_dataset");
            }
            
            if (this.datasetDescForm.equalsIgnoreCase(dd) && this.domainNameForm.equalsIgnoreCase(dn) 
            		&& this.uriIdPartForm.equalsIgnoreCase(uip) && this.uriDocPartForm.equalsIgnoreCase(udp)
            		&& this.uriDefPartForm.equalsIgnoreCase(udep) && this.uriConceptPartForm.equalsIgnoreCase(ucp)
            		&& this.uriSetPartForm.equalsIgnoreCase(usp) && this.listeningHostForm.equalsIgnoreCase(lh)
            		&& this.virtualHostForm.equalsIgnoreCase(vh) && this.sparqlEndpointLoginForm.equalsIgnoreCase(sel)
            		&& this.sparqlEndpointPasswordForm.equalsIgnoreCase(sep) && this.ckanDatasetNameForm.equalsIgnoreCase(cdn)
            		&& this.datasetLongDescForm.equalsIgnoreCase(dld) && this.datasetSourceURLForm.equalsIgnoreCase(dsu)
            		&& this.licenseCkanIdForm.equalsIgnoreCase(lci) && this.licenseURLForm.equalsIgnoreCase(lu)
            		&& this.isqlCommandsFileDatasetForm.equalsIgnoreCase(icfd)) {
            	rs.close();
            	statement.close();
            	connection.close();
           	    addActionError(getText("data.not.change"));
                showDatasets();
                return ERROR; 
            }
            
            if (!this.virtualHostForm.equalsIgnoreCase(vh)) {
            	String sparqlEndpUri = "http://" + this.virtualHostForm + "/sparql-auth";
            	String publicSparqlEndpUri = "http://" + this.virtualHostForm + "/sparql";
            	statement = connection.createStatement();
            	statement.executeUpdate("UPDATE aliada.dataset set sparql_endpoint_uri='"
                    + sparqlEndpUri + "', public_sparql_endpoint_uri='"
                    + publicSparqlEndpUri + "' where datasetId='" + datasetId + "'");
            }
            
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE aliada.dataset set dataset_desc='"
            		+ this.datasetDescForm + "',domain_name='"
                    + this.domainNameForm + "',uri_id_part='"
                    + this.uriIdPartForm + "',uri_doc_part='"
                    + this.uriDocPartForm + "',uri_def_part='"
                    + this.uriDefPartForm + "',uri_concept_part='"
                    + this.uriConceptPartForm + "',uri_set_part='"
                    + this.uriSetPartForm + "',listening_host='"
                    + this.listeningHostForm + "',virtual_host='"
                    + this.virtualHostForm + "',sparql_endpoint_login='"
                    + this.sparqlEndpointLoginForm + "',sparql_endpoint_password='"
                    + this.sparqlEndpointPasswordForm + "',ckan_dataset_name='"
                    + this.ckanDatasetNameForm + "',dataset_long_desc='"
                    + this.datasetLongDescForm + "',dataset_source_url='"
                    + this.datasetSourceURLForm + "',license_ckan_id='"
                    + this.licenseCkanIdForm + "',license_url='"
                    + this.licenseURLForm + "',isql_commands_file_dataset='"
                    + this.isqlCommandsFileDatasetForm + "' where datasetId='"
                    + datasetId + "'");
            statement.close();
            connection.close();
            addActionMessage(getText("dataset.edit.ok"));
            showDatasets();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        return SUCCESS;
    }
    /**
     * The method to delete the dataset selected.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String deleteDataset() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            int correct = statement
                    .executeUpdate("DELETE FROM aliada.dataset WHERE dataset_desc='" + getSelectedDataset() + "'");
            statement.close();
            connection.close();
            if (correct == 0) {
                addActionError(getText("dataset.not.selected"));
            } else {
                addActionMessage(getText("dataset.delete.ok"));
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            showDatasets();
            return ERROR;
        }
        showDatasets();
        return SUCCESS;
    }
    /**
     * The method to add a new dataset.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String addDataset() {
    	
    	 String datasetAuthor = "", isqlCommandsFilename = "", aliadaToolHost = "";
    	 String sparqlEndpoint = "";
    	 String publicSparqlEndpoint = "";
    	 if (!this.virtualHostForm.isEmpty()) {
    	 sparqlEndpoint = "http://" + this.virtualHostForm + "/sparql-auth";
    	 publicSparqlEndpoint = "http://" + this.virtualHostForm + "/sparql";
    	 } else {
    		 this.sparqlEndpointLoginForm = "";
    		 this.sparqlEndpointPasswordForm = "";
    	 }
    	    	
    	 Connection connection = null;
         String usernameLogged = (String) ServletActionContext.getRequest().getSession().getAttribute("logedUser");
         int organisationId = 0;
         String icp = "", si = "", sl = "", sp = "";
         int ssp = 0;
         try {
         	connection = new DBConnectionManager().getConnection();
         	
         	Statement statement = connection.createStatement();
            ResultSet rs = statement
                     .executeQuery("select u.organisationId, isql_command_path, store_ip, store_sql_port, "
                     		+ "sql_login, sql_password, dataset_author, isql_commands_file_dataset_creation, "
                     		+ "aliada_tool_hostname from aliada.organisation o inner join aliada.user u ON o.organisationId = u.organisationId "
                     		+ "where user_name='" + usernameLogged + "'");
             if (rs.next()) {
                 organisationId = rs.getInt("organisationId");
                 icp = rs.getString("isql_command_path");
                 si = rs.getString("store_ip");
                 ssp = rs.getInt("store_sql_port");
                 sl = rs.getString("sql_login");
                 sp = rs.getString("sql_password");
                 datasetAuthor = rs.getString("dataset_author");
                 isqlCommandsFilename = rs.getString("isql_commands_file_dataset_creation");
                 aliadaToolHost = rs.getString("aliada_tool_hostname");
             }
             if (this.datasetDescForm.trim().isEmpty()) {
            	 rs.close();
            	 statement.close();
            	 connection.close();
            	 addActionError(getText("desc.not.null"));
                 showDatasets();
                 return ERROR;            	 
             }
             if (this.uriIdPartForm.indexOf('/') != -1) {
            	 rs.close();
            	 statement.close();
            	 connection.close();
            	 addActionError(getText("id.not.with"));
                 showDatasets();
                 return ERROR; 
             }
             if (this.uriIdPartForm.trim().isEmpty()) {
            	 rs.close();
            	 statement.close();
            	 connection.close();
            	 addActionError(getText("id.not.null"));
                 showDatasets();
                 return ERROR; 
             }
             if (this.uriDocPartForm.indexOf('/') != -1) {
            	 rs.close();
            	 statement.close();
            	 connection.close();
            	 addActionError(getText("doc.not.with"));
                 showDatasets();
                 return ERROR;  
             }
             if (this.uriDefPartForm.indexOf('/') != -1) {
            	 rs.close();
            	 statement.close();
            	 connection.close();
            	 addActionError(getText("def.not.with"));
                 showDatasets();
                 return ERROR;  
             }
             if (this.uriDefPartForm.trim().isEmpty()) {
            	 rs.close();
            	 statement.close();
            	 connection.close();
            	 addActionError(getText("def.not.null"));
                 showDatasets();
                 return ERROR; 
             }
             if (this.uriSetPartForm.indexOf('/') != -1) {
            	 rs.close();
            	 statement.close();
            	 connection.close();
            	 addActionError(getText("set.not.with"));
                 showDatasets();
                 return ERROR;  
             }
             if (this.uriSetPartForm.trim().isEmpty()) {
            	 rs.close();
            	 statement.close();
            	 connection.close();
            	 addActionError(getText("set.not.null"));
                 showDatasets();
                 return ERROR; 
             }
             if (this.uriDocPartForm.trim().isEmpty() && this.uriConceptPartForm.trim().isEmpty()) {
            	 rs.close();
            	 statement.close();
            	 connection.close();
            	 addActionError(getText("doc.and.concept.not.null"));
                 showDatasets();
                 return ERROR;  
             }
             if (this.ckanDatasetNameForm.indexOf(' ') != -1) {
            	 rs.close();
            	 statement.close();
            	 connection.close();
            	 if (haveCapitalLetters(this.ckanDatasetNameForm)) {
            		 addActionError(getText("ckan.not.both"));
            	 } else {
            		 addActionError(getText("ckan.not.blank"));
            	 }
                 showDatasets();
                 return ERROR;
             } else if (haveCapitalLetters(this.ckanDatasetNameForm)) {
            	 rs.close();
            	 statement.close();
            	 connection.close();
            	 addActionError(getText("ckan.not.cap"));
                 showDatasets();
                 return ERROR;
             }
             
             final String ISQL_COMMAND_FORMAT = "%s %s:%d %s %s %s -u lhost='%s' vhost='%s' aliada_tool_host='%s'";
             final String isqlCommand = String.format(ISQL_COMMAND_FORMAT, icp, si, ssp, sl, sp , 
            		 isqlCommandsFilename, this.listeningHostForm, this.virtualHostForm, aliadaToolHost);
             logger.debug(isqlCommand);
             try {
            	 logger.debug(MessageCatalog._00070_EXECUTING_ISQL);
            	 final Process commandProcess = Runtime.getRuntime().exec(isqlCommand);
            	 final BufferedReader stdInput = new BufferedReader(new InputStreamReader(commandProcess.getInputStream()));
            	 String comOutput = "";
            	 while ((comOutput = stdInput.readLine()) != null) {
            		 logger.debug(comOutput);
            	 }
             } catch (IOException exception) {
            	 logger.error(MessageCatalog._00071_EXTERNAL_PROCESS_START_FAILURE, exception, isqlCommand);
             }
             
             statement = connection.createStatement();
             statement.executeUpdate("INSERT INTO aliada.dataset (organisationId, dataset_desc, domain_name,"
             		+ "uri_id_part, uri_doc_part, uri_def_part, uri_concept_part, uri_set_part,"
             		+ "listening_host, virtual_host, sparql_endpoint_uri,"
             		+ "sparql_endpoint_login, sparql_endpoint_password,"
             		+ "public_sparql_endpoint_uri, dataset_author,"
             		+ "ckan_dataset_name, dataset_long_desc, "
             		+ "dataset_source_url, license_ckan_id,"
             		+ "license_url,"
             		+ "isql_commands_file_dataset) VALUES (" + organisationId + ",'"
                     + this.datasetDescForm + "', '" + this.domainNameForm + "', '"
                     + this.uriIdPartForm + "', '" + this.uriDocPartForm + "', '"
                     + this.uriDefPartForm + "', '" + this.uriConceptPartForm + "', '" + this.uriSetPartForm + "', '"
                     + this.listeningHostForm + "', '" + this.virtualHostForm + "', '"
                     + sparqlEndpoint + "', '" + this.sparqlEndpointLoginForm + "', '" 
                     + this.sparqlEndpointPasswordForm + "', '" + publicSparqlEndpoint + "', '"
                     + datasetAuthor + "', '" + this.ckanDatasetNameForm + "', '" + this.datasetLongDescForm + "', '"
                     + this.datasetSourceURLForm + "', '" + this.licenseCkanIdForm + "', '" 
                     + this.licenseURLForm + "', '" 
                     + this.isqlCommandsFileDatasetForm + "')");
             rs.close();
             statement.close();
             connection.close();
             addActionMessage(getText("dataset.save.ok"));
             showDatasets();
             return SUCCESS;
         } catch (SQLException e) {
             logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
             return ERROR;
         }
    }
    /**
     * The method to know if a word have capital letters.
     * 
     * @return boolean
     * @param word
     * 			The word to know it.
     * @see
     * @since 1.0
     */
    public boolean haveCapitalLetters(final String word) {
    	String capitalLetters = "ABCDEFGHYJKLMNÑOPQRSTUVWXYZ";
    	   for (int i = 0; i < word.length(); i++) {
    	      if (capitalLetters.indexOf(word.charAt(i), 0) != -1) {
    	         return true;
    	      }
    	   }
    	   return false;
    	} 
    /**
     * The method to show the dataset form empty.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String showAddDataset() {
    	
    	// Default values
    	String id = "id";
    	String def = "def";
    	String set = "set";
    	String endLogin = "aliada_dev";
    	String endPass = "aliada_dev";
    	String ckanId = "cc-zero";
    	String licUrl = "http://creativecommons.org/publicdomain/zero/1.0/";
    	String isqlCommFileDataset = "";
    	Connection connection = new DBConnectionManager().getConnection();
    	String usernameLogged = (String) ServletActionContext.getRequest().getSession().getAttribute("logedUser");
     	Statement statement;
		try {
			statement = connection.createStatement();
			 ResultSet rs = statement
	                 .executeQuery("select isql_commands_file_dataset_default from aliada.organisation o "
	                 		+ "INNER JOIN aliada.user u ON u.organisationId=o.organisationId "
	                 		+ "where u.user_name='" + usernameLogged + "'");
	         if (rs.next()) {
	             isqlCommFileDataset = rs.getString("isql_commands_file_dataset_default");
	         }
	         rs.close();
	         statement.close();
	         connection.close();
		} catch (SQLException e) {
			logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
		}
		
        
    	setUriIdPartForm(id);
    	setUriDefPartForm(def);
    	setUriSetPartForm(set);
    	setSparqlEndpointLoginForm(endLogin);
    	setSparqlEndpointPasswordForm(endPass);
    	setLicenseCkanIdForm(ckanId);
    	setLicenseURLForm(licUrl);
    	setIsqlCommandsFileDatasetForm(isqlCommFileDataset);
    	
        showDatasets();
        setShowAddDatasetForm(true);
        return SUCCESS;
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
     * @return Returns the selectedDataset.
     * @exception
     * @since 1.0
     */
	public String getSelectedDataset() {
		return selectedDataset;
	}
	/**
     * @param selectedDataset
     *            The selectedDataset to set.
     * @exception
     * @since 1.0
     */
	public void setSelectedDataset(final String selectedDataset) {
		this.selectedDataset = selectedDataset;
	}
	/**
     * @return Returns the datasetIdForm.
     * @exception
     * @since 1.0
     */
	public int getDatasetIdForm() {
		return datasetIdForm;
	}
	/**
     * @param datasetIdForm
     *            The datasetIdForm to set.
     * @exception
     * @since 1.0
     */
	public void setDatasetIdForm(final int datasetIdForm) {
		this.datasetIdForm = datasetIdForm;
	}
	/**
     * @return Returns the organisationIdForm.
     * @exception
     * @since 1.0
     */
	public int getOrganisationIdForm() {
		return organisationIdForm;
	}
	/**
     * @param organisationIdForm
     *            The organisationIdForm to set.
     * @exception
     * @since 1.0
     */
	public void setOrganisationIdForm(final int organisationIdForm) {
		this.organisationIdForm = organisationIdForm;
	}
	/**
     * @return Returns the datasetDescForm.
     * @exception
     * @since 1.0
     */
	public String getDatasetDescForm() {
		return datasetDescForm;
	}
	/**
     * @param datasetDescForm
     *            The datasetDescForm to set.
     * @exception
     * @since 1.0
     */
	public void setDatasetDescForm(final String datasetDescForm) {
		this.datasetDescForm = datasetDescForm;
	}
	/**
     * @return Returns the domainNameForm.
     * @exception
     * @since 1.0
     */
	public String getDomainNameForm() {
		return domainNameForm;
	}
	/**
     * @param domainNameForm
     *            The domainNameForm to set.
     * @exception
     * @since 1.0
     */
	public void setDomainNameForm(final String domainNameForm) {
		this.domainNameForm = domainNameForm;
	}
	/**
     * @return Returns the uriIdPartForm.
     * @exception
     * @since 1.0
     */
	public String getUriIdPartForm() {
		return uriIdPartForm;
	}
	/**
     * @param uriIdPartForm
     *            The uriIdPartForm to set.
     * @exception
     * @since 1.0
     */
	public void setUriIdPartForm(final String uriIdPartForm) {
		this.uriIdPartForm = uriIdPartForm;
	}
	/**
     * @return Returns the uriDocPartForm.
     * @exception
     * @since 1.0
     */
	public String getUriDocPartForm() {
		return uriDocPartForm;
	}
	/**
     * @param uriDocPartForm
     *            The uriDocPartForm to set.
     * @exception
     * @since 1.0
     */
	public void setUriDocPartForm(final String uriDocPartForm) {
		this.uriDocPartForm = uriDocPartForm;
	}
	/**
     * @return Returns the uriDefPartForm.
     * @exception
     * @since 1.0
     */
	public String getUriDefPartForm() {
		return uriDefPartForm;
	}
	/**
     * @param uriDefPartForm
     *            The uriDefPartForm to set.
     * @exception
     * @since 1.0
     */
	public void setUriDefPartForm(final String uriDefPartForm) {
		this.uriDefPartForm = uriDefPartForm;
	}
	/**
     * @return Returns the uriConceptPartForm.
     * @exception
     * @since 1.0
     */
	public String getUriConceptPartForm() {
		return uriConceptPartForm;
	}
	/**
     * @param uriConceptPartForm
     *            The uriConceptPartForm to set.
     * @exception
     * @since 1.0
     */
	public void setUriConceptPartForm(final String uriConceptPartForm) {
		this.uriConceptPartForm = uriConceptPartForm;
	}
	/**
     * @return Returns the uriSetPartForm.
     * @exception
     * @since 1.0
     */
	public String getUriSetPartForm() {
		return uriSetPartForm;
	}
	/**
     * @param uriSetPartForm
     *            The uriSetPartForm to set.
     * @exception
     * @since 1.0
     */
	public void setUriSetPartForm(final String uriSetPartForm) {
		this.uriSetPartForm = uriSetPartForm;
	}
	/**
     * @return Returns the listeningHostForm.
     * @exception
     * @since 1.0
     */
	public String getListeningHostForm() {
		return listeningHostForm;
	}
	/**
     * @param listeningHostForm
     *            The listeningHostForm to set.
     * @exception
     * @since 1.0
     */
	public void setListeningHostForm(final String listeningHostForm) {
		this.listeningHostForm = listeningHostForm;
	}
	/**
     * @return Returns the virtualHostForm.
     * @exception
     * @since 1.0
     */
	public String getVirtualHostForm() {
		return virtualHostForm;
	}
	/**
     * @param virtualHostForm
     *            The virtualHostForm to set.
     * @exception
     * @since 1.0
     */
	public void setVirtualHostForm(final String virtualHostForm) {
		this.virtualHostForm = virtualHostForm;
	}
	/**
     * @return Returns the sparqlEndpointURIForm.
     * @exception
     * @since 1.0
     */
	public String getSparqlEndpointURIForm() {
		return sparqlEndpointURIForm;
	}
	/**
     * @param sparqlEndpointURIForm
     *            The sparqlEndpointURIForm to set.
     * @exception
     * @since 1.0
     */
	public void setSparqlEndpointURIForm(final String sparqlEndpointURIForm) {
		this.sparqlEndpointURIForm = sparqlEndpointURIForm;
	}
	/**
     * @return Returns the sparqlEndpointLoginForm.
     * @exception
     * @since 1.0
     */
	public String getSparqlEndpointLoginForm() {
		return sparqlEndpointLoginForm;
	}
	/**
     * @param sparqlEndpointLoginForm
     *            The sparqlEndpointLoginForm to set.
     * @exception
     * @since 1.0
     */
	public void setSparqlEndpointLoginForm(final String sparqlEndpointLoginForm) {
		this.sparqlEndpointLoginForm = sparqlEndpointLoginForm;
	}
	/**
     * @return Returns the sparqlEndpointPasswordForm.
     * @exception
     * @since 1.0
     */
	public String getSparqlEndpointPasswordForm() {
		return sparqlEndpointPasswordForm;
	}
	/**
     * @param sparqlEndpointPasswordForm
     *            The sparqlEndpointPasswordForm to set.
     * @exception
     * @since 1.0
     */
	public void setSparqlEndpointPasswordForm(final String sparqlEndpointPasswordForm) {
		this.sparqlEndpointPasswordForm = sparqlEndpointPasswordForm;
	}
	/**
     * @return Returns the publicSparqlEndpointURIForm.
     * @exception
     * @since 1.0
     */
	public String getPublicSparqlEndpointURIForm() {
		return publicSparqlEndpointURIForm;
	}
	/**
     * @param publicSparqlEndpointURIForm
     *            The publicSparqlEndpointURIForm to set.
     * @exception
     * @since 1.0
     */
	public void setPublicSparqlEndpointURIForm(final String publicSparqlEndpointURIForm) {
		this.publicSparqlEndpointURIForm = publicSparqlEndpointURIForm;
	}
	/**
     * @return Returns the datasetAuthorForm.
     * @exception
     * @since 1.0
     */
	public String getDatasetAuthorForm() {
		return datasetAuthorForm;
	}
	/**
     * @param datasetAuthorForm
     *            The datasetAuthorForm to set.
     * @exception
     * @since 1.0
     */
	public void setDatasetAuthorForm(final String datasetAuthorForm) {
		this.datasetAuthorForm = datasetAuthorForm;
	}
	/**
     * @return Returns the ckanDatasetNameForm.
     * @exception
     * @since 1.0
     */
	public String getCkanDatasetNameForm() {
		return ckanDatasetNameForm;
	}
	/**
     * @param ckanDatasetNameForm
     *            The ckanDatasetNameForm to set.
     * @exception
     * @since 1.0
     */
	public void setCkanDatasetNameForm(final String ckanDatasetNameForm) {
		this.ckanDatasetNameForm = ckanDatasetNameForm;
	}
	/**
     * @return Returns the datasetLongDescForm.
     * @exception
     * @since 1.0
     */
	public String getDatasetLongDescForm() {
		return datasetLongDescForm;
	}
	/**
     * @param datasetLongDescForm
     *            The datasetLongDescForm to set.
     * @exception
     * @since 1.0
     */
	public void setDatasetLongDescForm(final String datasetLongDescForm) {
		this.datasetLongDescForm = datasetLongDescForm;
	}
	/**
     * @return Returns the datasetSourceURLForm.
     * @exception
     * @since 1.0
     */
	public String getDatasetSourceURLForm() {
		return datasetSourceURLForm;
	}
	/**
     * @param datasetSourceURLForm
     *            The datasetSourceURLForm to set.
     * @exception
     * @since 1.0
     */
	public void setDatasetSourceURLForm(final String datasetSourceURLForm) {
		this.datasetSourceURLForm = datasetSourceURLForm;
	}
	/**
     * @return Returns the licenseCkanIdForm.
     * @exception
     * @since 1.0
     */
	public String getLicenseCkanIdForm() {
		return licenseCkanIdForm;
	}
	/**
     * @param licenseCkanIdForm
     *            The licenseCkanIdForm to set.
     * @exception
     * @since 1.0
     */
	public void setLicenseCkanIdForm(final String licenseCkanIdForm) {
		this.licenseCkanIdForm = licenseCkanIdForm;
	}
	/**
     * @return Returns the licenseURLForm.
     * @exception
     * @since 1.0
     */
	public String getLicenseURLForm() {
		return licenseURLForm;
	}
	/**
     * @param licenseURLForm
     *            The licenseURLForm to set.
     * @exception
     * @since 1.0
     */
	public void setLicenseURLForm(final String licenseURLForm) {
		this.licenseURLForm = licenseURLForm;
	}
	/**
     * @return Returns the isqlCommandsFileDatasetForm.
     * @exception
     * @since 1.0
     */
	public String getIsqlCommandsFileDatasetForm() {
		return isqlCommandsFileDatasetForm;
	}
	/**
     * @param isqlCommandsFileDatasetForm
     *            The isqlCommandsFileDatasetForm to set.
     * @exception
     * @since 1.0
     */
	public void setIsqlCommandsFileDatasetForm(final String isqlCommandsFileDatasetForm) {
		this.isqlCommandsFileDatasetForm = isqlCommandsFileDatasetForm;
	}
	/**
     * @return Returns the showAddDatasetForm.
     * @exception
     * @since 1.0
     */
	public boolean isShowAddDatasetForm() {
		return showAddDatasetForm;
	}
	/**
     * @param showAddDatasetForm
     *            The showAddDatasetForm to set.
     * @exception
     * @since 1.0
     */
	public void setShowAddDatasetForm(final boolean showAddDatasetForm) {
		this.showAddDatasetForm = showAddDatasetForm;
	}
	/**
     * @return Returns the showEditDatasetForm.
     * @exception
     * @since 1.0
     */
	public boolean isShowEditDatasetForm() {
		return showEditDatasetForm;
	}
	/**
     * @param showEditDatasetForm
     *            The showEditDatasetForm to set.
     * @exception
     * @since 1.0
     */
	public void setShowEditDatasetForm(final boolean showEditDatasetForm) {
		this.showEditDatasetForm = showEditDatasetForm;
	}
	/**
     * @return Returns the showTheDataset.
     * @exception
     * @since 1.0
     */
	public boolean isShowTheDataset() {
		return showTheDataset;
	}
	/**
     * @param showTheDataset
     *            The showTheDataset to set.
     * @exception
     * @since 1.0
     */
	public void setShowTheDataset(final boolean showTheDataset) {
		this.showTheDataset = showTheDataset;
	}
	/**
     * @return Returns the areDatasets.
     * @exception
     * @since 1.0
     */
	public boolean isAreDatasets() {
		return areDatasets;
	}
	/**
     * @param areDatasets
     *            The areDatasets to set.
     * @exception
     * @since 1.0
     */
	public void setAreDatasets(final boolean areDatasets) {
		this.areDatasets = areDatasets;
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
     * @return Returns the message1.
     * @exception
     * @since 1.0
     */
	public String getMessage1() {
		return message1;
	}
	/**
     * @param message1
     *            The message1 to set.
     * @exception
     * @since 1.0
     */
	public void setMessage1(final String message1) {
		this.message1 = message1;
	}
	/**
     * @return Returns the message2.
     * @exception
     * @since 1.0
     */
	public String getMessage2() {
		return message2;
	}
	/**
     * @param message2
     *            The message2 to set.
     * @exception
     * @since 1.0
     */
	public void setMessage2(final String message2) {
		this.message2 = message2;
	}
	/**
     * @return Returns the message3.
     * @exception
     * @since 1.0
     */
	public String getMessage3() {
		return message3;
	}
	/**
     * @param message3
     *            The message3 to set.
     * @exception
     * @since 1.0
     */
	public void setMessage3(final String message3) {
		this.message3 = message3;
	}
	/**
     * @return Returns the message4.
     * @exception
     * @since 1.0
     */
	public String getMessage4() {
		return message4;
	}
	/**
     * @param message4
     *            The message4 to set.
     * @exception
     * @since 1.0
     */
	public void setMessage4(final String message4) {
		this.message4 = message4;
	}
	/**
     * @return Returns the message5.
     * @exception
     * @since 1.0
     */
	public String getMessage5() {
		return message5;
	}
	/**
     * @param message5
     *            The message5 to set.
     * @exception
     * @since 1.0
     */
	public void setMessage5(final String message5) {
		this.message5 = message5;
	}
	/**
     * @return Returns the message6.
     * @exception
     * @since 1.0
     */
	public String getMessage6() {
		return message6;
	}
	/**
     * @param message6
     *            The message6 to set.
     * @exception
     * @since 1.0
     */
	public void setMessage6(final String message6) {
		this.message6 = message6;
	}
	/**
     * @return Returns the message7.
     * @exception
     * @since 1.0
     */
	public String getMessage7() {
		return message7;
	}
	/**
     * @param message7
     *            The message7 to set.
     * @exception
     * @since 1.0
     */
	public void setMessage7(final String message7) {
		this.message7 = message7;
	}
	/**
     * @return Returns the message8.
     * @exception
     * @since 1.0
     */
	public String getMessage8() {
		return message8;
	}
	/**
     * @param message8
     *            The message8 to set.
     * @exception
     * @since 1.0
     */
	public void setMessage8(final String message8) {
		this.message8 = message8;
	}
}
