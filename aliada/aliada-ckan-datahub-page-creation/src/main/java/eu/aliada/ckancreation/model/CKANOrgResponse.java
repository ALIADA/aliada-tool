// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.model;


import javax.xml.bind.annotation.XmlRootElement;

/**
 * Response to "Update Organization" CKAN API function.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
@XmlRootElement
public class CKANOrgResponse {

	/** Help text for the API function. */
	private String help;
	/** API function result. */
	private Organization result;
	/** It indicates if the API function was executed successfully or not. */
	private String success;
	/** If success=false, this field indicates the exception. */
	private CKANResponseError error;

	/**
	 * Constructor.
	 * 
	 * @since 2.0
	 */
	public CKANOrgResponse() {
	}

	/**
	 * Returns the help text for the API function.
	 * 
	 * @return The help text for the API function.
	 * @since 2.0
	 */
	public String getHelp() {
		return this.help;
	}
	/**
	 * Sets the help text for the API function.
	 * 
	 * @param help The help text for the API function.
	 * @since 2.0
	 */
	public void setHelp(String help) {
		this.help = help;
	}

	/**
	 * Returns the API function result
	 * 
	 * @return The {@link eu.aliada.ckancreation.model.Organization} in the API function result.
	 * @since 2.0
	 */
	public Organization getResult() {
		return this.result;
	}
	/**
	 * Sets the API function result.
	 * 
	 * @param result The {@link eu.aliada.ckancreation.model.Organization} in API function result.
	 * @since 2.0
	 */
	public void setResult(Organization result) {
		this.result = result;
	}

	/**
	 * Returns the message that indicates if the API function was executed successfully or not.
	 * 
	 * @return The message that indicates if the API function was executed successfully or not.
	 * @since 2.0
	 */
	public String getSuccess() {
		return this.success;
	}
	/**
	 * Sets the message that indicates if the API function was executed successfully or not.
	 * 
	 * @param success The message that indicates if the API function was executed successfully or not.
	 * @since 2.0
	 */
	public void setSuccess(String success) {
		this.success = success;
	}

	/**
	 * Returns the message that indicates the exception, if an error occurred.
	 * 
	 * @return The message that indicates the exception, if an error occurred.
	 * @since 2.0
	 */
	public CKANResponseError getError() {
		return this.error;
	}
	/**
	 * Sets the message that indicates the exception, if an error occurred.
	 * 
	 * @param error The message that indicates the exception, if an error occurred.
	 * @since 2.0
	 */
	public void setError(CKANResponseError error) {
		this.error = error;
	}
}
