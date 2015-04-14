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
import eu.aliada.rdfizer.pipeline.format.xml.XPath;

/**
 * Main subject detection rule implementation for LIDO records.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@Component("lido-subject-detection-rule")
public class LidoMainSubjectDetectionRule implements MainSubjectDetectionRule<Element, String> {
	@Autowired
	private XPath xpath;

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
			/*
			 * The class will always be the same for all LIDO objects. 
			 * The following processing is not necessary any more.
			List<Node> categories = xpath.many("category/conceptID", input);
			if (!categories.isEmpty()) {
				for (final Node categoryNode : categories) {
					final Element category = (Element) categoryNode;
					final NamedNodeMap attributes = category.getAttributes();
					for (int i = 0; i < attributes.getLength(); i++) {
						final Attr attribute = (Attr) attributes.item(i);
						if (attribute.getName().endsWith(":type") || attribute.getName().equals("type")) {
							final String type = attribute.getValue();
							if ("URI".equals(type)) {
								final String value = category.getTextContent();
								if (value.contains("cidoc-crm")) {
									int indexOfLocalName = value.lastIndexOf("/");
									if (indexOfLocalName != -1) {
										clazzURI = value.substring(indexOfLocalName + 1);
										break;
									} 
								}
							}
						} else if (attribute.getName().endsWith(":source") || attribute.getName().equals("source")) {
							final String description = category.getTextContent();
							clazzURI = description.substring(0, description.indexOf(" "));
							break;
						}
					}
				}
			} 
			*/
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
