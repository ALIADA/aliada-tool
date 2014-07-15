// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-shared
// Responsible: ALIADA Consortium
package eu.aliada.shared.rdfstore;

import java.io.FileInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
 
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;

import eu.aliada.shared.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * RDF Store common operations. 
 *  
 * @author Idoia Murua
 * @since 1.0
 */
public class RDFStoreDAO {
	private final Log logger = new Log(RDFStoreDAO.class);

	/**
	 * It clears the graph in the RDF store.
	 *
	 * @param isqlCommandPath	the path of the ISQL command of the RDF store.  
	 * @param storeIp			the IP of the RDF store.  
	 * @param storeSqlPort		the port for SQL data access.  
	 * @param user				the login required for authentication in the RDF store.
	 * @param password			the password required for authentication in the RDF store.
	 * @param graphName			the name of the graph.
	 * @return true if the graph has been cleared. False otherwise.
	 * @since 1.0
	 */
	public boolean clearGraph(String isqlCommandPath, String storeIp, int storeSqlPort, String user, String password, String graphName) {
		boolean done = false;
		String clearGraphSPARQL = "EXEC=SPARQL CLEAR GRAPH <" + graphName + ">;";
		//Compose ISQL command calling statement
		String isqlCommandFormat = "%s %s:%d %s %s \"%s\"";
		String isqlCommand = String.format(isqlCommandFormat,
				isqlCommandPath, storeIp,
				storeSqlPort, user,
				password, clearGraphSPARQL);
		//Execute ISQL command
		try {
			Runtime.getRuntime().exec(isqlCommand);
		} catch (Exception exception) {
			logger.error(MessageCatalog._00033_EXTERNAL_PROCESS_START_FAILURE, exception, isqlCommand);
		}
		return done;
	}

	/**
	 * It loads triples in a graph in the RDF store.
	 *
	 * @param triplesFilename	the path of the file containing the triples to load.  
	 * @param rdfSinkFolder		the URI of the RDF SINK folder in Virtuoso.  
	 * @param user				the login required for authentication in the RDF store.
	 * @param password			the password required for authentication in the RDF store.
	 * @return true if the triples have been loaded. False otherwise.
	 * @since 1.0
	 */
	public boolean loadDataIntoGraph(String triplesFilename, String rdfSinkFolder, String user, String password) {
		boolean done = false;
		//Get the triples file name
		File triplesFile = new File(triplesFilename);
		String triplesFilenameNoPath = triplesFile.getName();
		//Append the file name to the rdfSinkFolder 
		if (!rdfSinkFolder.endsWith("/"))
			rdfSinkFolder = rdfSinkFolder + "/";
		rdfSinkFolder = rdfSinkFolder + triplesFilenameNoPath;
		//HTTP Authentication
		Base64 b = new Base64();
		byte[] authenticationArray = new String(user + ":" + password).getBytes();
		String encoding = b.encodeAsString(authenticationArray);
		
		try {
			URL putUrl = new URL(rdfSinkFolder);
			HttpURLConnection connection = (HttpURLConnection) putUrl.openConnection();
	
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("PUT");
			//Write Authentication
			connection.setRequestProperty  ("Authorization", "Basic " + encoding);			
			//Write File Data
			OutputStream os = connection.getOutputStream();		
			InputStream is = new FileInputStream(triplesFilename);
			byte buf[] = new byte[1024];
			int len;
			while ((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
			is.close();
			int responseCode=((HttpURLConnection)connection).getResponseCode();
			if ((responseCode>= 200) &&(responseCode<=202) ) {
				done = true;
			} else {
				done = false;
			}
			((HttpURLConnection)connection).disconnect();
			done = true;
		} catch (Exception exception) {
			done = false;
			logger.error(MessageCatalog._00034_HTTP_PUT_FAILED, exception, rdfSinkFolder);
	    }
		return done;
	}
}

