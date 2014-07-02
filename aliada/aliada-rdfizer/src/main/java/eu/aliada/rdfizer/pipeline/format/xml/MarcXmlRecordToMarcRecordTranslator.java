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

/**
 * An converter between an XML and the corresponding object representation of a MARC record.
 * Assumes that the incoming message body is an XML representation of a MARC record (as a string).
 * If the conversion happens successfully then it pushes out that record on the outcoming message body (as {@link Record}).
 * 
 * @see Record
 * @author Emiliano Cammilletti
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class MarcXmlRecordToMarcRecordTranslator implements Processor {
	@Override
	public void process(final Exchange exchange) throws Exception {
		final String marcXmlRecord = exchange.getIn().getBody(String.class);
		final MarcReader reader = new MarcXmlReader(new ByteArrayInputStream(marcXmlRecord.getBytes()));
		if (reader.hasNext()) {
			final Record record = reader.next();
			exchange.getIn().setBody(record);
		}
	}
}