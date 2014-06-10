// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortium
package eu.aliada.rdfizer.kernel.framework;

import java.io.InputStream;

import org.apache.camel.ProducerTemplate;

/**
 * A splitter is a component which is able to split an incoming stream.
 * When a concrete format must be handled, then a corresponding splitter must exists as part of the 
 * associated conversion pipeline. 
 * 
 * A StreamSplitter has two main responsibilities:
 * 
 * <ul>
 * 	<li>Iterate over a given (input) stream by generating records</li>
 * 	<li>Send records to a given channel for further processing</li>
 * </ul>
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 * @param <R> the record kind.
 */
public interface StreamSplitter<R> extends Iterable<R> {
	
	/**
	 * Injects the (input) stream onto this splitter.
	 * 
	 * @param inputStream the input stream.
	 * @param name the stream name (used for identifying the stream).
	 */
	void setStream(InputStream inputStream, String name);
	
	/**
	 * Dispatches a messages on the record channel for further processing.
	 * 
	 * @param record the record (that depends on the concrete splitter).
	 */
	void dispatchForFurtherProcessing(final R record);
	
	/**
	 * Injects the producer on this processor.
	 * 
	 * @param producer the message producer.
	 */
	void setProducer(final ProducerTemplate producer);
	
	/**
	 * Returns true if the stream has been completely split.
	 * 
	 * @return true if the stream has been completely split.
	 */
	boolean isSplitCompleted();
	
	/**
	 * Returns the total amount of records that have been split.
	 * 
	 * @return the total amount of records that have been split.
	 */
	int getHowManyRecordsInThisStream();
}