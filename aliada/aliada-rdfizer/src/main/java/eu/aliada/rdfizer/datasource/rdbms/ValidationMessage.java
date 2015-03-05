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
 * RDF-izer (Error) Validation message entity.
 * 
 * @author Andrea Gazzarini
 * @since 2.0
 */
@Entity
@Table(name = "rdfizer_validation_messages")
public class ValidationMessage {
    @Id
    @Column(name = "job_id", nullable = false)
    private Integer id;
    
    @Column(name = "message_type", nullable = false)
    private String messageType;
    
    @Column(name = "description", nullable = false)
    private String description;
    
    public ValidationMessage() {}
    
    public ValidationMessage(
    		final Integer id, 
    		final String messageType, final 
    		String description) {
		this.id = id;
		this.messageType = messageType;
		this.description = description;
	}

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
     * Returns the validation message type.
     * 
     * @return the validation message type.
     */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * Sets the validation message type.
	 * 
	 * @param messageType the validation message type.
	 */
	public void setMessageType(final String messageType) {
		this.messageType = messageType;
	}

    /**
     * Returns the description message.
     * 
     * @return the description message.
     */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets  the description message.
	 * 
	 * @param description  the description message.
	 */
	public void setDescription(final String description) {
		this.description = description;
	}
}