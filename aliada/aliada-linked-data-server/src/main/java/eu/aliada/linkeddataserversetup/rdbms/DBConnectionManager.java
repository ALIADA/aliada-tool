// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-linked-data-server
// Responsible: ALIADA Consortium
package eu.aliada.linkeddataserversetup.rdbms;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import eu.aliada.shared.log.Log;
import eu.aliada.linkeddataserversetup.log.MessageCatalog;
import eu.aliada.linkeddataserversetup.model.Job;
import eu.aliada.linkeddataserversetup.model.JobConfiguration;

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
	 * @return	the {@link eu.aliada.linkeddataserversetup.model.JobConfiguration}
	 * 			which contains the configuration of the job.
	 * @since 1.0
	 */
	public JobConfiguration getJobConfiguration(final Integer jobId) {
		JobConfiguration job = null;
		try {
			final Statement sta = conn.createStatement();
			final String sql = "SELECT * FROM linkeddataserver_job_instances WHERE job_id=" + jobId;
			final ResultSet resultSet = sta.executeQuery(sql);
			job = new JobConfiguration();
			while (resultSet.next()) {
				job.setId(jobId);
				job.setStoreIp(resultSet.getString("store_ip"));
				job.setStoreSqlPort(resultSet.getInt("store_sql_port"));
				job.setSqlLogin(resultSet.getString("sql_login"));
				job.setSqlPassword(resultSet.getString("sql_password"));
				job.setGraph(resultSet.getString("graph"));
				job.setDatasetBase(resultSet.getString("dataset_base"));
				job.setIsqlCommandPath(resultSet.getString("isql_command_path"));
				job.setIsqlCommandsFilename(resultSet.getString("isql_commands_file"));
				job.setIsqlCommandsFilenameDefault(resultSet.getString("isql_commands_file_default"));
		    }
			resultSet.close();
			sta.close();
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			job = null;
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
		boolean updated = false;
    	try {
    		PreparedStatement preparedStatement = null;		
    		preparedStatement = conn.prepareStatement("UPDATE linkeddataserver_job_instances SET start_date = ? WHERE job_id = ?");
    		// (job_id, start_date)
    		// parameters start with 1
    		final java.util.Date today = new java.util.Date();
    		final java.sql.Timestamp todaySQL = new java.sql.Timestamp(today.getTime());
    		preparedStatement.setTimestamp(1, todaySQL);
    		preparedStatement.setInt(2, jobId);
    		preparedStatement.executeUpdate();
    		updated = true;
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			updated = false;
		}
  		return updated;
	}

	/**
	 * Updates the end_date of the job.
	 *
	 * @param jobId	the job identification.
	 * @return true if the date has been updated correctly in the DDBB.
	 *         False otherwise.
	 * @since 1.0
	 */
	public boolean updateJobEndDate(final int jobId){
		//Update end_date of job
		boolean updated = false;
    	try {
    		PreparedStatement preparedStatement = null;		
    		preparedStatement = conn.prepareStatement("UPDATE linkeddataserver_job_instances SET end_date = ? WHERE job_id = ?");
    		// (job_id, end_date)
    		// parameters start with 1
    		final java.util.Date today = new java.util.Date();
    		final java.sql.Timestamp todaySQL = new java.sql.Timestamp(today.getTime());
    		preparedStatement.setTimestamp(1, todaySQL);
    		preparedStatement.setInt(2, jobId);
    		preparedStatement.executeUpdate();
    		updated = true;
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			updated = false;
		}
    	return updated;
	}

	/**
	 * Returns job and its corresponding subjobs information in the DDBB.
	 *
	 * @param jobId	the job identification.
	 * @return	the {@link eu.aliada.linkeddataserversetup.model.Job}
	 * 			which contains the job information.
	 * @since 1.0
	 */
	public Job getJob(final int jobId) {
		//Get the job information from the DDBB
		Job job = new Job();
		job.setId(jobId);
		try {
			final Statement sta = conn.createStatement();
			final String sql = "SELECT * FROM linkeddataserver_job_instances WHERE job_id=" + jobId;
			final ResultSet resultSet = sta.executeQuery(sql);
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
			resultSet.close();
			sta.close();
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			job = null;
		}
		return job;
	}
}
