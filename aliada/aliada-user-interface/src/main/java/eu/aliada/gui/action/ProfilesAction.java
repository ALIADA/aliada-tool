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
import java.util.HashMap;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**
 * @author iosa
 * @since 1.0
 */
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
    private HashMap<Integer, String> types;
    private HashMap<Integer, String> formats;
    private HashMap<Integer, String> characterSets;
    private boolean showAddProfileForm;
    private boolean showEditProfileForm;
    private boolean showTheProfile;
    private boolean areProfiles;

    private final Log logger = new Log(ManagingAction.class);

    /**
     * The method to show the profile list.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String showProfiles() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select * from aliada.profile");
            profiles = new HashMap<Integer, String>();
            while (rs.next()) {
                profiles.put(rs.getInt("profile_id"),
                        rs.getString("profile_name"));
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
        getSchemesDb();
        getCharacterSetsDb();
        getFormatsDb();
        getProfileTypesDb();
        getTypesDb();
        setShowAddProfileForm(false);
        setShowEditProfileForm(false);
        setShowTheProfile(false);
        return SUCCESS;
    }

    /**
     * The method to see the profile selected.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String getTheProfile() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select p.profile_name,p.profile_description,t.profile_name,m.metadata_name,f.file_type_name,"
                    		+ "fo.file_format_name,c.character_set_name from aliada.profile p INNER JOIN aliada.t_profile_type t ON p.profile_type_code=t.profile_code "
                    		+ "INNER JOIN aliada.t_metadata_scheme m ON p.metadata_scheme_code=m.metadata_code "
                    		+ "INNER JOIN aliada.t_file_type f ON p.file_type_code=f.file_type_code "
                    		+ "INNER JOIN aliada.t_file_format fo ON fo.file_format_code=p.file_format_code INNER JOIN aliada.t_character_set c "
                    		+ "ON c.character_set_code=p.character_set_code where p.profile_name='"
                            + this.selectedProfile + "'");
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
            showProfiles();
            return ERROR;
        }
    }

    /**
     * The method to show the profile form with the profile selected.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String showEditProfile() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select * from aliada.profile where profile_name='"
                            + this.selectedProfile + "'");
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
                this.showEditProfileForm = true;
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
            showProfiles();
            return ERROR;
        }
    }

    /**
     * The method to edit the profile selected.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String editProfile() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE aliada.profile set profile_type_code='"
                    + this.profileTypeForm + "',profile_description='"
                    + this.descriptionForm + "',metadata_scheme_code='"
                    + this.schemeForm + "',file_type_code='"
                    + this.fileTypeForm + "',file_format_code='"
                    + this.fileFormatForm + "',character_set_code='"
                    + this.characterSetForm + "' where profile_name='"
                    + this.nameForm + "'");
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

    /**
     * The method to delete the profile selected.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String deleteProfile() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            int correct = statement
                    .executeUpdate("DELETE FROM aliada.profile WHERE profile_name='"
                            + getSelectedProfile() + "'");
            statement.close();
            connection.close();
            if (correct == 0) {
                addActionError(getText("profile.not.selected"));
            } else {
                addActionMessage(getText("profile.delete.ok"));
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            showProfiles();
            return ERROR;
        }
        showProfiles();
        return SUCCESS;
    }

    /**
     * The method to add a new profile.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String addProfile() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO aliada.profile VALUES (default,'"
                    + this.nameForm + "', '" + this.profileTypeForm + "', '"
                    + this.descriptionForm + "', '" + this.schemeForm + "', '"
                    + this.fileTypeForm + "', '" + this.fileFormatForm + "', '"
                    + this.characterSetForm + "')");
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

    /**
     * The method to show the profile form empty.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String showAddProfile() {
        showProfiles();
        setShowAddProfileForm(true);
        return SUCCESS;
    }

    /**
     * The method to show the metadata scheme list.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String getSchemesDb() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select * from aliada.t_metadata_scheme");
            schemes = new HashMap<Integer, String>();
            while (rs.next()) {
                schemes.put(rs.getInt("metadata_code"),
                        rs.getString("metadata_name"));
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * The method to show the profile type list.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String getProfileTypesDb() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select * from aliada.t_profile_type");
            profileTypes = new HashMap<Integer, String>();
            while (rs.next()) {
                profileTypes.put(rs.getInt("profile_code"),
                        rs.getString("profile_name"));
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * The method to show the file type list.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String getTypesDb() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select * from aliada.t_file_type");
            types = new HashMap<Integer, String>();
            while (rs.next()) {
                types.put(rs.getInt("file_type_code"),
                        rs.getString("file_type_name"));
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * The method to show the file format list.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String getFormatsDb() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select * from aliada.t_file_format");
            formats = new HashMap<Integer, String>();
            while (rs.next()) {
                formats.put(rs.getInt("file_format_code"),
                        rs.getString("file_format_name"));
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * The method to show the character set list.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String getCharacterSetsDb() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select * from aliada.t_character_set");
            characterSets = new HashMap<Integer, String>();
            while (rs.next()) {
                characterSets.put(rs.getInt("character_set_code"),
                        rs.getString("character_set_name"));
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * @return Returns the profiles.
     * @exception
     * @since 1.0
     */
    public HashMap<Integer, String> getProfiles() {
        return profiles;
    }

    /**
     * @param profiles
     *            The profiles to set.
     * @exception
     * @since 1.0
     */
    public void setProfiles(final HashMap<Integer, String> profiles) {
        this.profiles = profiles;
    }

    /**
     * @return Returns the selectedProfile.
     * @exception
     * @since 1.0
     */
    public String getSelectedProfile() {
        return selectedProfile;
    }

    /**
     * @param selectedProfile
     *            The selectedProfile to set.
     * @exception
     * @since 1.0
     */
    public void setSelectedProfile(final String selectedProfile) {
        this.selectedProfile = selectedProfile;
    }

    /**
     * @return Returns the nameForm.
     * @exception
     * @since 1.0
     */
    public String getNameForm() {
        return nameForm;
    }

    /**
     * @param nameForm
     *            The nameForm to set.
     * @exception
     * @since 1.0
     */
    public void setNameForm(final String nameForm) {
        this.nameForm = nameForm;
    }

    /**
     * @return Returns the descriptionForm.
     * @exception
     * @since 1.0
     */
    public String getDescriptionForm() {
        return descriptionForm;
    }

    /**
     * @param descriptionForm
     *            The descriptionForm to set.
     * @exception
     * @since 1.0
     */
    public void setDescriptionForm(final String descriptionForm) {
        this.descriptionForm = descriptionForm;
    }

    /**
     * @return Returns the profileTypeForm.
     * @exception
     * @since 1.0
     */
    public int getProfileTypeForm() {
        return profileTypeForm;
    }

    /**
     * @param profileTypeForm
     *            The profileTypeForm to set.
     * @exception
     * @since 1.0
     */
    public void setProfileTypeForm(final int profileTypeForm) {
        this.profileTypeForm = profileTypeForm;
    }

    /**
     * @return Returns the schemeForm.
     * @exception
     * @since 1.0
     */
    public int getSchemeForm() {
        return schemeForm;
    }

    /**
     * @param schemeForm
     *            The schemeForm to set.
     * @exception
     * @since 1.0
     */
    public void setSchemeForm(final int schemeForm) {
        this.schemeForm = schemeForm;
    }

    /**
     * @return Returns the fileTypeForm.
     * @exception
     * @since 1.0
     */
    public int getFileTypeForm() {
        return fileTypeForm;
    }

    /**
     * @param fileTypeForm
     *            The fileTypeForm to set.
     * @exception
     * @since 1.0
     */
    public void setFileTypeForm(final int fileTypeForm) {
        this.fileTypeForm = fileTypeForm;
    }

    /**
     * @return Returns the fileFormatForm.
     * @exception
     * @since 1.0
     */
    public int getFileFormatForm() {
        return fileFormatForm;
    }

    /**
     * @param fileFormatForm
     *            The fileFormatForm to set.
     * @exception
     * @since 1.0
     */
    public void setFileFormatForm(final int fileFormatForm) {
        this.fileFormatForm = fileFormatForm;
    }

    /**
     * @return Returns the characterSetForm.
     * @exception
     * @since 1.0
     */
    public int getCharacterSetForm() {
        return characterSetForm;
    }

    /**
     * @param characterSetForm
     *            The characterSetForm to set.
     * @exception
     * @since 1.0
     */
    public void setCharacterSetForm(final int characterSetForm) {
        this.characterSetForm = characterSetForm;
    }

    /**
     * @return Returns the schemes.
     * @exception
     * @since 1.0
     */
    public HashMap<Integer, String> getSchemes() {
        return schemes;
    }

    /**
     * @param schemes
     *            The schemes to set.
     * @exception
     * @since 1.0
     */
    public void setSchemes(final HashMap<Integer, String> schemes) {
        this.schemes = schemes;
    }

    /**
     * @return Returns the profileTypes.
     * @exception
     * @since 1.0
     */
    public HashMap<Integer, String> getProfileTypes() {
        return profileTypes;
    }

    /**
     * @param profileTypes
     *            The profileTypes to set.
     * @exception
     * @since 1.0
     */
    public void setProfileTypes(final HashMap<Integer, String> profileTypes) {
        this.profileTypes = profileTypes;
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
     * @param types
     *            The types to set.
     * @exception
     * @since 1.0
     */
    public void setTypes(final HashMap<Integer, String> types) {
        this.types = types;
    }

    /**
     * @return Returns the formats.
     * @exception
     * @since 1.0
     */
    public HashMap<Integer, String> getFormats() {
        return formats;
    }

    /**
     * @param formats
     *            The formats to set.
     * @exception
     * @since 1.0
     */
    public void setFormats(final HashMap<Integer, String> formats) {
        this.formats = formats;
    }

    /**
     * @return Returns the characterSets.
     * @exception
     * @since 1.0
     */
    public HashMap<Integer, String> getCharacterSets() {
        return characterSets;
    }

    /**
     * @param characterSets
     *            The characterSets to set.
     * @exception
     * @since 1.0
     */
    public void setCharacterSets(final HashMap<Integer, String> characterSets) {
        this.characterSets = characterSets;
    }

    /**
     * @return Returns the showAddProfileForm.
     * @exception
     * @since 1.0
     */
    public boolean isShowAddProfileForm() {
        return showAddProfileForm;
    }

    /**
     * @param showAddProfileForm
     *            The showAddProfileForm to set.
     * @exception
     * @since 1.0
     */
    public void setShowAddProfileForm(final boolean showAddProfileForm) {
        this.showAddProfileForm = showAddProfileForm;
    }

    /**
     * @return Returns the showEditProfileForm.
     * @exception
     * @since 1.0
     */
    public boolean isShowEditProfileForm() {
        return showEditProfileForm;
    }

    /**
     * @param showEditProfileForm
     *            The showEditProfileForm to set.
     * @exception
     * @since 1.0
     */
    public void setShowEditProfileForm(final boolean showEditProfileForm) {
        this.showEditProfileForm = showEditProfileForm;
    }

    /**
     * @return Returns the areProfiles.
     * @exception
     * @since 1.0
     */
    public boolean isAreProfiles() {
        return areProfiles;
    }

    /**
     * @param areProfiles
     *            The areProfiles to set.
     * @exception
     * @since 1.0
     */
    public void setAreProfiles(final boolean areProfiles) {
        this.areProfiles = areProfiles;
    }

    /**
     * @return Returns the showTheProfile.
     * @exception
     * @since 1.0
     */
    public boolean isShowTheProfile() {
        return showTheProfile;
    }

    /**
     * @param showTheProfile
     *            The showTheProfile to set.
     * @exception
     * @since 1.0
     */
    public void setShowTheProfile(final boolean showTheProfile) {
        this.showTheProfile = showTheProfile;
    }

    /**
     * @return Returns the profileTypeNameForm.
     * @exception
     * @since 1.0
     */
    public String getProfileTypeNameForm() {
        return profileTypeNameForm;
    }

    /**
     * @param profileTypeNameForm The profileTypeNameForm to set.
     * @exception
     * @since 1.0
     */
    public void setProfileTypeNameForm(final String profileTypeNameForm) {
        this.profileTypeNameForm = profileTypeNameForm;
    }

    /**
     * @return Returns the schemeNameForm.
     * @exception
     * @since 1.0
     */
    public String getSchemeNameForm() {
        return schemeNameForm;
    }

    /**
     * @param schemeNameForm The schemeNameForm to set.
     * @exception
     * @since 1.0
     */
    public void setSchemeNameForm(final String schemeNameForm) {
        this.schemeNameForm = schemeNameForm;
    }

    /**
     * @return Returns the fileTypeNameForm.
     * @exception
     * @since 1.0
     */
    public String getFileTypeNameForm() {
        return fileTypeNameForm;
    }

    /**
     * @param fileTypeNameForm The fileTypeNameForm to set.
     * @exception
     * @since 1.0
     */
    public void setFileTypeNameForm(final String fileTypeNameForm) {
        this.fileTypeNameForm = fileTypeNameForm;
    }

    /**
     * @return Returns the fileFormatNameForm.
     * @exception
     * @since 1.0
     */
    public String getFileFormatNameForm() {
        return fileFormatNameForm;
    }

    /**
     * @param fileFormatNameForm The fileFormatNameForm to set.
     * @exception
     * @since 1.0
     */
    public void setFileFormatNameForm(final String fileFormatNameForm) {
        this.fileFormatNameForm = fileFormatNameForm;
    }

    /**
     * @return Returns the characterSetNameForm.
     * @exception
     * @since 1.0
     */
    public String getCharacterSetNameForm() {
        return characterSetNameForm;
    }

    /**
     * @param characterSetNameForm The characterSetNameForm to set.
     * @exception
     * @since 1.0
     */
    public void setCharacterSetNameForm(final String characterSetNameForm) {
        this.characterSetNameForm = characterSetNameForm;
    }

}
