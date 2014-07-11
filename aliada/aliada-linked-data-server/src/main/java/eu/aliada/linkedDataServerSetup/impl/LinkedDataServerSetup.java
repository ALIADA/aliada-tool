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
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Linked Data Server setup implementation. 
 * It setups the rewrite rules in Virtuoso for dereferencing the dataset URI-s.
 *  
 * @author Idoia Murua
 * @since 1.0
 */
public class LinkedDataServerSetup {

	private final Log logger = new Log(LinkedDataServerSetup.class);

	/**
	 * It setups the rewrite rules in Virtuoso for dereferencing the dataset URI-s.
	 *
	 * @param jobConf		the {@link eu.aliada.linkedDataServerSetup.model.JobConfiguration}
	 *						that contains information for configuring the linking processes.  
	 * @param db			The DDBB connection. 
	 * @return the {@link eu.aliada.linkedDataServerSetup.model.job} created.  					
	 * @since 1.0
	 */
	public Job setup(JobConfiguration jobConf, DBConnectionManager db) {
		logger.info(MessageCatalog._00030_STARTING);
		//Update job start-date in DDBB
		db.updateJobStartDate(jobConf.getId());
		//TODO: Execute ISQL commands for rewriting rules in Virtuoso
		// Execute system command "crontab contrabfilename"
		String command = "D:\\Proyectos\\downloads\\OpenLinkVirtuoso\\virtuoso-opensource\\bin\\isql 172.25.5.15:1111 dba dba 'exec=select * from DB.DBA.load_list;'";
		try {
			logger.info(MessageCatalog._00040_EXECUTING_CRONTAB);
			Runtime.getRuntime().exec(command);
  	    	Process crontabList = Runtime.getRuntime().exec(command);
	    	BufferedReader stdInput = new BufferedReader(new InputStreamReader(crontabList.getInputStream()));
	    	String s = "";
	        while ((s = stdInput.readLine()) != null) {
	        	System.out.println(s);
	        }
		} catch (IOException exception) {
			logger.error(MessageCatalog._00033_EXTERNAL_PROCESS_START_FAILURE, exception, command);
		}
		//Update job end_date of DDBB, if needed
		logger.info(MessageCatalog._00057_UPDATING_JOB_DDBB, jobConf.getId());
		db.updateJobEndDate(jobConf.getId());
		Job job = db.getJob(jobConf.getId());
		logger.info(MessageCatalog._00041_STOPPED);
		return job;
	}

}
