// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.w3c.dom.Document;
/**
 * A process to converter an Xml format to Document object.
 * 
 * @author Emiliano Cammilletti
 * @since 1.0
 */
public class MarcXmlRecordToDocumentTranslator implements Processor, ApplicationContextAware {

	
	final ThreadLocal<DocumentBuilder> builders = new ThreadLocal<DocumentBuilder>() {
		protected DocumentBuilder initialValue() {
			try {
				final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setNamespaceAware(false);
				return factory.newDocumentBuilder();
			} catch (final ParserConfigurationException exception) {
				throw new IllegalStateException(exception);
			}
		};
	};
	
	@SuppressWarnings("unused")
	private ApplicationContext context;
	
	
	@Override
	public void process(final Exchange exchange) throws Exception {
		final String xmlRecord = exchange.getIn().getBody(String.class);
		final DocumentBuilder db =  builders.get();
        final InputStream is = new ByteArrayInputStream(xmlRecord.getBytes("utf-8"));
        final Document target = db.parse(is);
		exchange.getIn().setBody(target);
	}
	
	
	@Override
	public void setApplicationContext(final ApplicationContext context) {
		this.context = context;
	}
	

}