package eu.aliada.rdfizer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test case for {@link Function} utility.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class FunctionTest {

	private Function cut = new Function();

	@Test
	public void normalizeWithCopyrightSymbol() {
		assertEquals("botticellisandro", cut.normalize(" ©  Botticelli©Sandro   "));
	}
	
	@Test
	public void normalizeWithSpacesAndDelimiters() {
		assertEquals("botticellisandro", cut.normalize("   Botticelli, Sandro   "));
	}
	
	@Test
	public void normalizeWithDiacritics() {
		assertEquals("botticellisandro", cut.normalize("Botticelli, Sandro"));
		assertEquals("galleriadegliuffizipinacotecaflorence", cut.normalize("Galleria dégli Uffizi Pìnacotècà (Flòrence)"));
	}
	
	@Test
	public void normalizeWithDiamondUtdChars() {
		assertEquals("botticellisandro", cut.normalize("Botticelli, Sandro"));
		assertEquals("galleriadegliuffizipinacotecaflorence", cut.normalize("Galleria degli Uffizi � Pinacoteca (Florence)"));
	}	
}