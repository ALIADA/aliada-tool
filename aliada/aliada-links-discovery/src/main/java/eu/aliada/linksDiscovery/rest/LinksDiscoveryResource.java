// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.linksDiscovery.rest;

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

import eu.aliada.linksDiscovery.rdbms.DBConnectionManager;
import eu.aliada.linksDiscovery.impl.LinksDiscovery;
import eu.aliada.linksDiscovery.log.MessageCatalog;
import eu.aliada.linksDiscovery.model.JobConfiguration;
import eu.aliada.linksDiscovery.model.DDBBParams;
import eu.aliada.linksDiscovery.model.Job;
import eu.aliada.shared.log.Log;


/**
 * REST services definition for aliada-links-dicovery component.
 *
 * @author Idoia Murua
 * @since 1.0
 */
@Path("/")
public class LinksDiscoveryResource {
	private final Log logger = new Log(LinksDiscovery.class);

	@Context
	private UriInfo uriInfo;

	@Context 
	ServletContext context;
	
	/**
	 * Creates a new job on the Links Discovery Component.
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
		@Context HttpServletRequest request,
		@FormParam("jobid") Integer id) {

		logger.debug(MessageCatalog._00021_NEW_JOB_REQUEST);
		
		if (id == null) {
			logger.error(MessageCatalog._00022_MISSING_INPUT_PARAM, "jobid");
			return Response.status(Status.BAD_REQUEST).build();			
		}

		DBConnectionManager db = (DBConnectionManager) context.getAttribute("db");
	    JobConfiguration jobConf = db.getJobConfiguration(id);
		if (jobConf == null) {
			logger.error(MessageCatalog._00023_JOB_CONFIGURATION_NOT_FOUND, id);
			return Response.status(Status.BAD_REQUEST).build();								
		}
		//Program linking processes
		DDBBParams ddbbParams = new DDBBParams();
		ddbbParams.setUsername(context.getInitParameter("ddbb.username"));
		ddbbParams.setPassword(context.getInitParameter("ddbb.password"));
		ddbbParams.setDriverClassName(context.getInitParameter("ddbb.driverClassName"));
		ddbbParams.setUrl(context.getInitParameter("ddbb.url"));
		LinksDiscovery linksDisc = new LinksDiscovery();
		Job job = linksDisc.programLinkingProcesses(jobConf, db, ddbbParams);
		
//		return Response.created(uriInfo.getAbsolutePathBuilder().build()).build();
		return Response.status(Response.Status.CREATED).entity(job).build();
	}

	/**
	 * Gets job info.
	 * 
	 * @param id the job identifier associated with this instance.
	 * @return a response which includes the info the job.
	 * @since 1.0
	 */
	@GET
	@Path("/jobs/{jobid}")
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getJob(
		@Context HttpServletRequest request,
		@PathParam("jobid") Integer id) {
		logger.debug(MessageCatalog._00025_GET_JOB_REQUEST);
		
		if (id == null) {
			logger.error(MessageCatalog._00022_MISSING_INPUT_PARAM, "jobid");
			return Response.status(Status.BAD_REQUEST).build();			
		}

		DBConnectionManager db = (DBConnectionManager) context.getAttribute("db");
		Job job = db.getJob(id);
		return Response.status(Response.Status.ACCEPTED).entity(job).build();
	}
}