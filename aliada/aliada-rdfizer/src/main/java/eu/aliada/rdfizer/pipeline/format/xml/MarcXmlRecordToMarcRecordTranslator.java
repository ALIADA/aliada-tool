// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.xml;

import java.io.ByteArrayInputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.marc4j.MarcReader;
import org.marc4j.MarcXmlReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * An XML document translator.
 * 
 * @author Emiliano Cammilletti
 * @since 1.0
 */
public class MarcXmlRecordToMarcRecordTranslator implements Processor, ApplicationContextAware {

	private ApplicationContext context;
	
	
	@Override
	public void process(final Exchange exchange) throws Exception {
		final String marcXmlRecord = exchange.getIn().getBody(String.class);
		MarcReader reader = new MarcXmlReader(new ByteArrayInputStream(marcXmlRecord.getBytes()));
		exchange.getIn().setBody(reader.next());
	}
	
	
	@Override
	public void setApplicationContext(final ApplicationContext context) {
		this.context = context;
	}
	

}