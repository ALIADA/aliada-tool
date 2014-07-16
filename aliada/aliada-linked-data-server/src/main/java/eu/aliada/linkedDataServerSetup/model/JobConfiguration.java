// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-linked-data-server
// Responsible: ALIADA Consortium
package eu.aliada.linkedDataServerSetup.model;

/**
 * Linked Data Server job configuration.
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class JobConfiguration {
	private Integer id;
	private String storeIp;
	private int storeSqlPort;
	private String sqlLogin;
	private String sqlPassword;
	private String graph;
	private String datasetBase;
	private String isqlCommandPath;
	private String isqlCommandsFilename;
	private String isqlCommandsFilenameDefault;
    
	/**
	 * Returns the identifier of this job configuration.
	 * 
	 * @return The identifier of this job configuration.
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * Sets the identifier of this job configuration.
	 * 
	 * @param id The identifier of this job configuration.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Returns the IP of the RDF store.
	 * 
	 * @return The IP of the RDF store
	 */
	public String getStoreIp() {
		return storeIp;
	}
	/**
	 * Sets the IP of the RDF store.
	 * 
	 * @param storeIp The IP of the RDF store.
	 */
	public void setStoreIp(String storeIp) {
		this.storeIp = storeIp;
	}

	/**
	 * Returns the port for SQL data access.
	 * 
	 * @return The port for SQL data access.
	 */
	public int getStoreSqlPort() {
		return storeSqlPort;
	}
	/**
	 * Sets the port for SQL data access.
	 * 
	 * @param storeSqlPort The port for SQL data access.
	 */
	public void setStoreSqlPort(int storeSqlPort) {
		this.storeSqlPort = storeSqlPort;
	}

	/**
	 * Returns the login required for authentication in the RDF store.
	 * 
	 * @return The login required for authentication in the RDF store.
	 */
	public String getSqlLogin() {
		return sqlLogin;
	}
	/**
	 * Sets the login required for authentication in the RDF store.
	 * 
	 * @param sqlLogin The login required for authentication in the RDF store.
	 */
	public void setSqlLogin(String sqlLogin) {
		this.sqlLogin = sqlLogin;
	}
	
	/**
	 * Returns the password required for authentication in the RDF store.
	 * 
	 * @return The password required for authentication in the RDF store.
	 */
	public String getSqlPassword() {
		return sqlPassword;
	}
	/**
	 * Sets the password required for authentication in the RDF store.
	 * 
	 * @param sqlPassword Password required for authentication in the RDF store.
	 */
	public void setSqlPassword(String sqlPassword) {
		this.sqlPassword = sqlPassword;
	}	
	
	/**
	 * Returns graph name where dataset resides. 
	 * 
	 * @return Graph name where dataset resides. 
	 */
	public String getGraph() {
		return graph;
	}
	/**
	 * Sets graph name where dataset resides. 
	 * 
	 * @param graph Graph name where dataset resides. 
	 */
	public void setGraph(String graph) {
		this.graph = graph;
	}		

	/**
	 * Returns the URI prefix of all resources URIs in the dataset.
	 * 
	 * @return The URI prefix of all resources URIs in the dataset.
	 */
	public String getDatasetBase() {
		return datasetBase;
	}
	/**
	 * Sets the URI prefix of all resources URIs in the dataset.
	 * 
	 * @param datasetBase The URI prefix of all resources URIs in the dataset.
	 */
	public void setDatasetBase(String datasetBase) {
		this.datasetBase = datasetBase;
	}

	/**
	 * Returns the path of the ISQL command of the RDF store.
	 * 
	 * @return The path of the ISQL command of the RDF store.
	 */
	public String getIsqlCommandPath() {
		return isqlCommandPath;
	}
	/**
	 * Sets the path of the ISQL command of the RDF store.
	 * 
	 * @param isqlCommandPath The path the ISQL command of the RDF store.
	 */
	public void setIsqlCommandPath(String isqlCommandPath) {
		this.isqlCommandPath = isqlCommandPath;
	}		

	/**
	 * Returns the path of the file containing the ISQL commands for URL 
	 * rewriting in Virtuoso.
	 * 
	 * @return The path of the file containing the ISQL commands for URL 
	 *         rewriting in Virtuoso.
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
	 */
	public void setIsqlCommandsFilename(String isqlCommandsFilename) {
		this.isqlCommandsFilename = isqlCommandsFilename;
	}		

	/**
	 * Returns the path of the default file containing the ISQL commands for URL 
	 * rewriting in Virtuoso. It contains the default mapping for HTML rendering.
	 * 
	 * @return The path of the default file containing the ISQL commands for URL 
	 *         rewriting in Virtuoso.  It contains the default mapping for HTML rendering.
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
	 */
	public void setIsqlCommandsFilenameDefault(String isqlCommandsFilenameDefault) {
		this.isqlCommandsFilenameDefault = isqlCommandsFilenameDefault;
	}		
}
