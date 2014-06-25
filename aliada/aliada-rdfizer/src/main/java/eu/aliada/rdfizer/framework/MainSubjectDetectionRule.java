// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.framework;

import eu.aliada.rdfizer.datasource.rdbms.JobConfiguration;

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
	 * @param configuration the configuration of the job associated with the current conversion.
	 * @return the main subject.
	 * @throws UnableToDetermineMainSubectException in case the subject cannot be determined.
	 */
	O computeFrom(I input, JobConfiguration configuration) throws UnableToDetermineMainSubectException;
}