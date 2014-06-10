// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.lido;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.events.XMLEvent;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;

import com.google.common.collect.AbstractIterator;

import eu.aliada.rdfizer.kernel.framework.StreamSplitter;
import eu.aliada.shared.log.Log;

/**
 * LIDO stream splitter implementation. 
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class LidoStreamSplitter implements StreamSplitter<String> {
	private Log log = new Log(LidoStreamSplitter.class);
	private ProducerTemplate producer;
	private InputStream stream;
	private XMLEventReader reader;
	private int recordsCount;
	private boolean splitCompleted;
	
	@Override
	public Iterator<String> iterator() {
		try {
			final StringWriter outputWriter = new StringWriter();
			final BufferedWriter bufferedOutputWriter = new BufferedWriter(outputWriter);
			final XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(bufferedOutputWriter);
			return new AbstractIterator<String>() {
				@Override
				protected String computeNext() {
					try {
						boolean isActivelyPrinting = false;
						while (reader.hasNext()) {
					      final XMLEvent event = reader.nextEvent();
					      if (isActivelyPrinting && event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("lido")) {
					    	  writer.add(event);
					    	  writer.flush();
					    	  isActivelyPrinting = false;
					    	  recordsCount++;
					    	  return outputWriter.toString();				    	  
					      }
					      
					      if (!isActivelyPrinting && event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("lido")) {
					    	  isActivelyPrinting = true;
					      }
					      
					      if (isActivelyPrinting) {
						      writer.add(event);
					      }
						}

						splitCompleted = true;
						return endOfData();
					} catch (final Exception exception) {
						log.error("", exception);
						throw new RuntimeException(exception);
					}
				}
			};
		} catch (Exception exception) {
			log.error("", exception);
			throw new RuntimeException(exception);
		}
	}

	@Override
	public void setStream(final InputStream inputStream, final String name) {
		if (inputStream == null) {
			throw new IllegalArgumentException("InputStream cannot be null.");
		}
		
		this.stream = inputStream;
		XMLInputFactory xmlFactory = null;
		 
		  try {
		      xmlFactory = XMLInputFactory.newInstance();
		      xmlFactory.setProperty(
		              XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES,
		              Boolean.TRUE);
		      xmlFactory.setProperty(
		              XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES,
		              Boolean.FALSE);
		      xmlFactory.setProperty(
		    		  XMLInputFactory.IS_COALESCING, 
		    		  Boolean.FALSE);
		      reader = xmlFactory.createXMLEventReader(stream);
		      
		  } catch (Exception ex) {
		      ex.printStackTrace();
		  }
	}

	@Override
	public void dispatchForFurtherProcessing(final String record) {
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