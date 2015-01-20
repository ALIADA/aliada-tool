// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.model;



public class Linkset {

	/** The name of the linkset. */
	private String name;
	/** The name of the target dataset to link. */
	private String targetName;
	/** The SPARQL endpoint URL. */
	private String SPARQLendpoint;
	/** Number of triples. **/
	private int triples;
	/** RDF predicate for the links. **/
	private String linkPredicate;
	

	public Linkset() {
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getTargetName() {
		return this.targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	/**
	 * Returns the URI of the SPARQL endpoint of the dataset.
	 * 
	 * @return The URI of the SPARQL endpoint of the dataset.
	 * @since 2.0
	 */
	public String getSPARQLEndpoint() {
		return SPARQLendpoint;
	}
	/**
	 * Sets the URI of the SPARQL endpoint of the dataset.
	 * 
	 * @param SPARQLendpoint The URI of the SPARQL endpoint of the dataset.
	 * @since 2.0
	 */
	public void setSPARQLEndpoint(final String SPARQLendpoint) {
		this.SPARQLendpoint = SPARQLendpoint;
	}

	public int getTriples() {
		return this.triples;
	}
	public void setTriples(int triples) {
		this.triples = triples;
	}

	public String getLinkPredicate() {
		return this.linkPredicate;
	}
	public void setLinkPredicate(String linkPredicate) {
		this.linkPredicate = linkPredicate;
	}

}
