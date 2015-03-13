// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery-application-client
// Responsible: ALIADA Consortium
package eu.aliada.linksdiscovery.model;


import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import eu.aliada.linksdiscovery.model.VIAFResult;

/**
 * Search response of LOBID API function.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class VIAFSearchResponse {
	/** Contains the text to search for and sent to the API. */
	private String query;
	/** Contains the results of the query. */
	public VIAFResult[] result;

	/**
	 * Returns the text to search for and sent to the API.
	 * 
	 * @return The text to search for and sent to the API.
	 * @since 2.0
	 */
	public String getQuery() { return query; }
	/**
	 * Sets the text to search for and sent to the API.
	 * 
	 * @param query The text to search for and sent to the API.
	 * @since 2.0
	 */
	public void setQuery(String query) { this.query = query; }

	/**
	 * Returns the results of the query.
	 * 
	 * @return The results of the query.
	 * @since 2.0
	 */
	public VIAFResult[] getResult() {
		return this.result;
	}
	/**
	 * Sets the results of the query.
	 * 
	 * @param results The results of the query.
	 * @since 2.0
	 */
	public void setResult(VIAFResult[] result) {
		this.result = result;
	}
}
