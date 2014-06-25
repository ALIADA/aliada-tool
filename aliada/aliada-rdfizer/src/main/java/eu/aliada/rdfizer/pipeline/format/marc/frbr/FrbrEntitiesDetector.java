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
	
	// TODO: JMX per entità individuate e "mancate"
	

	@Override
	public void process(final Exchange exchange) throws Exception {
		final Document target = exchange.getIn().getBody(Document.class);
		
	//	System.out.println(workDetector.getFirstMatchUniformTitle().evaluate(target));
		
		String s = 	workDetector.getFirstMatchUniformTitle().evaluate(target) + " -- " + 
					workDetector.getFirstMatchOrderedWorkSequenceTagForDate().evaluate(target) + " -- " +
					workDetector.getFirstMatchOrderedWorkSequenceTagForOtherStandardIdentifier().evaluate(target) + " -- " +
					workDetector.getFirstMatchOrderedWorkSequenceTagForCharacterDistinguish().evaluate(target);
		
		exchange.getIn().setBody(s);
		
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

	public WorkDetector getWorkDetector() {
		return workDetector;
	}

	public void setWorkDetector(WorkDetector workDetector) {
		this.workDetector = workDetector;
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
