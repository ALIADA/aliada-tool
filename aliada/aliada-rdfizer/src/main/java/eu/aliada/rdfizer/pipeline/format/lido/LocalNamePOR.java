// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortium
package eu.aliada.rdfizer.pipeline.format.lido;

import org.semanticweb.yars.nx.Resource;
import static eu.aliada.shared.Strings.toURILocalName;

/**
 * A rule for creating 2 entities starting from a given local name.
 * This basically acts in the same way as {@link SPOR} with the difference that the 
 * evaluation of the XPATH in {@link SPOR} is a URI while here we have just a literal that will
 * be used as local name. As consequence of that, this rule needs an additional paramter which
 * represents the prefix to use for building the whole URI.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class LocalNamePOR extends SPOR {	
	private final String prefix;
	
	/**
	 * Builds a rule with the given data.
	 * 
	 * @param expression the XPATH expression.
	 * @param prefix the prefix (i.e. namespace) that will be used for building the dependent entity URI.
	 * @param p the predicate that will be used for building the triple.
	 * @param o the object that will be used for building the triple.
	 * @param r the relation predicate bewtween main subject and the new built entity.
	 */
	public LocalNamePOR(final String expression, final String prefix, final String p, final String o, final String r) {
		super(expression, p, o, r);
		this.prefix = prefix;
	}
	
	@Override
	protected Resource s(final String localName) {
		return new Resource(prefix + toURILocalName(localName));
	}	
}