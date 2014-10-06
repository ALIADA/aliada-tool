// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.linksDiscovery.model;

/**
 * Links discovery subjob configuration.
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class SubjobConfiguration {
	/** Subjob name. */
	private String name;
	/** Name of linking XML configuration file for the SILK process. */
	private String linkingXMLConfigFilename;
	/** Name of ALIADA datasource in the linking XML configuration file. */
	private String dataSource;
	/** Number of threads for the SILK process. */
	private int linkingNumThreads;
	/** Reload parameter for SILK. */
	private boolean linkingReload;
   
	/**
	 * Returns the name of the subjob.
	 * 
	 * @return The name of the subjob.
	 * @since 1.0
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name of the subjob.
	 * 
	 * @param name The name of the subjob.
	 * @since 1.0
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the linking XML configuration file.
	 * 
	 * @return The name of the linking XML configuration file.
	 * @since 1.0
	 */
	public String getLinkingXMLConfigFilename() {
		return linkingXMLConfigFilename;
	}
	/**
	 * Sets the name of the linking XML configuration file.
	 * 
	 * @param linkingXMLConfigFilename The name of the linking XML configuration file.
	 * @since 1.0
	 */
	public void setLinkingXMLConfigFilename(final String linkingXMLConfigFilename) {
		this.linkingXMLConfigFilename = linkingXMLConfigFilename;
	}

	/**
	 * Returns the name of ALIADA datasource in the linking XML configuration file.
	 * 
	 * @return The name of ALIADA datasource in the linking XML configuration file.
	 * @since 1.0
	 */
	public String getDs() {
		return dataSource;
	}
	/**
	 * Sets the name of ALIADA datasource in the linking XML configuration file.
	 * 
	 * @param ds The name of ALIADA datasource in the linking XML configuration file.
	 * @since 1.0
	 */
	public void setDs(final String dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Returns the number of threads for the SILK process.
	 * 
	 * @return The number of threads for the SILK process.
	 * @since 1.0
	 */
	public int getLinkingNumThreads() {
		return linkingNumThreads;
	}
	/**
	 * Sets the number of threads for the SILK process.
	 * 
	 * @param linkingNumThreads The number of threads for the SILK process.
	 * @since 1.0
	 */
	public void setLinkingNumThreads(final int linkingNumThreads) {
		this.linkingNumThreads = linkingNumThreads;
	}

	/**
	 * Returns the reload parameter for SILK.
	 * Specifies if the entity cache is to be reloaded before 
	 * executing the matching. Default: true. 
	 * 
	 * @return The reload parameter for SILK.
	 * @since 1.0
	 */
	public boolean isLinkingReload() {
		return linkingReload;
	}
	/**
	 * Sets the reload parameter for SILK.
	 * Specifies if the entity cache is to be reloaded before executing 
	 * the matching. Default: true. 
	 * 
	 * @param linkingReload The reload parameter for SILK.
	 * @since 1.0
	 */
	public void setLinkingReload(final boolean linkingReload) {
		this.linkingReload = linkingReload;
	}
}
