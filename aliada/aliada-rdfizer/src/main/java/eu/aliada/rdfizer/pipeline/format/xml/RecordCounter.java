// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.xml;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import eu.aliada.rdfizer.Constants;
import eu.aliada.rdfizer.datasource.Cache;
import eu.aliada.rdfizer.datasource.rdbms.JobInstance;
import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.rdfizer.mx.InMemoryJobResourceRegistry;
import eu.aliada.rdfizer.rest.JobResource;
import eu.aliada.shared.log.Log;

/**
 * A simple processor that sizes the (XML) incoming stream.
 * This is important as first step of each processing because we need to know in advance how many
 * records compose a job.
 * 
 * At the end of the counting job, this processor updates the management interface of the 
 * target job, so monitoring can be done in a more efficient way.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class RecordCounter implements Processor {
	private final Log log = new Log(RecordCounter.class); 
	
	private final String recordElementName;
	private final String recordElementNameWithNamespace;
	
	@Autowired
	protected Cache cache;
	
	@Autowired
	protected InMemoryJobResourceRegistry jobRegistry;
	
	/**
	 * Builds a new {@link RecordCounter} with the following element name (as a split criteria).
	 * 
	 * @param recordElementName the record element name.
	 */
	public RecordCounter(final String recordElementName) {
		this.recordElementName = recordElementName;
		this.recordElementNameWithNamespace = ":" + recordElementName;
	}
	
	@Override
	public void process(final Exchange exchange) throws Exception {
		final File inputFile = exchange.getIn().getBody(File.class);
		
		final Integer jobId = exchange.getIn().getHeader(Constants.JOB_ID_ATTRIBUTE_NAME, Integer.class);
		final JobInstance configuration = cache.getJobInstance(jobId);
		if (configuration == null) {
			log.error(MessageCatalog._00038_UNKNOWN_JOB_ID, jobId);
			throw new IllegalArgumentException(String.valueOf(jobId));
		}
		
		final SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(inputFile, new DefaultHandler() {
			private int howManyRecords;
			
			@Override
			public void startElement(
					final String uri, 
					final String localName, 
					final String qName, 
					final Attributes attributes) throws SAXException {
				if (qName.endsWith(recordElementNameWithNamespace) || qName.equals(recordElementName)) {
					howManyRecords++;
				}
			}
			
			@Override
			public void endDocument() throws SAXException {
				log.info(MessageCatalog._00046_JOB_SIZE, jobId, howManyRecords);
				final JobResource resource = jobRegistry.getJobResource(jobId);
				if (resource != null) {
					resource.setTotalRecordsCount(howManyRecords);					
				}
			}
		});
	}
}
