// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import org.w3c.dom.Document;

import eu.aliada.rdfizer.pipeline.format.marc.selector.FirstMatch;

/**
 * Class containes "expression" objects which extracts values related with Manifestation entity.
 * 
 * @author Emiliano Cammilletti
 * @since 1.0
*/
public class ManifestationDetector extends AbstractEntityDetecor {
	
 private FirstMatch<Document> firstMatchForControlNumber;

/**
 * @return the firstMatchControlNumber
 */
public final FirstMatch<Document> getFirstMatchForControlNumber() {
	return firstMatchForControlNumber;
}

/**
 * @param firstMatchForControlNumber the firstMatchForControlNumber to set
 */
public final void setFirstMatchForControlNumber(
		final FirstMatch<Document> firstMatchForControlNumber) {
	this.firstMatchForControlNumber = firstMatchForControlNumber;
}

	@Override
	String concat(final Document target) {
		return null;
	} 
	
}
