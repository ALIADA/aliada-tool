// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.impl;

import java.io.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
	


import eu.aliada.ckancreation.model.JobConfiguration;
import eu.aliada.ckancreation.model.DumpFileInfo;
import eu.aliada.ckancreation.log.MessageCatalog;
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
	public DataDump(final JobConfiguration jobConf) {
		this.jobConf = jobConf;
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
	 * @param graphURI			the graph URI.
	 * @param dataDumpsPath		the physical path of the folder where the data dumps are kept.
	 * @param dataDumpsUrl		the URL of the folder where the data dumps are kept.
	 * @param dumpFileNameInit	the initial name for the files that contain the graph dumps.
	 * @return	an array of  {@link eu.aliada.ckancreation.model.DumpFileInfo}
	 * 			objects with the info about the files containing the graph dump.
	 * @since 2.0
	 */
    public ArrayList<DumpFileInfo> getGraphDumpsNames(final String graphURI, final String dataDumpsPath, 
    		final String dataDumpsUrl, final String dumpFileNameInit){
    	/** Dump files of the graph. */
    	final ArrayList<DumpFileInfo> dumpFilesInfo = new ArrayList<DumpFileInfo>();
   		// List the dump files
   		final File dumpFolder = new File(dataDumpsPath);
   		final File[] listDumpFiles = dumpFolder.listFiles(new DumpsFileNameFilter(dumpFileNameInit, FILE_EXTENSION));
		if( (listDumpFiles != null) && (listDumpFiles.length > 0)) {
    		for(File dumpFile:listDumpFiles)
    		{
    	    	final String dumpFileUrl = dataDumpsUrl + "/" + dumpFile.getName();
    	    	DumpFileInfo dumpFileInfo = new DumpFileInfo();
    	    	dumpFileInfo.setDumpFileFormat(FILE_FORMAT);
    	    	try {
    	    		dumpFileInfo.setDumpFilePath(dumpFile.getCanonicalPath());
    	    	} catch (IOException exception) {
    				LOGGER.error(MessageCatalog._00035_FILE_ACCESS_FAILURE, exception, dumpFile.getName());
    	    	}
	    	    dumpFileInfo.setDumpFileUrl(dumpFileUrl);
	    	    dumpFilesInfo.add(dumpFileInfo);
			}
		}
    	return dumpFilesInfo;
	}
	
	/**
	 * Generates the dump in file format for a graph.
	 *
	 * @param graphURI		the URI of the graph.
	 * @param dataDumpsPath	the physical path of the folder where to save the data dumps.
	 * @param dataDumpsUrl	the URL of the folder where to save the data dumps.
	 * @return	an array of  {@link eu.aliada.ckancreation.model.DumpFileInfo}
	 * 			objects with the info about the files containing the graph dump.
	 * @since 2.0
	 */
    public ArrayList<DumpFileInfo> createGraphDump(final String graphURI, final String dataDumpsPath, 
    		final String dataDumpsUrl){
    	ArrayList<DumpFileInfo> dumpFilesInfo;
    	//Compose initial dump file name from the graph URI + current day
    	final int stInd = graphURI.lastIndexOf('/');
    	String dumpFileNameInit = graphURI.substring(stInd + 1);
    	dumpFileNameInit = dumpFileNameInit.replace('.', '_');
    	final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    	final Date date = new Date();
    	dumpFileNameInit = dumpFileNameInit + dateFormat.format(date);
    	String dumpFilePathInit = dataDumpsPath + File.separator + dumpFileNameInit;
		//Replace Windows file separator by "/" Java file separator
    	dumpFilePathInit = dumpFilePathInit.replace("\\", "/");
		//Compose ISQL command execution statement
    	final String isqlCommand = String.format(ISQL_COMMAND_FORMAT,
				jobConf.getIsqlCommandPath(), jobConf.getStoreIp(),
				jobConf.getStoreSqlPort(), jobConf.getSqlLogin(),
				jobConf.getSqlPassword(), graphURI,
				dumpFilePathInit,
				FILE_LENGTH_LIMIT);
		//Execute ISQL command
		try {
			LOGGER.debug(MessageCatalog._00040_EXECUTING_ISQL);
			final Process commandProcess = Runtime.getRuntime().exec(isqlCommand);
			final BufferedReader stdInput = new BufferedReader(new InputStreamReader(commandProcess.getInputStream()));
			String comOutput = "";
			while ((comOutput = stdInput.readLine()) != null) {
				LOGGER.debug(comOutput);
			}
			dumpFilesInfo = getGraphDumpsNames(graphURI, dataDumpsPath, dataDumpsUrl, dumpFileNameInit);
		} catch (IOException exception) {
			LOGGER.error(MessageCatalog._00033_EXTERNAL_PROCESS_START_FAILURE, exception, isqlCommand);
		}
		return dumpFilesInfo;
	}

}
