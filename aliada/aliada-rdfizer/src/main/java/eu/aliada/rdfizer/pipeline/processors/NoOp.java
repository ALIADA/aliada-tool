package eu.aliada.rdfizer.pipeline.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * A simple debug processor.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class NoOp implements Processor {
	@Override
	public void process(final Exchange exchange) throws Exception {
		// Nothing to be done here...
	}
}