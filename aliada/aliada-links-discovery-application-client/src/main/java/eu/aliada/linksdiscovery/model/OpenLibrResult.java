// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery-application-client
// Responsible: ALIADA Consortium
package eu.aliada.linksdiscovery.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class to be used for the list of results returned by Open Library.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenLibrResult {
	/** Contains the Id of the resource in Open Library. */
	private String key;

	/**
	 * Constructor.
	 * 
	 * @since 2.0
	 */
	public OpenLibrResult() {
	}

	/**
	 * Returns the Id of the resource in Open Library.
	 * 
	 * @return The Id of the resource in Open Library.
	 * @since 2.0
	 */
	public String getKey() { return key; }
	/**
	 * Sets Id of the resource in VIAF.
	 * 
	 * @param viafid The Id of the resource in Open Library.
	 * @since 2.0
	 */
	public void setKey(final String key) { this.key = key; }
}

