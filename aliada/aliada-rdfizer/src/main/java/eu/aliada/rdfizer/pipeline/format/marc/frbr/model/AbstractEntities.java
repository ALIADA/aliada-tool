// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr.model;

import java.util.UUID;

import org.w3c.dom.Document;

/** 
 * 
 * Base Class for Entities Uri mapping.
 * 
 * @author Emiliano Cammilletti
 * @since 1.0
 */
public class AbstractEntities {
	
	@SuppressWarnings("unused")
	private Document document;
	
	/**
	 * 
	 * @param document 	
	 */
	public AbstractEntities(final Document document) {
		this.document = document;
	}	
	
	/**
	 * Return an unique identifier from a simple String.
	 * 
	 * @TODO
	 * @param s return value
	 * @return the generated code
	 */
	public String getIdentifier(final String s) {
		return  UUID.nameUUIDFromBytes(s.getBytes()).toString();
	}
	
}
