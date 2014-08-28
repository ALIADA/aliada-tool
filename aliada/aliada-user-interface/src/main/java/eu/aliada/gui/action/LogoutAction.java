// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;


/**
 * @author elena
 * @version $Revision: 1.1 $, $Date: 2004/10/28 15:20:54 $
 * @since 1.0
 */

public class LogoutAction extends ActionSupport {
	  
	 // all struts logic here
	 public String execute() {	   
	  ServletActionContext.getRequest().getSession().invalidate();
	  addActionMessage("You are successfully logout!");
	  return "logout";
	 
	 }

}


