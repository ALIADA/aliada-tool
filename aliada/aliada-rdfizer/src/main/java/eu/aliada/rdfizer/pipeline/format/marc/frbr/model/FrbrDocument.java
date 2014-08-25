// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
	private final Map<String, List<String>> personID;
	private final Map<String, List<String>> familyID;
	private final Map<String, List<String>> corporateBodyID;
	private final Map<String, List<String>> itemID;
	
	
	/**
	 * Builds a new {@link FrbrDocument} with the given DOM {@link Document}.
	 * 
	 * @param document the {@link Document}.
	 * @param workURI the URI that has been assigned to the Work.
	 * @param expressionURI the URI that has been assigned to the Expression.
	 * @param personURI a map with list of person
	 * @param familyURI a map with list of family
	 * @param corporateBodyURI a map with list of corporateBody
	 * @param itemURI a map with list of item
	 * @param manifestationURI the URI that has been assigned to the Manifestation.
	 */
	public FrbrDocument(
			final Document document,
			final String workURI,
			final String expressionURI,
			final String manifestationURI,
			final Map<String, List<String>> personURI, 
			final Map<String, List<String>> familyURI,
			final Map<String, List<String>> corporateBodyURI,
			final Map<String, List<String>> itemURI) {
		this.document = document;
		this.workID = workURI;
		this.expressionID = expressionURI;
		this.manifestationID = manifestationURI;
		this.personID = personURI;
		this.familyID = familyURI;
		this.corporateBodyID = corporateBodyURI;
		this.itemID = itemURI;
	}
	
	/**
	 * Returns the URI that has been associated with the Work.
	 * 
	 * @return the URI that has been associated with the Work.
	 */
	public String getWorkID() {
		return workID;
	}
	
	/**
	 * Returns the URI that has been associated with the Expression.
	 * 
	 * @return the URI that has been associated with the Expression.
	 */
	public String getExpressionID() {
		return expressionID;
	}

	/**
	 * Returns the URI that has been associated with the Manifestation.
	 * 
	 * @return the URI that has been associated with the Manifestation.
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
	
	/**
	 * Return a map with tag as key and a List of String which represent the URIs.
	 * @return the personURI
	 */
	public  Map<String, List<String>> getPersonID() {
		return personID;
	}

	/**
	 * Return a map with tag as key and a List of String which represent the URIs.
	 * @return the familyURI
	 */
	public  Map<String, List<String>> getFamilyID() {
		return familyID;
	}

	/**
	 * Return a map with tag as key and a List of String which represent the URIs.
	 * @return the corporateBodyURI
	 */
	public  Map<String, List<String>> getCorporateBodyID() {
		return corporateBodyID;
	}

	/**
	 * Return a map with tag as key and a List of String which represent the URIs.
	 * 
	 * @return the itemURI
	 */
	public  Map<String, List<String>> getItemID() {
		return itemID;
	}
}