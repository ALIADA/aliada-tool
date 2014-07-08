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
 * Implementation of a Links Discovery REST service client application. 
 * @author Idoia Murua
 * @since 1.0
 */
public class LinksDiscoveryClient {
	protected String ALIADA_LInksDiscoveryServiceURL = "http://localhost:8890/links-discovery/";
	

	/**
	 * Implementation of a Links Discovery REST service client application.
	 *
     * @param inputURI			URI of the SPARQL/Update endpoint of the source dataset from where we want to generate links. 
     * @param inputLogin		Login required for authentication in the SPARQL endpoint. 
     * @param inputPassword		Password required for authentication in the SPARQL endpoint. 
     * @param inputGraph		Only retrieve instances from a specific graph in that SPARQL endpoint. 
     * 							If not specified, the query will not be restricted to any specific graph.
     * @param outputURI			URI of the SPARQL/Update endpoint of the dataset where to store the generated links. If omitted, the input URI will be used.
     * @param outputLogin		Login required for authentication in the SPARQL/Update endpoint. 
     * @param outputPassword	Password required for authentication in the SPARQL/Update endpoint. 
     * @param outputGraph		The URI of the graph where to put the dicovered links. If not specified, no graph will be used for the update.
     *
     * @return 					
     * @since 1.0
	 */
	public void linksDiscover(String inputURI, String inputLogin, String inputPassword, String inputGraph, String outputURI, String outputLogin, String outputPassword, String outputGraph){
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(ALIADA_LInksDiscoveryServiceURL);
		
		//Data to be sent via HTTP POST
		Form f = new Form();
		f.param("inputURI", inputURI);
		f.param("inputLogin", inputLogin);
		f.param("inputPassword", inputPassword);
		f.param("inputGraph", inputGraph);
		f.param("outputURI", outputURI);
		f.param("outputLogin", outputLogin);
		f.param("outputPassword", outputPassword);
		f.param("outputGraph", outputGraph);

		//POST 
		Response postResponse = webTarget.request().post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		System.out.println(postResponse.getStatus());
		System.out.println(postResponse.readEntity(String.class));
	}
	
	/**
	 * Implementation of a Links Discovery REST service client application.
	 *
     *
     * @return 					
     * @since 1.0
	 */
	public void newJob(Integer id){
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(ALIADA_LInksDiscoveryServiceURL);

		//Data to be sent via HTTP POST
		Form f = new Form();
		f.param("jobid", "1");

		//POST 
		Response postResponse = webTarget.path("/jobs").request().post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		System.out.println(postResponse.getStatus());
		System.out.println(postResponse.readEntity(String.class));
	}
	

	/**
	 * Main function.
	 *
     * @param args				Application arguments. 
     *
     * @return 					
     * @since 1.0
	 */
	public static void main(String[] args) {
		LinksDiscoveryClient client = new LinksDiscoveryClient();
		String inputURI = "";		
		String inputLogin = "";	 
		String inputPassword = ""; 
		String inputGraph = "";
		String outputURI = "";
		String outputLogin = ""; 
		String outputPassword = ""; 
		String outputGraph = "";
//		client.linksDiscover(inputURI, inputLogin, inputPassword, inputGraph, outputURI, outputLogin, outputPassword, outputGraph);
		client.newJob(1);
	}
}
