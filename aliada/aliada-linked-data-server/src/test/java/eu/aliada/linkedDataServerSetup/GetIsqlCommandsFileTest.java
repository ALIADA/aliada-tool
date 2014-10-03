// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortiums
package eu.aliada.linkedDataServerSetup;

import eu.aliada.linkedDataServerSetup.impl.LinkedDataServerSetup;
import eu.aliada.linkedDataServerSetup.model.JobConfiguration;
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
		final JobConfiguration jobConf = newJobConfiguration();
		final String result = ldsSetup.getIsqlCommandsFile(jobConf);
        if (result != null) {
        	LOGGER.info("OK");
        } else {
        	LOGGER.info("NOK");
        }
    }
    
	/**
	 * Returns a random identifier (as integer).
	 * 
	 * @return a random identifier (as integer).
     * @since 1.0
	 */
	public static Integer randomIdentifier() {
		return RANDOMIZER.nextInt();
	}

	/**
	 * Creates a dummy job configuration. 
	 * 
	 * @return a dummy job configuration.
     * @since 1.0
	 */
	public static JobConfiguration newJobConfiguration() {
		final JobConfiguration job = new JobConfiguration();
		job.setId(randomIdentifier());
		job.setStoreIp("aliada.scanbit.net");
		job.setStoreSqlPort(1111);
		job.setSqlLogin("sql_login");
		job.setSqlPassword("sql_password");
		job.setGraph("http://biblioteca.artium.org");
		job.setDatasetBase("http://aliada.scanbit.net:8890");
		job.setIsqlCommandPath("isql");
		job.setIsqlCommandsFilename("isql_id_rewrite_rules_html_artium.sql");
		job.setIsqlCommandsFilenameDefault("src/test/resources/isql_id_rewrite_rules_html_default.sql");
		return job;
	}

}
