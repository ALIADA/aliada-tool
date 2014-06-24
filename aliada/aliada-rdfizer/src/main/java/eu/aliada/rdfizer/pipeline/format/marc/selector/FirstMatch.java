// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.selector;

import static eu.aliada.shared.Strings.isNotNullAndNotEmpty;


/**
 * A composite expression that selects the first not-null evaluation of a set of expressions.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 * @param <K> the record kind.
 */
public class FirstMatch<K> implements Expression<String, K> {
	private final Expression<String, K> [] expressions;
	
	/**
	 * Builds a new {@link FirstMatch} with a given expressions chain.
	 * 
	 * @param expressions the expressions that form the execution chain.
	 */
	@SafeVarargs
	public FirstMatch(final Expression<String, K> ... expressions) {
		this.expressions = expressions;
	}
	
	@Override
	public String evaluate(final K target) {
		for (final Expression<String, K> expression : expressions) {
			final String result = expression.evaluate(target);
			if (isNotNullAndNotEmpty(result)) {
				return result;
			}
		}
		return null;
	}
	
	@Override
	public String specs() {
		throw new UnsupportedOperationException();
	}	
}