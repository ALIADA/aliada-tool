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
	
	private final String workID;
	private final String expressionID;
	private final String manifestationID;
	
	/**
	 * Builds a new {@link FrbrDocument} with the given DOM {@link Document}.
	 * 
	 * @param document the {@link Document}.
	 * @param workID the ID that has been assigned to the Work.
	 * @param expressionID the ID that has been assigned to the Expression.
	 * @param manifestationID the ID that has been assigned to the Manifestation.
	 */
	public FrbrDocument(
			final Document document,
			final String workID,
			final String expressionID,
			final String manifestationID) {
		this.document = document;
		this.workID = workID;
		this.expressionID = expressionID;
		this.manifestationID = manifestationID;
	}
	
	/**
	 * Returns the ID that has been associated with the Work.
	 * 
	 * @return the ID that has been associated with the Work.
	 */
	public String getWorkID() {
		return workID;
	}
	
	/**
	 * Returns the ID that has been associated with the Expression.
	 * 
	 * @return the ID that has been associated with the Expression.
	 */
	public String getExpressionID() {
		return expressionID;
	}

	/**
	 * Returns the ID that has been associated with the Manifestation.
	 * 
	 * @return the ID that has been associated with the Manifestation.
	 */
	public String getManifestationID() {
		return manifestationID;
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
		return workID != null && expressionID != null && manifestationID != null && document != null;
	}
}