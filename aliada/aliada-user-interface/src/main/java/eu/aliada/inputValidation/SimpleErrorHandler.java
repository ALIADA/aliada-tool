// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.inputValidation;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

/**
 * Implementation of an error handler to avoid unexpected bugs.
 * @author elena
 * @since 1.0
 */
public class SimpleErrorHandler implements ErrorHandler {
    
    private String myError;
  
  

    /**
     * @param e
     *            the exception
     * @see
     * @since 1.0
     */
    public void warning(final SAXParseException e) {
        setMyError(e.getMessage());
    }

    /**
     * @param e
     *            the exception
     * @see
     * @since 1.0
     */
    public void error(final SAXParseException e) {
        setMyError(e.getMessage());
    }

    /**
     * @param e
     *            the exception
     * @see
     * @since 1.0
     */
    public void fatalError(final SAXParseException e) {
        setMyError(e.getMessage());
    }

    /**
     * @return Returns the myError.
     * @exception
     * @since 1.0
     */
    public String getMyError() {
        return myError;
    }

    /**
     * @param myError The myError to set.
     * @exception
     * @since 1.0
     */
    public void setMyError(final String myError) {
        this.myError = myError;
    }

}
