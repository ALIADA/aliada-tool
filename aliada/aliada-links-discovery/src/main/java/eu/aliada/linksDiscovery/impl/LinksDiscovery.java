// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.linksDiscovery.impl;

import eu.aliada.linksDiscovery.log.MessageCatalog;
import eu.aliada.shared.log.Log;
import de.fuberlin.wiwiss.silk.Silk;
import java.io.File;


/**
 * Links discovery implementation via Silk library. 
 * @author Idoia Murua
 * @since 1.0
 */
public class LinksDiscovery {
	private final Log logger = new Log(LinksDiscovery.class);

	/**
	 * Links discovery implementation.
	 *
     * @param configurationFile	The configuration file.  
     * @param numThreads		The number of threads to be used for matching. 
     * @param reload			Specifies if the entity cache is to be reloaded 
     *
     * @return 					
     * @since 1.0
	 */
	public void discoveryLinks(File configurationFile, int numThreads, boolean reload) {
		logger.info(MessageCatalog._00001_STARTING);
		Silk.executeFile(configurationFile, (String) null, numThreads, reload);
	}
	
}
