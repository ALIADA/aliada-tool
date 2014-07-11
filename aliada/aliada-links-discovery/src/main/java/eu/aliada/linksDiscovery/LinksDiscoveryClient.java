// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.linksDiscovery;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Form;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

/**
 * Implementation of an example of a Links Discovery REST service client application.
 *
 * @author Idoia Murua
 * @since 1.0
 */
public class LinksDiscoveryClient {
	protected String ALIADA_LInksDiscoveryServiceURL = "http://localhost:8890/links-discovery/";

	/**
	 * Implementation of a Links Discovery REST service client application.
	 * POST /links-discovery/jobs/
	 *
	 * @param jobid the job identifier.
	 * @since 1.0
	 */
	public void newJob(int jobid) {
		//Convert integer to string
		String s_jobid = "" + jobid;
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(ALIADA_LInksDiscoveryServiceURL);

		//Data to be sent via HTTP POST
		Form f = new Form();
		f.param("jobid", s_jobid);

		//POST (Response in XML format)
		String accept_type = MediaType.APPLICATION_XML; //If we want the response in XML format
		Response postResponse = webTarget.path("/jobs").request(accept_type).post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		System.out.println("status =" + postResponse.getStatus());
		System.out.println("response data=" + postResponse.readEntity(String.class));
	}
	
	/**
	 * Implementation of a Links Discovery REST service client application.
	 * GET /links-discovery/jobs/<jobid>
	 *
	 * @param jobid the job identifier.
	 * @since 1.0
	 */
	public void getJob(Integer jobid){
		//Convert integer to string
		String s_jobid = "" + jobid;
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(ALIADA_LInksDiscoveryServiceURL);

		//GET (Response in XML format) 
		String accept_type = MediaType.APPLICATION_XML; //If we want the response in XML format
		Response getResponse = webTarget.path("/jobs").path(s_jobid).request(accept_type).get();
		System.out.println("status =" + getResponse.getStatus());
		System.out.println("response data=" + getResponse.readEntity(String.class));

		//GET (Response in JSON format)
		accept_type = MediaType.APPLICATION_JSON; //If we want the response in JSON format
		getResponse = webTarget.path("/jobs").path(s_jobid).request(accept_type).get();
		System.out.println("status =" + getResponse.getStatus());
		System.out.println("response data=" + getResponse.readEntity(String.class));
		
	}
	

	/**
	 * Main function.
	 *
	 * @param args				Application arguments.
	 * @since 1.0
	 */
	public static void main(String[] args) {
		LinksDiscoveryClient client = new LinksDiscoveryClient();
		int jobid = 1;
		//Create a Links Discovery Job
		client.newJob(jobid);
		//Get info about the created job
		client.getJob(jobid);
	}
}
