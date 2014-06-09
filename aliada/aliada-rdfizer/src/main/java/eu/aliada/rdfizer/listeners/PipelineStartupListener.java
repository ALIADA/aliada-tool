// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortium
package eu.aliada.rdfizer.listeners;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * RDF-izer pipeline startup listener.
 * Performs startup checks in order to make sure a minimal set of configuration required are 
 * properly set.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class PipelineStartupListener implements ApplicationListener<ContextStartedEvent>
{
	final static Log LOGGER = new Log(PipelineStartupListener.class);
	
	@Override
	public void onApplicationEvent(final ContextStartedEvent event)
	{
		LOGGER.info(MessageCatalog._00022_PIPELINE_STARTING);
		
		LOGGER.info(MessageCatalog._00023_PIPELINE_STARTED);
	}
}
