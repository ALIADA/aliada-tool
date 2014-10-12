// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.aliada.rdfizer.datasource.Cache;
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
	
	@Autowired
	private Cache cache;
	
	/**
	 * Returns a new generated UID.
	 * 
	 * @return a new generated UID.
	 */
	public long getId() {
		return ID.get();
	}
	
	/**
	 * Normalizes and lowercases a given string.
	 * Diacritics are normalized and space are replaced with underscores.
	 * 
	 * @param value the string to be normalized.
	 * @return the normalized string.
	 */
	public String normalize(final String value) {
		return Strings.toURILocalName(value).toLowerCase();
	}
	
	/**
	 * Normalizes a given string.
	 * Diacritics are normalized and space are replaced with underscores.
	 * 
	 * @param value the string to be normalized.
	 * @return the normalized string.
	 */
	public String normalizeWithoutLowercase(final String value) {
		return Strings.toURILocalName(value);
	}	
	
	public String uuid(final String value) {
		return UUID.nameUUIDFromBytes(value.getBytes()).toString();
	}
	
	/**
	 * Normalizes a given string as {@link Function#normalize} but also removing all spaces and punctuation.
	 * 
	 * @param value the string to be normalized.
	 * @return the normalized string.
	 */
	public String normalizeStrong(final String value) {
		   return value == null ? null
			        : Normalizer.normalize(value, Form.NFD)
			            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
			            .replaceAll("[^A-Za-z0-9]", "");
	}		
	
	public String lidoClass(final String value) {
		final String midx = "/id/resource/";
		
		int indexOfIdResource = value.indexOf(midx);
		if (indexOfIdResource != -1) {
			int indexOfSlash = value.indexOf("/", (indexOfIdResource + midx.length() + 1));
			if (indexOfSlash != -1) {
				return value.substring((indexOfIdResource + midx.length()), indexOfSlash);
			}
		}
		return null;
	}
	
	/**
	 * Returns the ALIADA event type class corresponding to the given CIDOC-CRM class.
	 * 
	 * @param from the CIDOC-CRM event type class.
	 * @return the ALIADA class that corresponds to the given input class.
	 */
	public String toAliadaEventTypeClass(final String from) {
		return cache.getAliadaEventTypeClassFrom(from);
	}
	
	/**
	 * Returns true if the given string is not null and not empty.
	 * 
	 * @param value the string to check.
	 * @return true if the given string is not null and not empty.
	 */
	public boolean isNotNullAndNotEmpty(final String value) {
		return Strings.isNotNullAndNotEmpty(value);
	}
}