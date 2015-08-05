// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.selector.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import eu.aliada.rdfizer.pipeline.format.marc.selector.Expression;
import eu.aliada.rdfizer.pipeline.format.xml.OXPath;

/**
 * A selector expression interpreter for MARC control fields.
 * It accepts two kind of specs:
 * 
 * <ul>
 * 	<li>008: selects all control fieldSpecs content;</li>
 * 	<li>008[38-41]: selects a specific section of control fieldSpecs content;</li>
 * </ul>
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@Component
@Scope("prototype")
public class ControlFieldExpression implements Expression<String, Document> {
	static final String MISSING_SQ_BRACKET_ERR_MESSAGE = "Invalid specs (%s): a square bracket is missing.";
	static final String MISSING_MINUS = "Invalid specs (%s): index separator '-' is missing.";
	static final String WRONG_MINUS_POSITION = "Invalid specs (%s): index separator '-' must be between square brackets.";
	static final String START_INDEX_NAN = "Invalid specs (%s): unable to get a valid start index.";
	static final String END_INDEX_NAN = "Invalid specs (%s): unable to get a valid end index.";
	
	@Autowired
	OXPath xpath;
	
	private final Expression<String, Document> fullSelector = new Expression<String, Document>() {
		@Override
		public String evaluate(final Document target) {
			try {
				return xpath.value(expression, target);
			} catch (final Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		@Override
		public String specs() {
			throw new UnsupportedOperationException();
		}	
	};

	private final Expression<String, Document> partialSelector = new Expression<String, Document>() {
		@Override
		public String evaluate(final Document target) {
			try {
				final String data = xpath.value(expression, target);
				if (data == null) {
					return null;
				}
				
				if (data == null || data.length() <= endIndex) {
					// TODO: log + JMX
					return null;	
				}
				
				return data.substring(startIndex, endIndex);
			} catch (final Exception exception) {
				throw new RuntimeException(exception);
			}
		}
		
		@Override
		public String specs() {
			throw new UnsupportedOperationException();
		}	
	};

	private final String specs;
	private final String expression;
	private int startIndex;
	private int endIndex;
	private final Expression<String, Document> currentState;
	
	/**
	 * Builds a new control field expression with the given specs.
	 * 
	 * @param specs the expression specs.
	 */
	public ControlFieldExpression(final String specs) {
		if (specs == null || specs.trim().length() == 0) {
			throw new IllegalArgumentException(NULL_SPECS_ERR_MESSAGE);
		}
		
		final int indexOfOpeningSquareBracket = specs.indexOf("[");
		final int indexOfClosingSquareBracket = specs.indexOf("]", indexOfOpeningSquareBracket);
		
		if ((indexOfOpeningSquareBracket != -1 && indexOfClosingSquareBracket == -1)
			||  
			(indexOfOpeningSquareBracket == -1 && indexOfClosingSquareBracket != -1)) {
			throw new IllegalArgumentException(String.format(MISSING_SQ_BRACKET_ERR_MESSAGE, specs));			
		}
		
		if (indexOfOpeningSquareBracket != -1 && indexOfClosingSquareBracket != -1) {
			final int indexOfMinus = specs.indexOf("-", indexOfOpeningSquareBracket);
			if (indexOfMinus == -1) {
				throw new IllegalArgumentException(String.format(MISSING_MINUS, specs));			
			}
			
			if (indexOfMinus > indexOfClosingSquareBracket) {
				throw new IllegalArgumentException(String.format(WRONG_MINUS_POSITION, specs));			
			}
			
			try {
				startIndex = Integer.parseInt(specs.substring(indexOfOpeningSquareBracket + 1, indexOfMinus).trim());
			} catch (Exception exception) {
				throw new IllegalArgumentException(String.format(START_INDEX_NAN, specs));			
			}
			
			try {
				endIndex = Integer.parseInt(specs.substring(indexOfMinus + 1, indexOfClosingSquareBracket).trim()) + 1;
			} catch (Exception exception) {
				throw new IllegalArgumentException(String.format(END_INDEX_NAN, specs));			
			}			
			
			expression = new StringBuilder()
				.append("controlfield[@tag='")
				.append(specs.substring(0, indexOfOpeningSquareBracket).trim())
				.append("']")
				.toString();
			currentState = partialSelector;
		} else {
			expression = new StringBuilder()
				.append("controlfield[@tag='")
				.append(specs.trim())
				.append("']")
				.toString();
			currentState = fullSelector;
		}
		this.specs = specs;
	}
	
	@Override
	public String evaluate(final Document target) {
		final String result = currentState.evaluate(target);
		return result != null && result.trim().length() > 0 ? result : null;
	}
	
	@Override
	public String specs() {
		return specs;
	}
	
	@Override
	public String toString() {
		return specs();
	}
}