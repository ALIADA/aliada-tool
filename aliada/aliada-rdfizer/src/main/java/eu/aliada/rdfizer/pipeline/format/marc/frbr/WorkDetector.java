// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import org.w3c.dom.Document;

import eu.aliada.rdfizer.pipeline.format.marc.selector.FirstMatch;
import eu.aliada.rdfizer.pipeline.format.marc.selector.xml.VariableFieldExpression;

/**
 * 
 * 
 * @author Emiliano Cammilletti
 * @since 1.0
*/
public class WorkDetector {
	
 private FirstMatch<Document> firstMatchUniformTitle;
 private FirstMatch<Document> firstMatchOrderedWorkSequenceTagForDate;
 private FirstMatch<Document> firstMatchOrderedWorkSequenceTagForOtherStandardIdentifier;
 private FirstMatch<Document> firstMatchOrderedWorkSequenceTagForCharacterDistinguish;
 
 
	public FirstMatch<Document> getFirstMatchOrderedWorkSequenceTagForDate() {
		return firstMatchOrderedWorkSequenceTagForDate;
	}

	public void setFirstMatchOrderedWorkSequenceTagForDate(
			FirstMatch<Document> firstMatchOrderedWorkSequenceTagForDate) {
		this.firstMatchOrderedWorkSequenceTagForDate = firstMatchOrderedWorkSequenceTagForDate;
	}

	public FirstMatch<Document> getFirstMatchOrderedWorkSequenceTagForOtherStandardIdentifier() {
		return firstMatchOrderedWorkSequenceTagForOtherStandardIdentifier;
	}

	public void setFirstMatchOrderedWorkSequenceTagForOtherStandardIdentifier(
			FirstMatch<Document> firstMatchOrderedWorkSequenceTagForOtherStandardIdentifier) {
		this.firstMatchOrderedWorkSequenceTagForOtherStandardIdentifier = firstMatchOrderedWorkSequenceTagForOtherStandardIdentifier;
	}

	public FirstMatch<Document> getFirstMatchOrderedWorkSequenceTagForCharacterDistinguish() {
		return firstMatchOrderedWorkSequenceTagForCharacterDistinguish;
	}

	public void setFirstMatchOrderedWorkSequenceTagForCharacterDistinguish(
			FirstMatch<Document> firstMatchOrderedWorkSequenceTagForCharacterDistinguish) {
		this.firstMatchOrderedWorkSequenceTagForCharacterDistinguish = firstMatchOrderedWorkSequenceTagForCharacterDistinguish;
	}

	public FirstMatch<Document> getFirstMatchUniformTitle() {
		return firstMatchUniformTitle;
	}

	public void setFirstMatchUniformTitle(
			final FirstMatch<Document> firstMatchUniformTitle) {
		this.firstMatchUniformTitle = firstMatchUniformTitle;
	}	
	
}
