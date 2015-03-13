// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery-application-client
// Responsible: ALIADA Consortium
package eu.aliada.linksdiscovery.model;


import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Search response of LOBID API function.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class LOBIDSearchResponse {

	/** Contains the title of the organisation/resource that is being searched for. */
	private String label;
	/** Contains the URI of the found resource. */
	private String value;

	/**
	 * Returns the title of the organisation/resource that is being searched for.
	 * 
	 * @return The title of the organisation/resource that is being searched for.
	 * @since 2.0
	 */
	public String getLabel() { return label; }
	/**
	 * Sets the title of the organisation/resource that is being searched for.
	 * 
	 * @param label The title of the organisation/resource that is being searched for.
	 * @since 2.0
	 */
	public void setLabel(String label) { this.label = label; }

	/**
	 * Returns the URI of the found resource.
	 * 
	 * @return The URI of the found resource.
	 * @since 2.0
	 */
	public String getValue() {
		return this.value;
	}
	/**
	 * Sets the URI of the found resource.
	 * 
	 * @param value The URI of the found resource.
	 * @since 2.0
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
