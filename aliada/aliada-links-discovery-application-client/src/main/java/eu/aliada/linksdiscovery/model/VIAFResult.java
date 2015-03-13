// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery-application-client
// Responsible: ALIADA Consortium
package eu.aliada.linksdiscovery.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class to be used for the list of results returned by VIAF API.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VIAFResult {
	/** Contains the Id of the resource in VIAF. */
	private String viafid;

	/**
	 * Constructor.
	 * 
	 * @since 2.0
	 */
	public VIAFResult() {
	}

	/**
	 * Returns the Id of the resource in VIAF.
	 * 
	 * @return The Id of the resource in VIAF.
	 * @since 2.0
	 */
	public String getViafid() { return viafid; }
	/**
	 * Sets Id of the resource in VIAF.
	 * 
	 * @param viafid The Id of the resource in VIAF.
	 * @since 2.0
	 */
	public void setViafid(String viafid) { this.viafid = viafid; }
}

