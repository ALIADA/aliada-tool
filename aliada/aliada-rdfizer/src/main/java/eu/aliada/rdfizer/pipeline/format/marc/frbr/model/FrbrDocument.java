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

import eu.aliada.rdfizer.pipeline.format.marc.frbr.FrbrExpression;
import eu.aliada.rdfizer.pipeline.format.marc.frbr.cluster.Cluster;

/** 
 * A Value Object that encapsulates a MARC record and some FRBR concepts (i.e. entities URIs).
 * 
 * @author Emiliano Cammilletti
 * @since 1.0
 */
public final class FrbrDocument implements Serializable {

	private static final long serialVersionUID = 203862468772292056L;

	private final Document document;
	private final Map<String, List<Cluster>> worksIDs;
	private final List<FrbrExpression> expressionIDs;
	private final String manifestationID;
	private final Map<String, List<Cluster>> personIDs;
	private final Map<String, List<Cluster>> familyIDs;
	private final Map<String, List<Cluster>> corporateBodyIDs;
	private final Map<String, List<String>> itemIDs;
	private final Map<String, List<String>> conceptIDs;
	private final Map<String, List<String>> eventIDs;
	private final Map<String, List<String>> placeIDs;
	

	/**
	 * Builds a new {@link FrbrDocument} with the given DOM {@link Document}.
	 * 
	 * @param document the {@link Document}.
	 * @param workIDs the URI that has been assigned to the Work.
	 * @param expressionIDs the URI that has been assigned to the Expression.
	 * @param personIDs a map with list of person
	 * @param familyIDs a map with list of family
	 * @param corporateBodyIDs a map with list of corporateBody
	 * @param itemIDs a map with list of item
	 * @param manifestationID the URI that has been assigned to the Manifestation.
	 */
	public FrbrDocument(
			final Document document,
			final Map<String, List<Cluster>> workIDs,
			final List<FrbrExpression> expressionIDs,
			final String manifestationID,
			final Map<String, List<Cluster>> personIDs, 
			final Map<String, List<Cluster>> familyIDs,
			final Map<String, List<Cluster>> corporateBodyIDs,
			final Map<String, List<String>> itemIDs,
			final Map<String, List<String>> conceptIDs,
			final Map<String, List<String>> eventIDs,
			final Map<String, List<String>> placeIDs) {
		this.document = document;
		this.worksIDs = workIDs;
		this.expressionIDs = expressionIDs;
		this.manifestationID = manifestationID;
		this.personIDs = personIDs;
		this.familyIDs = familyIDs;
		this.corporateBodyIDs = corporateBodyIDs;
		this.itemIDs = itemIDs;
		this.conceptIDs = conceptIDs;
		this.eventIDs = eventIDs;
		this.placeIDs = placeIDs;
	}
	
	/**
	 * Return a map with tag as key and a List of String which represent the URIs.
	 * 
	 * @return the itemURI
	 */
	public  Map<String, List<String>> getItemIDs() {
		return itemIDs;
	}
	
	/**
	 * Returns the identifiers that have been associated with the detected Works.
	 * 
	 * @return the identifiers that have been associated with the detected Works.
	 */
	public Map<String, List<Cluster>> getWorkIDs() {
		return worksIDs;
	}
	
	/**
	 * Returns the URI that has been associated with the Expression.
	 * 
	 * @return the URI that has been associated with the Expression.
	 */
	public List<FrbrExpression> getExpressionIDs() {
		return expressionIDs;
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
		return worksIDs != null && expressionIDs != null && manifestationID != null && document != null;
	}
	
	/**
	 * Return a map with tag as key and a List of String which represent the URIs.
	 * @return the personURI
	 */
	public  Map<String, List<Cluster>> getPersonURIs() {
		return personIDs;
	}

	/**
	 * Return a map with tag as key and a List of String which represent the URIs.
	 * @return the familyURI
	 */
	public  Map<String, List<Cluster>> getFamilyURIs() {
		return familyIDs;
	}

	/**
	 * Return a map with tag as key and a List of String which represent the URIs.
	 * @return the corporateBodyURI
	 */
	public  Map<String, List<Cluster>> getCorporateBodyIDs() {
		return corporateBodyIDs;
	}


	/**
	 * Return a map with tag as key and a List of String which represent the URIs.
	 * @return the conceptID
	 */
	public Map<String, List<String>> getConceptIDs() {
		return conceptIDs;
	}
	
	/**
	 * Return a map with tag as key and a List of String which represent the URIs.
	 * @return the eventID
	 */
	public Map<String, List<String>> getEventIDs() {
		return eventIDs;
	}
	
	/**
	 * Return a map with tag as key and a List of String which represent the URIs.
	 * @return the placeID
	 */
	public Map<String, List<String>> getPlaceIDs() {
		return placeIDs;
	}
}