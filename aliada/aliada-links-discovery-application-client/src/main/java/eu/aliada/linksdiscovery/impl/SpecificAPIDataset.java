// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery-application-client
// Responsible: ALIADA Consortium
package eu.aliada.linksdiscovery.impl;

import java.util.ArrayList;
import java.lang.Math;
import java.io.ByteArrayInputStream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Resource;

import eu.aliada.linksdiscovery.model.LOBIDSearchResponse;
import eu.aliada.linksdiscovery.model.VIAFSearchResponse;
import eu.aliada.linksdiscovery.model.OpenLibrSearchResponse;
import eu.aliada.linksdiscovery.model.JobConfiguration;
import eu.aliada.linksdiscovery.model.SubjobConfiguration;
import eu.aliada.linksdiscovery.model.ResourceToLink;
import eu.aliada.shared.log.Log;
import eu.aliada.linksdiscovery.log.MessageCatalog;
import eu.aliada.shared.rdfstore.RDFStoreDAO;

public class SpecificAPIDataset {
	/** For logging. */
	private static final Log LOGGER  = new Log(SpecificAPIDataset.class);
	/** RDF namespace. */
	protected final static String RDF_NAMESPACE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	/** SKOS namespace. */
	protected final static String SKOS_NAMESPACE = "http://www.w3.org/2004/02/skos/core#";
	/** CIDOC-CRM namespace. */
	protected final static String ECRM_NAMESPACE = "http://erlangen-crm.org/current/";
	/** EFRBRoo namespace. */
	protected final static String EFRBROO_NAMESPACE = "http://erlangen-crm.org/efrbroo/";
	/** owl:SameAs property. */
	protected final static String SAMEAS_PROP = "http://www.w3.org/2002/07/owl#sameAs";
	/** Maximum search results to use to generate the links. */
	private static final int MAX_SEARCH_RESULTS  = 1;
	/** RDFStore instance to use some of the functions provided by it. */
	final RDFStoreDAO rdfstoreDAO;
	/** Query to get resources to look for linked entities in VIAF (persons' and organisations' names). */
	private String queryVIAF; 
	private String queryOpenLibr; 
	private String queryLOBIDOrg; 
	private String queryLOBIDBibRes;
	private String queryLCSubjectHead;
	/** Property name to generate new triples with links. */
	private String propertyNameVIAF;
	private String propertyNameLOBID;
	private String propertyNameOpenLibr;
	private String propertyLCSubjectHead;
	/** Maximum PAGE SIZE for SPARQL. */
	private final int MAX_PAGE_SIZE = 1000;
	/** Maximum number of triples to in every SPARQL INSERT statement. */
	private final int MAX_NUMBER_TRIPLES = 100;

