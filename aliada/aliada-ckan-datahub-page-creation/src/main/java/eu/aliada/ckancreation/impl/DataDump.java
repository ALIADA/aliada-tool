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
import eu.aliada.ckancreation.model.Graph;
import eu.aliada.ckancreation.model.DumpFileInfo;
import eu.aliada.ckancreation.log.MessageCatalog;
import eu.aliada.ckancreation.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**
 * Data dump implementation. 
 * It stores the datasets triples of the RDF store in file format. 
 * 
 * @author Idoia Murua
 * @since 2.0
 */
public class DataDump {
	/** For logging. */
	private static final Log LOGGER  = new Log(DataDump.class);
	private final JobConfiguration jobConf;
	private final DBConnectionManager dbConn;
	/** Format for the ISQL command to execute */
	// isql host:port user pass exec="dump_one_graph_nt ('graphURI', 'filePathName', 'maxSize');" 
	private static final String ISQL_COMMAND_FORMAT = "%s %s:%d %s %s exec=\"dump_one_graph_nt('%s', '%s', %d);\"";
	/** Dump file maximum length **/
	private static final int FILE_LENGTH_LIMIT = 1000000000; 
	/** Dump file format **/
	private static final String FILE_FORMAT = "application/x-gzip"; 
	/** Dump file extension **/
	private static final String FILE_EXTENSION = ".gz"; 

	
	/**
	 * Constructor. 
	 * Initializes the class variables.
	 *
	 * @param jobConf		the {@link eu.aliada.ckancreation.model.JobConfiguration}
	 *						that contains information to create the dataset in CKAN Datahub.  
	 * @param db			The DDBB connection. 
	 * @since 2.0
	 */
	public DataDump(final JobConfiguration jobConf, final DBConnectionManager dbConn) {
		this.jobConf = jobConf;
		this.dbConn = dbConn;
	}

	/**
	 * FileNameFilter implementation.
	 * Used for listing the dump files generated.
	 *
	 * @since 2.0
	 */
	//FileNameFilter implementation
	public static class DumpsFileNameFilter implements FilenameFilter{
         
		private final String fileInitName;
		private final String fileExt;
         
		/**
		 * Constructor. 
		 * Initializes the class variables.
		 *
		 * @param fileInitName	the beginning string for the file names to filter.
		 * @since 2.0
		 */
		public DumpsFileNameFilter(final String fileInitName, final String fileExt){
			this.fileInitName = fileInitName;
			this.fileExt = fileExt;
		}
		/**
		 * Determines the files to list.
		 *
		 * @param dir	the folder name.
		 * @param name	the file name.
		 * @return true if the file name matches the filter. False otherwise.
		 * @since 2.0
		 */
		@Override
		public boolean accept(final File dir, final String name) {
			if(name.startsWith(fileInitName) && name.endsWith(fileExt)) {
				return true;
			} else {
				return false;
			}
        }
	}
    
	/**
	 * Obtains the dump file names generated for the graph.
	 *
	 * @param graph				the {@link eu.aliada.ckancreation.model.Graph}.
	 * @param dataDumpsPath		the physical path of the folder where the data dumps are kept.
	 * @param dataDumpsUrl		the URL of the folder where the data dumps are kept.
	 * @param dumpFileNameInit	the initial name for the files that contain the graph dumps.
	 * @return	
	 * @since 2.0
	 */
    public void updateGraphDumpsNames(final Graph graph, final String dataDumpsPath, final String dataDumpsUrl, final String dumpFileNameInit){
   		// List the dump files
   		final File dumpFolder = new File(dataDumpsPath);
   		final File[] listDumpFiles = dumpFolder.listFiles(new DumpsFileNameFilter(dumpFileNameInit, FILE_EXTENSION));
		if(listDumpFiles.length > 0) {
    		for(File dumpFile:listDumpFiles)
    		{
    	    	final String dumpFileUrl = dataDumpsUrl + "/" + dumpFile.getName();
    	    	DumpFileInfo dumpFileInfo = new DumpFileInfo();
    	    	try {
    	    		dumpFileInfo.setDumpFilePath(dumpFile.getCanonicalPath());
    	    	} catch (IOException exception) {
    				LOGGER.error(MessageCatalog._00034_FILE_ACCESS_FAILURE, exception, dumpFile.getName());
    	    	}
	    	    dumpFileInfo.setDumpFileUrl(dumpFileUrl);
	    		graph.addDumpFile(dumpFileInfo);
			}
		}
    	return;
	}
	
	/**
	 * Generates the dump of a graph in file format.
	 *
	 * @param graph			the graph for which a dump must be generated.
	 * @param dataDumpsPath	the physical path of the folder where to save the data dumps.
	 * @param dataDumpsUrl	the URL of the folder where to save the data dumps.
	 * @return	
	 * @since 2.0
	 */
    public void dumpGraph(final Graph graph, final String dataDumpsPath, final String dataDumpsUrl){
		//Compose initial dump file name from the graph URI + current day
    	final int stInd = graph.getUri().lastIndexOf('/');
    	String dumpFileNameInit = graph.getUri().substring(stInd + 1);
    	dumpFileNameInit = dumpFileNameInit.replace('.', '_');
    	final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    	final Date date = new Date();
    	dumpFileNameInit = dumpFileNameInit + dateFormat.format(date);
    	final String dumpFilePathInit = dataDumpsPath + File.separator + dumpFileNameInit;
		//Compose ISQL command execution statement
    	final String isqlCommand = String.format(ISQL_COMMAND_FORMAT,
				jobConf.getIsqlCommandPath(), jobConf.getStoreIp(),
				jobConf.getStoreSqlPort(), jobConf.getSqlLogin(),
				jobConf.getSqlPassword(), graph.getUri(),
				dumpFilePathInit,
				FILE_LENGTH_LIMIT);
		//Execute ISQL command
//		try {
			LOGGER.debug(MessageCatalog._00040_EXECUTING_ISQL);
/*			final Process commandProcess = Runtime.getRuntime().exec(isqlCommand);
			final BufferedReader stdInput = new BufferedReader(new InputStreamReader(commandProcess.getInputStream()));
			String comOutput = "";
			while ((comOutput = stdInput.readLine()) != null) {
				LOGGER.debug(comOutput);
			}*/
	    	graph.setDumpFileFormat(FILE_FORMAT);
			updateGraphDumpsNames(graph, dataDumpsPath, dataDumpsUrl, dumpFileNameInit);
/*		} catch (IOException exception) {
			LOGGER.error(MessageCatalog._00033_EXTERNAL_PROCESS_START_FAILURE, exception, isqlCommand);
		}*/
		return;
	}

	/**
	 * Generates the dumps in file format for the graphs of an organization.
	 *
	 * @param orgId			the organization id in the ALIADA DB.
	 * @param dataDumpsPath	the physical path of the folder where to save the data dumps.
	 * @param dataDumpsUrl	the URL of the folder where to save the data dumps.
	 * @return	an array of {@link eu.aliada.ckancreation.model.Graph}
	 * 			objects containing the URL of the files with the graphs dumps.
	 * @since 2.0
	 */
    public ArrayList<Graph> createDatasetDumps(final int orgId, final String dataDumpsPath, final String dataDumpsUrl){
    	final ArrayList<Graph> graphs = dbConn.getGraphs(orgId);
    	if(graphs !=null) {
			for (final Iterator<Graph> iter = graphs.iterator(); iter.hasNext();  ) {
				final Graph graph = iter.next();
				dumpGraph(graph, dataDumpsPath, dataDumpsUrl);
			}
    	}
		return graphs;
	}

}
