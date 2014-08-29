package eu.aliada.rdfizer.pipeline.processors;

import static eu.aliada.rdfizer.TestUtils.randomString;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.junit.Before;
import org.junit.Test;

import eu.aliada.rdfizer.Constants;
import eu.aliada.shared.rdfstore.RDFStoreDAO;

/**
 * Test case for {@link RESTSparqlInsertProcessor}.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class RESTSparqlInsertProcessorTest {
	private RESTSparqlInsertProcessor cut;
	private Message message;
	private Exchange exchange;
	private RDFStoreDAO dao;
	
	@Before
	public void setUp() {
		cut = new RESTSparqlInsertProcessor(randomString(), randomString(), randomString());
		dao = mock(RDFStoreDAO.class);
		message = mock(Message.class);
		exchange = mock(Exchange.class);
		when (exchange.getIn()).thenReturn(message);
	}
	
	/**
	 * If incoming triples string is empty the processor does nothing.
	 * 
	 * @throws Exception never otherwise the test fails.
	 */
	@Test
	public void emptyTriples() throws Exception {
		final String [] emptyStrings = {"", "  ", "\t"};
		
		for (final String emptyString : emptyStrings) {
			when (message.getBody(String.class)).thenReturn(emptyString);
			cut.process(exchange);
			verifyZeroInteractions(dao);
		}	
	}
	
	/**
	 * If incoming triples string is null the processor does nothing.
	 * 
	 * @throws Exception never otherwise the test fails.
	 */
	@Test
	public void nullTriples() throws Exception {
		when (message.getBody(String.class)).thenReturn(null);
		cut.process(exchange);
		verifyZeroInteractions(dao);
	}	
}