// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.model;


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
	/** Number of triples in graph. **/
	private int graphNumTriples;
	/** Number of triples in linksGraph. **/
	private int linksGraphNumTriples;

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
	 * Returns the number of triples of the subset in graph.
	 * 
	 * @return The number of triples of the subset in graph.
	 * @since 2.0
	 */
	public int getGraphNumTriples(){
		return graphNumTriples; 
	}
	/**
	 * Sets the number of triples of the subset in graph.
	 * 
	 * @param graphNumTriples The number of triples of the subset in graph.
	 * @since 2.0
	 */
	public void setGraphNumTriples(final int graphNumTriples) {
		this.graphNumTriples = graphNumTriples;
	}

	/**
	 * Returns the number of triples of the subset in linksGraph.
	 * 
	 * @return The number of triples of the subset in linksGraph.
	 * @since 2.0
	 */
	public int getLinksGraphNumTriples(){
		return linksGraphNumTriples; 
	}
	/**
	 * Sets the number of triples of the subset in linksGraph.
	 * 
	 * @param graphNumTriples The number of triples of the subset in linksGraph.
	 * @since 2.0
	 */
	public void setLinksGraphNumTriples(final int linksGraphNumTriples) {
		this.linksGraphNumTriples = linksGraphNumTriples;
	}
}
