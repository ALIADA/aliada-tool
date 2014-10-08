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

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.model.User;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**
 * 
 * @author iosa
 * @version $Revision: 1.1 $
 * @since 1.0
 */
public class UsersAction extends ActionSupport{
    private List<User> users;
    private HashMap<Integer, String> roles;
    private HashMap<Integer, String> types;    
    private String selectedUser;
    
    private boolean showAddForm;
    private boolean showEditForm;
    private boolean areUsers;
    
    private String usernameForm;
    private String passwordForm;
    private String emailForm;
    private int roleForm;
    private int typeForm;   

    private final Log logger = new Log(UsersAction.class);
    
    /**
     * Displays the form to add the user
     * @return
     * @see
     * @since 1.0
     */
    public String showAddForm(){
        getUsersDb();
        this.showAddForm = true;
        return SUCCESS;
    }
    /**
     * Delete an user from the DB
     * @return
     * @see
     * @since 1.0
     */
    public String deleteUser() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            int correct = statement.executeUpdate("DELETE FROM aliada.user WHERE user_name='"+getSelectedUser()+"'");
            statement.close();
            connection.close();
            if(correct==0){
                clearErrorsAndMessages();
                addActionError(getText("err.not.selected.user"));                
            }
            else{
                clearErrorsAndMessages();
                addActionMessage(getText("user.delete.ok"));
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
            getUsersDb();
            return ERROR;
        }
        return getUsersDb();           
    }
    /**
     * Displays the form to the edit the user
     * @return
     * @see
     * @since 1.0
     */
    public String showEdit(){
        Connection connection = null;
        HttpSession session = ServletActionContext.getRequest().getSession();
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            if(session.getAttribute("userToUpdate")!=null){
                setSelectedUser((String) ServletActionContext.getRequest().getSession().getAttribute("userToUpdate"));
            }
            ResultSet rs = statement.executeQuery("select * from aliada.user where user_name='"+getSelectedUser()+"'");
            if(rs.next()){
                this.usernameForm=getSelectedUser();
                this.passwordForm = rs.getString("user_password");
                this.emailForm = rs.getString("user_email");
                this.typeForm = rs.getInt("user_type_code");
                this.roleForm = rs.getInt("user_role_code"); 
                statement.close();
                connection.close();
                getUsersDb();
                session.setAttribute("userToUpdate", getSelectedUser());
                this.showEditForm=true;
                return SUCCESS;
            }
            else{
                clearErrorsAndMessages();
                addActionError(getText("err.not.selected.user"));
                statement.close();
                connection.close();
                getUsersDb();
                return ERROR;
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
            getUsersDb();
            return ERROR;
        }        
    }
    
    /**
     * Gets the users from the DB
     * @return
     * @see
     * @since 1.0
     */
    public String getUsersDb(){
        ServletActionContext.getRequest().getSession().removeAttribute("userToUpdate");    
        getRolesDb();
        getTypesDb();
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select * from aliada.user");
            users=new ArrayList<User>();
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("user_name"));
                user.setPassword(rs.getString("user_password"));
                user.setEmail(rs.getString("user_email"));
                user.setType(getUserType(rs.getInt("user_type_code")));
                user.setRole(getRoleCode(rs.getInt("user_role_code")));
                this.users.add(user);
            }
            statement.close();
            connection.close();
            if(users.isEmpty()){
                setAreUsers(false);
            }
            else{
                setAreUsers(true);
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
            return ERROR;
        }
        this.showAddForm=false;
        this.showEditForm=false;
        return SUCCESS;
    }
    /**
     * Add an user to the DB
     * @return
     * @see
     * @since 1.0
     */
    public String addUser(){
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM user WHERE user_name='"+getUsernameForm()+"'");
            if(!rs.next()){
                rs.close();
                statement.close();
                statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO user VALUES ('"+this.usernameForm+"', '"
                  +this.passwordForm+"', '"+this.emailForm+"', '"+this.roleForm+"', '"+this.typeForm+"')");
                addActionMessage(getText("user.save.ok"));
                connection.close(); 
                getUsersDb();
            }else{
                rs.close();
                statement.close();
                connection.close();
                clearErrorsAndMessages();
                addActionError(getText("err.duplicate.user"));    
                return ERROR;
            }         	
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
            return ERROR;
        }
        return SUCCESS;        
    }
    /**
     * Updates an user in the DB
     * @return
     * @see
     * @since 1.0
     */
    public String editUser(){
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
        	Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE user set user_password='"+this.passwordForm+"',user_email='"+this.emailForm+"',user_role_code='"+this.roleForm+"',user_type_code='"+this.typeForm+"' where user_name='"+this.usernameForm+"'");
            clearErrorsAndMessages();
            addActionMessage(getText("user.edit.ok"));
            statement.close(); 
            connection.close();
            getUsersDb();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
            getUsersDb();
            return ERROR;
        }
        return SUCCESS;        
    }
    /**
     * Gets the user role name for a given user role code
     * @param code
     * @return
     * @see
     * @since 1.0
     */
    private String getRoleCode(int code) {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
        	Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select user_role from aliada.t_user_role where user_role_code='"+code+"'");
            if (rs.next()) {
                String userRole = rs.getString("user_role");
                connection.close();
                return userRole;
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
            return ERROR;
        }
        return null;
    }
    /**
     * Gets the user type name for a given user type code
     * @param code
     * @return
     * @see
     * @since 1.0
     */
    private String getUserType(int code) {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select user_type from aliada.t_user_type where user_type_code='"+code+"'");
            if (rs.next()) {
                String userType = rs.getString("user_type");
                connection.close();
                return userType;
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
            return ERROR;
        }
        return null;
    }
    /**
     * Get the user roles from DB
     * @return
     * @see
     * @since 1.0
     */
    private String getRolesDb(){
        Connection connection;
        this.roles = new HashMap<Integer, String>();
        try {
            connection = new DBConnectionManager().getConnection();
        	Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from aliada.t_user_role");
            while (rs.next()) {
                int code = rs.getInt("user_role_code");
                String name = rs.getString("user_role");
                this.roles.put(code, name);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
            return ERROR;
        }
        return SUCCESS;   
    }
    /**
     * Gets the user types from DB
     * @return
     * @see
     * @since 1.0
     */
    private String getTypesDb(){
        Connection connection;
        this.types = new HashMap<Integer, String>();
        try {
            connection = new DBConnectionManager().getConnection();
        	Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from aliada.t_user_type");
            while (rs.next()) {
                int code = rs.getInt("user_type_code");
                String name = rs.getString("user_type");
                types.put(code, name);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
        }
        return SUCCESS;   
    }
    
    /**
     * @return Returns the roles.
     * @exception
     * @since 1.0
     */
    public HashMap<Integer, String> getRoles() {
        return roles;
    }
    /**
     * @param roles The roles to set.
     * @exception
     * @since 1.0
     */
    public void setRoles(HashMap<Integer, String> roles) {
        this.roles = roles;
    }
    /**
     * @return Returns the types.
     * @exception
     * @since 1.0
     */
    public HashMap<Integer, String> getTypes() {
        return types;
    }
    /**
     * @param types The types to set.
     * @exception
     * @since 1.0
     */
    public void setTypes(HashMap<Integer, String> types) {
        this.types = types;
    }
    
    /**
     * @return Returns the selectedUser.
     * @exception
     * @since 1.0
     */
    public String getSelectedUser() {
        return selectedUser;
    }
    /**
     * @return Returns the usernameForm.
     * @exception
     * @since 1.0
     */
    public String getUsernameForm() {
        return usernameForm;
    }
    /**
     * @param usernameForm The usernameForm to set.
     * @exception
     * @since 1.0
     */
    public void setUsernameForm(String usernameForm) {
        this.usernameForm = usernameForm;
    }
    /**
     * @return Returns the passwordForm.
     * @exception
     * @since 1.0
     */
    public String getPasswordForm() {
        return passwordForm;
    }
    /**
     * @param passwordForm The passwordForm to set.
     * @exception
     * @since 1.0
     */
    public void setPasswordForm(String passwordForm) {
        this.passwordForm = passwordForm;
    }
    /**
     * @return Returns the emailForm.
     * @exception
     * @since 1.0
     */
    public String getEmailForm() {
        return emailForm;
    }
    /**
     * @param emailForm The emailForm to set.
     * @exception
     * @since 1.0
     */
    public void setEmailForm(String emailForm) {
        this.emailForm = emailForm;
    }
    /**
     * @return Returns the roleForm.
     * @exception
     * @since 1.0
     */
    public int getRoleForm() {
        return roleForm;
    }
    /**
     * @param roleForm The roleForm to set.
     * @exception
     * @since 1.0
     */
    public void setRoleForm(int roleForm) {
        this.roleForm = roleForm;
    }
    /**
     * @return Returns the typeForm.
     * @exception
     * @since 1.0
     */
    public int getTypeForm() {
        return typeForm;
    }
    /**
     * @param typeForm The typeForm to set.
     * @exception
     * @since 1.0
     */
    public void setTypeForm(int typeForm) {
        this.typeForm = typeForm;
    }
    /**
     * @param selectedUser The selectedUser to set.
     * @exception
     * @since 1.0
     */
    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
    }
    
    /**
     * @return Returns the users.
     * @exception
     * @since 1.0
     */
    public List<User> getUsers() {
        return users;
    }
    /**
     * @param users The users to set.
     * @exception
     * @since 1.0
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    /**
     * @return Returns the showAddForm.
     * @exception
     * @since 1.0
     */
    public boolean isShowAddForm() {
        return showAddForm;
    }

    /**
     * @param showAddForm The showAddForm to set.
     * @exception
     * @since 1.0
     */
    public void setShowAddForm(boolean showAddForm) {
        this.showAddForm = showAddForm;
    }

    /**
     * @return Returns the showEditForm.
     * @exception
     * @since 1.0
     */
    public boolean isShowEditForm() {
        return showEditForm;
    }

    /**
     * @param showEditForm The showEditForm to set.
     * @exception
     * @since 1.0
     */
    public void setShowEditForm(boolean showEditForm) {
        this.showEditForm = showEditForm;
    }

    /**
     * @return Returns the areUsers.
     * @exception
     * @since 1.0
     */
    public boolean isAreUsers() {
        return areUsers;
    }

    /**
     * @param areUsers The areUsers to set.
     * @exception
     * @since 1.0
     */
    public void setAreUsers(boolean areUsers) {
        this.areUsers = areUsers;
    }
}
