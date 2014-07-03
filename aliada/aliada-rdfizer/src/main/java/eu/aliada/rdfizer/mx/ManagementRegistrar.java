// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consorti
package eu.aliada.rdfizer.mx;

import java.lang.management.ManagementFactory;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import eu.aliada.rdfizer.rest.JobResource;
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
	
	public static final ObjectName RDFIZER_OBJECT_NAME;
	public static final String JOB_OBJECT_NAME_PREFIX = DOMAIN + "Module=RDFizer,Type=Job,Format=";
	static {
		try {
			RDFIZER_OBJECT_NAME = new ObjectName(DOMAIN + "Module=RDFizer");
		} catch (final MalformedObjectNameException exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}
	
	/**
	 * Registers a new job with the Management server.
	 * 
	 * @param job the job manageable resource.
	 * @throws JMException in case of management subsystem failure.
	 */
	public static void registerJob(final JobResource job) throws JMException {
		MX_SERVER.registerMBean(job, createJobObjectName(job.getFormat(), job.getID()));
	}
	
	/**
	 * Return the {@link ObjectName} of the job associated with the incoming data.
	 * 
	 * @param format the (input) format of the job data.
	 * @param jobId the job identifier.
	 * @return the {@link ObjectName} of the job associated with the incoming data.
	 */
	public static ObjectName createJobObjectName(final String format, final Integer jobId) {
		try {
			return new ObjectName(JOB_OBJECT_NAME_PREFIX + format + ",ID=" + jobId);
		} catch (final MalformedObjectNameException exception) {
			throw new RuntimeException(exception);
		}
	}
}