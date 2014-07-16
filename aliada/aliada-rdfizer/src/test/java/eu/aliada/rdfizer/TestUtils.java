// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer;

import java.util.Random;

import eu.aliada.rdfizer.datasource.rdbms.JobConfiguration;
import eu.aliada.rdfizer.rest.JobResource;

/**
 * Test utilities.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public abstract class TestUtils {
	static final Random RANDOMIZER = new Random();
	
	/**
	 * Returns a random string.
	 * 
	 * @return a random string.
	 */
	public static String randomString() {
		return String.valueOf(RANDOMIZER.nextLong());
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
		final JobConfiguration configuration = new JobConfiguration();
		configuration.setId(randomIdentifier());		
		configuration.setFormat(randomString());
		return configuration;
	}
	
	/**
	 * Creates a dummy job resource. 
	 * 
	 * @return a dummy job resource.
	 */
	public static JobResource newJobResource() {
		return new JobResource(newJobConfiguration());
	}
}