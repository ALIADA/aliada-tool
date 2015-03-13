// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortiums
package eu.aliada.linksdiscovery;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.aliada.shared.log.Log;

     
/**
 * Implementation of an example of a Links Discovery REST 
 * service client application.
 *
 * @author Idoia Murua
 * @since 1.0
 */
public class LinksDiscoveryClient {
	/** For logging. */
	private static final Log LOGGER = new Log(LinksDiscoveryClient.class);
	/** URL of the REST service to test URL. */
	protected static final String ALIADA_LINKSDISCOVERYSERVICE_URL = "http://localhost:8890/links-discovery/";
//	protected static final String ALIADA_LINKSDISCOVERYSERVICE_URL = "http://aliada.scanbit.net:8080/aliada-links-discovery-1.0/";
//	protected static final String ALIADA_LINKSDISCOVERYSERVICE_URL = "http://aliada.szepmuveszeti.hu/aliada-links-discovery-1.0/";

	/**
	 * Implementation of a Links Discovery REST service client application.
	 * POST /links-discovery/jobs/
	 *
	 * @param jobid the job identifier.
	 * @since 1.0
	 */
	public void newJob(final int jobid) {
		//Convert integer to string
		final String s_jobid = "" + jobid;
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client.target(ALIADA_LINKSDISCOVERYSERVICE_URL);

		//Data to be sent via HTTP POST
		final Form form = new Form();
		form.param("jobid", s_jobid);

		//POST (Response in XML format)
		final String acceptType = MediaType.APPLICATION_XML; //If we want the response in XML format
		final Response postResponse = webTarget.path("/jobs").request(acceptType).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		LOGGER.info("status =" + postResponse.getStatus());
		LOGGER.info("response data=" + postResponse.readEntity(String.class));
	}
	
	/**
	 * Implementation of a Links Discovery REST service client application.
	 * GET /links-discovery/jobs/<jobid>
	 *
	 * @param jobid the job identifier.
	 * @since 1.0
	 */
	public void getJob(final Integer jobid){
		//Convert integer to string
		final String s_jobid = "" + jobid;
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client.target(ALIADA_LINKSDISCOVERYSERVICE_URL);

		//GET (Response in XML format) 
		String acceptType = MediaType.APPLICATION_XML; //If we want the response in XML format
		Response getResponse = webTarget.path("/jobs").path(s_jobid).request(acceptType).get();
		LOGGER.info("status =" + getResponse.getStatus());
		LOGGER.info("response data=" + getResponse.readEntity(String.class));

		//GET (Response in JSON format)
		acceptType = MediaType.APPLICATION_JSON; //If we want the response in JSON format
		getResponse = webTarget.path("/jobs").path(s_jobid).request(acceptType).get();
		LOGGER.info("status =" + getResponse.getStatus());
		LOGGER.info("response data=" + getResponse.readEntity(String.class));
		
	}
	

	/**
	 * Main function.
	 *
	 * @param args				Application arguments.
	 * @since 1.0
	 */
	public static void main(final String[] args) {
		final LinksDiscoveryClient client = new LinksDiscoveryClient();
		final int jobid = 1;
		//Create a Links Discovery Job
		client.newJob(jobid);
		//Get info about the created job
		client.getJob(jobid);
	}
}

