// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.model;

/**
 * @author iosa
 * @since 1.0
 */
public class User{
    private String username;
    private String password;
    private String email;
    private String role;
    private String type;   
    private String organisation;
    
    /**
     * @return Returns the username.
     * @exception
     * @since 1.0
     */
    public String getUsername() {
        return username;
    }
    /**
     * @param username The username to set.
     * @exception
     * @since 1.0
     */
    public void setUsername(final String username) {
        this.username = username;
    }
    /**
     * @return Returns the password.
     * @exception
     * @since 1.0
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password The password to set.
     * @exception
     * @since 1.0
     */
    public void setPassword(final String password) {
        this.password = password;
    }
    /**
     * @return Returns the email.
     * @exception
     * @since 1.0
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email The email to set.
     * @exception
     * @since 1.0
     */
    public void setEmail(final String email) {
        this.email = email;
    }
    /**
     * @return Returns the role.
     * @exception
     * @since 1.0
     */
    public String getRole() {
        return role;
    }
    /**
     * @param role The role to set.
     * @exception
     * @since 1.0
     */
    public void setRole(final String role) {
        this.role = role;
    }
    /**
     * @return Returns the type.
     * @exception
     * @since 1.0
     */
    public String getType() {
        return type;
    }
    /**
     * @param type The type to set.
     * @exception
     * @since 1.0
     */
    public void setType(final String type) {
        this.type = type;
    }
    /**
     * @return Returns the organisation.
     * @exception
     * @since 1.0
     */
    public String getOrganisation() {
        return organisation;
    }
    /**
     * @param organisation The organisation to set.
     * @exception
     * @since 1.0
     */
    public void setOrganisation(final String organisation) {
        this.organisation = organisation;
    }

}
