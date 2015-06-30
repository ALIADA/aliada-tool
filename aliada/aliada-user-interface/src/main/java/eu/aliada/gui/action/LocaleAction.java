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
 * This class is to control the Locale changes in the ALIADA's tool.
 * 
 * @author iosa
 * @since 1.0
 */
public class LocaleAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private final Log logger = new Log(LocaleAction.class);

	/**
	 * @return Returns the result of the execution.
	 */
	public String execute() {
		logger.info(MessageCatalog._00007_CHANGE_LOCALE 
				+ ServletActionContext.getRequest().getSession()
				.getAttribute("WW_TRANS_I18N_LOCALE"));
		return SUCCESS;
	}
}
