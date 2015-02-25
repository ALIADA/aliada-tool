// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.model;

/**
 * @author xabier
 * @since 1.0
 */
public class Triples {
	
	private String value;
	private String subject;
	private String object;
	/** Default Constructor.
	 *  
	 */
	public Triples() {
		
	}
	/** Constructor with parameters.
	 *  @param value The parameter value to create a Triples object.
	 *  @param subject The parameter subject to create a Triples object.
	 *  @param object The parameter object to create a Triples object.
	 */
	public Triples(final String value, final String subject, final String object) {
		super();
		this.value = value;
		this.subject = subject;
		this.object = object;
	}
	/**
     * @return Returns the value.
     * @exception
     * @since 1.0
     */
	public String getValue() {
		return value;
	}
	/**
     * @param value The value to set.
     * @exception
     * @since 1.0
     */
	public void setValue(final String value) {
		this.value = value;
	}
	/**
     * @return Returns the subject.
     * @exception
     * @since 1.0
     */
	public String getSubject() {
		return subject;
	}
	/**
     * @param subject The subject to set.
     * @exception
     * @since 1.0
     */
	public void setSubject(final String subject) {
		this.subject = subject;
	}
	/**
     * @return Returns the object.
     * @exception
     * @since 1.0
     */
	public String getObject() {
		return object;
	}
	/**
     * @param object The object to set.
     * @exception
     * @since 1.0
     */
	public void setObject(final String object) {
		this.object = object;
	}

}
