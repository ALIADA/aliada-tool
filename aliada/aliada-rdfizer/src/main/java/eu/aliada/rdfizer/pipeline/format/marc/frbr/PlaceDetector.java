// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

import eu.aliada.rdfizer.pipeline.format.marc.selector.Expression;
/**
 * Class for detecting entity Person.
 * Class containes "expression" objects which extracts values related with Person entity. 
 *  
 * @author Emiliano Cammilletti
 * @since 1.0
 */
public class PlaceDetector extends AbstractEntityDetector<Map<String, List<String>>> { 

	private final List<Expression<Map<String, List<String>>, Document>> expressions;
	
	/**
	 * Builds a new detector with the following rules.
	 * 
	 * @param expressions the subsequent detection rules.
	 */
	public PlaceDetector(final List<Expression<Map<String, List<String>>, Document>> expressions) {
		this.expressions = expressions;
	}

	/**
	 * This method detect every xpath values for the person entity
	 * 
	 * @param target the target
	 * @return the value 
	 */
	Map<String, List<String>> detect(final Document target) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (final Expression<Map<String, List<String>>, Document> expression : expressions) {
			put(expression.evaluate(target),map);
		}
		return map;
	}
}
