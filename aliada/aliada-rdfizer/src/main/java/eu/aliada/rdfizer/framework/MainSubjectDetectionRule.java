// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.framework;

/**
 * Before doing a conversion, we need to identify the main subject (i.e. the URI of the new entity).
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 * @param <I> the input kind.
 * @param <O> the output kind. 
 */
public interface MainSubjectDetectionRule<I, O> {
	/**
	 * Computes the main subject starting from a given input.
	 * 
	 * @param input the input.
	 * @return the main subject.
	 */
	O computeFrom(I input);
}