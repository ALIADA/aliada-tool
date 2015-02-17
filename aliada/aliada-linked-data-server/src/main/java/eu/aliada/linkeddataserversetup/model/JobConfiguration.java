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
	/** Full path of the ISQL commands global file to execute. */
	private String isqlCommandsGlobalFilename;
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
	/** Virtuoso HTTP Server Root physical path. */
	private String virtHttpServRoot;
	/** Aliada Ontology URI. */
	private String ontologyUri;
	/** Dataset short description. */
	private String datasetDesc;
	/** Dataset long description. */
	private String datasetLongDesc;
	/** Dataset Public Sparql endpoint */
	private String publicSparqlEndpointUri;
	
	/** The subsets where the dataset resides. */
	private ArrayList<Subset> subsets; 
   
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
	 * Returns the path of the file containing the ISQL commands for URL 
	 * rewriting in Virtuoso.
	 * 
	 * @return The path of the file containing the ISQL commands for URL 
	 *         rewriting in Virtuoso.
	 * @since 2.0
	 */
	public String getIsqlCommandsGlobalFilename() {
		return isqlCommandsGlobalFilename;
	}
	/**
	 * Sets the path of the file containing the ISQL commands for URL 
	 * rewriting in Virtuoso.
	 * 
	 * @param isqlCommandsGlobalFilename The path of the file containing 
	 *        the ISQL commands for URL rewriting in Virtuoso.
	 * @since 2.0
	 */
	public void setIsqlCommandsGlobalFilename(final String isqlCommandsGlobalFilename) {
		this.isqlCommandsGlobalFilename = isqlCommandsGlobalFilename;
	}		

	/**
	 * Returns the path of the default file containing the ISQL commands for URL 
	 * rewriting in Virtuoso for a subset. It contains the default mapping for HTML rendering.
	 * 
	 * @return The path of the default file containing the ISQL commands for URL 
	 *         rewriting in Virtuoso for a subset.  It contains the default mapping for HTML 
	 *         rendering.
	 * @since 2.0
	 */
	public String getIsqlCommandsSubsetFilenameDefault() {
		return isqlCommandsSubsetFilenameDefault;
	}
	/**
	 * Sets the path of the default file containing the ISQL commands for URL 
	 * rewriting in Virtuoso for a subset. It contains the default mapping for HTML rendering.
	 * 
	 * @param isqlCommandsSubsetFilenameDefault The path of the default file containing 
	 *        the ISQL commands for URL rewriting in Virtuoso for a subset.
	 * @since 2.0
	 */
	public void setIsqlCommandsSubsetFilenameDefault(String isqlCommandsSubsetFilenameDefault) {
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
