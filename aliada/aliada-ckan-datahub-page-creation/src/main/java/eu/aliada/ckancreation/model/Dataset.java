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
	private String url;
	@JsonProperty("owner_org")
	private String ownerOrg;
	

	public Dataset() {
	}

	public Dataset(String name, String id, String author, String notes, String url, String ownerOrg)
	{
		this.name = name.toLowerCase();
		this.id = id;
		this.author = author;
		this.notes = notes;
		this.url = url;
		this.ownerOrg = ownerOrg;
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

	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getOwnerOrg() {
		return this.ownerOrg;
	}
	public void setOwnerOrg(String ownerOrg) {
		this.ownerOrg = ownerOrg;
	}
	
}
