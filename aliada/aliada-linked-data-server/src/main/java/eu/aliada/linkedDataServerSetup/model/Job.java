// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-linked-data-server
// Responsible: ALIADA Consortium
package eu.aliada.linkedDataServerSetup.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Linked Data Server job entity.
 * 
 * @author Idoia Murua
 * @since 1.0
 */
@XmlRootElement
public class Job {
	private int id;
	private Date startDate;
	private Date endDate;
	private String status; /* Possible values: idle, running, finished. */

	public Job() {// JAXB needs this
	} 

	/**
	 * Returns the identifier of this job.
	 * 
	 * @return The identifier of this job.
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * Sets the identifier of this job.
	 * 
	 * @param id The identifier of this job.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Returns the start date of the job.
	 * 
	 * @return The start date of the job.
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * Sets the start date of the job.
	 * 
	 * @param startDate The start date of the job.
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Returns the end date of the job.
	 * 
	 * @return The end date of the job.
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * Sets the end date of the job.
	 * 
	 * @param endDate The end date of the job.
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Returns the status of the job.
	 * Possible values: idle, running, finished.
	 *  
	 * @return The status of the job.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * Sets the status of the job.
	 * Possible values: idle, running, finished.
	 * 
	 * @param status The status of the job.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
}
