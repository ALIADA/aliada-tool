package eu.aliada.rdfizer.pipeline.nlp;

import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

/**
 * ALIADA NER Service.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class NERService {

	public void detectEntities(String text, final EntityDetectionListener listener) throws ClassCastException, ClassNotFoundException, IOException {
		if (text == null || text.trim().length() == 0) {
			return;
		}
		
		AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier
				.getClassifier("/work/workspaces/aliada/aliada-tool/aliada/src/site/nlp/english.all.7class.distsim.crf.ser.gz");

		String labeledText = classifier.classifyWithInlineXML(text);
		Set<String> tags = classifier.labels();
		String background = classifier.backgroundSymbol();
		StringBuilder tagPattern = new StringBuilder();
		for (String tag : tags) {
			if (background.equals(tag)) {
				continue;
			}
			if (tagPattern.length() > 0) {
				tagPattern.append('|');
			}
			tagPattern.append(tag);
		}

		Pattern startPattern = Pattern.compile("<(" + tagPattern + ")>");
		Pattern endPattern = Pattern.compile("</(" + tagPattern + ")>");

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
				listener.onEntityDetected(tag, finalText.substring(start, end));
			} else {
				throw new IllegalArgumentException("Malformed tagged input text!");
			}
			m = startPattern.matcher(finalText);
		}
	}
}