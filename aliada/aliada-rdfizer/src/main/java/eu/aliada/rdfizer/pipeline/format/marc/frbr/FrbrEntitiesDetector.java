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
	

	@Override
	public void process(final Exchange exchange) throws Exception {
		final Document target = exchange.getIn().getBody(Document.class);
		
	//	System.out.println(workDetector.getFirstMatchUniformTitle().evaluate(target));
		
		String w = 	workDetector.getFirstMatchForUniformTitle().evaluate(target) + " -- " + 
					workDetector.getFirstMatchForDate().evaluate(target) + " -- " +
					workDetector.getFirstMatchForOtherStandardIdentifier().evaluate(target) + " -- " +
					workDetector.getFirstMatchForCharacterDistinguish().evaluate(target);
		
		String e = 	expressionDetector.getFirstMatchForcontentType().evaluate(target) + " -- " + 
				expressionDetector.getFirstMatchForDate().evaluate(target) + " -- " +
				expressionDetector.getFirstMatchForOtherDistinguishFeatureForWork().evaluate(target) + " -- " +
				expressionDetector.getFirstMatchLanguage().evaluate(target);
	
		String m = manifestationDetector.getFirstMatchForControlNumber().evaluate(target);
		
		
		exchange.getIn().setBody(m + " |" + w + "| " + e);
		
		// 1 group
//		detectWork(record);
//		
//		detectExpression(record);
//		
//		detectManifestation(record);
//		
//		detectItem(record);
		
		// 2 group
		// ...
	}


	
//	@Override
//	public void dispatchForFurtherProcessing(final Record record) {
//		producer.send(new Processor() {
//			@Override
//			public void process(final Exchange outExchange) {
//				outExchange.getIn().setBody(record);
//			}
//		});
//	}	
}
