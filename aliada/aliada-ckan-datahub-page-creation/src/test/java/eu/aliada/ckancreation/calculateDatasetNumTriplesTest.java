// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortiums
package eu.aliada.ckancreation;

import eu.aliada.ckancreation.impl.CKANCreation;
import eu.aliada.ckancreation.model.JobConfiguration;
import eu.aliada.ckancreation.model.Subset;
import eu.aliada.ckancreation.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

/**
 * Test {@link CKANCreation} class functions
 * 
 * @author Idoia Murua
 * @since 2.0
 */
public class calculateDatasetNumTriplesTest {
	/** For creating random variable values. */
	static final Random RANDOMIZER = new Random();
	/** For logging. */
	private static final Log LOGGER = new Log(calculateDatasetNumTriplesTest.class);

    /**
     * Test the calculateDatasetNumTriples method.
     * 
     * @since 2.0
     */
    @Test
    public void testCalculateDatasetNumTriples() {
    	DBConnectionManager dbConn = new DBConnectionManager();
		final JobConfiguration jobConf = newJobConfiguration();
		final CKANCreation ckanCreation = new CKANCreation(jobConf, dbConn);
		final  ArrayList<Subset> subsetsList = newSubsetList();
		final String sparqlEndpoint = "http://aliada.scanbit.net:8891/sparql";
		int result = -1;
		result = ckanCreation.calculateDatasetNumTriples(sparqlEndpoint, subsetsList);
        if (result >= 0) {
        	LOGGER.info("OK");
        } else {
        	LOGGER.info("NOK");
        }
    }
    
	/**
	 * Returns a random identifier (as integer).
	 * 
	 * @return a random identifier (as integer).
     * @since 2.0
	 */
	public static Integer randomIdentifier() {
		return RANDOMIZER.nextInt();
	}

	/**
	 * Creates a dummy job configuration. 
	 * 
	 * @return a dummy job configuration.
     * @since 2.0
	 */
	public static JobConfiguration newJobConfiguration() {
		final JobConfiguration job = new JobConfiguration();
		job.setId(randomIdentifier());
		job.setStoreIp("aliada.scanbit.net");
		job.setStoreSqlPort(1111);
		job.setSqlLogin("sql_login");
		job.setSqlPassword("sql_password");
		job.setUriDocPart("doc");
		job.setDomainName("data.artium.org");
		job.setOntologyUri("http://aliada-project.eu/2014/aliada-ontology#");
		job.setUriConceptPart("collections");
		job.setVirtualHost("*ini*");
		job.setIsqlCommandPath("isql");
		return job;
	}

	/**
	 * Creates a dummy subset list. 
	 * 
	 * @return a dummy subset list.
     * @since 2.0
	 */
	public static  ArrayList<Subset> newSubsetList() {
		ArrayList<Subset> subsetsList = new ArrayList<Subset>();
		Subset subset = new Subset();
		subset.setDescription("Library bibliography");
		subset.setGraph("http://aliada_graph1");
		subset.setLinksGraph("http://aliada_graph1.links");
		subset.setUriConceptPart("library/bib");
		subsetsList.add(subset);
		return subsetsList;
	}

}
