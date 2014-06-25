// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.datasource.rdbms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * RDF-izer Job entity.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@Entity
@Table(name = "JOB_CONFIGURATION")
@NamedQueries({
    @NamedQuery(name = "JobConfiguration.findById", query = "SELECT j FROM JobConfiguration j WHERE j.id = :jobid")})
public class JobConfiguration {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;
    
    @Column(name = "FORMAT", nullable = false)
    private String format;
    
    @Column(name = "DATAFILE", nullable = false)
    private String datafile;

    @Column(name = "NAMESPACE", nullable = false)
    private String namespace;
    
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
}