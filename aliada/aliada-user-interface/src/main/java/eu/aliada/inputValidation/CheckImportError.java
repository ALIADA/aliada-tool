// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.inputValidation;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.struts2.ServletActionContext;

import eu.aliada.shared.log.Log;

/**
 * @author elenaS
 * @version $Revision: 1.1 $, $Date: 2004/10/28 15:20:54 $
 * @since 1.0
 */

public class CheckImportError {
	 private final static Log logger = new Log(CheckImportError.class);
	static int count = 0;

	public static String increase() {
		count++;
		return null;
	}

	public static int getCount(){
		return count;
	}
	
	public static void inicialize(){
		count =0;
	}
	
	public static String getLocaleText(String key){
		//Locale locale = (Locale) ServletActionContext.getRequest().getSession().get("WW_TRANS_I18N_LOCALE");
				//session.getAttribute(WW_TRANS_I18N_LOCALE));
		Locale locale=new Locale("es");
		logger.debug("Locale es **** " + locale);
		 ResourceBundle defaults = ResourceBundle.getBundle(
	                "ApplicationResources",  locale);
	        return defaults.getString(key);		
	}
}
