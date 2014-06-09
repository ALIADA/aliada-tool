// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortium
package eu.aliada.rdfizer.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * RDF-izer startup and shutdown listener.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class ApplicationLifecycleListener implements ServletContextListener {
	private Log logger = new Log(ApplicationLifecycleListener.class);
	
	@Override
	public void contextInitialized(final ServletContextEvent event) {
		logger.info(MessageCatalog._00001_STARTING);

		logger.info(MessageCatalog._00010_STARTED);
	}

	@Override
	public void contextDestroyed(final ServletContextEvent event) {
		logger.info(MessageCatalog._00011_STOPPING);
		
		logger.info(MessageCatalog._00012_STOPPED);	
	}
}
