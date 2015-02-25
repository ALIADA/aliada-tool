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
		
	String _00001_STARTING = LT + MODULE_NAME + "-00001> : LinksDiscovery is starting...";
	String _00012_STOPPED = LT + MODULE_NAME + "-00012> : LinksDiscovery has been shutdown.";	

	String _00021_NEW_JOB_REQUEST = LT + MODULE_NAME + "-00021> : A job creation request has been detected.";		
	String _00022_MISSING_INPUT_PARAM = LT + MODULE_NAME + "-00022> : Invalid request: mandatory parameter '%s' is missing.";		
	String _00023_JOB_CONFIGURATION_NOT_FOUND = LT + MODULE_NAME + "-00023> : Unable to find a configuration for creating a job with id %s.";	
	String _00024_DATA_ACCESS_FAILURE = LT + MODULE_NAME + "-00024> : Data access failure has been detected. See below for further details.";	
	String _00025_GET_JOB_REQUEST = LT + MODULE_NAME + "-00025> : A job info request has been detected.";		
	String _00026_GET_NEW_DB_CONNECTION = LT + MODULE_NAME + "-00026> : Getting a new DB connection.";		

	String _00030_STARTING = LT + MODULE_NAME + "-00030> : LinksDiscovery programming is starting.";	
	String _00031_FILE_NOT_FOUND = LT + MODULE_NAME + "-00031> : File %s not found.";
	String _00032_BAD_FILE = LT + MODULE_NAME + "-00032> : Bad file format %s.";
	String _00033_EXTERNAL_PROCESS_START_FAILURE = LT + MODULE_NAME + "-00033> : Could not launch the external process %s.";		
	String _00034_FILE_CREATION_FAILURE = LT + MODULE_NAME + "-00034> : Could not create file %s.";
	String _00035_GET_LINKING_CONFIG_FILES = LT + MODULE_NAME + "-00035> : Get linking processes config files.";
	String _00036_CREATE_CRONTAB_FILE = LT + MODULE_NAME + "-00036> : Creating crontab file.";
	String _00037_CREATE_LINKING_XML_CONFIG_FILE = LT + MODULE_NAME + "-00037> : Creating linking XML config file %d.";
	String _00038_CREATE_LINKING_PROP_FILE = LT + MODULE_NAME + "-00038> : Creating linking properties file %d.";
	String _00039_INSERT_LINKING_CRONTAB_FILE = LT + MODULE_NAME + "-00039> : Inserting linking process %d in crontab file.";
	String _00040_EXECUTING_CRONTAB = LT + MODULE_NAME + "-00040> : Executing crontab command.";	
	String _00041_STOPPED = LT + MODULE_NAME + "-00041> : LinksDiscovery programming has finished.";	
	String _00042_UPDATE_SUBJOB_DDBB = LT + MODULE_NAME + "-00042> : Updating job %d - subjob %d in DDBB.";
}
