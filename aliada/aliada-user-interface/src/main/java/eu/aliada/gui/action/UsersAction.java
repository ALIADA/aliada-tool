// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.jasypt.util.password.StrongPasswordEncryptor;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.model.User;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**@author xabi
 * @version $Revision: 1.1 $, $Date: 2015/03/23 15:20:54 $
 * @since 1.0 */
public class UsersAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<User> users;
    private HashMap<Integer, String> roles;
    private HashMap<Integer, String> organisations;
    private HashMap<Integer, String> userTypes;
    private String selectedUser;
    private boolean showAddForm;
    private boolean showEditForm;
    private boolean areUsers;
    private String usernameForm;
    private String passwordForm;
    private String repeatPasswordForm;
    private String newPasswordForm;
    private String repeatNewPasswordForm;
    private String emailForm;
    private int roleForm;
    private int typeForm; 
    private int organisationForm;
    
    private final Log logger = new Log(UsersAction.class);
    private ResourceBundle defaults = ResourceBundle.getBundle("defaultValues", getLocale());
    
    /** Gets the users from the DB.
     * @return String */
    public String getUsersDb() {
    	Methods methods = new Methods();
        ServletActionContext.getRequest().getSession().removeAttribute("userToUpdate");    
        roles = methods.getRolesDb();
        userTypes = methods.getUsersTypesDb();
        organisations = methods.getOrganisationsDb();
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from aliada.user");
            users = new ArrayList<User>();
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("user_name"));
                user.setPassword(rs.getString("user_password"));
                user.setEmail(rs.getString("user_email"));
                user.setType(methods.getUserType(rs.getInt("user_type_code")));
                user.setRole(methods.getRoleCode(rs.getInt("user_role_code")));
                user.setOrganisation(methods.getOrganisationName(rs.getInt("organisationId")));
                users.add(user);
            }
            statement.close();
            connection.close();
            if (users.isEmpty()) {
                setAreUsers(false);
            } else {
                setAreUsers(true);
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        return SUCCESS;
    }
    
    /** Add an user to the DB.
     * @return String */
    public String addUser() {
    	HttpSession session = ServletActionContext.getRequest().getSession();
    	User us = new User();
    	
    	// Save fields
    	us.setUsername(this.usernameForm);
    	us.setPassword(this.passwordForm);
    	us.setRepeatPassword(this.repeatPasswordForm);
    	us.setEmail(this.emailForm);
    	us.setRole(String.valueOf(this.roleForm));
    	us.setType(String.valueOf(this.typeForm));
    	us.setOrganisation(String.valueOf(this.organisationForm));
    	
    	session.setAttribute("newUser", us);
    	
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM aliada.user WHERE user_name='" + getUsernameForm() + "'");
            if (!rs.next()) {
                statement = connection.createStatement();
                
                if (this.passwordForm.equals(this.repeatPasswordForm)) {
                	// Encrypt password
	                StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
	                String encryptedPassword = passwordEncryptor.encryptPassword(this.passwordForm);
	                
	                statement.executeUpdate("INSERT INTO aliada.user VALUES ('" + this.usernameForm + "', '"
	                  + encryptedPassword + "', '" + this.emailForm + "', '" + this.typeForm + "', '" + this.roleForm + "', '" + this.organisationForm + "')");
	                addActionMessage(getText("user.save.ok"));
	                rs.close();
	                statement.close();
	                connection.close(); 
	                getUsersDb();
	                session.setAttribute("newUser", null);
                } else {
                	rs.close();
	                statement.close();
	                connection.close();
                	addFieldError("passwordForm", getText("userPassword.not.equals"));
                	addFieldError("repeatPasswordForm", getText("userPassword.not.equals"));
                	return ERROR;
                }
            } else {
                rs.close();
                statement.close();
                connection.close();
                addFieldError("usernameForm", getText("err.duplicate.user"));
                return ERROR;
            }         	
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        return SUCCESS;        
    }
    
    /** Displays the form to add the user.
     * @return String */
    public String showAddForm() {
    	HttpSession session = ServletActionContext.getRequest().getSession();
    	User us = (User) session.getAttribute("newUser");
        getUsersDb();
        setShowAddForm(true);
        if (session.getAttribute("newUser") != null) {
    		setUsernameForm(us.getUsername());
            setPasswordForm(us.getPassword());
            setRepeatPasswordForm(us.getRepeatPassword());
            setEmailForm(us.getEmail());
            setTypeForm(Integer.parseInt(us.getType()));
            setRoleForm(Integer.parseInt(us.getRole()));
            setOrganisationForm(Integer.parseInt(us.getOrganisation()));
            session.setAttribute("newUser", us);
    	}
        return SUCCESS;
    }
    
    /** Updates an user in the DB.
     * @return String */
    public String editUser() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
        	Statement statement = connection.createStatement();
        	if (this.passwordForm.trim().isEmpty()) {
        		statement.executeUpdate("UPDATE aliada.user set user_email='" + this.emailForm + "',user_role_code='" + this.roleForm 
        				+ "',user_type_code='" + this.typeForm + "',organisationId='" + this.organisationForm + "' where user_name='" + this.usernameForm + "'");
        	            addActionMessage(getText("user.edit.ok"));
        	            statement.close(); 
        	            connection.close();
        	            getUsersDb();
        	} else {
        		HttpSession session = ServletActionContext.getRequest().getSession();
            	User u = (User) session.getAttribute("user");
            	StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
            	String pass = (String) session.getAttribute("pass");
            	// User password correct
            	if (passwordEncryptor.checkPassword(this.passwordForm, pass)) {
            		boolean s = true;
            		session.setAttribute("showPass", s);
            		u.setPassword(this.passwordForm);
            		if (this.newPasswordForm.trim().isEmpty() && this.repeatNewPasswordForm.trim().isEmpty()) {
                    	// New Pass empty
            			u.setNewPassword("");
            			u.setRepeatNewPassword("");
            			session.setAttribute("user", u);
                    	addFieldError("newPasswordForm", getText("userPassword.required"));
                    	addFieldError("repeatNewPasswordForm", getText("userPassword.required"));
                    	statement.close(); 
         	            connection.close();
         	            return ERROR;
                    } else if (this.newPasswordForm.trim().isEmpty()) {
                    	// New Pass empty
                    	addFieldError("newPasswordForm", getText("userPassword.required"));
                    	u.setNewPassword("");
                    	session.setAttribute("user", u);
                    	statement.close(); 
         	            connection.close();
         	            return ERROR;
                    } else if (this.repeatNewPasswordForm.trim().isEmpty()) {
                    	// Repeat new pass empty
                    	addFieldError("repeatNewPasswordForm", getText("userPassword.required"));
                    	u.setRepeatNewPassword("");
                    	session.setAttribute("user", u);
                    	statement.close(); 
         	            connection.close();
         	            return ERROR;
                    } else if (this.newPasswordForm.equals(this.repeatNewPasswordForm) && !passwordEncryptor.checkPassword(this.newPasswordForm, pass)) {
                    	// New pass and repeat new pass equals
                    	String encryptedPassword = passwordEncryptor.encryptPassword(this.newPasswordForm);
                        statement.executeUpdate("UPDATE aliada.user set user_password='" + encryptedPassword + "',user_email='" + this.emailForm + "',user_role_code='" 
                        + this.roleForm + "',user_type_code='" + this.typeForm + "',organisationId='" + this.organisationForm + "' where user_name='" + this.usernameForm + "'");
                        addActionMessage(getText("user.edit.ok"));
        	            statement.close(); 
        	            connection.close();
        	            getUsersDb();
                    } else if (passwordEncryptor.checkPassword(this.newPasswordForm, pass)) {
                    	// Pass equals to new Pass
                    	boolean sh = true;
                    	session.setAttribute("showPass", sh);
                		u.setPassword(this.passwordForm);
                    	u.setNewPassword(this.newPasswordForm);
                    	u.setRepeatNewPassword(this.repeatNewPasswordForm);
                    	session.setAttribute("user", u);
                    	addFieldError("passwordForm", getText("userPassword.equals"));
                    	addFieldError("newPasswordForm", getText("userPassword.equals"));
                    	addFieldError("repeatNewPasswordForm", getText("userPassword.equals"));
                    	statement.close(); 
         	            connection.close();
         	            return ERROR;                    	
                    } else {
                    	// Not equals
                    	u.setNewPassword(this.newPasswordForm);
                    	u.setRepeatNewPassword(this.repeatNewPasswordForm);
                    	session.setAttribute("user", u);
                    	addFieldError("newPasswordForm", getText("userPassword.not.equals"));
                    	addFieldError("repeatNewPasswordForm", getText("userPassword.not.equals"));
                    	statement.close(); 
         	            connection.close();
         	            return ERROR;
                    }
            	} else {
            		// User password wrong
            		if (!this.newPasswordForm.trim().isEmpty()) {
            			u.setNewPassword(this.newPasswordForm);
            		} else {
            			u.setNewPassword("");
            		}
            		if (!this.repeatNewPasswordForm.trim().isEmpty()) {
            			u.setRepeatNewPassword(this.repeatNewPasswordForm);
            		} else {
            			u.setRepeatNewPassword("");
            		}
            		boolean s = true;
            		session.setAttribute("showPass", s);
            		u.setPassword(this.passwordForm);
            		session.setAttribute("user", u);
            		addFieldError("passwordForm", getText("userPassword.not.equals"));
            		statement.close(); 
     	            connection.close();
     	            return ERROR;
            	}
        	}
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        return SUCCESS;        
    }
    
    /** Displays the form to edit the user.
     * @return String */
    public String showEditForm() {	
    	HttpSession session = ServletActionContext.getRequest().getSession();
    	User u = (User) session.getAttribute("user");
    	getUsersDb();
        setShowEditForm(true);
        setUsernameForm(u.getUsername());
        if ((boolean) session.getAttribute("showPass")) {
        	 setPasswordForm(u.getPassword());
        	 boolean s = false;
        	 session.setAttribute("showPass", s);
        }
        setNewPasswordForm(u.getNewPassword());
        setRepeatNewPasswordForm(u.getRepeatNewPassword());
        setEmailForm(u.getEmail());
        setTypeForm(Integer.parseInt(u.getType()));
        setRoleForm(Integer.parseInt(u.getRole()));
        setOrganisationForm(Integer.parseInt(u.getOrganisation()));
        session.setAttribute("user", u);
        return SUCCESS;
    }
    
    /** Displays the form to the edit the user.
     * @return String */
    public String showEdit() {
    	if (this.selectedUser != null) {
	    	if (getSelectedUser().equalsIgnoreCase(defaults.getString("user.admin"))) {
	        	addActionError(getText("err.not.allow.edit"));
	            getUsersDb();
	            return ERROR;          	
	        } else {
		        Connection connection = null;
		        HttpSession session = ServletActionContext.getRequest().getSession();
		        User u = new User();
		        try {
		            connection = new DBConnectionManager().getConnection();
		            Statement statement = connection.createStatement();
		            if (session.getAttribute("userToUpdate") != null) {
		                setSelectedUser((String) ServletActionContext.getRequest().getSession().getAttribute("userToUpdate"));
		            }
			            ResultSet rs = statement.executeQuery("select * from aliada.user where user_name='" + getSelectedUser() + "'");
			            if (rs.next()) {
			                this.usernameForm = getSelectedUser();
			                session.setAttribute("pass", rs.getString("user_password"));
			                this.emailForm = rs.getString("user_email");
			                this.typeForm = rs.getInt("user_type_code");
			                this.roleForm = rs.getInt("user_role_code"); 
			                this.organisationForm = rs.getInt("organisationId");
			                u.setUsername(getSelectedUser());
			                u.setPassword(rs.getString("user_password"));
			                u.setEmail(rs.getString("user_email"));
			                u.setType(String.valueOf(rs.getInt("user_type_code")));
			                u.setRole(String.valueOf(rs.getInt("user_role_code")));
			                u.setOrganisation(String.valueOf(rs.getInt("organisationId")));
			                statement.close();
			                connection.close();
			                getUsersDb();
			                session.setAttribute("user", u);
			                session.setAttribute("userToUpdate", getSelectedUser());
			                setShowEditForm(true);
			                return SUCCESS;
			            } else {
			                addActionError(getText("err.not.selected.user"));
			                statement.close();
			                connection.close();
			                getUsersDb();
			                return ERROR;
			            }
		        } catch (SQLException e) {
		            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
		            return ERROR;
		        }  
	        }
    	} else {
	    	addActionError(getText("err.not.selected.user"));
	    	getUsersDb();
	        return ERROR;
    	}
    }
    
    /** Delete an user from the DB.
     * @return String */
    public String deleteUser() {
    	if (this.selectedUser != null) {
	        String logedUser = (String) ServletActionContext.getRequest().getSession().getAttribute("logedUser");
	        if (getSelectedUser().equalsIgnoreCase(logedUser.trim())) {
	            addActionError(getText("err.user.deletion"));
	            getUsersDb();
	            return ERROR;
	        } else if (getSelectedUser().equalsIgnoreCase(defaults.getString("user.admin"))) {
	        	addActionError(getText("err.user.deletion"));
	            getUsersDb();
	            return ERROR;
	        } else {
	            Connection connection = null;
	            try {
	                connection = new DBConnectionManager().getConnection();
	                Statement statement = connection.createStatement();
	                int correct = statement.executeUpdate("DELETE FROM aliada.user WHERE user_name='" + getSelectedUser() + "'");
	                statement.close();
	                connection.close();
	                if (correct == 0) {
	                    addActionError(getText("err.not.selected.user"));                
	                } else {
	                    addActionMessage(getText("user.delete.ok"));
	                }
	            } catch (SQLException e) {
	                logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
	                return ERROR;
	            }
	            return getUsersDb(); 
	        }  
    	} else {
	    	addActionError(getText("err.not.selected.user"));
	    	getUsersDb();
	        return ERROR;
    	}
    }
    
    /** @return Returns the roles. */
    public HashMap<Integer, String> getRoles() {
        return roles;
    }
    /** @param roles The roles to set. */
    public void setRoles(final HashMap<Integer, String> roles) {
        this.roles = roles;
    }
    /** @return Returns the selectedUser. */
    public String getSelectedUser() {
        return selectedUser;
    }
    /** @return Returns the usernameForm. */
    public String getUsernameForm() {
        return usernameForm;
    }
    /** @param usernameForm The usernameForm to set. */
    public void setUsernameForm(final String usernameForm) {
        this.usernameForm = usernameForm;
    }
    /** @return Returns the passwordForm. */
    public String getPasswordForm() {
        return passwordForm;
    }
    /** @param passwordForm The passwordForm to set. */
    public void setPasswordForm(final String passwordForm) {
        this.passwordForm = passwordForm;
    }
    /** @return Returns the repeatPasswordForm. */
    public String getRepeatPasswordForm() {
        return repeatPasswordForm;
    }
    /** @param repeatPasswordForm The repeatPasswordForm to set. */
    public void setRepeatPasswordForm(final String repeatPasswordForm) {
        this.repeatPasswordForm = repeatPasswordForm;
    }
    /** @return Returns the newPasswordForm. */
    public String getNewPasswordForm() {
        return newPasswordForm;
    }
    /** @param newPasswordForm The newPasswordForm to set. */
    public void setNewPasswordForm(final String newPasswordForm) {
        this.newPasswordForm = newPasswordForm;
    }
    /** @return Returns the repeatNewPasswordForm. */
    public String getRepeatNewPasswordForm() {
        return repeatNewPasswordForm;
    }
    /** @param repeatNewPasswordForm The repeatNewPasswordForm to set. */
    public void setRepeatNewPasswordForm(final String repeatNewPasswordForm) {
        this.repeatNewPasswordForm = repeatNewPasswordForm;
    }
    /** @return Returns the emailForm. */
    public String getEmailForm() {
        return emailForm;
    }
    /** @param emailForm The emailForm to set. */
    public void setEmailForm(final String emailForm) {
        this.emailForm = emailForm;
    }
    /** @return Returns the roleForm. */
    public int getRoleForm() {
        return roleForm;
    }
    /** @param roleForm The roleForm to set. */
    public void setRoleForm(final int roleForm) {
        this.roleForm = roleForm;
    }
    /** @return Returns the typeForm. */
    public int getTypeForm() {
        return typeForm;
    }
    /** @param typeForm The typeForm to set. */
    public void setTypeForm(final int typeForm) {
        this.typeForm = typeForm;
    }
    /** @param selectedUser The selectedUser to set. */
    public void setSelectedUser(final String selectedUser) {
        this.selectedUser = selectedUser;
    }
    /** @return Returns the users. */
    public List<User> getUsers() {
        return users;
    }
    /** @param users The users to set. */
    public void setUsers(final List<User> users) {
        this.users = users;
    }
    /** @return Returns the showAddForm. */
    public boolean isShowAddForm() {
        return showAddForm;
    }
    /** @param showAddForm The showAddForm to set. */
    public void setShowAddForm(final boolean showAddForm) {
        this.showAddForm = showAddForm;
    }
    /** @return Returns the showEditForm. */
    public boolean isShowEditForm() {
        return showEditForm;
    }
    /** @param showEditForm The showEditForm to set. */
    public void setShowEditForm(final boolean showEditForm) {
        this.showEditForm = showEditForm;
    }
    /** @return Returns the areUsers. */
    public boolean isAreUsers() {
        return areUsers;
    }
    /** @param areUsers The areUsers to set. */
    public void setAreUsers(final boolean areUsers) {
        this.areUsers = areUsers;
    }
    /** @return Returns the organisationForm. */
    public int getOrganisationForm() {
        return organisationForm;
    }
    /** @param organisationForm The organisationForm to set. */
    public void setOrganisationForm(final int organisationForm) {
        this.organisationForm = organisationForm;
    }
    /** @return Returns the organisations.*/
    public HashMap<Integer, String> getOrganisations() {
        return organisations;
    }
    /** @param organisations The organisations to set. */
    public void setOrganisations(final HashMap<Integer, String> organisations) {
        this.organisations = organisations;
    }
    /** @return Returns the userTypes. */
	public HashMap<Integer, String> getUserTypes() {
		return userTypes;
	}
	/** @param userTypes The userTypes to set. */
	public void setUserTypes(final HashMap<Integer, String> userTypes) {
		this.userTypes = userTypes;
	}
	
}
