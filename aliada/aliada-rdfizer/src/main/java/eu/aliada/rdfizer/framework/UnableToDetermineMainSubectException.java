// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.framework;

import javax.xml.xpath.XPathExpressionException;

/**
 * Marker exception thrown in case we are not able to determine a main subject from a given record.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class UnableToDetermineMainSubectException extends Exception {
	
	private static final long serialVersionUID = -2782533122869719359L;
	
	public UnableToDetermineMainSubectException(final String message) {
		super(message);
	}

	public UnableToDetermineMainSubectException(XPathExpressionException exception) {
		super(exception);
	}
}