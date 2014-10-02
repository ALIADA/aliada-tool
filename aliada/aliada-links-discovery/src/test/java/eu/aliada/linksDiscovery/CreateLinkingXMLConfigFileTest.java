// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortiums
package eu.aliada.linksDiscovery;

import eu.aliada.linksDiscovery.impl.LinksDiscovery;
import eu.aliada.linksDiscovery.model.JobConfiguration;
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
	static final Random RANDOMIZER = new Random();
    private final Log log = new Log(CreateLinkingXMLConfigFileTest.class);

    /**
     * @see
     * @since 1.0
     */
    @Test
    public void testCreateLinkingXMLConfigFile() {
		LinksDiscovery linksDisc = new LinksDiscovery();
		String linkingFile = "src/test/resources/aliada_dbpedia_config.xml";
		String ds = "ALIADA_ds";
		String subjobName = "ALIADA_DBpedia";
		JobConfiguration jobConf = newJobConfiguration();
		String result = linksDisc.createLinkingXMLConfigFile(linkingFile, ds, subjobName, jobConf);
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
