// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.shared;

public abstract class Strings {

	public static boolean isNotNullAndNotEmpty(final String value) {
		return value != null && value.trim().length() != 0;
	}
	
	public static boolean isNullOrEmpty(final String value) {
		return value == null || value.trim().length() == 0;
	}	
}