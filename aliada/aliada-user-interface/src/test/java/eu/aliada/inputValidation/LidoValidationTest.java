// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.inputValidation;

import junit.framework.TestCase;

/**
 * @author elena
 * @since 1.0
 */
public class LidoValidationTest extends TestCase {

    /**
     * @see
     * @since 1.0
     */
    public void testValidate() {
        LidoValidation lv = new LidoValidation();
        boolean result = lv.validateLidoFile("src/test/resources/Lido_MFBA.xml");
    }
    
}
