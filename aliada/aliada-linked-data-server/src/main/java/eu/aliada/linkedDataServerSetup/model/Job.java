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
	/** Job identification number. */
	private int id;
	/** Job starting date. */
	private Date startDate;
	/** Job end date. */
	private Date endDate;
	/** Job status. Possible values: idle, running, finished. */
	private String status;

	/**
	 * Default constructor.

	 * @since 1.0
	 */
	public Job() {// JAXB needs this
	} 

	/**
	 * Returns the identifier of this job.
	 * 
	 * @return The identifier of this job.
	 * @since 1.0
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * Sets the identifier of this job.
	 * 
	 * @param id The identifier of this job.
	 * @since 1.0
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * Returns the start date of the job.
	 * 
	 * @return The start date of the job.
	 * @since 1.0
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * Sets the start date of the job.
	 * 
	 * @param startDate The start date of the job.
	 * @since 1.0
	 */
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Returns the end date of the job.
	 * 
	 * @return The end date of the job.
	 * @since 1.0
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * Sets the end date of the job.
	 * 
	 * @param endDate The end date of the job.
	 * @since 1.0
	 */
	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Returns the status of the job.
	 * Possible values: idle, running, finished.
	 *  
	 * @return The status of the job.
	 * @since 1.0
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * Sets the status of the job.
	 * Possible values: idle, running, finished.
	 * 
	 * @param status The status of the job.
	 * @since 1.0
	 */
	public void setStatus(final String status) {
		this.status = status;
	}
	
}
