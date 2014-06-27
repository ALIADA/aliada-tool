// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import org.w3c.dom.Document;

/**
 * Base class for Detecting entities.
 * 
 * @author Emiliano Cammilletti
 * @since 1.0
*/
public abstract class AbstractEntityDetecor {

 /**
  * @param target element
  * @return target the target
  */
  abstract String concat(Document target);
	
}
