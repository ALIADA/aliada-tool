// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortium
package eu.aliada.rdfizer.pipeline.format.xml;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * ALIADA O(ptimized)XPath tool.
 * It only supports few XPATH expressions (those needed for the conversion job).
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@Component
public class OXPath {
	
	private final static ImmutableNodeList EMPTY_LIST = new ImmutableNodeList(new MutableNodeList());
	
	final ThreadLocal<Map<String, XPathExpression>> expressions = new ThreadLocal<Map<String, XPathExpression>>() {
		protected Map<String, XPathExpression> initialValue() {
			return new WeakHashMap<String, XPathExpression>();
		};
	};
	
	final ThreadLocal<javax.xml.xpath.XPath> xpaths = new ThreadLocal<javax.xml.xpath.XPath>() {
		protected javax.xml.xpath.XPath initialValue() {
			
			return xpathfactory.newXPath();
		};
	};
	
	/**
	 * A Mutable {@link NodeList}.
	 * 
	 * @author Andrea Gazzarini
	 * @since 2.0
	 */
	public static class MutableNodeList implements NodeList {

		final List<Node> nodes;
		
		/**
		 * Builds a new {@link MutableNodeList} with the given nodes.
		 * 
		 * @param nodes the nodes.
		 */
		public MutableNodeList(final List<Node> nodes) {
			this.nodes = nodes;
		}
		
		/**
		 * 
		 */
		public MutableNodeList() {
			this.nodes = new ArrayList<Node>();
		}
		
		public void addNode(Node node) {
			nodes.add(node);
		}
		
		@Override
		public Node item(int index) {
			return nodes.get(index);
		}

		@Override
		public int getLength() {
			return nodes.size();
		}
	}
	
	/**
	 * Micro-optimization: avoid XPATH for simple expressions
	 * 
	 * @author Andrea Gazzarini
	 * @since 2.0
	 */
	public static class SimpleXPathExpression implements XPathExpression {
		private final String targetChild;
		private final String matchingField;
		
		/**
		 * Builds a simple expression with the given target.
		 * 
		 * @param targetChild the target node.
		 */
		public SimpleXPathExpression(final String targetChild) {
			this.targetChild = targetChild;
			int indexOfSquare = targetChild.indexOf("[");
			this.matchingField = indexOfSquare == -1 ? targetChild : targetChild.substring(0, indexOfSquare);
		}
		
		@Override
		public Object evaluate(final Object item, final QName returnType) {
			int indexOfSquareBracket = targetChild.indexOf("[");
			if (returnType == XPathConstants.STRING) {
				if (targetChild.startsWith("@")) {
					return ((Element) item).getAttribute(targetChild.substring(1));
				}
				final NodeList list = ((Element) item).getElementsByTagName("*");
				for (int i = 0; i < list.getLength(); i++) {
					final Element element = (Element)list.item(i);
					if (element.getNodeName().endsWith(matchingField)) {
						if (indexOfSquareBracket == -1) {
							return element.getTextContent();
						} else {
							final String name = targetChild.substring(indexOfSquareBracket + 2, targetChild.indexOf("="));
							int valueStartIndex = targetChild.indexOf("'");
							final String value = targetChild.substring(valueStartIndex+1, targetChild.lastIndexOf("'"));
							if (element.getAttribute(name).equals(value)) {
								return element.getTextContent();
							}
						}
					}
				}
				return (list != null && list.getLength() > 0) ? list.item(0).getTextContent() : null;
			} else if (returnType == XPathConstants.NODE) {
				final NodeList list = ((Element) item).getElementsByTagName("*");
				for (int i = 0; i < list.getLength(); i++) {
					Element element = (Element)list.item(i);
					if (element.getNodeName().endsWith(matchingField)) {
						if (indexOfSquareBracket == -1) {
							return element.getTextContent();
						} else {
							final String name = targetChild.substring(indexOfSquareBracket + 2, targetChild.indexOf("="));
							int valueStartIndex = targetChild.indexOf("'");
							final String value = targetChild.substring(valueStartIndex+1, targetChild.lastIndexOf("'"));
							if (element.getAttribute(name).equals(value)) {
								return element;
							}
						}
					}
				}
				return null;
			} else if (returnType == XPathConstants.NODESET) {
				final NodeList list = ((Element) item).getChildNodes();
				if (list.getLength() == 0) return list; 
				MutableNodeList result = new MutableNodeList();		
				for (int i = 0; i < list.getLength(); i++) {
					Node n = list.item(i);
					if (n.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element)n;
						if (element.getNodeName().endsWith(matchingField)) {
							if (indexOfSquareBracket == -1) {
								result.addNode(element);
							} else {
								final String name = targetChild.substring(indexOfSquareBracket + 2, targetChild.indexOf("="));
								int valueStartIndex = targetChild.indexOf("'");
								final String value = targetChild.substring(valueStartIndex+1, targetChild.lastIndexOf("'"));
								if (element.getAttribute(name).equals(value)) {
									result.addNode(element);
								}
							}
						}
					}
				}
				return result;
			}
			throw new IllegalArgumentException("Unsupported QName Type : " + returnType);
		}

