// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer;

import org.springframework.stereotype.Component;

import eu.aliada.shared.ID;
import eu.aliada.shared.Strings;

/**
 * A generic tool used in templates to invoke some useful functions.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@Component
public class Function {
	
	/**
	 * Returns a new generated UID.
	 * 
	 * @return a new generated UID.
	 */
	public long getId() {
		return ID.get();
	}
	
	/**
	 * Normalizes a given string.
	 * Diacritics are normalized and space are replaced with underscores.
	 * 
	 * @param value the string to be normalized.
	 * @return the normalized string.
	 */
	public String normalize(final String value) {
		return Strings.toURILocalName(value);
	}
}