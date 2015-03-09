// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortiums
package eu.aliada.linkeddataserversetup;

import eu.aliada.linkeddataserversetup.impl.LinkedDataServerSetup;
import eu.aliada.shared.log.Log;

import java.util.Random;

import org.junit.Test;

/**
 * Test {@link LinkedDataServerSetup} class functions
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class GetIsqlCommandsFileTest {
	/** For creating random variable values. */
	static final Random RANDOMIZER = new Random();
	/** For logging. */
	private static final Log LOGGER = new Log(GetIsqlCommandsFileTest.class);

    /**
     * Test the getIsqlCommandsFile method.
     * 
     * @since 1.0
     */
    @Test
    public void testGetIsqlCommandsFile() {
		final LinkedDataServerSetup ldsSetup = new LinkedDataServerSetup();
		final String fileName = "src/test/resources/isql_id_rewrite_rules_global_default.sql";
		final String defaultFileName = "src/test/resources/isql_id_rewrite_rules_global.sql";
		final String result = ldsSetup.getIsqlCommandsFile(fileName, defaultFileName);
        if (result != null) {
        	LOGGER.info("OK");
        } else {
        	LOGGER.info("NOK");
        }
    }
}
