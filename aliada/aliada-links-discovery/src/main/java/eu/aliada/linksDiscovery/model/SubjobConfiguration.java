// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.linksDiscovery.model;

/**
 * RDF-izer Job entity.
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class SubjobConfiguration {
    private String linkingXMLConfigFilename;
    private String ds;
    private int linkingNumThreads;
    private boolean linkingReload;
    
    /**
     * Returns the name of the linking XML configuration file.
     * 
     * @return The name of the linking XML configuration file.
     */
	public String getLinkingXMLConfigFilename() {
		return linkingXMLConfigFilename;
	}

	/**
	 * Sets the name of the linking XML configuration file.
	 * 
	 * @param linkingXMLConfigFilename The name of the linking XML configuration file.
	 */
	public void setLinkingXMLConfigFilename(String linkingXMLConfigFilename) {
		this.linkingXMLConfigFilename = linkingXMLConfigFilename;
	}

    /**
     * Returns the name of ALIADA datasource in the linking XML configuration file.
     * 
     * @return The name of ALIADA datasource in the linking XML configuration file.
     */
	public String getDs() {
		return ds;
	}

	/**
	 * Sets the name of ALIADA datasource in the linking XML configuration file.
	 * 
	 * @param ds The name of ALIADA datasource in the linking XML configuration file.
	 */
	public void setDs(String ds) {
		this.ds = ds;
	}

	/**
     * Returns the number of threads for the SILK process.
     * 
     * @return The number of threads for the SILK process.
     */
	public int geLlinkingNumThreads() {
		return linkingNumThreads;
	}

	/**
	 * Sets the number of threads for the SILK process.
	 * 
	 * @param linkingNumThreads The number of threads for the SILK process.
	 */
	public void setLinkingNumThreads(int linkingNumThreads) {
		this.linkingNumThreads = linkingNumThreads;
	}

    /**
     * Returns the reload parameter for SILK.
     *  (Specifies if the entity cache is to be reloaded before executing the matching. Default: true) 
     * 
     * @return The reload parameter for SILK.
     */
	public boolean getLinkingReload() {
		return linkingReload;
	}

	/**
	 * Sets the reload parameter for SILK.
     *  (Specifies if the entity cache is to be reloaded before executing the matching. Default: true) 
	 * 
	 * @param linkingReload The reload parameter for SILK.
	 */
	public void setLinkingReload(boolean linkingReload) {
		this.linkingReload = linkingReload;
	}
	
}