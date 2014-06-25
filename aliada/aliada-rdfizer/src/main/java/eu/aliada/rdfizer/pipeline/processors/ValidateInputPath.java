// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortium
package eu.aliada.rdfizer.pipeline.processors;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import eu.aliada.rdfizer.Constants;
import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * A general-purpose file path validator processor.
 * 
 * @author Andrea Gazzarini 
 * @since 1.0
 */
public class ValidateInputPath implements Processor {
	private final Log log = new Log(ValidateInputPath.class);
	
	@Override
	public void process(final Exchange exchange) throws Exception {
		final File inputFile = exchange.getIn().getBody(File.class);
		if (!inputFile.canRead() || !inputFile.canWrite()) {
			final String path = inputFile.getAbsolutePath();
			log.error(MessageCatalog._00020_WRONG_FILE_PERMISSIONS, path);
			throw new FileNotFoundException(path);
		}
		
		final String name = inputFile.getName();
		final int indexOfDot = name.lastIndexOf(".");
		if (indexOfDot == -1) {
			log.error(MessageCatalog._00037_WRONG_INPUT_FILENAME, name);
			throw new IllegalArgumentException(name);			
		}
		
		try {
			final Integer jobId = Integer.parseInt(name.substring(indexOfDot + 1));
			exchange.getIn().setHeader(Constants.JOB_ID_ATTRIBUTE_NAME, jobId);
		} catch (Exception exception) {
			log.error(MessageCatalog._00037_WRONG_INPUT_FILENAME, name);
			throw new IllegalArgumentException(name);			
		}
	}
}