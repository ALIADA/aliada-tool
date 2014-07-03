// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.linksDiscovery.impl;

import eu.aliada.linksDiscovery.log.MessageCatalog;
import eu.aliada.shared.log.Log;
import de.fuberlin.wiwiss.silk.Silk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Links discovery process via Silk library. 
 * @author Idoia Murua
 * @since 1.0
 */
public class LinkingProcess {
	private final Log logger = new Log(LinkingProcess.class);
	private String dbUsername;
	private String dbPassword;
	private String dbDriverClassName;
	private String dbURL;
	private String linkingXMLConfigFilename;
	private File linkingXMLConfigFile;
	private int linkingNumThreads;
	private boolean linkingReload;
	private Connection conn = null;

	public boolean getConfigProperties (String propertiesFileName) {
		try {
			InputStream propertyStream = new FileInputStream(propertiesFileName);
    		Properties p = new Properties();
    		p.load( propertyStream );
    		if (p.getProperty("database.username") != null)
    			dbUsername = p.getProperty("database.username");
    		if (p.getProperty("database.password") != null)
    			dbPassword = p.getProperty("database.password");
    		if (p.getProperty("database.driverClassName") != null)
    			dbDriverClassName = p.getProperty("database.driverClassName");
    		if (p.getProperty("database.url") != null)
    			dbURL = p.getProperty("database.url");
    		if (p.getProperty("linking.config.filename") != null)
    			linkingXMLConfigFilename = p.getProperty("linking.config.filename");
    		if (p.getProperty("linking.numthreads") != null)
    			linkingNumThreads = new Integer(p.getProperty("linking.numthreads"));
    		if (p.getProperty("linking.reload") != null)
    			linkingReload = new Boolean(p.getProperty("linking.reload"));
		} catch(FileNotFoundException exception) {
			logger.error(MessageCatalog._00031_CONFIG_FILE_NOT_FOUND, propertiesFileName);
			return false;
    	} catch(IOException exception) {
			logger.error(MessageCatalog._00032_BAD_CONFIG_FILE, exception, propertiesFileName);
			return false;
    	}
		try{
			linkingXMLConfigFile = new File(linkingXMLConfigFilename);
			if (!linkingXMLConfigFile.exists())
				return false;
		}catch (Exception exception) {
			return false;
		}
		return true;
    }
    
    public boolean connectToDDBB() {
    	try {
    		Class.forName(dbDriverClassName);
    		//setup the connection with the DB.
    		conn = DriverManager.getConnection(dbURL + "?user=" + dbUsername + "&password=" + dbPassword);
    		return true;
		} catch (ClassNotFoundException exception) {
			logger.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
    		return false;
		} catch (SQLException exception) {
			logger.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
    		return false;
		}
    	
    }

	public boolean updateSubJobStartDate(int job, int subjob){
		//Update start_date of subjob
    	try {
			PreparedStatement preparedStatement = null;		
		    preparedStatement = conn.prepareStatement("UPDATE linksdiscovery_subjob_instances SET start_date = ? WHERE job_id = ? AND subjob_id = ?");
		      // (job_id, subjob_id, start_date, end_date)
		      // parameters start with 1
		      java.util.Date today = new java.util.Date();
		      java.sql.Timestamp todaySQL = new java.sql.Timestamp(today.getTime());
		      preparedStatement.setTimestamp(1, todaySQL);
		      preparedStatement.setInt(2, job);
		      preparedStatement.setInt(3, subjob);
		      preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			logger.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
    		return false;
		}
  		return true;
	}

	public boolean updateSubJobEndDate(int job, int subjob){
		//Update end_date of subjob
    	try {
			PreparedStatement preparedStatement = null;		
		    preparedStatement = conn.prepareStatement("UPDATE linksdiscovery_subjob_instances SET end_date = ? WHERE job_id = ? AND subjob_id = ?");
		      // (job_id, subjob_id, start_date, end_date)
		      // parameters start with 1
		      java.util.Date today = new java.util.Date();
		      java.sql.Timestamp todaySQL = new java.sql.Timestamp(today.getTime());
		      preparedStatement.setTimestamp(1, todaySQL);
		      preparedStatement.setInt(2, job);
		      preparedStatement.setInt(3, subjob);
		      preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			logger.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
    		return false;
		}
  		return true;
	}

