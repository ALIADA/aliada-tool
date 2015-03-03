// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**
 * This class is the retrieve image from database.
 * @author xabier
 * @version $Revision: 1.1 $, $Date: 2015/01/28 15:20:54 $
 * @since 1.0
 */

public class ImageAction extends ActionSupport implements ServletRequestAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	byte[] imageInByte = null;
	
	private HttpServletRequest servletRequest;
	
	private final Log logger = new Log(ImageAction.class);
	
	/**
     * ImageAction constructor.
     */
	public ImageAction() {
	}
	
	/**
     * ImageAction execute.
     * @return String
     */
	public String execute() {
		return SUCCESS;
	}
	
	/**
     * Read the organisation logo from DB.
     * @return byte[]
     * @see
     * @since 1.0
     */
	
	public byte[] getCustomImageInBytes() {
		 
		String userName = (String) ServletActionContext.getRequest().getSession().getAttribute("logedUser");
        Connection connection;
        byte[] blobAsBytes = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT org_logo FROM aliada.organisation o INNER JOIN aliada.user u ON o.organisationId = u.organisationId "
            		+ "WHERE u.user_name='" + userName + "';");
            if (rs.next() && rs.getBlob("org_logo") != null) {
                Blob logo = rs.getBlob("org_logo");
                int blobLength = (int) logo.length();
                blobAsBytes = logo.getBytes(1, blobLength);
                //release the blob and free up memory. (since JDBC 4.0)
                logo.free();
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
        }
        return blobAsBytes;
		
	}
	
	/**
     * Return the custom content type.
     * @return String
     */
	public String getCustomContentType() {
		return "image/png";
	}
	
	/**
     * Return the custom content disposition.
     * @return String
     */
	public String getCustomContentDisposition() {
		return "anyname.png";
	}
	
	@Override
	public void setServletRequest(final HttpServletRequest request) {

		this.servletRequest = request;
		
	}
	
	
	
}
