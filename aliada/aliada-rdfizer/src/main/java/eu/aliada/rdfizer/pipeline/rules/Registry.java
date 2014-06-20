// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.Triple;

import com.google.common.collect.Iterators;

public class Registry implements Iterable<XPathBasedRule<List<Triple>>>{
	private final static Iterator<XPathBasedRule<List<Triple>>> EMPTY_ITERATOR = new ArrayList<XPathBasedRule<List<Triple>>>().iterator();
	
	private final XPathBasedRule<Node> subjectRule;
	
	private final List<XPathBasedRule<List<Triple>>> rules;
	
	public Registry(
			final XPathBasedRule<Node> subjectRule, 
			final List<XPathBasedRule<List<Triple>>> rules) {
		this.subjectRule = subjectRule;
		this.rules = rules;
	}
	
	@Override
	public Iterator<XPathBasedRule<List<Triple>>> iterator() {
		return rules != null ? rules.iterator() : EMPTY_ITERATOR;
	}	
	
	public XPathBasedRule<Node> getSubjectRule() {
		return subjectRule;
	}
}
