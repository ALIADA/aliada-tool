// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.xml;

import java.io.BufferedWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import eu.aliada.rdfizer.Function;
import eu.aliada.rdfizer.framework.MainSubjectDetectionRule;

/**
 * An XML document translator.
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
	
	final ThreadLocal<VelocityContext> contexts = new ThreadLocal<VelocityContext>() {
		protected VelocityContext initialValue() {
			final VelocityContext context = new VelocityContext();
			context.put("xpath", xpath);
			context.put("function", function);
			return context;
		};
	};	
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
	private XPath xpath;
	
	@Autowired
	private Function function;
	
	private ApplicationContext context;
	
	@Override
	public void process(final Exchange exchange) throws Exception {
		final Message in = exchange.getIn();
		final String recordAsString = in.getBody(String.class);
		final String format = in.getHeader("format", String.class);
		final Document document = builders.get().parse(new InputSource(new StringReader(recordAsString)));
		
		@SuppressWarnings("unchecked")
		final MainSubjectDetectionRule<Document, String> rule = (MainSubjectDetectionRule<Document, String>) context.getBean(format + "-subject-detection-rule");
		
		final String mainSubject = rule.computeFrom(document);
		
		final VelocityContext velocityContext = contexts.get();
		velocityContext.put("mainSubject", mainSubject);
		velocityContext.put("root", document.getDocumentElement());
		try {
			final Writer sw = new StringWriter();
			final Writer w = new BufferedWriter(sw);
			final Template template = velocityEngine.getTemplate(format + ".n3.vm");	 
			template.merge(velocityContext, w);
			in.setBody(sw.toString());
		} finally {
			contexts.get().remove("mainSubject");
			contexts.get().remove("root");
		}
	}

	@Override
	public void setApplicationContext(final ApplicationContext context) {
		this.context = context;
	}
}