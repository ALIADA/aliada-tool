// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.templating;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.ResourceManagerImpl;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

/**
 * ALIADA ResourceManager implementation to manage the text resource for the Velocity Runtime.
 *
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class AliadaResourceManager extends ResourceManagerImpl {
    @Override
	protected Resource createResource(final String resourceName, final int resourceType) {
        return AliadaResourceFactory.getResource(resourceName, resourceType);
    }

    @Override
    protected Resource refreshResource(final Resource resource, final String encoding) {
        resource.touch();

        final ResourceLoader loader = resource.getResourceLoader();
        if (resourceLoaders.size() > 0 && resourceLoaders.indexOf(loader) > 0) {
            final String name = resource.getName();
            if (loader != getLoaderForResource(name)) {
                return loadResource(name, resource.getType(), encoding);
            }
        }

        if (resource.isSourceModified()) {
            if (!StringUtils.equals(resource.getEncoding(), encoding)) {
                resource.setEncoding(encoding);
            }
            
            final String resourceName = resource.getName();
            final Resource newResource = AliadaResourceFactory.getResource(resourceName, resource.getType());

            newResource.setRuntimeServices(rsvc);
            newResource.setName(resourceName);
            newResource.setEncoding(resource.getEncoding());
            newResource.setResourceLoader(loader);
            newResource.setModificationCheckInterval(loader.getModificationCheckInterval());

            newResource.process();
            newResource.setLastModified(loader.getLastModified(resource));

            globalCache.put(
            		new StringBuilder(resource.getType())
            			.append(resource.getName())
            			.toString(), 
            		newResource);
            return newResource;
        }
        return resource;
    }

    /**
     * Returns the first {@link ResourceLoader} in which the specified
     * resource exists.
     * 
     * @param resourceName the resource name.
     * @return a resource loader for the given resource.
     */
	@SuppressWarnings("unchecked")
	private ResourceLoader getLoaderForResource(final String resourceName) {
        for (final Iterator<ResourceLoader> i = resourceLoaders.iterator(); i.hasNext();) {
            final ResourceLoader loader = (ResourceLoader)i.next();
            if (loader.resourceExists(resourceName)) {
                return loader;
            }
        }
        return null;
    }
}