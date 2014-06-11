// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.rest;

import static eu.aliada.shared.Strings.isNullOrEmpty;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * A conversion job representation on RDF-izer.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@Path("/job")
@Produces(MediaType.APPLICATION_JSON)
public class JobResource {
	
	private static final Log LOGGER = new Log(JobResource.class);
	
	@Context
	private UriInfo uriInfo;
	
	/**
	 * Creates a new job on the RDF-izer.
	 * 
	 * @param id the onfiguration identifier associated with this instance.
	 * @param datafilePath the path of the input datafile that will be associated with this job instance.
	 * @return a response which includes the URI of the new job.
	 */
	@POST
	public Response create(
			@FormParam("config-id") final String id,
			@FormParam("datafile") final String datafilePath) {
		LOGGER.debug(MessageCatalog._00029_NEW_JOB_REQUEST);
		
		if (isNullOrEmpty(id)) {
			LOGGER.error(MessageCatalog._00028_MISSING_INPUT_PARAM, "config-id");
			return Response.status(Status.BAD_REQUEST).build();			
		}

		if (isNullOrEmpty(datafilePath)) {
			LOGGER.error(MessageCatalog._00028_MISSING_INPUT_PARAM, "datafile");
			return Response.status(Status.BAD_REQUEST).build();			
		}

		final File datafile = new File(datafilePath);
		if (!datafile.canWrite()) {
			LOGGER.error(MessageCatalog._00020_WRONG_FILE_PERMISSIONS, datafile.getAbsolutePath());
			return Response.status(Status.BAD_REQUEST).build();						
		}
		
		final String path = datafile.getAbsolutePath();
		LOGGER.debug(MessageCatalog._00030_NEW_JOB_REQUEST_DEBUG, id, path);
				
		final java.nio.file.Path source = Paths.get(path);
		final java.nio.file.Path target = Paths.get("/var/data/pipeline/input/marcxml/" + datafile.getName());
		
		try {
			Files.move(source, target, REPLACE_EXISTING);
			return Response.created(uriInfo.getAbsolutePathBuilder().path("3").build()).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.serverError().build();						
		}
	}
}