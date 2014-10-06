// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortiums
package eu.aliada.linksdiscovery;

import eu.aliada.linksdiscovery.impl.LinksDiscovery;
import eu.aliada.linksdiscovery.model.SubjobConfiguration;
import eu.aliada.shared.log.Log;

import org.junit.Test;

/**
 * Test {@link LinksDiscovery} class functions
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class GetLinkingConfigFilesTest {
	/** For logging. */
	private static final Log LOGGER = new Log(GetLinkingConfigFilesTest.class);
	/** Properties file path. */
	private static final String PROPS_FILE_NAME = "src/test/resources/linksdiscovery.properties";

    /**
     * @see
     * @since 1.0
     */
    @Test
    public void testGetLinkingConfigFiles() {
		final LinksDiscovery linksDisc = new LinksDiscovery();
		final SubjobConfiguration[] result = linksDisc.getLinkingConfigFiles(PROPS_FILE_NAME);
        if (result.length > 0) {
        	LOGGER.info("OK");
        } else {
        	LOGGER.info("NOK");
        }
    }
}
