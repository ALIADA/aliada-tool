// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.model.Organisation;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**
 * @author elena
 * @version $Revision: 1.1 $, $Date: 2004/10/28 15:20:54 $
 * @since 1.0
 */

public class InstitutionConfigurationAction extends ActionSupport {

    private List<Organisation> organisations;
    private String organisation_name;
    private File organisation_logo;
    private String organisation_catalog_url;     
        
    private static final String DEFAULTLOGOPATH = "webapps/aliada-user-interface-1.0/images/aliada.png";


    private final Log logger = new Log(InstitutionConfigurationAction.class);

    /**
     * Read the institution
     */
    public String execute() {
        String userName = (String) ServletActionContext.getRequest().getSession().getAttribute("logedUser");
        Connection connection;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM organisation o INNER JOIN user u ON o.organisationId = u.organisationId WHERE u.user_name='"+userName+"';");
            if (rs.next() && rs.getString("organisation_name") != null) {
                setOrganisation_name(rs.getString("organisation_name"));
                // readFile(rs);
                setOrganisation_catalog_url(rs
                        .getString("organisation_catalog_url"));
            }
            statement.close();
            connection.close();
            return SUCCESS;
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
            return ERROR;
        }
    }

    /**
     * Edit the institution
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
                    .prepareStatement("UPDATE organisation  SET organisation_logo = ?, organisation_catalog_url =? WHERE organisation_name = ?");
            if (this.organisation_logo != null) {
                fis = new FileInputStream(this.organisation_logo);
                preparedStatement.setBinaryStream(1, fis,
                        (int) this.organisation_logo.length());
            } else {
                File defaultImg = new File(DEFAULTLOGOPATH);
                fis = new FileInputStream(defaultImg);
                preparedStatement.setBinaryStream(1, fis,
                        (int) defaultImg.length());
            }
            preparedStatement.setString(2, this.organisation_catalog_url);
            preparedStatement.setString(3, this.organisation_name);
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
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
            return ERROR;
        } catch (FileNotFoundException e) {
            logger.error(MessageCatalog._00013_FILE_NOT_FOUND_EXCEPTION,e);
            return ERROR;
        }
    }

    private void readFile(ResultSet rs) {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.reset();
        response.setContentType("multipart/form-data");
        try {
            int len = rs.getString("organisation_logo").length();
            byte[] buffer = new byte[len];
            InputStream readImg = rs.getBinaryStream("organisation_logo");
            int index = readImg.read(buffer, 0, len);
            response.getOutputStream().write(buffer, 0, len);
            response.getOutputStream().flush();
        } catch (SQLException | IOException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
        }
    }

    /**
     * @return Returns the organisation_name.
     * @exception
     * @since 1.0
     */
    public String getOrganisation_name() {
        return organisation_name;
    }

    /**
     * @param organisation_name
     *            The organisation_name to set.
     * @exception
     * @since 1.0
     */
    public void setOrganisation_name(String organisation_name) {
        this.organisation_name = organisation_name;
    }
    
    /**
     * @return Returns the organisation_logo.
     * @exception
     * @since 1.0
     */
    public File getOrganisation_logo() {
        return organisation_logo;
    }

    /**
     * @param organisation_logo
     *            The organisation_logo to set.
     * @exception
     * @since 1.0
     */
    public void setOrganisation_logo(File organisation_logo) {
        this.organisation_logo = organisation_logo;
    }

    /**
     * @return Returns the organisation_catalog_url.
     * @exception
     * @since 1.0
     */
    public String getOrganisation_catalog_url() {
        return organisation_catalog_url;
    }

    /**
     * @param organisation_catalog_url
     *            The organisation_catalog_url to set.
     * @exception
     * @since 1.0
     */
    public void setOrganisation_catalog_url(String organisation_catalog_url) {
        this.organisation_catalog_url = organisation_catalog_url;
    }
}
