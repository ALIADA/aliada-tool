// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import static eu.aliada.shared.Strings.isNotNullAndNotEmpty;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

import eu.aliada.rdfizer.pipeline.format.marc.selector.Expression;

/**
 * Class containes "expression" objects which extracts values related with
 * Expression entity.
 * 
 * @author Emiliano Cammilletti
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class ExpressionDetector extends AbstractEntityDetector<String> {
	private final List<Expression<Map<String, String>, Document>> expressions;

	/**
	 * Builds a new detector with the following rules.
	 * 
	 * @param expressions the detection rules.
	 */
	public ExpressionDetector(final List<Expression<Map<String, String>, Document>> expressions) {
		this.expressions = expressions;
	}
	
	/**
	 * This method concat every xpath values for the expression entity
	 * 
	 * @param target the target document.
	 * @return the detected value.
	 */
	String detect(final Document target) {
		final StringBuilder buffer = new StringBuilder();
		for (final Expression<Map<String, String>, Document> expression : expressions) {
			append(buffer, expression.evaluate(target));
		}
		final String result = buffer.toString().trim();
		return isNotNullAndNotEmpty(result) ? result : null;
	}

	@Override
	String entityKind() {
		return "EXPRESSION";
	}
}