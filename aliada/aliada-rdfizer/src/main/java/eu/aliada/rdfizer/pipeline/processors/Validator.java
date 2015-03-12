// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.processors;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.springframework.beans.factory.annotation.Autowired;

import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.reasoner.ValidityReport;

import eu.aliada.rdfizer.Constants;
import eu.aliada.rdfizer.datasource.Cache;
import eu.aliada.rdfizer.datasource.rdbms.JobInstance;
import eu.aliada.rdfizer.datasource.rdbms.ValidationMessage;
import eu.aliada.rdfizer.datasource.rdbms.ValidationMessageRepository;
import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.rdfizer.mx.InMemoryJobResourceRegistry;
import eu.aliada.rdfizer.pipeline.format.xml.NullObject;
import eu.aliada.rdfizer.rest.JobResource;
import eu.aliada.shared.log.Log;

/**
 * A processor that validates the RDF output using a set of sample records.
 * The size of the sample set is configurable (see below). 
 * 
 * @author Andrea Gazzarini
 * @since 2.0
 */
public class Validator implements Processor {
	private final Log log = new Log(Validator.class); 
	
	@Autowired
	protected Cache cache;
	
	@Autowired
	protected InMemoryJobResourceRegistry jobRegistry;
	
	final Map<Integer, Model> samples = new HashMap<Integer, Model>();
	
	final Model schema;
	final Reasoner reasoner;
	
	@Autowired
	protected ValidationMessageRepository validationMessageRepository;
	
	/**
	 * Builds a new {@link Validator} with the given rule for determining the sample size.
	 */
	public Validator() {
		schema = ModelFactory.createDefaultModel();
				
		RDFDataMgr.read(schema, getClass().getResourceAsStream("/aliada-ontology.owl"), Lang.RDFXML);
        RDFDataMgr.read(schema, getClass().getResourceAsStream("/efrbroo.owl"), Lang.RDFXML);
        RDFDataMgr.read(schema, getClass().getResourceAsStream("/current.owl"), Lang.RDFXML);
        RDFDataMgr.read(schema, getClass().getResourceAsStream("/wgs84_pos.owl"), Lang.RDFXML);
        RDFDataMgr.read(schema, getClass().getResourceAsStream("/time.owl"), Lang.RDFXML);
        RDFDataMgr.read(schema, getClass().getResourceAsStream("/skos-xl.owl"), Lang.RDFXML);
        RDFDataMgr.read(schema, getClass().getResourceAsStream("/0.1.owl"), Lang.RDFXML);		
        
        reasoner = ReasonerRegistry.getOWLMicroReasoner().bindSchema(schema);
	}
	
	@Override
	public void process(final Exchange exchange) throws Exception {
		final Integer jobId = exchange.getIn().getHeader(Constants.JOB_ID_ATTRIBUTE_NAME, Integer.class);
		final JobInstance configuration = cache.getJobInstance(jobId);
		
		if (configuration == null) {
			log.error(MessageCatalog._00038_UNKNOWN_JOB_ID, jobId);
			throw new IllegalArgumentException(String.valueOf(jobId));
		}
		
		// Sanity check: if previous processor didn't put a valid data object in the body
		// the conversion chain for this record must stop here
		if (exchange.getIn().getBody() instanceof NullObject) {
			return;
		}
		
		final Integer triggerSize = exchange.getIn().getHeader(Constants.SAMPLE_SIZE, Integer.class);
		
		final JobResource resource = jobRegistry.getJobResource(jobId);
		if (resource.hasntBeenValidated()) {
			int currentSize = resource.incrementValidationRecordSet();
			if (currentSize < triggerSize || resource.getTotalRecordsCount() == 1) {
				collectSample(jobId, exchange.getIn().getBody(String.class));
				log.info(MessageCatalog._00054_RECORD_ADDED_TO_VALIDATION_SAMPLE, jobId, currentSize + 1, triggerSize);
				
				if (triggerSize == 1) {
					validate(resource, exchange);
				} else {
					exchange.getIn().setBody(null);				
				}
			} else if (resource.hasntBeenValidated()) {
				validate(resource, exchange);
			} 
		}
	}
	
	/**
	 * Validates the collected sample.
	 * 
	 * @param resource the Job resource representation (i.e. the sample owner).
	 * @param exchange the current exchange.
	 */
	public void validate(final JobResource resource, final Exchange exchange) {
		log.info(MessageCatalog._00055_VALIDATING, resource.getID());

		resource.markAsValidated();

		collectSample(resource.getID(), exchange.getIn().getBody(String.class));
		
		final InfModel infmodel = ModelFactory.createInfModel(reasoner, samples.remove(resource.getID()));
        final ValidityReport validity = infmodel.validate();
        if (!validity.isClean()) {
        	log.info(MessageCatalog._00057_VALIDATION_KO, resource.getID());
        	for (final Iterator<ValidityReport.Report> iterator = validity.getReports(); iterator.hasNext(); ) {
        		final ValidityReport.Report report = iterator.next();
        		validationMessageRepository.save(new ValidationMessage(resource.getID(), report.getType(), report.getDescription()));
	        	log.info(MessageCatalog._00058_VALIDATION_MSG, resource.getID(), report.getDescription(), report.getType());
        	}
	        resource.setRunning(false);
	        exchange.setProperty(Exchange.ROUTE_STOP, Boolean.TRUE); 
        } else {
	        log.info(MessageCatalog._00056_VALIDATION_OK, resource.getID());
        }		
	}
	
	/**
	 * Collects the given triples sample.
	 * 
	 * @param jobId the job identifier.
	 * @param triples the sample triples.
	 */
	synchronized void collectSample(final Integer jobId, final String triples) {
		if (triples == null) {
			return;
		}
		
		Model sample = samples.get(jobId);
		if (sample == null) {
			sample = ModelFactory.createDefaultModel();
			samples.put(jobId, sample);
		}
		
		try {
			sample.read(new StringReader(triples), "http://example.org", "N-TRIPLES");
		} catch (final Exception exception) {
			log.info(MessageCatalog._00059_UNABLE_TO_PARSE_SAMPLE, exception, triples);
		}
	}
}  