// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-shared
// Responsible: ALIADA Consortium

package eu.aliada.shared.rdfstore;

/**
 * Retrieved Work.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
public class RetrievedWork {
	/** Work URI. */
	private String workURI;
	/** Expression URI. */
	private String exprURI;
	/** Manifestation URI. */
	private String manifURI;
	/** Work title. */
	private String title;
	/** Dimensions. */
	private String dimensions;
	/** Extension. */
	private String extension;
	/** Author. */
	private String author;
	/** Publication Place. */
	private String publicPlace;
	/** Publication Date. */
	private String publicDate;
	/** Edition. */
	private String edition;
   
	/**
	 * Returns the work URI.
	 * 
	 * @return The work URI.
	 * @since 2.0
	 */
	public String getWorkURI() {
		return workURI;
	}
	/**
	 * Sets the work URI.
	 * 
	 * @param workURI The work URI.
	 * @since 2.0
	 */
	public void setWorkURI(final String workURI) {
		this.workURI = workURI;
	}

	/**
	 * Returns the expression URI.
	 * 
	 * @return The expression URI.
	 * @since 2.0
	 */
	public String getExprURI() {
		return exprURI;
	}
	/**
	 * Sets the expression URI.
	 * 
	 * @param exprURI The expression URI.
	 * @since 2.0
	 */
	public void setExprURI(final String exprURI) {
		this.exprURI = exprURI;
	}

	/**
	 * Returns the manifestation URI.
	 * 
	 * @return The manifestation URI.
	 * @since 2.0
	 */
	public String getManifURI() {
		return manifURI;
	}
	/**
	 * Sets the manifestation URI.
	 * 
	 * @param manifURI The manifestation URI.
	 * @since 2.0
	 */
	public void setManifURI(final String manifURI) {
		this.manifURI = manifURI;
	}

	/**
	 * Returns the work title.
	 * 
	 * @return The work title.
	 * @since 2.0
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Sets the work title.
	 * 
	 * @param title The work title.
	 * @since 2.0
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * Returns the dimensions.
	 * 
	 * @return The dimensions.
	 * @since 2.0
	 */
	public String getDimensions() {
		return dimensions;
	}
	/**
	 * Sets the dimensions.
	 * 
	 * @param dimensions The dimensions.
	 * @since 2.0
	 */
	public void setDimensions(final String dimensions) {
		this.dimensions = dimensions;
	}

	/**
	 * Returns the extension.
	 * 
	 * @return The extension.
	 * @since 2.0
	 */
	public String getExtension() {
		return extension;
	}
	/**
	 * Sets the extension.
	 * 
	 * @param extension The extension.
	 * @since 2.0
	 */
	public void setExtension(final String extension) {
		this.extension = extension;
	}

	/**
	 * Returns the author.
	 * 
	 * @return The author.
	 * @since 2.0
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * Sets the author.
	 * 
	 * @param author The author.
	 * @since 2.0
	 */
	public void setAuthor(final String author) {
		this.author = author;
	}

	/**
	 * Returns the publication place.
	 * 
	 * @return The publication place.
	 * @since 2.0
	 */
	public String getPublicPlace() {
		return publicPlace;
	}
	/**
	 * Sets the publication place.
	 * 
	 * @param publicPlace The publication place.
	 * @since 2.0
	 */
	public void setPublicPlace(final String publicPlace) {
		this.publicPlace = publicPlace;
	}

	/**
	 * Returns the publication date.
	 * 
	 * @return The publication date.
	 * @since 2.0
	 */
	public String getPublicDate() {
		return publicDate;
	}
	/**
	 * Sets the publication date.
	 * 
	 * @param publicDate The publication date.
	 * @since 2.0
	 */
	public void setPublicDate(final String publicDate) {
		this.publicDate = publicDate;
	}


	/**
	 * Returns the edition.
	 * 
	 * @return The edition.
	 * @since 2.0
	 */
	public String getEdition() {
		return edition;
	}
	/**
	 * Sets the edition.
	 * 
	 * @param edition The edition.
	 * @since 2.0
	 */
	public void setEdition(final String edition) {
		this.edition = edition;
	}

}
