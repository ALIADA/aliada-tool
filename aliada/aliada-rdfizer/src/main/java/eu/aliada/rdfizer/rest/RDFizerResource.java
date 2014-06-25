// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.rest;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import eu.aliada.rdfizer.datasource.Cache;
import eu.aliada.rdfizer.datasource.rdbms.JobConfiguration;
import eu.aliada.rdfizer.datasource.rdbms.JobConfigurationRepository;
import eu.aliada.rdfizer.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * RDF-izer REST resource representation.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
@Singleton
@Component
@Path("/")
public class RDFizerResource {
	
	private static final Log LOGGER = new Log(RDFizerResource.class);

	@Context
	private UriInfo uriInfo;
	
	@Value(value = "${marcxml.input.dir}")
	protected String marcXmlInputDir;
	
	@Value(value = "${lido.input.dir}")
	protected String lidoInputDir;
	
	@Autowired
	protected Cache cache;
	
	/**
	 * Creates a new job on the RDF-izer.
	 * 
	 * @param id the job identifier associated with this instance.
	 * @return a response which includes the URI of the new job.
	 */
	@POST
	@Path("/jobs/{jobid}")
	public Response newJob(@PathParam("jobid") final Integer id) {
		LOGGER.debug(MessageCatalog._00029_NEW_JOB_REQUEST);
		
		if (id == null) {
			LOGGER.error(MessageCatalog._00028_MISSING_INPUT_PARAM, "jobid");
			return Response.status(Status.BAD_REQUEST).build();			
		}
		
		String path = null;
		try {
			final JobConfiguration job = cache.getJobConfiguration(id);
			if (job == null) {
				LOGGER.error(MessageCatalog._00032_JOB_CONFIGURATION_NOT_FOUND, id);
				return Response.status(Status.BAD_REQUEST).build();								
			}
			
			final File datafile = new File(job.getDatafile());
			if (!datafile.canWrite()) {
				LOGGER.error(MessageCatalog._00020_WRONG_FILE_PERMISSIONS, datafile.getAbsolutePath());
				return Response.status(Status.BAD_REQUEST).build();						
			}
			
			path = datafile.getAbsolutePath();
			LOGGER.debug(MessageCatalog._00030_NEW_JOB_REQUEST_DEBUG, id, path);
			
			final String listenPath = listenPath(job.getFormat());
			if (listenPath == null) {
				LOGGER.error(MessageCatalog._00033_UNSUPPORTED_FORMAT, job.getFormat(), id);
				return Response.status(Status.BAD_REQUEST).build();										
			}
						
			final java.nio.file.Path source = Paths.get(path);
			final java.nio.file.Path target = Paths.get(listenPath + "/" + rdfizerDataFilename(datafile, id));
			
			Files.move(source, target, REPLACE_EXISTING);
			return Response.created(uriInfo.getAbsolutePathBuilder().build()).build();
		} catch (final IOException exception) {
			LOGGER.debug(MessageCatalog._00030_NEW_JOB_REQUEST_DEBUG, id, path);
			return Response.serverError().build();						
		} catch (final DataAccessException exception)  {
			LOGGER.debug(MessageCatalog._00031_DATA_ACCESS_FAILURE, exception);
			return Response.serverError().build();						
		}
	}
	
	/**
	 * Returns the path where RDF-izer is listening for input datafiles.
	 * 
	 * @param format the format associated with the current conversion request.
	 * @return the path where RDF-izer is listening for input datafiles.
	 */
	// TODO : supported formats are wrongly hard-coded
	String listenPath(final String format) {
		if ("lido".equals(format)) {
			return lidoInputDir;
		} else if ("marcxml".equals(format)) {
			return marcXmlInputDir;
		}
		return null;
	}
	
	/**
	 * Returns a valid RDFizer datafile name.
	 * RDFizer needs an input file with a given format, specifically composed by
	 * 
	 * name.suffix.jobid
	 * 
	 * where 
	 * 
	 * <ul>
	 * 	<li>name is the original input file name;</li>
	 * 	<li>suffix (optional) is the suffix of the original input file;</li>
	 * 	<li>jobid is the identifier that has been assigned to the job;</li>
	 * </ul>
	 * 
	 * @param file the input data file.
	 * @param jobId the job identifier.
	 * @return a valid RDFizer datafile name.
	 */
	String rdfizerDataFilename(final File file, final Integer jobId) {
		return new StringBuilder()
			.append(file.getName())
			.append(".")
			.append(jobId)
			.toString();
	}
}