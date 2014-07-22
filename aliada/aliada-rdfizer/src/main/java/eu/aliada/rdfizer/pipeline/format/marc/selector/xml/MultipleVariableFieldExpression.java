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
import eu.aliada.rdfizer.pipeline.format.xml.ImmutableNodeList;
import eu.aliada.rdfizer.pipeline.format.xml.XPath;

/**
 * A selector expression interpreter for MARCXML for mutiple variable fields.
 * It accepts these kind of specs:
 * 
 * <ul>
 * 	<li>245()t: selects the not null subfields 't' of field 245 without indicators specified;</li>
 * 	<li>245[(<indicators pattern>)]t: selects the not null subfields 't' of field 245 that has a matching indicator pattern;</li>
 * </ul>
 * 
 * The indicator patterns is an optional 2 chars pattern between parenthesis where each position can be:
 * 
 * 
 * Some examples:
 * 
 * <ul>
 * 	<li>245(1-2)a</li>
 * 	<li>856(3-2)a-u</li>
 * </ul>
 * 
 * @author Emiliano Cammilletti
 * @since 1.0
 */
@Component
@Scope("prototype")
public class MultipleVariableFieldExpression implements Expression<ImmutableNodeList, Document> {
	static final String MISSING_PARENTHESIS_ERR_MESSAGE = "Invalid specs (%s): a parenthesis is missing.";
	static final String BAD_INDICATORS_PATTERN_ERR_MESSAGE = "Invalid specs (%s): bad indicators pattern.";
		
	private final String expression;
	
	private final String specs;
	

	@Autowired
	XPath xpath;	
	/**
	 * Builds a new expression with the given specs.
	 * 
	 * @param specs the expression specs.
	 */
	public MultipleVariableFieldExpression(final String specs) {
		if (specs == null || specs.trim().length() == 0) {
			throw new IllegalArgumentException(NULL_SPECS_ERR_MESSAGE);
		}
		
		final int indexOfOpeningParenthesis = specs.indexOf("(");
		final int indexOfClosingParenthesis = specs.indexOf(")", indexOfOpeningParenthesis);
		
		if ((indexOfOpeningParenthesis != -1 && indexOfClosingParenthesis == -1)
			||  
			(indexOfOpeningParenthesis == -1 && indexOfClosingParenthesis != -1)) {
			throw new IllegalArgumentException(String.format(MISSING_PARENTHESIS_ERR_MESSAGE, specs));	
		}
		
		if (indexOfOpeningParenthesis != -1 && indexOfClosingParenthesis != -1) {
			String tag = specs.substring(0, indexOfOpeningParenthesis).trim();
			
			final StringBuilder expressionBuilder = new StringBuilder()
				.append("record/datafield[@tag='")
				.append(tag)
				.append("']");
			
			expression = expressionBuilder.toString();
			
		} else {
			expression = new StringBuilder()
				.append("record/datafield[@tag='")
				.append(specs.substring(0, 3))
				.append("']")
				.toString();
		}
		this.specs = specs;
	}
	
	@Override
	public ImmutableNodeList evaluate(final Document target) {
		try {
			return xpath.many(expression, target);
		} catch (final Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Override
	public String specs() {
		return specs;
	}
	
}