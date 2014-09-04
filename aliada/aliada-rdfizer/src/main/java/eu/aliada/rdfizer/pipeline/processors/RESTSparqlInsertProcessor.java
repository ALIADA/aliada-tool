// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.processors;

import static eu.aliada.shared.Strings.isNotNullAndNotEmpty;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;

import eu.aliada.rdfizer.Constants;
import eu.aliada.rdfizer.datasource.Cache;
import eu.aliada.rdfizer.datasource.rdbms.JobInstance;
import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.rdfizer.pipeline.format.xml.NullObject;
import eu.aliada.shared.log.Log;
import eu.aliada.shared.rdfstore.RDFStoreDAO;

/**
 * RDF store outbound connector.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class RESTSparqlInsertProcessor implements Processor {
	private final Log logger = new Log(RESTSparqlInsertProcessor.class);
	
	final RDFStoreDAO rdfStore;
	
	@Autowired
	protected Cache cache;
	
	/**
	 * Builds a new connector using the given targets.
	 * 
	 * @param sparqlEndpointURI the REST SPARQL endpoint of the the target RDF store.
	 * @param user if a valid user is required for inserting triples.
	 * @param password if a valid user is required for inserting triples.
	 * @param batchSize how many statements will be grouped in a single request.
	 * @param cleanUpPeriod the sleep interval of the buffer cleanup worker.
	 */
	public RESTSparqlInsertProcessor() {
		this.rdfStore = new RDFStoreDAO();
	}

	@Override
	public void process(final Exchange exchange) throws Exception {
		if (exchange.getIn().getBody() instanceof NullObject) {
			return;
		}
		
		final String triples = exchange.getIn().getBody(String.class);
		
		logger.debug(MessageCatalog._00049_DEBUG_TRIPLES, triples);
			
		final String graphName = exchange.getIn().getHeader(Constants.GRAPH_ATTRIBUTE_NAME, String.class);
		if (isNotNullAndNotEmpty(triples)) {
			final Integer jobId = exchange.getIn().getHeader(Constants.JOB_ID_ATTRIBUTE_NAME, Integer.class);
			final JobInstance configuration = cache.getJobInstance(jobId);
			if (configuration == null) {
				logger.error(MessageCatalog._00038_UNKNOWN_JOB_ID, jobId);
				throw new IllegalArgumentException(String.valueOf(jobId));
			}

			try {
				rdfStore.executeInsert(
						configuration.getSparqlEndpointUrl(), 
						graphName, 
						configuration.getSparqlUsername(), 
						configuration.getSparqlPassword(), 
						triples);
			} catch (final Exception exception) {
				logger.error(MessageCatalog._00050_RDFSTORE_FAILURE, exception);
			}
		}
	}
}