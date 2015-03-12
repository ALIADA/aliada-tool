package eu.aliada.rdfizer.pipeline.nlp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import eu.aliada.rdfizer.datasource.Cache;
import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * ALIADA NER Service.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@Component
public class NERService {
	
	private static final Log LOGGER = new Log(NERService.class);
	
	private static Map<String, String> EMPTY_MAP = new HashMap<String, String>();
	private static Set<String> EMPTY_SET = new HashSet<String>();
	private static final CRFClassifier<CoreLabel> NULL_OBJECT_CLASSIFIER = new CRFClassifier<CoreLabel>(new Properties()) {
		@Override
		public String classifyWithInlineXML(String sentences) {
			return "";
		};
		
		public java.util.Set<String> labels() {
			return EMPTY_SET;
		};
	};
	
	@Value(value = "${ner.classifier}")
	protected String classifierFilePath;

	@Autowired
	Cache cache;

	private ThreadLocal<AbstractSequenceClassifier<CoreLabel>> classifiers = new ThreadLocal<AbstractSequenceClassifier<CoreLabel>>() {
		protected AbstractSequenceClassifier<CoreLabel> initialValue() {
			try {
				return CRFClassifier.getClassifier(classifierFilePath);
			} catch (final Exception exception) {
				LOGGER.error(MessageCatalog._00052_CLASSIFIER_LOAD_FAILURE, exception, classifierFilePath);
				return NULL_OBJECT_CLASSIFIER;
			}
		};
	};
	
	/**
	 * Executes the NER on a given text.
	 *  
	 * @param text the text that will be analyzed.
	 */
	public Map<String, String> detectEntities(final String text) {
		if (text == null || text.trim().length() == 0) {
			return EMPTY_MAP;
		}
		
		final String labeledText = classifiers.get().classifyWithInlineXML(text);
		final Set<String> tags = classifiers.get().labels();
		final String background = classifiers.get().backgroundSymbol();
		final StringBuilder tagPattern = new StringBuilder();
		
		if (tags == null || tags.isEmpty()) {
			return EMPTY_MAP;
		}
		
		for (String tag : tags) {
			if (background.equals(tag)) {
				continue;
			}
			
			if (tagPattern.length() > 0) {
				tagPattern.append('|');
			}
			
			tagPattern.append(tag);
		}

		if (tagPattern.length() == 0) {
			return EMPTY_MAP;
		}

		final String pattern = tagPattern.toString();
		
		final Pattern startPattern = cache.getStartPattern(pattern);
		final Pattern endPattern = cache.getEndPattern(pattern);
		
		final Map<String, String> result = new HashMap<String, String>();
		
		String finalText = labeledText;
		Matcher m = startPattern.matcher(finalText);
		while (m.find()) {
			int start = m.start();
			finalText = m.replaceFirst("");
			m = endPattern.matcher(finalText);
			if (m.find()) {
				int end = m.start();
				String tag = m.group(1);
				finalText = m.replaceFirst("");
				result.put(finalText.substring(start, end), tag);
			} else {
				throw new IllegalArgumentException("Malformed tagged input text!");
			}
			m = startPattern.matcher(finalText);
		}
		return result;
	}
}