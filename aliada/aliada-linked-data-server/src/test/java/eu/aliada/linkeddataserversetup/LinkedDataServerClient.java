// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-linked-data-server
// Responsible: ALIADA Consortium
package eu.aliada.linkeddataserversetup;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Form;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import eu.aliada.shared.log.Log;

/**
 * Implementation of an example of a Linked Data Server REST 
 * service client application.
 *
 * @author Idoia Murua
 * @since 1.0
 */
public class LinkedDataServerClient {
	/** For logging. */
	private static final Log LOGGER = new Log(LinkedDataServerClient.class);
	/** URL of the REST service to test URL. */
	//protected static final String ALIADA_LINKEDDATASERVER_URL = "http://localhost:8889/lds/";
	protected static final String ALIADA_LINKEDDATASERVER_URL = "http://aliada.scanbit.net:8080/aliada-linked-data-server-1.0/";

	/**
	 * Implementation of a Linked Data Server REST service client application.
	 * POST /lds/jobs/
	 *
	 * @param jobid the job identifier.
	 * @since 1.0
	 */
	public void newJob(final int jobid) {
		//Convert integer to string
		final String sJobid = "" + jobid;
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client.target(ALIADA_LINKEDDATASERVER_URL);

		//Data to be sent via HTTP POST
		final Form form = new Form();
		form.param("jobid", sJobid);

		//POST (Response in XML format)
		final String acceptType = MediaType.APPLICATION_XML; //If we want the response in XML format
		final Response postResponse = webTarget.path("/jobs").request(acceptType).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		LOGGER.info("status =" + postResponse.getStatus());
		LOGGER.info("response data=" + postResponse.readEntity(String.class));
	}
	
	/**
	 * Implementation of a Linked Data Server REST service client application.
	 * GET /lds/jobs/<jobid>
	 *
	 * @param jobid the job identifier.
	 * @since 1.0
	 */
	public void getJob(final Integer jobid){
		//Convert integer to string
		final String sJobid = "" + jobid;
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client.target(ALIADA_LINKEDDATASERVER_URL);

		//GET (Response in XML format) 
		String acceptType = MediaType.APPLICATION_XML; //If we want the response in XML format
		Response getResponse = webTarget.path("/jobs").path(sJobid).request(acceptType).get();
		LOGGER.info("status =" + getResponse.getStatus());
		LOGGER.info("response data=" + getResponse.readEntity(String.class));

		//GET (Response in JSON format)
		acceptType = MediaType.APPLICATION_JSON; //If we want the response in JSON format
		getResponse = webTarget.path("/jobs").path(sJobid).request(acceptType).get();
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
		final LinkedDataServerClient client = new LinkedDataServerClient();
		final int jobid = 1;
		//Create a Linked Data Server Job
		client.newJob(jobid);
		//Get info about the created job
		client.getJob(jobid);
	}
}
