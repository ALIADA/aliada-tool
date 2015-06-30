// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-shared
// Responsible: ALIADA Consortium
package eu.aliada.shared;

import java.net.InetAddress;

/**
 * A long UID maker.
 *
 * @author Andrea Gazzarini.
 * @since 1.0
 */
public abstract class ID 
{
	/** Current ID **/
	private static long currentID;

	static 
	{
		final StringBuffer sb = new StringBuffer();
		String ipAddress = "127.0.0.1";
		try 
		{
			final InetAddress ip = InetAddress.getLocalHost();
			ipAddress = ip.getHostAddress();
			sb.append(ipAddress.substring(ipAddress.lastIndexOf('.') + 1));
		} catch (Exception ignore) 
		{
			// Nothing to do here
		} 

		sb.append(System.currentTimeMillis());
		currentID = Long.parseLong(sb.toString());
	}

	/**
	 * Obtain the next available ID.
	 *
	 * @return long the next available ID.
	 */
	public static synchronized long get() 
	{
		return currentID++;
	}
}