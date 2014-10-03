// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-linked-data-server
// Responsible: ALIADA Consortium
package eu.aliada.linkedDataServerSetup.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;
import javax.servlet.ServletContext;

import eu.aliada.linkedDataServerSetup.rdbms.DBConnectionManager;
import eu.aliada.linkedDataServerSetup.impl.LinkedDataServerSetup;
import eu.aliada.linkedDataServerSetup.log.MessageCatalog;
import eu.aliada.linkedDataServerSetup.model.JobConfiguration;
import eu.aliada.linkedDataServerSetup.model.Job;
import eu.aliada.shared.log.Log;


/**
 * REST services definition for aliada-linked-data-server component.
 *
 * @author Idoia Murua
 * @since 1.0
 */
@Path("/")
public class LinkedDataServerResource {
	/** For logging. */
	private static final Log LOGGER = new Log(LinkedDataServerResource.class);

	/** Request URI information, */
	@Context
	private UriInfo uriInfo;

	/** Web application context. */
	@Context 
	private ServletContext context;
	
	/**
	 * Creates a new job on the Linked Data Server Component.
	 * 
	 * @param id the job identifier associated with this instance.
	 * @return a response which includes the info of the new job.
	 * @since 1.0
	 */
	@POST
	@Path("/jobs")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response newJob(
		@Context final HttpServletRequest request,
		@FormParam("jobid") final Integer jobid) {

		LOGGER.debug(MessageCatalog._00021_NEW_JOB_REQUEST);
		
		if (jobid == null) {
			LOGGER.error(MessageCatalog._00022_MISSING_INPUT_PARAM, "jobid");
			return Response.status(Status.BAD_REQUEST).build();			
		}
		
		//Get configuration parameters
		final DBConnectionManager dbConn = (DBConnectionManager) context.getAttribute("db");
		final JobConfiguration jobConf = dbConn.getJobConfiguration(jobid);
		if (jobConf == null) {
			LOGGER.error(MessageCatalog._00023_JOB_CONFIGURATION_NOT_FOUND, jobid);
			return Response.status(Status.BAD_REQUEST).build();								
		}
		//Program linking processes
		final LinkedDataServerSetup ldsSetup = new LinkedDataServerSetup();
		final Job job = ldsSetup.setup(jobConf, dbConn);
		
		return Response.created(uriInfo.getAbsolutePathBuilder().build()).entity(job).build();
	}

	/**
	 * Gets job info.
	 * 
	 * @param id the job identifier associated with this instance.
	 * @return a response which includes the info of the job.
	 * @since 1.0
	 */
	@GET
	@Path("/jobs/{jobid}")
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getJob(
		@Context final HttpServletRequest request,
		@PathParam("jobid") final Integer jobid) {
		LOGGER.debug(MessageCatalog._00025_GET_JOB_REQUEST);
		
		if (jobid == null) {
			LOGGER.error(MessageCatalog._00022_MISSING_INPUT_PARAM, "jobid");
			return Response.status(Status.BAD_REQUEST).build();
		}

		final DBConnectionManager dbConn = (DBConnectionManager) context.getAttribute("db");
		final Job job = dbConn.getJob(jobid);
		return Response.status(Response.Status.ACCEPTED).entity(job).build();
	}
}