		@Override
		public String evaluate(final Object item) {
			return (String) evaluate(item, XPathConstants.STRING);
		}

		@Override
		public Object evaluate(final InputSource source, final QName returnType) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String evaluate(final InputSource source) {
			throw new UnsupportedOperationException();
		}
	}	
	
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
		final Element doc = (Element) (context instanceof Document ? ((Document)context).getDocumentElement() : context);
		String [] members = expression.split("/");
		List<Node> current = null;
		String firstExp = members[0];
		final XPathExpression exp = xpath(firstExp);
		NodeList topLevel = (NodeList) exp.evaluate(doc, XPathConstants.NODESET);
		if (members.length == 1) {
			current = new ArrayList<Node>();
			for (int i = 0; i < topLevel.getLength(); i++) {
				current.add(topLevel.item(i));
			}
		} else {
			for (int i = 1; i < members.length; i++) {
				current = (i==1) ? select(topLevel, members[i]) : select(current, members[i]);
			}
		}
		
		return (current != null && current.size() > 0) ? current.get(0).getTextContent() : "";
	}	
		
	private List<Node> select(NodeList list, String child) throws XPathExpressionException {
		List<Node> nodes = new ArrayList<Node>();
		for (int x = 0; x < list.getLength(); x++) {
			final XPathExpression exp = xpath(child);
			NodeList result = (NodeList) exp.evaluate(list.item(x), XPathConstants.NODESET);
			if (result.getLength() > 0) {
				for (int i = 0; i < result.getLength(); i++) {
					nodes.add(result.item(i));
				}
			}
		}		
		return nodes;
	}
	
	private List<Node> select(List<Node> list, String child) throws XPathExpressionException {
		List<Node> nodes = new ArrayList<Node>();
		for (int x = 0; x < list.size(); x++) {
			final XPathExpression exp = xpath(child);
			NodeList result = (NodeList) exp.evaluate(list.get(x), XPathConstants.NODESET);
			if (result.getLength() > 0) {
				for (int i = 0; i < result.getLength(); i++) {
					nodes.add(result.item(i));
				}
			}
		}		
		return nodes;
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
		Element doc = (Element) (context instanceof Document ? ((Document)context).getDocumentElement() : context);
		String [] members = expression.split("/");
		List<Node> current = null;
		String firstExp = members[0];
		final XPathExpression exp = xpath(firstExp);
		NodeList topLevel = (NodeList) exp.evaluate(doc, XPathConstants.NODESET);
		if (members.length == 1) {
			current = new ArrayList<Node>();
			for (int i = 0; i < topLevel.getLength(); i++) {
				current.add(topLevel.item(i));
			}
		} else {
			for (int i = 1; i < members.length; i++) {
				current = (i==1) ? select(topLevel, members[i]) : select(current, members[i]);
			}
		}
		
		return (current != null && current.size() > 0) ? current.get(0) : null;
	}	
	
	/**
	 * Evaluates a given XPATH and returns the result as a nodelist.
	 * 
	 * @param expression the XPATH expression.
	 * @param context the DOM context.
	 * @return the result as a nodelist.
	 * @throws XPathExpressionException in case of XPATH failure.
	 */
	public List<Node> many(final String expression, final Object context) throws XPathExpressionException {
		Element doc = (Element) (context instanceof Document ? ((Document)context).getDocumentElement() : context);
		String [] members = expression.split("/");
		List<Node> current = null;
		String firstExp = members[0];
		final XPathExpression exp = xpath(firstExp);
		NodeList topLevel = (NodeList) exp.evaluate(doc, XPathConstants.NODESET);
		if (members.length == 1) {
			current = new ArrayList<Node>();
			for (int i = 0; i < topLevel.getLength(); i++) {
				current.add(topLevel.item(i));
			}
		} else {
			for (int i = 1; i < members.length; i++) {
				current = (i==1) ? select(topLevel, members[i]) : select(current, members[i]);
			}
		}
		
		return (current != null) ? current : EMPTY_LIST;
	}	

	/**
	 * Returns the value of the control field associated with the given tag.
	 * 
	 * @param tag the tag name / number.
	 * @param record the (MARC) record.
	 * @return the value of the requested control field, null in case there's no such control field.
	 * @throws XPathExpressionException in case of XPATH compilation failure.
	 */
	public String cf(final String tag, final Object record) throws XPathExpressionException {
		final String xpath = new StringBuilder("controlfield[@tag='")
			.append(tag)
			.append("']")
			.toString();
		return value(xpath, record);
	}
	
	/**
	 * Returns the value of the data field associated with the given tag.
	 * 
	 * @param tag the tag name / number.
	 * @param record the (MARC) record.
	 * @param code the subfield code.
	 * @return the value of the requested control field, null in case there's no such control field.
	 * @throws XPathExpressionException in case of XPATH compilation failure.
	 */
	public Node df(final String tag, final String code, final Object record) throws XPathExpressionException {
		final String xpath = new StringBuilder("datafield[@tag='")
			.append(tag)
			.append("']/subfield[@code='")
			.append(code)
			.append("']")
			.toString();
		return one(xpath, record);
	}
	
	/**
	 * Returns the value of the data field associated with the given tag.
	 * 
	 * @param tag the tag name / number.
	 * @param record the (MARC) record.
	 * @param code the subfield code.
	 * @return the value of the requested control field, null in case there's no such control field.
	 * @throws XPathExpressionException in case of XPATH compilation failure.
	 */
	public List<Node> dfs(final String tag, final String code, final Object record) throws XPathExpressionException {
		final String xpath = new StringBuilder("datafield[@tag='")
			.append(tag)
			.append("']/subfield[@code='")
			.append(code)
			.append("']")
			.toString();
		return many(xpath, record);
	}	
	
	/**
	 * Lazily creates, caches and returns an XPATH expression.
	 * 
	 * @param expression the XPATH expression as a string.
	 * @return a compiled XPATH expression.
	 * @throws XPathExpressionException in case of XPATH compilation failure.
	 */
	XPathExpression xpath(final String expression) throws XPathExpressionException {
		XPathExpression xpathExpression = expressions.get().get(expression);
		if (xpathExpression == null) {		
			if (expression.indexOf("/") == -1) {
				xpathExpression = new SimpleXPathExpression(expression);
			} else {		
				xpathExpression = xpaths.get().compile(expression);
			}
			expressions.get().put(expression, xpathExpression);
		}
		return xpathExpression;
	}
}