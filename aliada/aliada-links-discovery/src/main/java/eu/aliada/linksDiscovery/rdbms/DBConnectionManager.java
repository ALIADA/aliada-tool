// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.linksDiscovery.rdbms;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import eu.aliada.linksDiscovery.model.JobConfiguration;
import eu.aliada.linksDiscovery.model.SubjobConfiguration;
import eu.aliada.linksDiscovery.model.Job;
import eu.aliada.linksDiscovery.model.Subjob;
import eu.aliada.linksDiscovery.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * DDBB connection manager. 
 * It contains all the related functions with the DDBB: open, close connection,
 * execute SQL-s.
 *  
 * @author Idoia Murua
 * @since 1.0
 */
public class DBConnectionManager {
	/** For logging. */
	private static final Log LOGGER = new Log(DBConnectionManager.class);
	/* Job and subjob possible states */
	/** Subjob is idle */
	private final static String JOB_STATUS_IDLE = "idle"; 
	/** Subjob is running */
	private final static String JOB_STATUS_RUNNING = "running"; 
	/** Subjob has finished */
	private final static String JOB_STATUS_FINISHED = "finished"; 

	/** DDBB connection */
	private Connection conn;
	 
	/**
	 * Constructor. 
	 * Creates the DDBB connection.
	 *
	 * @since 1.0
	 */
	public DBConnectionManager() {
		InitialContext ic;
		DataSource ds;
		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/aliada");
			conn = ds.getConnection();
		} catch (NamingException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
		}
	}

	/**
	 * Closes the DDBB connection.
	 *
	 * @since 1.0
	 */
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
		}
	}
	
	/**
	 * Returns the DDBB connection.
	 *
	 * @return	the DDBB connection.
	 * @since 1.0
	 */
	public Connection getConnection() {
		return this.conn;
	}
 
	/**
	 * Gets the job configuration from the DDBB.
	 *
	 * @param jobId	the job identification.
	 * @return	the {@link eu.aliada.linksDiscovery.model.JobConfiguration}
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
				job.setConfigFile(resultSet.getString("config_file"));
				job.setRdfSinkFolder(resultSet.getString("rdf_sink_folder"));
				job.setRdfSinkLogin(resultSet.getString("rdf_sink_login"));
				job.setRdfSinkPassword(resultSet.getString("rdf_sink_password"));
				job.setTmpDir(resultSet.getString("tmp_dir"));
				job.setClientAppBinDir(resultSet.getString("client_app_bin_dir"));
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
	 * Updates the start_date of the job.
	 *
	 * @param jobId	the job identification.
	 * @return true if the date has been updated correctly in the DDBB. False otherwise.
	 * @since 1.0
	 */
	public boolean updateJobStartDate(final int jobId){
		//Update start_date of job
    	try {
    		PreparedStatement preparedStatement = null;		
    		preparedStatement = conn.prepareStatement("UPDATE linksdiscovery_job_instances SET start_date = ? WHERE job_id = ?");
    		// (job_id, start_date)
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
  		return true;
	}

	/**
	 * Updates the end_date of the job.
	 *
	 * @param jobId	the job identification.
	 * @return true if the date has been updated correctly in the DDBB. False otherwise.
	 * @since 1.0
	 */
	public boolean updateJobEndDate(final int jobId){
		//Update end_date of job
    	try {
    		PreparedStatement preparedStatement = null;		
    		preparedStatement = conn.prepareStatement("UPDATE linksdiscovery_job_instances SET end_date = ? WHERE job_id = ?");
    		// (job_id, end_date)
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
    	return true;
	}

	/**
	 * Inserts a new subjob in the DDBB.
	 *
	 * @param jobId						the job identification.
	 * @param subjobId					the subjob identification.
	 * @param subjobConf				{@link eu.aliada.linksDiscovery.model.SubjobConfiguration}
	 * 									which contain the configuration of the 
	 * 									subjob
	 * @param linkingXMLConfigFilename	Name of the XML configuration file for 
	 * 									the SILK process.
	 * @param tmpDir					the name of temporary folder.
	 * @return true if the subjob has been inserted correctly in the DDBB. False otherwise.
	 * @since 1.0
	 */
	public boolean insertSubjobToDDBB(final int jobId, final int subjobId, final SubjobConfiguration subjobConf, final String linkingXMLConfigFilename, final JobConfiguration jobConf){
    	try {
    		PreparedStatement preparedStatement = null;		
    		preparedStatement = conn.prepareStatement("INSERT INTO  linksdiscovery_subjob_instances VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, default, default, default)");
    		// (job_id, subjob_id, name, config_file, num_threads, reload, rdf_sink_folder, rdf_sink_login, rdf_sink_password, tmp_dir, num_links, start_date, end_date)
    		// parameters start with 1
    		preparedStatement.setInt(1, jobId);
    		preparedStatement.setInt(2, subjobId);
    		preparedStatement.setString(3, subjobConf.getName());
    		preparedStatement.setString(4, linkingXMLConfigFilename);
    		preparedStatement.setInt(5, subjobConf.getLinkingNumThreads());
    		preparedStatement.setBoolean(6, subjobConf.isLinkingReload());
    		preparedStatement.setString(7, jobConf.getRdfSinkFolder());
    		preparedStatement.setString(8, jobConf.getRdfSinkLogin());
    		preparedStatement.setString(9, jobConf.getRdfSinkPassword());
    		preparedStatement.setString(10, jobConf.getTmpDir());
    		preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			return false;
		}
    	return true;
	}
	
	/**
	 * Returns job and its corresponding subjobs information in the DDBB.
	 *
	 * @param jobId	the job identification.
	 * @return	the {@link eu.aliada.linksDiscovery.model.Job}
	 * 			which contains the job information.
	 * @since 1.0
	 */
	public Job getJob(final int jobId) {
		//Get the job information from the DDBB
		final Job job = new Job();
		job.setId(jobId);
		try {
			final Statement sta = conn.createStatement();
			String sql = "SELECT * FROM linksdiscovery_job_instances WHERE job_id=" + jobId;
			ResultSet resultSet = sta.executeQuery(sql);
			while (resultSet.next()) {
				job.setStartDate(resultSet.getTimestamp("start_date"));
				job.setEndDate(resultSet.getTimestamp("end_date"));
				//Determine job status
				String status = JOB_STATUS_IDLE;
				if(job.getStartDate() != null){
					status = JOB_STATUS_RUNNING;
					if(job.getEndDate() != null){
						status = JOB_STATUS_FINISHED;
					}
				}
				job.setStatus(status);
			}
			int totalNumLinks = 0;
			//Get subjobs of this job and their information in the DDBB.
			sql = "SELECT * FROM linksdiscovery_subjob_instances WHERE job_id=" + jobId;
			resultSet = sta.executeQuery(sql);
			while (resultSet.next()) {
				final Subjob subjob = new Subjob();
				subjob.setId(resultSet.getInt("subjob_id"));
				subjob.setName(resultSet.getString("name"));
				subjob.setStartDate(resultSet.getTimestamp("start_date"));
				subjob.setEndDate(resultSet.getTimestamp("end_date"));
				//Determine subjob status
				String status = JOB_STATUS_IDLE;
				if(subjob.getStartDate() != null){
					status = JOB_STATUS_RUNNING;
					if(subjob.getEndDate() != null){
						status = JOB_STATUS_FINISHED;
					}
				}
				subjob.setStatus(status);
				subjob.setNumLinks(resultSet.getInt("num_links"));
				//Add subjob to job structure
				job.addSubjob(subjob);
				//Compute total number of links generated by job
				totalNumLinks = totalNumLinks + subjob.getNumLinks();
			}
			//Set total number of links generated
			job.setNumLinks(totalNumLinks);
			resultSet.close();
			sta.close();
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			return null;
		}
		return job;
	}
}
