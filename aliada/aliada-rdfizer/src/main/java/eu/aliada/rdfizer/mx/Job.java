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
	 * As side note, a job that has been just created couldn't have been this information
	 * injected, so, only in this case, in order to indicate this particular state this 
	 * attribute will return -1.
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
	 * Returns the statements processing throughput in terms of triples / sec.
	 * 
	 * @return the statements processing throughput in terms of triples / sec.
	 */
	double getStatementsThroughput();	
	
	/**
	 * Returns true if this job is running.
	 * 
	 * @return true if this job is running.
	 */
	boolean isRunning();
	
	/**
	 * Returns true if all records belonging to this job have been processed.
	 * Although similar, this information is a bit different from {@link #isRunning()}.
	 * A job is running if it has been started and it is not yet completed, while it is 
	 * completed only if all records have been processed. 
	 * As consequence of that, a job that for example has been paused / stopped, is not 
	 * running but is not completed.
	 * 
	 * @return true if all records belonging to this job have been processed.
	 */
	boolean isCompleted();
}