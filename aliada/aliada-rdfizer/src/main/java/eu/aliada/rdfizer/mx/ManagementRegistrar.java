// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consorti
package eu.aliada.rdfizer.mx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

import eu.aliada.shared.log.Log;

/**
 * Utility class for registering / unregistering MX beans.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public abstract class ManagementRegistrar {
	
	static final MBeanServer MX_SERVER = ManagementFactory.getPlatformMBeanServer();
	static final Log LOGGER = new Log(ManagementRegistrar.class);
	static final String DOMAIN = "ALIADA:";	
}