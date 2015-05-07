package eu.aliada.rdfizer.pipeline.nlp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import eu.aliada.rdfizer.datasource.Cache;

/**
 * NLP Service test case.
 * 
 * @author Andrea Gazzarini
 * @since 2.0
 */
public class NERServiceTestCase {

	NERSingletonService service;

	/**
	 * Setup fixture for this test case.
	 * 
	 */
	@Before
	public void setUp() {
		service = new NERSingletonService();
		service.cache = new Cache();
		service.classifierFilePath = "../src/site/nlp/english.all.7class.distsim.crf.ser.gz";
	}

	/**
	 * If a null text is given then the NLP service won't detect any entity.
	 * 
	 * @throws Exception hopefully never, otherwise the test fails.
	 */
	@Test
	public void nullText() throws Exception {
		assertTrue(service.detectEntities(null).isEmpty());
	}

	/**
	 * If a null text is given then the NLP service won't detect any entity.
	 * 
	 * @throws Exception hopefully never, otherwise the test fails.
	 */
	@Test
	public void emptyText() throws Exception {
		final String[] emptyTexts = { "", "    ", "\t\t\t\n" };

		for (final String text : emptyTexts) {
			assertTrue(service.detectEntities(text).isEmpty());
		}
	}

	/**
	 * Positive test with a sample text.
	 * 
	 * @throws Exception hopefully never, otherwise the test fails.
	 */
	@Test
	public void success() throws Exception {
		String text = "In bringing his distinct vision to the Western genre, writer-director Jim Jarmusch has created a quasi-mystical avant-garde drama that remains a deeply spiritual viewing experience. After losing his parents and fiancée, a Cleveland accountant named William Blake (a remarkable Johnny Depp) spends all his money and takes a train to the frontier town of Machine in order to work at a factory. Upon arriving in Machine, he is denied his expected job and finds himself a fugitive after murdering a man in self-defense. Wounded and helpless, Blake is befriended by Nobody (Gary Farmer), a wandering Native American who considers him to be a ghostly manifestation of the famous poet. Nobody aids Blake in his flight from three bumbling bounty hunters, preparing him for his final journey--a return to the world of the spirits.";
		
		final List<String> check = new ArrayList<String>();
		check.add("PERSON: Jim Jarmusch");
		check.add("LOCATION: Cleveland");
		check.add("PERSON: William Blake");
		check.add("PERSON: Johnny Depp");
		check.add("LOCATION: Machine");
		check.add("PERSON: Gary Farmer");
		check.add("PERSON: Blake");
				
		Map<String, String> result = service.detectEntities(text);
		
		for(Entry<String, String> entry : result.entrySet()) {
			check.remove(entry.getValue()+": " + entry.getKey());
		}
		
		assertEquals(check.toString(), 0, check.size());
		
	}
}