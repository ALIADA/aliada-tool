// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * CKAN Page Creation job entity.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
@XmlRootElement
public class Job {
	/** Job identification number. */
	private int id;
	/** URL of the organization in CKAN. */
	private String ckanOrgUrl;
	/** URL of the dataset in CKAN. */
	private String ckanDatasetUrl;
	/** Void file path. */
	private String voidFilePath;
	/** Void file URL. */
	private String voidFileUrl;
	/** Job starting date. */
	private Date startDate;
	/** Job end date. */
	private Date endDate;
	/** Job status. Possible values: idle, running, finished. */
	private String status;

    
	/**
	 * Returns the identifier of this job.
	 * 
	 * @return The identifier of this job.
	 * @since 2.0
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * Sets the identifier of this job.
	 * 
	 * @param id The identifier of this job.
	 * @since 2.0
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * Returns the URL of the organization in CKAN.
	 * 
	 * @return The URL of the organization in CKAN.
	 * @since 2.0
	 */
	public String getCkanOrgUrl() {
		return ckanOrgUrl;
	}
	/**
	 * Sets the URL of the organization in CKAN.
	 * 
	 * @param ckanOrgUrl The URL of the organization in CKAN.
	 * @since 2.0
	 */
	public void setCkanOrgUrl(final String ckanOrgUrl) {
		this.ckanOrgUrl = ckanOrgUrl;
	}

	/**
	 * Returns the URL of the dataset in CKAN.
	 * 
	 * @return The URL of the dataset in CKAN.
	 * @since 2.0
	 */
	public String getCkanDatasetUrl() {
		return ckanDatasetUrl;
	}
	/**
	 * Sets the URL of the dataset in CKAN.
	 * 
	 * @param ckanDatasetUrl The URL of the dataset in CKAN.
	 * @since 2.0
	 */
	public void setCkanDatasetUrl(final String ckanDatasetUrl) {
		this.ckanDatasetUrl = ckanDatasetUrl;
	}

	/**
	 * Returns the Void file path.
	 * 
	 * @return The Void file path.
	 * @since 2.0
	 */
	public String getVoidFilePath() {
		return voidFilePath;
	}
	/**
	 * Sets the Void file path.
	 * 
	 * @param voidFilePath The void file path.
	 * @since 2.0
	 */
	public void setVoidFilePath(final String voidFilePath) {
		this.voidFilePath = voidFilePath;
	}

	/**
	 * Returns the Void file URL.
	 * 
	 * @return The Void file URL.
	 * @since 2.0
	 */
	public String getVoidFileUrl() {
		return voidFileUrl;
	}
	/**
	 * Sets the Void file URL.
	 * 
	 * @param voidFileUrl The Void file URL.
	 * @since 2.0
	 */
	public void setVoidFileUrl(final String voidFileUrl) {
		this.voidFileUrl = voidFileUrl;
	}

	/**
	 * Returns the start date of the job.
	 * 
	 * @return The start date of the job.
	 * @since 2.0
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * Sets the start date of the job.
	 * 
	 * @param startDate The start date of the job.
	 * @since 2.0
	 */
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Returns the end date of the job.
	 * 
	 * @return The end date of the job.
	 * @since 2.0
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * Sets the end date of the job.
	 * 
	 * @param endDate The end date of the job.
	 * @since 2.0
	 */
	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Returns the status of the job.
	 * Possible values: idle, running, finished.
	 *  
	 * @return The status of the job.
	 * @since 2.0
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * Sets the status of the job.
	 * Possible values: idle, running, finished.
	 * 
	 * @param status The status of the job.
	 * @since 2.0
	 */
	public void setStatus(final String status) {
		this.status = status;
	}
	
}
