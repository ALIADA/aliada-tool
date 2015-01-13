// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.model;


/**
 * Dump file info.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
public class DumpFileInfo {
	/** Graph dump file path. */
	private String dumpFilePath;
	/** Graph dump file URL. */
	private String dumpFileUrl;
    
	/**
	 * Returns the file path of the graph dump.
	 * 
	 * @return The file path of the graph dump.
	 * @since 1.0
	 */
	public String getDumpFilePath() {
		return dumpFilePath;
	}
	/**
	 * Sets the file path of the graph dump.
	 * 
	 * @param dumpFilePath The file path of the graph dump.
	 * @since 1.0
	 */
	public void setDumpFilePath(final String dumpFilePath) {
		this.dumpFilePath = dumpFilePath;
	}

	/**
	 * Returns the file URL of the graph dump.
	 * 
	 * @return The file URL of the graph dump.
	 * @since 1.0
	 */
	public String getDumpFileUrl() {
		return dumpFileUrl;
	}
	/**
	 * Sets the file URL of the graph dump.
	 * 
	 * @param dumpFileUrl The file URL of the graph dump.
	 * @since 1.0
	 */
	public void setDumpFileUrl(final String dumpFileUrl) {
		this.dumpFileUrl = dumpFileUrl;
	}
}
