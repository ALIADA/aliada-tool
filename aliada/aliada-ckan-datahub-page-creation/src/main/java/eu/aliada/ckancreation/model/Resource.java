// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Resource {

	private String name;
	@JsonProperty("package_id")
	private String packageId;
	private String description;
	private String format;
	private String url;
	@JsonProperty("resource_type")
	private String type;

	public Resource() {
	}

	public Resource(String packageId, String name, String description, String format, String url, String type)
	{
		this.name = name;
		this.packageId = packageId;
		this.description = description;
		this.format = format;
		this.url = url;
		this.type = type;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name.toLowerCase();
	}

	public String getPackageId() {
		return this.packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getFormat() {
		return this.format;
	}
	public void setFormat(String format) {
		this.format = format;
	}

	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
