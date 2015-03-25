// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium
package eu.aliada.gui.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
public class ConfigurationAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private String organisationName;
    private File organisationLogo;
    private String organisationCatalogUrl;     
    private HashMap<Integer, String> externalDatasets;
	private List<Integer> selectedExternalDatasets;
	private List<String> val;
    private HashMap<Integer, String> profiles;
    private String selectedProfile;
    private String nameForm;
    private String descriptionForm;
    private int profileTypeForm;
    private String profileTypeNameForm;
    private int schemeForm;
    private String schemeNameForm;
    private int fileTypeForm;
    private String fileTypeNameForm;
    private int fileFormatForm;
    private String fileFormatNameForm;
    private int characterSetForm;
    private String characterSetNameForm;
    private HashMap<Integer, String> schemes;
    private HashMap<Integer, String> profileTypes;
    private HashMap<Integer, String> formats;
    private HashMap<Integer, String> characterSets;
    private boolean showAddProfileForm;
    private boolean showEditProfileForm;
    private boolean showTheProfile;
    private boolean areProfiles;
    private final int NOTEMPLATESELECTED = -1;
    private HashMap<Integer, String> templates;
    private HashMap<String, Boolean> tags;
    private HashMap<Integer, String> types;
    private List selectedTags = new ArrayList();
    private String templateName;
    private String templateDescription;
    private int fileType;
    private String fileTypeName;
    private String selectedTemplate;
    private boolean showTheTemplate;
    private boolean showAddTemplateForm;
    private boolean showEditTemplateForm;
    private boolean areTemplates;
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
    private String emailForm;
    private int roleForm;
    private int typeForm; 
    private int organisationForm;
    private Methods methods;
    private int tab;
    private final Log logger = new Log(ConfigurationAction.class);
    /** Load all the tabs.
     * @return String */
    public String execute() {
        loadInstitInf();
		loadReloadDatasets();
		loadUsers();
		loadTemplates();
		loadProfiles();
		return SUCCESS;	
    }
	/** Edit the institution.
     * @return String */
    public String editInstitution() {
    	setTab(0);
    	ServletActionContext.getRequest().getSession().setAttribute("ConfOpc", 0);
        Connection connection;
        FileInputStream fis = null;
        try {
            connection = new DBConnectionManager().getConnection();
            PreparedStatement preparedStatement;
            if (this.organisationLogo != null) {
            	preparedStatement = connection.prepareStatement("UPDATE aliada.organisation  SET org_logo = ?, org_catalog_url =? WHERE org_name = ?");
                fis = new FileInputStream(this.organisationLogo);
                preparedStatement.setBinaryStream(1, fis, (int) this.organisationLogo.length());
                preparedStatement.setString(2, this.organisationCatalogUrl);
                preparedStatement.setString(3, this.organisationName);
            } else {
                preparedStatement = connection.prepareStatement("UPDATE aliada.organisation  SET org_catalog_url =? WHERE org_name = ?");
                preparedStatement.setString(1, this.organisationCatalogUrl);
                preparedStatement.setString(2, this.organisationName);
            }
            preparedStatement.executeUpdate();
            addActionMessage(getText("institution.changed"));
            preparedStatement.close();
            connection.close();
            return execute();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        } catch (FileNotFoundException e) {
            logger.error(MessageCatalog._00013_FILE_NOT_FOUND_EXCEPTION, e);
            return ERROR;
        }
    }
    /** Reload the external datasets selected in the UI.
     * @return String */
    public String reloadExternalDatasets() {
    	setTab(5);
    	ServletActionContext.getRequest().getSession().setAttribute("ConfOpc", 5);
    	new Methods().cleanReloadDataset();
    	Connection connection = null;
    		if (val != null) {
    			for (int i = 0; i < val.size(); i++) {
    				String extDataset = val.get(i);
    				try {
    		            connection = new DBConnectionManager().getConnection();
    		            Statement updateStatement = connection.createStatement();
                        updateStatement.executeUpdate("UPDATE aliada.t_external_dataset set external_dataset_linkingreloadtarget='1' "
                        		+ "where external_dataset_name='" + extDataset + "'");
                        updateStatement.close();
    		            connection.close();
    		        } catch (SQLException e) {
    		            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
    		            return ERROR;
    		        }
    			}
    		}
    	addActionMessage(getText("datasets.reload.ok"));
    	return execute();
    }
    /** The method to show the profile list.
     * @return String */
    public String showProfiles() {
    	methods = new Methods();
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from aliada.profile");
            profiles = new HashMap<Integer, String>();
            while (rs.next()) {
                profiles.put(rs.getInt("profile_id"), rs.getString("profile_name"));
            }
            if (profiles.isEmpty()) {
                setAreProfiles(false);
            } else {
                setAreProfiles(true);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        schemes = methods.getSchemesDb();
        characterSets = methods.getCharacterSetsDb();
        formats = methods.getFormatsDb();
        profileTypes = methods.getProfileTypesDb();
        types = methods.getTypesDb();
        return execute();
    }
    /** The method to see the profile selected.
     * @return String */
    public String getTheProfile() {
    	setTab(1);
    	ServletActionContext.getRequest().getSession().setAttribute("ConfOpc", 1);
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select p.profile_name,p.profile_description,t.profile_name,m.metadata_name,f.file_type_name,"
                    		+ "fo.file_format_name,c.character_set_name from aliada.profile p INNER JOIN aliada.t_profile_type t ON p.profile_type_code=t.profile_code "
                    		+ "INNER JOIN aliada.t_metadata_scheme m ON p.metadata_scheme_code=m.metadata_code "
                    		+ "INNER JOIN aliada.t_file_type f ON p.file_type_code=f.file_type_code "
                    		+ "INNER JOIN aliada.t_file_format fo ON fo.file_format_code=p.file_format_code INNER JOIN aliada.t_character_set c "
                    		+ "ON c.character_set_code=p.character_set_code where p.profile_name='" + this.selectedProfile + "'");
            if (rs.next()) {
                this.nameForm = rs.getString("p.profile_name");
                this.descriptionForm = rs.getString("p.profile_description");
                this.profileTypeNameForm = rs.getString("t.profile_name");
                this.schemeNameForm = rs.getString("m.metadata_name");
                this.fileTypeNameForm = rs.getString("f.file_type_name");
                this.fileFormatNameForm = rs.getString("fo.file_format_name");
                this.characterSetNameForm = rs.getString("c.character_set_name");
                statement.close();
                rs.close();
                connection.close();
                showProfiles();
                setShowTheProfile(true);
                return SUCCESS;
            } else {
                addActionError(getText("profile.not.selected"));
                statement.close();
                rs.close();
                connection.close();
                showProfiles();
                return ERROR;
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
    }
    /** The method to show the profile form with the profile selected.
     * @return String */
    public String showEditProfile() {	
    	setTab(1);
    	ServletActionContext.getRequest().getSession().setAttribute("ConfOpc", 1);
    	if (this.selectedProfile != null) {
	    	if (getSelectedProfile().equalsIgnoreCase("MARC BIB") || getSelectedProfile().equalsIgnoreCase("MARC AUT") || getSelectedProfile().equalsIgnoreCase("LIDO")) {
			        	addActionError(getText("err.not.allow.edit"));
			        	showProfiles();
			            return ERROR;          	
	        } else {
		        Connection connection = null;
		        try {
		            connection = new DBConnectionManager().getConnection();
		            Statement statement = connection.createStatement();
		            ResultSet rs = statement.executeQuery("select * from aliada.profile where profile_name='" + this.selectedProfile + "'");
		            if (rs.next()) {
		                this.nameForm = rs.getString("profile_name");
		                this.profileTypeForm = rs.getInt("profile_type_code");
		                this.descriptionForm = rs.getString("profile_description");
		                this.schemeForm = rs.getInt("metadata_scheme_code");
		                this.fileTypeForm = rs.getInt("file_type_code");
		                this.fileFormatForm = rs.getInt("file_format_code");
		                this.characterSetForm = rs.getInt("character_set_code");
		                statement.close();
		                rs.close();
		                connection.close();
		                showProfiles();
		                setShowEditProfileForm(true);
		                return SUCCESS;
		            } else {
		                addActionError(getText("profile.not.selected"));
		                statement.close();
		                rs.close();
		                connection.close();
		                showProfiles();
		                return ERROR;
		            }
		        } catch (SQLException e) {
		            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
		            return ERROR;
		        }
	        }
	    } else {
	    	addActionError(getText("profile.not.selected"));
	    	showProfiles();
	        return ERROR;
	    }
    }
    /** The method to edit the profile selected.
     * @return String */
    public String editProfile() {	
    	setTab(1);
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE aliada.profile set profile_type_code='" + this.profileTypeForm + "',profile_description='"
                    + this.descriptionForm + "',metadata_scheme_code='" + this.schemeForm + "',file_type_code='" + this.fileTypeForm + "',file_format_code='"
                    + this.fileFormatForm + "',character_set_code='" + this.characterSetForm + "' where profile_name='" + this.nameForm + "'");
            statement.close();
            connection.close();
            addActionMessage(getText("profile.edit.ok"));
            showProfiles();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        return SUCCESS;
    }
    /** The method to delete the profile selected.
     * @return String */
    public String deleteProfile() {	
    	setTab(1);
    	ServletActionContext.getRequest().getSession().setAttribute("ConfOpc", 1);
    	if (this.selectedProfile != null) {
	    	if (getSelectedProfile().equalsIgnoreCase("LIDO") || getSelectedProfile().equalsIgnoreCase("MARC AUT") || getSelectedProfile().equalsIgnoreCase("MARC BIB")) {
			    	addActionError(getText("err.profile.deletion"));
			    	showProfiles();
			        return SUCCESS;
	    	} else {
		        Connection connection = null;
		        try {
		            connection = new DBConnectionManager().getConnection();
		            Statement statement = connection.createStatement();
		            int correct = statement.executeUpdate("DELETE FROM aliada.profile WHERE profile_name='" + getSelectedProfile() + "'");
		            statement.close();
		            connection.close();
		            if (correct == 0) {
		                addActionError(getText("profile.not.selected"));
		            } else {
		                addActionMessage(getText("profile.delete.ok"));
		            }
		        } catch (SQLException e) {
		            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
		            return ERROR;
		        }
		        showProfiles();
		        return SUCCESS;
	    	}
    	} else {
	    	addActionError(getText("profile.not.selected"));
	    	showProfiles();
	        return ERROR;
	    }
    }
    /** The method to add a new profile.
     * @return String */
    public String addProfile() {	
    	setTab(1);
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO aliada.profile VALUES (default,'" + this.nameForm + "', '" + this.profileTypeForm + "', '"
                    + this.descriptionForm + "', '" + this.schemeForm + "', '" + this.fileTypeForm + "', '" + this.fileFormatForm + "', '" + this.characterSetForm + "')");
            statement.close();
            connection.close();
            addActionMessage(getText("profile.save.ok"));
            showProfiles();
            return SUCCESS;
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
    }
    /** The method to show the profile form empty.
     * @return String */
    public String showAddProfile() {	
    	setTab(1);
    	ServletActionContext.getRequest().getSession().setAttribute("ConfOpc", 1);
    	showProfiles();
        setShowAddProfileForm(true);
        return SUCCESS;
    }
    /** Gets the available templates from the DB.
     * @return String */ 
    public String getTemplatesDb() {
        Connection connection = null;
        methods = new Methods();
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from aliada.template");
            templates = new HashMap<Integer, String>();
            while (rs.next()) {
                templates.put(rs.getInt("template_id"), rs.getString("template_name"));
            }
            rs.close();
            statement.close();
            connection.close();
            if (templates.isEmpty()) {
                setAreTemplates(false);
            } else {
                setAreTemplates(true);
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        types = methods.getTypesDb();
        return execute();
    }    
    /** Displays the selected template.
     * @return String */
    public String getTheTemplate() {
    	setTab(2);
    	ServletActionContext.getRequest().getSession().setAttribute("ConfOpc", 2);
        Connection connection = null;
        methods = new Methods();
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select t.template_id,t.template_name,t.template_description,f.file_type_name "
                    		+ "from aliada.template t INNER JOIN aliada.t_file_type f ON t.file_type_code=f.file_type_code "
                    		+ "where t.template_name='" + this.selectedTemplate + "'");
            if (rs.next()) {
                int idTemplate = rs.getInt("t.template_id");
                this.templateName = rs.getString("t.template_name");
                this.templateDescription = rs.getString("t.template_description");
                this.fileTypeName = rs.getString("f.file_type_name");
                statement.close();
                rs.close();
                connection.close();
                getTemplatesDb();
                tags = methods.getTagsDb(idTemplate);
                setShowTheTemplate(true);
                ServletActionContext.getRequest().getSession().setAttribute("selectedTemplateId", idTemplate);
                return SUCCESS;
            } else {
                addActionError(getText("template.not.selected"));
                statement.close();
                rs.close();
                connection.close();
                getTemplatesDb();
                tags = methods.getTagsDb(NOTEMPLATESELECTED);
                return ERROR;
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
    }
    /** Displays the template adding form.
     * @return String */
    public String showAddTemplate() {
    	setTab(2);
    	ServletActionContext.getRequest().getSession().setAttribute("ConfOpc", 2);
    	methods = new Methods();
        getTemplatesDb();
        tags = methods.getTagsDb(NOTEMPLATESELECTED);
        setShowAddTemplateForm(true);
        return SUCCESS;
    }
    /** Adds a new template to the DB.
     * @return String */
    public String addTemplate() {	
    	setTab(2);
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO aliada.template VALUES (default,'" 
            + this.templateName + "', '" + this.templateDescription + "', '" + this.fileType + "')", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int idTemplate = 0;
            if (rs.next()) {
                idTemplate = (int) rs.getInt(1);
            }
            preparedStatement.close();
            Statement statement = connection.createStatement();
            Iterator iterator = selectedTags.iterator();
            while (iterator.hasNext()) {
                statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO aliada.template_xml_tag VALUES ('" + idTemplate + "', '" + iterator.next() + "')");
                statement.close();
            }
            connection.close();
            setShowAddTemplateForm(false);
            addActionMessage(getText("template.save.ok"));
            logger.debug(MessageCatalog._00060_CONVERSION_TEMPLATE_ADDED);
            getTemplatesDb();
            return SUCCESS;
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
    }
    /** Deletes a template from the DB.
     * @return String */
    public String deleteTemplate() {
    	setTab(2);
    	ServletActionContext.getRequest().getSession().setAttribute("ConfOpc", 2);
    	if (this.selectedTemplate != null) {
	    	if (getSelectedTemplate().equalsIgnoreCase("Authorities") || getSelectedTemplate().equalsIgnoreCase("MARC BIB") || getSelectedTemplate().equalsIgnoreCase("LIDO")) {
			    	addActionError(getText("err.template.deletion")); 
			    	getTemplatesDb();
			   		return SUCCESS;
	    	} else {
		        Connection connection = null;
		        try {
		            connection = new DBConnectionManager().getConnection();
		            Statement statement = connection.createStatement();
		            statement.executeUpdate("DELETE tags.* FROM aliada.template_xml_tag tags INNER JOIN template temp ON "
		                    		+ "tags.template_id=temp.template_id  WHERE temp.template_name='" + getSelectedTemplate() + "'");
		            statement.close();
		            statement = connection.createStatement();
		            int correct = statement.executeUpdate("DELETE FROM aliada.template WHERE template_name='" + getSelectedTemplate() + "'");
		            statement.close();
		            connection.close();
		            if (correct == 0) {
		                addActionError(getText("template.not.selected"));                
		            } else {
		                addActionMessage(getText("template.delete.ok"));
		            }
		        } catch (SQLException e) {
		            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
		            return ERROR;
		        }
		        getTemplatesDb();
		        return SUCCESS;
		    }
    	} else {
	    	addActionError(getText("template.not.selected"));
	    	getTemplatesDb();
	        return ERROR;
	    }
    }
    /** Displays the form to edit the template.
     * @return String */
    public String showEditTemplate() {	
    	setTab(2);
    	ServletActionContext.getRequest().getSession().setAttribute("ConfOpc", 2);
    	methods = new Methods();
    	if (this.selectedTemplate != null) {
	    	if (getSelectedTemplate().equalsIgnoreCase("MARC BIB") || getSelectedTemplate().equalsIgnoreCase("LIDO") || getSelectedTemplate().equalsIgnoreCase("Authorities")) {
			       	addActionError(getText("err.not.allow.edit"));
			        getTemplatesDb();
			        tags = methods.getTagsDb(NOTEMPLATESELECTED);
		            return ERROR;          	
	        } else {
		        Connection connection = null;
		        try {
		            connection = new DBConnectionManager().getConnection();
		            Statement statement = connection.createStatement();
		            ResultSet rs = statement.executeQuery("select * from aliada.template where template_name='" + this.selectedTemplate + "'");
		            if (rs.next()) {
		                int idTemplate = rs.getInt("template_id");
		                this.templateName = rs.getString("template_name");
		                this.templateDescription = rs.getString("template_description");
		                this.fileType = rs.getInt("file_type_code");
		                statement.close();
		                rs.close();
		                connection.close();
		                tags = methods.getTagsDb(idTemplate);
		                getTemplatesDb();
		                setShowEditTemplateForm(true);
		                ServletActionContext.getRequest().getSession().setAttribute("selectedTemplateId", idTemplate);
		                return SUCCESS;
		            } else {
		                addActionError(getText("template.not.selected"));
		                statement.close();
		                rs.close();
		                connection.close();
		                getTemplatesDb();
		                tags = methods.getTagsDb(NOTEMPLATESELECTED);
		                return ERROR;
		            }
		        } catch (SQLException e) {
		            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
		            return ERROR;
		        }
	        }
    	} else {
	    	addActionError(getText("template.not.selected"));
	    	getTemplatesDb();
	        return ERROR;
	    }
    }
    /** Updates an existing template.
     * @return String */
    public String editTemplate() {	
    	setTab(2);
        Connection connection = null;
        int idTemplate = (int) ServletActionContext.getRequest().getSession().getAttribute("selectedTemplateId");
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE aliada.template set template_description='" + this.templateDescription + "', file_type_code="
                            + this.fileType + " where template_id=" + idTemplate + "");
            statement.close();
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM aliada.template_xml_tag WHERE template_id=" + idTemplate);
            statement.close();
            Iterator iterator = selectedTags.iterator();
            while (iterator.hasNext()) {
                statement = connection.createStatement();
                statement.executeUpdate("INSERT IGNORE INTO aliada.template_xml_tag VALUES ('" + idTemplate + "', '" + iterator.next() + "')");
                statement.close();
            }
            ServletActionContext.getRequest().getSession().removeAttribute("selectedTemplateId");
            connection.close();
            addActionMessage(getText("template.save.ok"));
            getTemplatesDb();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        setShowEditTemplateForm(false);
        return SUCCESS;
    }
    /** Displays the form to add the user.
     * @return String */
    public String showAddForm() {
    	setTab(3);
    	ServletActionContext.getRequest().getSession().setAttribute("ConfOpc", 3);
        getUsersDb();
        setShowAddForm(true);
        return SUCCESS;
    }
    /** Delete an user from the DB.
     * @return String */
    public String deleteUser() {
    	setTab(3);
    	ServletActionContext.getRequest().getSession().setAttribute("ConfOpc", 3);
    	if (this.selectedUser != null) {
	        String logedUser = (String) ServletActionContext.getRequest().getSession().getAttribute("logedUser");
	        if (getSelectedUser().equalsIgnoreCase(logedUser.trim())) {
	            addActionError(getText("err.user.deletion"));
	            getUsersDb();
	            return ERROR;
	        } else if (getSelectedUser().equalsIgnoreCase("admin")) {
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
    /** Displays the form to add the user.
     * @return String */
    public String showEditForm() {	
    	setTab(3);
    	HttpSession session = ServletActionContext.getRequest().getSession();
    	session.setAttribute("ConfOpc", 3);
    	User u = (User) session.getAttribute("user");
    	getUsersDb();
        setShowEditForm(true);
        setUsernameForm(u.getUsername());
        setPasswordForm(u.getPassword());
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
    	setTab(3);
    	ServletActionContext.getRequest().getSession().setAttribute("ConfOpc", 3);
    	if (this.selectedUser != null) {
	    	if (getSelectedUser().equalsIgnoreCase("admin")) {
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
			                this.passwordForm = rs.getString("user_password");
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
    /** Gets the users from the DB.
     * @return String */
    public String getUsersDb() {
    	methods = new Methods();
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
        return execute();
    }
    /** Add an user to the DB.
     * @return String */
    public String addUser() {
    	setTab(3);
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM aliada.user WHERE user_name='" + getUsernameForm() + "'");
            if (!rs.next()) {
                statement = connection.createStatement();
                StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
                String encryptedPassword = passwordEncryptor.encryptPassword(this.passwordForm);
                statement.executeUpdate("INSERT INTO aliada.user VALUES ('" + this.usernameForm + "', '"
                  + encryptedPassword + "', '" + this.emailForm + "', '" + this.typeForm + "', '" + this.roleForm + "', '" + this.organisationForm + "')");
                addActionMessage(getText("user.save.ok"));
                rs.close();
                statement.close();
                connection.close(); 
                getUsersDb();
            } else {
                rs.close();
                statement.close();
                connection.close();
                addActionError(getText("err.duplicate.user"));    
                return ERROR;
            }         	
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        return SUCCESS;        
    }
    /** Updates an user in the DB.
     * @return String */
    public String editUser() {
    	setTab(3);
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
        	Statement statement = connection.createStatement();
            StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
            String encryptedPassword = passwordEncryptor.encryptPassword(this.passwordForm);
            statement.executeUpdate("UPDATE aliada.user set user_password='" + encryptedPassword + "',user_email='" + this.emailForm + "',user_role_code='" 
            + this.roleForm + "',user_type_code='" + this.typeForm + "',organisationId='" + this.organisationForm + "' where user_name='" + this.usernameForm + "'");
            addActionMessage(getText("user.edit.ok"));
            statement.close(); 
            connection.close();
            getUsersDb();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        return SUCCESS;        
    }
    /** Load institution data. */
    private void loadInstitInf() {
    	String userName = (String) ServletActionContext.getRequest().getSession().getAttribute("logedUser");
        try {
            Connection connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM aliada.organisation o INNER JOIN aliada.user u ON o.organisationId = "
            		+ "u.organisationId WHERE u.user_name='" + userName + "';");
            if (rs.next() && rs.getString("org_name") != null) {
                setOrganisationName(rs.getString("org_name"));
                setOrganisationCatalogUrl(rs.getString("org_catalog_url"));
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
        }
	}
    /** Load external datasets data. */
    private void loadReloadDatasets() {
    	selectedExternalDatasets = new ArrayList<>();
        try {
        	Connection connection = new DBConnectionManager().getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from aliada.t_external_dataset");
	        externalDatasets = new HashMap<Integer, String>();
	        while (rs.next()) {
	            externalDatasets.put(rs.getInt("external_dataset_code"), rs.getString("external_dataset_name"));
	            selectedExternalDatasets.add(rs.getInt("external_dataset_linkingreloadtarget"));
	        }
	        rs.close();
	        statement.close();
	        connection.close();
		} catch (SQLException e) {
			logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
		}
	}
    /** Load profiles data. */
	private void loadProfiles() {
		Connection connection = null;
		methods = new Methods();
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from aliada.profile");
            profiles = new HashMap<Integer, String>();
            while (rs.next()) {
                profiles.put(rs.getInt("profile_id"), rs.getString("profile_name"));
            }
            if (profiles.isEmpty()) {
                setAreProfiles(false);
            } else {
                setAreProfiles(true);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
        }
        schemes = methods.getSchemesDb();
        characterSets = methods.getCharacterSetsDb();
        formats = methods.getFormatsDb();
        profileTypes = methods.getProfileTypesDb();
        types = methods.getTypesDb();
	}
	/** Load templates data. */
	private void loadTemplates() {
		Connection connection = null;
		methods = new Methods();
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from aliada.template");
            templates = new HashMap<Integer, String>();
            while (rs.next()) {
                templates.put(rs.getInt("template_id"), rs.getString("template_name"));
            }
            rs.close();
            statement.close();
            connection.close();
            if (templates.isEmpty()) {
                setAreTemplates(false);
            } else {
                setAreTemplates(true);
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
        }
        types = methods.getTypesDb();
	}
	/** Load users data. */
	private void loadUsers() {
		methods = new Methods();
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
        }
	}
	/** @return Returns the organisationName. */
    public String getOrganisationName() {
        return organisationName;
    }
    /** @param organisationName The organisationName to set. */
    public void setOrganisationName(final String organisationName) {
        this.organisationName = organisationName;
    }
    /** @return Returns the organisationLogo. */
    public File getOrganisationLogo() {
        return organisationLogo;
    }
    /** @param organisationLogo The organisationLogo to set. */
    public void setOrganisationLogo(final File organisationLogo) {
        this.organisationLogo = organisationLogo;
    }
    /** @return Returns the organisationCatalogUrl. */
    public String getOrganisationCatalogUrl() {
        return organisationCatalogUrl;
    }
    /** @param organisationCatalogUrl The organisationCatalogUrl to set. */
    public void setOrganisationCatalogUrl(final String organisationCatalogUrl) {
        this.organisationCatalogUrl = organisationCatalogUrl;
    }
	/** @return Returns the externalDatasets. */
	public HashMap<Integer, String> getExternalDatasets() {
		return externalDatasets;
	}
	/** @param externalDatasets The externalDatasets to set. */
	public void setExternalDatasets(final HashMap<Integer, String> externalDatasets) {
		this.externalDatasets = externalDatasets;
	}
	/** @return Returns the selectedExternalDatasets. */
	public List<Integer> getSelectedExternalDatasets() {
		return selectedExternalDatasets;
	}
	/** @param selectedExternalDatasets The selectedExternalDatasets to set. */
	public void setSelectedExternalDatasets(final List<Integer> selectedExternalDatasets) {
		this.selectedExternalDatasets = selectedExternalDatasets;
	}
	/** @return Returns the val. */
	public List<String> getVal() {
		return val;
	}
	/** @param val The val to set. */
	public void setVal(final List<String> val) {
		this.val = val;
	}
	/** @return Returns the profiles. */
    public HashMap<Integer, String> getProfiles() {
        return profiles;
    }
    /** @param profiles The profiles to set. */
    public void setProfiles(final HashMap<Integer, String> profiles) {
        this.profiles = profiles;
    }
    /** @return Returns the selectedProfile. */
    public String getSelectedProfile() {
        return selectedProfile;
    }
    /** @param selectedProfile The selectedProfile to set. */
    public void setSelectedProfile(final String selectedProfile) {
        this.selectedProfile = selectedProfile;
    }
    /** @return Returns the schemes. */
    public HashMap<Integer, String> getSchemes() {
        return schemes;
    }
    /** @param schemes The schemes to set. */
    public void setSchemes(final HashMap<Integer, String> schemes) {
        this.schemes = schemes;
    }
    /** @return Returns the profileTypes. */
    public HashMap<Integer, String> getProfileTypes() {
        return profileTypes;
    }
    /** @param profileTypes The profileTypes to set. */
    public void setProfileTypes(final HashMap<Integer, String> profileTypes) {
        this.profileTypes = profileTypes;
    }
    /** @return Returns the types. */
    public HashMap<Integer, String> getTypes() {
        return types;
    }
    /** @param types The types to set. */
    public void setTypes(final HashMap<Integer, String> types) {
        this.types = types;
    }
    /** @return Returns the formats. */
    public HashMap<Integer, String> getFormats() {
        return formats;
    }
    /** @param formats The formats to set. */
    public void setFormats(final HashMap<Integer, String> formats) {
        this.formats = formats;
    }
    /** @return Returns the characterSets. */
    public HashMap<Integer, String> getCharacterSets() {
        return characterSets;
    }
    /** @param characterSets The characterSets to set. */
    public void setCharacterSets(final HashMap<Integer, String> characterSets) {
        this.characterSets = characterSets;
    }
    /** @return Returns the showAddProfileForm. */
    public boolean isShowAddProfileForm() {
        return showAddProfileForm;
    }
    /** @param showAddProfileForm The showAddProfileForm to set. */
    public void setShowAddProfileForm(final boolean showAddProfileForm) {
        this.showAddProfileForm = showAddProfileForm;
    }
    /** @return Returns the showEditProfileForm. */
    public boolean isShowEditProfileForm() {
        return showEditProfileForm;
    }
    /** @param showEditProfileForm The showEditProfileForm to set. */
    public void setShowEditProfileForm(final boolean showEditProfileForm) {
        this.showEditProfileForm = showEditProfileForm;
    }
    /** @return Returns the areProfiles. */
    public boolean isAreProfiles() {
        return areProfiles;
    }
    /** @param areProfiles The areProfiles to set. */
    public void setAreProfiles(final boolean areProfiles) {
        this.areProfiles = areProfiles;
    }
    /** @return Returns the showTheProfile. */
    public boolean isShowTheProfile() {
        return showTheProfile;
    }
    /** @param showTheProfile The showTheProfile to set. */
    public void setShowTheProfile(final boolean showTheProfile) {
        this.showTheProfile = showTheProfile;
    }
    /** @return Returns the nameForm. */
    public String getNameForm() {
        return nameForm;
    }
    /** @param nameForm The nameForm to set. */
    public void setNameForm(final String nameForm) {
        this.nameForm = nameForm;
    }
    /**  @return Returns the descriptionForm. */
    public String getDescriptionForm() {
        return descriptionForm;
    }
    /** @param descriptionForm The descriptionForm to set. */
    public void setDescriptionForm(final String descriptionForm) {
        this.descriptionForm = descriptionForm;
    }
    /** @return Returns the profileTypeNameForm. */
    public String getProfileTypeNameForm() {
		return profileTypeNameForm;
	}
    /** @param profileTypeNameForm The profileTypeNameForm to set. */
	public void setProfileTypeNameForm(final String profileTypeNameForm) {
		this.profileTypeNameForm = profileTypeNameForm;
	}
	/** @return Returns the schemeNameForm. */
	public String getSchemeNameForm() {
		return schemeNameForm;
	}
	/** @param schemeNameForm The schemeNameForm to set. */
	public void setSchemeNameForm(final String schemeNameForm) {
		this.schemeNameForm = schemeNameForm;
	}
	/** @return Returns the fileTypeNameForm. */
	public String getFileTypeNameForm() {
		return fileTypeNameForm;
	}
	/** @param fileTypeNameForm The fileTypeNameForm to set. */
	public void setFileTypeNameForm(final String fileTypeNameForm) {
		this.fileTypeNameForm = fileTypeNameForm;
	}
	/** @return Returns the fileFormatNameForm. */
	public String getFileFormatNameForm() {
		return fileFormatNameForm;
	}
	/** @param fileFormatNameForm The fileFormatNameForm to set. */
	public void setFileFormatNameForm(final String fileFormatNameForm) {
		this.fileFormatNameForm = fileFormatNameForm;
	}
	/** @return Returns the characterSetNameForm. */
	public String getCharacterSetNameForm() {
		return characterSetNameForm;
	}
	/** @param characterSetNameForm The characterSetNameForm to set. */
	public void setCharacterSetNameForm(final String characterSetNameForm) {
		this.characterSetNameForm = characterSetNameForm;
	}
	/** @return Returns the templates. */
    public HashMap<Integer, String> getTemplates() {
        return templates;
    }
    /** @param templates The templates to set. */
    public void setTemplates(final HashMap<Integer, String> templates) {
        this.templates = templates;
    }
    /** @return Returns the selectedTemplate. */
    public String getSelectedTemplate() {
        return selectedTemplate;
    }
    /** @param selectedTemplate The selectedTemplate to set. */
    public void setSelectedTemplate(final String selectedTemplate) {
        this.selectedTemplate = selectedTemplate;
    }
    /** @return Returns the templateName. */
    public String getTemplateName() {
        return templateName;
    }
    /** @param templateName The templateName to set. */
    public void setTemplateName(final String templateName) {
        this.templateName = templateName;
    }
    /** @return Returns the templateDescription. */
    public String getTemplateDescription() {
        return templateDescription;
    }
    /** @param templateDescription The templateDescription to set. */
    public void setTemplateDescription(final String templateDescription) {
        this.templateDescription = templateDescription;
    }
    /** @return Returns the showAddTemplateForm. */
    public boolean isShowAddTemplateForm() {
        return showAddTemplateForm;
    }
    /** @param showAddTemplateForm The showAddTemplateForm to set. */
    public void setShowAddTemplateForm(final boolean showAddTemplateForm) {
        this.showAddTemplateForm = showAddTemplateForm;
    }
    /** @return Returns the showEditTemplateForm. */
    public boolean isShowEditTemplateForm() {
        return showEditTemplateForm;
    }
    /** @param showEditTemplateForm The showEditTemplateForm to set. */
    public void setShowEditTemplateForm(final boolean showEditTemplateForm) {
        this.showEditTemplateForm = showEditTemplateForm;
    }
    /** @return Returns the tags. */
    public HashMap<String, Boolean> getTags() {
        return tags;
    }
    /** @param tags The tags to set. */
    public void setTags(final HashMap<String, Boolean> tags) {
        this.tags = tags;
    }
    /** @return Returns the selectedTags. */
    public List getSelectedTags() {
        return selectedTags;
    }
    /** @param selectedTags The selectedTags to set. */
    public void setSelectedTags(final List selectedTags) {
        this.selectedTags = selectedTags;
    }
    /** @return Returns the areTemplates. */
    public boolean isAreTemplates() {
        return areTemplates;
    }
    /** @param areTemplates The areTemplates to set. */
    public void setAreTemplates(final boolean areTemplates) {
        this.areTemplates = areTemplates;
    }
    /** @return Returns the showTheTemplate. */
    public boolean isShowTheTemplate() {
        return showTheTemplate;
    }
    /** @param showTheTemplate The showTheTemplate to set. */
    public void setShowTheTemplate(final boolean showTheTemplate) {
        this.showTheTemplate = showTheTemplate;
    }
    /** @return Returns the fileType. */
    public int getFileType() {
        return fileType;
    }
    /** @param fileType The fileType to set. */
    public void setFileType(final int fileType) {
        this.fileType = fileType;
    }
    /** @return Returns the fileTypeName. */
    public String getFileTypeName() {
        return fileTypeName;
    }
    /** @param fileTypeName The fileTypeName to set. */
    public void setFileTypeName(final String fileTypeName) {
        this.fileTypeName = fileTypeName;
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
	public int getTab() {
		return tab;
	}
	public void setTab(int tab) {
		this.tab = tab;
	}
}