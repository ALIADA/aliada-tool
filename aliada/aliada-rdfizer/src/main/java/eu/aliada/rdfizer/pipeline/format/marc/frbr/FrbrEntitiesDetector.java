// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	private final static boolean TRACE_FRBR_ENTITIES = Boolean.getBoolean("trace.frbr.entities");
	
	private Log log = new Log(FrbrEntitiesDetector.class);

	@Autowired
	@Qualifier("workDetector")
	MultiMapEntityDetector workDetector;
	
	@Autowired
	@Qualifier("expressionDetector")
	ExpressionDetector expressionDetector;
	
	@Autowired
	@Qualifier("manifestationDetector")
	UUIDManifestationDetector manifestationDetector;
	
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
	
	protected Map<String, List<Cluster>> works(final Document target) {
		return clusteredWorks(target, workDetector);
	}
	
	protected List<FrbrExpression> expression(final Document target, final Map<String, List<Cluster>> works) {
		return expressionDetector.detect(target);
	}
	
	protected String manifestation(final Document target) {
		return manifestationDetector.detect(target);
	}	
	
	protected Map<String, List<Cluster>> people(final Document target, final Map<String, List<Cluster>> works) {
		return clusteredNames(target, personDetector, works);
	}	
	
	protected Map<String, List<Cluster>> families(final Document target, final Map<String, List<Cluster>> works) {
		return clusteredNames(target, familyDetector, works);
	}			

	protected Map<String, List<Cluster>> corporates(final Document target, final Map<String, List<Cluster>> works) {
		return clusteredNames(target, corporateBodyDetector, works);
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
		final Map<String, List<Cluster>> works = works(target);
		final FrbrDocument document = new FrbrDocument(
				target,
				works,
				expression(target, works),
				manifestation(target),
				people(target, works),
				families(target, works),
				corporates(target, works),
				items(target),
				concepts(target),
				events(target),
				places(target));
		
		if (TRACE_FRBR_ENTITIES && log.isDebugEnabled()) {
			StringBuilder builder = new StringBuilder();
			builder.append("************************\n");
			builder.append("WORKs \n");
			
			works.forEach((k,v) -> {
				builder.append("Source tag = ").append(k).append(" (").append(v.size()).append("):\n");
				v.stream().forEach(cluster -> builder
						.append("\t - ")
						.append(cluster.getId())
						.append(" (").append(cluster.size()).append(" members) \n"));
			});
			
			builder.append("\n---------------\n");			
			if (document.getExpressionIDs() != null && !document.getExpressionIDs().isEmpty()) {
				builder.append("EXPRESSIONs \n");
				document
					.getExpressionIDs()
						.forEach(
								expression -> builder
												.append(expression.lang)
												.append(" ( Orphan : ")
												.append(expression.orphan)
												.append("):\n"));
				builder.append("\n---------------\n");			
			}
			builder.append("MANIFESTATION \n");
			builder.append(document.getManifestationID());
			builder.append("\n---------------\n");			
			builder.append("PERSONs \n");
			document.getPersonIDs().forEach((k,v) -> {
				builder.append("Source tag = ").append(k).append(" (").append(v.size()).append("):\n");
				v.stream().forEach(cluster -> builder
						.append("\t - ")
						.append(cluster.getId())
						.append(" (").append(cluster.size()).append(" members, has ")
						.append(cluster.parents().size())
						.append(" parents) \n"));
			});
			
			builder.append("\n---------------\n");			
			builder.append("CORPORATEs \n");
			
			builder.append("\n---------------\n");			
			builder.append("FAMILIes \n");
			
			log.debug(builder.toString());
		}
		return document;
	}
	
	/**
	 * Returns a map containing the source tag as key and a set of corresponding title clusters.
	 *  
	 * @param target the current MARC document (XML) representation.
	 * @param detector the detector currently used.
	 * @return a map containing the source tag as key and a set of corresponding title clusters.
	 */
	Map<String, List<Cluster>> clusteredWorks(
			final Document target, 
			final MultiMapEntityDetector detector) {
		final Map<String, List<String>> entities = detector.detect(target);
		final Map<String, List<Cluster>> result = new HashMap<String, List<Cluster>>(entities.size());
		for (final Entry<String, List<String>> entry : entities.entrySet()) {
			final List<Cluster> clusters = 	
					entry.getValue()
						.stream()
						.filter(heading -> function.isNotNullAndNotEmpty(heading))
						.map(heading -> function.getTitleCluster(heading))
						.collect(toList());
			if (clusters != null && !clusters.isEmpty()) {
				result.put(entry.getKey(), clusters);			
			}
		}

		return result;		
	}	
	
	/**
	 * Returns a map containing the source tag as key and a set of corresponding name clusters.
	 *  
	 * @param target the current MARC document (XML) representation.
	 * @param detector the detector currently used.
	 * @param titlesClusters the identifiers of the (title) clusters for this document.
	 * @return a map containing the source tag as key and a set of corresponding name clusters.
	 */
	Map<String, List<Cluster>> clusteredNames(
			final Document target, 
			final MultiMapEntityDetector detector, 
			final Map<String, List<Cluster>> works) {
		if (works == null || works.isEmpty()) {
			return null;
		}
				
		final Map<String, List<String>> entities = detector.detect(target);
		final Map<String, List<Cluster>> result = new HashMap<String, List<Cluster>>(entities.size());
		for (final Entry<String, List<String>> entry : entities.entrySet()) {
			final List<Cluster> clusters = 
					entry.getValue()
						.stream()
						.filter(heading -> function.isNotNullAndNotEmpty(heading))
						.map(heading -> function.getNameCluster(heading, null))
						.collect(toList());
			if (clusters != null && !clusters.isEmpty()) {		
					result.put(entry.getKey(), clusters);
			}
		}

		return result;		
	}		
}