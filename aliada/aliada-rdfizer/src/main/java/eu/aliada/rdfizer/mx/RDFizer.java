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
	
	int getRunningJobsCount();
	
	int getCompletedJobsCount();
	
	int getProcessedRecordsCount();
	
	void start();
	
	void stop();
}