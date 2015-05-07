// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.dc;
 
import static eu.aliada.shared.Strings.isNullOrEmpty;

import javax.xml.xpath.XPathExpressionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import eu.aliada.rdfizer.datasource.rdbms.JobInstance;
import eu.aliada.rdfizer.framework.MainSubjectDetectionRule;
import eu.aliada.rdfizer.framework.UnableToProceedWithConversionException;
import eu.aliada.rdfizer.pipeline.format.xml.OXPath;

/**
 * Main subject detection rule implementation for DC records.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@Component("dc-subject-detection-rule")
public class DublinCoreMainSubjectDetectionRule implements MainSubjectDetectionRule<Element, String> {
	@Autowired
	private OXPath xpath;
	
	@Override
	public String computeFrom(final Element input, final JobInstance configuration) throws UnableToProceedWithConversionException {
		try {
			final String id = xpath.value("header/identifier", input);
			if (isNullOrEmpty(id)) {
				throw new UnableToProceedWithConversionException("Unable to find a record ID for this record.");
			}
			return new StringBuilder()
				.append(configuration.getNamespace())
				.append("/")
				.append("E73_Information_Object")
				.append("/")
				.append(id)
				.toString();
		} catch (XPathExpressionException exception) {
			throw new UnableToProceedWithConversionException(exception);
		}
	}
}
