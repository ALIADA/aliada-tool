// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.datasource.rdbms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import eu.aliada.rdfizer.rest.JobResource;

/**
 * RDF-izer Job stats.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@Entity
@Table(name = "rdfizer_job_stats")
@XmlRootElement(name = "job")
public class JobStats {	
    @Id
    @Column(name = "job_id", nullable = false)
    private Integer id;
    
    @Column(name = "total_records_count", nullable = false)
    private int totalRecordsCount;
    
    @Column(name = "total_triples_produced", nullable = false)
    private int totalTriplesProduced;

    @Column(name = "records_throughput", nullable = false)
    private BigDecimal recordsThroughput;
        
    @Column(name = "triples_throughput", nullable = false)
    private BigDecimal triplesThroughput;
    
    @Column(name = "status_code", nullable = true)
    private Integer statusCode;

    @Transient
    private Date startDate;
    
    @Transient
    private Date endDate;
    
    @Transient
    private List<ValidationMessage> validationMessages = new ArrayList<ValidationMessage>();
    
	@Transient
	@XmlTransient
    private JobInstance instance;
   	
    /**
     * Sets the job instance on this stats.
     * 
     * @param instance the job instance on this stats.
     */
    public void setInstance(final JobInstance instance) {
    	this.instance = instance;
    }
    
	/**
	 * Returns the total number of processed records for this job.
	 * A "processed" record is a record that has been translated.
	 * 
	 * @return the total number of processed records for this job.
	 */
	@XmlElement(name = "processed-records-count")
	public int getProcessedRecordsCount() {
		return totalRecordsCount;
	}
	
    /**
     * Returns the identifier of this job configuration.
     * 
     * @return the identifier of this job configuration.
     */
	@XmlElement(name = "id")
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the identifier of this job configuration.
	 * 
	 * @param id the identifier of this job configuration.
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * Returns the total records count of the owning job.
	 * 
	 * @return the total records count of the owning job.
	 */
	@XmlElement(name = "total-records-count")
	public int getTotalRecordsCount() {
		return totalRecordsCount;
	}

	/**
	 * Sets the total records count of the owning job.
	 * 
	 * @param value the total records count of the owning job.
	 */
	public void setTotalRecordsCount(final int value) {
		this.totalRecordsCount = value;
	}

	/**
	 * Returns the total number of triples produced by the owning job.
	 * 
	 * @return the total number of triples produced by the owning job.
	 */
	@XmlElement(name = "output-statements-count")
	public int getTotalTriplesProduced() {
		return totalTriplesProduced;
	}

	/**
	 * Sets the total number of triples produced by the owning job.
	 * 
	 * @param value the total number of triples produced by the owning job.
	 */
	public void setTotalTriplesProduced(final int value) {
		this.totalTriplesProduced = value;
	}

	/**
	 * Returns the record processing throughput in terms of rec / sec.
	 * 
	 * @return the record processing throughput in terms of rec / sec.
	 */
	@XmlElement(name = "records-throughput")
	public BigDecimal getRecordsThroughput() {
		return recordsThroughput;
	}

	/**
	 * Sets the average triples production throughput in terms of triples / sec.
	 * 
	 * @param throughput the average triples production throughput in terms of triples / sec.
	 */
	public void setRecordsThroughput(final BigDecimal throughput) {
		this.recordsThroughput = throughput;
	}

	/**
	 * Returns the average triples production throughput in terms of triples / sec.
	 * 
	 * @return the average triples production throughput in terms of triples / sec.
	 */
	@XmlElement(name = "triples-throughput")
	public BigDecimal getTriplesThroughput() {
		return triplesThroughput;
	}

	/**
	 * Sets the triples production throughput in terms of triples / sec.
	 * 
	 * @param throughput the triples production throughput in terms of triples / sec.
	 */
	public void setTriplesThroughput(final BigDecimal throughput) {
		this.triplesThroughput = throughput;
	}
	
	/**
	 * Returns true if the job has been completed.
	 * 
	 * @see JobResource#isCompleted()
	 * @return true if the job has been completed.
	 */
	@XmlElement(name = "completed")
	public boolean isCompleted() {
		return true;
	}
	
	/**
	 * Returns true if the job is running.
	 * 
	 * @see JobResource#isRunning()
	 * @return true if the job is running.
	 */
	@XmlElement(name = "running")
	public boolean isRunning() {
		return false ;
	}	
	
	/**
	 * Returns the format that has been associated with this job.
	 * 
	 * @see JobResource#getFormat()
	 * @return the format that has been associated with this job.
	 */
	@XmlElement(name = "format")
	public String getFormat() {
		return instance != null ? instance.getFormat() : "Unknown";
	}

	@XmlElement(name = "status-code")
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = (statusCode != null) ? statusCode : 0;
	}

	public List<ValidationMessage> getValidationMessages() {
		return validationMessages;
	}

	public void setValidationMessages(List<ValidationMessage> validationMessages) {
		this.validationMessages = validationMessages;
	}

	@XmlElement(name = "start-date")
	public Date getStartDate() {
		return instance != null ? instance.getStartDate() : null;
	}

	@XmlElement(name = "end-date")
	public Date getEndDate() {
		return instance != null ? instance.getEndDate() : null;
	}
}