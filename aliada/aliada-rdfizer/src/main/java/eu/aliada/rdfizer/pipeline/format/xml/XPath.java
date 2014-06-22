// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.xml;

import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * ALIADA XPath tool.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class XPath {
	final ThreadLocal<Map<String, XPathExpression>> expressions = new ThreadLocal<Map<String, XPathExpression>>() {
		protected Map<String, XPathExpression> initialValue() {
			return new HashMap<String, XPathExpression>();
		};
	};
	
	final ThreadLocal<javax.xml.xpath.XPath> xpaths = new ThreadLocal<javax.xml.xpath.XPath>() {
		protected javax.xml.xpath.XPath initialValue() {
			return xpathfactory.newXPath();
		};
	};
	
	
	final XPathFactory xpathfactory = XPathFactory.newInstance();

	/**
	 * Evaluates a given XPATH and returns the result as a string.
	 * 
	 * @param expression the XPATH expression.
	 * @param context the DOM context.
	 * @return the result as a string.
	 * @throws XPathExpressionException in case of XPATH failure.
	 */
	public String value(final String expression, final Object context) throws XPathExpressionException {
		return (String) xpath(expression).evaluate(context, XPathConstants.STRING);
	}	
	
	/**
	 * Evaluates a given XPATH and returns the result as a single node.
	 * 
	 * @param expression the XPATH expression.
	 * @param context the DOM context.
	 * @return the result as a single node.
	 * @throws XPathExpressionException in case of XPATH failure.
	 */
	public Node one(final String expression, final Object context) throws XPathExpressionException {
		return (Node) xpath(expression).evaluate(context, XPathConstants.NODE);
	}	
	
	/**
	 * Evaluates a given XPATH and returns the result as a nodelist.
	 * 
	 * @param expression the XPATH expression.
	 * @param context the DOM context.
	 * @return the result as a nodelist.
	 * @throws XPathExpressionException in case of XPATH failure.
	 */
	public ImmutableNodeList many(final String expression, final Object context) throws XPathExpressionException {
		return new ImmutableNodeList((NodeList) xpath(expression).evaluate(context, XPathConstants.NODESET));
	}	
	
	/**
	 * Lazily creates, caches and returns an XPATH expression.
	 * 
	 * @param expression the XPATH expression as a string.
	 * @return a compiled XPATH expression.
	 * @throws XPathExpressionException in case of XPATH compilation failure.
	 */
	XPathExpression xpath(final String expression) throws XPathExpressionException {
		XPathExpression xpath = expressions.get().get(expression);
		if (xpath == null) {
			xpath = xpaths.get().compile(expression);
			expressions.get().put(expression, xpath);
		}
		return xpath;
	}
}