// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.inputValidation;

import junit.framework.TestCase;
import eu.aliada.shared.log.Log;

/**
 * @author elena
 * @since 1.0
 */
public class VisualizeXMLTest extends TestCase {
	private final Log log = new Log(VisualizeXMLTest.class);

	/**
	 * @see
	 * @since 1.0
	 */
	public void testVisualizeXML() {
		VisualizeXML vx = new VisualizeXML();
		//LIDO
		//boolean result = vx.toStyledDocument("src/test/resources/lido/lidoMFAB_modified.xml", "src/main/resources/xmlVisualize/lido.xsl", "src/main/webapp/content/errorContent.jsp");
		//MARC bib
		//boolean result = vx.toStyledDocument("src/test/resources/marc/datasetART.xml", "src/main/resources/xmlVisualize/marc_bib.xsl", "src/main/webapp/content/errorContent.jsp");
		//MARC aut
		//boolean result = vx.toStyledDocument("src/test/resources/marc/marc_aut.xml", "src/main/resources/xmlVisualize/marc_aut.xsl", "src/main/webapp/content/errorContent.jsp");
		//DC
		boolean result = vx.toStyledDocument("src/test/resources/drupal/podcastXML.xml", "src/main/resources/xmlVisualize/dc.xsl", "src/main/webapp/content/errorContent.jsp");
		if (result) {
			log.info("BIEN");
		} else {
			log.info("MAL");
		}
	}

}
