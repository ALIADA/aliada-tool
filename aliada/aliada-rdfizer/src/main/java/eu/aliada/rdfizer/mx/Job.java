// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.mx;

import javax.management.MXBean;

/**
 * Management interface of a RDFizer job.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@MXBean
public interface Job {
	/**
	 * Returns the identifier associated with this job.
	 * 
	 * @return the identifier associated with this job.
	 */
	Integer getID(); 

	/**
	 * Returns the format associated with this job.
	 * 
	 * @return the format associated with this job.
	 */
	String getFormat(); 
	
	/**
	 * Returns the total records count for this job.
	 * Note that this counter could vary between requests if {@link #isSplitCompleted()} indicates
	 * that split hasn't been yet completed.
	 * 
	 * @return the total records count for this job.
	 */
	int getTotalRecordsCount();
	
	/**
	 * Returns the total number of processed records for this job.
	 * A "processed" record is a record that has been translated.
	 * 
	 * @return the total number of processed records for this job.
	 */
	int getProcessedRecordsCount();

	/**
	 * Returns the total number of emitted statements for this job.
	 * 
	 * @return the total number of emitted statements for this job.
	 */
	int getOutputStatementsCount();
	
	/**
	 * Returns the record processing throughput in terms of records / sec.
	 * 
	 * @return the record processing throughput in terms of records / sec.
	 */
	double getRecordsThroughput();
	
	/**
	 * Returns true if this job is running.
	 * 
	 * @return true if this job is running.
	 */
	boolean isRunning();
}