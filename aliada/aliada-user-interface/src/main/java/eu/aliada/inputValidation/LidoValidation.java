// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.inputValidation;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import eu.aliada.shared.log.Log;

/**
 * Java program to check if LidoXML file is correct.
 * @author elena
 * @since 1.0
 */
public class LidoValidation {
    private final Log log = new Log(LidoValidation.class);
  
    /**
     * @param xmlName the name of XML file
     * @return boolean
     * @see
     * @since 1.0
     */
    public boolean validateLidoFile(final String xmlName) {
        try {
            // create and configure the factory to parser the XML documents
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
  
            // Load the XML file
            DocumentBuilder parser = dbf.newDocumentBuilder();
            Document doc = parser.parse(new File(xmlName));

            // create a SchemaFactory to interpret XML W3C schemas
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // Load the XSD schema
            Schema schema = sf.newSchema(new File("src/main/webapp/WEB-INF/xmlValidators/lido-v1.0.xsd"));

            // create the object to validate the XML
            Validator validator = schema.newValidator();

            // validate the XML file
            validator.validate(new DOMSource(doc));
            
           log.info("DOCUMENTO VALIDO");
            return true;
        } catch (SAXException e) {
            // fail in the validation
            System.err.println("DOCUMENTO INVÁLIDO");
            e.printStackTrace();
            return false;
        } catch (ParserConfigurationException e) {
            // errors in parser configuration 
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // errors in I/O
            e.printStackTrace();
            return false;
        }
    }
}
