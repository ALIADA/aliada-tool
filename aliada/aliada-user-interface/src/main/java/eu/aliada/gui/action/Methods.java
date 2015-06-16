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
import java.util.Iterator;
import java.util.List;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**
 * @author xabier
 * @version $Revision: 1.1 $, $Date: 2015/03/23 15:20:54 $
 * @since 1.0
 */
public class Methods {
	
	private final Log logger = new Log(Methods.class);
	
	/**
     * Update the datasets reload to 0.
     * @see
     * @since 1.0
     */
    public void cleanReloadDataset() {
    	String datasetName = "";
    	Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
        	Statement updateStatement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select * from aliada.t_external_dataset");
            while (rs.next()) {
            	datasetName = rs.getString("external_dataset_name"); 
                updateStatement.executeUpdate("UPDATE aliada.t_external_dataset set external_dataset_linkingreloadtarget='0' "
                		+ "where external_dataset_name='" + datasetName + "'");
            }
            connection.close();
            rs.close();
            updateStatement.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
        }
	}
    /**
     * The method to show the metadata scheme list.
     * @return String
     * @see
     * @since 1.0
     */
    public HashMap<Integer, String> getSchemesDb() {
        Connection connection = null;
        HashMap<Integer, String> schemes = new HashMap<>();
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
        }
		return schemes;
    }
    /**
     * The method to show the profile type list. 
     * @return String
     * @see
     * @since 1.0
     */
    public HashMap<Integer, String> getProfileTypesDb() {
        Connection connection = null;
        HashMap<Integer, String> profileTypes = new HashMap<>();
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
        }
		return profileTypes;
    }
    /**
     * The method to show the file format list.
     * @return String
     * @see
     * @since 1.0
     */
    public HashMap<Integer, String> getFormatsDb() {
        Connection connection = null;
        HashMap<Integer, String> formats = new HashMap<>();
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
        }
		return formats;
    }
    /**
     * The method to show the character set list.
     * @return String
     * @see
     * @since 1.0
     */
    public HashMap<Integer, String> getCharacterSetsDb() {
        Connection connection = null;
        HashMap<Integer, String> characterSets = new HashMap<>();
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
        }
		return characterSets;
    }
    /**
     * The method to show the file type list.
     * @return String
     * @see
     * @since 1.0
     */
    public HashMap<Integer, String> getTypesDb() {
        Connection connection = null;
        HashMap<Integer, String> types = new HashMap<>();
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
        }
		return types;
    }
    /**
     * Gets the available tags from the DB.
     * @param templateId The available templates
     * @return HashMap<String, Boolean>
     * @see
     * @since 1.0
     */
    public HashMap<String, Boolean> getTagsDb(final int templateId) {
        final int NOTEMPLATESELECTED = -1;
        Connection connection = null;
        HashMap<String, Boolean> tags = new HashMap<>();
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
		return tags;
    }
    /**
     * Gets the user types from DB.
     * @return String
     * @see
     * @since 1.0
     */
    public HashMap<Integer, String> getUsersTypesDb() {
        Connection connection;
        HashMap<Integer, String> userTypes = new HashMap<>();
        try {
            connection = new DBConnectionManager().getConnection();
        	Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from aliada.t_user_type");
            while (rs.next()) {
                int code = rs.getInt("user_type_code");
                String name = rs.getString("user_type");
                userTypes.put(code, name);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
        }
		return userTypes;
    }
    /**
     * Gets the user role name for a given user role code.
     * @param code The role code
     * @return String
     * @see
     * @since 1.0
     */
    public String getRoleCode(final int code) {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
        	Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select user_role from aliada.t_user_role where user_role_code='" + code + "'");
            if (rs.next()) {
                String userRole = rs.getString("user_role");
                connection.close();
                return userRole;
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
        }
        return null;
    }
    /**
     * Gets the user organisation name for a given user organisation id.
     * @param code The organisation code
     * @return String
     * @see
     * @since 1.0
     */
    public String getOrganisationName(final int code) {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select org_name from aliada.organisation where organisationId='" + code + "'");
            if (rs.next()) {
                String organisationName = rs.getString("org_Name");
                connection.close();
                return organisationName;
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
        }
        return null;
    }
    /**
     * Gets the user type name for a given user type code.
     * @param code The user type code
     * @return String
     * @see
     * @since 1.0
     */
    public String getUserType(final int code) {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select user_type from aliada.t_user_type where user_type_code='" + code + "'");
            if (rs.next()) {
                String userType = rs.getString("user_type");
                connection.close();
                return userType;
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
        }
        return null;
    }
    /**
     * Get the user roles from DB.
     * @return String
     * @see
     * @since 1.0
     */
    public HashMap<Integer, String> getRolesDb() {
        Connection connection;
        HashMap<Integer, String> roles = new HashMap<>();
        try {
            connection = new DBConnectionManager().getConnection();
        	Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from aliada.t_user_role");
            while (rs.next()) {
                int code = rs.getInt("user_role_code");
                String name = rs.getString("user_role");
                roles.put(code, name);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
        }
		return roles;
    }
    /**
     * Get the organisations from DB.
     * @return String
     * @see
     * @since 1.0
     */
    public HashMap<Integer, String> getOrganisationsDb() {
        Connection connection;
        HashMap<Integer, String> organisations = new HashMap<>();
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from aliada.organisation");
            while (rs.next()) {
                int code = rs.getInt("organisationId");
                String name = rs.getString("org_name");
                organisations.put(code, name);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
        }
		return organisations;
    }
}
