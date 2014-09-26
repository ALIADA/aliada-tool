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
	private final Log logger = new Log(LinkedDataServerSetup.class);
	/* Input parameters for URL rewrite rules */
	private String datasetBaseEncoded = ""; 
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
	private String getIsqlCommandsFile(JobConfiguration jobConf){
		String isqlCommandsFilename;
		isqlCommandsFilename = jobConf.getIsqlCommandsFilename();
		//Check if isqlCommandsFilename exists
		boolean isqlCommandsFilenameExists = false;
		if(isqlCommandsFilename != null){
			File f = new File(isqlCommandsFilename);
			if (f.exists())
				isqlCommandsFilenameExists = true;
		}
		if (!isqlCommandsFilenameExists){
			//If there is not a ISQL command file specifically for this job, use the default one
			isqlCommandsFilename = jobConf.getIsqlCommandsFilenameDefault();
			//Check if default isqlCommandsFilename exists
			isqlCommandsFilenameExists = false;
			if(isqlCommandsFilename != null){
				File f = new File(isqlCommandsFilename);
				if (f.exists())
					isqlCommandsFilenameExists = true;
			}
			if (!isqlCommandsFilenameExists){
				logger.error(MessageCatalog._00031_FILE_NOT_FOUND, isqlCommandsFilename);
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
	private boolean encodeParams(JobConfiguration jobConf){
		boolean encoded = false;
		try{
			datasetBaseEncoded = URLEncoder.encode(jobConf.getDatasetBase(),"UTF-8"); 
			graphEncoded = URLEncoder.encode(jobConf.getGraph(),"UTF-8"); 
			datasetBaseEncoded = datasetBaseEncoded.replace("%", "%%");
			graphEncoded = graphEncoded.replace("%", "%%");
			encoded = true;
		} catch (Exception exception){
			logger.error(MessageCatalog._00038_ENCODING_ERROR, exception);
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
	public Job setup(JobConfiguration jobConf, DBConnectionManager db) {
		logger.debug(MessageCatalog._00030_STARTING);
		//Update job start-date in DDBB
		db.updateJobStartDate(jobConf.getId());
		//Get ISQL commands file for rewriting rules in Virtuoso
		logger.debug(MessageCatalog._00036_GET_ISQL_COMMANDS_FILE);
		String isqlCommandsFilename = getIsqlCommandsFile(jobConf);
		if (isqlCommandsFilename != null){
			//URLEncode and prepare some command parameters for Virtuoso Rewrite Rules
			logger.debug(MessageCatalog._00037_ENCODE_PARAMS);
			boolean encoded = encodeParams(jobConf);
			if(encoded) {
				//Compose ISQL command execution statement
				String isqlCommandFormat = "%s %s:%d %s %s %s -i %s %s";
				String isqlCommand = String.format(isqlCommandFormat,
						jobConf.getIsqlCommandPath(), jobConf.getStoreIp(),
						jobConf.getStoreSqlPort(), jobConf.getSqlLogin(),
						jobConf.getSqlPassword(), isqlCommandsFilename,
						datasetBaseEncoded, graphEncoded);
				//Execute ISQL command
				try {
					logger.debug(MessageCatalog._00040_EXECUTING_ISQL);
					Process commandProcess = Runtime.getRuntime().exec(isqlCommand);
					BufferedReader stdInput = new BufferedReader(new InputStreamReader(commandProcess.getInputStream()));
					String s = "";
					while ((s = stdInput.readLine()) != null) {
						System.out.println(s);
					}
				} catch (IOException exception) {
					logger.error(MessageCatalog._00033_EXTERNAL_PROCESS_START_FAILURE, exception, isqlCommand);
				}
			}
		}
		
		//Update job end_date of DDBB
		logger.debug(MessageCatalog._00057_UPDATING_JOB_DDBB, jobConf.getId());
		db.updateJobEndDate(jobConf.getId());
		Job job = db.getJob(jobConf.getId());
		logger.debug(MessageCatalog._00041_STOPPED);
		return job;
	}

}
