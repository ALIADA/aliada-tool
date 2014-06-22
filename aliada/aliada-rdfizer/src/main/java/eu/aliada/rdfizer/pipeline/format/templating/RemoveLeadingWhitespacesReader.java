// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortium
package eu.aliada.rdfizer.pipeline.format.templating;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

/**
 * A FilterReader that strips leading whitespaces.
 * This is basically needed for template processing: on developers side, we want to allow
 * indentation on templates, that makes transformation templates more readabale and maintainable.
 * At the same time, the final output stream mustn't have any leading whitespace. 
 * So, instead of doing something on Writer side, we prefer to have a Reader, that will be used by 
 * the Velocity runtime, that communicates a streams without leading spaces.
 * 
 * As a nice addition in future we could skip comments too.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class RemoveLeadingWhitespacesReader extends FilterReader {
	/**
	 * Builds a new reader with the given inner reader.
	 * 
	 * @param reader the inner reader.
	 */
	public RemoveLeadingWhitespacesReader(final Reader reader) {
		super(reader);
	}

	private boolean isSkipping; 

	@Override
	public int read(final char[] buf, final int from, final int len) throws IOException {
		int numchars = 0;
		while (numchars == 0) {
			numchars = in.read(buf, from, len); 
			if (numchars == -1) {
				return -1; 
			}

			int last = from;
			for (int i = from; i < from + numchars; i++) {
				if (!isSkipping) {
					if (buf[i] == '\n') {
						buf[last++] = buf[i]; 
						isSkipping = true;
					} else {
						buf[last++] = buf[i]; 
					}
				} else if (!Character.isWhitespace(buf[i])) {
					buf[last++] = buf[i]; 
					isSkipping = false; 
				}
			}
			numchars = last - from;
		}
		return numchars;
	}

	@Override
	public int read() throws IOException {
		final char[] buf = new char[1];
		return read(buf, 0, 1) == -1 ? -1 : (int) buf[0];
	}
}