// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-linked-data-server
// Responsible: ALIADA Consortium
package eu.aliada.linkeddataserversetup.model;


/**
 * Subset information.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
public class Subset {
	/** The URI subset Concept section. */
	private String uriConceptPart; 
	/** The subset description. */
	private String description;
	/** The graph name where the subset resides. */
	private String graph;
	/** The links graph name. */
	private String linksGraph;
	/** Full path of the ISQL commands global file to execute for every subset. */
	private String isqlCommandsSubsetFilename;

	/**
	 * Returns the URI subset Concept section.
	 * 
	 * @return The URI subset Concept section.
	 * @since 2.0
	 */
	public String getUriConceptPart() {
		return uriConceptPart;
	}
	/**
	 * Sets the URI subset Concept section.
	 * 
	 * @param uriConceptPart The URI subset Concept section.
	 * @since 2.0
	 */
	public void setUriConceptPart(final String uriConceptPart) {
		this.uriConceptPart = uriConceptPart;
	}

	/**
	 * Returns the description of the subset. 
	 * 
	 * @return description description of the subset. 
	 * @since 2.0
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Sets the description of the subset. 
	 * 
	 * @param description Description of the subset. 
	 * @since 2.0
	 */
	public void setDescription(final String description) {
		this.description = description;
	}		

	/**
	 * Returns the graph name where the subset resides. 
	 * 
	 * @return Graph name where the subset resides. 
	 * @since 2.0
	 */
	public String getGraph() {
		return graph;
	}
	/**
	 * Sets the graph name where the subset resides. 
	 * 
	 * @param graph Graph name where the subset resides. 
	 * @since 2.0
	 */
	public void setGraph(final String graph) {
		this.graph = graph;
	}		

	/**
	 * Returns the graph name where the subset external links reside. 
	 * 
	 * @return Graph name where the subset external links reside. 
	 * @since 2.0
	 */
	public String getLinksGraph() {
		return linksGraph;
	}
	/**
	 * Sets the graph name where the subset external links reside. 
	 * 
	 * @param graph Graph name where the subset external links reside. 
	 * @since 2.0
	 */
	public void setLinksGraph(final String linksGraph) {
		this.linksGraph = linksGraph;
	}		

	/**
	 * Returns the path of the file containing the ISQL commands for URL 
	 * rewriting in Virtuoso for the subset. 
	 * 
	 * @return The path of the file containing the ISQL commands for URL 
	 *         rewriting in Virtuoso for the subset.
	 * @since 2.0
	 */
	public String getIsqlCommandsSubsetFilename() {
		return isqlCommandsSubsetFilename;
	}
	/**
	 * Sets the path of the file containing the ISQL commands for URL 
	 * rewriting in Virtuoso for the subset. 
	 * 
	 * @param isqlCommandsSubsetFilename The path of the file containing 
	 *        the ISQL commands for URL rewriting in Virtuoso for the subset.
	 * @since 2.0
	 */
	public void setIsqlCommandsSubsetFilename(String isqlCommandsSubsetFilename) {
		this.isqlCommandsSubsetFilename = isqlCommandsSubsetFilename;
	}		

}
