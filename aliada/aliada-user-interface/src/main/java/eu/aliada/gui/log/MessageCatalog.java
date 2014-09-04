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
	String _00007_CHANGE_LOCALE = LT + MODULE_NAME + "-00007> : The locale is changed to: ";
	String _00010_LOGON_FAILURE = LT + MODULE_NAME + "-00010> : The logon access is invalid.";
	String _00011_SQL_EXCEPTION = LT + MODULE_NAME + "-00011> : SQL exception. See below for further details.";
	String _00012_IO_EXCEPTION = LT + MODULE_NAME + "-00012> : IO exception. See below for further details.";
	
    String _00013_FILE_NOT_FOUND_EXCEPTION = LT + MODULE_NAME + "-00013> : File not found exception. See below for further details.";
    String _00020_MANAGE_FILE_NOT_FOUND = LT + MODULE_NAME + "-00020> : File not found exception.";
    String _00021_MANAGE_NOT_VALIDATED_BY_WELL_FORMED = LT + MODULE_NAME + "-00021> : The file is invalid by well formed.";
    String _00022_MANAGE_NOT_VALIDATED_BY_VISUALIZE_FILE_TYPE = LT + MODULE_NAME + "-00022> : The file type is not the same profile type.";
    String _00023_MANAGE_NOT_VALIDATED_BY_VISUALIZE_MANDATORY = LT + MODULE_NAME + "-00023> : The file has not all mandatory tags.";
    String _00024_MANAGE_NOT_VALIDATED_BY_VISUALIZE = LT + MODULE_NAME + "-00024> : The file is invalid by visualization.";
    String _00025_MANAGE_NOT_VALIDATED_BY_VALIDATION = LT + MODULE_NAME + "-00025> : The file is invalid by validation.";

}
