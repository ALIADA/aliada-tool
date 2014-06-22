// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.templating;
import org.apache.velocity.runtime.resource.ContentResource;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.ResourceManager;

/**
 * Aliada (Velocity) resource factory.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public abstract class AliadaResourceFactory {
    /**
     * Returns a resource according with given data.
     * 
     * @param resourceName the resource name.
     * @param resourceType the resource kind.
     * @return The resource described by name and type.
     */
    public static Resource getResource(final String resourceName, final int resourceType) {
        switch (resourceType) {
            case ResourceManager.RESOURCE_TEMPLATE:
                return new AliadaTemplate();
            case ResourceManager.RESOURCE_CONTENT:
                return new ContentResource();
            default:
            	throw new IllegalArgumentException("Unknown resource type: " + resourceType);
        }
    }
}