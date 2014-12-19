// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortiums
package eu.aliada.ckancreation;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Form;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import eu.aliada.shared.log.Log;

/**
 * Implementation of an example of a CKAN Datahub Page Creation 
 * service client application.
 *
 * @author Idoia Murua
 * @since 2.0
 */
public class CKANCreationClient {
	/** For logging. */
	private static final Log LOGGER = new Log(CKANCreationClient.class);
	/** URL of the REST service to test URL. */
	protected String ALIADA_CKANCREATION_URL = "http://localhost:8888/ckan-datahub/";
	
	/**
	 * Implementation of a CKAN Datahub Page Creation REST service client application.
	 * POST /ckan-datahub/jobs/
	 *
	 * @param jobid the job identifier.
	 * @since 2.0
	 */
	public void newJob(final int jobid) {
		//Convert integer to string
		final String s_jobid = "" + jobid;
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client.target(ALIADA_CKANCREATION_URL);

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
		final WebTarget webTarget = client.target(ALIADA_CKANCREATION_URL);

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
	 * @since 2.0
	 */
	public static void main(String[] args) {
		CKANCreationClient client = new CKANCreationClient();
		final int jobid = 1;
		//Create a Links Discovery Job
		client.newJob(jobid);
		//Get info about the created job
		client.getJob(jobid);
	}
}
