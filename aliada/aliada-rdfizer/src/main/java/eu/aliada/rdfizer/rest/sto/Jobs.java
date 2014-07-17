// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.rest.sto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import eu.aliada.rdfizer.datasource.rdbms.JobConfiguration;

/**
 * A simple transfer object for listing all jobs on the RDFizer.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@XmlRootElement(name = "jobs")
public class Jobs {
	@XmlElement(name = "running")
	private List<Integer> activeJobs = new ArrayList<Integer>();
	
	@XmlElement(name = "completed")
	private List<Integer> completedJobs = new ArrayList<Integer>();
	
	/**
	 * Empty constructor only needed for JAXB.
	 */
	public Jobs() {
		// Do nothing here...
	}
	
	/**
	 * Builds a Jobs state transfer object with the given data.
	 * 
	 * @param jobs the job(s) iterator.
	 */
	public Jobs(final Iterable<JobConfiguration> jobs) {
		for (final JobConfiguration jobConfiguration : jobs) {
			if (jobConfiguration.getEndDate() == null) {
				activeJobs.add(jobConfiguration.getId());
			} else {
				completedJobs.add(jobConfiguration.getId());				
			}
		}
	}
	
	/**
	 * Returns the total number of jobs currently registered in RDFizer.
	 * 
	 * @return the total number of jobs currently registered in RDFizer.
	 */
	@XmlElement(name = "total-jobs-count")
	public int getTotalJobsCount() {
		return activeJobs.size() + completedJobs.size();
	}
	
	/**
	 * Returns the total number of active jobs currently registered in RDFizer.
	 * 
	 * @return the total number of active jobs currently registered in RDFizer.
	 */
	@XmlElement(name = "total-active-jobs-count")
	public int getTotalActiveJobsCount() {
		return activeJobs.size();
	}	
	
	/**
	 * Returns the total number of completed jobs currently registered in RDFizer.
	 * 
	 * @return the total number of completed jobs currently registered in RDFizer.
	 */
	@XmlElement(name = "total-completed-jobs-count")
	public int getTotalCompletedJobsCount() {
		return completedJobs.size();
	}		
}