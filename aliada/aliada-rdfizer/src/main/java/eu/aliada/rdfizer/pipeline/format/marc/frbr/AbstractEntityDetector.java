// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import static eu.aliada.shared.Strings.isNotNullAndNotEmpty;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.w3c.dom.Document;

/**
 * Base class for detecting entities.
 * 
 * @author Emiliano Cammilletti
 * @author Andrea Gazzarini
 * @since 1.0
 */
public abstract class AbstractEntityDetector<O> {

	/**
	 * @param target the (record) document that is the detection target.
	 * @return an identity Object associated with the detected entity.
	 */
	abstract O detect(Document target);

	/**
	 * Appends a given value to the buffer only if that value is not null and
	 * not empty.
	 * 
	 * @param builder the identity buffer actings as accumulator.
	 * @param value the value to append.
	 * @return the builder reference eventually updated with the incoming value.
	 */
	protected StringBuilder append(
			final StringBuilder builder, 
			final String value) {
		if (isNotNullAndNotEmpty(value)) {
			return builder.append(value);
		}
		return builder;
	}

	/**
	 * Appends the given values to the buffer only if that value is not null and
	 * not empty.
	 * 
	 * @param builder the identity buffer actings as accumulator.
	 * @param values the value to append.
	 * @return the builder reference eventually updated with the incoming value.
	 */
	protected StringBuilder append(
			final StringBuilder builder,
			final Map<String, String> values) {
		if (values != null) {
			for (final Entry<String, String> entry : values.entrySet()) {
				append(builder, entry.getValue());
			}
		}
		return builder;
	}
	
	/**
	 * Add values map into the result map Object after assigned the identifier.
	 * @param map result map refined with identifier value
	 * @param result object with the key and values of map @param
	 * @return the result object 
	 */
	protected Map<String, List<String>> put(final Map<String, List<String>> map, final Map<String, List<String>> result) {
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			List<String> l = map.get(key);
			for (int i = 0; i < l.size(); i++) {
				String element = identifier(l.get(i));
				l.set(i, element);
			}
			result.putAll(map);
		}
		
		return result;
	}
	
	/**
	 * Return an unique identifier from a string.
	 * 
	 * @param value the string value.
	 * @return the identifier associated with the incoming value.
	 */
	public static String identifier(final String value) {
		return value != null ? UUID.nameUUIDFromBytes(value.getBytes()).toString() : null;
	}	
}