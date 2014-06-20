// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortium
package eu.aliada.rdfizer.pipeline.rules;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.Triple;
import org.w3c.dom.Document;

import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * Supertype layer for all XPATH based rules.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 * @param <R> the rule evaluation result kind.
 */
public abstract class XPathBasedRule<R> {
	protected static final XPathFactory FACTORY = XPathFactory.newInstance();
	
	protected final Log log = new Log(getClass());
	protected static final Log LOGGER = new Log(XPathBasedRule.class);
	
	protected final XPathExpression expression;
	
	/**
	 * Compiles a given XPATH expression.
	 * 
	 * @param expression the XPATH expression as string.
	 * @return the XPATH expression
	 */
	protected static XPathExpression compile(final String expression) {
		try {
			return XPathFactory.newInstance().newXPath().compile(expression);
		} catch (XPathExpressionException exception) {
			LOGGER.error(MessageCatalog._00035_XPATH_COMPILATION_FAILURE, exception, expression);
			throw new IllegalArgumentException(exception);
		}
	}
	
	/**
	 * Builds a new rule with the given (XPATH) expression.
	 * 
	 * @param expression the XPath expression.
	 */
	public XPathBasedRule(final String expression) {
		this.expression = compile(expression);
	}
	
	/**
	 * Returns the return type of the XPATH evaluation.
	 * Although not elegant, this is the only way to make dynamic this kind of information: return types of 
	 * an XPath evalutaion don't share any supertype layer so there's no way to indicate an abstract supertype.
	 * 
	 * @return the returns type of the XPATH evaluation.
	 */
	public abstract QName xpathEvaluationKind();
	
	/**
	 * Executes this (conversion) rule.
	 * 
	 * @param topLevelSubject this is the main subject previously detected by another specific rule.
	 * @param document the current record (as XML document).
	 * @param accumulator the result (triples) accumulator.
	 * @return the result of the evaluation.
	 * @throws XPathExpressionException in case the XPATH evaluation fails.
	 */
	public final R execute(
			final Node topLevelSubject, 
			final Document document, 
			final List<Triple> accumulator) throws XPathExpressionException {
		final Object evaluation = expression.evaluate(document, xpathEvaluationKind());
		return toTriples(topLevelSubject, evaluation, accumulator);
	}
	
	/**
	 * This is where the magic happens.
	 * Each concrete rule must define here the conversion job.
	 * 
	 * @param topLevelSubject this is the main subject previously detected by another specific rule.
	 * @param evaluation the XPATH evaluation.
	 * @param accumulator the triples list, acting as accumulator.
	 * @return the result of the evaluation.
	 */
	protected abstract R toTriples(Node topLevelSubject, Object evaluation, List<Triple> accumulator);
}