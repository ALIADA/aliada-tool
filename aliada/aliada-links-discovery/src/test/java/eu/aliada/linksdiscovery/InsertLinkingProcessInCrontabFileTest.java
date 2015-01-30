// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortiums
package eu.aliada.linksdiscovery;

import java.util.Random;

import eu.aliada.linksdiscovery.impl.LinksDiscovery;
import eu.aliada.shared.log.Log;

import org.junit.Test;

/**
 * Test {@link LinksDiscovery} class functions
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class InsertLinkingProcessInCrontabFileTest {
	/** For creating random variable values. */
	static final Random RANDOMIZER = new Random();
	/** For logging. */
	private static final Log LOGGER = new Log(InsertLinkingProcessInCrontabFileTest.class);
	/** Crontab file path.*/
	private static final String CRONTAB_FILE_NAME = "src/test/resources/aliada_links_discovery.cron";

    /**
     * @see
     * @since 1.0
     */
    @Test
    public void testInsertLinkingProcessInCrontabFile() {
		final LinksDiscovery linksDisc = new LinksDiscovery();
		final String clientAppBinDir = randomString();
		final int jobId = randomIdentifier();
		final int subjobId = randomIdentifier();
		final String linkingPropConfigFilename = randomString();
		final boolean reloadTarget = false;
		final boolean result = linksDisc.insertLinkingProcessInCrontabFile(CRONTAB_FILE_NAME, clientAppBinDir, jobId, subjobId, linkingPropConfigFilename, reloadTarget);
        if (result) {
        	LOGGER.info("OK");
        } else {
        	LOGGER.info("NOK");
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
