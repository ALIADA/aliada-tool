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
import eu.aliada.rdfizer.datasource.rdbms.JobInstance;
import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.rdfizer.pipeline.format.marc.frbr.model.FrbrDocument;
import eu.aliada.rdfizer.pipeline.format.xml.NullObject;
import eu.aliada.shared.log.Log;

/**
 * Extracts from a given (MARC) record, the FRBR entities.
 * 
 * @author Emiliano Cammilletti
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
	
	@Autowired
	PersonDetector personDetector;
	
	@Autowired
	FamilyDetector familyDetector;
	
	@Autowired
	CorporateBodyDetector corporateBodyDetector;
	
	@Autowired
	ItemDetector itemDetector;
		
	@Autowired
	private Cache cache;

	@Override
	public void process(final Exchange exchange) throws Exception {
		final Message in = exchange.getIn();
		final Integer jobId = in.getHeader(Constants.JOB_ID_ATTRIBUTE_NAME, Integer.class);
		final JobInstance configuration = cache.getJobInstance(jobId);
		if (configuration == null) {
			log.error(MessageCatalog._00038_UNKNOWN_JOB_ID, jobId);
			throw new IllegalArgumentException(String.valueOf(jobId));
		}

		final FrbrDocument entitiesDocument = frbrDetection(exchange.getIn().getBody(Document.class));
		if (entitiesDocument.isValid()) {
			in.setBody(entitiesDocument);
		} else {
			log.debug(MessageCatalog._00041_FRBR_ENTITY_DETECTION_FAILED);
			in.setBody(NullObject.instance);
		}
	}

	/**
	 * Detects the FRBR entities.
	 * 
	 * @param target the current record (as {@link Document}). 
	 * @return the FRBR value object.
	 */
	FrbrDocument frbrDetection(final Document target) {
		return new FrbrDocument(
				target,
				AbstractEntityDetector.identifier(workDetector.detect(target)),
				AbstractEntityDetector.identifier(expressionDetector.detect(target)),
				manifestationDetector.detect(target),
				personDetector.detect(target),
				familyDetector.detect(target),
				corporateBodyDetector.detect(target),
				itemDetector.detect(target)
				);
	}
}