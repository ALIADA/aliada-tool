package eu.aliada.rdfizer.pipeline.format.xml;

import static eu.aliada.rdfizer.TestUtils.randomString;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import eu.aliada.rdfizer.datasource.rdbms.JobInstance;

/**
 * Test case for {@link SynchXmlDocumentTranslator}.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class SynchXmlDocumentTranslatorTest {
	
	private SynchXmlDocumentTranslator cut;
	
	@Before
	public void setUp() {
		cut = new SynchXmlDocumentTranslator();
	}
	
	/**
	 * In case the graph name is null then the namespace will be used as graph name.
	 */
	@Test
	public void graphNameNull() {
		final String [] invalidGraphNames = {null, " ", "","\t"};
		final String namespace = randomString();
		
		final JobInstance instance = new JobInstance();
		instance.setNamespace(namespace);
		for (final String invalidGraphName : invalidGraphNames) {
			instance.setGraphName(invalidGraphName);
			assertEquals(namespace, cut.graphName(instance));
		}
	}
	
	/**
	 * In case the graph name is not null then it will be used as graph name.
	 */
	@Test
	public void graphNameNotNull() {
		final String graphName = randomString() + "GN";
		final String namespace = randomString() + "NS";
		
		final JobInstance instance = new JobInstance();
		instance.setNamespace(namespace);
		instance.setNamespace(graphName);

		assertEquals(graphName, cut.graphName(instance));
	}	
}