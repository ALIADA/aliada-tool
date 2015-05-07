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

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import eu.aliada.rdfizer.datasource.Cache;
import eu.aliada.shared.log.Log;

/**
 * ALIADA NER Service.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public abstract class NERService {
	
	protected static final Log LOGGER = new Log(NERService.class);
	
	protected static Map<String, String> EMPTY_MAP = new HashMap<String, String>();
	protected static Set<String> EMPTY_SET = new HashSet<String>();
	protected static final CRFClassifier<CoreLabel> NULL_OBJECT_CLASSIFIER = new CRFClassifier<CoreLabel>(new Properties()) {
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
	
	abstract AbstractSequenceClassifier<CoreLabel> classifier();

	/**
	 * Executes the NER on a given text.
	 *  
	 * @param text the text that will be analyzed.
	 */
	public Map<String, String> detectEntities(final String text) {
		if (text == null || text.trim().length() == 0) {
			return EMPTY_MAP;
		}
		
		final String labeledText = classifier().classifyWithInlineXML(text);
		final Set<String> tags = classifier().labels();
		final String background = classifier().backgroundSymbol();
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