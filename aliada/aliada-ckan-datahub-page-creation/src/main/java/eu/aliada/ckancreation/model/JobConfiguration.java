// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.model;

import javax.ws.rs.FormParam;

/**
 * Links discovery job configuration.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
public class JobConfiguration {
	/** Job identification number. */
	private Integer id;
	/** CKAN API URL. */
	private String ckanApiURL;
	/** CKAN API Key. */
	private String ckanApiKey;
	/** The organization name. */
	private String orgName;
	/** The organization title. */
	private String orgTitle;
	/** The organization description. */
	private String orgDescription;
	/** The organization image URL. */
	private String orgImageURL;
	/** The organization home page. */
	private String orgHomePage;
	/** The dataset name. */
	private String datasetName;
	/** The dataset  author. */
	private String datasetAuthor;
	/** The dataset description. */
	private String datasetNotes;
	/** The dataset URL. */
	private String datasetURL;
	/** The SPARQL endpoint URL. */
	private String SPARQLendpoint;

    
	/**
	 * Returns the identifier of this job configuration.
	 * 
	 * @return The identifier of this job configuration.
	 * @since 2.0
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * Sets the identifier of this job configuration.
	 * 
	 * @param id The identifier of this job configuration.
	 * @since 2.0
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * Returns the CKAN API URL.
	 * 
	 * @return The CKAN API URL.
	 * @since 2.0
	 */
	public String getCkanApiURL() {
		return ckanApiURL;
	}
	/**
	 * Sets the CKAN API URL.
	 * 
	 * @param ckanApiURL CKAN API URL.
	 * @since 2.0
	 */
	public void setCkanApiURL(final String ckanApiURL) {
		this.ckanApiURL = ckanApiURL;
	}

	/**
	 * Returns the CKAN API Key.
	 * 
	 * @return The CKAN API Key.
	 * @since 2.0
	 */
	public String getCkanApiKey() {
		return ckanApiKey;
	}
	/**
	 * Sets the CKAN API Key.
	 * 
	 * @param ckanApiKey CKAN API Key.
	 * @since 2.0
	 */
	public void setCkanApiKey(final String ckanApiKey) {
		this.ckanApiKey = ckanApiKey;
	}

	/**
	 * Returns the organization name.
	 * 
	 * @return The organization name.
	 * @since 2.0
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * Sets the organization name.
	 * 
	 * @param orgName Organization name.
	 * @since 2.0
	 */
	public void setOrgName(final String orgName) {
		this.orgName = orgName;
	}

	/**
	 * Returns the organization title.
	 * 
	 * @return The organization title.
	 * @since 2.0
	 */
	public String getOrgTitle() {
		return orgTitle;
	}
	/**
	 * Sets the organization title.
	 * 
	 * @param orgTitle Organization title.
	 * @since 2.0
	 */
	public void setOrgTitle(final String orgTitle) {
		this.orgTitle = orgTitle;
	}

	/**
	 * Returns the organization description.
	 * 
	 * @return The organization description.
	 * @since 2.0
	 */
	public String getOrgDescription() {
		return orgDescription;
	}
	/**
	 * Sets the organization description.
	 * 
	 * @param orgDescription Organization description.
	 * @since 2.0
	 */
	public void setOrgDescription(final String orgDescription) {
		this.orgDescription = orgDescription;
	}
	
	/**
	 * Returns the organization image URL.
	 * 
	 * @return The organization image URL.
	 * @since 2.0
	 */
	public String getOrgImageURL() {
		return orgImageURL;
	}
	/**
	 * Sets the organization image URL.
	 * 
	 * @param orgImageURL Organization image URL.
	 * @since 2.0
	 */
	public void setOrgImageURL(final String orgImageURL) {
		this.orgImageURL = orgImageURL;
	}
	
	/**
	 * Returns the organization home page.
	 * 
	 * @return The organization home page.
	 * @since 2.0
	 */
	public String getOrgHomePage() {
		return orgHomePage;
	}
	/**
	 * Sets the organization home page.
	 * 
	 * @param orgHomePage Organization home page.
	 * @since 2.0
	 */
	public void setOrgHomePage(final String orgHomePage) {
		this.orgHomePage = orgHomePage;
	}

	/**
	 * Returns the dataset name.
	 * 
	 * @return The dataset name.
	 * @since 2.0
	 */
	public String getDatasetName() {
		return datasetName;
	}
	/**
	 * Sets the dataset name.
	 * 
	 * @param datasetName dataset name.
	 * @since 2.0
	 */
	public void setDatasetName(final String datasetName) {
		this.datasetName = datasetName;
	}

	/**
	 * Returns the dataset author.
	 * 
	 * @return The dataset author.
	 * @since 2.0
	 */
	public String getDatasetAuthor() {
		return datasetAuthor;
	}
	/**
	 * Sets the dataset author.
	 * 
	 * @param datasetAuthor dataset author.
	 * @since 2.0
	 */
	public void setDatasetAuthor(final String datasetAuthor) {
		this.datasetAuthor = datasetAuthor;
	}

	/**
	 * Returns the dataset description.
	 * 
	 * @return The dataset description.
	 * @since 2.0
	 */
	public String getDatasetNotes() {
		return datasetNotes;
	}
	/**
	 * Sets the dataset description.
	 * 
	 * @param datasetNotes dataset description.
	 * @since 2.0
	 */
	public void setDatasetNotes(final String datasetNotes) {
		this.datasetNotes = datasetNotes;
	}

	/**
	 * Returns the dataset URL.
	 * 
	 * @return The dataset URL.
	 * @since 2.0
	 */
	public String getDatasetURL() {
		return datasetURL;
	}
	/**
	 * Sets the dataset URL.
	 * 
	 * @param datasetURL dataset URL.
	 * @since 2.0
	 */
	public void setDatasetURL(final String datasetURL) {
		this.datasetURL = datasetURL;
	}

	/**
	 * Returns the URI of the SPARQL endpoint of the dataset.
	 * 
	 * @return The URI of the SPARQL endpoint of the dataset.
	 * @since 2.0
	 */
	public String getSPARQLEndpoint() {
		return SPARQLendpoint;
	}
	/**
	 * Sets the URI of the SPARQL endpoint of the dataset.
	 * 
	 * @param SPARQLendpoint The URI of the SPARQL endpoint of the dataset.
	 * @since 2.0
	 */
	public void setSPARQLEndpoint(final String SPARQLendpoint) {
		this.SPARQLendpoint = SPARQLendpoint;
	}

}
