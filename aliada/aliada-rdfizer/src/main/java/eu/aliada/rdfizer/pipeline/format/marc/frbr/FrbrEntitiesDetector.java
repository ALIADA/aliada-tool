// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;

import eu.aliada.rdfizer.datasource.Cache;
import eu.aliada.rdfizer.pipeline.format.marc.frbr.model.EntitiesUriDocument;
import eu.aliada.shared.log.Log;

/**
 * Extracts from a given (MARC) record, the FRBR entities.
 * 
 * @author Andrea Gazzarini
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
	
	// TODO: JMX per entità individuate e "mancate"
	@Autowired
	private Cache cache;

	@Override
	public void process(final Exchange exchange) throws Exception {
		final Document target = exchange.getIn().getBody(Document.class);
		
		EntitiesUriDocument entitiesDocument = new EntitiesUriDocument(target);
		entitiesDocument.setWorkUri("id/resource/work/" + entitiesDocument.getIdentifier(workDetector.concat(target)));
		entitiesDocument.setExpressionUri("id/resource/expression/" + entitiesDocument.getIdentifier(expressionDetector.concat(target)));
		entitiesDocument.setManifestationUri("id/resource/work/" + manifestationDetector.getFirstMatchForControlNumber());
		
		exchange.getIn().setBody(entitiesDocument);
		
	}

	
}
