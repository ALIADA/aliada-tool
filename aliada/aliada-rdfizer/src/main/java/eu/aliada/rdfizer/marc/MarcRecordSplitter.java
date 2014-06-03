// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortium
package eu.aliada.rdfizer.marc;

import java.io.File;
import java.io.FileInputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.marc4j.MarcException;
import org.marc4j.MarcReader;
import org.marc4j.MarcXmlReader;
import org.marc4j.marc.MarcFactory;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * Marc record splitter. 
 * 
 * @author agazzarini
 * @since 1.0
 */
public class MarcRecordSplitter implements Processor {
	private Log log = new Log(MarcRecordSplitter.class);
	private MarcFactory factory = MarcFactory.newInstance();

	private ProducerTemplate chunkRecordsChannelProducer;
	
	/**
	 * Injects the producer on this processor.
	 * 
	 * @param producer the message producer.
	 */
	public void setProducer(final ProducerTemplate producer) {
		this.chunkRecordsChannelProducer = producer;
	}
	
	/**
	 * Splits the incoming MARC file message.
	 * 
	 * @param exchange the route exchange.
	 * @throws Exception in case of process failure.
	 */
	public void process(final Exchange exchange) throws Exception {
		final File file = exchange.getIn().getBody(File.class);
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			final MarcReader reader = new MarcXmlReader(inputStream);
			while (reader.hasNext()) {
				final Record record = reader.next();
				
				// TODO: Add to external storage (i.e. Cassandra)
				// TODO: split
			}
		} catch (final MarcException exception) {
			log.error(MessageCatalog._00021_BAD_MARCXML_FILE, file.getAbsolutePath());
			// TODO: dead letter channel.
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (final Exception ignore) {
				// Nothing to be done here...
			}
		}
	}
	
	/**
	 * Dispatches a messages to the next processor.
	 * 
	 * @param field the variable field (the main record chunk component).
	 * @param correlationId the correlation id.
	 */
	// TODO: Which level of granularity? Field or record?
	private void dispatch(final VariableField field, final String correlationId)  {
		// TODO: sanity check: in case the field is not configured then avoid this block
		chunkRecordsChannelProducer.send(new Processor()
		{
			@Override
			public void process(final Exchange outExchange) {
				// Send
			}
		});
	}
}