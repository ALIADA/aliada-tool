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

	/**
	 * @return Returns the result of the execution.
	 */

	public String execute() {
		ServletActionContext.getRequest().getSession()
				.setAttribute("loggedInUser", getInputUser());
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
			rs = st.executeQuery("select * from aliada.user where user_name = '"
					+ getInputUser()
					+ "' and user_password = '"
					+ getInputPassword() + "'");
			if (rs.next()) {
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
		} catch (SQLException e) {
			logger.debug(MessageCatalog._00011_SQL_EXCEPTION);
			e.printStackTrace();
		}

		return SUCCESS;
	}

}
