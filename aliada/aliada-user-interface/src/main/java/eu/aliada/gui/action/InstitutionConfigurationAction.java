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

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**
 * @author elena
 * @version $Revision: 1.1 $, $Date: 2004/10/28 15:20:54 $
 * @since 1.0
 */

public class InstitutionConfigurationAction extends ActionSupport {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private String organisationName;
    private File organisationLogo;
    private String organisationCatalogUrl;     
        
    private static final String DEFAULTLOGOPATH = "webapps/aliada-user-interface-2.0/images/aliada.png";

    private final Log logger = new Log(InstitutionConfigurationAction.class);

    /**
     * Read the institution.
     * @return String
     */
    public String execute() {
        String userName = (String) ServletActionContext.getRequest().getSession().getAttribute("logedUser");
        Connection connection;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM aliada.organisation o INNER JOIN aliada.user u ON o.organisationId = "
            		+ "u.organisationId WHERE u.user_name='" + userName + "';");
            if (rs.next() && rs.getString("org_name") != null) {
                setOrganisationName(rs.getString("org_name"));
               // readFile(rs);
                setOrganisationCatalogUrl(rs
                        .getString("org_catalog_url"));
            }
            statement.close();
            connection.close();
            return SUCCESS;
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
    }

    /**
     * Edit the institution.
     * @return String
     */
    public String editInstitution() {
        Connection connection = null;
        FileInputStream fis = null;
        try {
            connection = new DBConnectionManager().getConnection();
//            Statement statement = connection.createStatement();
//            ResultSet rs = statement.executeQuery("SELECT * FROM organisation WHERE organisation_name='"+getOrganisation_name()+"'");
//            if(!rs.next()){
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE aliada.organisation  SET org_logo = ?, org_catalog_url =? WHERE org_name = ?");
            if (this.organisationLogo != null) {
                fis = new FileInputStream(this.organisationLogo);
                preparedStatement.setBinaryStream(1, fis,
                        (int) this.organisationLogo.length());
            } else {
                File defaultImg = new File(DEFAULTLOGOPATH);
                fis = new FileInputStream(defaultImg);
                preparedStatement.setBinaryStream(1, fis,
                        (int) defaultImg.length());
            }
            preparedStatement.setString(2, this.organisationCatalogUrl);
            preparedStatement.setString(3, this.organisationName);
            preparedStatement.executeUpdate();
            addActionMessage(getText("institution.changed"));
            preparedStatement.close();
//            }
//            else{
//                addActionError(getText("err.duplicate.organisation"));                
//            }
//            statement.close();
//            rs.close();
            connection.close();
            return SUCCESS;
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        } catch (FileNotFoundException e) {
            logger.error(MessageCatalog._00013_FILE_NOT_FOUND_EXCEPTION, e);
            return ERROR;
        }
    }

    /**
     * @return Returns the organisationName.
     * @exception
     * @since 1.0
     */
    public String getOrganisationName() {
        return organisationName;
    }

    /**
     * @param organisationName
     *            The organisationName to set.
     * @exception
     * @since 1.0
     */
    public void setOrganisationName(final String organisationName) {
        this.organisationName = organisationName;
    }
    
    /**
     * @return Returns the organisationLogo.
     * @exception
     * @since 1.0
     */
    public File getOrganisationLogo() {
        return organisationLogo;
    }

    /**
     * @param organisationLogo
     *            The organisationLogo to set.
     * @exception
     * @since 1.0
     */
    public void setOrganisationLogo(final File organisationLogo) {
        this.organisationLogo = organisationLogo;
    }

    /**
     * @return Returns the organisationCatalogUrl.
     * @exception
     * @since 1.0
     */
    public String getOrganisationCatalogUrl() {
        return organisationCatalogUrl;
    }

    /**
     * @param organisationCatalogUrl
     *            The organisationCatalogUrl to set.
     * @exception
     * @since 1.0
     */
    public void setOrganisationCatalogUrl(final String organisationCatalogUrl) {
        this.organisationCatalogUrl = organisationCatalogUrl;
    }
}
