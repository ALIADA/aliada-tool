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


/**
 * Organization information.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Organization {

	/** The organization name. */
	private String name;
	/** The organization id in CKAN. */
	private String id;
	/** The organization title. */
	private String title;
	/** The organization description. */
	private String description;
	/** The organization image URL. */
	private String image_url;
	/** The users that belong to the organization. */
	private ArrayList<Map<String, String>> users = new ArrayList<Map<String, String>>();
	/** The extra attributes for the organization. */
	private ArrayList<Map<String, String>> extras = new ArrayList<Map<String, String>>();
	/** The organization packages/datasets. */
	private ArrayList<Map<String, String>> packages = new ArrayList<Map<String, String>>();

	/**
	 * Constructor.
	 * 
	 * @since 2.0
	 */
	public Organization() {
	}

	/**
	 * Constructor.
	 * 
	 * @param name			the organization name.
	 * @param id			the organization id in CKAN.
	 * @param title			the organization title.
	 * @param description	the organization description.
	 * @param image_url		the organization image URL.
	 * @param homePage		the organization home page.
	 * @since 2.0
	 */
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
	
	/**
	 * Returns the organization name.
	 * 
	 * @return The organization name.
	 * @since 2.0
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Sets the organization name.
	 * 
	 * @param name The organization name.
	 * @since 2.0
	 */
	public void setName(String name) {
		this.name = name.toLowerCase();
	}

	/**
	 * Returns the organization id in CKAN.
	 * 
	 * @return The organization id in CKAN.
	 * @since 2.0
	 */
	public String getId() {
		return this.id;
	}
	/**
	 * Sets the organization id in CKAN.
	 * 
	 * @param id The organization id in CKAN.
	 * @since 2.0
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the organization title.
	 * 
	 * @return The organization title.
	 * @since 2.0
	 */
	public String getTitle() {
		return this.title;
	}
	/**
	 * Sets the organization title.
	 * 
	 * @param title The organization title.
	 * @since 2.0
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Returns the organization description.
	 * 
	 * @return The organization description.
	 * @since 2.0
	 */
	public String getDescription() {
		return this.description;
	}
	/**
	 * Sets the organization description.
	 * 
	 * @param description The organization description.
	 * @since 2.0
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the organization image URL.
	 * 
	 * @return The organization image URL.
	 * @since 2.0
	 */
	public String getImage_url() {
		return this.image_url;
	}
	/**
	 * Sets the organization image URL.
	 * 
	 * @param image_url The organization image URL.
	 * @since 2.0
	 */
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	/**
	 * Returns the users that belong to the organization.
	 * 
	 * @return The users that belong to the organization.
	 * @since 2.0
	 */
	public ArrayList<Map<String, String>> getUsers() {
		return this.users;
	}
	/**
	 * Adds a user that belong to the organization.
	 * 
	 * @param user The user to add.
	 * @since 2.0
	 */
	public void addUser(Map<String, String> user) {
		users.add(user);
	}

	/**
	 * Returns the extra attributes for the organization.
	 * 
	 * @return The extra attributes for the organization.
	 * @since 2.0
	 */
	public ArrayList<Map<String, String>> getExtras() {
		return this.extras;
	}
	/**
	 * Adds a extra attribute for the organization.
	 * 
	 * @param extra The extra attribute to add.
	 * @since 2.0
	 */
	public void addExtra(Map<String, String> extra) {
		extras.add(extra);
	}

	/**
	 * Returns the organization packages/datasets.
	 * 
	 * @return The organization packages/datasets.
	 * @since 2.0
	 */
	public ArrayList<Map<String, String>> getPackages() {
		return this.packages;
	}
	/**
	 * Adds a package/dataset to the organization.
	 * 
	 * @param pack The package/dataset to add.
	 * @since 2.0
	 */
	public void addPackage(Map<String, String> pack) {
		packages.add(pack);
	}

	////////////////////////////////////////////
	/**
	 * Adds a user that belong to the organization.
	 * 
	 * @param username The name of the user to add.
	 * @param capacity The capacity of the user to add.
	 * @since 2.0
	 */
	public void setUser(String username, String capacity) {
		Map<String, String> user = new HashMap<String, String>();
		user.put("name", username);		
		user.put("capacity", capacity);		
		this.users.add(user);
	}
	////////////////////////////////////////////

	////////////////////////////////////////////
	/**
	 * Adds the extra attribute of the organization home page.
	 * 
	 * @param homePage The organization home page.
	 * @since 2.0
	 */
	public void setHomePage(String homePage) {
		Map<String, String> extra = new HashMap<String, String>();
		extra.put("key", "homePage");		
		extra.put("value", homePage);		
		this.extras.add(extra);
	}
	////////////////////////////////////////////
}
