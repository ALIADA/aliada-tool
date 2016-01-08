// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import static eu.aliada.shared.Strings.isNullOrEmpty;

import java.util.Collections;
import java.util.List;

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
public class WorkDetector extends AbstractEntityDetector<String> {

	private final FirstMatch<Document> uniformTitleDetectionRule;
	private final static List<Expression<String, Document>> NO_EXPRESSIONS = Collections.emptyList();
	
	private final List<Expression<String, Document>> expressions;
	
	/**
	 * Builds a new detector with the following rules.
	 * 
	 * @param uniformTitleDetectionRule the uniform title detection rule.
	 * @param expressions the subsequent detection rules.
	 */
	public WorkDetector(
			final FirstMatch<Document> uniformTitleDetectionRule,
			final List<Expression<String, Document>> expressions) {
		this.uniformTitleDetectionRule = uniformTitleDetectionRule;
		this.expressions = (expressions != null) ? expressions : NO_EXPRESSIONS;
	}
	
	/**
	 * Builds a new detector with the following rules.
	 * 
	 * @param uniformTitleDetectionRule the uniform title detection rule.
	 * @param expressions the subsequent detection rules.
	 */
	public WorkDetector(final FirstMatch<Document> uniformTitleDetectionRule) {
		this(uniformTitleDetectionRule, null);
	}

	/**
	 * This method concat every xpath values for the work entity
	 * 
	 * @param target the target
	 * @return the value 
	 */
	public String detect(final Document target) {
		final StringBuilder buffer = new StringBuilder();
		
		final String uniformTitle = uniformTitleDetectionRule.evaluate(target);
		if (isNullOrEmpty(uniformTitle)) {
			return null; 
		}			
		
		append(buffer, uniformTitle);
		expressions.stream().forEach(expression -> append(buffer, expression.evaluate(target)));
		
		return buffer.toString();
	}

	@Override
	public String entityKind() {
		return "WORK";
	}
}
