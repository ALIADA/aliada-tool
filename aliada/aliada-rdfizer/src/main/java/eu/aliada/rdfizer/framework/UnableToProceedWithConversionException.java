// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.framework;

import javax.xml.xpath.XPathExpressionException;

/**
 * Marker exception thrown in case we are not able to proceeed with the conversion.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class UnableToProceedWithConversionException extends Exception {
	
	private static final long serialVersionUID = -2782533122869719359L;
	
	/**
	 * Builds a new exception with the given message.
	 * 
	 * @param message the exception message.
	 */
	public UnableToProceedWithConversionException(final String message) {
		super(message);
	}

	/**
	 * Builds a new exception with the given cause.
	 * 
	 * @param cause the exception cause.
	 */
	public UnableToProceedWithConversionException(final XPathExpressionException cause) {
		super(cause);
	}
}