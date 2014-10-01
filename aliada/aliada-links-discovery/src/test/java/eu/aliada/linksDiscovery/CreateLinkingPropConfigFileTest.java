// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortiums
package eu.aliada.linksDiscovery;

import eu.aliada.linksDiscovery.impl.LinksDiscovery;
import eu.aliada.linksDiscovery.model.DDBBParams;
import eu.aliada.shared.log.Log;
import junit.framework.TestCase;

/**
 * Test LinksDiscovery class functions
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class CreateLinkingPropConfigFileTest extends TestCase {
    private final Log log = new Log(CreateLinkingPropConfigFileTest.class);

    /**
     * @see
     * @since 1.0
     */
    public void testCreateLinkingPropConfigFile() {
		LinksDiscovery linksDisc = new LinksDiscovery();
		String tmpDir = "D:\\Proyectos\\023779_ALIADA\\Development\\aliada-links-discovery\\tmp";
		DDBBParams ddbbParams = newDDBBParams();
		String result = linksDisc.createLinkingPropConfigFile(tmpDir, ddbbParams);
        if (result != null) {
            log.info("OK");
        } else {
            log.info("NOK");
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
