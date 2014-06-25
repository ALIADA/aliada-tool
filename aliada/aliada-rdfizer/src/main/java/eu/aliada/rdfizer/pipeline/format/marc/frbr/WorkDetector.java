// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import org.w3c.dom.Document;

import eu.aliada.rdfizer.pipeline.format.marc.selector.FirstMatch;

/**
 * 
 * @author Emiliano Cammilletti
 * @since 1.0
*/
public class WorkDetector {
	
 private FirstMatch<Document> firstMatchForUniformTitle;
 private FirstMatch<Document> firstMatchForDate;
 private FirstMatch<Document> firstMatchForOtherStandardIdentifier;
 private FirstMatch<Document> firstMatchForCharacterDistinguish;
/**
 * @return the firstMatchForUniformTitle
 */
public FirstMatch<Document> getFirstMatchForUniformTitle() {
	return firstMatchForUniformTitle;
}
/**
 * @param firstMatchForUniformTitle the firstMatchForUniformTitle to set
 */
public void setFirstMatchForUniformTitle(
		final FirstMatch<Document> firstMatchForUniformTitle) {
	this.firstMatchForUniformTitle = firstMatchForUniformTitle;
}
/**
 * @return the firstMatchForSequenceTagForDate
 */

/**
 * @return the firstMatchForOtherStandardIdentifier
 */
public FirstMatch<Document> getFirstMatchForOtherStandardIdentifier() {
	return firstMatchForOtherStandardIdentifier;
}
/**
 * @return the firstMatchForForDate
 */
public final FirstMatch<Document> getFirstMatchForDate() {
	return firstMatchForDate;
}
/**
 * @param firstMatchForDate the firstMatchForDate to set
 */
public final void setFirstMatchForDate(
		final FirstMatch<Document> firstMatchForDate) {
	this.firstMatchForDate = firstMatchForDate;
}
/**
 * @param firstMatchForOtherStandardIdentifier the firstMatchForOtherStandardIdentifier to set
 */
public void setFirstMatchForOtherStandardIdentifier(
		final FirstMatch<Document> firstMatchForOtherStandardIdentifier) {
	this.firstMatchForOtherStandardIdentifier = firstMatchForOtherStandardIdentifier;
}
/**
 * @return the firstMatchForCharacterDistinguish
 */
public FirstMatch<Document> getFirstMatchForCharacterDistinguish() {
	return firstMatchForCharacterDistinguish;
}
/**
 * @param firstMatchForCharacterDistinguish the firstMatchForCharacterDistinguish to set
 */
public void setFirstMatchForCharacterDistinguish(
		final FirstMatch<Document> firstMatchForCharacterDistinguish) {
	this.firstMatchForCharacterDistinguish = firstMatchForCharacterDistinguish;
}
 
 

	
}
