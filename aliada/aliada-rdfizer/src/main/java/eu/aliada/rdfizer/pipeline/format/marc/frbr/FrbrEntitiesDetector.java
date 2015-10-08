// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.w3c.dom.Document;

import eu.aliada.rdfizer.Constants;
import eu.aliada.rdfizer.Function;
import eu.aliada.rdfizer.datasource.Cache;
import eu.aliada.rdfizer.datasource.rdbms.JobInstance;
import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.rdfizer.pipeline.format.marc.frbr.cluster.Cluster;
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
	private Function function;
	
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
	
	protected String work(final Document target) {
		return AbstractEntityDetector.identifier(
				workDetector.entityKind(), 
				workDetector.detect(target));
	}
	
	protected String expression(final Document target) {
		return AbstractEntityDetector.identifier(
				expressionDetector.entityKind(), 
				expressionDetector.detect(target));
	}
	
	protected String manifestation(final Document target) {
		return AbstractEntityDetector.identifier(
				manifestationDetector.entityKind(), 
				manifestationDetector.detect(target));
	}	
	
	protected Map<String, List<Cluster>> people(final Document target) {
		return clusteredNames(target, personDetector);
	}	
	
	protected Map<String, List<Cluster>> families(final Document target) {
		return clusteredNames(target, familyDetector);
	}			

	protected Map<String, List<Cluster>> corporates(final Document target) {
		return clusteredNames(target, corporateBodyDetector);
	}		
	
	protected Map<String, List<String>> items(final Document target) {
		return itemDetector.detect(target);
	}		
	
	protected Map<String, List<String>> concepts(final Document target) {
		return conceptDetector.detect(target);
	}			
	
	protected Map<String, List<String>> events(final Document target) {
		return eventDetector.detect(target);
	}		

	protected Map<String, List<String>> places(final Document target) {
		return placeDetector.detect(target);
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
				work(target),
				expression(target),
				manifestation(target),
				people(target),
				families(target),
				corporates(target),
				items(target),
				concepts(target),
				events(target),
				places(target));
	}
	
	Map<String, List<Cluster>> clusteredNames(final Document target, final MultiMapEntityDetector detector) {
		final Map<String, List<String>> entities = detector.detect(target);
		final Map<String, List<Cluster>> result = new HashMap<String, List<Cluster>>(entities.size());
		for (final Entry<String, List<String>> entry : entities.entrySet()) {
			result.put(
					entry.getKey(), 
					entry.getValue()
						.stream()
						.map(heading -> function.getNameCluster(heading))
						.collect(Collectors.toList()));
		}

		return result;		
	}		
}