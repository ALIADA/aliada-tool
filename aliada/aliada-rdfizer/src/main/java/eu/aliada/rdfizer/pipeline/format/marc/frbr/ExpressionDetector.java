// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.rdfizer.pipeline.format.xml.OXPath;

/**
 * Class containes "expression" objects which extracts values related with
 * Expression entity.
 * 
 * @author Emiliano Cammilletti
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class ExpressionDetector extends AbstractEntityDetector<List<FrbrExpression>> {
	@Autowired
	private OXPath xpath;

	
	
	/**
	 * Builds a new detector with the following rules.
	 * 
	 * @param expressions the detection rules.
	 */
	public ExpressionDetector() {
		// Nothing to be done here...maybe we should make the detectors configurable
	}
		
	/**
	 * This method concat every xpath values for the expression entity
	 * 
	 * @param target the target document.
	 * @return the detected value.
	 */
	public List<FrbrExpression> detect(final Document target) {
		try {
			final List<Node> $a = xpath.dfs("101", "a", target);
			if ($a.isEmpty()) {
				return null;
			}
					
			final List<FrbrExpression> expressions = 
					$a
						.stream()
						.map(node -> new FrbrExpression(node.getTextContent(), false))
						.collect(toList());
			
			final List<Node> $b = xpath.dfs("101", "b", target);
			if (!$b.isEmpty()) {
				expressions.addAll($b
						.stream()
						.map(node -> new FrbrExpression(node.getTextContent(), true))
						.collect(toList()));
			}
			
			final List<Node> $c = xpath.dfs("101", "c", target);
			if (!$c.isEmpty()) {
				expressions.addAll($c
						.stream()
						.map(node -> new FrbrExpression(node.getTextContent(), true))
						.collect(toList()));
			}			
			return expressions;
		} catch (final Exception exception) {
			LOGGER.error(MessageCatalog._00034_NWS_SYSTEM_INTERNAL_FAILURE, exception);
			return null;
		}		
	}

	@Override
	public String entityKind() {
		return "EXPRESSION";
	}
}