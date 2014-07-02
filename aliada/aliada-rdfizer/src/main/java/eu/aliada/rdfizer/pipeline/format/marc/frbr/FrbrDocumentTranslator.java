// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import org.apache.camel.Message;
import org.apache.velocity.VelocityContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import eu.aliada.rdfizer.Constants;
import eu.aliada.rdfizer.datasource.rdbms.JobConfiguration;
import eu.aliada.rdfizer.framework.UnableToDetermineMainSubectException;
import eu.aliada.rdfizer.pipeline.format.marc.frbr.model.FrbrDocument;
import eu.aliada.rdfizer.pipeline.format.xml.SynchXmlDocumentTranslator;

/**
 * Converts the incoming {@link Document} record in RDF using an FRBR ontology.
 * This is basically an extension of {@link SynchXmlDocumentTranslator} with the additional behaviour needed 
 * for dealing with FRBR entities and conversion rules.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class FrbrDocumentTranslator extends SynchXmlDocumentTranslator {
	
	@Override
	protected void populateVelocityContext(
			final VelocityContext velocityContext, 
			final Message message, 
			final JobConfiguration configuration) throws UnableToDetermineMainSubectException {
		final FrbrDocument data = message.getBody(FrbrDocument.class);
		
		final Document document = data.getDocument();
		final Element root = document.getDocumentElement();
		velocityContext.put(Constants.ROOT_ELEMENT_ATTRIBUTE_NAME, root);
		velocityContext.put(Constants.FRBR_DATA_ATTRIBUTE_NAME, data);		
	}
	
	@Override
	protected String templateName(final String format) {
		return new StringBuilder(format).append(".frbr.n3.vm").toString();
	}
}