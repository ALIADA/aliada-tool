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
import eu.aliada.rdfizer.pipeline.format.xml.XPath;

/**
 * Test case for {@link ControlFieldExpression}.
 * 
 * @author Emiliano Cammilletti
 * @since 1.0
 */
public class ControlFieldExpressionTest implements TestData{
	/**
	 * In case specs are null then an exception must be thrown.
	 */
	@Test
	public void nullSpecs() {
		try {
			new ControlFieldExpression(null);
			fail();
		} catch (final IllegalArgumentException expected) {
			assertEquals(
				ControlFieldExpression.NULL_SPECS_ERR_MESSAGE, 
				expected.getMessage());
		}
	}
	
	/**
	 * In case a square bracket is missing an exception must be thrown.
	 */
	@Test
	public void missingSquareBracket() {
		final String [] invalidSpecs = {"856[aslks", "856[", "856]", "856sdhsjkdh]"};
		for (final String invalid : invalidSpecs) {
			try {
				new ControlFieldExpression(invalid);
				fail();
			} catch (final IllegalArgumentException expected) {
				assertEquals(
					String.format(ControlFieldExpression.MISSING_SQ_BRACKET_ERR_MESSAGE, invalid), 
					expected.getMessage());
			}
		}
	}
	
	/**
	 * In case of partial selector, a minus must act as separator between the start and the end index.
	 */
	@Test
	public void missingMinus() {
		final String [] invalidSpecs = {"856[1233]", "856[12 33]"};
		for (final String invalid : invalidSpecs) {
			try {
				new ControlFieldExpression(invalid);
				fail();
			} catch (final IllegalArgumentException expected) {
				assertEquals(
					String.format(ControlFieldExpression.MISSING_MINUS, invalid), 
					expected.getMessage());
			}
		}
	}		
	
	/**
	 * In case of partial selector, a minus must be positioned between the square brackets.
	 */
	@Test
	public void minusOutsideBrackets() {
		final String [] invalidSpecs = {"856[1233]-"};
		for (final String invalid : invalidSpecs) {
			try {
				new ControlFieldExpression(invalid);
				fail();
			} catch (final IllegalArgumentException expected) {
				assertEquals(
					String.format(ControlFieldExpression.WRONG_MINUS_POSITION, invalid), 
					expected.getMessage());
			}
		}
	}		
	
	/**
	 * Start index on specs is Not a Number (NaN).
	 */
	@Test
	public void startIndexNaN() {
		final String [] invalidSpecs = {"856[ab-112]"};
		for (final String invalid : invalidSpecs) {
			try {
				new ControlFieldExpression(invalid);
				fail();
			} catch (final IllegalArgumentException expected) {
				assertEquals(
					String.format(ControlFieldExpression.START_INDEX_NAN, invalid), 
					expected.getMessage());
			}
		}
	}		
	
	/**
	 * End index on specs is Not a Number (NaN).
	 */
	@Test
	public void endIndexNaN() {
		final String [] invalidSpecs = {"856[12-aa]"};
		for (final String invalid : invalidSpecs) {
			try {
				new ControlFieldExpression(invalid);
				fail();
			} catch (final IllegalArgumentException expected) {
				assertEquals(
					String.format(ControlFieldExpression.END_INDEX_NAN, invalid), 
					expected.getMessage());
			}
		}
	}		
	
	/**
	 * Incompatible control field length with partial control field selector.
	 */
	@Test
	public void incompatibleControlFieldLength() {
		int endIndex = A_CONTROL_FIELD_VALUE.length() + 10;
		final Document document = newRecord(A_CONTROL_FIELD_NAME, A_CONTROL_FIELD_VALUE);
		
		final String [] expressions = {
				A_CONTROL_FIELD_NAME + "[00-" + endIndex + "]", 
				A_CONTROL_FIELD_NAME + " [ 00 - " + endIndex + " ]", 
				A_CONTROL_FIELD_NAME + " [ 00   -   " + endIndex + " ]"};
		for (final String specs : expressions) {			
			final ControlFieldExpression expression = new ControlFieldExpression(specs);
			expression.xpath = new XPath();
			assertNull(expression.evaluate(document));
		}
	}		
	
	/**
	 * If record doesn't have the requested control field, then the evaluation must return null.
	 */
	@Test
	public void fullSelectorWithNoTargetField() {
		final Document document = newRecord(null, null);
		final ControlFieldExpression expression = new ControlFieldExpression(A_CONTROL_FIELD_NAME);
		expression.xpath = new XPath();
		assertNull(expression.evaluate(document));
	}
	
	/**
	 * If record doesn't have the requested control field, then the evaluation must return null.
	 */
	@Test
	public void partialSelectorWithNoTargetField() {
		final Document document = newRecord(null, null);
		final ControlFieldExpression expression = new ControlFieldExpression(A_CONTROL_FIELD_NAME + "[00-05]");
		expression.xpath = new XPath();
		assertNull(expression.evaluate(document));
	}	
	
	/**
	 * Positive test for full control field selector.
	 */
	@Test
	public void fullSelector() {
		final Document document = newRecord(A_CONTROL_FIELD_NAME, A_CONTROL_FIELD_VALUE);
		final ControlFieldExpression expression = new ControlFieldExpression(A_CONTROL_FIELD_NAME);
		expression.xpath = new XPath();
		assertEquals(A_CONTROL_FIELD_VALUE, expression.evaluate(document));
	}
	
	/**
	 * Positive test for partial control field selector.
	 */
	@Test 
	public void partialSelector() {
		int startIndex = 0; 
		int endIndex = 5;
		final String expectedValue = A_CONTROL_FIELD_VALUE.substring(startIndex, endIndex + 1);
		
		final Document document = newRecord(A_CONTROL_FIELD_NAME, A_CONTROL_FIELD_VALUE);
		
		final String [] expressions = {
				A_CONTROL_FIELD_NAME + "[00-" + endIndex + "]", 
				A_CONTROL_FIELD_NAME + " [ 00 - " + endIndex + " ]", 
				A_CONTROL_FIELD_NAME + " [ 00   -   " + endIndex + " ]"};
		
		for (final String specs : expressions) {			
			final ControlFieldExpression expression = new ControlFieldExpression(specs);
			expression.xpath = new XPath();
			assertEquals(expectedValue, expression.evaluate(document));
		}
	}	
	
//	/**
//	 * Creates a new record with the given control field.
//	 * If the control field is null then it won't be created.
//	 * 
//	 * @param controlFieldName the control field name.
//	 * @param controlFieldValue the control field value.
//	 * @return a new record with the given control field.
//	 */
//	private Record newRecord(final String controlFieldName, final String controlFieldValue) {
//		final MarcFactory factory = MarcFactory.newInstance();
//		final Record record = factory.newRecord();
//		
//		if (controlFieldName != null) {
//			record.addVariableField(factory.newControlField(controlFieldName, controlFieldValue));
//		}
//		return record;		
//	}
	
	/**
	 * Creates a new record with the given control field.
	 * If the control field is null then it won't be created.
	 * 
	 * @param controlFieldName the control field name.
	 * @param controlFieldValue the control field value.
	 * @return a new record with the given control field.
	 */
	private Document newRecord(final String controlFieldName, final String controlFieldValue) {
		Document document = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
			Element root = (Element) document.createElement("marc:record");
			document.appendChild(root);

			Element element = document.createElement("marc:controlfield");
			if (controlFieldName != null) {
				element.setAttribute("tag", controlFieldName);
				element.setTextContent(controlFieldValue);
			}
			root.appendChild(element);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return document;
	}
	
}