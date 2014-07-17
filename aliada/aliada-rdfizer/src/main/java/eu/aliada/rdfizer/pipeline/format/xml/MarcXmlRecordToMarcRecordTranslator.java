// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.xml;

import java.io.ByteArrayInputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.marc4j.MarcReader;
import org.marc4j.MarcXmlReader;
import org.marc4j.marc.Record;

import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * An converter between an XML and the corresponding object representation of a MARC record.
 * Assumes that the incoming message body is an XML representation of 1 and only 1 MARC record (as a string).
 * If the conversion happens successfully then it pushes out that record on the outcoming message body (as {@link Record}).
 * 
 * @see Record
 * @author Emiliano Cammilletti
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class MarcXmlRecordToMarcRecordTranslator implements Processor {
	protected final Log log = new Log(MarcXmlRecordToMarcRecordTranslator.class);
	
	@Override
	public void process(final Exchange exchange) throws Exception {
		final String marcXmlRecord = exchange.getIn().getBody(String.class);
		try {
			final MarcReader reader = new MarcXmlReader(new ByteArrayInputStream(marcXmlRecord.getBytes("UTF-8")));
			if (reader.hasNext()) {
				final Record record = reader.next();
				exchange.getIn().setBody(record);
			} else {
				log.error(MessageCatalog._00047_EMPTY_INCOMING_RECORD_STREAM);
				throw new IllegalArgumentException(MessageCatalog._00047_EMPTY_INCOMING_RECORD_STREAM);
			}
		} catch (final IllegalArgumentException exception) {
			throw exception;
		} catch (final Exception exception) {
			log.error(MessageCatalog._00047_EMPTY_INCOMING_RECORD_STREAM, exception);
			throw exception;
		}
	}
}