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
 * This class is to validate the mandatory tags in the imported xml file.
 * 
 * @author elena
 * @version $Revision: 1.1 $, $Date: 2004/10/28 15:20:54 $
 * @since 1.0
 */

public final class CheckImportError {

	// private static final Log logger = new Log(CheckImportError.class);
	static int count;
	static boolean fileCorrect;
	static int fileType;

	private static final int TYPE_BIBLIOGRAPHIC = 0;
	private static final int TYPE_AUTHORITY = 1;
	private static final int TYPE_LIDO = 2;

	/**
	 * Class constructor.
	 * 
	 * @see
	 * @since 1.0
	 */

	private CheckImportError() {
		super();
		count = 0;
		fileCorrect = true;
		fileType = TYPE_BIBLIOGRAPHIC;
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
	 * The method is used by identify a bibliographic file.
	 * 
	 * @see
	 * @since 1.0
	 */
	public static void putBibliographicType() {
		setFileType(TYPE_BIBLIOGRAPHIC);
	}

	/**
	 * The method is used by identify an authority file.
	 * 
	 * @see
	 * @since 1.0
	 */
	public static void putAuthorityType() {
		setFileType(TYPE_AUTHORITY);
	}

	/**
	 * The method is used by identify a lido file.
	 * 
	 * @see
	 * @since 1.0
	 */
	public static void putLidoType() {
		setFileType(TYPE_LIDO);
	}

	/**
	 * The method is used by control if the file is bibliographic or authority
	 * type and if the leader tags are correct.
	 * 
	 * @param fileType
	 *            The type of record indicated by leader tag
	 * @return String
	 * @see
	 * @since 1.0
	 */
	public static String getFileType(final String fileType) {
		if (isFileCorrect()
				&& ((getFileType() == TYPE_BIBLIOGRAPHIC) && (fileType
						.charAt(0) != 'z')) || (getFileType() == TYPE_AUTHORITY)
						&& (fileType.charAt(0) == 'z')) {
			setFileCorrect(true);
		} else {
			setFileCorrect(false);
		}
		return null;
	}

	/**
	 * @return Returns the fileType.
	 * @exception
	 * @since 1.0
	 */

	public static int getFileType() {
		return fileType;
	}

	/**
	 * @param fileType
	 *            The fileType to set.
	 * @exception
	 * @since 1.0
	 */

	public static void setFileType(final int fileType) {
		CheckImportError.fileType = fileType;
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
	 * @return Returns the fileCorrect.
	 * @exception
	 * @since 1.0
	 */

	public static boolean isFileCorrect() {
		return fileCorrect;
	}

	/**
	 * @param fileCorrect
	 *            The fileCorrect to set.
	 * @exception
	 * @since 1.0
	 */

	public static void setFileCorrect(final boolean fileCorrect) {
		CheckImportError.fileCorrect = fileCorrect;
	}

	/**
	 * The method to initialize the count of errors.
	 * 
	 * @see
	 * @since 1.0
	 */
	public static void initialize() {
		count = 0;
		fileCorrect = true;
		fileType = TYPE_BIBLIOGRAPHIC;
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
