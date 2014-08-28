// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.shared.log.Log;

/**
 * @author iosa
 * @since 1.0
 */
public class LocaleAction extends ActionSupport{

    private final Log logger = new Log(UsersAction.class);
    
    public String execute() {
        logger.debug("Changed locale");
        return SUCCESS;
    }
}
