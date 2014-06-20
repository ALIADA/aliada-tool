// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.xml;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.Triple;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import eu.aliada.rdfizer.pipeline.rules.Registry;
import eu.aliada.rdfizer.pipeline.rules.XPathBasedRule;

/**
 * An XML document translator.
 * The prefix "Synch" means synchronous, indicating that this processor executes the conversion 
 * job on a given document *sequentially*; specifically that means each (conversion) rule, 
 * associated with the incoming format, found in the registry will be processed sequentially.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class SynchXmlDocumentTranslator implements Processor, ApplicationContextAware {
	
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
	
	final ThreadLocal<Registry> registries = new ThreadLocal<Registry>() {
		protected Registry initialValue() {
			return (Registry) context.getBean(format + "-registry");
		};
	};
			
	private ApplicationContext context;
	private String format;
	
	/**
	 * Builds a new translator associated with a given format.
	 * The format name will be used for registry lookup. 
	 * 
	 * @param accept the format associated with this translator.
	 */
	public SynchXmlDocumentTranslator(final String accept) {
		this.format = accept;
	}
	
	@Override
	public void process(final Exchange exchange) throws Exception {
		final String recordAsString = exchange.getIn().getBody(String.class);
		final List<Triple> triples = new ArrayList<Triple>();
		
		final Registry registry = registries.get();
			
		final Document document = builders.get().parse(new InputSource(new StringReader(recordAsString)));
		
		final Node subject = registry.getSubjectRule().execute(
				null,
				document, 
				null);

		if (subject != null) {
			for (final XPathBasedRule<List<Triple>> rule : registries.get()) {
				rule.execute(subject, document, triples);
			}
		}
		
		
		exchange.getIn().setBody(triples);
	}

	@Override
	public void setApplicationContext(final ApplicationContext context) {
		this.context = context;
	}
}