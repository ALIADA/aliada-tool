// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-shared
// Responsible: ALIADA Consortium
package eu.aliada.shared.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ALIADA Logger.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public final class Log
{
	private final Logger log;

	/**
	 * Builds a new log for a given class.
	 * 
	 * @param clazz the class associated with this logger instance.
	 */
	public Log(final Class<?> clazz) {
		log = LoggerFactory.getLogger(clazz);
	}
	
	/**
	 * Logs out the given message with ERROR severity level.
	 * 
	 * @param message the error message.
	 * @param parameters the parameters that will be replaced in the message.
	 */
	public void error(final String message, final Object... parameters)
	{
		if (parameters == null)
		{
			log.error(message);
		} else
		{
			log.error(String.format(message, parameters));
		}
	}

	/**
	 * Logs out the given message with ERROR severity level.
	 * 
	 * @param message the error message.
	 * @param cause the cause.
	 * @param parameters the parameters that will be replaced in the message.
	 */
	public void error(final String message, final Throwable cause, final Object... parameters)
	{
		if (parameters == null)
		{
			log.error(message);
		} else
		{
			log.error(String.format(message, parameters), cause);
		}
	}

	/**
	 * Logs out the given message with INFO severity level.
	 * 
	 * @param message the info message.
	 * @param parameters the parameters that will be replaced in the message.
	 */
	public void info(final String message, final Object... parameters)
	{
		log.info(String.format(message, parameters));
	}

	/**
	 * Logs out the given message with INFO severity level.
	 * 
	 * @param message the info message.
	 * @param parameters the parameters that will be replaced in the message.
	 */
	public void debug(final String message, final Object... parameters)
	{
		if (log.isDebugEnabled())
		{
			log.debug(String.format(message, parameters));
		}
	}
}