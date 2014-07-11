// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.linksDiscovery.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Links discovery job entity.
 * 
 * @author Idoia Murua
 * @since 1.0
 */
@XmlRootElement
public class Subjob {
	private int id;
	private String name;
	private Date startDate;
	private Date endDate;
	private int numLinks;
	private String status; /* Possible values: idle, running, finished. */

	public Subjob(){} // JAXB needs this

	/**
	 * Returns the identifier of this subjob.
	 * 
	 * @return The identifier of this subjob.
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * Sets the identifier of this subjob.
	 * 
	 * @param id The identifier of this subjob.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Returns the name of the subjob.
	 * 
	 * @return The name of the subjob.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name of the subjob.
	 * 
	 * @param name The name of the subjob.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the start date of the subjob.
	 * 
	 * @return The start date of the subjob.
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * Sets the start date of the subjob.
	 * 
	 * @param startDate The start date of the subjob.
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Returns the end date of the subjob.
	 * 
	 * @return The end date of the subjob.
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * Sets the end date of the subjob.
	 * 
	 * @param endDate The end date of the subjob.
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
	 * Returns the status of the subjob.
	 * Possible values: idle, running, finished.
	 * 
	 * @return The status of the subjob.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * Sets the status of the subjob.
	 * Possible values: idle, running, finished.
	 * 
	 * @param status The status of the subjob.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
