// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.selector.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import eu.aliada.rdfizer.TestData;
import eu.aliada.rdfizer.pipeline.format.xml.OXPath;

/**
 * Test case for {@link VariableFieldExpression}.
 * 
 * @author Emiliano Cammilletti
 * @since 1.0
 */
public class VariableFieldExpressionTest implements TestData {
	
	/**
	 * In case specs are null then an exception must be thrown.
	 */
	@Test
	public void nullSpecs() {
		try {
			new VariableFieldExpression(null);
			fail();
		} catch (final IllegalArgumentException expected) {
			assertEquals(
				ControlFieldExpression.NULL_SPECS_ERR_MESSAGE, 
				expected.getMessage());
		}
	}
	
	/**
	 * In case a parenthesis is missing an exception must be thrown.
	 */
	@Test
	public void missingParenthesis() {
		final String [] invalidSpecs = {"856(aslks", "856(", "856)", "856sdhsjkdh)"};
		for (final String invalid : invalidSpecs) {
			try {
				new VariableFieldExpression(invalid);
				fail();
			} catch (final IllegalArgumentException expected) {
				assertEquals(
					String.format(VariableFieldExpression.MISSING_PARENTHESIS_ERR_MESSAGE, invalid), 
					expected.getMessage());
			}
		}
	}
	
	/**
	 * Indicators pattern, when declared, must consist in two characters.
	 */
	@Test
	public void badIndicatorsPattern() {
		final String [] invalidSpecs = {"856(1)", "856(128)", "856()"};
		for (final String invalid : invalidSpecs) {
			try {
				new VariableFieldExpression(invalid);
				fail();
			} catch (final IllegalArgumentException expected) {
				assertEquals(
					String.format(VariableFieldExpression.BAD_INDICATORS_PATTERN_ERR_MESSAGE, invalid), 
					expected.getMessage());
			}
		}
	}		
	
	/**
	 * If record doesn't have the requested variable field, then the evaluation must return null.
	 */
	@Test
	public void noTargetField() {
		final Document document = newRecord(A_VARIABLE_FIELD_NAME, '#', '#', 'a');
		final VariableFieldExpression expression = new VariableFieldExpression(A_VARIABLE_FIELD_NAME + "a");
		expression.xpath = new OXPath();
		assertNull(expression.evaluate(document));
	}	

	/**
	 * If record has the the target field but doesn't have that subfield, then the evaluation must return null.
	 */
	@Test
	public void noTargetSubField() {
		final Document document = newRecord(A_VARIABLE_FIELD_NAME, '#', '#', 'a');
		final VariableFieldExpression expression = new VariableFieldExpression(A_VARIABLE_FIELD_NAME);
		expression.xpath = new OXPath();
		assertNull(expression.evaluate(document));
	}	
	
	/**
	 * Positive test without indicators pattern and one value for the requested subfield.
	 */
	@Test
	public void selectorWithoutIndicatorsPattern() {
		final String expectedValue = "This is the expected value";
		final Document document = newRecord(A_VARIABLE_FIELD_NAME, '#', '#', 'a', expectedValue);
		final VariableFieldExpression expression = new VariableFieldExpression(A_VARIABLE_FIELD_NAME + "a");
		expression.xpath = new OXPath();
		assertEquals(expectedValue, expression.evaluate(document));
	}	
	
	/**
	 * Positive test without indicators pattern and multiple values for the requested subfield.
	 * In this case only the first value will be selected.
	 */
	@Test
	public void selectorWithoutIndicatorsPatternAndMultipleMatchingValues() {
		final String expectedValue = "This is the expected value";
		final Document document = newRecord(A_VARIABLE_FIELD_NAME, '#', '#', 'a', 
				expectedValue, 
				"another value", 
				"still another value");
		final VariableFieldExpression expression = new VariableFieldExpression(A_VARIABLE_FIELD_NAME + "a");
		expression.xpath = new OXPath();
		assertEquals(expectedValue, expression.evaluate(document));
	}		

	/**
	 * Positive test with indicators pattern (only literals).
	 */
	@Test
	public void indicatorsPatternWithLiterals() {
		final String expectedValue = "This is the expected value";
		final Document document = newRecord(A_VARIABLE_FIELD_NAME, '1', '2', 'a', expectedValue);
		final VariableFieldExpression expression = new VariableFieldExpression(A_VARIABLE_FIELD_NAME + "(12)a");
		expression.xpath = new OXPath();
		assertEquals(expectedValue, expression.evaluate(document));
	}
	
	/**
	 * Positive test with globs in indicators pattern.
	 */
	@Test
	public void indicatorsPatternWithGlob() {
		final String expectedValue = "This is the expected value";
		final Document document = newRecord(A_VARIABLE_FIELD_NAME, '1', '2', 'a', expectedValue);
		
		final String [] matchingPatterns = {"??", "1?", "?2"};
		for (final String matchingPattern : matchingPatterns) {
			final VariableFieldExpression expression = new VariableFieldExpression(A_VARIABLE_FIELD_NAME + "(" + matchingPattern + ")a");
			expression.xpath = new OXPath();
			assertEquals(expectedValue, expression.evaluate(document));
		}
	}
	
