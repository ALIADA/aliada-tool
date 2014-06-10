// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortium
package eu.aliada.rdfizer.marc;

import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.marc4j.MarcException;
import org.marc4j.MarcReader;
import org.marc4j.MarcXmlReader;
import org.marc4j.marc.Record;

import com.google.common.collect.AbstractIterator;

import eu.aliada.rdfizer.kernel.framework.StreamSplitter;
import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * MarcXML stream splitter implementation. 
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class MarcXmlStreamSplitter implements StreamSplitter<Record> {
	public static final Iterator<Record> EMPTY_ITERATOR = Collections.emptyIterator();
		
	Iterable<Record> noValidStreamAvailable = new Iterable<Record>() {
		@Override
		public Iterator<Record> iterator() {
			log.error(MessageCatalog._00026_NO_STREAM_AVAILABLE);
			return EMPTY_ITERATOR;
		}
	};
	
	Iterable<Record> validStreamAvailable = new Iterable<Record>() {
		@Override
		public Iterator<Record> iterator() {
			return new AbstractIterator<Record>() {
				protected Record computeNext() {
					if (reader.hasNext()) {
						recordsCount++;
						return reader.next();					
					} else {
						splitCompleted = true;
						return endOfData();
					}
				};
			};
		}
	};
	
	private Log log = new Log(MarcXmlStreamSplitter.class);
	private ProducerTemplate producer;
	private MarcReader reader;
	private Iterable<Record> currentState = noValidStreamAvailable;
	private int recordsCount;
	private boolean splitCompleted;
	
	@Override
	public void setStream(final InputStream inputStream, final String name) {
		if (inputStream == null) {
			throw new IllegalArgumentException("InputStream cannot be null.");
		}
		
		try {
			this.reader = new MarcXmlReader(inputStream);
			this.currentState = validStreamAvailable;
		} catch (final MarcException exception) {
			log.error(MessageCatalog._00021_BAD_MARCXML_FILE, name);
			this.currentState = noValidStreamAvailable;
		}
	}
	
	@Override
	public Iterator<Record> iterator() {
		return currentState.iterator();
	}

	@Override
	public void dispatchForFurtherProcessing(final Record record) {
		producer.send(new Processor() {
			@Override
			public void process(final Exchange outExchange) {
				outExchange.getIn().setBody(record);
			}
		});
	}
	
	@Override
	public void setProducer(final ProducerTemplate producer) {
		this.producer = producer;
	}

	@Override
	public boolean isSplitCompleted() {
		return splitCompleted;
	}

	@Override
	public int getHowManyRecordsInThisStream() {
		return recordsCount;
	}
}