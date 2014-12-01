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
    	String sparqlEndpointURI = "http://aliada.scanbit.net:8890/sparql-auth";
    	String graphUri = "http://aliada_graph1";
    	String user = "aliada_dev";
    	String password = "aliada_dev";
    	String triplesFilename = "src/test/resources/links.n3";
    	RDFStoreDAO rdfStoreDAO = new RDFStoreDAO();
		boolean result = rdfStoreDAO.loadDataIntoGraphBySparql(triplesFilename, sparqlEndpointURI, user, password, graphUri);
        if (result) {
            LOGGER.info("OK");
        } else {
            LOGGER.info("NOK");
        }
    }

}
