package eu.aliada.shared;

import eu.aliada.shared.rdfstore.RDFStoreDAO;
import eu.aliada.shared.log.Log;

import org.junit.Test;

/**
 * Test {@link RDFStoreDAO} class functions
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class LoadDataIntoGraphBySparqlTest {
	/** For Logging. */
	private static final Log LOGGER = new Log(LoadDataIntoGraphBySparqlTest.class);

    /**
     * @see
     * @since 1.0
     */
    @Test
    public void testLoadDataIntoGraphBySparqlTest() {
    	final String sparqlEndpointURI = "http://aliada.scanbit.net:8891/sparql-auth";
    	final String graphUri = "http://aliada_graph1";
    	final String user = "aliada_dev";
    	final String password = "aliada_dev";
    	final String triplesFilename = "src/test/resources/links.n3";
    	final RDFStoreDAO rdfStoreDAO = new RDFStoreDAO();
		final boolean result = rdfStoreDAO.loadDataIntoGraphBySparql(triplesFilename, sparqlEndpointURI, user, password, graphUri);
        if (result) {
            LOGGER.info("OK");
        } else {
            LOGGER.info("NOK");
        }
    }

}
