// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortium
package eu.aliada.rdfizer.pipeline.format.lido;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;

import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.Resource;
import org.semanticweb.yars.nx.Triple;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import eu.aliada.rdfizer.pipeline.rules.XPathBasedRule;

/**
 * A rule for detecting the main subject from a LIDO record.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class SubjectDetectionRule extends XPathBasedRule<Node> {
//	private final XPathExpression idExpression = compile("lidoRecID/text()");
//	private final XPathExpression categoryExpression = compile("/lidoRecID/text()");
	
	/**
	 * Builds a new subject detection rule with the given XPATH expression.
	 * 
	 * @param expression the XPATH expression.
	 */
	public SubjectDetectionRule(final String expression) {
		super(expression);
	}

	@Override
	public QName xpathEvaluationKind() {
		return XPathConstants.NODESET;
	}

	@Override
	protected Node toTriples(
			final Node topLevelSubject, 
			final Object evaluation,
			final List<Triple> accumulator) {
				
		final NodeList nodes = (NodeList) evaluation;
		if (nodes == null) {
			return null;
		}
		
		// TODO: URI composition
		for (int index = 0; index < nodes.getLength(); index++) {
			final Element node = (Element) nodes.item(index);
			final String nodeName = node.getNodeName();
			if (nodeName.endsWith(":lidoRecID") || "lidoRecID".equals(nodeName)) {
				return new Resource("http://www.szepmuveszeti.hu/id/resource/E22_Man-Made_Object/" + node.getTextContent());
			}
		}
		return null;
	}
}