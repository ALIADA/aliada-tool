// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.linksDiscovery.rest;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.aliada.linksDiscovery.model.LinksDiscoveryResponse;
import eu.aliada.linksDiscovery.impl.LinksDiscovery;
import eu.aliada.linksDiscovery.log.MessageCatalog;
import eu.aliada.shared.log.Log;


/**
 * REST services definition for aliada-links-dicovery component.
 * /aliada/linksDiscovery
 * @author Idoia Murua
 * @since 1.0
 */
@Path("/")
public class LinksDiscoveryREST {
	private final Log logger = new Log(LinksDiscovery.class);
	private LinksDiscovery linksDisc;
	
	/**
	* Constructor. 
	* @author Idoia Murua
	* @since 1.0
	*/
	public LinksDiscoveryREST() {
		logger.info(MessageCatalog._00001_STARTING);
		linksDisc = new LinksDiscovery();
	}

	
	/**
	 * Links discovery REST service.
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
     * @return 					Response object.
     * @since 1.0
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response linksDiscovery(
		@Context HttpServletRequest request,
		@FormParam("inputURI") String inputURI,
		@FormParam("inputLogin") String inputLogin,
		@FormParam("inputPassword") String inputPassword,
		@FormParam("inputGraph") String inputGraph,
		@FormParam("outputURI") String outputURI,
		@FormParam("outputLogin") String outputLogin,
		@FormParam("outputPassword") String outputPassword,
		@FormParam("outputGraph") String outputGraph) {

		String confFileName = "D:\\Proyectos\\downloads\\SILK\\silk_2.5.3\\examples\\generatour_foaf_config_v2.xml"; 
//		String confFileName = "D:\\Proyectos\\downloads\\SILK\\silk_2.6.0\\commandline\\aliada_lido_dbpedia_config.xml"; 
		File configurationFile = new File(confFileName);
		int numThreads = 8;
		boolean reload = true;
		linksDisc.discoveryLinks(configurationFile, numThreads, reload);
		LinksDiscoveryResponse linksDiscoveryResponse = new LinksDiscoveryResponse();
		linksDiscoveryResponse.setLinksCreated(true);
		return Response.status(Response.Status.CREATED).entity(linksDiscoveryResponse).build();
	}
}