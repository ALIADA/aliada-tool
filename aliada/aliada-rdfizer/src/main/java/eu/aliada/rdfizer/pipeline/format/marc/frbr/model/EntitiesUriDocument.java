// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr.model;

import org.w3c.dom.Document;

/** 
 * 
 * 
 * @author Emiliano Cammilletti
 * @since 1.0
 */
public class EntitiesUriDocument extends AbstractEntities{
	
	
	private String workUri;
	private String expressionUri;
	private String manifestationUri;
	
	/**
	 * 
	 * @param document 	
	 */
	public EntitiesUriDocument(final Document document) {
		super(document);
	}
	
	/**
	 * @return the workUri
	 */
	public final String getWorkUri() {
		return workUri;
	}
	/**
	 * @param workUri the workUri to set
	 */
	public final void setWorkUri(final String workUri) {
		this.workUri = workUri;
	}
	/**
	 * @return the expressionUri
	 */
	public final String getExpressionUri() {
		return expressionUri;
	}
	/**
	 * @param expressionUri the expressionUri to set
	 */
	public final void setExpressionUri(final String expressionUri) {
		this.expressionUri =  expressionUri;
	}
	/**
	 * @return the manifestationUri
	 */
	public final String getManifestationUri() {
		return manifestationUri;
	}
	/**
	 * @param manifestationUri the manifestationUri to set
	 */
	public final void setManifestationUri(final String manifestationUri) {
		this.manifestationUri = manifestationUri;
	}		
	
}
