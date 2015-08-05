package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import eu.aliada.rdfizer.pipeline.format.marc.selector.Expression;

@Component
public class MultiMapEntityDetector extends AbstractEntityDetector<Map<String, List<String>>>{
	protected final List<Expression<Map<String, List<String>>, Document>> expressions;
	
	private final String entityCode;
	
	public MultiMapEntityDetector() {
		this.expressions = null;
		this.entityCode = null;
	}
	
	/**
	 * Builds a new detector with the following rules.
	 * 
	 * @param expressions the subsequent detection rules.
	 */
	public MultiMapEntityDetector(final List<Expression<Map<String, List<String>>, Document>> expressions, final String entityCode) {
		this.expressions = expressions;
		this.entityCode = entityCode;
	}
	
	/**
	 * This method detect every xpath values for the person entity
	 * 
	 * @param target the target
	 * @return the value 
	 */
	Map<String, List<String>> detect(final Document target) {
		final Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (final Expression<Map<String, List<String>>, Document> expression : expressions) {
			put(expression.evaluate(target),map);
		}
		return map;
	}

	@Override
	String entityKind() {
		return entityCode;
	}	
}