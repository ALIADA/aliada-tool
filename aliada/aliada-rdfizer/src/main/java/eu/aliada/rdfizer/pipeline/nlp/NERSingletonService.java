package eu.aliada.rdfizer.pipeline.nlp;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import eu.aliada.rdfizer.log.MessageCatalog;

/**
 * ALIADA NER Service.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@Component
@Scope("singleton")
public class NERSingletonService extends NERService {

	AbstractSequenceClassifier<CoreLabel> classifier;

	@Override
	AbstractSequenceClassifier<CoreLabel> classifier() {
		synchronized(this) {
			if (classifier == null) {
					try {
						classifier = CRFClassifier.getClassifier(classifierFilePath);
					} catch (final Exception exception) {
						LOGGER.error(MessageCatalog._00052_CLASSIFIER_LOAD_FAILURE, classifierFilePath);
						classifier = NULL_OBJECT_CLASSIFIER;
					}
			}
			return classifier;
		}
	};
}