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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.model.FileWork;
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

	private HashMap<Integer, String> profiles;
	private int enableErrorLogButton;
	private File importFile;
	private String importFileFileName;
	private List<FileWork> importedFiles;
	private String selectedFile;
	private String profileSelected;
 
	
	private static final String VISUALIZE_PATH = "webapps/aliada-user-interface-1.0/WEB-INF/classes/xmlVisualize/";
    private static final String VALIDATOR_PATH = "webapps/aliada-user-interface-1.0/WEB-INF/classes/xmlValidators/";
    private static final String ERROR_CONTENT_PATH = "webapps/aliada-user-interface-1.0/content/errorContent.jsp";
    private final Log logger = new Log(ManagingAction.class);

    public String execute(){
        getProfilesDb();
        return SUCCESS;
    }
	/**
	 * The method is to import the file in the institution path and do the input
	 * validation.
	 * 
	 * @return String
	 * @see
	 * @since 1.0
	 */
	public String importXML() {
		CheckImportError.initialize();
		String message;
		HttpSession session = ServletActionContext.getRequest().getSession();
		WellFormed wf = new WellFormed();
		if (importFile == null) {
			addActionError(getText("err.not.file"));
			logger.debug(MessageCatalog._00020_MANAGE_FILE_NOT_FOUND);
			message = ERROR;
			getProfilesDb();
		} else if (!wf.isWellFormedXML(importFile.getPath())) {
			addActionError(getText("err.xml.wrong"));
			logger.debug(MessageCatalog._00021_MANAGE_NOT_VALIDATED_BY_WELL_FORMED);
			message = ERROR;
            getProfilesDb();
		} else {
			Connection connection = null;
			try {
				connection = new DBConnectionManager().getConnection();
				Statement statement = connection.createStatement();
				ResultSet rs = statement
						.executeQuery("select metadata_scheme_code,file_type_code from aliada.profile where profile_id='"
								+ this.profileSelected + "'");
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

						if (!CheckImportError.isFileCorrect()) {
							addActionError(getText("err.not.validated"));
							logger.debug(MessageCatalog._00022_MANAGE_NOT_VALIDATED_BY_VISUALIZE_FILE_TYPE);
				            getProfilesDb();
							setEnableErrorLogButton(1);
						} else if (CheckImportError.getCount() == 0) {
							statement = connection.createStatement();
							rs = statement
									.executeQuery("select organisation_path FROM organisation");
							rs.next();
							String filePath = rs.getString(1);
							rs.close();
							statement.close();
							String fileNameWithOutExt = FilenameUtils.removeExtension(this.importFileFileName);
							File fileCreated = new File(filePath,
							        fileNameWithOutExt+System.currentTimeMillis()+".xml");
							FileUtils.copyFile(this.importFile, fileCreated);
							FileWork fileWork = new FileWork();
							fileWork.setFile(fileCreated);
							fileWork.setFilename(fileCreated.getName());
                            fileWork.setStatus("idle");
							fileWork.setProfile(getProfileNameFromCode(this.profileSelected));
							if(session.getAttribute("importedFiles")==null){
							    importedFiles = new ArrayList<FileWork>();
							    importedFiles.add(fileWork);
							}
							else{
							    importedFiles = (ArrayList<FileWork>) session.getAttribute("importedFiles");
							    importedFiles.add(fileWork);
							}
							session.setAttribute("importedFiles", importedFiles);
							logger.debug(MessageCatalog._00026_MANAGE_VALIDATED);
							addActionMessage(getText("correct.file"));
				            getProfilesDb();
							setEnableErrorLogButton(0);
						} else {
							addActionError(getText("err.not.validated"));
							logger.debug(MessageCatalog._00023_MANAGE_NOT_VALIDATED_BY_VISUALIZE_MANDATORY);
				            getProfilesDb();
							setEnableErrorLogButton(1);
						}
					} else {
						addActionError(getText("err.not.validated"));
						logger.debug(MessageCatalog._00024_MANAGE_NOT_VALIDATED_BY_VISUALIZE);
						message = ERROR;
			            getProfilesDb();
                        setEnableErrorLogButton(1);
					}
				} else {
					addActionError(getText("err.wrong.file"));
					logger.debug(MessageCatalog._00025_MANAGE_NOT_VALIDATED_BY_VALIDATION);
					message = ERROR;
		            getProfilesDb();
				}
				connection.close();
			} catch (SQLException e) {
				logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
	            getProfilesDb();
				message = ERROR;
			} catch (IOException e) {
			    logger.error(MessageCatalog._00012_IO_EXCEPTION,e);
	            getProfilesDb();
				message = ERROR;
			}
		}
		return message;
	}
	private void getFiles(){
	    if(ServletActionContext.getRequest().getSession().getAttribute("importedFiles") != null){
	        importedFiles = (ArrayList<FileWork>) ServletActionContext.getRequest().getSession().getAttribute("importedFiles");
	    }	    
	}
	public String saveFilesToConversion(){
//	    List<FileWork> conversionFiles = new ArrayList<FileWork>();
//	    for (FileWork file : importedFiles){
//            if (file.isFileChecked()){
//                conversionFiles.add(file);
//            }
//	    }
	    //ServletActionContext.getRequest().getSession().setAttribute("conversionFiles", conversionFiles);
	    if(ServletActionContext.getRequest().getSession().getAttribute("importedFiles")!=null){
	        importedFiles = (ArrayList<FileWork>) ServletActionContext.getRequest().getSession().getAttribute("importedFiles");
	        for (FileWork file : importedFiles){
	              if (file.getFilename().equals(this.selectedFile)){
	                  ServletActionContext.getRequest().getSession().setAttribute("importedFile", file);
	              }
	        }   
	        return SUCCESS;
	    }
	    else{
	        logger.error(MessageCatalog._00027_MANAGE_FILE_NOT_SAVE);
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
	public String getProfilesDb() {
	    getFiles();
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
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
			return ERROR;
		}
		setEnableErrorLogButton(0);
		return SUCCESS;
	}
	private String getProfileNameFromCode(String selectedProfile){
	    Connection connection = null;
	    String profileName = "";
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement;
            statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select profile_name from aliada.profile where profile_id="+selectedProfile);
            if(rs.next()){
                profileName = rs.getString("profile_name");
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
            return "";
        }
        return profileName;
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
	 * @return Returns the importFile.
	 * @exception
	 * @since 1.0
	 */
	public File getImportFile() {
		return importFile;
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
	 * @return Returns the enableErrorLogButton.
	 * @exception
	 * @since 1.0
	 */

	public int getEnableErrorLogButton() {
		return enableErrorLogButton;
	}

	/**
	 * @param enableErrorLogButton
	 *            The enableErrorLogButton to set.
	 * @exception
	 * @since 1.0
	 */

	public void setEnableErrorLogButton(int enableErrorLogButton) {
		this.enableErrorLogButton = enableErrorLogButton;
	}
    /**
     * @return Returns the importedFiles.
     * @exception
     * @since 1.0
     */
    public List<FileWork> getImportedFiles() {
        return importedFiles;
    }
    /**
     * @param importedFiles The importedFiles to set.
     * @exception
     * @since 1.0
     */
    public void setImportedFiles(List<FileWork> importedFiles) {
        this.importedFiles = importedFiles;
    }
    /**
     * @return Returns the selectedFile.
     * @exception
     * @since 1.0
     */
    public String getSelectedFile() {
        return selectedFile;
    }
    /**
     * @param selectedFile The selectedFile to set.
     * @exception
     * @since 1.0
     */
    public void setSelectedFile(String selectedFile) {
        this.selectedFile = selectedFile;
    }
    /**
     * @return Returns the profileSelected.
     * @exception
     * @since 1.0
     */
    public String getProfileSelected() {
        return profileSelected;
    }
    /**
     * @param profileSelected The profileSelected to set.
     * @exception
     * @since 1.0
     */
    public void setProfileSelected(String profileSelected) {
        this.profileSelected = profileSelected;
    }

}
