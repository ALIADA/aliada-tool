package eu.aliada.rdfizer.pipeline.nlp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import eu.aliada.rdfizer.datasource.Cache;
import eu.aliada.rdfizer.log.MessageCatalog;

/**
 * ALIADA NER Service.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@Component
public class NERThreadLocalService extends NERService {
	@Autowired
	Cache cache;
	
	private ThreadLocal<AbstractSequenceClassifier<CoreLabel>> classifiers = new ThreadLocal<AbstractSequenceClassifier<CoreLabel>>() {
		protected AbstractSequenceClassifier<CoreLabel> initialValue() {
			try {
				return CRFClassifier.getClassifier(classifierFilePath);
			} catch (final Exception exception) {
				LOGGER.error(MessageCatalog._00052_CLASSIFIER_LOAD_FAILURE, classifierFilePath);
				return NULL_OBJECT_CLASSIFIER;
			}
		};
	};

	@Override
	AbstractSequenceClassifier<CoreLabel> classifier() {
		return classifiers.get();
	};
}