// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortiums
package eu.aliada.linksDiscovery;

import eu.aliada.linksDiscovery.impl.LinkingProcess;
import eu.aliada.shared.log.Log;
import junit.framework.TestCase;

/**
 * Test LinkingProcess class functions
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class GetConfigPropertiesTest extends TestCase {
    private final Log log = new Log(GetConfigPropertiesTest.class);

    /**
     * @see
     * @since 1.0
     */
    public void testGetConfigProperties() {
		LinkingProcess lProcess = new LinkingProcess();
		String propertiesFileName = "D:\\Proyectos\\023779_ALIADA\\Development\\aliada-links-discovery\\tmp\\linking1410253798499.properties";
		boolean result = lProcess.getConfigProperties(propertiesFileName);
        if (result) {
            log.info("OK");
        } else {
            log.info("NOK");
        }
    }
}
