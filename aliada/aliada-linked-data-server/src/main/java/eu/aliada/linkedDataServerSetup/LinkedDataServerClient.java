// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-linked-data-server
// Responsible: ALIADA Consortium
package eu.aliada.linkedDataServerSetup;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Form;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

/**
 * Implementation of an example of a Linked Data Server REST service client application.
 *
 * @author Idoia Murua
 * @since 1.0
 */
public class LinkedDataServerClient {
	//protected String ALIADA_LinkedDataServerURL = "http://localhost:8889/lds/";
	protected String ALIADA_LinkedDataServerURL = "http://aliada.scanbit.net:8080/aliada-linked-data-server-1.0/";

	/**
	 * Implementation of a Linked Data Server REST service client application.
	 * POST /lds/jobs/
	 *
	 * @param jobid the job identifier.
	 * @since 1.0
	 */
	public void newJob(int jobid) {
		//Convert integer to string
		String s_jobid = "" + jobid;
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(ALIADA_LinkedDataServerURL);

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
	 * Implementation of a Linked Data Server REST service client application.
	 * GET /lds/jobs/<jobid>
	 *
	 * @param jobid the job identifier.
	 * @since 1.0
	 */
	public void getJob(Integer jobid){
		//Convert integer to string
		String s_jobid = "" + jobid;
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(ALIADA_LinkedDataServerURL);

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
		LinkedDataServerClient client = new LinkedDataServerClient();
		int jobid = 1;
		//Create a Linked Data Server Job
		client.newJob(jobid);
		//Get info about the created job
		client.getJob(jobid);
	}
}
