// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.rest;

import java.util.concurrent.atomic.AtomicInteger;

import eu.aliada.rdfizer.datasource.rdbms.JobConfiguration;
import eu.aliada.rdfizer.mx.Job;

/**
 * A conversion job resource representation on RDF-izer.
 * Provides a REST management interface for the RDF-izer job resource.
 * Note that the creation of a new job is not handled here but directly in RDF-izer resource; here we are
 * working on an existing job.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class JobResource implements Job {

	private final JobConfiguration configuration;
	
	private int totalRecordsCount;
	private AtomicInteger totalProcessedRecordsCount = new AtomicInteger();
	private AtomicInteger totalOutputStatementsCount = new AtomicInteger();
	
	/**
	 * Builds a new {@link JobResource} with the given configuration.
	 * 
	 * @param configuration the job instance configuration.
	 */
	public JobResource(final JobConfiguration configuration) {
		this.configuration = configuration;
	}

	/**
	 * Sets the total records count.
	 * 
	 * @param size how many records for this job.
	 */
	public void setTotalRecordsCount(final int size) {
		this.totalRecordsCount = size;
	}

	/**
	 * Increments the total processed records count.
	 */
	public void incrementProcessedRecordsCount() {
		totalProcessedRecordsCount.incrementAndGet();
	}

	/**
	 * Increments the total output statements count.
	 */
	public void incrementOutputStatementsCount() {
		totalOutputStatementsCount.incrementAndGet();
	}
	
	@Override
	public int getTotalRecordsCount() {
		return totalRecordsCount;
	}

	@Override
	public int getProcessedRecordsCount() {
		return totalProcessedRecordsCount.get();
	}

	@Override
	public int getOutputStatementsCount() {
		return totalOutputStatementsCount.get();
	}

	@Override
	public double getRecordsThroughput() {
		return 0;
	}

	@Override
	public Integer getID() {
		return configuration.getId();
	}

	@Override
	public String getFormat() {
		return configuration.getFormat();
	}
}