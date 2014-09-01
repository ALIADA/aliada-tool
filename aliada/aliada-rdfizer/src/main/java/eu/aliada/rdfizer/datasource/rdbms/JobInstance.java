// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.datasource.rdbms;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * RDF-izer Job instance entity.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@Entity
@Table(name = "rdfizer_job_instances")
public class JobInstance {
    @Id
    @Column(name = "job_id", nullable = false)
    private Integer id;
    
    @Column(name = "format", nullable = false)
    private String format;
    
    @Column(name = "datafile", nullable = false)
    private String datafile;

    @Column(name = "namespace", nullable = false)
    private String namespace;
        
    @Column(name = "graph_name")
    private String graphName;
    
    @Column(name = "aliada_ontology", nullable = false)
    private String aliadaOntologyNamespace;
    
    @Column(name = "start_date", nullable = true)
    private Timestamp startDate;

    @Column(name = "end_date", nullable = true)
    private Timestamp endDate;

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
     * Returns the data format of this job configuration.
     * 
     * @return the data format of this job configuration.
     */
	public String getFormat() {
		return format;
	}

	/**
	 * Sets the data format of this job configuration.
	 * 
	 * @param format the data format of this job configuration.
	 */
	public void setFormat(final String format) {
		this.format = format;
	}

    /**
     * Returns the datafile of this job configuration.
     * 
     * @return the datafile of this job configuration.
     */
	public String getDatafile() {
		return datafile;
	}

	/**
	 * Sets the datafile of this job configuration.
	 * 
	 * @param datafile the datafile of this job configuration.
	 */
	public void setDatafile(final String datafile) {
		this.datafile = datafile;
	}
	
    /**
     * Returns the namespace of this job configuration.
     * 
     * @return the namespace of this job configuration.
     */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * Sets the namespace of this job configuration.
	 * 
	 * @param namespace the datafile of this job configuration.
	 */
	public void setNamespace(final String namespace) {
		this.namespace = namespace;
	}	
	
    /**
     * Returns the ALIADA ontology namespace of this job configuration.
     * 
     * @return the ALIADA ontology namespace of this job configuration.
     */
	public String getAliadaOntologyNamespace() {
		return aliadaOntologyNamespace;
	}

	/**
	 * Sets the ALIADA ontology namespace of this job configuration.
	 * 
	 * @param aliadaOntologyNamespace the datafile of this job configuration.
	 */
	public void setAliadaOntologyNamespace(final String aliadaOntologyNamespace) {
		this.aliadaOntologyNamespace = aliadaOntologyNamespace;
	}

	/**
	 * Returns the start date of this job.
	 * 
	 * @return the start date of this job.
	 */
	public Timestamp getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date of this job.
	 * 
	 * @param startDate the start date of this job.
	 */
	public void setStartDate(final Timestamp startDate) {
		this.startDate = startDate;
	}

	/**
	 * Returns the end date of this job.
	 * 
	 * @return the end date of this job.
	 */
	public Timestamp getEndDate() {
		return endDate;
	}

	/**
	 * Sets the end date of this job.
	 * 
	 * @param endDate the end date of this job.
	 */
	public void setEndDate(final Timestamp endDate) {
		this.endDate = endDate;
	}

	/**
	 * Returns the name of the graph that will be associated with this job.
	 * 
	 * @return the name of the graph that will be associated with this job.
	 */
	public String getGraphName() {
		return graphName;
	}

	/**
	 * Sets the name of the graph that will be associated with this job.
	 * 
	 * @param graphName the name of the graph that will be associated with this job.
	 */
	public void setGraphName(final String graphName) {
		this.graphName = graphName;
	}		
	
	
}