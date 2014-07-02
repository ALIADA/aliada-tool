// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.xml;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import eu.aliada.rdfizer.Constants;
import eu.aliada.rdfizer.Function;
import eu.aliada.rdfizer.datasource.Cache;
import eu.aliada.rdfizer.datasource.rdbms.JobConfiguration;
import eu.aliada.rdfizer.framework.MainSubjectDetectionRule;
import eu.aliada.rdfizer.framework.UnableToDetermineMainSubectException;
import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * Converts the incoming {@link Document} record in RDF.
 * Conversion happens following rules associated with a specific format.
 * The format itself is an information that has been previously put on a specific message header ('format').
 * 
 * In order to properly work this processor needs:
 * 
 * <ul>
 * 	<li>A valid {@link Document} object in the incoming message body;</li>
 * 	<li>A valid 'format' header in the incoming message;</li>
 * 	<li>A transformation template associated with that format.</li>
 * </ul>
 * 
 * On the third point, the association is done using naming conventions: that is, the system will look for a 
 * template that follows this (naming) convention:
 * 
 * <p>[FORMAT].n3.vm</p>
 * 
 * Where [FORMAT] is the format header. So for example if the message header contains 'lido' as incoming format, then
 * the system will try to find (in the classpath) a template named lido.n3.vm.
 * 
 * The final outcoming of this processor is a string including one or more triples in n3 format. That result will be the 
 * body of the outcoming message.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class SynchXmlDocumentTranslator implements Processor, ApplicationContextAware {
	protected final ThreadLocal<VelocityContext> contexts = new ThreadLocal<VelocityContext>() {
		protected VelocityContext initialValue() {
			final VelocityContext context = new VelocityContext();
			context.put(Constants.XPATH_ATTRIBUTE_NAME, xpath);
			context.put(Constants.FUNCTION_ATTRIBUTE_NAME, function);
			return context;
		};
	};	
	
	protected final Log log = new Log(getClass());
	
	@Autowired
	protected VelocityEngine velocityEngine;
	
	@Autowired
	protected XPath xpath;

	@Autowired
	protected Cache cache;

	@Autowired
	protected Function function;
	
	protected ApplicationContext context;
	
	@Override
	public final void process(final Exchange exchange) throws Exception {
		final Message in = exchange.getIn();
		final String format = in.getHeader(Constants.FORMAT_ATTRIBUTE_NAME, String.class);
		final Integer jobId = in.getHeader(Constants.JOB_ID_ATTRIBUTE_NAME, Integer.class);
		final JobConfiguration configuration = cache.getJobConfiguration(jobId);
		if (configuration == null) {
			log.error(MessageCatalog._00038_UNKNOWN_JOB_ID, jobId);
			throw new IllegalArgumentException(String.valueOf(jobId));
		}

		VelocityContext velocityContext = null;
		try {
			final Template template = velocityEngine.getTemplate(templateName(format));	 
			if (template == null) {
				log.error(MessageCatalog._00040_TEMPLATE_NOT_FOUND, format);
				return;
			}
			
			velocityContext = contexts.get();
			velocityContext.put(Constants.JOB_CONFIGURATION_ATTRIBUTE_NAME, configuration);
			
			populateVelocityContext(velocityContext, in, configuration);
			
			final Writer sw = new StringWriter();
			final Writer w = new BufferedWriter(sw);
			template.merge(velocityContext, w);
			w.flush();
			in.setBody(sw.toString());
		} catch (final ResourceNotFoundException exception) {
			log.error(MessageCatalog._00040_TEMPLATE_NOT_FOUND, exception, format);
		} finally {
			if (velocityContext != null) {
				velocityContext.remove(Constants.MAIN_SUBJECT_ATTRIBUTE_NAME);
				velocityContext.remove(Constants.ROOT_ELEMENT_ATTRIBUTE_NAME);
				velocityContext.remove(Constants.JOB_CONFIGURATION_ATTRIBUTE_NAME);
			}
		}
	}

	@Override
	public void setApplicationContext(final ApplicationContext context) {
		this.context = context;
	}
	
	/**
	 * Populates the velocity context according with rules of this specific translator.
	 * 
	 * @param velocityContext the velocity context.
	 * @param message the incoming message.
	 * @param configuration the job configuration.
	 * @throws UnableToDetermineMainSubectException in case is not possible to determine the main entity subject.
	 */
	protected void populateVelocityContext(
			final VelocityContext velocityContext, 
			final Message message, 
			final JobConfiguration configuration) throws UnableToDetermineMainSubectException {
		final String format = message.getHeader(Constants.FORMAT_ATTRIBUTE_NAME, String.class);

		@SuppressWarnings("unchecked")
		final MainSubjectDetectionRule<Element, String> rule = (MainSubjectDetectionRule<Element, String>) context.getBean(format + "-subject-detection-rule");
		
		final Document document = message.getBody(Document.class);
		final Element root = document.getDocumentElement();
		final String mainSubject = rule.computeFrom(root, configuration);
		velocityContext.put(Constants.MAIN_SUBJECT_ATTRIBUTE_NAME, mainSubject);
		velocityContext.put(Constants.ROOT_ELEMENT_ATTRIBUTE_NAME, root);
	}
	
	/**
	 * Returns the same of the template associated with the given format.
	 * 
	 * @param format the format of the incoming record.
	 * @return the same of the template associated with the given format.
	 */
	protected String templateName(final String format) {
		return new StringBuilder(format).append(".n3.vm").toString();
	}
}