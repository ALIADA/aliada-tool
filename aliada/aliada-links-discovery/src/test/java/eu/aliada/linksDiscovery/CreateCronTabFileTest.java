// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortiums
package eu.aliada.linksDiscovery;

import eu.aliada.linksDiscovery.impl.LinksDiscovery;
import eu.aliada.shared.log.Log;
import org.junit.Test;

/**
 * Test {@link LinksDiscovery} class functions
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class CreateCronTabFileTest {
    private final Log log = new Log(CreateCronTabFileTest.class);

    /**
     * @see
     * @since 1.0
     */
    @Test
    public void testCreateCrontabFile() {
		LinksDiscovery linksDisc = new LinksDiscovery();
		String tmpDir = "src/test/resources/";
		String result = linksDisc.createCrontabFile(tmpDir);
        if (result != null) {
            log.info("OK");
        } else {
            log.info("NOK");
        }
    }
}
