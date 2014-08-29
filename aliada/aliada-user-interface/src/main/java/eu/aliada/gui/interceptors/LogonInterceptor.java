// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import eu.aliada.gui.log.MessageCatalog;

/**
 * This class is to intercept the LogonAction.
 * 
 * @author elena
 * @version $Revision: 1.1 $, $Date: 2004/10/28 15:20:54 $
 * @since 1.0
 */

public class LogonInterceptor extends AbstractInterceptor implements
		StrutsStatics {

	private final Log logger = LogFactory.getLog(LogonInterceptor.class);
	private static final long serialVersionUID = 1L;
	private static final String USER_HANDLE = "loggedInUser";
	private static final String LOGIN_ATTEMPT = "loginAttempt";

	/**
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#init()
	 */
	public void init() {
		logger.info(MessageCatalog._00001_STARTING);
		logger.info(MessageCatalog._00003_STARTING_LOGON_INTERCEPTOR);
	}

	/**
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#destroy()
	 */
	public void destroy() {
		logger.info(MessageCatalog._00002_STOPPED);
		logger.info(MessageCatalog._00004_STOPPED_LOGON_INTERCEPTOR);
	}

	/**
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 * @param  invocation
	 *            The ActionInvocation to set.
	 * @return Returns the result of the invocation.
	 * @throws Exception for the invoke method
	 */
	public String intercept(final ActionInvocation invocation) throws Exception {

		final ActionContext context = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) context
				.get(HTTP_REQUEST);
		HttpSession session = request.getSession(true);

		// Is there a "user" object stored in the user's HttpSession?
		Object user = session.getAttribute(USER_HANDLE);
		if (user == null) {
			// The user has not logged in yet.

			// Is the user attempting to log in right now?
			String loginAttempt = request.getParameter(LOGIN_ATTEMPT);

			/* The user is attempting to log in. */
			if (!StringUtils.isBlank(loginAttempt)) {
				return invocation.invoke();
			}
			return "logon";
		} else {
			return invocation.invoke();
		}
	}

}
