// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.mx;

import static eu.aliada.rdfizer.TestData.RANDOMIZER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import eu.aliada.rdfizer.datasource.rdbms.JobConfiguration;
import eu.aliada.rdfizer.rest.JobResource;

/**
 * Test case for {@link InMemoryJobResourceRegistry}.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class InMemoryJobResourceRegistryTest {
	
	private InMemoryJobResourceRegistry cut;
	
	/**
	 * Setup fixture for this test case.
	 */
	@Before
	public void setUp() {
		this.cut = new InMemoryJobResourceRegistry();
		assertTrue(cut.registry.isEmpty());
	}
	
	/**
	 * Positive test for adding a new job resource.
	 */
	@Test
	public void addAndGetJobResource() {
		final Integer id = RANDOMIZER.nextInt();
		final JobConfiguration configuration = new JobConfiguration();
		configuration.setId(id);
		final JobResource resource = new JobResource(configuration);
		
		cut.addJobResource(resource);
		
		assertFalse(cut.registry.isEmpty());
		
		final JobResource result = cut.getJobResource(id);
		
		assertSame(resource, result);
		assertEquals(resource.getID(), result.getID());
	}	

	/**
	 * If a job resource is not found then null must be returned.
	 */
	@Test
	public void jobResourceNotFound() {
		final Integer id = RANDOMIZER.nextInt();
		final JobConfiguration configuration = new JobConfiguration();
		configuration.setId(id);
		final JobResource resource = new JobResource(configuration);
		
		cut.addJobResource(resource);

		assertNull(cut.getJobResource(id + 1));
	}
}