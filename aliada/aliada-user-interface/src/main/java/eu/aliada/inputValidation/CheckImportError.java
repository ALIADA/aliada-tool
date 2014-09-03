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
 * This class is to validate the mandatory tags in the imported xml file.
 * 
 * @author elena
 * @version $Revision: 1.1 $, $Date: 2004/10/28 15:20:54 $
 * @since 1.0
 */

public final class CheckImportError {

	private static final Log logger = new Log(CheckImportError.class);
	static int count;

	/**
	 * Class constructor.
	 * 
	 * @see
	 * @since 1.0
	 */

	private CheckImportError() {
		super();
		count = 0;
	}

	/**
	 * The method to increase the number of errors in the mandatory tags
	 * validation.
	 * 
	 * @return String
	 * @see
	 * @since 1.0
	 */
	public static String increase() {
		count++;
		return null;
	}

	/**
	 * The method to get the number of errors.
	 * 
	 * @return int
	 * @see
	 * @since 1.0
	 */
	public static int getCount() {
		return count;
	}

	/**
	 * The method to initialize the count of errors.
	 * 
	 * @see
	 * @since 1.0
	 */
	public static void initialize() {
		count = 0;
	}

	/**
	 * The method to get the content in the different ALIADA's languages.
	 * 
	 * @param key
	 *            The key to translate.
	 * @return String
	 * @see
	 * @since 1.0
	 */
	public static String getLocaleText(final String key) {
		Locale locale = (Locale) ServletActionContext.getRequest().getSession()
				.getAttribute("WW_TRANS_I18N_LOCALE");
		if (locale == null) {
			locale = Locale.ROOT;
		}
		ResourceBundle defaults = ResourceBundle.getBundle(
				"ApplicationResources", locale);
		return defaults.getString(key);
	}
}
