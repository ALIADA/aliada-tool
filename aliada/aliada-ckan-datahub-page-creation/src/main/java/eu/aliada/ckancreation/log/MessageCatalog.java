// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.log;

/**
 * CKAN Creation message catalog.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
public interface MessageCatalog {
	String MODULE_NAME = "CKANCreation";
	String LT = "<";
		
	String _00001_STARTING = LT + MODULE_NAME + "-00001> : CKANCreation is starting...";
	String _00012_STOPPED = LT + MODULE_NAME + "-00012> : CKANCreation has been shutdown.";	

	String _00021_NEW_JOB_REQUEST = LT + MODULE_NAME + "-00021> : A job creation request has been detected.";		
	String _00022_MISSING_INPUT_PARAM = LT + MODULE_NAME + "-00022> : Invalid request: mandatory parameter '%s' is missing.";		
	String _00023_JOB_CONFIGURATION_NOT_FOUND = LT + MODULE_NAME + "-00023> : Unable to find a configuration for creating a job with id %s.";	
	String _00024_DATA_ACCESS_FAILURE = LT + MODULE_NAME + "-00024> : Data access failure has been detected. See below for further details.";	
	String _00025_GET_JOB_REQUEST = LT + MODULE_NAME + "-00025> : A job info request has been detected.";		
	String _00026_GET_NEW_DB_CONNECTION = LT + MODULE_NAME + "-00026> : Getting a new DB connection.";		

	String _00030_STARTING = LT + MODULE_NAME + "-00030> : CKANCreation programming is starting.";	
	String _00031_FILE_NOT_FOUND = LT + MODULE_NAME + "-00031> : File %s not found.";
	String _00032_OBJECT_CONVERSION_FAILURE = LT + MODULE_NAME + "-00032> : Error converting object.";
	String _00033_EXTERNAL_PROCESS_START_FAILURE = LT + MODULE_NAME + "-00033> : Could not launch the external process %s.";		
	String _00034_FILE_CREATION_FAILURE = LT + MODULE_NAME + "-00034> : Could not create file %s.";
	String _00035_FILE_ACCESS_FAILURE = LT + MODULE_NAME + "-00035> : Could not access file %s.";
	String _00036_GET_ORGANIZATION_CKAN_FAILURE = LT + MODULE_NAME + "-00036> : Error getting organization from CKAN: %s";
	String _00037_UPDATE_ORGANIZATION_CKAN_FAILURE = LT + MODULE_NAME + "-00037> : Error updating organization in CKAN: %s";
	String _00038_CREATE_DATASET_CKAN_FAILURE = LT + MODULE_NAME + "-00038> : Error creating dataset in CKAN: %s";
	String _00039_CREATE_RESOURCE_CKAN_FAILURE = LT + MODULE_NAME + "-00039> : Error creating dataset in CKAN: %s";
	String _00040_EXECUTING_ISQL = LT + MODULE_NAME + "-00040> : Executing ISQL command.";	
	String _00041_STOPPED = LT + MODULE_NAME + "-00041> : CKANCreation programming has finished.";	
	String _00042_CKAN_ORG_UPDATED = LT + MODULE_NAME + "-00042> : Organization updated in CKAN.";	
	String _00043_CKAN_DATASET_CREATED = LT + MODULE_NAME + "-00043> : Dataset created in CKAN.";	
	String _00044_CKAN_RESOURCE_CREATED = LT + MODULE_NAME + "-00044> : Resource %s created in CKAN.";	
	String _00057_UPDATING_JOB_DDBB = LT + MODULE_NAME + "-00057> : Updating job %d in DDBB.";	
}
