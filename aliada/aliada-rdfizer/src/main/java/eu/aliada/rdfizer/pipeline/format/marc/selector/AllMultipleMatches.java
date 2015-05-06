// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.selector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A composite expression that selects the multiple not-null evaluation of a set of expressions.
 * 
 * @author Emiliano Cammilletti
 * @since 1.0
 * @param <K> the record kind.
 */
public class AllMultipleMatches<K> implements Expression<Map<String, List<String>>, K> {
	private final Expression<List<Node>, K> [] expressions;
	private final static String TAG = "TAG";
	private final static String INDICATORS = "INDICATORS";
	private final static String SUBFIELDS = "SUBFIELDS";
	private final static String XML_ATTRIBUTE_CODE= "code";
	private final static String XML_ATTRIBUTE_IND1= "ind1";
	
	
	/**
	 * Builds a new {@link AllMultipleMatches} with a given expressions chain.
	 * 
	 * @param expressions the expressions that form the execution chain.
	 */
	@SafeVarargs
	public AllMultipleMatches(final Expression<List<Node>, K> ... expressions) {
		this.expressions = expressions;
	}
	
	/**
	 * Find the values from a specified Expression and put the results into a
	 * map Object with the tag as key and a List as multiple values
	 * 
	 * (non-Javadoc)
	 * 
	 * @see eu.aliada.rdfizer.pipeline.format.marc.selector.Expression#evaluate(java.lang.Object)
	 */
	@Override
	public Map<String, List<String>> evaluate(final K target) {
		final Map<String, List<String>> result = new HashMap<String, List<String>>(
				expressions.length);
		for (final Expression<List<Node>, K> expression : expressions) {
			Map<String, Object> map = getMarcSpecs(expression.specs());
			String tag = (String) map.get(TAG);
			String[] indicators = (String[]) map.get(INDICATORS);
			String[] subfields = (String[]) map.get(SUBFIELDS);
			final Iterator<Node> iterNode = expression.evaluate(target)
					.iterator();
			while (iterNode.hasNext()) {
				Node node = iterNode.next();
				NamedNodeMap attributes = node.getAttributes();
			   if(attributes != null) {
				for (int k = 0; k < attributes.getLength(); k++) {
					Attr attr = (Attr) attributes.item(k);
					if (attr != null) {
						String attrName = attr.getNodeName();
						String attrValue = attr.getNodeValue();
						if ((attrName.equals(XML_ATTRIBUTE_IND1) && isAttributeValuePresent(
								attrValue, indicators)) || indicators[0].trim().length() == 0) {
							putValue(
									result,
									tag,
									append(node, subfields, new StringBuilder())
											.toString());
							break;
						}
					}
				}
			  } 
			}
		}
		return result;
	}
	
	/**
	 *  
	 * Recursive search of values into child nodes for attribute 'code' and configured subfields
	 *  
	 * @param node
	 * @param subfields
	 * @param builder
	 * @return
	 */
	private StringBuilder append(final Node node,final String[] subfields, final StringBuilder builder) {
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node actualNode = list.item(i);
			NamedNodeMap attributes = actualNode.getAttributes();
			if(attributes != null) {
			for (int k = 0; k < attributes.getLength(); k++) {
				Attr attr = (Attr) attributes.item(k);
				if(attr != null)
				{
					String attrName = attr.getNodeName();
					String attrValue = attr.getNodeValue();
					if (attrName.equals(XML_ATTRIBUTE_CODE) && isAttributeValuePresent(attrValue,subfields)) {
						builder.append(actualNode.getTextContent());
					}
				}
			}
			}
			
		}
		return builder;
	}
	/**
	 * Update the values of map Object.
	 * The method check for the value stored in the map ('key' as tag) after update or create the new value.
	 * 
	 * @param map
	 * @param tag
	 * @param value
	 */
	private void putValue(final Map<String, List<String>> map,final String tag,final String value) {
		List<String> list =  map.get(tag);
		if(list == null) {
			list = new ArrayList<String>();
		}
		list.add(value);
		map.put(tag, list);
	}
	
	/**
	 * Check if specified value is into an array
	 * 
	 * @param attrValue
	 * @param values
	 * @return
	 */
	private boolean isAttributeValuePresent(final String attrValue,
			final String[] values) {
		boolean isPresent = false;
		if (attrValue != null) {
			for (int i = 0; i < values.length; i++) {
				if (attrValue.equals(values[i])) {
					isPresent = true;
				}
			}
		}
		return isPresent;
	}
	/**
	 * Store the values for tag,indicators and subfields into an object map. 
	 * 
	 * @param specs
	 * @return
	 */
	public Map<String,Object> getMarcSpecs(final String specs)
	{
		int indexOfOpeningParenthesis = specs.indexOf("(");
		int indexOfClosingParenthesis = specs.indexOf(")", indexOfOpeningParenthesis);
		String tag = specs.substring(0, indexOfOpeningParenthesis).trim();
		String[] indicators = specs.substring(indexOfOpeningParenthesis + 1, indexOfClosingParenthesis).trim().split("-");
		String[] subfields = specs.substring(indexOfClosingParenthesis + 1, specs.length()).split("-");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(TAG, tag);
		map.put(INDICATORS, indicators);
		map.put(SUBFIELDS, subfields);
		
		return map;
	}

	
	@Override
	public String specs() {
		throw new UnsupportedOperationException();
	}
}