// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.lido;

import org.w3c.dom.Document;

import eu.aliada.rdfizer.framework.MainSubjectDetectionRule;

/**
 * Main subject detection rule implementation for LIDO records.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class LidoMainSubjectDetectionRule implements MainSubjectDetectionRule<Document, String> {
	@Override
	public String computeFrom(final Document input) {
		// FIXME : hardcoded!!
		return "http://www.szepmuveszeti.hu/id/resource/E22_Man-Made_Object/szepmuveszeti.hu_object_29";
	}
}
