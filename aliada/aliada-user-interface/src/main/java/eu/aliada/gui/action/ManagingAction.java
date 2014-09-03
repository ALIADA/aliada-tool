// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.inputValidation.CheckImportError;
import eu.aliada.inputValidation.VisualizeXML;
import eu.aliada.inputValidation.WellFormed;
import eu.aliada.inputValidation.XMLValidation;
import eu.aliada.shared.log.Log;

/**
 * This class is to support the profiles, import the file to publish and do the
 * input validation.
 * 
 * @author iosa
 * @since 1.0
 */
public class ManagingAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private String selectedProfile;
	private String nameForm;
	private String descriptionForm;
	private int profileTypeForm;
	private int schemeForm;
	private int fileTypeForm;
	private int fileFormatForm;
	private int characterSetForm;
	private HashMap<Integer, String> profiles;
	private HashMap<Integer, String> schemes;
	private HashMap<Integer, String> profileTypes;
	private HashMap<Integer, String> types;
	private HashMap<Integer, String> formats;
	private HashMap<Integer, String> characterSets;
	private boolean showAddProfileForm;
	private boolean showEditProfileForm;
	private boolean showNextButton;
	private boolean enableErrorLogButton;
	private boolean areProfiles;
	private File importFile;
	private String importFileFileName;
	private String profilesSelect;

	private static final String VISUALIZE_PATH = "src/main/resources/xmlVisualize/";
	private static final String VALIDATOR_PATH = "src/main/resources/xmlValidators/";
	private static final String ERROR_CONTENT_PATH = "src/main/webapp/content/errorContent.jsp";
	private final Log logger = new Log(ManagingAction.class);

	/**
	 * The method is to import the file in the institution path and do the input
	 * validation.
	 * 
	 * @return String
	 * @see
	 * @since 1.0
	 */
	public String importXML() {
		CheckImportError.inicialize();
		setShowNextButton(false);
		String message;
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.removeAttribute("importFile");
		WellFormed wf = new WellFormed();
		if (importFile == null) {
			addActionError(getText("err.not.file"));
			logger.debug(MessageCatalog._00020_MANAGE_FILE_NOT_FOUND);
			message = ERROR;
			showProfiles();
		} else if (!wf.isWellFormedXML(importFile.getPath())) {
			addActionError(getText("err.xml.wrong"));
			logger.debug(MessageCatalog._00021_MANAGE_NOT_VALIDATED_BY_WELL_FORMED);
			message = ERROR;
			showProfiles();
		} else {
			Connection connection = null;
			try {
				connection = new DBConnectionManager().getConnection();
				Statement statement = connection.createStatement();
				ResultSet rs = statement
						.executeQuery("select metadata_scheme_code,file_type_code from aliada.profile where profile_id='"
								+ this.profilesSelect + "'");
				rs.next();
				int validationType = rs.getInt(1);
				int fileType = rs.getInt(2);
				statement = connection.createStatement();
				rs = statement
						.executeQuery("select metadata_conversion_file from aliada.t_metadata_scheme where metadata_code='"
								+ validationType + "'");
				rs.next();
				String validatorPath = rs.getString(1);
				statement = connection.createStatement();
				rs = statement
						.executeQuery("select file_type_conversion_file from aliada.t_file_type where file_type_code='"
								+ fileType + "'");
				rs.next();
				String visualizeTypePath = rs.getString(1);
				rs.close();
				statement.close();
				XMLValidation xmlVal = new XMLValidation();
				if (xmlVal.isValidatedXMLFile(importFile.getPath(),
						VALIDATOR_PATH + validatorPath)) {
					message = SUCCESS;
					VisualizeXML vx = new VisualizeXML();
					if (vx.toStyledDocument(importFile.getPath(),
							VISUALIZE_PATH + visualizeTypePath,
							ERROR_CONTENT_PATH)) {
						if (CheckImportError.getCount() == 0) {
							statement = connection.createStatement();
							rs = statement
									.executeQuery("select organisation_path FROM organisation");
							rs.next();
							String filePath = rs.getString(1);
							rs.close();
							statement.close();
							File fileCreated = new File(filePath,
									this.importFileFileName);
							FileUtils.copyFile(this.importFile, fileCreated);
							session.setAttribute("importFile", fileCreated);
							session.setAttribute("profile", this.profilesSelect);
							addActionMessage(getText("correct.file"));
							showProfiles();
							setShowNextButton(true);
							setEnableErrorLogButton(false);
						} else {
							addActionError(getText("err.not.validated"));
							logger.debug(MessageCatalog._00022_MANAGE_NOT_VALIDATED_BY_VISUALIZE_MANDATORY);
							showProfiles();
							setEnableErrorLogButton(true);
						}
					} else {
						addActionError(getText("err.not.validated"));
						logger.debug(MessageCatalog._00023_MANAGE_NOT_VALIDATED_BY_VISUALIZE);
						message = ERROR;
						showProfiles();
					}
				} else {
					addActionError(getText("err.wrong.file"));
					logger.debug(MessageCatalog._00024_MANAGE_NOT_VALIDATED_BY_VALIDATION);
					message = ERROR;
					showProfiles();
				}
				connection.close();
			} catch (SQLException e) {
				logger.debug(MessageCatalog._00011_SQL_EXCEPTION);
				e.printStackTrace();
				showProfiles();
				message = ERROR;
			} catch (IOException e) {
				e.printStackTrace();
				showProfiles();
				message = ERROR;
			}
		}
		return message;
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
			logger.debug(MessageCatalog._00011_SQL_EXCEPTION);
			e.printStackTrace();
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
			statement.executeUpdate("UPDATE profile set profile_type_code='"
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
			logger.debug(MessageCatalog._00011_SQL_EXCEPTION);
			e.printStackTrace();
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
			}
			else{
	            addActionMessage(getText("profile.delete.ok"));			    
			}
		} catch (SQLException e) {
			logger.debug(MessageCatalog._00011_SQL_EXCEPTION);
			e.printStackTrace();
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
			statement.executeUpdate("INSERT INTO profile VALUES (default,'"
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
			logger.debug(MessageCatalog._00011_SQL_EXCEPTION);
			e.printStackTrace();
			return ERROR;
		}
	}

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
			logger.debug(MessageCatalog._00011_SQL_EXCEPTION);
			e.printStackTrace();
			return ERROR;
		}
		getSchemesDb();
		getCharacterSetsDb();
		getFormatsDb();
		getProfileTypesDb();
		getTypesDb();
		setShowAddProfileForm(false);
		setShowEditProfileForm(false);
		setShowNextButton(false);
		if (ServletActionContext.getRequest().getSession()
				.getAttribute("importFile") != null) {
			setShowNextButton(true);
		}
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
			logger.debug(MessageCatalog._00011_SQL_EXCEPTION);
			e.printStackTrace();
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
			logger.debug(MessageCatalog._00011_SQL_EXCEPTION);
			e.printStackTrace();
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
			logger.debug(MessageCatalog._00011_SQL_EXCEPTION);
			e.printStackTrace();
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
			logger.debug(MessageCatalog._00011_SQL_EXCEPTION);
			e.printStackTrace();
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
			logger.debug(MessageCatalog._00011_SQL_EXCEPTION);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
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
	 * @return Returns the importFile.
	 * @exception
	 * @since 1.0
	 */
	public File getImportFile() {
		return importFile;
	}

	/**
	 * @return Returns the profilesSelect.
	 * @exception
	 * @since 1.0
	 */
	public String getProfilesSelect() {
		return profilesSelect;
	}

	/**
	 * @param profilesSelect
	 *            The profilesSelect to set.
	 * @exception
	 * @since 1.0
	 */
	public void setProfilesSelect(final String profilesSelect) {
		this.profilesSelect = profilesSelect;
	}

	/**
	 * @param importFile
	 *            The importFile to set.
	 * @exception
	 * @since 1.0
	 */
	public void setImportFile(final File importFile) {
		this.importFile = importFile;
	}

	/**
	 * @return Returns the showNextButton.
	 * @exception
	 * @since 1.0
	 */
	public boolean isShowNextButton() {
		return showNextButton;
	}

	/**
	 * @param showNextButton
	 *            The showNextButton to set.
	 * @exception
	 * @since 1.0
	 */
	public void setShowNextButton(final boolean showNextButton) {
		this.showNextButton = showNextButton;
	}

	/**
	 * @return Returns the importFileFileName.
	 * @exception
	 * @since 1.0
	 */
	public String getImportFileFileName() {
		return importFileFileName;
	}

	/**
	 * @param importFileFileName
	 *            The importFileFileName to set.
	 * @exception
	 * @since 1.0
	 */
	public void setImportFileFileName(final String importFileFileName) {
		this.importFileFileName = importFileFileName;
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
	 * @return Returns the enableErrorLogButton.
	 * @exception
	 * @since 1.0
	 */

	public boolean isEnableErrorLogButton() {
		return enableErrorLogButton;
	}

	/**
	 * @param enableErrorLogButton
	 *            The enableErrorLogButton to set.
	 * @exception
	 * @since 1.0
	 */

	public void setEnableErrorLogButton(final boolean enableErrorLogButton) {
		this.enableErrorLogButton = enableErrorLogButton;
	}

}
