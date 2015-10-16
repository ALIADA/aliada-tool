// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.model;

/**
 * External Dataset information.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
public class ExternalDataset {

	/** The dataset name. */
	private String name;
	/** The dataset name in CKAN Datahub. */
	private String ckanName;

	/**
	 * Constructor.
	 * 
	 * @since 2.0
	 */
	public ExternalDataset() {
	}

	/**
	 * Constructor.
	 * 
	 * @param name			the dataset name.
	 * @param ckanName		the dataset name in CKAN Datahub.
	 * @since 2.0
	 */
	public ExternalDataset(final String name, final String ckanName)
	{
		this.name = name;
		this.ckanName = ckanName;
	}
	
	/**
	 * Returns the dataset name.
	 * 
	 * @return The dataset name.
	 * @since 2.0
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Sets the dataset name.
	 * 
	 * @param name The dataset name.
	 * @since 2.0
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Returns the dataset name in CKAN Datahub.
	 * 
	 * @return The dataset name in CKAN Datahub.
	 * @since 2.0
	 */
	public String getCkanName() {
		return this.ckanName;
	}
	/**
	 * Sets the dataset name in CKAN Datahub.
	 * 
	 * @param ckanName The dataset name in CKAN Datahub.
	 * @since 2.0
	 */
	public void setCkanName(final String ckanName) {
		this.ckanName = ckanName;
	}
}
