// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import org.w3c.dom.Document;

import eu.aliada.rdfizer.pipeline.format.marc.selector.FirstMatch;

/**
 * Class containing "expression" objects which extracts values related with
 * Manifestation entity.
 * 
 * @author Emiliano Cammilletti
 * @author Andrea Gazzarini.
 * @since 1.0
 */
public class ManifestationDetector extends AbstractEntityDetector<String> {

	private final FirstMatch<Document> controlNumberDetectionRule;

	/**
	 * Builds a new manifestation detection rule with the following rule.
	 * 
	 * @param controlNumberDetectionRule the control number detection rule.
	 */
	public ManifestationDetector(final FirstMatch<Document> controlNumberDetectionRule) {
		this.controlNumberDetectionRule = controlNumberDetectionRule;
	}

	@Override
	String detect(final Document target) {
		return controlNumberDetectionRule.evaluate(target);
	}
}