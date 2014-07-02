// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr.model;

import java.io.Serializable;

import org.w3c.dom.Document;

/** 
 * A Value Object that encapsulates a MARC record and some FRBR concepts (i.e. entities URIs).
 * 
 * @author Emiliano Cammilletti
 * @since 1.0
 */
public final class FrbrDocument implements Serializable {

	private static final long serialVersionUID = 203862468772292056L;

	private final Document document;
	
	private final String workURI;
	private final String expressionURI;
	private final String manifestationURI;
	
	/**
	 * Builds a new {@link FrbrDocument} with the given DOM {@link Document}.
	 * 
	 * @param document the {@link Document}.
	 * @param workURI the URI that has been assigned to the Work.
	 * @param expressionURI the URI that has been assigned to the Expression.
	 * @param manifestationURI the URI that has been assigned to the Manifestation.
	 */
	public FrbrDocument(
			final Document document,
			final String workURI,
			final String expressionURI,
			final String manifestationURI) {
		this.document = document;
		this.workURI = workURI;
		this.expressionURI = expressionURI;
		this.manifestationURI = manifestationURI;
	}
	
	/**
	 * Returns the URI that has been associated with the Work.
	 * 
	 * @return the URI that has been associated with the Work.
	 */
	public String getWorkURI() {
		return workURI;
	}
	
	/**
	 * Returns the URI that has been associated with the Expression.
	 * 
	 * @return the URI that has been associated with the Expression.
	 */
	public String getExpressionURI() {
		return expressionURI;
	}

	/**
	 * Returns the URI that has been associated with the Manifestation.
	 * 
	 * @return the URI that has been associated with the Manifestation.
	 */
	public String getManifestationURI() {
		return manifestationURI;
	}
	
	/**
	 * Returns the DOM {@link Document} associated with this instance.
	 * 
	 * @return the DOM {@link Document} associated with this instance.
	 */
	public Document getDocument() {
		return document;
	}
	
	/**
	 * Checks if the detected FRBR structure can be considered valid for further processing.
	 * 
	 * @return true if the detected FRBR structure can be considered valid, otherwise false.
	 */
	public boolean isValid() {
		return workURI != null && expressionURI != null && manifestationURI != null && document != null;
	}
}