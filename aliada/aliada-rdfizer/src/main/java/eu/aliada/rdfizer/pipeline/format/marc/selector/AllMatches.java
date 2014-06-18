// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.selector;

import static eu.aliada.shared.Strings.isNotNullAndNotEmpty;

import java.util.HashMap;
import java.util.Map;

import org.marc4j.marc.Record;

/**
 * A composite expression that selects the first not-null evaluation of a set of expressions.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class AllMatches implements Expression<Map<String, String>> {
	private final Expression<String> [] expressions;
	
	/**
	 * Builds a new {@link AllMatches} with a given expressions chain.
	 * 
	 * @param expressions the expressions that form the execution chain.
	 */
	@SafeVarargs
	public AllMatches(final Expression<String> ... expressions) {
		this.expressions = expressions;
	}
	
	@Override
	public Map<String, String> evaluate(final Record target) {
		final Map<String, String> result = new HashMap<String, String>(expressions.length);
		for (final Expression<String> expression : expressions) {
			final String value = expression.evaluate(target);
			if (isNotNullAndNotEmpty(value)) {
				result.put(expression.specs(), value);
			}
		}
		return result;
	}
	
	@Override
	public String specs() {
		throw new UnsupportedOperationException();
	}	
}