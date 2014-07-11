// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.linksDiscovery.model;

import java.util.ArrayList;
import java.util.Date;

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
    private int id;
    private Date startDate;
    private Date endDate;
    private int numLinks;
    private String status; /* Possible values: idle, running, finished. */

    @XmlElement
	private ArrayList<Subjob> subjobs = new ArrayList<Subjob>();
    
    public Job() {// JAXB needs this
    	this.subjobs = new ArrayList<Subjob>();
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
	 * Returns the number of links generated.
	 * 
	 * @return The number of links generated.
	 */
	public Integer getNumLinks() {
		return numLinks;
	}
	/**
	 * Sets the number of links generated.
	 * 
	 * @param numLinks The number of links generated.
	 */
	public void setNumLinks(Integer numLinks) {
		this.numLinks = numLinks;
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
	
	/**
	 * Returns the subjobs of the job.
	 * 
	 * @return The subjobs of the job.
	 */
	public Subjob[] getSubjobs() {
		return this.subjobs.toArray(new Subjob[]{});
	}
	/**
	 * Adds a subjob to the job.
	 * 
	 * @param subjob The subjob to add to the job.
	 */
	public void addSubjob(Subjob subjob) {
		subjobs.add(subjob);
	}
}
