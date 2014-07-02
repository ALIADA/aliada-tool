// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.datasource.rdbms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * RDF-izer Job entity.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@Entity
@Table(name = "rdfizer_job_instances")
public class JobConfiguration {
    @Id
    @Column(name = "job_id", nullable = false)
    private Integer id;
    
    @Column(name = "format", nullable = false)
    private String format;
    
    @Column(name = "datafile", nullable = false)
    private String datafile;

    @Column(name = "namespace", nullable = false)
    private String namespace;
        
    @Column(name = "aliada_ontology", nullable = false)
    private String aliadaOntonologyNamespace;
    
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
		return aliadaOntonologyNamespace;
	}

	/**
	 * Sets the ALIADA ontology namespace of this job configuration.
	 * 
	 * @param aliadaOntonologyNamespace the datafile of this job configuration.
	 */
	public void setAliadaOntologyNamespace(final String aliadaOntonologyNamespace) {
		this.aliadaOntonologyNamespace = aliadaOntonologyNamespace;
	}		
}