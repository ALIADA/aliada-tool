// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**
 * @author iosa
 * @since 1.0
 */
public class ConversionAction extends ActionSupport {
    private static final int NOTEMPLATESELECTED = -1;
    private File importFile;
    private HashMap<Integer, String> templates;
    private HashMap<String, Boolean> tags;
    private List selectedTags = new ArrayList();
    private String selectedTemplate;
    private String templateName;
    private String templateDescription;
    private boolean showAddTemplateForm;
    private boolean showEditTemplateForm;
    private boolean showCheckButton;
    private boolean showRdfizerButton;
    private boolean areTemplates;
    
    private final Log logger = new Log(ConversionAction.class);

    public String execute() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        File newFile = (File) session.getAttribute("importFile");
        if(!newFile.equals(session.getAttribute("oldImportFile"))){
            session.setAttribute("oldImportFile", newFile);
            setImportFile(newFile);
            setShowCheckButton(false);
            setShowRdfizerButton(true);
        }        
        else if (session.getAttribute("rdfizerJobId") == null) {
            setImportFile(newFile);
            setShowCheckButton(false);
            setShowRdfizerButton(true);
        } else {
            setImportFile(newFile);
            setShowCheckButton(true);
            setShowRdfizerButton(false); 
        }
        return getTemplatesDb();
    }

    public String rdfize() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        String format = null;
        logger.debug("Asked to RDF-ize");
        String namespace = "http://www.artium.org/";
        String aliada_ontology = "http://aliada-project.eu/2014/aliada-ontology/";
        try {
            format = getFormat();
            Connection connection = null;
            connection = new DBConnectionManager().getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement(
                            "INSERT INTO aliada.rdfizer_job_instances (datafile,format,namespace,aliada_ontology) VALUES(?,?,?,?)",
                            PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, importFile.getAbsolutePath());
            preparedStatement.setString(2, format);
            preparedStatement.setString(3, namespace);
            preparedStatement.setString(4, aliada_ontology);
            logger.debug("format" + format + importFile.getAbsolutePath());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int addedId = 0;
            if (rs.next()) {
                addedId = (int) rs.getInt(1);
                logger.debug("Added job id: " + addedId);
            }
            try {
                enableRdfizer();
                createJob(addedId);
            } catch (IOException e) {
                getTemplatesDb();
                rs.close();
                preparedStatement.close();
                connection.close();
                return ERROR;
            }
            rs.close();
            preparedStatement.close();
            connection.close();
            setImportFile((File) session.getAttribute("importFile"));
            session.setAttribute("rdfizerJobId", addedId);
            setShowCheckButton(true);
            return getTemplatesDb();
        } catch (SQLException e) {
            logger.debug("SQL error: " + e);
            getTemplatesDb();
            return ERROR;
        }
    }

    private String getFormat() throws SQLException {
        Connection connection = null;
        String format = null;
        connection = new DBConnectionManager().getConnection();
        Statement statement = connection.createStatement();
        String profile = (String) ServletActionContext.getRequest()
                .getSession().getAttribute("profile");
        setImportFile((File) ServletActionContext.getRequest().getSession()
                .getAttribute("importFile"));
        ResultSet rs = statement
                .executeQuery("select metadata_name from t_metadata_scheme JOIN profile ON t_metadata_scheme.metadata_code=profile.metadata_scheme_code WHERE profile.profile_id= "
                        + profile);
        if (rs.next()) {
            format = rs.getString(1);
        }
        rs.close();
        statement.close();
        connection.close();
        return format;
    }

    private void enableRdfizer() throws IOException {
        URL url = new URL("http://localhost:8891/rdfizer/enable/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("PUT");
        if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        logger.debug("Rdfizer enabled");
        conn.disconnect();
    }

    private void createJob(int addedId) throws IOException {
        URL url = new URL("http://localhost:8891/rdfizer/jobs/" + addedId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("PUT");
        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        logger.debug("Rdfizer job created");
        setShowRdfizerButton(false);
        conn.disconnect();
    }

    public String getTemplatesDb() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select * from aliada.template");
            templates = new HashMap<Integer, String>();
            while (rs.next()) {
                templates.put(rs.getInt("template_id"),
                        rs.getString("template_name"));
            }
            rs.close();
            statement.close();
            connection.close();
            if(templates.isEmpty()){
                setAreTemplates(false);
            }
            else{
                setAreTemplates(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ERROR;
        }
        return SUCCESS;
    }

    public String showAddTemplate() {
        getTemplatesDb();
        getTagsDb(NOTEMPLATESELECTED);
        setShowAddTemplateForm(true);
        logger.debug("SHOWADD: "+isShowAddTemplateForm());
        return SUCCESS;
    }

    public String addTemplate() {
        Connection connection = null;
        int id;
        try {
            connection = new DBConnectionManager().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO template VALUES (default,'" + this.templateName
                            + "', '" + this.templateDescription + "')",
                    PreparedStatement.RETURN_GENERATED_KEYS);
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
                statement
                        .executeUpdate("INSERT INTO template_xml_tag VALUES ('"
                                + idTemplate + "', '" + iterator.next() + "')");
                statement.close();
            }
            connection.close();
            logger.debug("HERE IM");
            setShowAddTemplateForm(false);
            return SUCCESS;
        } catch (SQLException e) {
            logger.debug("SQL error: " + e);
            getTemplatesDb();
            return ERROR;
        }
    }

    public String deleteTemplate() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            statement
                    .executeUpdate("DELETE tags.* FROM template_xml_tag tags INNER JOIN template temp ON tags.template_id=temp.template_id  WHERE temp.template_name='"
                            + getSelectedTemplate() + "'");
            statement.close();
            statement = connection.createStatement();
            statement
                    .executeUpdate("DELETE FROM aliada.template WHERE template_name='"
                            + getSelectedTemplate() + "'");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error("SQL error deleting template");
            e.printStackTrace();
            return ERROR;
        }
        return SUCCESS;
    }

    public String showEditTemplate() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select * from aliada.template where template_name='"
                            + this.selectedTemplate + "'");
            if (rs.next()) {
                int idTemplate = rs.getInt("template_id");
                this.templateName = rs.getString("template_name");
                this.templateDescription = rs.getString("template_description");
                statement.close();
                rs.close();
                connection.close();
                getTemplatesDb();
                getTagsDb(idTemplate);
                setShowEditTemplateForm(true);
                ServletActionContext.getRequest().getSession()
                        .setAttribute("selectedTemplateId", idTemplate);
                return SUCCESS;
            } else {
                addActionError(getText("template.not.selected"));
                statement.close();
                rs.close();
                connection.close();
                getTemplatesDb();
                getTagsDb(NOTEMPLATESELECTED);
                return ERROR;
            }
        } catch (SQLException e) {
            logger.debug("SQL error: " + e);
            getTemplatesDb();
            getTagsDb(NOTEMPLATESELECTED);
            return ERROR;
        }
    }

    public String editTemplate() {
        Connection connection = null;
        int idTemplate = (int) ServletActionContext.getRequest().getSession()
                .getAttribute("selectedTemplateId");
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            statement
                    .executeUpdate("UPDATE template set template_description='"
                            + this.templateDescription + "' where template_id='"
                            + idTemplate + "'");
            statement.close();
            statement = connection.createStatement();
            statement
                    .executeUpdate("DELETE FROM aliada.template_xml_tag WHERE template_id="
                            + idTemplate);
            statement.close();
            Iterator iterator = selectedTags.iterator();
            while (iterator.hasNext()) {
                statement = connection.createStatement();
                statement
                        .executeUpdate("INSERT IGNORE INTO template_xml_tag VALUES ('"
                                + idTemplate + "', '" + iterator.next() + "')");
                statement.close();
            }
            ServletActionContext.getRequest().getSession()
                    .removeAttribute("selectedTemplateId");
            connection.close();
        } catch (SQLException e) {
            logger.error("SQL error editing user" + e);
            e.printStackTrace();
            setShowEditTemplateForm(false);
            return ERROR;
        }
        logger.debug("Updated template");
        setShowEditTemplateForm(false);
        return SUCCESS;
    }

    private void getTagsDb(int templateId) {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select * from aliada.xml_tag");
            List tagNames = new ArrayList<String>();
            while (rs.next()) {
                tagNames.add(rs.getString("xml_tag_id"));
            }
            rs.close();
            statement.close();
            tags = new HashMap<String, Boolean>();
            Iterator iterator = tagNames.iterator();
            if (templateId != NOTEMPLATESELECTED) {
                statement = connection.createStatement();
                rs = statement
                        .executeQuery("select xml_tag_id from aliada.template_xml_tag WHERE template_id="
                                + templateId);
                List tagsChecked = new ArrayList<String>();
                while (rs.next()) {
                    tagsChecked.add(rs.getString(1));
                }
                while (iterator.hasNext()) {
                    String listTagName = (String) iterator.next();
                    if (tagsChecked.contains(listTagName)) {
                        tags.put(listTagName, true);
                    } else {
                        tags.put(listTagName, false);
                    }
                }
            } else {
                while (iterator.hasNext()) {
                    tags.put((String) iterator.next(), false);
                }
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.debug("SQL Error: " + e);
        }
    }

    /**
     * @return Returns the templates.
     * @exception
     * @since 1.0
     */
    public HashMap<Integer, String> getTemplates() {
        return templates;
    }

    /**
     * @param templates
     *            The templates to set.
     * @exception
     * @since 1.0
     */
    public void setTemplates(HashMap<Integer, String> templates) {
        this.templates = templates;
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
    public void setImportFile(File importFile) {
        this.importFile = importFile;
    }

    /**
     * @return Returns the selectedTemplate.
     * @exception
     * @since 1.0
     */
    public String getSelectedTemplate() {
        return selectedTemplate;
    }

    /**
     * @param selectedTemplate
     *            The selectedTemplate to set.
     * @exception
     * @since 1.0
     */
    public void setSelectedTemplate(String selectedTemplate) {
        this.selectedTemplate = selectedTemplate;
    }

    /**
     * @return Returns the templateName.
     * @exception
     * @since 1.0
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * @param templateName
     *            The templateName to set.
     * @exception
     * @since 1.0
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * @return Returns the templateDescription.
     * @exception
     * @since 1.0
     */
    public String getTemplateDescription() {
        return templateDescription;
    }

    /**
     * @param templateDescription
     *            The templateDescription to set.
     * @exception
     * @since 1.0
     */
    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }

    /**
     * @return Returns the showAddTemplateForm.
     * @exception
     * @since 1.0
     */
    public boolean isShowAddTemplateForm() {
        return showAddTemplateForm;
    }

    /**
     * @param showAddTemplateForm
     *            The showAddTemplateForm to set.
     * @exception
     * @since 1.0
     */
    public void setShowAddTemplateForm(boolean showAddTemplateForm) {
        this.showAddTemplateForm = showAddTemplateForm;
    }

    /**
     * @return Returns the showEditTemplateForm.
     * @exception
     * @since 1.0
     */
    public boolean isShowEditTemplateForm() {
        return showEditTemplateForm;
    }

    /**
     * @param showEditTemplateForm
     *            The showEditTemplateForm to set.
     * @exception
     * @since 1.0
     */
    public void setShowEditTemplateForm(boolean showEditTemplateForm) {
        this.showEditTemplateForm = showEditTemplateForm;
    }

    /**
     * @return Returns the tags.
     * @exception
     * @since 1.0
     */
    public HashMap<String, Boolean> getTags() {
        return tags;
    }

    /**
     * @param tags
     *            The tags to set.
     * @exception
     * @since 1.0
     */
    public void setTags(HashMap<String, Boolean> tags) {
        this.tags = tags;
    }

    /**
     * @return Returns the showCheckButton.
     * @exception
     * @since 1.0
     */
    public boolean isShowCheckButton() {
        return showCheckButton;
    }

    /**
     * @param showCheckButton
     *            The showCheckButton to set.
     * @exception
     * @since 1.0
     */
    public void setShowCheckButton(boolean showCheckButton) {
        this.showCheckButton = showCheckButton;
    }

    /**
     * @return Returns the selectedTags.
     * @exception
     * @since 1.0
     */
    public List getSelectedTags() {
        return selectedTags;
    }

    /**
     * @param selectedTags
     *            The selectedTags to set.
     * @exception
     * @since 1.0
     */
    public void setSelectedTags(List selectedTags) {
        this.selectedTags = selectedTags;
    }

    /**
     * @return Returns the showRdfizerButton.
     * @exception
     * @since 1.0
     */
    public boolean isShowRdfizerButton() {
        return showRdfizerButton;
    }

    /**
     * @param showRdfizerButton The showRdfizerButton to set.
     * @exception
     * @since 1.0
     */
    public void setShowRdfizerButton(boolean showRdfizerButton) {
        this.showRdfizerButton = showRdfizerButton;
    }

    /**
     * @return Returns the areTemplates.
     * @exception
     * @since 1.0
     */
    public boolean isAreTemplates() {
        return areTemplates;
    }

    /**
     * @param areTemplates The areTemplates to set.
     * @exception
     * @since 1.0
     */
    public void setAreTemplates(boolean areTemplates) {
        this.areTemplates = areTemplates;
    }

}
