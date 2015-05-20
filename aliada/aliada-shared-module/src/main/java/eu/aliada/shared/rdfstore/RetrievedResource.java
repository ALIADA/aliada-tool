// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-shared
// Responsible: ALIADA Consortium

package eu.aliada.shared.rdfstore;

/**
 * Retrieved Resource.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
public class RetrievedResource {
	/** Resource URI. */
	private String resourceURI;
	/** Resource name. */
	private String name;
   
	/**
	 * Constructor.
	 * 
	 * @param resourceURI	the resource URI.
	 * @param name			the resource name.
	 * @since 2.0
	 */
	public RetrievedResource(final String resourceURI, final String name)
	{
		this.resourceURI = resourceURI;
		this.name = name;
	}
	
	/**
	 * Returns the resource URI.
	 * 
	 * @return The resource URI.
	 * @since 2.0
	 */
	public String getResourceURI() {
		return resourceURI;
	}
	/**
	 * Sets the resource URI.
	 * 
	 * @param resourceURI The resource URI.
	 * @since 2.0
	 */
	public void setSourceURI(final String resourceURI) {
		this.resourceURI = resourceURI;
	}

	/**
	 * Returns the resource name.
	 * 
	 * @return The resource name.
	 * @since 2.0
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name URI.
	 * 
	 * @param name The name URI.
	 * @since 2.0
	 */
	public void setName(final String name) {
		this.name = name;
	}
}
