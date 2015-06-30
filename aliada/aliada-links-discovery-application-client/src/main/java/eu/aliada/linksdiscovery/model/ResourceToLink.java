// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery-application-client
// Responsible: ALIADA Consortium
package eu.aliada.linksdiscovery.model;


/**
 * Resources and text to search for links in external datasets.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
public class ResourceToLink {

	/** Contains the text to search for. */
	private String text;
	/** Contains the URI of the resource to link. */
	private String resURI;

	/**
	 * Constructor.
	 *
	 * @param text 		The text to search for.
	 * @param resURI 	The URI of the resource to link.
	 * @since 2.0
	 */
	public ResourceToLink(final String text, final String resURI) {
		this.text = text;
		this.resURI = resURI;
	} 

	/**
	 * Returns the text to search for.
	 * 
	 * @return The text to search for.
	 * @since 2.0
	 */
	public String getText() { return text; }
	/**
	 * Sets the text to search for.
	 * 
	 * @param text The text to search for.
	 * @since 2.0
	 */
	public void setText(final String text) { this.text = text; }

	/**
	 * Returns the URI of the resource to link.
	 * 
	 * @return The URI of the resource to link.
	 * @since 2.0
	 */
	public String getResURI() {
		return this.resURI;
	}
	/**
	 * Sets the URI of the resource to link.
	 * 
	 * @param resURI The URI of the resource to link.
	 * @since 2.0
	 */
	public void setResURI(final String resURI) {
		this.resURI = resURI;
	}
}
