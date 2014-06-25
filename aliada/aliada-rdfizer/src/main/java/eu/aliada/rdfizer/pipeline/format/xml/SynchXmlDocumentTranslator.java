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
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import eu.aliada.rdfizer.Constants;
import eu.aliada.rdfizer.Function;
import eu.aliada.rdfizer.datasource.Cache;
import eu.aliada.rdfizer.datasource.rdbms.JobConfiguration;
import eu.aliada.rdfizer.framework.MainSubjectDetectionRule;
import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.shared.log.Log;

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
			context.put(Constants.XPATH_ATTRIBUTE_NAME, xpath);
			context.put(Constants.FUNCTION_ATTRIBUTE_NAME, function);
			return context;
		};
	};	
	
	private final Log log = new Log(SynchXmlDocumentTranslator.class);
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
	private XPath xpath;

	@Autowired
	private Cache cache;

	@Autowired
	private Function function;
	
	private ApplicationContext context;
	
	@Override
	public void process(final Exchange exchange) throws Exception {
		final Message in = exchange.getIn();
		final String recordAsString = in.getBody(String.class);
		final String format = in.getHeader(Constants.FORMAT_ATTRIBUTE_NAME, String.class);
		final Integer jobId = in.getHeader(Constants.JOB_ID_ATTRIBUTE_NAME, Integer.class);
		final JobConfiguration configuration = cache.getJobConfiguration(jobId);
		if (configuration == null) {
			log.error(MessageCatalog._00038_UNKNOWN_JOB_ID, jobId);
			throw new IllegalArgumentException(String.valueOf(jobId));
		}
		
		final Document document = builders.get().parse(new InputSource(new StringReader(recordAsString)));
		final Element root = document.getDocumentElement();
		
		@SuppressWarnings("unchecked")
		final MainSubjectDetectionRule<Element, String> rule = (MainSubjectDetectionRule<Element, String>) context.getBean(format + "-subject-detection-rule");
		
		final String mainSubject = rule.computeFrom(root, configuration);
		
		final VelocityContext velocityContext = contexts.get();
		velocityContext.put(Constants.MAIN_SUBJECT_ATTRIBUTE_NAME, mainSubject);
		velocityContext.put(Constants.ROOT_ELEMENT_ATTRIBUTE_NAME, root);
		velocityContext.put(Constants.JOB_CONFIGURATION_ATTRIBUTE_NAME, configuration);
		
		try {
			final Writer sw = new StringWriter();
			final Writer w = new BufferedWriter(sw);
			final Template template = velocityEngine.getTemplate(format + ".n3.vm");	 
			template.merge(velocityContext, w);
			w.flush();
			in.setBody(sw.toString());
		} finally {
			velocityContext.remove(Constants.MAIN_SUBJECT_ATTRIBUTE_NAME);
			velocityContext.remove(Constants.ROOT_ELEMENT_ATTRIBUTE_NAME);
			velocityContext.remove(Constants.JOB_CONFIGURATION_ATTRIBUTE_NAME);
		}
	}

	@Override
	public void setApplicationContext(final ApplicationContext context) {
		this.context = context;
	}
}