	public boolean checkJobSubjobsFinished(int job){
		//Check if all subjobs of a jof have finished
		try {
			Statement sta = conn.createStatement();
			ResultSet resultSet = sta.executeQuery("SELECT * FROM linksdiscovery_subjob_instances WHERE job_id=" + job);
			while (resultSet.next()) {
				java.sql.Date endDate = resultSet.getDate("end_date");
				//If one of the subjobs has not finished yet, the job is not finished
				if (endDate == null)
					return false;
		    }
		} catch (SQLException exception) {
			logger.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			return false;
		}
  		return true;
	}

	public boolean updateJobEndDate(int job){
		//Check if all subjobs of a jof have finished
		//If one of the subjobs has not finished yet, the job is not finished
		if(checkJobSubjobsFinished(job)){
			//Update end_date of job
	    	try {
				PreparedStatement preparedStatement = null;		
			    preparedStatement = conn.prepareStatement("UPDATE linksdiscovery_job_instances SET end_date = ? WHERE job_id = ?");
			      // (job_id, subjob_id, start_date, end_date)
			      // parameters start with 1
			      java.util.Date today = new java.util.Date();
			      java.sql.Timestamp todaySQL = new java.sql.Timestamp(today.getTime());
			      preparedStatement.setTimestamp(1, todaySQL);
			      preparedStatement.setInt(2, job);
			      preparedStatement.executeUpdate();
			} catch (SQLException exception) {
				logger.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
	    		return false;
			}
		}
  		return true;
	}

	/**
	 * Main function.
	 *
     * @param args				Application arguments. 
     *
     * @return 					
     * @since 1.0
	 */
	public static void main(String[] args) {
		LinkingProcess lProcess = new LinkingProcess();
		lProcess.logger.info(MessageCatalog._00050_STARTING);
		if(args.length == 3) {
			int job = new Integer(args[0]); 
			int subjob = new Integer(args[1]);
			String propertiesFileName = args[2];
			lProcess.logger.info(MessageCatalog._00057_SUBJOB_PARAMETERS, job, subjob, propertiesFileName);
			//Read configuration file to start threads execution
			lProcess.logger.info(MessageCatalog._00051_GET_CONFIG_FILE_PROPERTIES, propertiesFileName);
			if (!lProcess.getConfigProperties(propertiesFileName)){
				lProcess.logger.info(MessageCatalog._00059_STOPPING);
				System.exit(2);
			}
			//Connect to DDBB
			lProcess.logger.info(MessageCatalog._00052_CONNECT_DDBB);
			if(!lProcess.connectToDDBB()){
				lProcess.logger.info(MessageCatalog._00059_STOPPING);
				System.exit(2);
			}
			//Update subjob start_date in DDBB
			lProcess.logger.info(MessageCatalog._00056_UPDATING_SUBJOB_DDBB, job, subjob);
			if(!lProcess.updateSubJobStartDate(job, subjob)){
				lProcess.logger.info(MessageCatalog._00059_STOPPING);
				System.exit(2);
			}
			//Execute SILK
			lProcess.logger.info(MessageCatalog._00053_SILK_STARTING, lProcess.linkingXMLConfigFile, lProcess.linkingNumThreads, lProcess.linkingReload);
			try {
				Silk.executeFile(lProcess.linkingXMLConfigFile, (String) null, lProcess.linkingNumThreads, lProcess.linkingReload);
			} catch (Exception exception){
				lProcess.logger.error(MessageCatalog._00054_SILK_EXCEPTION, exception);
			}
			lProcess.logger.info(MessageCatalog._00055_SILK_FINISHED, lProcess.linkingXMLConfigFile, lProcess.linkingNumThreads, lProcess.linkingReload);
			//Update subjob end_date of DDBB
			lProcess.logger.info(MessageCatalog._00056_UPDATING_SUBJOB_DDBB, job, subjob);
			lProcess.updateSubJobEndDate(job, subjob);
			//Update job end_date of DDBB, if needed
			lProcess.logger.info(MessageCatalog._00057_UPDATING_JOB_DDBB, job);
			lProcess.updateJobEndDate(job);
			lProcess.logger.info(MessageCatalog._00059_STOPPING);
		}
		else {
			//If the arguments are not correct, exit the program
			String parameters = "";
			for(int i=0;i<args.length;i++)
				parameters = parameters + " arg" + i + "=" + args[i];
			lProcess.logger.error(MessageCatalog._00058_SUBJOB_PARAMETERS_INCORRECT, parameters);
			lProcess.logger.info(MessageCatalog._00059_STOPPING);
			System.exit(2);
		}
	}
}
