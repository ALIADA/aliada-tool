package eu.aliada.rdfizer.pipeline.format.marc;

import static eu.aliada.rdfizer.TestUtils.randomString;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.junit.Before;
import org.junit.Test;
import org.marc4j.MarcException;
import org.marc4j.marc.Record;

/**
 * Test case for {@link MarcXmlRecordToMarcRecordTranslator}.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class MarcXmlRecordToMarcRecordTranslatorTest {
	private MarcXmlRecordToMarcRecordTranslator cut;
	private Message message;
	private Exchange exchange;
	
	@Before
	public void setUp() {
		cut = new MarcXmlRecordToMarcRecordTranslator();
		message = mock(Message.class);
		exchange = mock(Exchange.class);
		when (exchange.getIn()).thenReturn(message);
	}
	
	/**
	 * In case the incoming message contains an invalid stream, an exception must be thrown.
	 */
	@Test
	public void invalidStream() {
		final String invalidMarcXMLStream = randomString();
		when (message.getBody(String.class)).thenReturn(invalidMarcXMLStream);
		
		try {
			cut.process(exchange);
			fail();
		} catch (final Exception expected) {
			assertTrue(expected instanceof MarcException);
		}
	}
	
	/**
	 * In case the incoming stream is null, an exception must be thrown.
	 */
	@Test
	public void nullStream() {
		when (message.getBody(String.class)).thenReturn(null);
		
		try {
			cut.process(exchange);
			fail();
		} catch (final Exception expected) {
			assertTrue(expected instanceof NullPointerException);
		}
	}	
	
	/**
	 * In case the incoming stream is null, an exception must be thrown.
	 */
	@Test
	public void emptyStream() {
		final String emptyStream = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\" ?> " +
				"<marc:collection xmlns:marc=\"http://www.loc.gov/MARC21/slim\" " + 
				"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
				"xsi:schemaLocation=\"http://www.loc.gov/MARC21/slim http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd\"> " + 
				"</marc:collection>";
		when (message.getBody(String.class)).thenReturn(emptyStream);
		
		try {
			cut.process(exchange);
			fail();
		} catch (final Exception expected) {
			assertTrue(expected instanceof IllegalArgumentException);
		}
	}		
	
	/**
	 * Positive test.
	 * 
	 * @throws Exception never otherwise the test fails.
	 */
	@Test
	public void positive() throws Exception {
		final String stream = new String(Files.readAllBytes(Paths.get(new File("./src/test/resources/marcxml-artium.xml").toURI())));
		when (message.getBody(String.class)).thenReturn(stream);
		cut.process(exchange);
		verify(message).setBody(any(Record.class));
	}		
}