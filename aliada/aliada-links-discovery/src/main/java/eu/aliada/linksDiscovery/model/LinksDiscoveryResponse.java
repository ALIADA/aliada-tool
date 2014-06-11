// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.linksDiscovery.model;


import javax.xml.bind.annotation.XmlRootElement;

/**
 * Links discovery REST service returned structure. 
 * @author Idoia Murua
 * @since 1.0
 */
@XmlRootElement
public class LinksDiscoveryResponse {

	private boolean linksCreated;
	
	/**
	 * Get linksCreated property value.
	 *
     * @return			 linksCreated property value.					
     * @since 1.0
	 */
	public boolean getLinksCreated() {
		return this.linksCreated;
	}

	/**
	 * Set linksCreated property value.
	 *
     * @param created	The value for linksCreated property value.  
     *
     * @return 					
     * @since 1.0
	 */
	public void setLinksCreated(boolean created) {
		this.linksCreated = created;
	}
}
