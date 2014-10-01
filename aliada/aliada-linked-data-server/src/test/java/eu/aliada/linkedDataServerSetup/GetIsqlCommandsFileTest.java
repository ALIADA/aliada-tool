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
import junit.framework.TestCase;

/**
 * Test LinkedDataServerSetup class functions
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class GetIsqlCommandsFileTest extends TestCase {
	static final Random RANDOMIZER = new Random();
    private final Log log = new Log(GetIsqlCommandsFileTest.class);

    /**
     * @see
     * @since 1.0
     */
    public void testGetIsqlCommandsFile() {
		LinkedDataServerSetup ldsSetup = new LinkedDataServerSetup();
		JobConfiguration jobConf = newJobConfiguration();
		String result = ldsSetup.getIsqlCommandsFile(jobConf);
        if (result != null) {
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
	 * Creates a dummy job configuration. 
	 * 
	 * @return a dummy job configuration.
	 */
	public static JobConfiguration newJobConfiguration() {
		JobConfiguration job = new JobConfiguration();
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
