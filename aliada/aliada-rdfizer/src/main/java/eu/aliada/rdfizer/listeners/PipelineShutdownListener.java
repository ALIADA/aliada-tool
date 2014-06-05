// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortium
package eu.aliada.rdfizer.listeners;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * RDF-izer pipeline shutdown listener.
 * Performs finalization required by shutdown procedure.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class PipelineShutdownListener implements ApplicationListener<ContextClosedEvent>
{
	final static Log LOGGER = new Log(PipelineShutdownListener.class);
	
	@Override
	public void onApplicationEvent(final ContextClosedEvent event)
	{
		LOGGER.info(MessageCatalog._00024_PIPELINE_STOPPING);
		
		LOGGER.info(MessageCatalog._00025_PIPELINE_STOPPED);
	}
}