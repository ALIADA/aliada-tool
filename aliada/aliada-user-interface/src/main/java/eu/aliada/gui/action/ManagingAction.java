// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
	private int enableNextButton;
	private File importFile;
	private String importFileFileName;
	private List<FileWork> importedFiles;
	private String selectedFile;
	private String profileSelected;
 
	 // Local server
//    private static final String VISUALIZE_PATH = "src/main/resources/xmlVisualize/";
//    private static final String VALIDATOR_PATH = "src/main/resources/xmlValidators/";
//    private static final String ERROR_CONTENT_PATH = "src/main/webapp/content/errorContent.jsp";
   
     // Server
	  private static final String VISUALIZE_PATH = "webapps/aliada-user-interface-2.0/WEB-INF/classes/xmlVisualize/";
	  private static final String VALIDATOR_PATH = "webapps/aliada-user-interface-2.0/WEB-INF/classes/xmlValidators/";
	  private static final String ERROR_CONTENT_PATH = "webapps/aliada-user-interface-2.0/content/errorContent.jsp";
    
	private final Log logger = new Log(ManagingAction.class);
	/**
     * Get the profiles from DB.
     * @return String
     * @see
     * @since 1.0
     */
    public String execute() {
        // getProfilesDb();
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
			enableNextBut(session);
		} else if (!wf.isWellFormedXML(importFile.getPath())) {
			addActionError(getText("err.xml.wrong"));
			logger.debug(MessageCatalog._00021_MANAGE_NOT_VALIDATED_BY_WELL_FORMED);
			message = ERROR;
            getProfilesDb();
            enableNextBut(session);
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
							String fileNameIDWithOutExt =  fileNameWithOutExt + System.currentTimeMillis();
							String fileNameIDWithExt =  fileNameIDWithOutExt + ".xml";
							File fileCreated = new File(filePath, fileNameIDWithExt);
							FileUtils.copyFile(this.importFile, fileCreated);
							FileWork fileWork = new FileWork();
							fileWork.setFile(fileCreated);
							fileWork.setFilename(fileCreated.getName());
                            fileWork.setStatus("idle");
							fileWork.setProfile(getProfileNameFromCode(this.profileSelected));
							/* Get data from session*/
							if (session.getAttribute("importedFiles") == null) {
							    importedFiles = new ArrayList<FileWork>();
							    importedFiles.add(fileWork);
							} else {
							    importedFiles = (ArrayList<FileWork>) session.getAttribute("importedFiles");
							    importedFiles.add(fileWork);
							}
							session.setAttribute("rdfizerStatus", "idle");
							session.setAttribute("importedFiles", importedFiles);
							
							/***FIN GUARDAR EN SESION***/
							/***GUARDAR EN BD EL FICHERO SELECCIONADO***/	
							String usernameLogged = (String) ServletActionContext.getRequest().getSession().getAttribute("logedUser");

							PreparedStatement preparedStatement = connection
					                .prepareStatement(
					                        "INSERT INTO aliada.user_session (user_name,file_name,datafile,profile,status) VALUES(?,?,?,?,?)",
					                        PreparedStatement.RETURN_GENERATED_KEYS);
							preparedStatement.setString(1, usernameLogged);
							preparedStatement.setString(2, fileNameIDWithExt);
					        preparedStatement.setString(3, filePath);
					        preparedStatement.setString(4, this.profileSelected);
					        preparedStatement.setString(5, "idle");
					        preparedStatement.executeUpdate();
					        preparedStatement.close(); 
					        connection.close();
					        
							/***FIN GUARDAR EN BD***/
					        
							logger.debug(MessageCatalog._00026_MANAGE_VALIDATED);
							addActionMessage(getText("correct.file"));
				            getProfilesDb();
							setEnableErrorLogButton(0);
							setEnableNextButton(1);
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
				enableNextBut(session);
				connection.close();
			} catch (SQLException e) {
				logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
	            getProfilesDb();
				message = ERROR;
			} catch (IOException e) {
			    logger.error(MessageCatalog._00012_IO_EXCEPTION, e);
	            getProfilesDb();
				message = ERROR;
			}
		}
		return message;
	}
	/**
     * Enable the Next Button.
     * @param session The user_session
     * @return 
     * @see
     * @since 1.0
     */
	private void enableNextBut(final HttpSession session) {
        
	    if (session.getAttribute("importedFiles") != null) {
            setEnableNextButton(1);
        }
        
    }
	/**
     * Get the files saved in the user_session.
     * @return 
     * @see
     * @since 1.0
     */
    private void getFiles() {
	    if (ServletActionContext.getRequest().getSession().getAttribute("importedFiles") != null) {
	        importedFiles = (ArrayList<FileWork>) ServletActionContext.getRequest().getSession().getAttribute("importedFiles");
	    }	    
	}
    /**
     * Save the user_session files.
     * @return String
     * @see
     * @since 1.0
     */
	public String saveFilesToConversion() {
//	    List<FileWork> conversionFiles = new ArrayList<FileWork>();
//	    for (FileWork file : importedFiles){
//            if (file.isFileChecked()){
//                conversionFiles.add(file);
//            }
//	    }
	    //ServletActionContext.getRequest().getSession().setAttribute("conversionFiles", conversionFiles);
	    if (ServletActionContext.getRequest().getSession().getAttribute("importedFiles") != null) {
	        importedFiles = (ArrayList<FileWork>) ServletActionContext.getRequest().getSession().getAttribute("importedFiles");
	        for (FileWork file : importedFiles) {
	              if (file.getFilename().equals(this.selectedFile)) {
	                  ServletActionContext.getRequest().getSession().setAttribute("importedFile", file);
	              }
	        }   
	        return SUCCESS;
	    } else {
	        logger.error(MessageCatalog._00027_MANAGE_FILE_NOT_SAVE);
	        return ERROR;
	    }	    
	/* Get from Database
		getFilesDb();
	    if(importedFiles != null){
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
	     */
	}
	
	/**
	 * The method to show the profile list.
	 * 
	 * @return String
	 * @see
	 * @since 1.0
	 */
	
	public String getFilesDb() {
		HttpSession session = ServletActionContext.getRequest().getSession();		
		String usernameLogged = (String) ServletActionContext.getRequest().getSession().getAttribute("logedUser");
		Connection connection = null;
        try {
			connection = new DBConnectionManager().getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement
					.executeQuery("select user_name,file_name,datafile,profile,template,graph,status,job_id from "
							+ "aliada.user_session where user_name ='" + usernameLogged + "'");
			if (rs != null) {
				if (session.getAttribute("importedFiles") == null) {
				    importedFiles = new ArrayList<FileWork>();
				}
				while (rs.next()) {
					File fileCreated = new File(rs.getString("datafile"), rs.getString("file_name"));
					FileWork fileWork = new FileWork();
					fileWork.setFilename(fileCreated.getName());
					fileWork.setFile(fileCreated);
					fileWork.setProfile(getProfileNameFromCode(Integer.toString(rs.getInt("profile"))));
					if (rs.getString("template") != null) {
						fileWork.setTemplate(rs.getString("template").toString());
					}
					if (rs.getString("graph") != null) {
						fileWork.setGraph(rs.getString("graph").toString());
					}
					fileWork.setStatus(rs.getString("status"));
	               
					importedFiles.add(fileWork);
				}
			}
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
			return ERROR;
		}
		setEnableErrorLogButton(0);
		return SUCCESS;
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
		/*getFilesDb();*/
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
			logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
			return ERROR;
		}
		setEnableErrorLogButton(0);
		return SUCCESS;
	}
	/**
     * Get the profile name.
     * @param selectedProfile The selected profile.
     * @return String
     * @see
     * @since 1.0
     */
	private String getProfileNameFromCode(final String selectedProfile) {
	    Connection connection = null;
	    String profileName = "";
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement;
            statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select profile_name from aliada.profile where profile_id=" + selectedProfile);
            if (rs.next()) {
                profileName = rs.getString("profile_name");
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
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

	public void setEnableErrorLogButton(final int enableErrorLogButton) {
		this.enableErrorLogButton = enableErrorLogButton;
	}
	
    /**
     * @return Returns the enableNextButton.
     * @exception
     * @since 1.0
     */
    public int getEnableNextButton() {
        return enableNextButton;
    }
    /**
     * @param enableNextButton The enableNextButton to set.
     * @exception
     * @since 1.0
     */
    public void setEnableNextButton(final int enableNextButton) {
        this.enableNextButton = enableNextButton;
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
    public void setImportedFiles(final List<FileWork> importedFiles) {
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
    public void setSelectedFile(final String selectedFile) {
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
    public void setProfileSelected(final String profileSelected) {
        this.profileSelected = profileSelected;
    }

}
