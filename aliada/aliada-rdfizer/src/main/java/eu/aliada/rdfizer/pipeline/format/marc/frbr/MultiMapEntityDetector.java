package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import eu.aliada.rdfizer.pipeline.format.marc.selector.Expression;
import eu.aliada.shared.Strings;

@Component
public class MultiMapEntityDetector extends AbstractEntityDetector<Map<String, List<String>>>{
	protected final List<Expression<Map<String, List<String>>, Document>> expressions;
	
	private final String entityCode;
	private final boolean useLiteralValueAsIdentifier;
	
	public MultiMapEntityDetector() {
		this.expressions = null;
		this.entityCode = null;
		this.useLiteralValueAsIdentifier = true;
	}
	
	/**
	 * Builds a new detector with the following rules.
	 * 
	 * @param expressions the subsequent detection rules.
	 */
	public MultiMapEntityDetector(final List<Expression<Map<String, List<String>>, Document>> expressions, final String entityCode) {
		this.expressions = expressions;
		this.entityCode = entityCode;
		this.useLiteralValueAsIdentifier = "PERSON".equals(entityKind()) || "CORPORATE".equals(entityKind()) || "FAMILY".equals(entityKind());
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

	/**
	 * Add values map into the result map Object after assigned the identifier.
	 * @param map result map refined with identifier value
	 * @param result object with the key and values of map @param
	 * @return the result object 
	 */
	protected Map<String, List<String>> put(final Map<String, List<String>> map, final Map<String, List<String>> result) {
		for (final Entry<String, List<String>> entry : map.entrySet()) {
			final String tag = entry.getKey();
			final List<String> headings = entry.getValue();
			final List<String> uris = new ArrayList<String>(headings.size());
			
			for (final String heading : headings) {
				uris.add(useLiteralValueAsIdentifier ? heading : identifier(entityKind(), Strings.clean(heading)));
			}
			
			result.put(tag, uris);
		}
		return result;
	}
	
	@Override
	String entityKind() {
		return entityCode;
	}	
}