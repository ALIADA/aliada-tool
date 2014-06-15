// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.selector;

import org.marc4j.marc.Record;


/**
 * Expression interface for MARC evaluators.
 * 
 * 100t:110t
 * 008[35-41]
 * 100(#12)t:
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 * @param <T> the evaluation result kind.
 */
public interface Expression<T> {
	String NULL_SPECS_ERR_MESSAGE = "Expression specs cannot be null.";
	
	/**
	 * Evaluates this expression.
	 * 
	 * @param target the target record.
	 * @return the evaluation of this expression as a string (null in case of empty evaluation).
	 */
	T evaluate(final Record target);
}