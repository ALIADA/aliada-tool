// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.rest;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

import eu.aliada.rdfizer.datasource.rdbms.JobInstance;
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
@XmlRootElement(name = "job")
public class JobResource implements Job {

	private JobInstance configuration;
	
	private AtomicInteger validationRecordSetCount = new AtomicInteger(-1);
	
	private int totalRecordsCount = -1;
	private final AtomicInteger totalProcessedRecordsCount = new AtomicInteger();
	private final AtomicInteger totalOutputStatementsCount = new AtomicInteger();
	
	private final AtomicLong elapsed = new AtomicLong();
	
	private boolean isRunning;
	private boolean validated;
	
	/**
	 * Builds a new {@link JobResource}. 
	 * 
	 * NB: Dummy constructor needed only by JAXB.
	 */
	public JobResource() {
		this.configuration = null;
	}
	
	/**
	 * Builds a new {@link JobResource} with the given configuration.
	 * 
	 * @param configuration the job instance configuration.
	 */
	public JobResource(final JobInstance configuration) {
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
	 * Increments the total elapsed.
	 * 
	 * @param increment the additional elapsed.
	 */
	public void incrementElapsed(final long increment) {
		elapsed.addAndGet(increment);
	}
	
	/**
	 * Increments the total output statements count.
	 * 
	 * @param increment the delta that will be added to the total amount.
	 */
	public void incrementOutputStatementsCount(final int increment) {
		totalOutputStatementsCount.addAndGet(increment);
	}
	
	@XmlElement(name = "total-records-count")
	@Override
	public int getTotalRecordsCount() {
		return totalRecordsCount;
	}

	@XmlElement(name = "processed-records-count")
	@Override
	public int getProcessedRecordsCount() {
		return totalProcessedRecordsCount.get();
	}

	@XmlElement(name = "output-statements-count")
	@Override
	public int getOutputStatementsCount() {
		return totalOutputStatementsCount.get();
	}

	@XmlElement(name = "records-throughput")
	@Override
	public double getRecordsThroughput() {
		return elapsed.get() != 0 
				? (totalProcessedRecordsCount.doubleValue() / elapsed.get()) * 1000
				: 0;
	}

	@XmlElement(name = "id")
	@Override
	public Integer getID() {
		return configuration.getId();
	}

	@XmlElement(name = "format")
	@Override
	public String getFormat() {
		return configuration.getFormat();
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}
	
	/**
	 * Sets the status of this job.
	 * 
	 * @param running the current state of this job.
	 */
	public void setRunning(final boolean running) {
		this.isRunning = running;
	}

	@XmlElement(name = "completed")
	@Override
	public boolean isCompleted() {
		return totalRecordsCount <= totalProcessedRecordsCount.get();
	}

	@XmlElement(name = "triples-throughput")
	@Override
	public double getStatementsThroughput() {
		return elapsed.get() != 0 
				? (totalOutputStatementsCount.doubleValue() / elapsed.doubleValue()) * 1000
				: 0;		
	}
	
	/**
	 * Incremements the validation record set and returns the actual value.
	 * 
	 * @return the actual size of the validation set.
	 */
	public int incrementValidationRecordSet() {
		return validationRecordSetCount.incrementAndGet();
	}
	
	/**
	 * Checks if this job has been validated.
	 * 
	 * @return true if this job has been validated, false otherwise.
	 */	
	public boolean hasntBeenValidated() {
		return !validated;
	}
	
	/**
	 * Marks this job as validated.
	 */
	public void markAsValidated() {
		validated = true;
	}
}