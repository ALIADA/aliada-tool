// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

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
	private String isqlCommandsFileGlobalForm;
	private String isqlCommandsFileSubsetDefaultForm;
	
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
                this.isqlCommandsFileGlobalForm = rs.getString("isql_commands_file_global");
                this.isqlCommandsFileSubsetDefaultForm = rs.getString("isql_commands_file_subset_default");
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
                this.isqlCommandsFileGlobalForm = rs.getString("isql_commands_file_global");
                this.isqlCommandsFileSubsetDefaultForm = rs.getString("isql_commands_file_subset_default");
                statement.close();
                rs.close();
                connection.close();
                session.setAttribute("DatasetId", this.datasetIdForm);
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
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE aliada.dataset set dataset_desc='"
            		+ this.datasetDescForm + "',domain_name='"
                    + this.domainNameForm + "',uri_id_part='"
                    + this.uriIdPartForm + "',uri_doc_part='"
                    + this.uriDocPartForm + "',uri_def_part='"
                    + this.uriDefPartForm + "',uri_concept_part='"
                    + this.uriConceptPartForm + "',listening_host='"
                    + this.listeningHostForm + "',virtual_host='"
                    + this.virtualHostForm + "',sparql_endpoint_uri='"
                    + this.sparqlEndpointURIForm + "',sparql_endpoint_login='"
                    + this.sparqlEndpointLoginForm + "',sparql_endpoint_password='"
                    + this.sparqlEndpointPasswordForm + "',public_sparql_endpoint_uri='"
                    + this.publicSparqlEndpointURIForm + "',dataset_author='"
                    + this.datasetAuthorForm + "',ckan_dataset_name='"
                    + this.ckanDatasetNameForm + "',dataset_long_desc='"
                    + this.datasetLongDescForm + "',dataset_source_url='"
                    + this.datasetSourceURLForm + "',license_ckan_id='"
                    + this.licenseCkanIdForm + "',license_url='"
                    + this.licenseURLForm + "',isql_commands_file_global='"
                    + this.isqlCommandsFileGlobalForm + "',isql_commands_file_subset_default='"
                    + this.isqlCommandsFileSubsetDefaultForm + "' where datasetId='"
                    + session.getAttribute("DatasetId") + "'");
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
    	 Connection connection = null;
         String usernameLogged = (String) ServletActionContext.getRequest().getSession().getAttribute("logedUser");
         int organisationId = 0;
         try {
         	connection = new DBConnectionManager().getConnection();
         	
         	Statement statement = connection.createStatement();
            ResultSet rs = statement
                     .executeQuery("select organisationId from aliada.user where user_name='" + usernameLogged + "'");
             if (rs.next()) {
                 organisationId = rs.getInt("organisationId");
             }
             
             statement = connection.createStatement();
             statement.executeUpdate("INSERT INTO aliada.dataset (organisationId, dataset_desc, domain_name,"
             		+ "uri_id_part, uri_doc_part, uri_def_part, uri_concept_part, "
             		+ "listening_host, virtual_host, sparql_endpoint_uri,"
             		+ "sparql_endpoint_login, sparql_endpoint_password,"
             		+ "public_sparql_endpoint_uri, dataset_author,"
             		+ "ckan_dataset_name, dataset_long_desc, "
             		+ "dataset_source_url, license_ckan_id,"
             		+ "license_url,"
             		+ "isql_commands_file_global, isql_commands_file_subset_default) VALUES (" + organisationId + ",'"
                     + this.datasetDescForm + "', '" + this.domainNameForm + "', '"
                     + this.uriIdPartForm + "', '" + this.uriDocPartForm + "', '"
                     + this.uriDefPartForm + "', '" + this.uriConceptPartForm + "', '"
                     + this.listeningHostForm + "', '" + this.virtualHostForm + "', '"
                     + this.sparqlEndpointURIForm + "', '" + this.sparqlEndpointLoginForm + "', '" 
                     + this.sparqlEndpointPasswordForm + "', '" + this.publicSparqlEndpointURIForm + "', '"
                     + this.datasetAuthorForm + "', '" + this.ckanDatasetNameForm + "', '" + this.datasetLongDescForm + "', '"
                     + this.datasetSourceURLForm + "', '" + this.licenseCkanIdForm + "', '" 
                     + this.licenseURLForm + "', '" 
                     + this.isqlCommandsFileGlobalForm + "', '" + this.isqlCommandsFileSubsetDefaultForm + "')");
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
     * The method to show the dataset form empty.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String showAddDataset() {
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
     * @return Returns the isqlCommandsFileGlobalForm.
     * @exception
     * @since 1.0
     */
	public String getIsqlCommandsFileGlobalForm() {
		return isqlCommandsFileGlobalForm;
	}
	/**
     * @param isqlCommandsFileGlobalForm
     *            The isqlCommandsFileGlobalForm to set.
     * @exception
     * @since 1.0
     */
	public void setIsqlCommandsFileGlobalForm(final String isqlCommandsFileGlobalForm) {
		this.isqlCommandsFileGlobalForm = isqlCommandsFileGlobalForm;
	}
	/**
     * @return Returns the isqlCommandsFileSubsetDefaultForm.
     * @exception
     * @since 1.0
     */
	public String getIsqlCommandsFileSubsetDefaultForm() {
		return isqlCommandsFileSubsetDefaultForm;
	}
	/**
     * @param isqlCommandsFileSubsetDefaultForm
     *            The isqlCommandsFileSubsetDefaultForm to set.
     * @exception
     * @since 1.0
     */
	public void setIsqlCommandsFileSubsetDefaultForm(
			final String isqlCommandsFileSubsetDefaultForm) {
		this.isqlCommandsFileSubsetDefaultForm = isqlCommandsFileSubsetDefaultForm;
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
    
}
