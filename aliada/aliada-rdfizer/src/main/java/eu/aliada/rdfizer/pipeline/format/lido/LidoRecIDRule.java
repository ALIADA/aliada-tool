// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortium
package eu.aliada.rdfizer.pipeline.format.lido;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;

import org.semanticweb.yars.nx.Literal;
import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.Resource;
import org.semanticweb.yars.nx.Triple;
import org.semanticweb.yars.nx.namespace.RDF;

import eu.aliada.rdfizer.pipeline.rules.XPathBasedRule;
import eu.aliada.shared.ID;

/**
 * A rule for detecting the main subject from a LIDO record.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
// TODO: HARDCODED URIs
public class LidoRecIDRule extends XPathBasedRule<List<Triple>> {
	
	private static final Resource E31_DOCUMENT = new Resource("http://erlangen-crm.org/current/E31_Document");
	private static final Resource E41_APPELLATION = new Resource("http://erlangen-crm.org/current/E41_Appellation");
	private static final Resource P3_HAS_NOTE = new Resource("http://erlangen-crm.org/current/P3_has_note");
	private static final Resource P1_IS_IDENTIFIED_BY = new Resource("http://erlangen-crm.org/current/P1_is_identified_by");
	private static final Resource P70I_IS_DOCUMENTED_IN = new Resource("http://erlangen-crm.org/current/P70i_is_documented_in");
	
	static final Map<String, String> CIDOC_TO_ALIADA = new HashMap<String, String>();
	static {
		CIDOC_TO_ALIADA.put("http://www.cidoc-crm.org/crm-concepts/E22", "http://erlangen-crm.org/current/E22_Man-Made_Object");
		CIDOC_TO_ALIADA.put("http://www.cidoc-crm.org/crm-concepts/E25", "http://erlangen-crm.org/current/E25_Man-Made_Feature");
		CIDOC_TO_ALIADA.put("http://www.cidoc-crm.org/crm-concepts/E78", "http://erlangen-crm.org/current/E78_Collection");
	}
	
	/**
	 * Builds a new subject detection rule with the given XPATH expression.
	 * 
	 * @param expression the XPATH expression.
	 */
	public LidoRecIDRule(final String expression) {
		super(expression);
	}

	@Override
	public QName xpathEvaluationKind() {
		return XPathConstants.STRING;
	}

	@Override
	protected List<Triple> toTriples(
			final Node topLevelSubject, 
			final Object evaluation,
			final List<Triple> accumulator) {
		final Resource appellation = new Resource("http://www.szepmuveszeti.hu/id/resource/E41_Appellation/" + ID.get());	
		accumulator.add(new Triple(appellation, RDF.TYPE, E41_APPELLATION));
		accumulator.add(new Triple(appellation, P3_HAS_NOTE, new Literal(String.valueOf(evaluation))));
		
		final Resource document = new Resource("http://www.szepmuveszeti.hu/id/resource/E31_Document/" + ID.get());
		accumulator.add(new Triple(document, RDF.TYPE, E31_DOCUMENT));
		accumulator.add(new Triple(document, P1_IS_IDENTIFIED_BY, appellation));
		
		accumulator.add(new Triple(topLevelSubject, P70I_IS_DOCUMENTED_IN, document));
		return accumulator;
	}
}