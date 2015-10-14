// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-linked-data-server
// Responsible: ALIADA Consortium
package eu.aliada.linkeddataserversetup.model;

import java.util.ArrayList;

import eu.aliada.linkeddataserversetup.model.Subset;

/**
 * Linked Data Server job configuration.
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class JobConfiguration {
	/** Job identification number. */
	private Integer id;
	/** IP address of the machine where the RDF store resides. */
	private String storeIp;
	/** Port number of the RDF store for SQL access. */
	private int storeSqlPort;
	/** The login of the SQL access. */
	private String sqlLogin;
	/** The password of the SQL access. */
	private String sqlPassword;
	/** The dataset domain name. */
	private String domainName;
	/** The address of the network interface the Virtuoso HTTP
	 *  server uses to listen and accept connections. */
	private String listeningHost;
	/** The virtual host name that the browser presents as Host: 
	 * entry in the request headers. i.e. Name-based virtual hosting. */
	private String virtualHost;
	/** Full path to the ISQL command. */
	private String isqlCommandPath;
	/** Full path of the ISQL commands global file to execute.
	 *  If the isql_commands_file_dataset is null or it does not exist, 
	 *  this one will be used. */
	private String isqlCommandsDatasetFilenameDefault;
	/** Dataset identifier. */
	private int datasetId;
	/** Full path of the ISQL commands global file to execute. */
	private String isqlCommandsDatasetFilename;
	/** Full path of the ISQL commands default file to execute for every subset. 
	 *  If the isql_commands_file_subset is null or it does not exist, 
	 *  this one will be used. */
	private String isqlCommandsSubsetFilenameDefault;
	/** The URI Identifier section. */
	private String uriIdPart;
	/** The URI Document section. */
	private String uriDocPart;
	/** The URI Ontology section. */
	private String uriDefPart;
	/** The URI Dataset Concept section. */
	private String uriConceptPart; 
	/** The URI Set section. */
	private String uriSetPart;
	/** Virtuoso HTTP Server Root physical path. */
	private String virtHttpServRoot;
	/** Aliada Ontology URI. */
	private String ontologyUri;
	/** Dataset short description. */
	private String datasetDesc;
	/** Dataset long description. */
	private String datasetLongDesc;
	/** The URI of the SPARQL endpoint of Aliada. */
	private String sparqlEndpointUri;
	/** The login of the SPARQL endpoint . */
	private String sparqlLogin;
	/** The password of the SPARQL endpoint . */
	private String sparqlPassword;
	/** Dataset Public Sparql endpoint */
	private String publicSparqlEndpointUri;
	/** The dataset  author. */
	private String datasetAuthor;
	/** The dataset source URL. */
	private String datasetSourceURL;
	/** License URL.*/
	private String licenseURL;
	/** The organization name. */
	private String orgName;
	/** Temporary folder name where to create temporary files. */
	private String tmpDir;
	/** The organization image file path. */
	private String orgImagePath;
	/** The organization image URL. */
	private String orgImageURL;
	/** The CSS file path. */
	private String cssFilePath;
	/** The CSS file URL. */
	private String cssFileURL;
	
	/** The subsets where the dataset resides. */
	private final ArrayList<Subset> subsets = new ArrayList<Subset>(); 
   
	/**
	 * Returns the identifier of this job configuration.
	 * 
	 * @return The identifier of this job configuration.
	 * @since 1.0
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * Sets the identifier of this job configuration.
	 * 
	 * @param id The identifier of this job configuration.
	 * @since 1.0
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * Returns the IP of the RDF store.
	 * 
	 * @return The IP of the RDF store
	 * @since 1.0
	 */
	public String getStoreIp() {
		return storeIp;
	}
	/**
	 * Sets the IP of the RDF store.
	 * 
	 * @param storeIp The IP of the RDF store.
	 * @since 1.0
	 */
	public void setStoreIp(final String storeIp) {
		this.storeIp = storeIp;
	}

	/**
	 * Returns the port for SQL data access.
	 * 
	 * @return The port for SQL data access.
	 * @since 1.0
	 */
	public int getStoreSqlPort() {
		return storeSqlPort;
	}
	/**
	 * Sets the port for SQL data access.
	 * 
	 * @param storeSqlPort The port for SQL data access.
	 * @since 1.0
	 */
	public void setStoreSqlPort(final int storeSqlPort) {
		this.storeSqlPort = storeSqlPort;
	}

	/**
	 * Returns the login required for authentication in the RDF store.
	 * 
	 * @return The login required for authentication in the RDF store.
	 * @since 1.0
	 */
	public String getSqlLogin() {
		return sqlLogin;
	}
	/**
	 * Sets the login required for authentication in the RDF store.
	 * 
	 * @param sqlLogin The login required for authentication in the RDF store.
	 * @since 1.0
	 */
	public void setSqlLogin(final String sqlLogin) {
		this.sqlLogin = sqlLogin;
	}
	
	/**
	 * Returns the password required for authentication in the RDF store.
	 * 
	 * @return The password required for authentication in the RDF store.
	 * @since 1.0
	 */
	public String getSqlPassword() {
		return sqlPassword;
	}
	/**
	 * Sets the password required for authentication in the RDF store.
	 * 
	 * @param sqlPassword Password required for authentication in the RDF store.
	 * @since 1.0
	 */
	public void setSqlPassword(final String sqlPassword) {
		this.sqlPassword = sqlPassword;
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
	 * Returns the address of the network interface the Virtuoso HTTP
	 * server uses to listen and accept connections.
	 * 
	 * @return The address of the listening host.
	 * @since 1.0
	 */
	public String getListeningHost() {
		return listeningHost;
	}
	/**
	 * Sets  the address of the network interface the Virtuoso HTTP
	 * server uses to listen and accept connections.
	 * 
	 * @param listeningHost The address of the listening host.
	 * @since 1.0
	 */
	public void setListeningHost(final String listeningHost) {
		this.listeningHost = listeningHost;
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
	 * Returns the path of the ISQL command of the RDF store.
	 * 
	 * @return The path of the ISQL command of the RDF store.
	 * @since 1.0
	 */
	public String getIsqlCommandPath() {
		return isqlCommandPath;
	}
	/**
	 * Sets the path of the ISQL command of the RDF store.
	 * 
	 * @param isqlCommandPath The path the ISQL command of the RDF store.
	 * @since 1.0
	 */
	public void setIsqlCommandPath(final String isqlCommandPath) {
		this.isqlCommandPath = isqlCommandPath;
	}		

	/**
	 * Returns the path of the default file containing the ISQL commands for URL 
	 * rewriting in Virtuoso for a dataset. It contains the default mapping 
	 * for HTML rendering.
	 * 
	 * @return The path of the default file containing the ISQL commands for URL 
	 *         rewriting in Virtuoso for a dataset.  It contains the default 
	 *         mapping for HTML rendering.
	 * @since 2.0
	 */
	public String getIsqlCommandsDatasetFilenameDefault() {
		return isqlCommandsDatasetFilenameDefault;
	}
	/**
	 * Sets the path of the default file containing the ISQL commands for URL 
	 * rewriting in Virtuoso for a dataset. It contains the default mapping 
	 * for HTML rendering.
	 * 
	 * @param isqlCommandsDatasetFilenameDefault The path of the default file 
	 *                                           containing the ISQL commands 
	 *                                           for URL rewriting in Virtuoso 
	 *                                           for a dataset.
	 * @since 2.0
	 */
	public void setIsqlCommandsDatasetFilenameDefault(final String isqlCommandsDatasetFilenameDefault) {
		this.isqlCommandsDatasetFilenameDefault = isqlCommandsDatasetFilenameDefault;
	}		

	/**
	 * Returns the dataset identifier.
	 * 
	 * @return The dataset identifier.
	 * @since 2.0
	 */
	public int getDatasetId() {
		return datasetId;
	}
	/**
	 * Sets dataset identifier.
	 * 
	 * @param datasetId The dataset identifier.
	 * @since 2.0
	 */
	public void setDatasetId(final int datasetId) {
		this.datasetId = datasetId;
	}		

	/**
	 * Returns the path of the file containing the ISQL commands for URL 
	 * rewriting in Virtuoso.
	 * 
	 * @return The path of the file containing the ISQL commands for URL 
	 *         rewriting in Virtuoso.
	 * @since 2.0
	 */
	public String getIsqlCommandsDatasetFilename() {
		return isqlCommandsDatasetFilename;
	}
	/**
	 * Sets the path of the file containing the ISQL commands for URL 
	 * rewriting in Virtuoso.
	 * 
	 * @param isqlCommandsDatasetFilename The path of the file containing 
	 *        the ISQL commands for URL rewriting in Virtuoso.
	 * @since 2.0
	 */
	public void setIsqlCommandsDatasetFilename(final String isqlCommandsDatasetFilename) {
		this.isqlCommandsDatasetFilename = isqlCommandsDatasetFilename;
	}		

	/**
	 * Returns the path of the default file containing the ISQL commands for URL 
	 * rewriting in Virtuoso for a subset. It contains the default mapping for 
	 * HTML rendering.
	 * 
	 * @return The path of the default file containing the ISQL commands for URL 
	 *         rewriting in Virtuoso for a subset.  It contains the default 
	 *         mapping for HTML rendering.
	 * @since 2.0
	 */
	public String getIsqlCommandsSubsetFilenameDefault() {
		return isqlCommandsSubsetFilenameDefault;
	}
	/**
	 * Sets the path of the default file containing the ISQL commands for URL 
	 * rewriting in Virtuoso for a subset. It contains the default mapping 
	 * for HTML rendering.
	 * 
	 * @param isqlCommandsSubsetFilenameDefault The path of the default file containing 
	 *        the ISQL commands for URL rewriting in Virtuoso for a subset.
	 * @since 2.0
	 */
	public void setIsqlCommandsSubsetFilenameDefault(final String isqlCommandsSubsetFilenameDefault) {
		this.isqlCommandsSubsetFilenameDefault = isqlCommandsSubsetFilenameDefault;
	}		

	/**
	 * Returns the URI Identifier section.
	 * 
	 * @return The URI Identifier section.
	 * @since 2.0
	 */
	public String getUriIdPart() {
		return uriIdPart;
	}
	/**
	 * Sets the URI Identifier section.
	 * 
	 * @param uriIdPart The URI Identifier section.
	 * @since 2.0
	 */
	public void setUriIdPart(final String uriIdPart) {
		this.uriIdPart = uriIdPart;
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
	 * Returns the URI Ontology section.
	 * 
	 * @return The URI Ontology section.
	 * @since 2.0
	 */
	public String getUriDefPart() {
		return uriDefPart;
	}
	/**
	 * Sets the URI Ontology section.
	 * 
	 * @param uriDefPart The URI Ontology section.
	 * @since 2.0
	 */
	public void setUriDefPart(final String uriDefPart) {
		this.uriDefPart = uriDefPart;
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
	 * Returns the URI of the SPARQL/Update endpoint of the dataset. 
	 * 
	 * @return The URI of the SPARQL/Update endpoint of the dataset. 
	 * @since 2.0
	 */
	public String getSparqlEndpointUri() {
		return sparqlEndpointUri;
	}
	/**
	 * Sets the URI of the SPARQL/Update endpoint of the dataset.
	 * 
	 * @param sparqlEndpointUri The URI of the SPARQL/Update endpoint of the  
	 *        dataset.
	 * @since 2.0
	 */
	public void setSparqlEndpointUri(final String sparqlEndpointUri) {
		this.sparqlEndpointUri = sparqlEndpointUri;
	}

	/**
	 * Returns the login required for authentication in the SPARQL endpoint.
	 * 
	 * @return The login required for authentication in the SPARQL endpoint.
	 * @since 2.0
	 */
	public String getSparqlLogin() {
		return sparqlLogin;
	}
	/**
	 * Sets the login required for authentication in the SPARQL endpoint.
	 * 
	 * @param sparqlLogin The login required for authentication in the SPARQL 
	 *        endpoint.
	 * @since 2.0
	 */
	public void setSparqlLogin(final String sparqlLogin) {
		this.sparqlLogin = sparqlLogin;
	}
	
	/**
	 * Returns the password required for authentication in the SPARQL endpoint.
	 * 
	 * @return The password required for authentication in the SPARQL endpoint.
	 * @since 2.0
	 */
	public String getSparqlPassword() {
		return sparqlPassword;
	}
	/**
	 * Sets the password required for authentication in the SPARQL endpoint.
	 * 
	 * @param sparqlPassword Password required for authentication in the SPARQL 
	 *        endpoint.
	 * @since 2.0
	 */
	public void setSparqlPassword(final String sparqlPassword) {
		this.sparqlPassword = sparqlPassword;
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
	public void setLicenseURL(final String licenseURL) {
		this.licenseURL = licenseURL;
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
	 * Returns the path of the temporary directory for creating temporary files.
	 * 
	 * @return The path of the temporary directory for creating temporary files.
	 * @since 2.0
	 */
	public String getTmpDir() {
		return tmpDir;
	}
	/**
	 * Sets the path of the temporary directory for creating temporary files.
	 * 
	 * @param tmpDir The path of the temporary directory for creating temporary files.
	 * @since 2.0
	 */
	public void setTmpDir(final String tmpDir) {
		this.tmpDir = tmpDir;
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
	 * Returns the organization image Path.
	 * 
	 * @return The organization image Path.
	 * @since 2.0
	 */
	public String getCssFilePath() {
		return cssFilePath;
	}
	/**
	 * Sets the organization image Path.
	 * 
	 * @param orgImagePath Organization image Path.
	 * @since 2.0
	 */
	public void setCssFilePath(final String cssFilePath) {
		this.cssFilePath = cssFilePath;
	}
	
	/**
	 * Returns the organization image URL.
	 * 
	 * @return The organization image URL.
	 * @since 2.0
	 */
	public String getCssFileURL() {
		return cssFileURL;
	}
	/**
	 * Sets the organization image URL.
	 * 
	 * @param orgImageURL Organization image URL.
	 * @since 2.0
	 */
	public void setCssFileURL(final String cssFileURL) {
		this.cssFileURL = cssFileURL;
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
