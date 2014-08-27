// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.processors;

import static eu.aliada.shared.Strings.isNotNullAndNotEmpty;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import eu.aliada.rdfizer.Constants;
import eu.aliada.shared.rdfstore.RDFStoreDAO;

/**
 * RDF store outbound connector.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class RESTSparqlInsertProcessor implements Processor {
	final String sparqlEndpointURI;
	final String user; 
	final String password;
	final RDFStoreDAO rdfStore;
	
	/**
	 * Builds a new connector using the given targets.
	 * 
	 * @param sparqlEndpointURI the REST SPARQL endpoint of the the target RDF store.
	 * @param user if a valid user is required for inserting triples.
	 * @param password if a valid user is required for inserting triples.
	 * @param batchSize how many statements will be grouped in a single request.
	 * @param cleanUpPeriod the sleep interval of the buffer cleanup worker.
	 */
	public RESTSparqlInsertProcessor(
			final String sparqlEndpointURI, 
			final String user, 
			final String password) {

		this.sparqlEndpointURI = sparqlEndpointURI;
		this.user = user;
		this.password = password;
		this.rdfStore = new RDFStoreDAO();
	}

	@Override
	public void process(final Exchange exchange) throws Exception {
		
		final String triples = exchange.getIn().getBody(String.class);
		final String graphName = exchange.getIn().getHeader(Constants.GRAPH_ATTRIBUTE_NAME, String.class);
		if (isNotNullAndNotEmpty(triples)) {
			rdfStore.executeInsert(sparqlEndpointURI, graphName, user, password, triples);
		}
	}
}