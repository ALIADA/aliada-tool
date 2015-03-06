// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.xml;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

import com.google.common.collect.AbstractIterator;
import com.hp.hpl.jena.sparql.sse.writers.WriterBasePrefix.Fmt;
import com.hp.hpl.jena.sparql.util.FmtUtils;

/**
 * A {@link} adapter for {@link Node}. This class simply decorates a
 * {@link Node} adding *only* the escape behaviour for textContent method.
 * 
 * @author Emiliano Cammilletti
 * @since 1.0
 */
public class ImmutableNode implements Node {

	private final Node wrapped;

	/**
	 * Buidls an immutable wrapper.
	 * 
	 * @param wrapped
	 *            the node.
	 */
	ImmutableNode(final Node wrapped) {
		this.wrapped = wrapped;
	}

	public String getNodeName() {
		return wrapped.getNodeName();
	}

	public String getNodeValue() throws DOMException {
		return wrapped.getNodeValue();
	}

	public void setNodeValue(String nodeValue) throws DOMException {
		wrapped.setNodeValue(nodeValue);
	}

	public short getNodeType() {
		return wrapped.getNodeType();
	}

	public Node getParentNode() {
		return wrapped.getParentNode();
	}

	public NodeList getChildNodes() {
		return wrapped.getChildNodes();
	}

	public Node getFirstChild() {
		return wrapped.getFirstChild();
	}

	public Node getLastChild() {
		return wrapped.getLastChild();
	}

	public Node getPreviousSibling() {
		return wrapped.getPreviousSibling();
	}

	public Node getNextSibling() {
		return wrapped.getNextSibling();
	}

	public NamedNodeMap getAttributes() {
		return wrapped.getAttributes();
	}

	public Document getOwnerDocument() {
		return wrapped.getOwnerDocument();
	}

	public Node insertBefore(Node newChild, Node refChild) throws DOMException {
		return wrapped.insertBefore(newChild, refChild);
	}

	public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
		return wrapped.replaceChild(newChild, oldChild);
	}

	public Node removeChild(Node oldChild) throws DOMException {
		return wrapped.removeChild(oldChild);
	}

	public Node appendChild(Node newChild) throws DOMException {
		return wrapped.appendChild(newChild);
	}

	public boolean hasChildNodes() {
		return wrapped.hasChildNodes();
	}

	public Node cloneNode(boolean deep) {
		return wrapped.cloneNode(deep);
	}

	public void normalize() {
		wrapped.normalize();
	}

	public boolean isSupported(String feature, String version) {
		return wrapped.isSupported(feature, version);
	}

	public String getNamespaceURI() {
		return wrapped.getNamespaceURI();
	}

	public String getPrefix() {
		return wrapped.getPrefix();
	}

	public void setPrefix(String prefix) throws DOMException {
		wrapped.setPrefix(prefix);
	}

	public String getLocalName() {
		return wrapped.getLocalName();
	}

	public boolean hasAttributes() {
		return wrapped.hasAttributes();
	}

	public String getBaseURI() {
		return wrapped.getBaseURI();
	}

	public short compareDocumentPosition(Node other) throws DOMException {
		return wrapped.compareDocumentPosition(other);
	}

	public String getTextContent() throws DOMException {
		return wrapped != null ? FmtUtils.stringEsc(wrapped.getTextContent()) : null;
	}

	public void setTextContent(String textContent) throws DOMException {
		wrapped.setTextContent(textContent);
	}

	public boolean isSameNode(Node other) {
		return wrapped.isSameNode(other);
	}

	public String lookupPrefix(String namespaceURI) {
		return wrapped.lookupPrefix(namespaceURI);
	}

	public boolean isDefaultNamespace(String namespaceURI) {
		return wrapped.isDefaultNamespace(namespaceURI);
	}

	public String lookupNamespaceURI(String prefix) {
		return wrapped.lookupNamespaceURI(prefix);
	}

	public boolean isEqualNode(Node arg) {
		return wrapped.isEqualNode(arg);
	}

	public Object getFeature(String feature, String version) {
		return wrapped.getFeature(feature, version);
	}

	public Object setUserData(String key, Object data, UserDataHandler handler) {
		return wrapped.setUserData(key, data, handler);
	}

	public Object getUserData(String key) {
		return wrapped.getUserData(key);
	}

}