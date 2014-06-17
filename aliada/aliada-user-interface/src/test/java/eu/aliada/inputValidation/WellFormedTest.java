// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.inputValidation;

import eu.aliada.shared.log.Log;
import junit.framework.TestCase;

/**
 * @author elena
 * @since 1.0
 */
public class WellFormedTest extends TestCase{
    private final Log log = new Log(WellFormedTest.class);
    /**
     * @see
     * @since 1.0
     */
    public void testWellFormed() {
        WellFormed wf = new WellFormed();
        //LIDO
        //boolean result = wf.isWellFormedXML("src/test/resources/lido/lido_elGreco.xml");
        //MARC
        boolean result = wf.isWellFormedXML("src/test/resources/marc/marc_comic.xml");
        if (result) {
            log.info("BIEN");
        } else {
            log.info("MAL");
        }
    }
}
