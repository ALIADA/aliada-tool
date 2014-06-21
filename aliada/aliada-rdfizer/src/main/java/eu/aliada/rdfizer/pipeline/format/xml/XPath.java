// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.xml;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XPath {
	final XPathFactory xpathfactory = XPathFactory.newInstance();

	public String value(final String expression, final Object context) throws XPathExpressionException {
		final XPathExpression xpath = xpathfactory.newXPath().compile(expression);
		return (String) xpath.evaluate(context, XPathConstants.STRING);
	}	
	
	public Node one(final String expression, final Object context) throws XPathExpressionException {
		final XPathExpression xpath = xpathfactory.newXPath().compile(expression);
		return (Node) xpath.evaluate(context, XPathConstants.NODE);
	}	
	
	public ImmutableNodeList many(final String expression, final Object context) throws XPathExpressionException {
		final XPathExpression xpath = xpathfactory.newXPath().compile(expression);
		return new ImmutableNodeList((NodeList) xpath.evaluate(context, XPathConstants.NODESET));
	}	
}
