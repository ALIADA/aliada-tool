// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.kernel.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import eu.aliada.rdfizer.kernel.framework.StreamSplitter;
import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * Splits an incoming records stream.
 * As first step of the conversion pipeline, an incoming stream needs to be split by 
 * iterating over included records.
 * This is the process which is in charge to control the overall split process; keep in mind that
 * concrete splitter job needs to be done by a specific {@link StreamSplitter} component that is aware 
 * about the specific format internalities.
 * 
 * Each record produced by this splitter will be sent to a dedicated channel for further processing.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 * @param <R> the record kind.
 */
public class RecordSplitter<R> implements Processor, ApplicationContextAware {
	private Log log = new Log(RecordSplitter.class);
	private ApplicationContext context;
	
	@SuppressWarnings("unchecked")
	@Override
	public void process(final Exchange exchange) throws Exception { 
		final File file = exchange.getIn().getBody(File.class);
		final String format = exchange.getIn().getHeader("format", String.class);
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		
			final StreamSplitter<R> streamSplitter = context.getBean(format + "-splitter", StreamSplitter.class);
					
			final Iterator<R> iterator = streamSplitter.iterator();
			while (iterator.hasNext()) {
				streamSplitter.dispatchForFurtherProcessing(iterator.next());
			}
		} catch (final IOException exception) {
			log.error(MessageCatalog._00027_INPUSTREAM_IO_FAILURE, exception, file.getAbsolutePath());
		} finally {
			//CHECKSTYLE:OFF
			try { if (inputStream != null) { inputStream.close(); }} catch (final Exception ignore) {}
			//CHECKSTYLE:ON
		}
	}
	
	@Override
	public void setApplicationContext(final ApplicationContext context) {
		this.context = context;
	}
}