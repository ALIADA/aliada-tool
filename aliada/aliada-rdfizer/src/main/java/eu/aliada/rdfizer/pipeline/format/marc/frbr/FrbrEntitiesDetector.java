// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;

import eu.aliada.rdfizer.Constants;
import eu.aliada.rdfizer.datasource.Cache;
import eu.aliada.rdfizer.pipeline.format.marc.frbr.model.EntitiesUriDocument;
import eu.aliada.shared.log.Log;

/**
 * Extracts from a given (MARC) record, the FRBR entities.
 * 
 * @author Emiliano Cammilletti
 * @since 1.0
 */
public class FrbrEntitiesDetector implements Processor {
	private Log log = new Log(FrbrEntitiesDetector.class);

	@Autowired
	WorkDetector workDetector;
	
	@Autowired
	ExpressionDetector expressionDetector;
	
	@Autowired
	ManifestationDetector manifestationDetector;
	
	/*
 	* TODO  This attibute must be configurable
 	* 
 	*/
	private String uriConstant = "/id/resource/";
	
	// TODO: JMX per entità individuate e "mancate"
	@Autowired
	private Cache cache;

	@Override
	public void process(final Exchange exchange) throws Exception {
		
		final Message in = exchange.getIn();
		final Integer jobId = in.getHeader(Constants.JOB_ID_ATTRIBUTE_NAME, Integer.class);
		final String namespace = cache.getJobConfiguration(jobId).getNamespace();
		
		final Document target = exchange.getIn().getBody(Document.class);
		
		EntitiesUriDocument entitiesDocument = new EntitiesUriDocument(target);
		entitiesDocument.setWorkUri(namespace + uriConstant + "work/" + entitiesDocument.getIdentifier(workDetector.concat(target)));
		entitiesDocument.setExpressionUri(namespace + uriConstant + "expression/" + entitiesDocument.getIdentifier(expressionDetector.concat(target)));
		entitiesDocument.setManifestationUri(namespace + uriConstant + "manifestation/" + manifestationDetector.concat(target));
		
		exchange.getIn().setBody(entitiesDocument);
	}
}
