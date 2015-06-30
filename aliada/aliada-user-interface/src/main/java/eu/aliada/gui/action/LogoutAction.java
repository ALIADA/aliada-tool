// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.shared.log.Log;


/**
 * This class is to logout ALIADA's tool.
 * @author elena
 * @version $Revision: 1.1 $, $Date: 2004/10/28 15:20:54 $
 * @since 1.0
 */

public class LogoutAction extends ActionSupport {
	  

	private final Log logger = new Log(LogoutAction.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @return Returns the result of the execution.
	 */
	 public String execute() {
	   
	  ServletActionContext.getRequest().getSession().invalidate();
	  //addActionMessage("You are successfully logout!");
	  logger.info(MessageCatalog._00002_STOPPED);
	  return "logout";
	 
	 }

}