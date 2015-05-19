// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-shared
// Responsible: ALIADA Consortium

package eu.aliada.shared.rdfstore;

import java.util.ArrayList;

/**
 * Ambiguous links for a given resource.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
public class AmbiguousLink {
	/** Source URI of the ambiguous links. */
	private String sourceURI;
	/** List of target URI-s of the ambiguous links. */
	private ArrayList<String> targetURI = new ArrayList<String>();
   
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
	 * Returns the target URI-s of the ambiguous links.
	 * 
	 * @return The target URI-s of the ambiguous links.
	 * @since 1.0
	 */
	public String[] getTargetURI() {
		return this.targetURI.toArray(new String[]{});
	}
	/**
	 * Adds a target URI-s of the ambiguous links.
	 * 
	 * @param subjob The target URI-s of the ambiguous link to add.
	 * @since 1.0
	 */
	public void addTargetURI(final String link) {
		targetURI.add(link);
	}

}
