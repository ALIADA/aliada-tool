// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.linksdiscovery.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

/**
 * Links discovery job entity.
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
	/** Number of links generated. */
	private int numLinks;
	/** Linking process duration. */
	private long durationSeconds;

	/** Subjobs associated. */
	@XmlElement
	private ArrayList<Subjob> subjobs = new ArrayList<Subjob>();
    
	/**
	 * Default constructor.

	 * @since 1.0
	 */
	public Job() {// JAXB needs this
		this.subjobs = new ArrayList<Subjob>();
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
	 * Returns the number of links generated.
	 * 
	 * @return The number of links generated.
	 * @since 1.0
	 */
	public Integer getNumLinks() {
		return numLinks;
	}
	/**
	 * Sets the number of links generated.
	 * 
	 * @param numLinks The number of links generated.
	 * @since 1.0
	 */
	public void setNumLinks(final Integer numLinks) {
		this.numLinks = numLinks;
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
	
	/**
	 * Returns the linking process duration in seconds.
	 * 
	 * @return The linking process duration in seconds.
	 * @since 2.0
	 */
	public long getDurationSeconds() {
		this.durationSeconds = 0;
		if(startDate != null & endDate != null) {
			long diffInMillies = endDate.getTime() - startDate.getTime();
			TimeUnit timeUnit = TimeUnit.SECONDS;
			this.durationSeconds = timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
		}
		return durationSeconds;
	}
	/**
	 * Sets the linking process duration in seconds.
	 * 
	 * @param numLinks The linking process duration in seconds.
	 * @since 2.0
	 */
	public void setDurationSeconds(final long durationSeconds) {
		this.durationSeconds = durationSeconds;
	}

	/**
	 * Returns the subjobs of the job.
	 * 
	 * @return The subjobs of the job.
	 * @since 1.0
	 */
	public Subjob[] getSubjobs() {
		return this.subjobs.toArray(new Subjob[]{});
	}
	/**
	 * Adds a subjob to the job.
	 * 
	 * @param subjob The subjob to add to the job.
	 * @since 1.0
	 */
	public void addSubjob(final Subjob subjob) {
		subjobs.add(subjob);
	}
}
