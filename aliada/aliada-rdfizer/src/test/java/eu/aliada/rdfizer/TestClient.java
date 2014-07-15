// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.JerseyClient;

public class TestClient {

	/**
	 * @param args
	 */   
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://localhost:8891/rdfizer");
		Response response = webTarget.path("/jobs/1").request().put(Entity.text("1"));
		System.out.println("status =" + response.getStatus());
		System.out.println("response data=" + response.readEntity(String.class));
	}
}
