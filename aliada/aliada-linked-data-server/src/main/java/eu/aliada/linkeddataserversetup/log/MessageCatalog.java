// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-linked-data-server
// Responsible: ALIADA Consortium
package eu.aliada.linkeddataserversetup.log;

/**
 * Linked Data Server message catalog.
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public interface MessageCatalog {
	String MODULE_NAME = "LinkedDataServer";
	String LT = "<";
		
	String _00001_STARTING = LT + MODULE_NAME + "-00001> : LinkedDataServer is starting...";
	String _00012_STOPPED = LT + MODULE_NAME + "-00012> : LinkedDataServer has been shutdown.";	

	String _00021_NEW_JOB_REQUEST = LT + MODULE_NAME + "-00021> : A job creation request has been detected.";		
	String _00022_MISSING_INPUT_PARAM = LT + MODULE_NAME + "-00022> : Invalid request: mandatory parameter '%s' is missing.";		
	String _00023_JOB_CONFIGURATION_NOT_FOUND = LT + MODULE_NAME + "-00023> : Unable to find a configuration for creating a job with id %s.";	
	String _00024_DATA_ACCESS_FAILURE = LT + MODULE_NAME + "-00024> : Data access failure has been detected. See below for further details.";	
	String _00025_GET_JOB_REQUEST = LT + MODULE_NAME + "-00025> : A job info request has been detected.";		

	String _00030_STARTING = LT + MODULE_NAME + "-00030> : LinkedDataServer programming is starting.";	
	String _00031_FILE_NOT_FOUND = LT + MODULE_NAME + "-00031> : File %s not found.";
	String _00033_EXTERNAL_PROCESS_START_FAILURE = LT + MODULE_NAME + "-00033> : Could not launch the external process %s.";		
	String _00036_GET_ISQL_COMMANDS_FILE = LT + MODULE_NAME + "-00036> : Getting ISQL commands file.";
	String _00037_ENCODE_PARAMS = LT + MODULE_NAME + "-00037> : Encode params for ISQL commands.";
	String _00038_ENCODING_ERROR = LT + MODULE_NAME + "-00038> : Encoding error. See below for further details..";
	String _00040_EXECUTING_ISQL = LT + MODULE_NAME + "-00040> : Executing ISQL command.";	
	String _00041_STOPPED = LT + MODULE_NAME + "-00041> : LinkedDataServer programming has finished.";	
	String _00057_UPDATING_JOB_DDBB = LT + MODULE_NAME + "-00057> : Updating job %d in DDBB.";	
}
