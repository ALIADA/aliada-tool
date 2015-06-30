// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery-application-client
// Responsible: ALIADA Consortium
package eu.aliada.linksdiscovery.model;


import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import eu.aliada.linksdiscovery.model.OpenLibrResult;

/**
 * Search response of Open library API function.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenLibrSearchResponse {
	/** Contains the paging start index. */
	private String start;
	/** Contains the number of results. */
	private String numFound;
	/** Contains the results of the query. */
	public OpenLibrResult[] docs;

	/**
	 * Returns the paging start index.
	 * 
	 * @return The paging start index.
	 * @since 2.0
	 */
	public String getStart() { return start; }
	/**
	 * Sets the paging start index.
	 * 
	 * @param start The paging start index.
	 * @since 2.0
	 */
	public void setStart(final String start) { this.start = start; }

	/**
	 * Returns the number of results.
	 * 
	 * @return The number of results.
	 * @since 2.0
	 */
	public String getNumFound() { return numFound; }
	/**
	 * Sets the number of results.
	 * 
	 * @param start The number of results.
	 * @since 2.0
	 */
	public void setNumFound(final String numFound) { this.numFound = numFound; }

	/**
	 * Returns the results of the query.
	 * 
	 * @return The results of the query.
	 * @since 2.0
	 */
	public OpenLibrResult[] getDocs() {
		return this.docs;
	}
	/**
	 * Sets the results of the query.
	 * 
	 * @param results The results of the query.
	 * @since 2.0
	 */
	public void setDocs(final OpenLibrResult[] docs) {
		this.docs = docs;
	}
}
