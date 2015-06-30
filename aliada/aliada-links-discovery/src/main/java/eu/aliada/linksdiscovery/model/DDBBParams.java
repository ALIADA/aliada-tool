// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.linksdiscovery.model;

/**
 * Links discovery DDBB parameters.
 *
 * @author Idoia Murua
 * @since 1.0
 */
public class DDBBParams {
	/** DB user name */
	private String username;
	/** DB password */
	private String password;
	/** DB driver class name */
	private String driverClassName;
	/** DB URL */
	private String url;

	/**
	 * Returns the username for the DDBB.
	 *
	 * @return The username for the DDBB.
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * Sets the username for the DDBB.
	 * 
	 * @param username The username for the DDBB.
	 */
	public void setUsername(final String username) {
		this.username = username;
	}

	/**
	 * Returns the password for the DDBB.
	 * 
	 * @return The password for the DDBB.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Sets the password for the DDBB.
	 * 
	 * @param username The password for the DDBB.
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * Returns the driverClassName for the DDBB.
	 * 
	 * @return The driverClassName for the DDBB.
	 */
	public String getDriverClassName() {
		return driverClassName;
	}
	/**
	 * Sets the driverClassName for the DDBB.
	 * 
	 * @param driverClassName The driverClassName for the DDBB.
	 */
	public void setDriverClassName(final String driverClassName) {
		this.driverClassName = driverClassName;
	}

	/**
	 * Returns the url for the DDBB.
	 * 
	 * @return The url for the DDBB.
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * Sets the url for the DDBB.
	 * 
	 * @param url The url for the DDBB.
	 */
	public void setUrl(final String url) {
		this.url = url;
	}
}
