// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.lido;
 
import static eu.aliada.shared.Strings.isNullOrEmpty;

import javax.xml.xpath.XPathExpressionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import eu.aliada.rdfizer.datasource.Cache;
import eu.aliada.rdfizer.datasource.rdbms.JobInstance;
import eu.aliada.rdfizer.framework.MainSubjectDetectionRule;
import eu.aliada.rdfizer.framework.UnableToProceedWithConversionException;
import eu.aliada.rdfizer.pipeline.format.xml.OXPath;

/**
 * Main subject detection rule implementation for LIDO records.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@Component("lido-subject-detection-rule")
public class LidoMainSubjectDetectionRule implements MainSubjectDetectionRule<Element, String> {
	@Autowired
	private OXPath xpath;

	@Autowired
	private Cache cache;
	
	@Override
	public String computeFrom(final Element input, final JobInstance configuration) throws UnableToProceedWithConversionException {
		try {
			final String lidoRecId = xpath.value("lidoRecID", input);
			if (isNullOrEmpty(lidoRecId)) {
				throw new UnableToProceedWithConversionException("Unable to find a record ID.");
			}
			final StringBuilder builder = new StringBuilder(configuration.getNamespace());
			String clazzURI = null;
			String uri = cache.getAliadaClassFrom(clazzURI);
			int indexOfLocalName = uri.lastIndexOf("/");
			return builder
					.append(uri.substring(indexOfLocalName + 1))
					.append("/")
					.append(lidoRecId)
					.toString();
		} catch (XPathExpressionException exception) {
			throw new UnableToProceedWithConversionException(exception);
		}
	}
}
