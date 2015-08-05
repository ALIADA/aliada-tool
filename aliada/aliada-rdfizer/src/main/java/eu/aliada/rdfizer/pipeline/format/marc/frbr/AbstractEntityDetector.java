// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import static eu.aliada.shared.Strings.isNotNullAndNotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.w3c.dom.Document;

import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * Base class for detecting entities.
 * 
 * @author Emiliano Cammilletti
 * @author Andrea Gazzarini
 * @since 1.0
 */
public abstract class AbstractEntityDetector<O> {	
	protected static Log LOGGER = new Log(AbstractEntityDetector.class);
	
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
		for (final Entry<String, List<String>> entry : map.entrySet()) {
			final String tag = entry.getKey();
			final List<String> headings = entry.getValue();
			final List<String> uris = new ArrayList<String>(headings.size());
			for (final String heading : headings) {
				uris.add(identifier(entityKind(), heading));
			}
			
			result.put(tag, uris);
		}
		return result;
	}
	
	/**
	 * Return an unique identifier from a string.
	 * 
	 * @param value the string value.
	 * @return the identifier associated with the incoming value.
	 */
	public static String identifier(final String entityKind, final String value) {	
		if (value != null) {
			LOGGER.debug(MessageCatalog._00060_FRBR_ENTITY_DEBUG, entityKind, value);
			return UUID.nameUUIDFromBytes(value.getBytes()).toString();
		} 
		return null;
	}	
	
	/**
	 * Returns a mnemonic code of the entity kind detected by this component.
	 * 
	 * @return a mnemonic code of the entity kind detected by this component.
	 */
	abstract String entityKind();
}