// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-linked-data-server
// Responsible: ALIADA Consortium
package eu.aliada.linkeddataserversetup.model;

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
	/** The URI of the dataset graph of Aliada. */
	private String graph;
	/** The URI prefix of all resources URIs in the dataset. */
	private String datasetBase;
	/** Full path to the ISQL command. */
	private String isqlCommandPath;
	/** Full path of the ISQL commands file to execute. */
	private String isqlCommandsFilename;
	/** Full path of the ISQL commands default file to execute. 
	 *  If the isql_commands_file is null or it does not exist, 
	 *  this one will be used. */
	private String isqlCommandsFilenameDefault;
    
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
	 * Returns graph name where dataset resides. 
	 * 
	 * @return Graph name where dataset resides. 
	 * @since 1.0
	 */
	public String getGraph() {
		return graph;
	}
	/**
	 * Sets graph name where dataset resides. 
	 * 
	 * @param graph Graph name where dataset resides. 
	 * @since 1.0
	 */
	public void setGraph(final String graph) {
		this.graph = graph;
	}		

	/**
	 * Returns the URI prefix of all resources URIs in the dataset.
	 * 
	 * @return The URI prefix of all resources URIs in the dataset.
	 * @since 1.0
	 */
	public String getDatasetBase() {
		return datasetBase;
	}
	/**
	 * Sets the URI prefix of all resources URIs in the dataset.
	 * 
	 * @param datasetBase The URI prefix of all resources URIs in the dataset.
	 * @since 1.0
	 */
	public void setDatasetBase(final String datasetBase) {
		this.datasetBase = datasetBase;
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
	 * @since 1.0
	 */
	public String getIsqlCommandsFilename() {
		return isqlCommandsFilename;
	}
	/**
	 * Sets the path of the file containing the ISQL commands for URL 
	 * rewriting in Virtuoso.
	 * 
	 * @param isqlCommandsFilename The path of the file containing 
	 *        the ISQL commands for URL rewriting in Virtuoso.
	 * @since 1.0
	 */
	public void setIsqlCommandsFilename(final String isqlCommandsFilename) {
		this.isqlCommandsFilename = isqlCommandsFilename;
	}		

	/**
	 * Returns the path of the default file containing the ISQL commands for URL 
	 * rewriting in Virtuoso. It contains the default mapping for HTML rendering.
	 * 
	 * @return The path of the default file containing the ISQL commands for URL 
	 *         rewriting in Virtuoso.  It contains the default mapping for HTML 
	 *         rendering.
	 * @since 1.0
	 */
	public String getIsqlCommandsFilenameDefault() {
		return isqlCommandsFilenameDefault;
	}
	/**
	 * Sets the path of the default file containing the ISQL commands for URL 
	 * rewriting in Virtuoso. It contains the default mapping for HTML rendering.
	 * 
	 * @param isqlCommandsFilenameDefault The path of the default file containing 
	 *        the ISQL commands for URL rewriting in Virtuoso.
	 * @since 1.0
	 */
	public void setIsqlCommandsFilenameDefault(String isqlCommandsFilenameDefault) {
		this.isqlCommandsFilenameDefault = isqlCommandsFilenameDefault;
	}		
}
