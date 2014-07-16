// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.datasource;

import static eu.aliada.rdfizer.TestData.RANDOMIZER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import eu.aliada.rdfizer.datasource.rdbms.JobConfiguration;
import eu.aliada.rdfizer.datasource.rdbms.JobConfigurationRepository;
import eu.aliada.rdfizer.datasource.rdfstore.AliadaRDFStoreDAO;

/**
 * Test case for RDFizer {@link Cache}.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class CacheTest {
	private Cache cut;
	private AliadaRDFStoreDAO dao;
	private JobConfigurationRepository repository;
	
	/**
	 * Setup fixture for this test case.
	 */
	@Before
	public void setUp() {
		cut = new Cache();
		dao = mock(AliadaRDFStoreDAO.class);
		repository = mock(JobConfigurationRepository.class);
		cut.rdfStore = dao;
		cut.jobConfigurationRepository = repository;
	}

	/**
	 * If a CRM class is in cache then is returned.
	 */
	@Test
	public void crmClassFoundInCache() {
		final String aClassThatIsInCache = cut.crm2AliadaClasses.keySet().iterator().next();
		final String expectedResult = cut.crm2AliadaClasses.get(aClassThatIsInCache);

		assertEquals(
				expectedResult, 
				cut.getAliadaClassFrom(aClassThatIsInCache));
		
		verifyZeroInteractions(dao);
	}	
	
	/**
	 * If a CRM class is not in cache then the RDF Store is queried.
	 */
	@Test
	public void crmClassNotFoundInCacheButFoundInRDFStore() {
		final String aClassThatIsNotInCache = String.valueOf(RANDOMIZER.nextLong());
		final String expectedResult = String.valueOf(RANDOMIZER.nextLong());
		when(dao.crm2AliadaClass(aClassThatIsNotInCache)).thenReturn(expectedResult);

		assertEquals(
				expectedResult, 
				cut.getAliadaClassFrom(aClassThatIsNotInCache));
		
		verify(dao).crm2AliadaClass(aClassThatIsNotInCache);
	}
	
	/**
	 * If a CRM class is not in cache and is not managed by ALIADA the a default class will be returned.
	 */
	@Test
	public void crmClassNotFound() {
		final String aClassThatIsNotInCache = String.valueOf(RANDOMIZER.nextLong());
		when(dao.crm2AliadaClass(aClassThatIsNotInCache)).thenReturn(null);

		assertEquals(
				Cache.DEFAULT_ALIADA_CLASS, 
				cut.getAliadaClassFrom(aClassThatIsNotInCache));
		
		verify(dao).crm2AliadaClass(aClassThatIsNotInCache);
	}
	
	/**
	 * If a CRM event type is in cache then is returned.
	 */
	@Test
	public void crmEventTypeFoundInCache() {
		final String anEventTypeThatIsInCache = cut.crm2AliadaEventTypes.keySet().iterator().next();
		final String expectedResult = cut.crm2AliadaEventTypes.get(anEventTypeThatIsInCache);

		assertEquals(
				expectedResult, 
				cut.getAliadaEventTypeClassFrom(anEventTypeThatIsInCache));
		
		verifyZeroInteractions(dao);
	}	
	
	/**
	 * If a CRM event type is not in cache then the RDF Store is queried.
	 */
	@Test
	public void crmEventTypeNotFoundInCacheButFoundInRDFStore() {
		final String anEventTypeThatIsNotInCache = String.valueOf(RANDOMIZER.nextLong());
		final String expectedResult = String.valueOf(RANDOMIZER.nextLong());
		when(dao.crm2AliadaClass(anEventTypeThatIsNotInCache)).thenReturn(expectedResult);

		assertEquals(
				expectedResult, 
				cut.getAliadaEventTypeClassFrom(anEventTypeThatIsNotInCache));
		
		verify(dao).crm2AliadaClass(anEventTypeThatIsNotInCache);
	}
	
	/**
	 * If a CRM event type is not in cache and is not managed by ALIADA the null will be returned.
	 */
	@Test
	public void crmEventTypeNotFound() {
		final String anEventTypeThatIsNotInCache = String.valueOf(RANDOMIZER.nextLong());
		when(dao.crm2AliadaClass(anEventTypeThatIsNotInCache)).thenReturn(null);

		assertNull(cut.getAliadaEventTypeClassFrom(anEventTypeThatIsNotInCache));
		
		verify(dao).crm2AliadaClass(anEventTypeThatIsNotInCache);
	}	
	
	/**
	 * If a job configuration is in cache that avoids unnecessary database lookups. 
	 */
	@Test
	public void jobConfigurationInCache() {
		final Integer id = RANDOMIZER.nextInt();
		final JobConfiguration configuration = new JobConfiguration();
		configuration.setId(id);
		cut.activeJobConfigurations.put(id, configuration);
		
		assertSame(configuration, cut.getJobConfiguration(id));
		assertEquals(id, cut.getJobConfiguration(id).getId());
		
		verifyZeroInteractions(repository);
	}
	
	/**
	 * If a job configuration is not in cache that requires a database lookup. 
	 */
	@Test
	public void jobConfigurationNotInCache() {
		final Integer id = RANDOMIZER.nextInt();
		final JobConfiguration configuration = new JobConfiguration();
		configuration.setId(id);
		assertNull(cut.activeJobConfigurations.get(id));
		
		when(repository.findOne(id)).thenReturn(configuration);
		
		assertSame(configuration, cut.getJobConfiguration(id));
		assertEquals(id, cut.getJobConfiguration(id).getId());
	}	 
	
	/**
	 * If a job configuration is not found null will be returned. 
	 */
	@Test
	public void jobConfigurationNotFound() {
		final Integer id = RANDOMIZER.nextInt();
		final JobConfiguration configuration = new JobConfiguration();
		configuration.setId(id);
		assertNull(cut.activeJobConfigurations.get(id));
		
		when(repository.findOne(id)).thenReturn(null);
		
		assertNull(cut.getJobConfiguration(id));
	}		
}