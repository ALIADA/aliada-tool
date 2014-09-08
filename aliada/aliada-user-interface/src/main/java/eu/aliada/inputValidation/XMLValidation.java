// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.inputValidation;

import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * Java program to check if XML file is correct.
 * 
 * @author elena
 * @since 1.0
 */
public class XMLValidation {
	private final Log logger = new Log(XMLValidation.class);

	/**
	 * @param xmlName
	 *            the name of XML file
	 * @param schemaName
	 *            the name of schema file
	 * @return boolean
	 * @see
	 * @since 1.0
	 */
	public boolean isValidatedXMLFile(final String xmlName,
			final String schemaName) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(false);
			factory.setNamespaceAware(true);

			SchemaFactory schemaFactory = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

			factory.setSchema(schemaFactory
					.newSchema(new Source[] { new StreamSource(schemaName) }));

			SAXParser parser = factory.newSAXParser();

			XMLReader reader = parser.getXMLReader();

			reader.setErrorHandler(new SimpleErrorHandler());
			reader.parse(new InputSource(xmlName));

			return ((SimpleErrorHandler) reader.getErrorHandler()).getMyError() == null;
		} catch (ParserConfigurationException e) {
			logger.debug(MessageCatalog._00102_PARSER_CONFIGURATION_EXCEPTION);
			e.printStackTrace();
			return false;
		} catch (SAXException e) {
			logger.debug(MessageCatalog._00103_SAX_EXCEPTION);
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			logger.debug(MessageCatalog._00012_IO_EXCEPTION);
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			logger.debug(MessageCatalog._00100_EXCEPTION);
			e.printStackTrace();
			return false;
		}
	}
}
