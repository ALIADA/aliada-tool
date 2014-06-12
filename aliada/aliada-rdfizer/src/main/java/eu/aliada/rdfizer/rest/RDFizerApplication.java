// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.rest;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * RDF-izer resource representation.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class RDFizerApplication extends ResourceConfig {
	/**
	 * Builds a new RDF-izer resource.
	 */
	public RDFizerApplication() {
		packages(JobResource.class.getPackage().getName());
	}
}