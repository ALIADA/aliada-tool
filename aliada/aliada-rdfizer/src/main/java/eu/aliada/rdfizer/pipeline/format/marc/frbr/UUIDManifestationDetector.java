// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;

import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.rdfizer.pipeline.format.xml.OXPath;

/**
 * Class containing "expression" objects which extracts identifiers related with
 * Manifestation entity.
 * 
 * @author Andrea Gazzarini.
 * @since 1.0
 */
public class UUIDManifestationDetector extends AbstractEntityDetector<String> {
	
	private final String prefixExp;
	private final String idExp;
	
	@Autowired
	private OXPath xpath;
	
	/**
	 * Builds a new manifestation detection rule with the following rule.
	 * 
	 * @param controlNumberDetectionRule the control number detection rule.
	 */
	public UUIDManifestationDetector(final String prefixExp, final String idExp) {
		this.prefixExp = prefixExp;
		this.idExp = idExp;
	}

	@Override
	public String detect(final Document target) {
		try {
			return new StringBuilder()
				.append(xpath.df(prefixExp.substring(0,3), prefixExp.substring(3,4), target).getTextContent())
				.append(xpath.df(idExp.substring(0,3), idExp.substring(3,4), target).getTextContent())
				.toString();
		} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00034_NWS_SYSTEM_INTERNAL_FAILURE, exception);
			return null;
		}
	}

	@Override
	public String entityKind() {
		return "MANIFESTATION";
	}
}