package eu.aliada.rdfizer.pipeline.common;

import java.util.Collection;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class JustDebug implements Processor {

	@SuppressWarnings("rawtypes")
	@Override
	public void process(final Exchange exchange) throws Exception {
		Object o = exchange.getIn().getBody();
		if (o != null && o instanceof Collection) {
			Collection c = (Collection) o;
			
			for (Object object : c) {
				System.out.println(object);
			}
		} else
		{
			System.out.println(o);
		}
	}
}
