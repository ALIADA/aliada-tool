// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.inputValidation;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import eu.aliada.shared.log.Log;

/**
 * Java program to visualize the XML mandatory tags.
 * @author elena
 * @since 1.0
 */
public class VisualizeXML {

    private final Log log = new Log(VisualizeXML.class);

    /**
     * @param xmlName
     *            the name of XML file
     * @param stylesheetName
     *            the name of style sheet
     * @param outputFile
     *            the name of log file
     * @return boolean
     * @see
     * @since 1.0
     */
    public boolean toStyledDocument(final String xmlName,
            final String stylesheetName, final String outputFile) {
        TransformerFactory  factory = TransformerFactory .newInstance();
        StreamSource xslStream = new StreamSource(stylesheetName);
        Transformer transformer;
        try {
            transformer = factory.newTransformer(xslStream);
        StreamSource in = new StreamSource(xmlName);
        StreamResult out = new StreamResult(outputFile);
            transformer.transform(in, out);
        } catch (TransformerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
}