	/**
	 * Constructor.
	 * 
	 * @since 2.0
	 */
	public SpecificAPIDataset (final String inputGraphName) {
		//Initialize some global variables
		rdfstoreDAO = new RDFStoreDAO();
		final String queryStart = "PREFIX rdf: <" + RDF_NAMESPACE + ">"
            	+ " PREFIX skos: <" + SKOS_NAMESPACE + ">"
            	+ " PREFIX ecrm: <" + ECRM_NAMESPACE + ">"
            	+ " PREFIX efrbroo: <" + EFRBROO_NAMESPACE + ">"
            	+ " SELECT DISTINCT ?res ?text FROM <" + inputGraphName + "> WHERE {"; 
		//Queries
		queryVIAF = queryStart  
				+ " {?res rdf:type ecrm:E39_Actor . ?res ecrm:P131_is_identified_by  ?apel . ?apel ecrm:P3_has_note ?text}" 
				+ " UNION {?res rdf:type ecrm:E21_Person . ?res ecrm:P131_is_identified_by  ?apel . ?apel ecrm:P3_has_note ?text}" 
				+ " UNION {?res rdf:type efrbroo:F10_Person . ?res ecrm:P131_is_identified_by  ?apel . ?apel ecrm:P3_has_note ?text}"
				+ "} ORDER BY ?res";	
		queryLOBIDOrg = queryStart
				+ " {?res rdf:type ecrm:E39_Actor . ?res ecrm:P131_is_identified_by  ?apel . ?apel ecrm:P3_has_note ?text}" 
				+ "} ORDER BY ?res";
		queryLOBIDBibRes = queryStart
            	+ " ?res rdf:type efrbroo:F3_Manifestation_Product_Type . "
            	+ " ?res ecrm:P102_has_title ?apel . "
            	+ " ?apel ecrm:P3_has_note ?text "
            	+ "} ORDER BY ?res";
		queryOpenLibr = queryLOBIDBibRes;
		queryLCSubjectHead = queryStart
            	+ " {?obj rdf:type ecrm:E89_PropositionalObject . "
            	+ " ?obj ecrm:P129_is_about ?res . "
            	+ " ?res rdf:type skos:Concept . "
            	+ " ?res ecrm:P3_has_note ?text }"
            	+ " UNION "
            	+ " {?obj rdf:type ecrm:E89_PropositionalObject . "
            	+ " ?obj ecrm:P129_is_about ?res . "
            	+ " ?res rdf:type skos:Concept . "
            	+ " ?res skos:prefLabel ?text }"
            	+ " UNION "
            	+ " {?obj ecrm:P137_exemplifies ?res . "
            	+ " ?res rdf:type skos:Concept . "
            	+ " ?res ecrm:P3_has_note ?text }"
				+ " UNION "
				+ " {?obj ecrm:P137_exemplifies ?res . "
				+ " ?res rdf:type skos:Concept . "
				+ " ?res skos:prefLabel ?text }"
				+ "} ORDER BY ?res";
		//Properties
		propertyNameVIAF = SAMEAS_PROP;
		propertyNameLOBID = SAMEAS_PROP;
		propertyNameOpenLibr = SAMEAS_PROP;
		propertyLCSubjectHead = SAMEAS_PROP;
	}

	/**
	 * It executes a SELECT SPARQL query on the SPARQL endpoint, to get the URIs of the resources 
	 *   to use to discover links towards external datasets.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param graphName 			the graphName, null in case of default graph.
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @param query					the query to use to look for the resources.
	 * @param offset				causes the solutions generated to start after the specified number of solutions.
	 * @param limit					upper bound on the number of solutions returned.
	 * @return a list of the matched URIs.
	 * @since 2.0
	 */
	public ResourceToLink[] getResourcesToLink(final String sparqlEndpointURI, final String user, final String password, final String sparql, final Integer offset, final Integer limit) {
	  	ArrayList<ResourceToLink> resourcesList = new ArrayList<ResourceToLink>();
	  	final String query = sparql + " OFFSET " + offset + " LIMIT " + limit;
        // Execute the query and obtain results
	  	ResultSet results = rdfstoreDAO.executeSelect(sparqlEndpointURI, user, password, query);
	 	if (results != null){
            while (results.hasNext())
            {
            	final QuerySolution soln = results.nextSolution() ;
            	final Resource res = soln.getResource("res");
        		final String resURI = res.getURI();
            	final String textToSearch = soln.getLiteral("text").getString();
            	final ResourceToLink resToLink = new ResourceToLink(textToSearch, resURI);
        		resourcesList.add(resToLink);
            }
		}
		if (resourcesList.isEmpty()) {
			return new ResourceToLink[0];
		}
		return (ResourceToLink[]) resourcesList.toArray(new ResourceToLink[resourcesList.size()]);
	}

