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
		vx.toStyledDocument("src/test/resources/marc/marc_bib_more.xml",
				"src/main/webapp/WEB-INF/xmlVisualize/marc_bib.xsl",
				"src/main/webapp/logs/marc_bib.log");
	}

}
