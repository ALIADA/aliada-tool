// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.templating;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.velocity.Template;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.TemplateInitException;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.parser.ParseException;

/** 
 * A subclass of Velocity template for ALIADA specific needs.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class AliadaTemplate extends Template {
	@Override
	public boolean process() {
        data = null;
        final InputStream is = resourceLoader.getResourceStream(name);

        if (is != null) {
            try {
                BufferedReader br = new BufferedReader(new RemoveLeadingWhitespacesReader(new InputStreamReader(is, encoding)));
                data = rsvc.parse(br, name);
                initDocument();
                return true;
            } catch (final UnsupportedEncodingException exception) {
                throw new ParseErrorException("Unsupported input encoding " + encoding + " in template (" + name + ") processing request.");
            } catch (final ParseException exception) {
            	throw new ParseErrorException(exception, name);
            } catch (final TemplateInitException exception) {
                throw new ParseErrorException(exception, name);
            } catch (final RuntimeException exception) {
                throw new VelocityException("Exception thrown processing Template " + getName(), exception);
            } finally {
                try {
                    is.close();
                } catch (final IOException exception) {
                	throw new VelocityException(exception);
                }
            }
        } else {
            throw new ResourceNotFoundException("Unknown resource error for resource " + name);
        }
    }
}