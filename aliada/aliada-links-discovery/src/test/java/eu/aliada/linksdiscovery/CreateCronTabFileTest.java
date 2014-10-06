// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortiums
package eu.aliada.linksdiscovery;

import eu.aliada.linksdiscovery.impl.LinksDiscovery;
import eu.aliada.shared.log.Log;

import org.junit.Test;

/**
 * Test {@link LinksDiscovery} class functions
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class CreateCronTabFileTest {
	/** For logging. */
	private static final Log LOGGER = new Log(CreateCronTabFileTest.class);
	/** Temporary folder path. */
	private static final String TMP_DIR = "src/test/resources/";
    /**
     * @see
     * @since 1.0
     */
    @Test
    public void testCreateCrontabFile() {
		final LinksDiscovery linksDisc = new LinksDiscovery();
		final String result = linksDisc.createCrontabFile(TMP_DIR);
        if (result != null) {
        	LOGGER.info("OK");
        } else {
        	LOGGER.info("NOK");
        }
    }
}
