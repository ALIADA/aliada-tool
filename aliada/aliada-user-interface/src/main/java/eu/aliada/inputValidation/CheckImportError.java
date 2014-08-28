// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.inputValidation;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.struts2.ServletActionContext;

/**
 * @author elena
 * @version $Revision: 1.1 $, $Date: 2004/10/28 15:20:54 $
 * @since 1.0
 */

public class CheckImportError {

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
		 ResourceBundle defaults = ResourceBundle.getBundle(
	                "ApplicationResources");
	        return defaults.getString(key);		
	}
}
