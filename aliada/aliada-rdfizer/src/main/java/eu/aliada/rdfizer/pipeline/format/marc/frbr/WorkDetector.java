// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import static eu.aliada.shared.Strings.isNullOrEmpty;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

import eu.aliada.rdfizer.pipeline.format.marc.selector.Expression;
import eu.aliada.rdfizer.pipeline.format.marc.selector.FirstMatch;
/**
 * Class for detecting entity Work.
 * Class containes "expression" objects which extracts values related with Work entity. 
 *  
 * @author Emiliano Cammilletti
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class WorkDetector extends AbstractEntityDetector {

	private final FirstMatch<Document> uniformTitleDetectionRule;

	private final List<Expression<Map<String, String>, Document>> expressions;
	
	/**
	 * Builds a new detector with the following rules.
	 * 
	 * @param uniformTitleDetectionRule the uniform title detection rule.
	 * @param expressions the subsequent detection rules.
	 */
	public WorkDetector(
			final FirstMatch<Document> uniformTitleDetectionRule,
			final List<Expression<Map<String, String>, Document>> expressions) {
		this.uniformTitleDetectionRule = uniformTitleDetectionRule;
		this.expressions = expressions;
	}

	/**
	 * This method concat every xpath values for the work entity
	 * 
	 * @param target the target
	 * @return the value 
	 */
	String detect(final Document target) {
		final StringBuilder buffer = new StringBuilder();
		
		final String uniformTitle = uniformTitleDetectionRule.evaluate(target);
		if (isNullOrEmpty(uniformTitle)) {
			// TODO: Log + JMX 
			return null; 
		}			
		
		append(buffer, uniformTitle);
		
		for (final Expression<Map<String, String>, Document> expression : expressions) {
			append(buffer, expression.evaluate(target));
		}
		return buffer.toString();
	}
}
