// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortiums
package eu.aliada.linkeddataserversetup;

import eu.aliada.linkeddataserversetup.impl.LinkedDataServerSetup;
import eu.aliada.linkeddataserversetup.model.JobConfiguration;
import eu.aliada.linkeddataserversetup.model.Subset;
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
		final Subset subset = newSubset();
		final String result = ldsSetup.getIsqlCommandsFileSubset(jobConf, subset);
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
		job.setIsqlCommandPath("isql");
		job.setIsqlCommandsSubsetFilenameDefault("src/test/resources/isql_id_rewrite_rules_html_default.sql");
		return job;
	}

	/**
	 * Creates a dummy subset. 
	 * 
	 * @return a dummy subset.
     * @since 1.0
	 */
	public static Subset newSubset() {
		final Subset subset = new Subset();
		subset.setIsqlCommandsSubsetFilename("isql_id_rewrite_rules_html_artium.sql");
		return subset;
	}
}
