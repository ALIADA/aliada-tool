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
    String _00014_MALFORMED_URL_EXCEPTION = LT + MODULE_NAME + "-00014> : Mal formed url exception. See below for further details.";
    String _00015_HTTP_ERROR_CODE = LT + MODULE_NAME + "-00015> : HTTP error code:";
    String _00016_ERROR_READING_XML = LT + MODULE_NAME + "-00016> : Error reading XML file. See below for further details.";
    
    String _00020_MANAGE_FILE_NOT_FOUND = LT + MODULE_NAME + "-00020> : File not found exception.";
    String _00021_MANAGE_NOT_VALIDATED_BY_WELL_FORMED = LT + MODULE_NAME + "-00021> : The file is invalid by well formed.";
    String _00022_MANAGE_NOT_VALIDATED_BY_VISUALIZE_FILE_TYPE = LT + MODULE_NAME + "-00022> : The file type is not the same profile type.";
    String _00023_MANAGE_NOT_VALIDATED_BY_VISUALIZE_MANDATORY = LT + MODULE_NAME + "-00023> : The file has not all mandatory tags.";
    String _00024_MANAGE_NOT_VALIDATED_BY_VISUALIZE = LT + MODULE_NAME + "-00024> : The file is invalid by visualization.";
    String _00025_MANAGE_NOT_VALIDATED_BY_VALIDATION = LT + MODULE_NAME + "-00025> : The file is invalid by validation.";
    String _00026_MANAGE_VALIDATED = LT + MODULE_NAME + "-00026> : Validated file";
    
    String _00030_CONVERSION_RDFIZE_ENABLE = LT + MODULE_NAME + "-00030> : RDFizer enabled";
    String _00031_CONVERSION_RDFIZE_JOB = LT + MODULE_NAME + "-00031> : RDFizer job created";
    
    String _00040_LDS_STARTED = LT + MODULE_NAME + "-00040> : LDS started";
    
    String _00050_LINKING_JOB= LT + MODULE_NAME + "-00040> : Link discovery started";

    
    String _00100_EXCEPTION = LT + MODULE_NAME + "-00100> : Exception. See below for further details.";
    String _00101_TRANSFORMATION_EXCEPTION = LT + MODULE_NAME + "-00101> : Transformation exception. See below for further details.";
    String _00102_PARSER_CONFIGURATION_EXCEPTION = LT + MODULE_NAME + "-00102> : Parser configuration exception. See below for further details.";
    String _00103_SAX_EXCEPTION = LT + MODULE_NAME + "-00103> : SAX exception. See below for further details.";
    
}
