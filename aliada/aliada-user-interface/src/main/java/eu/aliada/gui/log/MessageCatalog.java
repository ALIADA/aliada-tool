// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.gui.log;

/**
 * User interface message catalog.
 * 
 * @author Elena 
 * @since 1.0
 */
public interface MessageCatalog {
	String MODULE_NAME = "UserInterface";
	String LT = "<";
		
	String _00001_DATA_ACCESS_FAILURE = LT + MODULE_NAME + "-00001> : Data access failure has been detected. See below for further details.";
	String _00010_LOGON_FAILURE = LT + MODULE_NAME + "-00010> : The logon access is invalid.";
	String _00011_SQL_EXCEPTION_LOGON = LT + MODULE_NAME + "-00011> : SQL exception. See below for further details.";

}
