// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.model;

import java.util.ArrayList;

import eu.aliada.ckancreation.model.Subset;

/**
 * CKAN Page Creation job configuration.
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
	/** Temporary folder name where to create temporary files. */
	private String tmpDir;
	/** IP address of the machine where the RDF store resides. */
	private String storeIp;
	/** Port number of the RDF store for SQL access. */
	private int storeSqlPort;
	/** The login of the SQL access. */
	private String sqlLogin;
	/** The password of the SQL access. */
	private String sqlPassword;
	/** Full path to the ISQL command. */
	private String isqlCommandPath;
	/** Aliada Ontology URI. */
	private String ontologyUri;
	/** Virtuoso HTTP Server Root physical path. */
	private String virtHttpServRoot;

	/** ORGANIZATION **/
	/** The organization name. */
	private String orgName;
	/** The organization title. */
	private String orgTitle;
	/** The organization description. */
	private String orgDescription;
	/** The organization image file path. */
	private String orgImagePath;
	/** The organization image URL. */
	private String orgImageURL;
	/** The organization home page. */
	private String orgHomePage;
	
	/** DATASET **/
	/** The dataset name. */
	private String ckanDatasetName;
	/** The dataset  author. */
	private String datasetAuthor;
	/** The dataset source URL. */
	private String datasetSourceURL;
	/** the dataset short description. */
	private String datasetDesc;
	/** the dataset long description. */
	private String datasetLongDesc;
	/** The dataset Public Sparql endpoint */
	private String publicSparqlEndpointUri;
	/** License id in CKAN. See opendefinition.org .*/
	private String licenseCKANId;
	/** License URL.*/
	private String licenseURL;
	/** The dataset domain name. */
	private String domainName;
	/** The virtual host name that the browser presents as Host: 
	 * entry in the request headers. i.e. Name-based virtual hosting. */
	private String virtualHost;
	/** The URI Document section. */
	private String uriDocPart;
	/** The URI Dataset Concept section. */
	private String uriConceptPart; 
	/** The URI Set section. */
	private String uriSetPart;
	/** Total number of triples */
	private int numTriples;

	/** The subsets where the dataset resides. */
	private ArrayList<Subset> subsets = new ArrayList<Subset>(); 
    
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
	 * Returns the path of the temporary directory for creating temporary files.
	 * 
	 * @return The path of the temporary directory for creating temporary files.
	 * @since 1.0
	 */
	public String getTmpDir() {
		return tmpDir;
	}
	/**
	 * Sets the path of the temporary directory for creating temporary files.
	 * 
	 * @param tmpDir The path of the temporary directory for creating temporary files.
	 * @since 1.0
	 */
	public void setTmpDir(final String tmpDir) {
		this.tmpDir = tmpDir;
	}		

	/**
	 * Returns the IP of the RDF store.
	 * 
	 * @return The IP of the RDF store
	 * @since 2.0
	 */
	public String getStoreIp() {
		return storeIp;
	}
	/**
	 * Sets the IP of the RDF store.
	 * 
	 * @param storeIp The IP of the RDF store.
	 * @since 2.0
	 */
	public void setStoreIp(final String storeIp) {
		this.storeIp = storeIp;
	}

	/**
	 * Returns the port for SQL data access.
	 * 
	 * @return The port for SQL data access.
	 * @since 2.0
	 */
	public int getStoreSqlPort() {
		return storeSqlPort;
	}
	/**
	 * Sets the port for SQL data access.
	 * 
	 * @param storeSqlPort The port for SQL data access.
	 * @since 2.0
	 */
	public void setStoreSqlPort(final int storeSqlPort) {
		this.storeSqlPort = storeSqlPort;
	}

	/**
	 * Returns the login required for authentication in the RDF store.
	 * 
	 * @return The login required for authentication in the RDF store.
	 * @since 2.0
	 */
	public String getSqlLogin() {
		return sqlLogin;
	}
	/**
	 * Sets the login required for authentication in the RDF store.
	 * 
	 * @param sqlLogin The login required for authentication in the RDF store.
	 * @since 2.0
	 */
	public void setSqlLogin(final String sqlLogin) {
		this.sqlLogin = sqlLogin;
	}
	
	/**
	 * Returns the password required for authentication in the RDF store.
	 * 
	 * @return The password required for authentication in the RDF store.
	 * @since 2.0
	 */
	public String getSqlPassword() {
		return sqlPassword;
	}
	/**
	 * Sets the password required for authentication in the RDF store.
	 * 
	 * @param sqlPassword Password required for authentication in the RDF store.
	 * @since 2.0
	 */
	public void setSqlPassword(final String sqlPassword) {
		this.sqlPassword = sqlPassword;
	}	
	
	/**
	 * Returns the path of the ISQL command of the RDF store.
	 * 
	 * @return The path of the ISQL command of the RDF store.
	 * @since 2.0
	 */
	public String getIsqlCommandPath() {
		return isqlCommandPath;
	}
	/**
	 * Sets the path of the ISQL command of the RDF store.
	 * 
	 * @param isqlCommandPath The path the ISQL command of the RDF store.
	 * @since 2.0
	 */
	public void setIsqlCommandPath(final String isqlCommandPath) {
		this.isqlCommandPath = isqlCommandPath;
	}		

	/**
	 * Returns the Aliada ontology URI.
	 * 
	 * @return The Aliada ontology URI.
	 * @since 1.0
	 */
	public String getOntologyUri() {
		return ontologyUri;
	}
	/**
	 * Sets the Aliada ontology URI.
	 * 
	 * @param ontologyUri The Aliada ontology URI.
	 * @since 1.0
	 */
	public void setOntologyUri(final String ontologyUri) {
		this.ontologyUri = ontologyUri;
	}

	/**
	 * Returns Virtuoso HTTP Server Root physical path.
	 * 
	 * @return Virtuoso HTTP Server Root physical path.
	 * @since 2.0
	 */
	public String getVirtHttpServRoot() {
		return virtHttpServRoot;
	}
	/**
	 * Sets Virtuoso HTTP Server Root physical path.
	 * 
	 * @param virtHttpServRoot Virtuoso HTTP Server Root physical path.
	 * @since 2.0
	 */
	public void setVirtHttpServRoot(final String virtHttpServRoot) {
		this.virtHttpServRoot = virtHttpServRoot;
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
	 * Returns the organization image Path.
	 * 
	 * @return The organization image Path.
	 * @since 2.0
	 */
	public String getOrgImagePath() {
		return orgImagePath;
	}
	/**
	 * Sets the organization image Path.
	 * 
	 * @param orgImagePath Organization image Path.
	 * @since 2.0
	 */
	public void setOrgImagePath(final String orgImagePath) {
		this.orgImagePath = orgImagePath;
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
	 * Returns the dataset name in CKAN.
	 * 
	 * @return The dataset name in CKAN.
	 * @since 2.0
	 */
	public String getCkanDatasetName() {
		return ckanDatasetName;
	}
	/**
	 * Sets the dataset name in CKAN.
	 * 
	 * @param datasetName dataset name in CKAN.
	 * @since 2.0
	 */
	public void setCkanDatasetName(final String ckanDatasetName) {
		this.ckanDatasetName = ckanDatasetName;
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
	 * Returns the dataset source URL.
	 * 
	 * @return The dataset source URL.
	 * @since 2.0
	 */
	public String getDatasetSourceURL() {
		return datasetSourceURL;
	}
	/**
	 * Sets the dataset source URL.
	 * 
	 * @param datasetURL dataset source URL.
	 * @since 2.0
	 */
	public void setDatasetSourceURL(final String datasetSourceURL) {
		this.datasetSourceURL = datasetSourceURL;
	}

	/**
	 * Returns the dataset short description.
	 * 
	 * @return The dataset short description.
	 * @since 1.0
	 */
	public String getDatasetDesc() {
		return datasetDesc;
	}
	/**
	 * Sets the dataset short description.
	 * 
	 * @param datasetDesc The dataset short description.
	 * @since 1.0
	 */
	public void setDatasetDesc(final String datasetDesc) {
		this.datasetDesc = datasetDesc;
	}

	/**
	 * Returns the dataset long description.
	 * 
	 * @return The dataset long description.
	 * @since 1.0
	 */
	public String getDatasetLongDesc() {
		return datasetLongDesc;
	}
	/**
	 * Sets the dataset long description.
	 * 
	 * @param datasetLongDesc The dataset long description.
	 * @since 1.0
	 */
	public void setDatasetLongDesc(final String datasetLongDesc) {
		this.datasetLongDesc = datasetLongDesc;
	}

	/**
	 * Returns the dataset Public Sparql endpoint.
	 * 
	 * @return The dataset Public Sparql endpoint.
	 * @since 1.0
	 */
	public String getPublicSparqlEndpointUri() {
		return publicSparqlEndpointUri;
	}
	/**
	 * Sets the dataset Public Sparql endpoint.
	 * 
	 * @param publicSparqlEndpointUri The dataset Public Sparql endpoint.
	 * @since 1.0
	 */
	public void setPublicSparqlEndpointUri(final String publicSparqlEndpointUri) {
		this.publicSparqlEndpointUri = publicSparqlEndpointUri;
	}
	
	/**
	 * Returns the CKAN id of the License of the dataset. See opendefinition.org .
	 * 
	 * @return The CKAN id of the License of the dataset.
	 * @since 2.0
	 */
	public String getLicenseCKANId() {
		return this.licenseCKANId;
	}
	/**
	 * Sets the CKAN id of the License of the dataset. See opendefinition.org .
	 * 
	 * @param licenseCKANId CKAN id of the License of the dataset.
	 * @since 2.0
	 */
	public void setLicenseCKANId(String licenseCKANId) {
		this.licenseCKANId = licenseCKANId;
	}

	/**
	 * Returns the license URL of the dataset. 
	 * 
	 * @return The license URL of the dataset.
	 * @since 2.0
	 */
	public String getLicenseURL() {
		return this.licenseURL;
	}
	/**
	 * Sets the license URL of the dataset.
	 * 
	 * @param licenseURL license URL of the dataset.
	 * @since 2.0
	 */
	public void setLicenseURL(String licenseURL) {
		this.licenseURL = licenseURL;
	}

	/**
	 * Returns the dataset domain name.
	 * 
	 * @return The dataset domain name.
	 * @since 2.0
	 */
	public String getDomainName() {
		return domainName;
	}
	/**
	 * Sets the dataset domain name.
	 * 
	 * @param domainName The dataset domain name.
	 * @since 2.0
	 */
	public void setDomainName(final String domainName) {
		this.domainName = domainName;
	}

	/**
	 * Returns the virtual host name that the browser presents as Host: 
	 * entry in the request headers. i.e. Name-based virtual hosting.
	 * 
	 * @return The address of the virtual host.
	 * @since 1.0
	 */
	public String getVirtualHost() {
		return virtualHost;
	}
	/**
	 * Sets  the virtual host name that the browser presents as Host: 
	 * entry in the request headers. i.e. Name-based virtual hosting.
	 * 
	 * @param virtualHost The address of the virtual host.
	 * @since 1.0
	 */
	public void setVirtualHost(final String virtualHost) {
		this.virtualHost = virtualHost;
	}

	/**
	 * Returns the URI Document section.
	 * 
	 * @return The URI Document section.
	 * @since 2.0
	 */
	public String getUriDocPart() {
		return uriDocPart;
	}
	/**
	 * Sets the URI Document section.
	 * 
	 * @param uriDocPart The URI Document section.
	 * @since 2.0
	 */
	public void setUriDocPart(final String uriDocPart) {
		this.uriDocPart = uriDocPart;
	}

	/**
	 * Returns the URI Dataset Concept section.
	 * 
	 * @return The URI Dataset Concept section.
	 * @since 2.0
	 */
	public String getUriConceptPart() {
		return uriConceptPart;
	}
	/**
	 * Sets the URI Dataset Concept section.
	 * 
	 * @param uriConceptPart The URI Dataset Concept section.
	 * @since 2.0
	 */
	public void setUriConceptPart(final String uriConceptPart) {
		this.uriConceptPart = uriConceptPart;
	}

	
	/**
	 * Returns the URI Set section.
	 * 
	 * @return The URI Set section.
	 * @since 2.0
	 */
	public String getUriSetPart() {
		return uriSetPart;
	}
	/**
	 * Sets the URI Set section.
	 * 
	 * @param uriSetPart The URI Set section.
	 * @since 2.0
	 */
	public void setUriSetPart(final String uriSetPart) {
		this.uriSetPart = uriSetPart;
	}

	/**
	 * Returns the number of triples of the dataset. 
	 * 
	 * @return The number of triples of the dataset.
	 * @since 2.0
	 */
	public int getNumTriples() {
		return this.numTriples;
	}
	/**
	 * Sets the number of triples of the dataset.
	 * 
	 * @param numTriples Number of triples of the dataset.
	 * @since 2.0
	 */
	public void setNumTriples(int numTriples) {
		this.numTriples = numTriples;
	}

	/**
	 * Returns the subsets of the dataset. 
	 * 
	 * @return Subsets where dataset resides. 
	 * @since 2.0
	 */
	public ArrayList<Subset> getSubsets() {
		return subsets;
	}
	/**
	 * Adds a subset where dataset resides. 
	 * 
	 * @param subset Subset where dataset resides. 
	 * @since 2.0
	 */
	public void setSubset(final Subset subset) {
		subsets.add(subset);
	}		
}
