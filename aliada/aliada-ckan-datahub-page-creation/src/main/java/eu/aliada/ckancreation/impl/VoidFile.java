// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.impl;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import java.util.Date;
	



import eu.aliada.ckancreation.model.JobConfiguration;
import eu.aliada.ckancreation.model.DumpFileInfo;
import eu.aliada.ckancreation.log.MessageCatalog;
import eu.aliada.ckancreation.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**
 * Void File generation implementation. 
 * 
 * @author Idoia Murua
 * @since 2.0
 */
public class VoidFile {
	private final JobConfiguration jobConf;
	private final DBConnectionManager dbConn;
	/** Void file created **/
	private File voidFileCreated;
	/** For logging. */
	private static final Log LOGGER  = new Log(DataDump.class);

	
	/**
	 * Constructor. 
	 * Initializes the class variables.
	 *
	 * @param jobConf		the {@link eu.aliada.ckancreation.model.JobConfiguration}
	 *						that contains information to create the Void file.  
	 * @param db			The DDBB connection. 
	 * @since 2.0
	 */
	public VoidFile(final JobConfiguration jobConf, final DBConnectionManager dbConn) {
		this.jobConf = jobConf;
		this.dbConn = dbConn;
	}

	/**
	 * Returns the void file.
	 * 
	 * @return The void file.
	 * @since 2.0
	 */
	public File getVoidFileCreated() {
		return voidFileCreated;
	}
	/**
	 * Sets the void file.
	 * 
	 * @param voidFileCreated The void file.
	 * @since 2.0
	 */
	public void setVoidFileCreated(final File voidFileCreated) {
		this.voidFileCreated = voidFileCreated;
	}
   
	/**
	 * Creates the void file of a dataset.
	 *
	 * @param graphURI		the URI of the graph.
	 * @param dataDumpsPath	the physical path of the folder where to save the data dumps.
	 * @param dataDumpsUrl	the URL of the folder where to save the data dumps.
	 * @return	an array of {@link eu.aliada.ckancreation.model.Graph}
	 * 			objects containing the URL of the files with the graphs dumps.
	 * @since 2.0
	 */
    public File createVoidFile(final String graphURI, final String dataDumpsPath, 
    		final String dataDumpsUrl){
		return voidFileCreated;
	}

}