	/**
	 * It searches for links in LOBID external dataset.
	 *
	 * @param text					text to search for.  
	 * @param externalDatasetName 	external dataset name.
	 * @return a list of the found URIs in the external dataset.
	 * @since 2.0
	 */
	public String[] searchExternalDatasetLOBID (String text, String externalDatasetName) {
		String[] searchResults = null;
		final String searchURI = "http://api.lobid.org";
		String path = null;
		if(externalDatasetName.toUpperCase().contains("ORG")) {
			path = "/organisation";
		} else {
			path = "/resource";
		}
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client.target(searchURI);

		//GET (Response in JSON format)
		String acceptType = MediaType.APPLICATION_JSON; //If we want the response in JSON format
		final WebTarget resourceWebTarget = webTarget.path(path);
		final Response getResponse = resourceWebTarget
				.queryParam("format", "ids")
				.queryParam("name", text)
				.queryParam("from", 0)
				.queryParam("size", 10) //maximum items to return
				.request(acceptType).get();
		final String searchRespStr = getResponse.readEntity(String.class);
		//Convert JSON representation to Java Object with JACKSON libraries
		LOBIDSearchResponse[] searchResp = null;
		final ObjectMapper mapper = new ObjectMapper();
		try {
			searchResp = mapper.readValue(searchRespStr, LOBIDSearchResponse[].class);
    	} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00082_OBJECT_CONVERSION_FAILURE, exception);
		}
		if(searchResp != null) {
			int numLinks = 0;
			if(searchResp.length > MAX_SEARCH_RESULTS) {
				numLinks = MAX_SEARCH_RESULTS; 
			} else {
				numLinks = searchResp.length; 
			}
			searchResults = new String[numLinks];
			for (int i=0; i< numLinks;i++){
				searchResults[i] = searchResp[i].getValue();
			}
		}
		return searchResults;
	}

	/**
	 * It searches for links in VIAF external dataset.
	 *
	 * @param text					text to search for.  
	 * @param externalDatasetName 	external dataset name.
	 * @return a list of the found URIs in the external dataset.
	 * @since 2.0
	 */
	public String[] searchExternalDatasetVIAF (String text, String externalDatasetName) {
		String[] searchResults = null;
		final String searchURI = "http://www.viaf.org/viaf/AutoSuggest";
		final String resURI = "http://www.viaf.org/viaf/";
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client.target(searchURI);

		//GET (Response in JSON format)
		String acceptType = MediaType.APPLICATION_JSON; //If we want the response in JSON format
		final Response getResponse = webTarget
				.queryParam("query", text)
				.request(acceptType).get();
		final String searchRespStr = getResponse.readEntity(String.class);
		//Convert JSON representation to Java Object with JACKSON libraries
		VIAFSearchResponse searchResp = null;
		final ObjectMapper mapper = new ObjectMapper();
		try {
			searchResp = mapper.readValue(searchRespStr, VIAFSearchResponse.class);
    	} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00082_OBJECT_CONVERSION_FAILURE, exception);
		}
		if(searchResp != null) {
			if(searchResp.getResult() != null) {
				int numLinks = 0;
				if(searchResp.getResult().length > MAX_SEARCH_RESULTS) {
					numLinks = MAX_SEARCH_RESULTS; 
				} else {
					numLinks = searchResp.getResult().length; 
				}
				searchResults = new String[numLinks];
				for (int i=0; i< numLinks;i++){
					searchResults[i] = resURI + searchResp.getResult()[i].getViafid();
				}
			}
		}
		return searchResults;
	}

	/**
	 * It searches for links in Open Library external dataset.
	 *
	 * @param text					text to search for.  
	 * @param externalDatasetName 	external dataset name.
	 * @return a list of the found URIs in the external dataset.
	 * @since 2.0
	 */
	public String[] searchExternalDatasetOpenLibr (String text, String externalDatasetName) {
		String[] searchResults = null;
		final String searchURI = "https://openlibrary.org/search";
		final String resURI = "http://openlibrary.org";
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client.target(searchURI);

		//GET (Response in JSON format)
		String acceptType = MediaType.APPLICATION_JSON; //If we want the response in JSON format
		final Response getResponse = webTarget
				.queryParam("title", text)
				.request(acceptType).get();
		final String searchRespStr = getResponse.readEntity(String.class);
		//Convert JSON representation to Java Object with JACKSON libraries
		OpenLibrSearchResponse searchResp = null;
		final ObjectMapper mapper = new ObjectMapper();
		try {
			searchResp = mapper.readValue(searchRespStr, OpenLibrSearchResponse.class);
    	} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00082_OBJECT_CONVERSION_FAILURE, exception);
		}
		if(searchResp != null) {
			if(searchResp.getDocs() != null) {
				int numLinks = 0;
				if(searchResp.getDocs().length > MAX_SEARCH_RESULTS) {
					numLinks = MAX_SEARCH_RESULTS; 
				} else {
					numLinks = searchResp.getDocs().length; 
				}
				searchResults = new String[numLinks];
				for (int i=0; i< numLinks;i++){
					searchResults[i] = resURI + searchResp.getDocs()[i].getKey();
				}
			}
		}
		return searchResults;
	}

	/**
	 * It searches for links in Library of Congress Subject Headings external dataset.
	 *
	 * @param text					text to search for.  
	 * @param externalDatasetName 	external dataset name.
	 * @return a list of the found URIs in the external dataset.
	 * @since 2.0
	 */
	public String[] searchExternalDatasetLC (String text, String externalDatasetName) {
		 ArrayList<String> searchResults = new ArrayList<String>();
		final String searchURI = "http://id.loc.gov/search/";
		final String resURI = "http://id.loc.gov";
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client.target(searchURI);

		//GET (Response in XML format)
		String acceptType = MediaType.APPLICATION_XML; //If we want the response in XML format
		final Response getResponse = webTarget
				.queryParam("q", text)
				.queryParam("q", "cs:http://id.loc.gov/authorities/subjects")
				.queryParam("format", "xml")
				.request(acceptType).get();
		final String searchRespStr = getResponse.readEntity(String.class);
		//Parse XML string returned
		try {
			final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			final Document doc = dBuilder.parse(new ByteArrayInputStream(searchRespStr.getBytes("utf-8")));		 
			doc.getDocumentElement().normalize();
			int numLinks = 0;
			//Get <search:result> tags
			NodeList nList = doc.getElementsByTagName("search:result");
			for (int temp = 0; (temp < nList.getLength()) && (numLinks < MAX_SEARCH_RESULTS); temp++) {
				final Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					final Element resultElem = (Element) nNode;
					final String uriValue = resultElem.getAttribute("uri");	
					String uriValueNoext = uriValue;
					if(uriValue.indexOf(".") > 0) {
						uriValueNoext = uriValue.substring(0, uriValue.indexOf("."));
					}
					searchResults.add(resURI + uriValueNoext);
					numLinks++;
				}
			}
		} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00082_OBJECT_CONVERSION_FAILURE, exception);
		}
		return (String[]) searchResults.toArray(new String[searchResults.size()]);
	}

	/**
	 * It searches for links in the external datasets. It chooses the appropriate 
	 * search function depending on the dataset name.
	 *
	 * @param text					text to search for.  
	 * @param externalDatasetName 	external dataset name.
	 * @return a list of the found URIs in the external dataset.
	 * @since 2.0
	 */
	public String[] searchExternalDataset (String text, String externalDatasetName) {
		String[] searchResults = null;
		if(externalDatasetName.toUpperCase().contains("LOBID")) {
			searchResults = searchExternalDatasetLOBID (text, externalDatasetName);
		} else if(externalDatasetName.toUpperCase().contains("VIAF")) {
			searchResults = searchExternalDatasetVIAF (text, externalDatasetName);
		} else if(externalDatasetName.toUpperCase().contains("CONGRESS")) {
			searchResults = searchExternalDatasetLC (text, externalDatasetName);
		} else if(externalDatasetName.toUpperCase().contains("OPEN")) {
			searchResults = searchExternalDatasetOpenLibr (text, externalDatasetName);
		}
		return searchResults;
	}

	/**
	 * It performs the search in the external datasets and inserts the discovered links in the appropriate graph.
	 *
	 * @param jobConf		the {@link eu.aliada.linksdiscovery.model.JobConfiguration}
	 *						that contains global information for configuring the linking processes.  
	 * @param subjobConf 	the {@link eu.aliada.linksdiscovery.model.SubjobConfiguration}
	 *						which contains the configuration of the subjob.
	 * @return number of links generated
	 * @since 2.0
	 */
	public int searchProcess (JobConfiguration jobConf, SubjobConfiguration subjobConf) {
		String query = null;
		String propertyName = null;
		int numLinks = 0; 
		String logExtDatasetName = "";
		//Select the appropriate query and property name to use, depending on the external dataset
		if(subjobConf.getName().toUpperCase().contains("LOBID")) {
			propertyName = propertyNameLOBID;
			if(subjobConf.getName().toUpperCase().contains("ORG")) {
				query = queryLOBIDOrg;
				logExtDatasetName = "LOBID. Index of libraries and related organisations";
			} else {
				query = queryLOBIDBibRes;
				logExtDatasetName = "LOBID. Bibliographic Resources";
			}
		} else if(subjobConf.getName().toUpperCase().contains("VIAF")) {
			propertyName = propertyNameVIAF;
			query = queryVIAF;
			logExtDatasetName = "VIAF";
		} else if(subjobConf.getName().toUpperCase().contains("CONGRESS")) {
			propertyName = propertyLCSubjectHead;
			query = queryLCSubjectHead;
			logExtDatasetName = "Library of Congress Subject Headings";
		} else if(subjobConf.getName().toUpperCase().contains("OPEN")) {
			propertyName = propertyNameOpenLibr;
			query = queryOpenLibr;
			logExtDatasetName = "Open Library";
		}
		//Begin the search process
		LOGGER.info(MessageCatalog._00083_SPECIFICAPI_STARTING, logExtDatasetName);
		if((query!= null) && (propertyName!= null)) {
			Integer maxLimit = Integer.MAX_VALUE;
			int pageSize = MAX_PAGE_SIZE;
			Integer offset = 0;
			Integer limit = Math.min(pageSize, maxLimit - offset);
			// Get some of the URIs of the resources to use to discover links towards external datasets.
			ResourceToLink[] resToLink= getResourcesToLink(jobConf.getInputURI(), jobConf.getInputLogin(), jobConf.getInputPassword(), query, offset, limit);
			String triples = "";
			int numInsertTriples = 0;
			while (resToLink.length > 0) {
				//For each resource to link, do a search in the external dataset
				for (int i=0; i<resToLink.length;i++){
					String[] externalRes = searchExternalDataset(resToLink[i].getText(), subjobConf.getName());
					if(externalRes != null) {
						for (int j=0; j<externalRes.length;j++) {
							String triple = "<" + resToLink[i].getResURI() + "> <" + propertyName + "> <" + externalRes[j] + ">";    
							triples = triples + triple + " . ";
							numLinks++;
							numInsertTriples++;
							if(numInsertTriples >= MAX_NUMBER_TRIPLES) {
							//Insert the discovered links in the corresponding graph
								try {
									rdfstoreDAO.executeInsert(jobConf.getOutputURI(), jobConf.getOutputGraph(), jobConf.getOutputLogin(), jobConf.getOutputPassword(), triples);
								} catch (final Exception exception) {
									LOGGER.error(MessageCatalog._00080_RDFSTORE_FAILURE, exception);
								}
								triples = "";
								numInsertTriples = 0;
							}
						}
					}
				}
				offset = offset + pageSize;
				limit = Math.min(pageSize, maxLimit - offset);
				// Get the rest of the URIs of the resources to use to discover links towards external datasets.
				resToLink= getResourcesToLink(jobConf.getInputURI(), jobConf.getInputLogin(), jobConf.getInputPassword(), query, offset, limit);
			}
			if(numInsertTriples > 0) {
			//Insert the remaining discovered links in the corresponding graph
				try {
					rdfstoreDAO.executeInsert(jobConf.getOutputURI(), jobConf.getOutputGraph(), jobConf.getOutputLogin(), jobConf.getOutputPassword(), triples);
				} catch (final Exception exception) {
					LOGGER.error(MessageCatalog._00080_RDFSTORE_FAILURE, exception);
				}
			}
		}
		LOGGER.info(MessageCatalog._00084_SPECIFICAPI_FINISHED, logExtDatasetName);
		return numLinks;	
	}

}
