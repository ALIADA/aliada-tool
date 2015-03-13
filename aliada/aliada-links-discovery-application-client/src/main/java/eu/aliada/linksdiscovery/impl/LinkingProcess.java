// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery-application-client
// Responsible: ALIADA Consortium
package eu.aliada.linksdiscovery.impl;

import eu.aliada.linksdiscovery.log.MessageCatalog;
import eu.aliada.linksdiscovery.model.JobConfiguration;
import eu.aliada.linksdiscovery.model.SubjobConfiguration;
import eu.aliada.shared.log.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Links discovery process which uses SILK library for discovering links. 
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class LinkingProcess {
	/** For logging. */
	static final Log LOGGER = new Log(LinkingProcess.class);
	/** Number of input parameters for the process. */
	static final int PROCESS_PARAM_NUM = 3;
	/** Crontab file name. */
	private final static String CRONTAB_FILENAME = "aliada_links_discovery";
	/** Crontab file extension. */
	private final static String CRONTAB_EXT = ".cron"; 
	/** Crontab List command. */
	private final static String CRONTAB_LIST_COMMAND = "crontab -l";

	/** Name of the linking process to program with crontab, that executes 
	 * {@link eu.aliada.linksDiscovery.impl.LinkingProcess} (this class) java application
	 */
	private final static String LINKING_PROCESS_NAME = "links-discovery-task-runner.sh";
	/* DDBB connection parameters */
	/** DDBB connection user name. */
	private String dbUsername;
	/** DDBB connection password. */
	private String dbPassword;
	/** DDBB connection driver class name. */
	private String dbDriverClassName;
	/** DDBB connection URL. */
	private String dbURL;
	/** DDBB connection. */
	private Connection conn;
	/** Subjob name, which includes external dataset name. */
	private String subjobName;
	/** XML configuration file name for SILK. */
	private String linkingXMLConfigFilename;
	/** Temporary folder */
	private String tmpDir;

	/**
	 * Gets the properties from a properties file.
	 *
	 * @param propertiesFileName	the name of the properties file.
	 * @return true if the properties have been obtained. False otherwise.
	 * @since 1.0
	 */
	public boolean getConfigProperties (final String propertiesFileName) {
		try {
			final InputStream propertyStream = new FileInputStream(propertiesFileName);
    		final Properties props = new Properties();
    		props.load( propertyStream );
    		if (props.getProperty("database.username") != null) {
    			dbUsername = props.getProperty("database.username");
    		}
    		if (props.getProperty("database.password") != null) {
    			dbPassword = props.getProperty("database.password");
    		}
    		if (props.getProperty("database.driverClassName") != null) {
    			dbDriverClassName = props.getProperty("database.driverClassName");
    		}
    		if (props.getProperty("database.url") != null) {
    			dbURL = props.getProperty("database.url");
    		}
		} catch(FileNotFoundException exception) {
			LOGGER.error(MessageCatalog._00031_FILE_NOT_FOUND, propertiesFileName);
			return false;
    	} catch(IOException exception) {
			LOGGER.error(MessageCatalog._00032_BAD_FILE, exception, propertiesFileName);
			return false;
    	}
		return true;
	}
    
	/**
	 * Gets the DDBB connection.
	 *
	 * @return true if the DDBB connection has been obtained. False otherwise.
	 * @since 1.0
	 */
	public boolean connectToDDBB() {
		try {
			Class.forName(dbDriverClassName);
			//setup the connection with the DB.
			conn = DriverManager.getConnection(dbURL + "?user=" + dbUsername + "&password=" + dbPassword);
			return true;
		} catch (ClassNotFoundException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			return false;
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			return false;
		}
	}
    
	/**
	 * Closes the DDBB connection.
	 *
	 * @since 1.0
	 */
	public void closeDDBBConnection() {
		try {
			conn.close();
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
		}
	}


	/**
	 * Gets the job configuration from the DB.
	 *
	 * @param jobId	the job identification.
	 * @return	the {@link eu.aliada.linksdiscovery.model.JobConfiguration}
	 * 			which contains the configuration of the job.
	 * @since 1.0
	 */
	public JobConfiguration getJobConfiguration(final Integer jobId) {
		JobConfiguration job = null;
		try {
			final Statement sta = conn.createStatement();
			final String sql = "SELECT * FROM linksdiscovery_job_instances WHERE job_id=" + jobId;
			final ResultSet resultSet = sta.executeQuery(sql);
			while (resultSet.next()) {
				job = new JobConfiguration();
				job.setId(jobId);
				job.setInputURI(resultSet.getString("input_uri"));
				job.setInputLogin(resultSet.getString("input_login"));
				job.setInputPassword(resultSet.getString("input_password"));
				job.setInputGraph(resultSet.getString("input_graph"));
				job.setOutputURI(resultSet.getString("output_uri"));
				job.setOutputLogin(resultSet.getString("output_login"));
				job.setOutputPassword(resultSet.getString("output_password"));
				job.setOutputGraph(resultSet.getString("output_graph"));
				job.setTmpDir(resultSet.getString("tmp_dir"));
				job.setClientAppBinDir(resultSet.getString("client_app_bin_dir"));
				job.setClientAppBinUser(resultSet.getString("client_app_user"));
		    }
			resultSet.close();
			sta.close();
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			return null;
		}
		return job;
	}

	/**
	 * Gets the subjob configuration from the DDBB.
	 *
	 * @param jobId		the job identification.
	 * @param subjobId	the subjob identification.
	 * @return	the {@link eu.aliada.linksdiscovery.model.SubjobConfiguration}
	 *			which contain the configuration of the subjob.
	 * @since 1.0
	 */
	public SubjobConfiguration getSubjobConfiguration(final int jobId, final int subjobId) {
		//Get subjob properties from DDBB
		SubjobConfiguration subjobConf = null;
		boolean found = false;
		try {
			final Statement sta = conn.createStatement();
			final String sql = "SELECT * FROM linksdiscovery_subjob_instances WHERE job_id=" + jobId + " AND subjob_id=" + subjobId;
			final ResultSet resultSet = sta.executeQuery(sql);
			while (resultSet.next()) {
				found = true;
				subjobName = resultSet.getString("name");
	    		linkingXMLConfigFilename = resultSet.getString("config_file");
	    		tmpDir = resultSet.getString("tmp_dir");
				subjobConf = new SubjobConfiguration();
				subjobConf.setId(resultSet.getInt("subjob_id"));
				subjobConf.setName(resultSet.getString("name"));
				subjobConf.setLinkingXMLConfigFilename(resultSet.getString("config_file"));
				subjobConf.setDs("ALIADA_ds");
				subjobConf.setLinkingNumThreads(resultSet.getInt("num_threads"));
				final int reloadSource = resultSet.getInt("reload_source");
				if(reloadSource == 1) {
					subjobConf.setLinkingReloadSource(true);
				} else {
					subjobConf.setLinkingReloadSource(false);
				}
				final int reloadTarget = resultSet.getInt("reload_target");
				if(reloadTarget == 1) {
					subjobConf.setLinkingReloadTarget(true);
				} else {
					subjobConf.setLinkingReloadTarget(false);
				}
		    }
			resultSet.close();
			sta.close();
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
		}
		if(!found){
			LOGGER.error(MessageCatalog._00067_SUBJOB_CONFIGURATION_NOT_FOUND, jobId, subjobId);
		}
		return subjobConf;
	}
    
	/**
	 * Updates the start_date of the subjob.
	 *
	 * @param jobId		the job identification.
	 * @param subjobId	the subjob identification.
	 * @return true if the date has been updated correctly ç
	 *         in the DDBB. False otherwise.
	 * @since 1.0
	 */
	public boolean updateSubjobStartDate(final int jobId, final int subjobId){
		//Update start_date of subjob
		try {
			PreparedStatement preparedStatement = null;		
			preparedStatement = conn.prepareStatement("UPDATE linksdiscovery_subjob_instances SET start_date = ? WHERE job_id = ? AND subjob_id = ?");
			// parameters start with 1
			final java.util.Date today = new java.util.Date();
			final java.sql.Timestamp todaySQL = new java.sql.Timestamp(today.getTime());
			preparedStatement.setTimestamp(1, todaySQL);
			preparedStatement.setInt(2, jobId);
			preparedStatement.setInt(3, subjobId);
			preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			return false;
		}
		return true;
	}

	/**
	 * Updates the end_date and num_links generated of the subjob.
	 *
	 * @param jobId		the job identification.
	 * @param subjobId	the subjob identification.
	 * @return true if the data have been updated correctly 
	 *         in the DDBB. False otherwise.
	 * @since 1.0
	 */
	public boolean updateSubjobEndDate(final int jobId, final int subjobId, final int numLinks){
		//Update end_date, num_links of subjob
		try {
    		PreparedStatement preparedStatement = null;		
    		preparedStatement = conn.prepareStatement("UPDATE linksdiscovery_subjob_instances SET end_date = ?, num_links = ?  WHERE job_id = ? AND subjob_id = ?");
    		// parameters start with 1
    		final java.util.Date today = new java.util.Date();
    		final java.sql.Timestamp todaySQL = new java.sql.Timestamp(today.getTime());
    		preparedStatement.setTimestamp(1, todaySQL);
    		preparedStatement.setInt(2, numLinks);
    		preparedStatement.setInt(3, jobId);
    		preparedStatement.setInt(4, subjobId);
    		preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			return false;
		}
		return true;
	}

	/**
	 * Checks if all subjobs of a job have finished.
	 *
	 * @param jobId		the job identification.
	 * @return true if all subjobs of the job have finished. False otherwise.
	 * @since 1.0
	 */
	public boolean checkJobSubjobsFinished(final int job){
		//Check if all subjobs of a jof have finished
		try {
			final Statement sta = conn.createStatement();
			final ResultSet resultSet = sta.executeQuery("SELECT * FROM linksdiscovery_subjob_instances WHERE job_id=" + job);
			while (resultSet.next()) {
				final java.sql.Date endDate = resultSet.getDate("end_date");
				//If one of the subjobs has not finished yet, the job is not finished
				if (endDate == null) {
					resultSet.close();
					sta.close();
					return false;
				}
			}
			resultSet.close();
			sta.close();
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			return false;
		}
  		return true;
	}

	/**
	 * Updates the end_date of the job.
	 *
	 * @param jobId	the job identification.
	 * @return true if the date has been updated correctly 
	 *         in the DDBB. False otherwise.
	 * @since 1.0
	 */
	public boolean updateJobEndDate(final int jobId){
		//Check if all subjobs of a jof have finished
		//If one of the subjobs has not finished yet, the job is not finished
		if(checkJobSubjobsFinished(jobId)){
			//Update end_date of job
	    	try {
	    		PreparedStatement preparedStatement = null;		
	    		preparedStatement = conn.prepareStatement("UPDATE linksdiscovery_job_instances SET end_date = ? WHERE job_id = ?");
	    		// parameters start with 1
	    		final java.util.Date today = new java.util.Date();
	    		final java.sql.Timestamp todaySQL = new java.sql.Timestamp(today.getTime());
	    		preparedStatement.setTimestamp(1, todaySQL);
	    		preparedStatement.setInt(2, jobId);
	    		preparedStatement.executeUpdate();
			} catch (SQLException exception) {
				LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
				return false;
			}
		}
  		return true;
	}

	/**
	 * Removes the subjob programming from the crontab.
	 *
	 * @param jobId					the job identification.
	 * @param subjobId				the subjob identification.
	 * @param propertiesFileName	the name of the properties 
	 *                              file of the programmed subjob.
	 * 
	 * @return true if the subjob has been removed correctly from 
	 *         the crontab. False otherwise.
	 * @since 1.0
	 */
	public boolean removeSubjobFromCrontab(final int jobId, final int subjobId, final String propertiesFileName){
		boolean removed = false;
		//String to supress from crontab file
		final String crontabLineToSearch = String.format("%s %d %d %s", LINKING_PROCESS_NAME, jobId, subjobId, propertiesFileName);
		//Create a new crontab file supressing the subjob already finished
		String crontabFilename = tmpDir + File.separator + CRONTAB_FILENAME +  System.currentTimeMillis() + CRONTAB_EXT;
		//Replace Windows file separator by "/" Java file separator
		crontabFilename = crontabFilename.replace("\\", "/");
		//Remove the crontab file if it already exists
		final File cronFile = new File(crontabFilename);
		if (cronFile.exists()) {
			cronFile.delete();
		}
		//Now, create a new one
		try {
			final FileWriter fstream = new FileWriter(crontabFilename);
			final BufferedWriter out = new BufferedWriter(fstream);
			// Execute system command "crontab -l"
	    	try {
		    	String line = null;
	  	    	final Process crontabList = Runtime.getRuntime().exec(CRONTAB_LIST_COMMAND);
		    	final BufferedReader stdInput = new BufferedReader(new InputStreamReader(crontabList.getInputStream()));
		        while ((line = stdInput.readLine()) != null) {
		        	if (line.indexOf(crontabLineToSearch) < 0){
		        		//If it is not the subjob already finished, include the line again in crontab file
		        		out.write(line);
		        		out.newLine();
		        	}
		        }
	    	} catch (IOException exception) {
	    		LOGGER.error(MessageCatalog._00033_EXTERNAL_PROCESS_START_FAILURE, exception, CRONTAB_LIST_COMMAND);
	    	}
	    	out.close();
		} catch (IOException exception) {
			LOGGER.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, crontabFilename);
		}
		// Execute system command "crontab contrabfilename"
		final String command = "crontab " + crontabFilename;
		try {
			LOGGER.debug(MessageCatalog._00040_EXECUTING_CRONTAB);
			Process proc = Runtime.getRuntime().exec(command);
			proc.waitFor();
			removed = true;
		} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00033_EXTERNAL_PROCESS_START_FAILURE, exception, command);
		}
		//Remove crontab file
		if (cronFile.exists()) {
			cronFile.delete();
		}
		return removed;
	}

	/**
	 * Removes the configuration files used by the subjob from the temporary folder.
	 *
	 * @param propertiesFileName		the name of the properties file of the 
	 * 									programmed subjob.
	 * @param linkingXMLConfigFilename	the name of the XML configuration file 
	 * 									used by SILK.
	 * @since 1.0
	 */
	public void removeConfigFiles(final String propertiesFileName, final String linkingXMLConfigFilename){
		//Remove configuration files
		String fileName = propertiesFileName; 
		try {
			File propFile = new File(propertiesFileName);
			if (propFile.exists()) {
				propFile.delete();
			}
			fileName = linkingXMLConfigFilename;
			propFile = new File(linkingXMLConfigFilename);
			if (propFile.exists()) {
				propFile.delete();
			}
		} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00066_FILE_REMOVING_FAILURE, exception, fileName);
		}
	}

	
	/**
	 * Main function. This is the function that executes when the programmed 
	 * process in crontab starts executing. This function gets all the 
	 * needed parameters to configure SILK, executes it, and gets the results.
	 * The number of generated links is saved in the subjob, in the DDBB.
	 * When finished, all temporary files are removed, and the programmed subjob
	 * is removed from crontab    
	 *
	 * @param args	Application arguments. 
	 * @since 1.0
	 */
	public static void main(final String[] args) {
		final LinkingProcess lProcess = new LinkingProcess();
		lProcess.LOGGER.info(MessageCatalog._00050_STARTING);
		if(args.length == PROCESS_PARAM_NUM) {
			final int job = Integer.valueOf(args[0]); 
			final int subjob = Integer.valueOf(args[1]);
			final String propertiesFileName = args[2];
			lProcess.LOGGER.debug(MessageCatalog._00057_SUBJOB_PARAMETERS, job, subjob, propertiesFileName);
			//Read configuration file to start threads execution
			lProcess.LOGGER.debug(MessageCatalog._00051_GET_CONFIG_FILE_PROPERTIES, propertiesFileName);
			if (!lProcess.getConfigProperties(propertiesFileName)){
				lProcess.LOGGER.info(MessageCatalog._00059_STOPPING);
				System.exit(2);
			}
			//Connect to DDBB
			lProcess.LOGGER.debug(MessageCatalog._00052_CONNECT_DDBB);
			if(!lProcess.connectToDDBB()){
				lProcess.LOGGER.info(MessageCatalog._00059_STOPPING);
				System.exit(2);
			}
			//Get subjob properties from DDBB
			lProcess.LOGGER.debug(MessageCatalog._00064_GET_PROPERTIES_FROM_DDBB, job, subjob);
			final JobConfiguration jobConf = lProcess.getJobConfiguration(job);
			if(jobConf == null){
				lProcess.closeDDBBConnection();
				lProcess.LOGGER.info(MessageCatalog._00059_STOPPING);
				System.exit(2);
			}
			final SubjobConfiguration subjobConf = lProcess.getSubjobConfiguration(job, subjob);
			if(subjobConf == null){
				lProcess.closeDDBBConnection();
				lProcess.LOGGER.info(MessageCatalog._00059_STOPPING);
				System.exit(2);
			}
			//Update subjob start_date in DDBB
			lProcess.LOGGER.debug(MessageCatalog._00056_UPDATING_SUBJOB_DDBB, job, subjob);
			if(!lProcess.updateSubjobStartDate(job, subjob)){
				lProcess.closeDDBBConnection();
				lProcess.LOGGER.info(MessageCatalog._00059_STOPPING);
				System.exit(2);
			}
			int numLinks = 0;
			//Check if the external dataset is one of the ones that do not provide a SPARQL endpoint.
			if(lProcess.subjobName.toUpperCase().contains("LOBID") || 
					lProcess.subjobName.toUpperCase().contains("VIAF") ||
					lProcess.subjobName.toUpperCase().contains("CONGRESS") ||
					lProcess.subjobName.toUpperCase().contains("OPEN")) {
				//Search using the specific API provided
				final SpecificAPIDataset specificAPIDataset = new SpecificAPIDataset();
				numLinks = specificAPIDataset.searchProcess(jobConf, subjobConf);
			} else {
				//Provides a SPARQL endpoint, so SILK is used 
				final SPARQLDataset sparqlDataset = new SPARQLDataset();
				numLinks = sparqlDataset.searchProcess(jobConf, subjobConf);
			}
			//Update subjob end_date, num_links of DDBB
			lProcess.LOGGER.debug(MessageCatalog._00056_UPDATING_SUBJOB_DDBB, job, subjob);
			lProcess.updateSubjobEndDate(job, subjob, numLinks);
			//Remove from crontab the programmed process (subjob)
			lProcess.LOGGER.debug(MessageCatalog._00063_REMOVING_SUBJOB_FROM_CRONTAB, job, subjob);
			lProcess.removeSubjobFromCrontab(job, subjob, propertiesFileName);
			//Remove config files
			lProcess.LOGGER.debug(MessageCatalog._00065_REMOVING_CONFIG_FILES);
			lProcess.removeConfigFiles(propertiesFileName, lProcess.linkingXMLConfigFilename);
			//Update job end_date of DDBB, if needed
			lProcess.LOGGER.debug(MessageCatalog._00057_UPDATING_JOB_DDBB, job);
			lProcess.updateJobEndDate(job);
			lProcess.LOGGER.info(MessageCatalog._00059_STOPPING);
		}
		else {
			//If the arguments are not correct, exit the program
			String parameters = "";
			for(int i=0;i<args.length;i++){
				parameters = parameters + " arg" + i + "=" + args[i];
			}
			lProcess.LOGGER.error(MessageCatalog._00058_SUBJOB_PARAMETERS_INCORRECT, parameters);
			lProcess.LOGGER.info(MessageCatalog._00059_STOPPING);
			System.exit(2);
		}
	}
}
