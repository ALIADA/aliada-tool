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
public class GetOntologyTypeURITest {
	/** For Logging. */
	private static final Log LOGGER = new Log(GetOntologyTypeURITest.class);

    /**
     * @see
     * @since 1.0
     */
    @Test
    public void testGetOntologyTypeURITest() {
    	String sparqlEndpointURI = "http://aliada.scanbit.net:8890/sparql";
    	String user = "aliada_dev";
    	String password = "aliada_dev";
//    	String typeLabel = "1800 utáni gyűjtemény";
    	String typeLabel = "paintings";
    	RDFStoreDAO rdfStoreDAO = new RDFStoreDAO();
		String[] result = rdfStoreDAO.getOntologyTypeURI(sparqlEndpointURI, user, password, typeLabel);
        if (result.length != 0) {
            LOGGER.info("OK");
        } else {
            LOGGER.info("NOK");
        }
    }

}
