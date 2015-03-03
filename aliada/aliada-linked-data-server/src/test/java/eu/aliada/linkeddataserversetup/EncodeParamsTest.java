// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortiums
package eu.aliada.linkeddataserversetup;

import eu.aliada.linkeddataserversetup.impl.LinkedDataServerSetup;
import eu.aliada.linkeddataserversetup.model.JobConfiguration;
import eu.aliada.shared.log.Log;

import java.util.Random;

import org.junit.Test;

/**
 * Test {@link LinkedDataServerSetup} class functions
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class EncodeParamsTest {
	/** For creating random variable values. */
	static final Random RANDOMIZER = new Random();
	/** For logging. */
	private static final Log LOGGER = new Log(EncodeParamsTest.class);

    /**
     * Test the encodeParams method.
     * 
     * @since 1.0
     */
    @Test
    public void testEncodeParams() {
		final LinkedDataServerSetup ldsSetup = new LinkedDataServerSetup();
		final JobConfiguration jobConf = newJobConfiguration();
		final boolean result = ldsSetup.encodeParams(jobConf);
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
		job.setUriDocPart("doc");
		job.setUriIdPart("id");
		job.setUriDefPart("def");
		job.setDomainName("data.artium.org");
		job.setOntologyUri("http://aliada-project.eu/2014/aliada-ontology#");
		job.setUriConceptPart("collections");
		job.setListeningHost("*ini*");
		job.setVirtualHost("*ini*");
		job.setIsqlCommandPath("isql");
		return job;
	}

}
