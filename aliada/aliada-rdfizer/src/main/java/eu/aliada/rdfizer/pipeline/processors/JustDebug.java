package eu.aliada.rdfizer.pipeline.processors;

import java.util.Collection;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * A simple debug processor.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class JustDebug implements Processor {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void process(final Exchange exchange) throws Exception {
		final Object incomingData = exchange.getIn().getBody();
		if (incomingData != null && incomingData instanceof Collection) {
			((Collection)incomingData).stream().forEach(triple -> System.out.println(triple));
		} else {
			System.out.println(incomingData);
		}
	}
}
