// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import static eu.aliada.shared.Strings.isNotNullAndNotEmpty;

import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Document;

/**
 * Base class for detecting entities.
 * 
 * @author Emiliano Cammilletti
 * @author Andrea Gazzarini
 * @since 1.0
 */
public abstract class AbstractEntityDetector {

	/**
	 * @param target the (record) document that is the detection target.
	 * @return an identity string associated with the detected entity.
	 */
	abstract String detect(Document target);

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
}