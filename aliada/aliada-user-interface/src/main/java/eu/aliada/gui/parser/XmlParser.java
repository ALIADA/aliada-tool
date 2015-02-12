// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.parser;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

/**
 * @author iosa
 * @since 1.0
 */
public class XmlParser {
	/**
     * XML Parser.
     * @param stream The input
     * @return Document
     * @throws Exception The Exception
     * @see
     * @since 1.0
     */
    public Document parseXML(final InputStream stream)
            throws Exception {
                DocumentBuilderFactory objDocumentBuilderFactory = null;
                DocumentBuilder objDocumentBuilder = null;
                Document doc = null;
                try {
                    objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
                    objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

                    doc = objDocumentBuilder.parse(stream);
                } catch (Exception ex) {
                    throw ex;
                }       

                return doc;
            }

}
