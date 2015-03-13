// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Resource information.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Resource {
	/** The resource name. */
	private String name;
	/** The package id in CKAN. */
	@JsonProperty("package_id")
	private String packageId;
	/** The resource description. */
	private String description;
	/** The resource format. */
	private String format;
	/** The resource URL. */
	private String url;
	/** The resource type. */
	@JsonProperty("resource_type")
	private String type;

	/**
	 * Constructor.
	 * 
	 * @since 2.0
	 */
	public Resource() {
	}

	/**
	 * Constructor.
	 * 
	 * @param packageId		the package id in CKAN.
	 * @param name			the resource name.
	 * @param description	the resource description.
	 * @param format		the resource format.
	 * @param url			the resource URL.
	 * @param type			the resource type.
	 * @since 2.0
	 */
	public Resource(String packageId, String name, String description, String format, String url, String type)
	{
		this.name = name;
		this.packageId = packageId;
		this.description = description;
		this.format = format;
		this.url = url;
		this.type = type;
	}
	
	/**
	 * Returns the resource name.
	 * 
	 * @return The resource name.
	 * @since 2.0
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Sets the resource name.
	 * 
	 * @param name The resource name.
	 * @since 2.0
	 */
	public void setName(String name) {
		this.name = name.toLowerCase();
	}

	/**
	 * Returns the package id in CKAN.
	 * 
	 * @return The package id in CKAN.
	 * @since 2.0
	 */
	public String getPackageId() {
		return this.packageId;
	}
	/**
	 * Sets the package id in CKAN.
	 * 
	 * @param packageId The package id in CKAN.
	 * @since 2.0
	 */
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	/**
	 * Returns the resource description.
	 * 
	 * @return The resource description.
	 * @since 2.0
	 */
	public String getDescription() {
		return this.description;
	}
	/**
	 * Sets the resource description.
	 * 
	 * @param description The resource description.
	 * @since 2.0
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Returns the resource format.
	 * 
	 * @return The resource format.
	 * @since 2.0
	 */
	public String getFormat() {
		return this.format;
	}
	/**
	 * Sets the resource format.
	 * 
	 * @param format The resource format.
	 * @since 2.0
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * Returns the resource URL.
	 * 
	 * @return The resource URL.
	 * @since 2.0
	 */
	public String getUrl() {
		return this.url;
	}
	/**
	 * Sets the resource URL.
	 * 
	 * @param url The resource URL.
	 * @since 2.0
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Returns the resource type.
	 * 
	 * @return The resource type.
	 * @since 2.0
	 */
	public String getType() {
		return this.type;
	}
	/**
	 * Sets the resource type.
	 * 
	 * @param type The resource type.
	 * @since 2.0
	 */
	public void setType(String type) {
		this.type = type;
	}
}
