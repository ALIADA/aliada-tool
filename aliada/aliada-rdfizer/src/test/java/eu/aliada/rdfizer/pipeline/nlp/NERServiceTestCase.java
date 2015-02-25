package eu.aliada.rdfizer.pipeline.nlp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * NLP Service test case.
 * 
 * @author Andrea Gazzarini
 * @since 2.0
 */
public class NERServiceTestCase {

	NERService service;

	/**
	 * Setup fixture for this test case.
	 * 
	 */
	@Before
	public void setUp() {
		service = new NERService();
	}

	/**
	 * If a null text is given then the NLP service won't detect any entity.
	 * 
	 * @throws Exception hopefully never, otherwise the test fails.
	 */
	@Test
	public void nullText() throws Exception {
		final EntityDetectionListener listener = mock(EntityDetectionListener.class);

		service.detectEntities(null, listener);

		verifyZeroInteractions(listener);
	}

	/**
	 * If a null text is given then the NLP service won't detect any entity.
	 * 
	 * @throws Exception hopefully never, otherwise the test fails.
	 */
	@Test
	public void emptyText() throws Exception {
		final String[] emptyTexts = { "", "    ", "\t\t\t\n" };

		final EntityDetectionListener listener = mock(EntityDetectionListener.class);
		for (final String text : emptyTexts) {
			service.detectEntities(text, listener);
		}

		verifyZeroInteractions(listener);
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
		check.add("PERSON: Blake");
		check.add("PERSON: Gary Farmer");
		check.add("PERSON: Blake");
		
		final EntityDetectionListener listener = new EntityDetectionListener() {
			@Override
			public void onEntityDetected(final String tag, final String entity) {
				check.remove(tag + ": " + entity);
			}
		};
		service.detectEntities(text, listener);
		
		assertEquals(0, check.size());
		
	}
}