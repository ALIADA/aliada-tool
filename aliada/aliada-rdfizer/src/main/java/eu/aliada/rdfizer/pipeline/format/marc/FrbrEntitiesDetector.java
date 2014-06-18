// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.marc4j.marc.Record;
import org.springframework.beans.factory.annotation.Autowired;

import eu.aliada.rdfizer.pipeline.common.RecordSplitter;
import eu.aliada.rdfizer.pipeline.format.marc.selector.FirstMatch;
import eu.aliada.shared.log.Log;

/**
 * Extracts from a given (MARC) record, the FRBR entities.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class FrbrEntitiesDetector implements Processor {
	private Log log = new Log(RecordSplitter.class);

	@Autowired
	private FirstMatch workDetectionExpression;
	
	// TODO: JMX per entità individuate e "mancate"
	
	@Override
	public void process(final Exchange exchange) throws Exception {
		final Record record = exchange.getIn().getBody(Record.class);

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
