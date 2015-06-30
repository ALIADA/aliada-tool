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

import org.apache.struts2.ServletActionContext;
import org.jasypt.util.password.StrongPasswordEncryptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**
 * This class is to Access to ALIADA's tool.
 * 
 * @author elena
 * @since 1.0
 */

public class LogonAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private final Log logger = new Log(LogonAction.class);
	private String inputUser;
	private String inputPassword;

	/**
	 * @return Returns the result of the execution.
	 */

	public String execute() {
		
		ServletActionContext.getRequest().getSession().setAttribute("action", ActionContext.getContext().getName());
		ServletActionContext.getRequest().getSession().setAttribute("loggedInUser", getInputUser());
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = new DBConnectionManager().getConnection();
			if (conn == null) {
				logger.debug(MessageCatalog._00006_BBDD_CONFIGURATION_INVALID);
				addActionError(getText("error.databaseConfiguration"));
				return ERROR;
			}
			st = conn.createStatement();
			StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
			
			rs = st.executeQuery("select user_name,user_password,org_name, u.user_type_code from aliada.user u "
					+ "INNER JOIN aliada.organisation o ON u.organisationId = o.organisationId "
					+ "INNER JOIN aliada.t_user_type t ON t.user_type_code = u.user_type_code "
					+ "where user_name = '" + getInputUser() + "' "
					+ "AND language = '" + getLocale().getISO3Language() + "'");
			
			if (rs.next()) {
			    if (passwordEncryptor.checkPassword(getInputPassword(), rs.getString("user_password"))) {
                    ServletActionContext.getRequest().getSession().setAttribute("logedUser", rs.getString("user_name"));
                    ServletActionContext.getRequest().getSession().setAttribute("inst", rs.getString("org_name"));
                    // User type
                    ServletActionContext.getRequest().getSession().setAttribute("type", rs.getInt("user_type_code"));
                    rs.close();
	                st.close();
	                conn.close();
	                return SUCCESS;
	              } else {
	                logger.debug(MessageCatalog._00010_LOGON_FAILURE);
	                addActionError(getText("error.login"));
                    rs.close();
                    st.close();
                    conn.close();
                    return ERROR;
	              }			
			} else {
				logger.debug(MessageCatalog._00010_LOGON_FAILURE);
				addActionError(getText("error.login"));
				rs.close();
				st.close();
				conn.close();
				return ERROR;
			}
		} catch (SQLException e) {
			logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
		}

		return SUCCESS;
	}

	/**
	 * @return Returns the inputUser.
	 * @exception
	 * @since 1.0
	 */

	public String getInputUser() {
		return inputUser;
	}

	/**
	 * @param inputUser
	 *            The inputUser to set.
	 * @exception
	 * @since 1.0
	 */

	public void setInputUser(final String inputUser) {
		this.inputUser = inputUser;
	}

	/**
	 * @return Returns the inputPassword.
	 * @exception
	 * @since 1.0
	 */

	public String getInputPassword() {
		return inputPassword;
	}

	/**
	 * @param inputPassword
	 *            The inputPassword to set.
	 * @exception
	 * @since 1.0
	 */

	public void setInputPassword(final String inputPassword) {
		this.inputPassword = inputPassword;
	}
}
