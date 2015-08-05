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
import org.springframework.beans.factory.annotation.Qualifier;
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
	@Qualifier("workDetector")
	WorkDetector workDetector;
	
	@Autowired
	@Qualifier("expressionDetector")
	ExpressionDetector expressionDetector;
	
	@Autowired
	@Qualifier("manifestationDetector")
	ManifestationDetector manifestationDetector;
	
	@Autowired
	@Qualifier("personDetector")
	MultiMapEntityDetector personDetector;
	
	@Autowired
	@Qualifier("familyDetector")
	MultiMapEntityDetector familyDetector;
	
	@Autowired
	@Qualifier("corporateBodyDetector")
	MultiMapEntityDetector corporateBodyDetector;
	
	@Autowired
	@Qualifier("itemDetector")
	MultiMapEntityDetector itemDetector;
	
	@Autowired
	@Qualifier("conceptDetector")
	MultiMapEntityDetector conceptDetector;
	
	@Autowired
	@Qualifier("eventDetector")
	MultiMapEntityDetector eventDetector;
	
	@Autowired
	@Qualifier("placeDetector")
	MultiMapEntityDetector placeDetector;
	
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
		if (isValid(entitiesDocument)) {
			in.setBody(entitiesDocument);
		} else {
			log.debug(MessageCatalog._00041_FRBR_ENTITY_DETECTION_FAILED);
			in.setBody(NullObject.instance);
		}
	}

	/**
	 * Returns true if the FRBR document is valid according with the rules of the current conversion.
	 * 
	 * @param entitiesDocument the FRBR entities collector.
	 * @return true if the FRBR document is valid according with the rules of the current conversion.
	 */
	protected boolean isValid(final FrbrDocument entitiesDocument) {
		return entitiesDocument.isValid();
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
				AbstractEntityDetector.identifier(workDetector.entityKind(), workDetector.detect(target)),
				AbstractEntityDetector.identifier(expressionDetector.entityKind(), expressionDetector.detect(target)),
				manifestationDetector.detect(target),
				personDetector.detect(target),
				familyDetector.detect(target),
				corporateBodyDetector.detect(target),
				itemDetector.detect(target),
				conceptDetector.detect(target),
				eventDetector.detect(target),
				placeDetector.detect(target));
	}
}