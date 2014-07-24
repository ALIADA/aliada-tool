// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortium
package eu.aliada.rdfizer.log;

/**
 * RDF-izer message catalog.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public interface MessageCatalog {
	String MODULE_NAME = "RDF-IZER";
	String LT = "<";
		
	String _00001_STARTING = LT + MODULE_NAME + "-00001> : RDF-izer is starting...";
	String _00010_STARTED = LT + MODULE_NAME + "-00010> : RDF-izer open for e-business.";
	String _00011_STOPPING = LT + MODULE_NAME + "-00011> : Shutdown procedure for RDF-izer has been initiated...";
	String _00012_STOPPED = LT + MODULE_NAME + "-00012> : RDF-izer has been shutdown.";	
	String _00020_WRONG_FILE_PERMISSIONS = LT + MODULE_NAME
			+ "-00020> : Input file (%s) doesn't have right permission. As consequence of that it will be skept.";
	String _00021_BAD_MARCXML_FILE = LT + MODULE_NAME + "-00013> : Bad input file. Are you sure %s is a valid MARCXML file?";
	String _00022_PIPELINE_STARTING = LT + MODULE_NAME + "-00022> : RDF-izer pipeline is starting...preliminary checks start...";
	String _00023_PIPELINE_STARTED = LT + MODULE_NAME + "-00023> : RDF-izer pipeline configuration seems Ok. The pipeline is active.";
	String _00024_PIPELINE_STOPPING = LT + MODULE_NAME + "-00024> : Shutdown procedure for RDF-izer pipeline has been initiated...";
	String _00025_PIPELINE_STOPPED = LT + MODULE_NAME + "-00025> : RDF-izer pipeline has been shutdown.";
	String _00026_NO_STREAM_AVAILABLE = LT + MODULE_NAME + "-00026> : RecordSplitter cannot execute: no available stream to process.";
	String _00027_INPUSTREAM_IO_FAILURE = LT + MODULE_NAME + "-00027> : RecordSplitter raised an I/O failure (datafile was %s):. See below for further details.";		
	String _00028_MISSING_INPUT_PARAM = LT + MODULE_NAME + "-00028> : Invalid request: mandatory parameter '%s' is missing.";		
	String _00029_NEW_JOB_REQUEST = LT + MODULE_NAME + "-00029> : A job creation request has been detected.";		
	String _00030_NEW_JOB_REQUEST_DEBUG = LT + MODULE_NAME + "-00030> : Job creation request formally valid. Configuration ID is %s and datafile is %s.";		
	String _00031_DATA_ACCESS_FAILURE = LT + MODULE_NAME + "-00031> : Data access failure has been detected. See below for further details.";	
	String _00032_JOB_CONFIGURATION_NOT_FOUND = LT + MODULE_NAME + "-00032> : Unable to find a configuration for creating a job with id %s.";	
	String _00033_UNSUPPORTED_FORMAT = LT + MODULE_NAME + "-00033> : Unsupported format (%s) detected for job %s.";		
	String _00034_NWS_SYSTEM_INTERNAL_FAILURE = LT + MODULE_NAME + "-00034> : System internal failure. See below for further details.";
	String _00035_XPATH_COMPILATION_FAILURE = LT + MODULE_NAME + "-00035> : Unable to compile XPATH expression (%s).";
	String _00036_UNSUPPORTED_INPUT_ENCODING = LT + MODULE_NAME + "-00036> : Unsupported input encoding (%s) in template (%s) processing request.";	
	String _00037_WRONG_INPUT_FILENAME = 
			LT + MODULE_NAME + "-00037> : RDFizer requires a data file with the following format " 
					+ "<name>[.suffix].<valid job id>. Your input file name (%s) doesn't follow this convention.";	
	String _00038_UNKNOWN_JOB_ID = LT + MODULE_NAME + "-00038> : Unable to find the job configuration associated with job identifier %s";	
	String _00039_STRING_TO_XML_FAILURE = LT + MODULE_NAME + "-00039> : Unable to translate  a given stream in a DOM Document. See below for further details.";	
	String _00039_STRING_TO_XML_FAILURE_DEBUG_MESSAGE = LT + MODULE_NAME + "-00039> : ";	
	String _00040_TEMPLATE_NOT_FOUND = LT + MODULE_NAME + "-00040> : Transformation template associated with format %s cannot be found. Check your configuration.";	
	String _00041_FRBR_ENTITY_DETECTION_FAILED = 
			LT + MODULE_NAME + "-00041> : FRBR entity detection produced no results. As consequence of that the current record will be skept.";	
	String _00042_MX_SUBSYSTEM_FAILURE = LT + MODULE_NAME + "-00042> : Management subsystem failure. See below for further details.";
	String _00043_MX_RESOURCE_ALREADY_REGISTERED = LT + MODULE_NAME + "-00043> : Management interface already registered (%s). Probably it hasn't been properly unregistered.";
	String _00044_MX_RESOURCE_REGISTERED = LT + MODULE_NAME + "-00044> : Management interface %s has been registered.";
	String _00045_MX_JOB_RESOURCE_REGISTRATION_FAILED = LT + MODULE_NAME + "-00045> : Management interface for Job %s cannot be registered. See below for further details.";
	String _00046_JOB_SIZE = LT + MODULE_NAME + "-00046> : Job associated with id %s consists of %s records.";
	String _00047_EMPTY_INCOMING_RECORD_STREAM = LT + MODULE_NAME + "-00047> : Incoming string stream doesn't contain a valid record.";
	String _00048_JOB_COMPLETED = LT + MODULE_NAME + "-00048> : Job %s marked as completed.";
}