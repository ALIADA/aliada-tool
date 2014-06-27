// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import org.w3c.dom.Document;

import eu.aliada.rdfizer.pipeline.format.marc.selector.FirstMatch;

/**
 * Class containes "expression" objects which extracts values related with Expression entity.
 * 
 * @author Emiliano Cammilletti
 * @since 1.0
*/
public class ExpressionDetector extends AbstractEntityDetecor{

	
	 private FirstMatch<Document> firstMatchForcontentType;
	 private FirstMatch<Document> firstMatchForDate;
	 private FirstMatch<Document> firstMatchLanguage;
	 private FirstMatch<Document> firstMatchForOtherDistinguishFeatureForWork;
	 private FirstMatch<Document> firstMatchForUniformTitle;
	 
	/**
	 * @return the firstMatchForcontentType
	 */
	public FirstMatch<Document> getFirstMatchForcontentType() {
		return firstMatchForcontentType;
	}
	/**
	 * @param firstMatchForcontentType the firstMatchForcontentType to set
	 */
	public void setFirstMatchForcontentType(
			final FirstMatch<Document> firstMatchForcontentType) {
		this.firstMatchForcontentType = firstMatchForcontentType;
	}
	/**
	 * @return the firstMatchForDate
	 */
	public FirstMatch<Document> getFirstMatchForDate() {
		return firstMatchForDate;
	}
	/**
	 * @param firstMatchForDate the firstMatchForDate to set
	 */
	public void setFirstMatchForDate(final FirstMatch<Document> firstMatchForDate) {
		this.firstMatchForDate = firstMatchForDate;
	}
	/**
	 * @return the firstMatchLanguage
	 */
	public FirstMatch<Document> getFirstMatchLanguage() {
		return firstMatchLanguage;
	}
	/**
	 * @param firstMatchLanguage the firstMatchLanguage to set
	 */
	public void setFirstMatchLanguage(final FirstMatch<Document> firstMatchLanguage) {
		this.firstMatchLanguage = firstMatchLanguage;
	}
	/**
	 * @return the firstMatchForOtherDistinguishFeatureForWork
	 */
	public FirstMatch<Document> getFirstMatchForOtherDistinguishFeatureForWork() {
		return firstMatchForOtherDistinguishFeatureForWork;
	}
	/**
	 * @param firstMatchForOtherDistinguishFeatureForWork the firstMatchForOtherDistinguishFeatureForWork to set
	 */
	public void setFirstMatchForOtherDistinguishFeatureForWork(
			final FirstMatch<Document> firstMatchForOtherDistinguishFeatureForWork) {
		this.firstMatchForOtherDistinguishFeatureForWork = firstMatchForOtherDistinguishFeatureForWork;
	}
	 
	/**
	 * @return the firstMatchForUniformTitle
	 */
	public FirstMatch<Document> getFirstMatchForUniformTitle() {
		return firstMatchForUniformTitle;
	}

	/**
	 * @param firstMatchForUniformTitle
	 *            the firstMatchForUniformTitle to set
	 */
	public void setFirstMatchForUniformTitle(
			final FirstMatch<Document> firstMatchForUniformTitle) {
		this.firstMatchForUniformTitle = firstMatchForUniformTitle;
	}
	
	/**
	 * This method concat every xpath values for the expression entity 
	 * 
	 * @param target the target
	 * @return the value 
	 */
	String concat(final Document target) {
		StringBuffer buffer = new StringBuffer();
		return 	 buffer.append(getFirstMatchForUniformTitle().evaluate(target))
				.append(getFirstMatchForcontentType().evaluate(target)) 
				.append(getFirstMatchForDate().evaluate(target)) 
				.append(getFirstMatchLanguage().evaluate(target)) 
				.append(getFirstMatchForOtherDistinguishFeatureForWork().evaluate(target)).toString();
	}
}
