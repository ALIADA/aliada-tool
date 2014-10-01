// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortiums
package eu.aliada.linksDiscovery;

import eu.aliada.linksDiscovery.impl.LinksDiscovery;
import eu.aliada.linksDiscovery.model.SubjobConfiguration;
import eu.aliada.shared.log.Log;
import junit.framework.TestCase;

/**
 * Test LinksDiscovery class functions
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class GetLinkingConfigFilesTest extends TestCase {
    private final Log log = new Log(GetLinkingConfigFilesTest.class);

    /**
     * @see
     * @since 1.0
     */
    public void testGetLinkingConfigFiles() {
		LinksDiscovery linksDisc = new LinksDiscovery();
		String propertiesFileName = "src/test/resources/linksdiscovery.properties";
		SubjobConfiguration[] result = linksDisc.getLinkingConfigFiles(propertiesFileName);
        if (result.length > 0) {
            log.info("OK");
        } else {
            log.info("NOK");
        }
    }
}
