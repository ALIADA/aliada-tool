// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.model;

/**
 * @author xabi
 * @since 1.0
 */

public class Tags {
	private String tagId;
	private boolean selected;
	private int mandatory;
	private int entity;
	private String tagDescription;
	
	/**
     * @return Returns the tagId.
     * @exception
     * @since 1.0
     */
    public String getTagId() {
        return tagId;
    }
    /**
     * @param tagId The tagId to set.
     * @exception
     * @since 1.0
     */
	public void setTagId(final String tagId) {
		this.tagId = tagId;
	}
    /**
     * @return Returns the selected.
     * @exception
     * @since 1.0
     */
	public boolean isSelected() {
		return selected;
	}
	/**
     * @param selected The selected to set.
     * @exception
     * @since 1.0
     */
	public void setSelected(final boolean selected) {
		this.selected = selected;
	}
	/**
     * @return Returns the mandatory.
     * @exception
     * @since 1.0
     */
	public int getMandatory() {
		return mandatory;
	}
	/**
     * @param mandatory The mandatory to set.
     * @exception
     * @since 1.0
     */
	public void setMandatory(final int mandatory) {
		this.mandatory = mandatory;
	}
	/**
     * @return Returns the entity.
     * @exception
     * @since 1.0
     */
	public int getEntity() {
		return entity;
	}
	/**
     * @param entity The entity to set.
     * @exception
     * @since 1.0
     */
	public void setEntity(final int entity) {
		this.entity = entity;
	}
	/**
     * @return Returns the tagDescription.
     * @exception
     * @since 1.0
     */
	public String getTagDescription() {
		return tagDescription;
	}
	/**
     * @param tagDescription The tagDescription to set.
     * @exception
     * @since 1.0
     */
	public void setTagDescription(final String tagDescription) {
		this.tagDescription = tagDescription;
	}
}
