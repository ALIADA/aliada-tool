// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.model;


import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Error response of CKAN API function.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class CKANResponseError {

	/** Contains the error message. */
	private String message;
	/** Contains the error message. */
	private String[] name;

	public String getMessage() { return message; }
	public void setMessage(String message) { this.message = message; }

	public String[] getName() {
		return this.name;
	}
	public void setName(String[] name) {
		this.name = name;
	}
}
