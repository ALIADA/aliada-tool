// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.linksDiscovery.log;

/**
 * RDF-izer message catalog.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public interface MessageCatalog {
	String MODULE_NAME = "LinksDiscovery";
	String LT = "<";
		
	String _00001_STARTING = LT + MODULE_NAME + "-00001> : LinksDiscovery REST services are starting...";
	String _00012_STOPPED = LT + MODULE_NAME + "-00012> : LinksDiscovery REST services have been shutdown.";	
	String _00020_STARTING = LT + MODULE_NAME + "-00020> : LinksDiscovery is starting.";	
}