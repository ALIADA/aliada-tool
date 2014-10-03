// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-linked-data-server
// Responsible: ALIADA Consortium
package eu.aliada.linkedDataServerSetup.impl;

import eu.aliada.linkedDataServerSetup.log.MessageCatalog;
import eu.aliada.shared.log.Log;
import eu.aliada.linkedDataServerSetup.model.JobConfiguration;
import eu.aliada.linkedDataServerSetup.model.Job;
import eu.aliada.linkedDataServerSetup.rdbms.DBConnectionManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.InputStreamReader;
import java.net.URLEncoder;

/**
 * Linked Data Server setup implementation. 
 * It setups the rewrite rules in Virtuoso for de-referencing the dataset URI-s.
 *  
 * @author Idoia Murua
 * @since 1.0
 */
public class LinkedDataServerSetup {
	/** For logging. */
	private static final Log LOGGER = new Log(LinkedDataServerSetup.class);
	/** Format for the ISQL command to execute */
	private static final String ISQL_COMMAND_FORMAT = "%s %s:%d %s %s %s -i %s %s"; 
	/* Input parameters for URL rewrite rules */
	/** datasetBase encoded in UTF-8 */
	private String datasetBaseEncoded = "";
	/** graph name encoded in UTF-8 */
	private String graphEncoded = ""; 

	/**
	 * Get the appropiate ISQL commands file.
	 * The ISQL commands are for creating rewrite rules in Virtuoso for
	 * dereferencing the dataset URI-s. 
	 *
	 * @param jobConf	the {@link eu.aliada.linkedDataServerSetup.model.JobConfiguration}
	 *               	that contains the ISQL commands file names.  
	 * @param htmlMap	it indicates whether to use the mapping for HTML
	 * 					rendering provided by the job. Otherwise, use
	 * 					Virtuoso´s default HTML rendering.
	 * @return the name of the newly created commands file.
	 * @since 1.0
	 */
	public String getIsqlCommandsFile(final JobConfiguration jobConf){
		String isqlCommandsFilename;
		isqlCommandsFilename = jobConf.getIsqlCommandsFilename();
		//Check if isqlCommandsFilename exists
		boolean isqlCommandsFilenameExists = false;
		if(isqlCommandsFilename != null){
			final File isqlFile = new File(isqlCommandsFilename);
			if (isqlFile.exists()){
				isqlCommandsFilenameExists = true;
			}
		}
		if (!isqlCommandsFilenameExists){
			//If there is not a ISQL command file specifically for this job, 
			//use the default one
			isqlCommandsFilename = jobConf.getIsqlCommandsFilenameDefault();
			//Check if default isqlCommandsFilename exists
			isqlCommandsFilenameExists = false;
			if(isqlCommandsFilename != null){
				final File isqlFile = new File(isqlCommandsFilename);
				if (isqlFile.exists()){
					isqlCommandsFilenameExists = true;
				}
			}
			if (!isqlCommandsFilenameExists){
				LOGGER.error(MessageCatalog._00031_FILE_NOT_FOUND, isqlCommandsFilename);
				isqlCommandsFilename = null;
			}
		}
		return isqlCommandsFilename;
	}

	/**
	 * Encodes the parameters to pass to the ISQL commands.
	 * URLEncode and replace % by %%, for Virtuoso Rewrite Rules.
	 *
	 * @param jobConf	the {@link eu.aliada.linkedDataServerSetup.model.JobConfiguration}
	 *               	that contains the ISQL commands parameters.  
	 * @return the name of the newly created commands file.
	 * @since 1.0
	 */
	public boolean encodeParams(final JobConfiguration jobConf){
		boolean encoded = false;
		try{
			datasetBaseEncoded = URLEncoder.encode(jobConf.getDatasetBase(),"UTF-8"); 
			graphEncoded = URLEncoder.encode(jobConf.getGraph(),"UTF-8"); 
			datasetBaseEncoded = datasetBaseEncoded.replace("%", "%%");
			graphEncoded = graphEncoded.replace("%", "%%");
			encoded = true;
		} catch (UnsupportedEncodingException exception){
			LOGGER.error(MessageCatalog._00038_ENCODING_ERROR, exception);
		}
		return encoded;
	}

	/**
	 * It setups the rewrite rules in Virtuoso for dereferencing the dataset URI-s.
	 *
	 * @param jobConf		the {@link eu.aliada.linkedDataServerSetup.model.JobConfiguration}
	 *						that contains information to setup the rewrite rules in Virtuoso  
	 * @param db			The DDBB connection. 
	 * @return the {@link eu.aliada.linkedDataServerSetup.model.job} created.  					
	 * @since 1.0
	 */
	public Job setup(final JobConfiguration jobConf, final DBConnectionManager dbConn) {
		LOGGER.debug(MessageCatalog._00030_STARTING);
		//Update job start-date in DDBB
		dbConn.updateJobStartDate(jobConf.getId());
		//Get ISQL commands file for rewriting rules in Virtuoso
		LOGGER.debug(MessageCatalog._00036_GET_ISQL_COMMANDS_FILE);
		final String isqlCommandsFilename = getIsqlCommandsFile(jobConf);
		if (isqlCommandsFilename != null){
			//URLEncode and prepare some command parameters for Virtuoso Rewrite Rules
			LOGGER.debug(MessageCatalog._00037_ENCODE_PARAMS);
			final boolean encoded = encodeParams(jobConf);
			if(encoded) {
				//Compose ISQL command execution statement
				final String isqlCommand = String.format(ISQL_COMMAND_FORMAT,
						jobConf.getIsqlCommandPath(), jobConf.getStoreIp(),
						jobConf.getStoreSqlPort(), jobConf.getSqlLogin(),
						jobConf.getSqlPassword(), isqlCommandsFilename,
						datasetBaseEncoded, graphEncoded);
				//Execute ISQL command
				try {
					LOGGER.debug(MessageCatalog._00040_EXECUTING_ISQL);
					final Process commandProcess = Runtime.getRuntime().exec(isqlCommand);
					final BufferedReader stdInput = new BufferedReader(new InputStreamReader(commandProcess.getInputStream()));
					String comOutput = "";
					while ((comOutput = stdInput.readLine()) != null) {
						LOGGER.debug(comOutput);
					}
				} catch (IOException exception) {
					LOGGER.error(MessageCatalog._00033_EXTERNAL_PROCESS_START_FAILURE, exception, isqlCommand);
				}
			}
		}
		
		//Update job end_date of DDBB
		LOGGER.debug(MessageCatalog._00057_UPDATING_JOB_DDBB, jobConf.getId());
		dbConn.updateJobEndDate(jobConf.getId());
		final Job job = dbConn.getJob(jobConf.getId());
		LOGGER.debug(MessageCatalog._00041_STOPPED);
		return job;
	}

}
