// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.mx;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import eu.aliada.rdfizer.rest.JobResource;

/**
 * A transient, in-memory registry of JMX resources.
 * This is needed in this version in order to avoid reflection invocations with JMX.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@Component
@Scope("singleton")
public final class InMemoryJobResourceRegistry {
	
	private final Map<Integer, JobResource> registry = new HashMap<Integer, JobResource>();
	
	/**
	 * Adds a new resource to this registry.
	 * 
	 * @param resource the {@link JobResource} that will be collected in the registry.
	 */
	public void addJobResource(final JobResource resource) {
		registry.put(resource.getID(), resource);
	}
	
	/**
	 * Returns the resource associated with this registry.
	 * 
	 * @param identifier the job resource identifier.
	 * @return the job resource associated with the input identifier.
	 */
	public JobResource getJobResource(final Integer identifier) {
		return registry.get(identifier);
	}	
}