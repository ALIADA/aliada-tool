// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.rest;

import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

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
@XmlRootElement(name = "job")
public class JobResource implements Job {

	private JobConfiguration configuration;
	
	private int totalRecordsCount;
	private AtomicInteger totalProcessedRecordsCount = new AtomicInteger();
	private AtomicInteger totalOutputStatementsCount = new AtomicInteger();
	
	private boolean isRunning;
	
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

	@XmlElement(name = "record-throughput")
	@Override
	public double getRecordsThroughput() {
		return 0;
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
}