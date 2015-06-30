// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery-application-client
// Responsible: ALIADA Consortium
package eu.aliada.linksdiscovery;

import eu.aliada.linksdiscovery.impl.LinkingProcess;
import eu.aliada.shared.log.Log;
import org.junit.Test;

/**
 * Test {@link LinkingProcess} class functions
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class GetConfigPropertiesTest {
	/** For logging. */
    private final Log log = new Log(GetConfigPropertiesTest.class);

    /**
     * @see
     * @since 1.0
     */
    @Test
    public void testGetConfigProperties() {
		final LinkingProcess lProcess = new LinkingProcess();
		final String propertiesFileName = "src/test/resources/linking.properties";
		final boolean result = lProcess.getConfigProperties(propertiesFileName);
        if (result) {
            log.info("OK");
        } else {
            log.info("NOK");
        }
    }
}
