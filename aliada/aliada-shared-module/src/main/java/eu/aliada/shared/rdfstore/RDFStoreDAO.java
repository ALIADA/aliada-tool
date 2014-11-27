// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-shared
// Responsible: ALIADA Consortium
package eu.aliada.shared.rdfstore;

import static eu.aliada.shared.Strings.isNotNullAndNotEmpty;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.codec.binary.Base64;
import org.apache.jena.atlas.web.auth.HttpAuthenticator;
import org.apache.jena.atlas.web.auth.SimpleAuthenticator;

import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Resource;

import eu.aliada.shared.log.Log;
import eu.aliada.shared.log.MessageCatalog;

/**
 * RDF Store common operations. 
 *  
 * @author Idoia Murua
 * @since 1.0
 */
public class RDFStoreDAO {
	/** For logging. */
	private static final Log LOGGER = new Log(RDFStoreDAO.class);
	/** HTTP authentication cache. */
	private final Map<String, HttpAuthenticator> authCache = new HashMap<String, HttpAuthenticator>();
	
	/**
	 * It clears the graph in the RDF store using ISQL.
	 *
	 * @param isqlCommandPath	the path of the ISQL command of the RDF store.  
	 * @param storeIp			the IP of the RDF store.  
	 * @param storeSqlPort		the port for SQL data access.  
	 * @param user				the login required for authentication in the RDF store.
	 * @param password			the password required for authentication in the RDF store.
	 * @param graphUri			the URI of the graph.
	 * @return true if the graph has been cleared. False otherwise.
	 * @since 1.0
	 */
	public boolean clearGraphByIsql(final String isqlCommandPath, final String storeIp, final int storeSqlPort, final String user, final String password, final String graphUri) {
		final boolean done = false;
		final String clearGraphSPARQL = "EXEC=SPARQL CLEAR GRAPH <" + graphUri + ">;";
		//Compose ISQL command calling statement
		final String isqlCommandFormat = "%s %s:%d %s %s \"%s\"";
		final String isqlCommand = String.format(isqlCommandFormat,
				isqlCommandPath, storeIp,
				storeSqlPort, user,
				password, clearGraphSPARQL);
		//Execute ISQL command
		try {
			Runtime.getRuntime().exec(isqlCommand);
		} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00033_EXTERNAL_PROCESS_START_FAILURE, exception, isqlCommand);
		}
		return done;
	}

	/**
	 * It loads triples in a graph in the RDF store using HTTP PUT.
	 *
	 * @param triplesFilename	the path of the file containing the triples to load.  
	 * @param rdfSinkFolder		the URI of the RDF SINK folder in the RDF Store.  
	 * @param user				the login required for authentication in the RDF store.
	 * @param password			the password required for authentication in the RDF store.
	 * @return true if the triples have been loaded. False otherwise.
	 * @since 1.0
	 */
	public boolean loadDataIntoGraphByHTTP(final String triplesFilename, String rdfSinkFolder, final String user, final String password) {
		boolean done = false;
		//Get the triples file name
		final File triplesFile = new File(triplesFilename);
		final String triplesFilenameNoPath = triplesFile.getName();
		//Append the file name to the rdfSinkFolder 
		if (rdfSinkFolder != null && !rdfSinkFolder.endsWith("/")) {
			rdfSinkFolder = rdfSinkFolder + "/";
		}
		rdfSinkFolder = rdfSinkFolder + triplesFilenameNoPath;
		//HTTP Authentication
		final Base64 base64 = new Base64();
		final byte[] authenticationArray = new String(user + ":" + password).getBytes();
		final String encoding = base64.encodeAsString(authenticationArray);
		
		try {
			final URL putUrl = new URL(rdfSinkFolder);
			final HttpURLConnection connection = (HttpURLConnection) putUrl.openConnection();
	
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("PUT");
			//Write Authentication
			connection.setRequestProperty  ("Authorization", "Basic " + encoding);			
			//Write File Data
			final OutputStream outStream = connection.getOutputStream();		
			final InputStream inStream = new FileInputStream(triplesFilename);
			final byte buf[] = new byte[1024];
			int len;
			while ((len = inStream.read(buf)) > 0) {
				outStream.write(buf, 0, len);
			}
			inStream.close();
			final int responseCode=((HttpURLConnection)connection).getResponseCode();
			if (responseCode>= 200 && responseCode<=202) {
				done = true;
			} else {
				done = false;
			}
			((HttpURLConnection)connection).disconnect();
		} catch (Exception exception) {
			done = false;
			LOGGER.error(MessageCatalog._00034_HTTP_PUT_FAILED, exception, rdfSinkFolder);
	    }
		return done;
	}

	/**
	 * It executes an UPDATE SPARQL query on the SPARQL endpoint.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @param query					the UPDATE query to execute.
	 * @return true if the SPARQL has been executed. False otherwise.
	 * @since 1.0
	 */
	public boolean executeUpdateQuerySparqlEndpoint(final String sparqlEndpointURI, final String user, final String password, final String query) {
		boolean done = false;
		try {
			UpdateExecutionFactory.createRemoteForm(
					UpdateFactory.create(query), 
					sparqlEndpointURI, 
					auth(sparqlEndpointURI, user, password))
				.execute();
			done = true;
		} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00035_SPARQL_FAILED, exception, query);
		}
		return done;
	}
	
	/**
	 * It executes an INSERT SPARQL query on the SPARQL endpoint.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @param triples				the triples that will be added.
	 * throws Exception in case the INSERT goes wrong.
	 */
	public void executeInsert(
			final String sparqlEndpointURI, 
			final String graphName, 
			final String user, 
			final String password, 
			final String triples) throws Exception {
		UpdateExecutionFactory.createRemoteForm(
				UpdateFactory.create(buildInsertQuery(graphName, triples)), 
				sparqlEndpointURI, 
				auth(sparqlEndpointURI, user, password))
			.execute();
	}	

	/**
	 * It clears the graph in the RDF store using SPARQL endpoint.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @param graphUri				the URI of the graph.
	 * @return true if the graph has been cleared. False otherwise.
	 * @since 1.0
	 */
	public boolean clearGraphBySparql(final String sparqlEndpointURI, final String user, final String password, final String graphUri) {
		boolean done = false;
		final String clearGraphSPARQL = "CLEAR GRAPH <" + graphUri + ">;";
		//Execute SPARQL
		done = executeUpdateQuerySparqlEndpoint(sparqlEndpointURI, user, password, clearGraphSPARQL);
		return done;
	}
	
	/**
	 * It loads triples in a graph in the RDF store using SPARQL endpoint.
	 *
	 * @param triplesFilename	    the path of the file containing the triples to load.  
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @param graphUri				the URI of the graph.
	 * @return true if the triples have been loaded in the graph. False otherwise.
	 * @since 1.0
	 */
	public boolean loadDataIntoGraphBySparql(final String triplesFilename, final String sparqlEndpointURI, final String user, final String password, final String graphUri) {
		boolean done = true;
		//The following lines only works if the file is located where Virtuoso is installed
		//final String loadDataSPARQL = "LOAD <file://" + triplesFilename + "> INTO GRAPH <" + graphUri + ">;";
		//Execute SPARQL
		//done = executeUpdateQuerySparqlEndpoint(sparqlEndpointURI, user, password, loadDataSPARQL);
		try {
			FileInputStream fTriples = new FileInputStream(triplesFilename);
			BufferedReader inTriples = new BufferedReader(new InputStreamReader(fTriples));
			String line;
			String triples = "";
			int numTriples = 0;
			while (((line = inTriples.readLine()) != null) && done){
			    // Deal with the line
				triples = triples + line;
				numTriples++;
				if(numTriples >= 10) {
					try {
						LOGGER.debug(MessageCatalog._00049_DEBUG_TRIPLES, triples);
						executeInsert(sparqlEndpointURI, graphUri, user, password, triples);
					} catch (Exception exception) {
						done = false;
						LOGGER.error(MessageCatalog._00035_SPARQL_FAILED, exception, "INSERT");
					}
					triples = "";
					numTriples = 0;
				}
			}
			if(done && (numTriples > 0)) {
				try {
					LOGGER.debug(MessageCatalog._00049_DEBUG_TRIPLES, triples);
					executeInsert(sparqlEndpointURI, graphUri, user, password, triples);
				} catch (Exception exception) {
					done = false;
					LOGGER.error(MessageCatalog._00035_SPARQL_FAILED, exception, "INSERT");
				}
			}
			// Done with the file
			inTriples.close();
		} catch (IOException exception) {
			done = false;
			LOGGER.error(MessageCatalog._00036_FILE_READING_FAILURE, exception, triplesFilename);
		}
		return done;
	}
	
	/**
	 * It creates a graph in the RDF store using SPARQL endpoint.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @param graphUri				the URI of the graph.
	 * @return true if the graph has been created. False otherwise.
	 * @since 1.0
	 */
	public boolean createGraphBySparql(final String sparqlEndpointURI, final String user, final String password, final String graphUri) {
		boolean done = false;
		final String createGraphSPARQL = "CREATE GRAPH <" + graphUri + ">;";
		//Execute SPARQL
		done = executeUpdateQuerySparqlEndpoint(sparqlEndpointURI, user, password, createGraphSPARQL);
		return done;
	}

	/**
	 * It removes a graph in the RDF store using SPARQL endpoint.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @param graphUri				the URI of the graph.
	 * @return true if the graph has been removed. False otherwise.
	 * @since 1.0
	 */
	public boolean removeGraphBySparql(final String sparqlEndpointURI, final String user, final String password, final String graphUri) {
		boolean done = false;
		final String removeGraphSPARQL = "DROP GRAPH <" + graphUri + ">;";
		//Execute SPARQL
		done = executeUpdateQuerySparqlEndpoint(sparqlEndpointURI, user, password, removeGraphSPARQL);
		return done;
	}

	/**
	 * It executes a SELECT SPARQL query on the SPARQL endpoint, to get the URIs of a type from 
	 *   ALIADA ontology.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @param typeLabel				the label value of the type to search for.
	 * @return a list of URIs with the matched types.
	 * @since 1.0
	 */
	public String[] getOntologyTypeURI(final String sparqlEndpointURI, final String user, final String password, final String typeLabel) {
		String query = "select distinct ?type FROM <http://aliada-project.eu/2014/aliada-ontology#> " + 
						"where {?type a <http://www.w3.org/2004/02/skos/core#Concept> . " +
						"?type <http://www.w3.org/2004/02/skos/core#prefLabel> ?label . " +
						"FILTER regex(str(?label), \"^" + typeLabel + "$\")}";
	  	ArrayList<String> typesList = new ArrayList<String>();
	 	try {
	        // Execute the query and obtain results
	        QueryExecution qexec = QueryExecutionFactory.sparqlService(
	        		sparqlEndpointURI, 
	        		QueryFactory.create(query), 
					auth(sparqlEndpointURI, user, password));
            ResultSet results = qexec.execSelect() ;
            while (results.hasNext())
            {
            	QuerySolution soln = results.nextSolution() ;
            	Resource resType = soln.getResource("type");
        		String type = resType.getURI();
        		typesList.add(type);
            }
	        qexec.close() ;
	      } catch (Exception exception) {
			LOGGER.error(MessageCatalog._00035_SPARQL_FAILED, exception, query);
		}
		if (typesList.isEmpty()) {
			return new String[0];
		}
		return (String[]) typesList.toArray(new String[typesList.size()]);
	}

	/**
	 * Builds a SPARQL INSERT with the given data.
	 * 
	 * @param graphName the graphName, null in case of default graph.
	 * @param triples the triples (in N3 format) that will be added.
	 * @return a new SPARQL INSERT query.
	 */
	String buildInsertQuery(final String graphName, final String triples) {
		final StringBuilder builder = new StringBuilder();
		builder.append("INSERT DATA ");
		if (isNotNullAndNotEmpty(graphName)) {
			builder.append("{ GRAPH ");
			if (graphName.startsWith("<") && graphName.endsWith(">")) {
				builder.append(graphName);
			} else 
			{
				builder.append("<").append(graphName).append(">");
			}
		}
		
		builder.append("{ ").append(triples).append("}");
		if (isNotNullAndNotEmpty(graphName)) {
			builder.append("}");
		}
		return builder.toString();
	}
	
	/**
	 * Returns the authenticator associated with a given endpoint.
	 * If no authentication can be found, then a new one is created 
	 * and cached for further reuse.
	 * 
	 * @param endpointAddress the endpoint address.
	 * @param username the username.
	 * @param password the password.
	 * @return the authenticator associated with a given endpoint.
	 */
	HttpAuthenticator auth(final String endpointAddress, final String username, final String password) {
		HttpAuthenticator auth = authCache.get(endpointAddress);
		if (auth == null) {
			auth = new SimpleAuthenticator(username, password.toCharArray());
			authCache.put(endpointAddress, auth);
		}
		return auth;
	}
}