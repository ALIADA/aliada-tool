// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.datasource.rdbms;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * RDF-izer Job stats.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@Entity
@Table(name = "rdfizer_job_stats")
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
    
    /**
     * Returns the identifier of this job configuration.
     * 
     * @return the identifier of this job configuration.
     */
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
}