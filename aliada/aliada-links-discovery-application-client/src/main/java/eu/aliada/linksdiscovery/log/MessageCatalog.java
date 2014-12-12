// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.linksdiscovery.log;

/**
 * Links discovery message catalog.
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public interface MessageCatalog {
	String MODULE_NAME = "LinksDiscovery";
	String LT = "<";
		
	String _00024_DATA_ACCESS_FAILURE = LT + MODULE_NAME + "-00024> : Data access failure has been detected. See below for further details.";	
	String _00031_FILE_NOT_FOUND = LT + MODULE_NAME + "-00031> : File %s not found.";
	String _00032_BAD_FILE = LT + MODULE_NAME + "-00032> : Bad file format %s.";
	String _00033_EXTERNAL_PROCESS_START_FAILURE = LT + MODULE_NAME + "-00033> : LinksDiscovery could not launch the external process %s.";		
	String _00034_FILE_CREATION_FAILURE = LT + MODULE_NAME + "-00034> : Could not create file %s.";
	String _00040_EXECUTING_CRONTAB = LT + MODULE_NAME + "-00040> : Executing crontab command.";	

	String _00050_STARTING = LT + MODULE_NAME + "-00050> : LinksDiscovery process is starting.";	
	String _00051_GET_CONFIG_FILE_PROPERTIES = LT + MODULE_NAME + "-00051> : Getting properties from config file %s.";
	String _00052_CONNECT_DDBB = LT + MODULE_NAME + "-00052> : Connecting to DDBB.";
	String _00053_SILK_STARTING = LT + MODULE_NAME + "-00053> : Starting SILK with parameters: configfile=%s, numthreads=%d, reloadSource=%s, reloadTarget=%s .";
	String _00054_SILK_EXCEPTION = LT + MODULE_NAME + "-00054> : SILK launched an exception. See below for further details.";
	String _00055_SILK_FINISHED = LT + MODULE_NAME + "-00055> : Finished SILK with parameters: configfile=%s, numthreads=%d, reloadSource=%s, reloadTarget=%s .";
	String _00056_UPDATING_SUBJOB_DDBB = LT + MODULE_NAME + "-00056> : Updating job %d - subjob %d in DDBB.";	
	String _00057_UPDATING_JOB_DDBB = LT + MODULE_NAME + "-00057> : Updating job %d in DDBB.";	
	String _00057_SUBJOB_PARAMETERS = LT + MODULE_NAME + "-00057> : Subjob parameters: job=%d subjob=%d propertiesfile=%s.";	
	String _00058_SUBJOB_PARAMETERS_INCORRECT = LT + MODULE_NAME + "-00058> : Incorrect Subjob parameters: %s.";	
	String _00059_STOPPING = LT + MODULE_NAME + "-00059> : LinksDiscovery process finished.";	
	String _00060_VALIDATING_NUM_GENERATED_LINKS = LT + MODULE_NAME + "-00060> : Validating the number of generated links with config. file %s.";	
	String _00061_NUM_GENERATED_LINKS = LT + MODULE_NAME + "-00061> : The number of generated links is %d.";	
	String _00062_XML_FILE_PARSING_FAILURE = LT + MODULE_NAME + "-00062> : Error parsing XML file %s.";	
	String _00063_REMOVING_SUBJOB_FROM_CRONTAB = LT + MODULE_NAME + "-00063> : Removing job %d subjob %d from crontab.";	
	String _00064_GET_PROPERTIES_FROM_DDBB = LT + MODULE_NAME + "-00064> : Getting properties from DDBB job %d subjob %d .";
	String _00065_REMOVING_CONFIG_FILES = LT + MODULE_NAME + "-00065> : Removing configuration files.";
	String _00066_FILE_REMOVING_FAILURE = LT + MODULE_NAME + "-00066> : Error removing configuration files %s.";
	String _00067_SUBJOB_CONFIGURATION_NOT_FOUND = LT + MODULE_NAME + "-00067> : Unable to find a configuration for creating a subjob with job_id %s, subjob_id %d.";	
	String _00068_UPLOADING_GENERATED_LINKS = LT + MODULE_NAME + "-00068> : Uploading the generated links in triples files to the RDF store.";
	String _00069_TRIPLES_FILE_UPLOAD_ERROR = LT + MODULE_NAME + "-00069> : The triples file %s with the generated links could not be uploaded.";
}