	/**
	 * If the indicators pattern doesn't find a match then the evaluation returns null.
	 */
	@Test
	public void indicatorsPatternDoesntMatch() {
		final Document document = newRecord(A_VARIABLE_FIELD_NAME, '1', '2', 'a', "A value");
		
		final String [] unmatchingPatterns = {"?3", "#?", "##", "44", "21"};
		for (final String unmatchingPattern : unmatchingPatterns) {
			final VariableFieldExpression expression = new VariableFieldExpression(A_VARIABLE_FIELD_NAME + "(" + unmatchingPattern + ")a");
			expression.xpath = new OXPath();
			assertNull(expression.evaluate(document));
		}
	}
	
//	/**
//	 * In case of partial selector, a minus must be positioned between the square brackets.
//	 */
//	@Test
//	public void minusOutsideBrackets() {
//		final String [] invalidSpecs = {"856[1233]-"};
//		for (final String invalid : invalidSpecs) {
//			try {
//				new ControlFieldExpression(invalid);
//				fail();
//			} catch (final IllegalArgumentException expected) {
//				assertEquals(
//					String.format(ControlFieldExpression.WRONG_MINUS_POSITION, invalid), 
//					expected.getMessage());
//			}
//		}
//	}		
//	
//	/**
//	 * Start index on specs is Not a Number (NaN).
//	 */
//	@Test
//	public void startIndexNaN() {
//		final String [] invalidSpecs = {"856[ab-112]"};
//		for (final String invalid : invalidSpecs) {
//			try {
//				new ControlFieldExpression(invalid);
//				fail();
//			} catch (final IllegalArgumentException expected) {
//				assertEquals(
//					String.format(ControlFieldExpression.START_INDEX_NAN, invalid), 
//					expected.getMessage());
//			}
//		}
//	}		
//	
//	/**
//	 * End index on specs is Not a Number (NaN).
//	 */
//	@Test
//	public void endIndexNaN() {
//		final String [] invalidSpecs = {"856[12-aa]"};
//		for (final String invalid : invalidSpecs) {
//			try {
//				new ControlFieldExpression(invalid);
//				fail();
//			} catch (final IllegalArgumentException expected) {
//				assertEquals(
//					String.format(ControlFieldExpression.END_INDEX_NAN, invalid), 
//					expected.getMessage());
//			}
//		}
//	}		
//	
//	/**
//	 * Positive test for full control field selector.
//	 */
//	@Test
//	public void fullSelector() {
//		final String controlFieldName = "008";
//		final String controlFieldValue = "Probably this is not a valid value for 008 field ;)";
//		
//		final Record record = newRecord(controlFieldName, controlFieldValue);
//		
//		final ControlFieldExpression expression = new ControlFieldExpression("008");
//		assertEquals(controlFieldValue, expression.evaluate(record));
//	}
//	
//	/**
//	 * Positive test for partial control field selector.
//	 */
//	@Test
//	public void partialSelector() {
//		final String controlFieldName = "008";
//		final String controlFieldValue = "Probably this is not a valid value for 008 field ;)";
//		
//		int startIndex = 0; 
//		int endIndex = 5;
//		final String expectedValue = controlFieldValue.substring(startIndex, endIndex + 1);
//		
//		final Record record = newRecord(controlFieldName, controlFieldValue);
//		
//		final String [] expressions = {"008[00-05]", "008 [ 00 - 05 ]", "008 [ 00   -   05 ]"};
//		for (final String specs : expressions) {			
//			final ControlFieldExpression expression = new ControlFieldExpression(specs);
//			assertEquals(expectedValue, expression.evaluate(record));
//		}
//	}	
//	

	/**
	 * Creates a new record with a given data field (with just one subfield).
	 * If the subfield values array is null or empty then the data field won't be created at all.
	 * 
	 * @param dataFieldName the name of the field.
	 * @param firstIndicatorValue the value for the first indicator.
	 * @param secondIndicatorValue the value for the second indicator.
	 * @param subfieldCode the subfield code.
	 * @param subFieldValues one or more values for the subfield
	 * @return a new record with a given data field (with just one subfield).
	 */
	private Document newRecord(final String dataFieldName,
			final char firstIndicatorValue, final char secondIndicatorValue,
			final char subfieldCode, final String... subFieldValues) {
		Document document = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
			Element root = (Element) document.createElement("marc:record");
			document.appendChild(root);

			Element element = document.createElement("marc:datafield");
			if (subFieldValues != null && subFieldValues.length > 0) {
				element.setAttribute("tag", dataFieldName);
				element.setAttribute("ind1",
						Character.toString(firstIndicatorValue));
				element.setAttribute("ind2",
						Character.toString(secondIndicatorValue));

				for (final String value : subFieldValues) {
					Element tag_subfield_element = document
							.createElement("marc:subfield");
					tag_subfield_element.setAttribute("code",
							Character.toString(subfieldCode));
					tag_subfield_element.setTextContent(value);
					element.appendChild(tag_subfield_element);
				}

			}
			root.appendChild(element);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return document;
	}
	
}