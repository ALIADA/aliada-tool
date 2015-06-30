// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;



import java.util.ResourceBundle;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**@author xabi
 * @version $Revision: 1.1 $, $Date: 2015/03/23 15:20:54 $
 * @since 1.0 */
public class ProfilesAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
    private HashMap<Integer, String> types;
    private boolean showAddProfileForm;
    private boolean showEditProfileForm;
    private boolean showTheProfile;
    private boolean areProfiles;
    
    private final Log logger = new Log(ProfilesAction.class);
    private ResourceBundle defaults = ResourceBundle.getBundle("defaultValues", getLocale());
    
    /** The method to show the profile list.
     * @return String */
    public String showProfiles() {
    	
    	ServletActionContext.getRequest().getSession().setAttribute("action", defaults.getString("lang.showProfiles"));
    	
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
        Methods m = new Methods();
        m.setLang(getLocale().getISO3Language());
        schemes = m.getSchemesDb();
        characterSets = m.getCharacterSetsDb();
        formats = m.getFormatsDb();
        profileTypes = m.getProfileTypesDb();
        types = m.getTypesDb();
        return SUCCESS;
    }
    
    /** The method to see the profile selected.
     * @return String */
    public String getTheProfile() {
    	
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            
            ResultSet rs = statement.executeQuery("select p.profile_name,p.profile_description,t.profile_name,m.metadata_name,f.file_type_name,"
            		+ "fo.file_format_name,c.character_set_name from aliada.profile p INNER JOIN aliada.t_profile_type t ON p.profile_type_code=t.profile_code "
            		+ "INNER JOIN aliada.t_metadata_scheme m ON p.metadata_scheme_code=m.metadata_code "
            		+ "INNER JOIN aliada.t_file_type f ON p.file_type_code=f.file_type_code "
            		+ "INNER JOIN aliada.t_file_format fo ON fo.file_format_code=p.file_format_code INNER JOIN aliada.t_character_set c "
            		+ "ON c.character_set_code=p.character_set_code where p.profile_name='" + this.selectedProfile 
            		+ "' AND t.language = '" + getLocale().getISO3Language() + "' AND m.language = '" + getLocale().getISO3Language() + "' "
            		+ "AND f.language = '" + getLocale().getISO3Language() + "' AND fo.language = '" + getLocale().getISO3Language() + "' "
            		+ "AND c.language = '" + getLocale().getISO3Language() + "'");
            
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
                
                ServletActionContext.getRequest().getSession().setAttribute("action", defaults.getString("lang.default"));
                
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
    
    /** The method to add a new profile.
     * @return String */
    public String addProfile() {	
    	
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            PreparedStatement preparedStatement;
            preparedStatement = connection
                    .prepareStatement(
                            "INSERT INTO `aliada`.`profile` (`profile_name`, `profile_type_code`, `profile_description`, `metadata_scheme_code`, "
                            + "`file_type_code`, `file_format_code`, `character_set_code`) VALUES (?, ?, ?, ?, ?, ?, ?);");
            preparedStatement.setString(1, this.nameForm);
            preparedStatement.setInt(2, this.profileTypeForm);
            preparedStatement.setString(3, this.descriptionForm);
            preparedStatement.setInt(4, this.schemeForm);
            preparedStatement.setInt(5, this.fileTypeForm);
            preparedStatement.setInt(6, this.fileFormatForm);
            preparedStatement.setInt(7, this.characterSetForm);
            preparedStatement.executeUpdate();
            preparedStatement.close();
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
    	
    	showProfiles();
    	
    	ServletActionContext.getRequest().getSession().setAttribute("action", defaults.getString("lang.default"));
    	
        setShowAddProfileForm(true);
        return SUCCESS;
    }
    
    /** The method to edit the profile selected.
     * @return String */
    public String editProfile() {	
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
    
    /** The method to show the profile form with the profile selected.
     * @return String */
    public String showEditProfile() {	
    	if (this.selectedProfile != null) {
	    	if (getSelectedProfile().equalsIgnoreCase(defaults.getString("profile.bib")) 
	    	|| getSelectedProfile().equalsIgnoreCase(defaults.getString("profile.aut")) 
	    	|| getSelectedProfile().equalsIgnoreCase(defaults.getString("profile.lido")) 
	    	|| getSelectedProfile().equalsIgnoreCase(defaults.getString("profile.dc"))) {
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
		                
		                ServletActionContext.getRequest().getSession().setAttribute("action", defaults.getString("lang.default"));
		                
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
    
    /** The method to delete the profile selected.
     * @return String */
    public String deleteProfile() {	
    	if (this.selectedProfile != null) {
	    	if (getSelectedProfile().equalsIgnoreCase(defaults.getString("profile.bib")) 
	    	|| getSelectedProfile().equalsIgnoreCase(defaults.getString("profile.aut")) 
	    	|| getSelectedProfile().equalsIgnoreCase(defaults.getString("profile.lido")) 
	    	|| getSelectedProfile().equalsIgnoreCase(defaults.getString("profile.dc"))) {
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
	/** @return Returns the profileTypeForm. */
	public int getProfileTypeForm() {
		return profileTypeForm;
	}
	/** @param profileTypeForm The profileTypeForm to set. */
	public void setProfileTypeForm(final int profileTypeForm) {
		this.profileTypeForm = profileTypeForm;
	}
	/** @return Returns the schemeForm. */
	public int getSchemeForm() {
		return schemeForm;
	}
	/** @param schemeForm The schemeForm to set. */
	public void setSchemeForm(final int schemeForm) {
		this.schemeForm = schemeForm;
	}
	/** @return Returns the fileTypeForm. */
	public int getFileTypeForm() {
		return fileTypeForm;
	}
	/** @param fileTypeForm The fileTypeForm to set. */
	public void setFileTypeForm(final int fileTypeForm) {
		this.fileTypeForm = fileTypeForm;
	}
	/** @return Returns the fileFormatForm. */
	public int getFileFormatForm() {
		return fileFormatForm;
	}
	/** @param fileFormatForm The fileFormatForm to set. */
	public void setFileFormatForm(final int fileFormatForm) {
		this.fileFormatForm = fileFormatForm;
	}
	/** @return Returns the characterSetForm. */
	public int getCharacterSetForm() {
		return characterSetForm;
	}
	/** @param characterSetForm The characterSetForm to set. */
	public void setCharacterSetForm(final int characterSetForm) {
		this.characterSetForm = characterSetForm;
	}
	
}
