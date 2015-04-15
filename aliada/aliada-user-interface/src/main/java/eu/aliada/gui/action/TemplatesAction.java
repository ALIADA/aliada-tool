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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**
 * @author iosa
 * @since 1.0
 */
public class TemplatesAction extends ActionSupport {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int NOTEMPLATESELECTED = -1;
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

    private final Log logger = new Log(ConversionAction.class);
    
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
     * Gets the available templates from the DB.
     * @return String
     * @see
     * @since 1.0
     */
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
            if (templates.isEmpty()) {
                setAreTemplates(false);
            } else {
                setAreTemplates(true);
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        getTypesDb();
        return SUCCESS;
    }    
    /**
     * Displays the selected template.
     * @return String
     * @see
     * @since 1.0
     */
    public String getTheTemplate() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select t.template_id,t.template_name,t.template_description,f.file_type_name "
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
                getTagsDb(idTemplate);
                setShowTheTemplate(true);
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
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            getTemplatesDb();
            getTagsDb(NOTEMPLATESELECTED);
            return ERROR;
        }
    }
    /**
     * Displays the template adding form.
     * @return String
     * @see
     * @since 1.0
     */
    public String showAddTemplate() {
        getTemplatesDb();
        getTagsDb(NOTEMPLATESELECTED);
        setShowAddTemplateForm(true);
        return SUCCESS;
    }

    /**
     * Adds a new template to the DB.
     * @return String
     * @see
     * @since 1.0
     */
    public String addTemplate() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO aliada.template VALUES (default,'" + this.templateName
                            + "', '" + this.templateDescription + "', '" + this.fileType + "')",
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
                        .executeUpdate("INSERT INTO aliada.template_xml_tag VALUES ('"
                                + idTemplate + "', '" + iterator.next() + "')");
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
            getTemplatesDb();
            return ERROR;
        }
    }

    /**
     * Deletes a template from the DB.
     * @return String
     * @see
     * @since 1.0
     */
    public String deleteTemplate() {
    	if (getSelectedTemplate().equalsIgnoreCase("Authorities") 
    			|| getSelectedTemplate().equalsIgnoreCase("MARC BIB") 
    			|| getSelectedTemplate().equalsIgnoreCase("LIDO")) {
		    		addActionError(getText("err.template.deletion")); 
		    		getTemplatesDb();
		    		return SUCCESS;
    	} else {
	        Connection connection = null;
	        try {
	            connection = new DBConnectionManager().getConnection();
	            Statement statement = connection.createStatement();
	            statement
	                    .executeUpdate("DELETE tags.* FROM aliada.template_xml_tag tags "
	                    		+ "INNER JOIN template temp ON tags.template_id=temp.template_id  WHERE temp.template_name='"
	                            + getSelectedTemplate() + "'");
	            statement.close();
	            statement = connection.createStatement();
	            int correct = statement
	                    .executeUpdate("DELETE FROM aliada.template WHERE template_name='"
	                            + getSelectedTemplate() + "'");
	            statement.close();
	            connection.close();
	            if (correct == 0) {
	                addActionError(getText("template.not.selected"));                
	            } else {
	                addActionMessage(getText("template.delete.ok"));
	            }
	        } catch (SQLException e) {
	            getTemplatesDb();
	            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
	            return ERROR;
	        }
	        getTemplatesDb();
	        return SUCCESS;
	    }
    }

    /**
     * Displays the form to edit the template.
     * @return String
     * @see
     * @since 1.0
     */
    public String showEditTemplate() {
    	if (getSelectedTemplate().equalsIgnoreCase("MARC BIB") 
    			|| getSelectedTemplate().equalsIgnoreCase("LIDO")
    			|| getSelectedTemplate().equalsIgnoreCase("Authorities")) {
		        	clearErrorsAndMessages();
		        	addActionError(getText("err.not.allow.edit"));
		            getTemplatesDb();
		            getTagsDb(NOTEMPLATESELECTED);
		            return ERROR;          	
        } else {
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
	                this.fileType = rs.getInt("file_type_code");
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
	            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
	            getTemplatesDb();
	            getTagsDb(NOTEMPLATESELECTED);
	            return ERROR;
	        }
        }
    }

    /**
     * Updates a existing template.
     * @return String
     * @see
     * @since 1.0
     */
    public String editTemplate() {
        Connection connection = null;
        int idTemplate = (int) ServletActionContext.getRequest().getSession()
                .getAttribute("selectedTemplateId");
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            statement
                    .executeUpdate("UPDATE aliada.template set template_description='"
                            + this.templateDescription + "', file_type_code="
                            + this.fileType + " where template_id="
                            + idTemplate + "");
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
                        .executeUpdate("INSERT IGNORE INTO aliada.template_xml_tag VALUES ('"
                                + idTemplate + "', '" + iterator.next() + "')");
                statement.close();
            }
            ServletActionContext.getRequest().getSession()
                    .removeAttribute("selectedTemplateId");
            connection.close();
            addActionMessage(getText("template.save.ok"));
            getTemplatesDb();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            setShowEditTemplateForm(false);
            return ERROR;
        }
        setShowEditTemplateForm(false);
        return SUCCESS;
    }

    /**
     * Gets the available tags from the DB
     * @param templateId The available templates
     * @see
     * @since 1.0
     */
    private void getTagsDb(final int templateId) {
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
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
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
    public void setTemplates(final HashMap<Integer, String> templates) {
        this.templates = templates;
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
    public void setSelectedTemplate(final String selectedTemplate) {
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
    public void setTemplateName(final String templateName) {
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
    public void setTemplateDescription(final String templateDescription) {
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
    public void setShowAddTemplateForm(final boolean showAddTemplateForm) {
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
    public void setShowEditTemplateForm(final boolean showEditTemplateForm) {
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
    public void setTags(final HashMap<String, Boolean> tags) {
        this.tags = tags;
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
    public void setSelectedTags(final List selectedTags) {
        this.selectedTags = selectedTags;
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
    public void setAreTemplates(final boolean areTemplates) {
        this.areTemplates = areTemplates;
    }
    /**
     * @return Returns the showTheTemplate.
     * @exception
     * @since 1.0
     */
    public boolean isShowTheTemplate() {
        return showTheTemplate;
    }
    /**
     * @param showTheTemplate The showTheTemplate to set.
     * @exception
     * @since 1.0
     */
    public void setShowTheTemplate(final boolean showTheTemplate) {
        this.showTheTemplate = showTheTemplate;
    }
    /**
     * @return Returns the fileType.
     * @exception
     * @since 1.0
     */
    public int getFileType() {
        return fileType;
    }
    /**
     * @param fileType The fileType to set.
     * @exception
     * @since 1.0
     */
    public void setFileType(final int fileType) {
        this.fileType = fileType;
    }
    /**
     * @return Returns the fileTypeName.
     * @exception
     * @since 1.0
     */
    public String getFileTypeName() {
        return fileTypeName;
    }
    /**
     * @param fileTypeName The fileTypeName to set.
     * @exception
     * @since 1.0
     */
    public void setFileTypeName(final String fileTypeName) {
        this.fileTypeName = fileTypeName;
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
    public void setTypes(final HashMap<Integer, String> types) {
        this.types = types;
    }

}