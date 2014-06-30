// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.xml;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * Converts an XML stream to a Document object.
 * The incoming stream is supposed to be a valid XML document.
 * 
 * @author Emiliano Cammilletti
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class XmlRecordToDocumentTranslator implements Processor {
	private final Log logger = new Log(XmlRecordToDocumentTranslator.class);
	
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
	
	@Override
	public void process(final Exchange exchange) throws Exception {
		final String xmlRecord = exchange.getIn().getBody(String.class);
		try {
			final Document product =  builders.get().parse(new InputSource(new StringReader(xmlRecord)));
			exchange.getIn().setBody(product);
		} catch (final Exception exception) {
			logger.error(MessageCatalog._00039_STRING_TO_XML_FAILURE, exception);
			logger.debug(MessageCatalog._00039_STRING_TO_XML_FAILURE_DEBUG_MESSAGE, xmlRecord);
			throw exception;
		}
	}
}