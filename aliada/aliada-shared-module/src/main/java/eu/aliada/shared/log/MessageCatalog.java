// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-linked-data-server
// Responsible: ALIADA Consortium
package eu.aliada.shared.log;

/**
 * Shared module message catalog.
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public interface MessageCatalog {
	String MODULE_NAME = "SharedModule";
	String LT = "<";
		
	String _00033_EXTERNAL_PROCESS_START_FAILURE = LT + MODULE_NAME + "-00033> : Could not launch the external process %s. See below for further details.";		
	String _00034_HTTP_PUT_FAILED = LT + MODULE_NAME + "-00034> : HTTP PUT failed %s. See below for further details.";
	String _00035_SPARQL_FAILED = LT + MODULE_NAME + "-00035> : SPARQL %s failed. See below for further details.";
	String _00036_FILE_READING_FAILURE = LT + MODULE_NAME + "-00036> : Error reading file %s.";
	String _00049_DEBUG_TRIPLES = LT + MODULE_NAME + "-00049> : *** TRIPLES ***\n%s";
}
