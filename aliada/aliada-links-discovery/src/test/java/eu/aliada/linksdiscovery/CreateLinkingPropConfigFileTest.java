// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortiums
package eu.aliada.linksdiscovery;

import eu.aliada.linksdiscovery.impl.LinksDiscovery;
import eu.aliada.linksdiscovery.model.DDBBParams;
import eu.aliada.shared.log.Log;

import org.junit.Test;

/**
 * Test {@link LinksDiscovery} class functions
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class CreateLinkingPropConfigFileTest {
	/** For Logging. */
	private static final Log LOGGER = new Log(CreateLinkingPropConfigFileTest.class);

    /**
     * @see
     * @since 1.0
     */
    @Test
    public void testCreateLinkingPropConfigFile() {
		LinksDiscovery linksDisc = new LinksDiscovery();
		String tmpDir = "src/test/resources/";
		DDBBParams ddbbParams = newDDBBParams();
		String result = linksDisc.createLinkingPropConfigFile(tmpDir, ddbbParams);
        if (result != null) {
            LOGGER.info("OK");
        } else {
            LOGGER.info("NOK");
        }
    }

	/**
	 * Creates a dummy DDBBParams structure. 
	 * 
	 * @return a dummy DDBBParams structure.
	 */
	public static DDBBParams newDDBBParams() {
		DDBBParams ddbbParams = new DDBBParams();
		ddbbParams.setUsername("username");
		ddbbParams.setPassword("password");
		ddbbParams.setDriverClassName("com.mysql.jdbc.Driver");
		ddbbParams.setUrl("jdbc:mysql://aliada:3306/aliada");
		return ddbbParams;
	}
}
