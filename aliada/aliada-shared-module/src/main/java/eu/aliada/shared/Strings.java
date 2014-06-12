// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.shared;

/**
 * Strings Booch utility.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public abstract class Strings {

	/**
	 * Returns true if the given string is not null and not empty.
	 * 
	 * @param value the string to check.
	 * @return true if the given string is not null and not empty.
	 */
	public static boolean isNotNullAndNotEmpty(final String value) {
		return value != null && value.trim().length() != 0;
	}
	
	/**
	 * Returns true if the given string is null or empty.
	 * 
	 * @param value the string to check.
	 * @return true if the given string is null or empty.
	 */
	public static boolean isNullOrEmpty(final String value) {
		return value == null || value.trim().length() == 0;
	}	
}