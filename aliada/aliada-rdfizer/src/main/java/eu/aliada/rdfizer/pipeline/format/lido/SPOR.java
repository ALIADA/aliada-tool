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
import org.w3c.dom.NodeList;

import eu.aliada.rdfizer.pipeline.rules.XPathBasedRule;

/**
 * A rule for creating 2 entities starting from a given URI (i.e the result of the XPATH expression evaluation).
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class SPOR extends XPathBasedRule<List<Triple>> {	
	protected final Resource p;
	protected final Resource o;
	protected final Resource r;
	
	/**
	 * Builds a new subject detection rule with the given data.
	 * 
	 * @param expression the XPATH expression.
	 * @param p the predicate that will be used for building the triple.
	 * @param o the object that will be used for building the triple.
	 * @param r the relation predicate bewtween main subject and the new built entity.
	 */
	public SPOR(final String expression, final String p, final String o, final String r) {
		super(expression);
		this.p = new Resource(p);
		this.o = new Resource(o);
		this.r = new Resource(r);
	}

	@Override
	public QName xpathEvaluationKind() {
		return XPathConstants.NODESET;
	}

	@Override
	protected List<Triple> toTriples(
			final Node topLevelSubject, 
			final Object evaluation,
			final List<Triple> accumulator) {
		
		final NodeList nodes = (NodeList) evaluation;
		if (nodes != null && nodes.getLength() != 0) {
			for (int index = 0; index < nodes.getLength(); index++) {
				final Resource s = s(nodes.item(index).getTextContent());
				accumulator.add(new Triple(s, p, o));
				accumulator.add(new Triple(topLevelSubject, r, s));			
			}
		}		
		return accumulator;
	}
	
	/**
	 * Returns the URI of the dependent entity.
	 *  
	 * @param evaluation the result of the XPATH evaluation.
	 * @return the URI of the dependent entity.
	 */
	protected Resource s(final String evaluation) {
		return new Resource(evaluation);
	}
}