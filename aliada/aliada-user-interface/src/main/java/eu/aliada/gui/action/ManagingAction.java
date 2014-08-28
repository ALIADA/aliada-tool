// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.parser.XmlParser;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.inputValidation.VisualizeXML;
import eu.aliada.inputValidation.XMLValidation;
import eu.aliada.inputValidation.WellFormed;
import eu.aliada.shared.log.Log;

/**
 * @author iosa
 * @since 1.0
 */
public class ManagingAction extends ActionSupport {
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
    private boolean areProfiles;
    private File importFile;
    private String importFileFileName;
    private String profilesSelect;
    private List<String> errorLog;
    
    private final static String HTMLLOGPATH = "target/classes/log.html";
    private final static String XMLLOGPATH = "target/classes/log.xml";
    private final Log logger = new Log(ManagingAction.class);

    public String importXML() {
        setShowNextButton(false); 
        String message;
        List<String> errorLog = new ArrayList<String>();
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.removeAttribute("errorLog");
        session.removeAttribute("importFile");
        WellFormed wf = new WellFormed();
        if (importFile == null) {
            addActionError(getText("err.not.file"));
            errorLog.add(getText("err.not.file"));
            session.setAttribute("errorLog", errorLog);
            message = ERROR;
            showProfiles();
        } else if (!wf.isWellFormedXML(importFile.getPath())) {
            addActionError(getText("err.xml.wrong"));
            errorLog.add(getText("err.xml.wrong"));
            session.setAttribute("errorLog", errorLog);
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
                XMLValidation xmlVal = new XMLValidation();
                if (xmlVal.isValidatedXMLFile(importFile.getPath(),
                        "target/classes/xmlValidators/" + validatorPath)) {
                    message = SUCCESS;
                    VisualizeXML vx = new VisualizeXML();
                    if(vx.toStyledDocument(importFile.getPath(),
                            "target/classes/xmlVisualize/" + visualizeTypePath,
                            HTMLLOGPATH)){
                        if(analiseLog(errorLog)){
                            addActionError(getText("correct.file"));
                            String filePath = session.getServletContext().getRealPath("/importedFiles");
                            File fileCreated = new File(filePath, this.importFileFileName);
                            FileUtils.copyFile(this.importFile, fileCreated);
                            session.setAttribute("importFile", fileCreated);
                            errorLog.add(getText("correct.file"));
                            session.setAttribute("errorLog", errorLog);
                            session.setAttribute("profile", this.profilesSelect);
                            showProfiles();
                            setShowNextButton(true); 
                        }
                        else{
                            addActionError(getText("err.not.validated"));
                            errorLog.add(getText("err.not.validated"));
                            session.setAttribute("errorLog", errorLog);
                            showProfiles();
                        }
                    }
                    else{
                        addActionError(getText("err.not.validated"));
                        errorLog.add(getText("err.not.validated"));
                        session.setAttribute("errorLog", errorLog);
                        message = ERROR;
                        showProfiles();                        
                    }
                } else {
                    addActionError(getText("err.wrong.file"));
                    errorLog.add(getText("err.wrong.file"));
                    session.setAttribute("errorLog", errorLog);
                    message = ERROR;
                    showProfiles();
                }
                rs.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                logger.debug("SQL error: "+e);
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
    private boolean analiseLog(List <String> errorLog){
        htmlToXml();
        File file = new File(XMLLOGPATH);
        boolean correct = true;
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
            XmlParser parser = new XmlParser();
            Document doc = parser.parseXML(inputStream);
            NodeList readNode = doc.getElementsByTagName("td");  
            if (readNode != null && readNode.getLength() > 0) {
                for (int i = 0; i < readNode.getLength(); i++) {
                    String readTag = readNode.item(i).getTextContent();
                    logger.debug("tag: "+readTag);
                    if(readTag.equals("ERROR")){
                        correct = false;
                    }
                    errorLog.add(readTag);
                }
            }       
        } catch (Exception e) {
            e.printStackTrace();
        }    
        return correct;
    }
    
    private void htmlToXml(){
        File file = new File(HTMLLOGPATH);
        FileInputStream fis =null;             
        try   
        {  
            fis = new FileInputStream(file);  
        }  
        catch (java.io.FileNotFoundException e)   
        {  
            logger.error("html log file not found");  
        }  
        Tidy tidy = new Tidy();  
        tidy.setXmlOut(true);  
        tidy.getQuoteAmpersand();
        Document xmlDoc = tidy.parseDOM(fis, null);  
        try  
        {  
            tidy.pprint(xmlDoc,new FileOutputStream(XMLLOGPATH));  
        }  
        catch(Exception e)  
        {  
        }  
    }
    
    public String showErrors(){
        errorLog = new ArrayList <String>();
        errorLog = (List<String>) ServletActionContext.getRequest().getSession().getAttribute("errorLog");
        return SUCCESS;
    }

    public String showAddProfile() {
        showProfiles();
        setShowAddProfileForm(true);
        return "success";
    }

    public String showEditProfile() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection(); 
            Statement statement = connection.createStatement();
            logger.debug("Profile selected to update: " + this.selectedProfile);
            ResultSet rs = statement
                    .executeQuery("select * from aliada.profile where profile_name='"
                            + this.selectedProfile + "'");
            if (rs.next()) {
                logger.debug("Accesed to: " + rs.getString("profile_name"));
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
            e.printStackTrace();
            showProfiles();
            return ERROR;
        }
    }

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
            logger.debug("updated profile: " + this.nameForm);
        }catch (SQLException e) {
            logger.error("SQL error: "+e);
            return ERROR;
        }
        return SUCCESS;
    }
    
    public String deleteProfile() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            int correct = statement.executeUpdate("DELETE FROM aliada.profile WHERE profile_name='"+getSelectedProfile()+"'");
            statement.close();
            connection.close();
            if(correct==0){
                logger.debug("Not profile selected for delete");
                addActionError(getText("profile.not.selected"));
            }
        } catch (SQLException e) {
            logger.error("SQL error deleting profile"+e);
            return ERROR;
        }
        return SUCCESS;            
    }

    public String addProfile() {
        Connection connection = null;
        int id;
        try {
            connection = new DBConnectionManager().getConnection(); 
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO profile VALUES (default,'" + this.nameForm + "', '" + this.profileTypeForm
                    + "', '" + this.descriptionForm + "', '"
                    + this.schemeForm + "', '" + this.fileTypeForm + "', '"
                    + this.fileFormatForm + "', '" + this.characterSetForm
                    + "')");
            logger.debug("Added profile :");
            statement.close();
            connection.close();
            return SUCCESS;
        } catch (SQLException e ) {
            logger.error("SQL error: "+e);
            return ERROR;
        }
    }

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
            if(profiles.isEmpty()){
                setAreProfiles(false);
            }
            else{
                setAreProfiles(true);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error("SQL error: "+e);
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
        if(ServletActionContext.getRequest().getSession().getAttribute("importFile")!=null){
            setShowNextButton(true);            
        }
        return SUCCESS;
    }

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
        }catch (SQLException e) {
            logger.error("SQL error: "+e);
            return "error";
        }
        return "success";
    }

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
        }catch (SQLException e) {
            logger.error("SQL error: "+e);
            return "error";
        }
        return "success";
    }

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
        }  catch (SQLException e) {
            logger.error("SQL error: "+e);
            return "error";
        }
        return "success";
    }

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
            logger.error("SQL error: "+e);
            return "error";
        }
        return "success";
    }
    
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
            logger.error("SQL error: "+e);
            return "error";
        }
        return "success";
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
    public void setSchemes(HashMap<Integer, String> schemes) {
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
    public void setProfileTypes(HashMap<Integer, String> profileTypes) {
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
    public void setTypes(HashMap<Integer, String> types) {
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
    public void setFormats(HashMap<Integer, String> formats) {
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
    public void setCharacterSets(HashMap<Integer, String> characterSets) {
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
    public void setProfiles(HashMap<Integer, String> profiles) {
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
    public void setShowAddProfileForm(boolean showAddProfileForm) {
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
    public void setShowEditProfileForm(boolean showEditProfileForm) {
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
    public void setNameForm(String nameForm) {
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
    public void setDescriptionForm(String descriptionForm) {
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
    public void setProfileTypeForm(int profileTypeForm) {
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
    public void setSchemeForm(int schemeForm) {
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
    public void setFileTypeForm(int fileTypeForm) {
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
    public void setFileFormatForm(int fileFormatForm) {
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
    public void setCharacterSetForm(int characterSetForm) {
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
    public void setSelectedProfile(String selectedProfile) {
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
    public void setProfilesSelect(String profilesSelect) {
        this.profilesSelect = profilesSelect;
    }

    /**
     * @param importFile
     *            The importFile to set.
     * @exception
     * @since 1.0
     */
    public void setImportFile(File importFile) {
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
     * @param showNextButton The showNextButton to set.
     * @exception
     * @since 1.0
     */
    public void setShowNextButton(boolean showNextButton) {
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
     * @param importFileFileName The importFileFileName to set.
     * @exception
     * @since 1.0
     */
    public void setImportFileFileName(String importFileFileName) {
        this.importFileFileName = importFileFileName;
    }
    /**
     * @return Returns the errorLog.
     * @exception
     * @since 1.0
     */
    public List<String> getErrorLog() {
        return errorLog;
    }
    /**
     * @param errorLog The errorLog to set.
     * @exception
     * @since 1.0
     */
    public void setErrorLog(List<String> errorLog) {
        this.errorLog = errorLog;
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
     * @param areProfiles The areProfiles to set.
     * @exception
     * @since 1.0
     */
    public void setAreProfiles(boolean areProfiles) {
        this.areProfiles = areProfiles;
    }

}
