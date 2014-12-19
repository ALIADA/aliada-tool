// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Organization {

	private String name;
	private String id;
	private String title;
	private String description;
	private String image_url;
	private ArrayList<Map<String, String>> users = new ArrayList<Map<String, String>>();
	private ArrayList<Map<String, String>> extras = new ArrayList<Map<String, String>>();
	private ArrayList<Map<String, String>> packages = new ArrayList<Map<String, String>>();

	public Organization() {
	}

	public Organization(String name, String id, String title, String description, String image_url, String homePage)
	{
		this.name = name.toLowerCase();
		this.id = id;
		this.title = title;
		this.description = description;
		this.image_url = image_url;
		Map<String, String> extra = new HashMap<String, String>();
		extra.put("key", "homePage");		
		extra.put("value", homePage);		
		this.extras.add(extra);
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

	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage_url() {
		return this.image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public ArrayList<Map<String, String>> getUsers() {
		return this.users;
	}
	public void addUser(Map<String, String> user) {
		users.add(user);
	}

	public ArrayList<Map<String, String>> getExtras() {
		return this.extras;
	}
	public void addExtra(Map<String, String> extra) {
		extras.add(extra);
	}

	public ArrayList<Map<String, String>> getPackages() {
		return this.packages;
	}
	public void addPackage(Map<String, String> pack) {
		packages.add(pack);
	}

	////////////////////////////////////////////
	public void setUser(String username, String capacity) {
		Map<String, String> user = new HashMap<String, String>();
		user.put("name", username);		
		user.put("capacity", capacity);		
		this.users.add(user);
	}
	////////////////////////////////////////////

	////////////////////////////////////////////
	public void setHomePage(String homePage) {
		Map<String, String> extra = new HashMap<String, String>();
		extra.put("key", "homePage");		
		extra.put("value", homePage);		
		this.extras.add(extra);
	}
	////////////////////////////////////////////
}
