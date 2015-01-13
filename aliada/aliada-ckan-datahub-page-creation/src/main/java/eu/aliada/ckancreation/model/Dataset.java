// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Dataset {

	private String name;
	private String id;
	private String author;
	private String notes;
	@JsonProperty("url")
	private String sourceUrl;
	@JsonProperty("owner_org")
	private String ownerOrg;
	@JsonProperty("license_id")
	private String licenseId;
	private String state;
	

	public Dataset() {
	}

	public Dataset(String name, String id, String author, String notes, String sourceUrl, 
			String ownerOrg, String licenseId)
	{
		this.name = name.toLowerCase();
		this.id = id;
		this.author = author;
		this.notes = notes;
		this.sourceUrl = sourceUrl;
		this.ownerOrg = ownerOrg;
		this.licenseId = licenseId;
	}

	public Dataset(String name, String id, String author, String notes, String sourceUrl, 
			String ownerOrg, String licenseId, String state)
	{
		this.name = name.toLowerCase();
		this.id = id;
		this.author = author;
		this.notes = notes;
		this.sourceUrl = sourceUrl;
		this.ownerOrg = ownerOrg;
		this.licenseId = licenseId;
		this.state = state;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name.toLowerCase();
	}

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getAuthor() {
		return this.author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getNotes() {
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getSourceUrl() {
		return this.sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getOwnerOrg() {
		return this.ownerOrg;
	}
	public void setOwnerOrg(String ownerOrg) {
		this.ownerOrg = ownerOrg;
	}
	
	public String getLicenseId() {
		return this.licenseId;
	}
	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public String getState() {
		return this.state;
	}
	public void setState(String state) {
		this.state = state;
	}

}
