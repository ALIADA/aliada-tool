package eu.aliada.rdfizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
	public void splitDates_bothDates() {
		final String [] test = {"1988-1999","1988 - 1999", "1988- 1999", "1988 1999"};
		final String expectedBirth = "1988";
		final String expectedDeath = "1999";
		
		for (final String dateExpression : test) {
			assertEquals(dateExpression, expectedBirth, cut.splitDateAndPartialGet(dateExpression, true));			
			assertEquals(dateExpression, expectedDeath, cut.splitDateAndPartialGet(dateExpression, false));			
		}
	}
	
	@Test
	public void splitDates_onlyBirthDates() {
		final String [] test = {"1988- ","1988 - ", "1988", "1988 ", "d. 1988 "};
		final String expectedBirth = "1988";
		
		for (final String dateExpression : test) {
			assertEquals(dateExpression, expectedBirth, cut.splitDateAndPartialGet(dateExpression, true));			
			assertNull(dateExpression, cut.splitDateAndPartialGet(dateExpression, false));			
		}
	}
	
	@Test
	public void splitDates_onlyDeathDates() {
		final String [] test = {"-1999","- 1999"};
		final String expectedDeath= "1999";
		
		for (final String dateExpression : test) {
			assertEquals(dateExpression, expectedDeath, cut.splitDateAndPartialGet(dateExpression, false));			
			assertNull(dateExpression, cut.splitDateAndPartialGet(dateExpression, true));			
		}
	}
	
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