// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.selector;

import static eu.aliada.shared.Strings.isNotNullAndNotEmpty;

import java.util.HashMap;
import java.util.Map;
import static eu.aliada.shared.Strings.clean;
/**
 * A composite expression that selects the first not-null evaluation of a set of expressions.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 * @param <K> the record kind.
 */
public class AllMatches<K> implements Expression<Map<String, String>, K> {
	private final Expression<String, K> [] expressions;
	
	/**
	 * Builds a new {@link AllMatches} with a given expressions chain.
	 * 
	 * @param expressions the expressions that form the execution chain.
	 */
	@SafeVarargs
	public AllMatches(final Expression<String, K> ... expressions) {
		this.expressions = expressions;
	}
	
	@Override
	public Map<String, String> evaluate(final K target) {
		final Map<String, String> result = new HashMap<String, String>(expressions.length);
		for (final Expression<String, K> expression : expressions) {
			final String value = expression.evaluate(target);
			if (isNotNullAndNotEmpty(value)) {
				result.put(expression.specs(), clean(value));
			}
		}
		return result;
	}
	
	@Override
	public String specs() {
		throw new UnsupportedOperationException();
	}	
}