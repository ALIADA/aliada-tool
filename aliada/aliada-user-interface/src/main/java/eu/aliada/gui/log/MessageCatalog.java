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
		
	String _00001_STARTING = LT + MODULE_NAME + "-00001> : User Interface is starting..."; 
	String _00002_STOPPED = LT + MODULE_NAME + "-00002> : User Interface has been shutdown.";	
	String _00003_STARTING_LOGON_INTERCEPTOR = LT + MODULE_NAME + "-00003> : Intializing LogonInterceptor."; 
	String _00004_STOPPED_LOGON_INTERCEPTOR = LT + MODULE_NAME + "-00004> : Destroying LogonInterceptor.";	
	
	String _00005_DATA_ACCESS_FAILURE = LT + MODULE_NAME + "-00005> : Data access failure has been detected. See below for further details.";
	String _00006_BBDD_CONFIGURATION_INVALID = LT + MODULE_NAME + "-00006> : The database configuration is invalid.";
	String _00010_LOGON_FAILURE = LT + MODULE_NAME + "-00010> : The logon access is invalid.";
	String _00011_SQL_EXCEPTION_LOGON = LT + MODULE_NAME + "-00011> : SQL exception. See below for further details.";
    String _00012_FILE_NOT_FOUND_EXCEPTION = LT + MODULE_NAME + "-00011> : File not found exception. See below for further details.";

}
