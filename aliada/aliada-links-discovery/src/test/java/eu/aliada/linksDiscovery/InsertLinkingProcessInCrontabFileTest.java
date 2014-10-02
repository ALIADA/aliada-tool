// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortiums
package eu.aliada.linksDiscovery;

import java.util.Random;

import eu.aliada.linksDiscovery.impl.LinksDiscovery;
import eu.aliada.shared.log.Log;
import org.junit.Test;

/**
 * Test {@link LinksDiscovery} class functions
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class InsertLinkingProcessInCrontabFileTest {
	static final Random RANDOMIZER = new Random();
    private final Log log = new Log(InsertLinkingProcessInCrontabFileTest.class);

    /**
     * @see
     * @since 1.0
     */
    @Test
    public void testInsertLinkingProcessInCrontabFile() {
		LinksDiscovery linksDisc = new LinksDiscovery();
		String crontabFilename = "src/test/resources/aliada_links_discovery.cron";
		String clientAppBinDir = randomString();
		int jobId = randomIdentifier();
		int subjobId = randomIdentifier();
		String linkingPropConfigFilename = randomString();
		boolean result = linksDisc.insertLinkingProcessInCrontabFile(crontabFilename, clientAppBinDir, jobId, subjobId, linkingPropConfigFilename);
        if (result) {
            log.info("OK");
        } else {
            log.info("NOK");
        }
    }

    /**
	 * Returns a random identifier (as integer).
	 * 
	 * @return a random identifier (as integer).
	 */
	public static Integer randomIdentifier() {
		return RANDOMIZER.nextInt();
	}
    

	/**
	 * Returns a random string.
	 * 
	 * @return a random string.
	 */
	public static String randomString() {
		return String.valueOf(RANDOMIZER.nextLong());
	}
	
}
