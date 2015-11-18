package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import static java.util.stream.Collectors.toList;

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
		this.useLiteralValueAsIdentifier = 
				"WORK".equals(entityKind()) || 
				"PERSON".equals(entityKind()) || 
				"CORPORATE".equals(entityKind()) || 
				"FAMILY".equals(entityKind());
	}
	
	/**
	 * This method detect every xpath values for the person entity
	 * 
	 * @param target the target
	 * @return the value 
	 */
	public Map<String, List<String>> detect(final Document target) {
		final Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (final Expression<Map<String, List<String>>, Document> expression : expressions) {
			put(expression.evaluate(target),map);
		}
		return map;
	}

	/**
	 * Add values map into the result map Object after assigned the identifier.
	 * @param tagAndHeadings result map refined with identifier value
	 * @param result object with the key and values of map @param
	 * @return the result object 
	 */
	protected Map<String, List<String>> put(final Map<String, List<String>> tagAndHeadings, final Map<String, List<String>> result) {
		for (final Entry<String, List<String>> entry : tagAndHeadings.entrySet()) {
			result.put(
					entry.getKey(), 
					entry.getValue().stream()
						.map(
							heading -> useLiteralValueAsIdentifier 
											? heading 
											: identifier(entityKind(), Strings.clean(heading)))
						.collect(toList()));
		}
		return result;
	}
	
	@Override
	public String entityKind() {
		return entityCode;
	}	
}