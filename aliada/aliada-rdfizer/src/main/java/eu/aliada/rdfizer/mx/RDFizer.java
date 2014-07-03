// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.mx;

import javax.management.MXBean;

/**
 * RDF-izer monitoring and management interface.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@MXBean
public interface RDFizer {
	/**
	 * Returns the total number of running jobs.
	 * 
	 * @return the total number of running jobs.
	 */
	int getRunningJobsCount();
	
	/**
	 * Returns the total number of completed jobs.
	 * 
	 * @return the total number of completed jobs.
	 */
	int getCompletedJobsCount();
	
	/**
	 * Returns the total number of processed records.
	 * 
	 * @return the total number of processed records.
	 */	
	int getProcessedRecordsCount();
	
	/**
	 * Enables the RDFizer for further job processing.
	 */
	void enable();

	/**
	 * Disables the RDFizer for further job processing.
	 */
	void disable();
}