package eu.aliada.rdfizer.pipeline.processors;

import java.util.Collection;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * A debug outbound processor that counts the outcoming triples.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class JustDebugTriplesCount implements Processor {

	@SuppressWarnings("rawtypes")
	@Override
	public void process(final Exchange exchange) throws Exception {
		final Object incomingData = exchange.getIn().getBody();

		if (incomingData != null && incomingData instanceof Collection) {
			System.out.println(((Collection)incomingData).size());
		} else {
			System.out.println(incomingData);
		}
	}
}
