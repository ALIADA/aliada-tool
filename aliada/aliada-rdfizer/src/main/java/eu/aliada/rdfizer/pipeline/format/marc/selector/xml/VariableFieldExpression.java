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
import eu.aliada.rdfizer.pipeline.format.xml.XPath;

/**
 * A selector expression interpreter for MARCXML variable fields.
 * It accepts these kind of specs:
 * 
 * <ul>
 * 	<li>245t: selects the first not null subfield 't' of field 245;</li>
 * 	<li>245[(<indicators pattern>)]t: selects the first not null subfield 't' of field 245 that has a matching indicator pattern;</li>
 * </ul>
 * 
 * The indicator patterns is an optional 2 chars pattern between parenthesis where each position can be:
 * 
 * <ul>
 * 	<li>a number, therefore a literal value that has to be matched;</li>
 * 	<li>a pound (#), indicating an empty value.</li>
 * 	<li>a question mark: in this case the selector doesn't consider this position (i.e. all values).</li>
 * </ul>
 * 
 * Some examples:
 * 
 * <ul>
 * 	<li>245(#2)a</li>
 * 	<li>856(42)u</li>
 * 	<li>856u = 856(??)u</li>
 * 	<li>856(#4)u</li>
 * </ul>
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@Component
@Scope("prototype")
public class VariableFieldExpression implements Expression<String, Document> {
	static final String MISSING_PARENTHESIS_ERR_MESSAGE = "Invalid specs (%s): a parenthesis is missing.";
	static final String BAD_INDICATORS_PATTERN_ERR_MESSAGE = "Invalid specs (%s): bad indicators pattern.";
		
	private final String expression;
	
	private final String specs;

	@Autowired
	private XPath xpath;	
	/**
	 * Builds a new expression with the given specs.
	 * 
	 * @param specs the expression specs.
	 */
	public VariableFieldExpression(final String specs) {
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
			final String pattern = specs.substring(indexOfOpeningParenthesis + 1, indexOfClosingParenthesis).trim();
			if (pattern.length() != 2) {				
				throw new IllegalArgumentException(String.format(BAD_INDICATORS_PATTERN_ERR_MESSAGE, specs));		
			}
			
			char firstIndicatorPattern = pattern.charAt(0);
			char secondIndicatorPattern = pattern.charAt(1);
			
			final StringBuilder expressionBuilder = new StringBuilder()
				.append("datafield[@tag='")
				.append(specs.substring(0, indexOfOpeningParenthesis).trim())
				.append("'");
			
			if (firstIndicatorPattern != '?') {
				if (firstIndicatorPattern == '#') {
					firstIndicatorPattern = ' ';
				} 				
				
				expressionBuilder
					.append(" and contains(@ind1,'")
					.append(firstIndicatorPattern)
					.append("')");
			}

			if (secondIndicatorPattern != '?') {
				if (secondIndicatorPattern == '#') {
					secondIndicatorPattern = ' ';
				} 				
				
				expressionBuilder
					.append(" and contains(@ind2,'")
					.append(secondIndicatorPattern)
					.append("')");
			}

			expressionBuilder
				.append("]/subfield[@code=']")
				.append(specs.charAt(specs.trim().length() - 1))
				.toString();
			expression = expressionBuilder.toString();
			
		} else {
			expression = new StringBuilder()
				.append("datafield[@tag='")
				.append(specs.substring(0, 3))
				.append("']/subfield[@code=']")
				.append(specs.charAt(specs.trim().length() - 1))
				.toString();
		}
		this.specs = specs;
	}
	
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
		return specs;
	}
}