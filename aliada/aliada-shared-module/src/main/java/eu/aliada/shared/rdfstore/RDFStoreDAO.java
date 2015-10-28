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
import com.hp.hpl.jena.query.ARQ;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;
import com.hp.hpl.jena.sparql.engine.http.Service;
import com.hp.hpl.jena.sparql.util.Context;

import eu.aliada.shared.log.Log;
import eu.aliada.shared.log.MessageCatalog;
import eu.aliada.shared.rdfstore.AmbiguousLink;
import eu.aliada.shared.rdfstore.RetrievedResource;

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
	/** Maximum number of triples to in every SPARQL INSERT statement. */
	private static final int MAX_NUMBER_TRIPLES = 100;
	
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
			connection.setReadTimeout(2000);
			connection.setConnectTimeout(5000);
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
					context(),
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
	 * @since 1.0
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
				context(),
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
			final FileInputStream fTriples = new FileInputStream(triplesFilename);
			final BufferedReader inTriples = new BufferedReader(new InputStreamReader(fTriples));
			String line;
			String triples = "";
			int numTriples = 0;
			while (((line = inTriples.readLine()) != null) && done){
			    // Deal with the line
				triples = triples + line;
				numTriples++;
				if(numTriples >= MAX_NUMBER_TRIPLES) {
					try {
						//LOGGER.debug(MessageCatalog._00049_DEBUG_TRIPLES, triples);
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
					//LOGGER.debug(MessageCatalog._00049_DEBUG_TRIPLES, triples);
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
	 * It executes a SELECT SPARQL query on the SPARQL endpoint, to get the 
	 * URIs of a type from ALIADA ontology.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @param typeLabel				the label value of the type to search for.
	 * @return a list of URIs with the matched types.
	 * @since 1.0
	 */
	public String[] getOntologyTypeURI(final String sparqlEndpointURI, final String user, final String password, final String typeLabel) {
		final String query = "select distinct ?type FROM <http://aliada-project.eu/2014/aliada-ontology#> " + 
						"where {?type a <http://www.w3.org/2004/02/skos/core#Concept> . " +
						"?type <http://www.w3.org/2004/02/skos/core#prefLabel> ?label . " +
						"FILTER regex(str(?label), \"^" + typeLabel + "$\")}";
		final ArrayList<String> typesList = new ArrayList<String>();
		QueryExecution qexec = null;
		try {
	        // Execute the query and obtain results
	  		qexec = QueryExecutionFactory.sparqlService(
	  				sparqlEndpointURI, 
	        		QueryFactory.create(query), 
					auth(sparqlEndpointURI, user, password));
            
	        if (qexec instanceof QueryEngineHTTP) {
	        	((QueryEngineHTTP)qexec).setTimeout(2000L, 5000L);
	        }
	        
	        final ResultSet results = qexec.execSelect() ;
            while (results.hasNext()) {
            	final QuerySolution soln = results.nextSolution() ;
            	final Resource resType = soln.getResource("type");
        		final String type = resType.getURI();
        		typesList.add(type);
            }
	  	} catch (Exception exception) {
	  		LOGGER.error(MessageCatalog._00035_SPARQL_FAILED, exception, query);
	  	} finally {
	  		try {
				qexec.close();
			} catch (Exception e) {
				// Ignore
			}
	  	}
	 	
		if (typesList.isEmpty()) {
			return new String[0];
		}
		return (String[]) typesList.toArray(new String[typesList.size()]);
	}

	/**
	 * It executes a SELECT SPARQL query on the SPARQL endpoint, 
	 * to get the number of triples of a graph.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param graphName 			the graphName, null in case of default graph.
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @return the number of triples in the graph.
	 * @since 1.0
	 */
	public int getNumTriples(final String sparqlEndpointURI, final String graphName, final String user, final String password) {
		final StringBuilder builder = new StringBuilder();
		builder.append("SELECT (COUNT(*) AS ?count) ");
		if (isNotNullAndNotEmpty(graphName)) {
			builder.append("FROM ");
			if (graphName.startsWith("<") && graphName.endsWith(">")) {
				builder.append(graphName);
			} else 
			{
				builder.append("<").append(graphName).append(">");
			}
		}
		
		builder.append(" WHERE { ?s ?p ?o }");
		final String query = builder.toString();
		return getNumResources(query, sparqlEndpointURI, user, password);
	}

	/**
	 * It executes a SELECT SPARQL query on the SPARQL endpoint, 
	 * to get the discovered links.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param graphName 			the graphName, null in case of default graph.
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @param offset				causes the solutions generated to start after 
	 *                              the specified number of solutions.
	 * @param limit					upper bound on the number of solutions returned.
	 * @return a list of triples with the discovered links.
	 * @since 2.0
	 */
	public Triple[] getDiscoveredLinks(final String sparqlEndpointURI, final String graphName, final String user, final String password, final int offset, final int limit) {
		final String query = "select * FROM <" + graphName + "> " + 
						"where {?source ?rel ?target }" +
						" ORDER BY ?source ?target" +
						" OFFSET " + offset + " LIMIT " + limit;
	  	ArrayList<Triple> linksList = new ArrayList<Triple>();
	 	try {
	        // Execute the query and obtain results
	        final QueryExecution qexec = QueryExecutionFactory.sparqlService(
	        		sparqlEndpointURI, 
	        		QueryFactory.create(query), 
					auth(sparqlEndpointURI, user, password));
	        qexec.setTimeout(2000, 5000);
            final ResultSet results = qexec.execSelect() ;
            while (results.hasNext())
            {
            	final QuerySolution soln = results.nextSolution() ;
            	final Resource sourceResType = soln.getResource("source");
            	final Resource targetResType = soln.getResource("target");
            	final Resource relResType = soln.getResource("rel");
        		final Triple triple = new Triple(sourceResType.asNode(), relResType.asNode(), targetResType.asNode());
        		linksList.add(triple);
            }
	        qexec.close() ;
	      } catch (Exception exception) {
			LOGGER.error(MessageCatalog._00035_SPARQL_FAILED, exception, query);
		}
		if (linksList.isEmpty()) {
			return new Triple[0];
		}
		return (Triple[]) linksList.toArray(new Triple[linksList.size()]);
	}

	/**
	 * It executes a SELECT SPARQL query on the SPARQL endpoint, 
	 * to get the number of discovered links.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param graphName 			the graphName, null in case of default graph.
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @return the number of the discovered links.
	 * @since 2.0
	 */
	public int getNumDiscoveredLinks(final String sparqlEndpointURI, final String graphName, final String user, final String password) {
		final String query = "select (COUNT(*) AS ?count) FROM <" + graphName + "> " + 
						"where {?source ?rel ?target }";
		return getNumResources(query, sparqlEndpointURI, user, password);
	}

	/**
	 * It executes a SELECT SPARQL query on the SPARQL endpoint,
	 * to get the ambiguous discovered links.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param graphName 			the graphName, null in case of default graph.
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @param offset				causes the solutions generated to start after 
	 *                              the specified number of solutions.
	 * @param limit					upper bound on the number of solutions returned.
	 * @return a list of {@link eu.aliada.shared.rdfstore.AmbiguousLink} with the ambiguous discovered links.
	 * @since 2.0
	 */
	public AmbiguousLink[] getAmbiguousDiscoveredLinks(final String sparqlEndpointURI, final String graphName, final String user, final String password, final int offset, final int limit) {
		final String query = "SELECT ?localRes ?extResBegin (COUNT(?localRes) AS ?count) FROM <" + graphName + "> " + 
				" WHERE {?localRes ?rel ?extRes ." +
				" BIND( str(?extRes) as ?extResStr )." +
				" BIND( SUBSTR(?extResStr, 1,14) AS ?extResBegin)" +
				" }" +
				" GROUP BY ?localRes ?extResBegin" +
				" HAVING (COUNT(?localRes) > 1)" +
				" ORDER BY ?localRes" +
				" OFFSET " + offset + " LIMIT " + limit;
		
		ArrayList<AmbiguousLink> ambiguousLinksList = new ArrayList<AmbiguousLink>();
		try {
			// Execute the query and obtain results
			final QueryExecution qexec = QueryExecutionFactory.sparqlService(
					sparqlEndpointURI, 
					QueryFactory.create(query), 
					auth(sparqlEndpointURI, user, password));
			qexec.setTimeout(2000, 5000);
			final ResultSet results = qexec.execSelect() ;
			while (results.hasNext())
			{
				final QuerySolution soln = results.nextSolution() ;
		    	final Resource localRes = soln.getResource("localRes");
		    	final String extResBegin = soln.getLiteral("extResBegin").getString();
		    	final AmbiguousLink ambiguousLink = new AmbiguousLink();
		    	ambiguousLink.setSourceURI(localRes.getURI());
		    	getSourceURIAmbiguousLinks(ambiguousLink, localRes, extResBegin, sparqlEndpointURI, graphName, user, password);
		    	ambiguousLinksList.add(ambiguousLink);
		    }
		    qexec.close() ;
		  } catch (Exception exception) {
			LOGGER.error(MessageCatalog._00035_SPARQL_FAILED, exception, query);
		}
		if (ambiguousLinksList.isEmpty()) {
			return new AmbiguousLink[0];
		}
		return (AmbiguousLink[]) ambiguousLinksList.toArray(new AmbiguousLink[ambiguousLinksList.size()]);
	}

	/**
	 * It executes a SELECT SPARQL query on the SPARQL endpoint, 
	 * to get the number of ambiguous discovered links.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param graphName 			the graphName, null in case of default graph.
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @return the number of the ambiguous discovered links.
	 * @since 2.0
	 */
	public int getNumAmbiguousDiscoveredLinks (final String sparqlEndpointURI, final String graphName, final String user, final String password) {
		final String query = "SELECT (COUNT(?localRes) AS ?count) FROM <" + graphName + "> " + 
						" WHERE {?localRes ?rel ?extRes ." +
						" BIND( str(?extRes) as ?extResStr )." +
						" BIND( SUBSTR(?extResStr, 1,14) AS ?extResBegin)" +
						" }" +
						" GROUP BY ?localRes ?extResBegin" +
						" HAVING (COUNT(?localRes) > 1)";

		int numLinks = 0;
		try {
	        // Execute the query and obtain results
	        final QueryExecution qexec = QueryExecutionFactory.sparqlService(
	        		sparqlEndpointURI, 
	        		QueryFactory.create(query), 
					auth(sparqlEndpointURI, user, password));
	        qexec.setTimeout(2000, 5000);
            final ResultSet results = qexec.execSelect() ;
            while (results.hasNext())
            {
            	final QuerySolution soln = results.nextSolution() ;
            	numLinks = numLinks + soln.getLiteral("count").getInt();
            }
	        qexec.close() ;
	      } catch (Exception exception) {
			LOGGER.error(MessageCatalog._00035_SPARQL_FAILED, exception, query);
		}
		return numLinks;
	}

	/**
	 * It executes a SELECT SPARQL query on the SPARQL endpoint, 
	 * to get the ambiguous discovered links of a source URI.
	 *
	 * @param ambiguousLink			a {@link eu.aliada.shared.rdfstore.AmbiguousLink} that contains the source URI.  
	 * @param localRes				the source resource of the link.  
	 * @param extResBegin			the beginning string of the target link.  
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param graphName 			the graphName, null in case of default graph.
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @param offset				causes the solutions generated to start after 
	 *                              the specified number of solutions.
	 * @param limit					upper bound on the number of solutions returned.
	 * @since 2.0
	 */
	public void getSourceURIAmbiguousLinks(final AmbiguousLink ambiguousLink, final Resource localRes, final String extResBegin, final String sparqlEndpointURI, final String graphName, final String user, final String password) {
		final String query = "SELECT ?rel ?extRes FROM <" + graphName + "> " + 
				" WHERE {<" + ambiguousLink.getSourceURI() + "> ?rel ?extRes ." +
				" FILTER regex(?extRes, \"^" + extResBegin + "\", \"i\")" +
				" }";
		try {
			// Execute the query and obtain results
			final QueryExecution qexec = QueryExecutionFactory.sparqlService(
					sparqlEndpointURI, 
					QueryFactory.create(query), 
					auth(sparqlEndpointURI, user, password));
			qexec.setTimeout(2000, 5000);
			final ResultSet results = qexec.execSelect() ;
			while (results.hasNext())
			{
				final QuerySolution soln = results.nextSolution() ;
		    	final Resource extRes = soln.getResource("extRes");
            	final Resource relResType = soln.getResource("rel");
            	final Triple triple = new Triple(localRes.asNode(), relResType.asNode(), extRes.asNode());
		    	ambiguousLink.addLink(triple);
		    }
		    qexec.close() ;
		  } catch (Exception exception) {
			LOGGER.error(MessageCatalog._00035_SPARQL_FAILED, exception, query);
		}
	}

	/**
	 * It executes a SELECT SPARQL query on the SPARQL endpoint, 
	 * to get the resources specified in the query argument.
	 *
	 * @param query					the query to execute to get the resources.  
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @param offset				causes the solutions generated to start after 
	 *                              the specified number of solutions.
	 * @param limit					upper bound on the number of solutions returned.
	 * @return a list of {@link eu.aliada.shared.rdfstore.RetrievedResource} with the resources.
	 * @since 2.0
	 */
	public RetrievedResource[] getResources(final String query, final String sparqlEndpointURI, final String user, final String password, final int offset, final int limit) {
		final ArrayList<RetrievedResource> resList = new ArrayList<RetrievedResource>();
	 	try {
	        // Execute the query and obtain results
	        final QueryExecution qexec = QueryExecutionFactory.sparqlService(
	        		sparqlEndpointURI, 
	        		QueryFactory.create(query), 
					auth(sparqlEndpointURI, user, password));
	        qexec.setTimeout(2000, 5000);
            final ResultSet results = qexec.execSelect() ;
            while (results.hasNext())
            {
            	final QuerySolution soln = results.nextSolution() ;
            	final Resource res = soln.getResource("res");
        		String name = "";
            	if(soln.contains("name")) {
            		name = soln.getLiteral("name").getString();
            	}
        		final RetrievedResource retrievedRes = new RetrievedResource(res.getURI(), name);
        		resList.add(retrievedRes);
            }
	        qexec.close() ;
	      } catch (Exception exception) {
			LOGGER.error(MessageCatalog._00035_SPARQL_FAILED, exception, query);
		}
		if (resList.isEmpty()) {
			return new RetrievedResource[0];
		}
		return (RetrievedResource[]) resList.toArray(new RetrievedResource[resList.size()]);
	}

	/**
	 * It executes a SELECT SPARQL query on the SPARQL endpoint, 
	 * to get the number of resources specified in the query argument.
	 *
	 * @param query					the query to execute to get the number of resources.  
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @return the number of resources.
	 * @since 2.0
	 */
	public int getNumResources(final String query, final String sparqlEndpointURI, final String user, final String password) {
		int numRes = 0;
		try {
	        // Execute the query and obtain results
	        final QueryExecution qexec = QueryExecutionFactory.sparqlService(
	        		sparqlEndpointURI, 
	        		QueryFactory.create(query), 
					auth(sparqlEndpointURI, user, password));
	        qexec.setTimeout(2000, 5000);
            final ResultSet results = qexec.execSelect() ;
            while (results.hasNext())
            {
            	final QuerySolution soln = results.nextSolution() ;
            	numRes = soln.getLiteral("count").getInt();
            }
	        qexec.close() ;
	      } catch (Exception exception) {
			LOGGER.error(MessageCatalog._00035_SPARQL_FAILED, exception, query);
		}
		return numRes;
	}
	
	/**
	 * It executes a SELECT SPARQL query on the SPARQL endpoint, to get the authors.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param graphName 			the graphName, null in case of default graph.
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @param offset				causes the solutions generated to start after 
	 *                              the specified number of solutions.
	 * @param limit					upper bound on the number of solutions returned.
	 * @return a list of {@link eu.aliada.shared.rdfstore.RetrievedResource} with the authors.
	 * @since 2.0
	 */
	public RetrievedResource[] getAuthors(final String sparqlEndpointURI, final String graphName, final String user, final String password, final int offset, final int limit) {
		final String query = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
		" PREFIX ecrm:   <http://erlangen-crm.org/current/>" +
		" PREFIX efrbroo: <http://erlangen-crm.org/efrbroo/>" +
		" SELECT DISTINCT ?res ?name FROM <" + graphName + "> " +
		" WHERE { {{?res rdf:type ecrm:E39_Actor}" +
		" UNION {?res rdf:type ecrm:E21_Person}" +
		" UNION {?res rdf:type efrbroo:F10_Person}}" +
		" . ?res ecrm:P131_is_identified_by ?apel" +
		" . ?apel ecrm:P3_has_note ?name }" +
		" ORDER BY ?res ?name" +
		" OFFSET " + offset + " LIMIT " + limit;

		return getResources(query, sparqlEndpointURI, user, password, offset, limit);
	}

	/**
	 * It executes a SELECT SPARQL query on the SPARQL endpoint, 
	 * to get the number of authors.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param graphName 			the graphName, null in case of default graph.
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @return the number of authors.
	 * @since 2.0
	 */
	public int getNumAuthors(final String sparqlEndpointURI, final String graphName, final String user, final String password) {
		final String query = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
		" PREFIX ecrm:   <http://erlangen-crm.org/current/>" +
		" PREFIX efrbroo: <http://erlangen-crm.org/efrbroo/>" +
		" SELECT (COUNT(*) AS ?count) FROM <" + graphName + "> {" +
		" SELECT DISTINCT ?res ?name " +
		" WHERE { {{?res rdf:type ecrm:E39_Actor}" +
		" UNION {?res rdf:type ecrm:E21_Person}" +
		" UNION {?res rdf:type efrbroo:F10_Person}}" +
		" . ?res ecrm:P131_is_identified_by ?apel" +
		" . ?apel ecrm:P3_has_note ?name }" +
		"}";

		return getNumResources(query, sparqlEndpointURI, user, password);
	}

	/**
	 * It executes a SELECT SPARQL query on the SPARQL endpoint, to get the objects.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param graphName 			the graphName, null in case of default graph.
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @param offset				causes the solutions generated to start after 
	 *                              the specified number of solutions.
	 * @param limit					upper bound on the number of solutions returned.
	 * @return a list of {@link eu.aliada.shared.rdfstore.RetrievedResource} with the objects.
	 * @since 2.0
	 */
	public RetrievedResource[] getObjects(final String sparqlEndpointURI, final String graphName, final String user, final String password, final int offset, final int limit) {
		final String query = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
		" PREFIX ecrm:   <http://erlangen-crm.org/current/>" +
		" PREFIX efrbroo: <http://erlangen-crm.org/efrbroo/>" +
		" SELECT DISTINCT ?res ?name FROM <" + graphName + "> " +
		" WHERE {" +
		" {?res rdf:type ecrm:E18_Physical_Thing ." +
		" OPTIONAL {?res ecrm:P1_is_identified_by ?apel. ?apel ecrm:P3_has_note ?name}}" +
		" UNION" +
		" {?res rdf:type ecrm:E73_Information_Object ." +
		" OPTIONAL {?res ecrm:P102_has_title ?apel. ?apel ecrm:P3_has_note ?name} }}" +
		" ORDER BY ?res ?name" +
		" OFFSET " + offset + " LIMIT " + limit;

		return getResources(query, sparqlEndpointURI, user, password, offset, limit);
	}

	/**
	 * It executes a SELECT SPARQL query on the SPARQL endpoint, 
	 * to get the number of objects.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param graphName 			the graphName, null in case of default graph.
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @return the number of objects.
	 * @since 2.0
	 */
	public int getNumObjects(final String sparqlEndpointURI, final String graphName, final String user, final String password) {
		final String query = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
		" PREFIX ecrm:   <http://erlangen-crm.org/current/>" +
		" PREFIX efrbroo: <http://erlangen-crm.org/efrbroo/>" +
		" SELECT (COUNT(*) AS ?count) FROM <" + graphName + "> {" +
		" SELECT DISTINCT ?res ?name " +
		" WHERE {" +
		" {?res rdf:type ecrm:E18_Physical_Thing ." +
		" OPTIONAL {?res ecrm:P1_is_identified_by ?apel. ?apel ecrm:P3_has_note ?name}}" +
		" UNION" +
		" {?res rdf:type ecrm:E73_Information_Object ." +
		" OPTIONAL {?res ecrm:P102_has_title ?apel. ?apel ecrm:P3_has_note ?name} }}" +
		"}";

		return getNumResources(query, sparqlEndpointURI, user, password);
	}

	/**
	 * It executes a SELECT SPARQL query on the SPARQL endpoint, to get the manifestations.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param graphName 			the graphName, null in case of default graph.
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @param offset				causes the solutions generated to start after 
	 *                              the specified number of solutions.
	 * @param limit					upper bound on the number of solutions returned.
	 * @return a list of {@link eu.aliada.shared.rdfstore.RetrievedResource} with the manifestations.
	 * @since 2.0
	 */
	public RetrievedResource[] getManifestations(final String sparqlEndpointURI, final String graphName, final String user, final String password, final int offset, final int limit) {
		final String query = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
		" PREFIX ecrm:   <http://erlangen-crm.org/current/>" +
		" PREFIX efrbroo: <http://erlangen-crm.org/efrbroo/>" +
		" SELECT DISTINCT ?res ?name FROM <" + graphName + "> " +
		" WHERE {" +
		" ?res rdf:type efrbroo:F3_Manifestation_Product_Type ." +
		" ?res ecrm:P102_has_title ?apel. ?apel ecrm:P3_has_note ?name }" +
		" ORDER BY ?res ?name" +
		" OFFSET " + offset + " LIMIT " + limit;
		
		return getResources(query, sparqlEndpointURI, user, password, offset, limit);
	}

	/**
	 * It executes a SELECT SPARQL query on the SPARQL endpoint, 
	 * to get the number of manifestations.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param graphName 			the graphName, null in case of default graph.
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @return the number of manifestations.
	 * @since 2.0
	 */
	public int getNumManifestations(final String sparqlEndpointURI, final String graphName, final String user, final String password) {
		final String query = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
		" PREFIX ecrm:   <http://erlangen-crm.org/current/>" +
		" PREFIX efrbroo: <http://erlangen-crm.org/efrbroo/>" +
		" SELECT (COUNT(*) AS ?count) FROM <" + graphName + "> {" +
		" SELECT DISTINCT ?res ?name " +
		" WHERE {" +
		" ?res rdf:type efrbroo:F3_Manifestation_Product_Type ." +
		" ?res ecrm:P102_has_title ?apel. ?apel ecrm:P3_has_note ?name }" +
		"}";

		return getNumResources(query, sparqlEndpointURI, user, password);
	}

	/**
	 * It executes a SELECT SPARQL query on the SPARQL endpoint, 
	 * to get the resources specified in the query argument.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param graphName 			the graphName, null in case of default graph.
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @param offset				causes the solutions generated to start after 
	 *                              the specified number of solutions.
	 * @param limit					upper bound on the number of solutions returned.
	 * @return a list of {@link eu.aliada.shared.rdfstore.RetrievedResource} with the resources.
	 * @since 2.0
	 */
	public RetrievedWork[] getWorks(final String sparqlEndpointURI, final String graphName, final String user, final String password, final int offset, final int limit) {
		final String query = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
		" PREFIX ecrm:   <http://erlangen-crm.org/current/>" +
		" PREFIX efrbroo: <http://erlangen-crm.org/efrbroo/>" +
		" SELECT DISTINCT ?work ?expr ?manif ?title FROM <" + graphName + "> " +
		" WHERE { ?work rdf:type efrbroo:F1_Work . "+
		" ?work efrbroo:R40_has_representative_expression ?expr ." +
		" ?expr efrbroo:R4_carriers_provided_by ?manif ." +
		" ?manif ecrm:P102_has_title ?apel ." +
		" ?apel ecrm:P3_has_note ?title ." +
		" }" +
		" ORDER BY ?work ?expr ?manif" +
		" OFFSET " + offset + " LIMIT " + limit;

		ArrayList<RetrievedWork> resList = new ArrayList<RetrievedWork>();
	 	try {
	        // Execute the query and obtain results
	        final QueryExecution qexec = QueryExecutionFactory.sparqlService(
	        		sparqlEndpointURI, 
	        		QueryFactory.create(query), 
					auth(sparqlEndpointURI, user, password));
	        qexec.setTimeout(2000, 5000);
            final ResultSet results = qexec.execSelect() ;
            while (results.hasNext())
            {
            	final QuerySolution soln = results.nextSolution() ;
		    	final RetrievedWork retrievedRes = new RetrievedWork();
		    	retrievedRes.setWorkURI(soln.getResource("work").getURI());
		    	retrievedRes.setExprURI(soln.getResource("expr").getURI());
		    	retrievedRes.setManifURI(soln.getResource("manif").getURI());
		    	retrievedRes.setTitle(soln.getLiteral("title").getString());
        		
		    	String dimensions = "";
            	if(soln.contains("dimensions")) {
            		dimensions = soln.getLiteral("dimensions").getString();
            	}
		    	retrievedRes.setDimensions(dimensions);
        		
		    	String extension = "";
            	if(soln.contains("extension")) {
            		extension = soln.getLiteral("extension").getString();
            	}
		    	retrievedRes.setExtension(extension);
        		
		    	String author = "";
            	if(soln.contains("author")) {
            		author = soln.getLiteral("author").getString();
            	}
		    	retrievedRes.setAuthor(author);

		    	String publicPlace = "";
            	if(soln.contains("place_publication")) {
            		publicPlace = soln.getLiteral("place_publication").getString();
            	}
		    	retrievedRes.setPublicPlace(publicPlace);

		    	String publicDate = "";
            	if(soln.contains("date_publication")) {
            		publicDate = soln.getLiteral("date_publication").getString();
            	}
		    	retrievedRes.setPublicDate(publicDate);

		    	String edition = "";
            	if(soln.contains("edition")) {
            		edition = soln.getLiteral("edition").getString();
            	}
		    	retrievedRes.setEdition(edition);
        		
		    	resList.add(retrievedRes);
            }
	        qexec.close() ;
	      } catch (Exception exception) {
			LOGGER.error(MessageCatalog._00035_SPARQL_FAILED, exception, query);
		}
		if (resList.isEmpty()) {
			return new RetrievedWork[0];
		}
		return (RetrievedWork[]) resList.toArray(new RetrievedWork[resList.size()]);
	}

	/**
	 * It executes a SELECT SPARQL query on the SPARQL endpoint, 
	 * to get the number of resources specified in the query argument.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param graphName 			the graphName, null in case of default graph.
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @return the number of resources.
	 * @since 2.0
	 */
	public int getNumWorks(final String sparqlEndpointURI, final String graphName, final String user, final String password) {
		final String query = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
		" PREFIX ecrm:   <http://erlangen-crm.org/current/>" +
		" PREFIX efrbroo: <http://erlangen-crm.org/efrbroo/>" +
		" SELECT (COUNT(*) AS ?count) FROM <" + graphName + "> {" +
		" SELECT DISTINCT ?work ?expr ?manif ?title " +
		" WHERE { ?work rdf:type efrbroo:F1_Work . "+
		" ?work efrbroo:R40_has_representative_expression ?expr ." +
		" ?expr efrbroo:R4_carriers_provided_by ?manif ." +
		" ?manif ecrm:P102_has_title ?apel ." +
		" ?apel ecrm:P3_has_note ?title ." +
		" }" +
		"}";

		return getNumResources(query, sparqlEndpointURI, user, password);
	}
	
	
	/**
	 * It executes an DELETE SPARQL query on the SPARQL endpoint.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param graphName the graphName, null in case of default graph.
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @param triples				the triples that will be removed.
	 * throws Exception in case the DELETE goes wrong.
	 * @since 2.0
	 */
	public void executeDelete(
			final String sparqlEndpointURI, 
			final String graphName, 
			final String user, 
			final String password, 
			final String triples) throws Exception {
		UpdateExecutionFactory.createRemoteForm(
				UpdateFactory.create(buildDeleteQuery(graphName, triples)), 
				sparqlEndpointURI, 
				context(),
				auth(sparqlEndpointURI, user, password))
			.execute();
	}	

	/**
	 * It executes a SELECT SPARQL query on the SPARQL endpoint.
	 *
	 * @param sparqlEndpointURI		the SPARQL endpoint URI.  
	 * @param user					the user name for the SPARQl endpoint.
	 * @param password				the password for the SPARQl endpoint.
	 * @param query					the query to use to look for the resources.
	 * @return the {@link com.hp.hpl.jena.query.ResultSet} of the SELECT SPARQL query.
	 * @since 2.0
	 */
	public ResultSet executeSelect(final String sparqlEndpointURI, final String user, final String password, final String query) {
		ResultSet results = null;
	 	try {
	        // Execute the query and obtain results
	        final QueryExecution qexec = QueryExecutionFactory.sparqlService(
	        		sparqlEndpointURI, 
	        		QueryFactory.create(query), 
					auth(sparqlEndpointURI, user, password));
	        qexec.setTimeout(2000, 5000);
            results = qexec.execSelect() ;
	        qexec.close() ;
	      } catch (Exception exception) {
			LOGGER.error(MessageCatalog._00035_SPARQL_FAILED, exception, query);
		}
	 	return results;
	}

	/**
	 * Builds a SPARQL INSERT with the given data.
	 * 
	 * @param graphName the graphName, null in case of default graph.
	 * @param triples the triples (in N3 format) that will be added.
	 * @return a new SPARQL INSERT query.
	 * @since 1.0
	 */
	String buildInsertQuery(final String graphName, final String triples) {
		final StringBuilder builder = new StringBuilder();
		builder.append("INSERT DATA ");
		if (isNotNullAndNotEmpty(graphName)) {
			builder.append("{ GRAPH ");
			if (graphName.startsWith("<") && graphName.endsWith("> ")) {
				builder.append(graphName);
			} else 
			{
				builder.append("<").append(graphName).append("> ");
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
	 * @since 1.0
	 */
	HttpAuthenticator auth(final String endpointAddress, final String username, final String password) {
		HttpAuthenticator auth = authCache.get(endpointAddress);
		if (auth == null) {
			auth = new SimpleAuthenticator(username, password.toCharArray());
			authCache.put(endpointAddress, auth);
		}
		return auth;
	}

	/**
	 * Builds a SPARQL DELETE with the given data.
	 * 
	 * @param graphName the graphName, null in case of default graph.
	 * @param triples the triples (in N3 format) that will be removed.
	 * @return a new SPARQL INSERT query.
	 * @since 2.0
	 */
	String buildDeleteQuery(final String graphName, final String triples) {
		final StringBuilder builder = new StringBuilder();
		builder.append("DELETE DATA ");
		if (isNotNullAndNotEmpty(graphName)) {
			builder.append("{ GRAPH ");
			if (graphName.startsWith("<") && graphName.endsWith("> ")) {
				builder.append(graphName);
			} else 
			{
				builder.append("<").append(graphName).append("> ");
			}
		}
		
		builder.append("{ ").append(triples).append("}");
		if (isNotNullAndNotEmpty(graphName)) {
			builder.append("}");
		}
		return builder.toString();
	}
	
	Context context() {
		final Context ctx = new Context(ARQ.getContext());
		ctx.put(Service.queryTimeout, 2000);
		return ctx; 
	}
}