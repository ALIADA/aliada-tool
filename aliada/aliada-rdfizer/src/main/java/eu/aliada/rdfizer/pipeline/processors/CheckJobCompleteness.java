// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.processors;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;

import eu.aliada.rdfizer.Constants;
import eu.aliada.rdfizer.datasource.Cache;
import eu.aliada.rdfizer.datasource.rdbms.JobInstance;
import eu.aliada.rdfizer.datasource.rdbms.JobInstanceRepository;
import eu.aliada.rdfizer.datasource.rdbms.JobStats;
import eu.aliada.rdfizer.datasource.rdbms.JobStatsRepository;
import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.rdfizer.mx.InMemoryJobResourceRegistry;
import eu.aliada.rdfizer.mx.ManagementRegistrar;
import eu.aliada.rdfizer.rest.JobResource;
import eu.aliada.shared.log.Log;

/**
 * A processor that checks if a given job has been completed.
 * This step, previously part of the RDF translator, has been implemented in this dedicated processor, 
 * for a better cohesion.
 * 
 * @author Andrea Gazzarini
 * @since 2.0
 */
public class CheckJobCompleteness implements Processor {
	private final Log log = new Log(CheckJobCompleteness.class); 
	
	@Autowired
	protected Cache cache;
	
	@Autowired
	protected InMemoryJobResourceRegistry jobRegistry;
		
	@Autowired
	protected JobInstanceRepository jobInstanceRepository;
	
	@Autowired
	protected JobStatsRepository jobStatsRepository;
	
	@Override
	public void process(final Exchange exchange) throws Exception {
		final Integer jobId = exchange.getIn().getHeader(Constants.JOB_ID_ATTRIBUTE_NAME, Integer.class);
		final JobInstance configuration = cache.getJobInstance(jobId);
		
		if (configuration == null) {
			log.error(MessageCatalog._00038_UNKNOWN_JOB_ID, jobId);
			throw new IllegalArgumentException(String.valueOf(jobId));
		}
		
		checkForCompleteness(jobId);
	}
	
	/**
	 * Updates the processed records on the management interface of the given job. 
	 * 
	 * @param jobId the job identifier.
	 */
	void checkForCompleteness(final Integer jobId) {
		final JobResource job = jobRegistry.getJobResource(jobId);
		if (job != null && job.isCompleted()) {
			markJobAsCompleted(job);
			persistJobStats(job);
			unregisterJobFromMxSystem(job);
			removeFromInMemoryCache(job);
		}
	}
	
	/**
	 * Marks a given job as completed.
	 * 
	 * @param job the job.
	 */
	 void markJobAsCompleted(final JobResource job) {
		try { 
			final JobInstance instance = cache.getJobInstance(job.getID());
			instance.setEndDate(new Timestamp(System.currentTimeMillis()));
			jobInstanceRepository.save(instance);		
		} catch (Exception ignore) {
			// Nothing to be done.
		} finally {
			log.info(MessageCatalog._00048_JOB_COMPLETED, job.getID());
		}
	}	
	
	/**
	 * Records the stats of a given (completed) job.
	 * 
	 * @param job the job.
	 */
	synchronized void persistJobStats(final JobResource job) {
		final JobStats stats = new JobStats();
		stats.setStatusCode(0); 
		stats.setId(job.getID());
		stats.setTotalRecordsCount(job.getTotalRecordsCount());
		stats.setTotalTriplesProduced(job.getOutputStatementsCount());
		stats.setRecordsThroughput(
				job.getRecordsThroughput() > 0 
					? BigDecimal.valueOf(job.getRecordsThroughput())
					: BigDecimal.ZERO);
		stats.setTriplesThroughput(
				job.getStatementsThroughput() > 0
					? BigDecimal.valueOf(job.getStatementsThroughput())
					: BigDecimal.ZERO);		
			jobStatsRepository.save(stats);					
	}		
	
	/**
	 * Unregisters a job from managemenent (sub)system.
	 * 
	 * @param job the job.
	 */
	void unregisterJobFromMxSystem(final JobResource job) {
		ManagementRegistrar.unregister(
				ManagementRegistrar.createJobObjectName(
						job.getFormat(), 
						job.getID()));
		
		jobRegistry.removeJob(job.getID());
	}
	
	/**
	 * Removes a job from local in memory cache.
	 * 
	 * @param job the job.
	 */
	void removeFromInMemoryCache(final JobResource job) {
		cache.removeJobInstance(job.getID());		
	}	
}  