// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.mx;

import static eu.aliada.rdfizer.TestUtils.newJobResource;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.management.InstanceAlreadyExistsException;
import javax.management.JMException;

import org.junit.Test;

/**
 * {@link ManagementRegistrar} test case.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class ManagementRegistrarTest {
	
	/**
	 * The registrar musr allow job registration.
	 */
	@Test
	public void registerJob() {
		final Job job = newJobResource();
		try {
			ManagementRegistrar.registerJob(job);
			
			assertTrue(ManagementRegistrar.isAlreadyRegistered(
					ManagementRegistrar.createJobObjectName(
							job.getFormat(), 
							job.getID())));
		} catch (final JMException exception) {
			fail();
		} finally {
			ManagementRegistrar.unregister(
					ManagementRegistrar.createJobObjectName(
							job.getFormat(), 
							job.getID()));
		}
	}
	
	/**
	 * If the job has been already registered then an exception must be thrown.
	 */
	@Test
	public void registerJobTwice() {
		final Job job = newJobResource();
		try {
			ManagementRegistrar.registerJob(job);
			assertTrue(ManagementRegistrar.isAlreadyRegistered(
					ManagementRegistrar.createJobObjectName(
							job.getFormat(), 
							job.getID())));

			ManagementRegistrar.registerJob(job);
			fail();
		} catch (final JMException exception) {
			assertTrue(exception instanceof InstanceAlreadyExistsException);
		} finally {
			ManagementRegistrar.unregister(
					ManagementRegistrar.createJobObjectName(
							job.getFormat(), 
							job.getID()));
		}
	}	
}