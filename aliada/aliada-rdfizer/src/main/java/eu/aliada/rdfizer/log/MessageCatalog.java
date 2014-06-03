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
}