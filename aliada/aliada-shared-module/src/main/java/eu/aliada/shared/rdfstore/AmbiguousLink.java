// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-shared
// Responsible: ALIADA Consortium

package eu.aliada.shared.rdfstore;

import java.util.ArrayList;
import com.hp.hpl.jena.graph.Triple;

/**
 * Ambiguous links for a given resource.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
public class AmbiguousLink {
	/** Source URI of the ambiguous links. */
	private String sourceURI;
	/** List of triples that contains the ambiguous links. */
	private ArrayList<Triple> linksList = new ArrayList<Triple>();
   
   
	/**
	 * Returns the source URI of the ambiguous links.
	 * 
	 * @return The source URI of the ambiguous links.
	 * @since 2.0
	 */
	public String getSourceURI() {
		return sourceURI;
	}
	/**
	 * Sets the source URI of the ambiguous links.
	 * 
	 * @param id The source URI of the ambiguous links.
	 * @since 2.0
	 */
	public void setSourceURI(final String sourceURI) {
		this.sourceURI = sourceURI;
	}

	/**
	 * Returns the triples that contains the ambiguous links.
	 * 
	 * @return The triples that contains the ambiguous links.
	 * @since 1.0
	 */
	public Triple[] getLinks() {
		return this.linksList.toArray(new Triple[]{});
	}
	/**
	 * Adds one of the triples of the ambiguous links.
	 * 
	 * @param link One of the triples of the ambiguous link to add.
	 * @since 1.0
	 */
	public void addLink(final Triple link) {
		linksList.add(link);
	}
}
