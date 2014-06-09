// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortium
package eu.aliada.rdfizer.kernel.logic;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

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
	}
}