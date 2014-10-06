// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortiums
package eu.aliada.linksdiscovery;

import eu.aliada.linksdiscovery.impl.LinksDiscovery;
import eu.aliada.linksdiscovery.model.JobConfiguration;
import eu.aliada.shared.log.Log;

import java.util.Random;

import org.junit.Test;

/**
 * Test {@link LinksDiscovery} class functions
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class CreateLinkingXMLConfigFileTest {
	/** For creating random variable values. */
	static final Random RANDOMIZER = new Random();
	/** For logging. */
	private static final Log LOGGER = new Log(CreateLinkingXMLConfigFileTest.class);
	/** Linking XML file path. */
	private static final String LINKING_FILE = "src/test/resources/aliada_dbpedia_config.xml";
	/** Data source name. */
	private static final String DATA_SOURCE = "ALIADA_ds";
	/** Subjob name. */
	private static final String SUBJOB_NAME = "ALIADA_DBpedia";

    /**
     * @see
     * @since 1.0
     */
    @Test
    public void testCreateLinkingXMLConfigFile() {
		final LinksDiscovery linksDisc = new LinksDiscovery();
		final JobConfiguration jobConf = newJobConfiguration();
		final String result = linksDisc.createLinkingXMLConfigFile(LINKING_FILE, DATA_SOURCE, SUBJOB_NAME, jobConf);
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
		final JobConfiguration job = new JobConfiguration();
		job.setId(randomIdentifier());
		job.setInputURI("http://aliada.scanbit.net:8890/sparql-auth");
		job.setInputLogin("login");
		job.setInputPassword("password");
		job.setInputGraph("http://biblioteca.artium.org");
		job.setOutputURI("http://aliada.scanbit.net:8890/sparql-auth");
		job.setOutputLogin("login");
		job.setOutputPassword("password");
		job.setOutputGraph("http://biblioteca.artium.org");
		job.setConfigFile("src/test/resources/linksdiscovery.properties");
		job.setRdfSinkFolder("http://aliada.scanbit.net:8890/aliada/rdf-sink");
		job.setRdfSinkLogin("login");
		job.setRdfSinkPassword("password");
		job.setTmpDir("D:\\Proyectos\\023779_ALIADA\\Development\\aliada-links-discovery\\tmp");
		job.setClientAppBinDir("src/test/resources/");
		return job;
	}

}
