// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-linked-data-server
// Responsible: ALIADA Consortium
package eu.aliada.linkedDataServerSetup.rdbms;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import eu.aliada.linkedDataServerSetup.log.MessageCatalog;
import eu.aliada.shared.log.Log;
import eu.aliada.linkedDataServerSetup.model.JobConfiguration;
import eu.aliada.linkedDataServerSetup.model.Job;

/**
 * DDBB connection manager. 
 * It contains all the related functions with the DDBB: open, close connection,
 * execute SQL-s.
 *  
 * @author Idoia Murua
 * @since 1.0
 */
public class DBConnectionManager {
	private final Log logger = new Log(DBConnectionManager.class);
	/* Job and subjob possible states */
	private final static String job_status_idle = "idle"; 
	private final static String job_status_running = "running"; 
	private final static String job_status_finished = "finished"; 

	private Connection conn = null;
	 
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
			logger.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
		} catch (SQLException exception) {
			logger.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
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
			logger.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
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
	 * @return	the {@link eu.aliada.linkedDataServerSetup.model.JobConfiguration}
	 * 			which contains the configuration of the job.
	 * @since 1.0
	 */
	public JobConfiguration getJobConfiguration(Integer jobId) {
		JobConfiguration job = null;
		try {
			Statement sta = conn.createStatement();
			String sql = "SELECT * FROM linkeddataserver_job_instances WHERE job_id=" + jobId;
			ResultSet resultSet = sta.executeQuery(sql);
			while (resultSet.next()) {
				job = new JobConfiguration();
				job.setId(jobId);
				job.setStoreIp(resultSet.getString("store_ip"));
				job.setStoreSqlPort(resultSet.getInt("store_sql_port"));
				job.setSqlLogin(resultSet.getString("sql_login"));
				job.setSqlPassword(resultSet.getString("sql_password"));
				job.setGraph(resultSet.getString("graph"));
				job.setDatasetBase(resultSet.getString("dataset_base"));
				job.setUriMappingfile(resultSet.getString("uri_mapping_file"));
				job.setIsqlCommandPath(resultSet.getString("isql_command_path"));
				job.setTmpDir(resultSet.getString("tmp_dir"));
		    }
		} catch (SQLException exception) {
			logger.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
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
	public boolean updateJobStartDate(int jobId){
		//Update start_date of job
    	try {
    		PreparedStatement preparedStatement = null;		
    		preparedStatement = conn.prepareStatement("UPDATE linkeddataserver_job_instances SET start_date = ? WHERE job_id = ?");
    		// (job_id, start_date)
    		// parameters start with 1
    		java.util.Date today = new java.util.Date();
    		java.sql.Timestamp todaySQL = new java.sql.Timestamp(today.getTime());
    		preparedStatement.setTimestamp(1, todaySQL);
    		preparedStatement.setInt(2, jobId);
    		preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			logger.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
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
	public boolean updateJobEndDate(int jobId){
		//Update end_date of job
    	try {
    		PreparedStatement preparedStatement = null;		
    		preparedStatement = conn.prepareStatement("UPDATE linkeddataserver_job_instances SET end_date = ? WHERE job_id = ?");
    		// (job_id, end_date)
    		// parameters start with 1
    		java.util.Date today = new java.util.Date();
    		java.sql.Timestamp todaySQL = new java.sql.Timestamp(today.getTime());
    		preparedStatement.setTimestamp(1, todaySQL);
    		preparedStatement.setInt(2, jobId);
    		preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			logger.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			return false;
		}
    	return true;
	}

	/**
	 * Returns job and its corresponding subjobs information in the DDBB.
	 *
	 * @param jobId	the job identification.
	 * @return	the {@link eu.aliada.linkedDataServerSetup.model.Job}
	 * 			which contains the job information.
	 * @since 1.0
	 */
	public Job getJob(int jobId) {
		//Get the job information from the DDBB
		Job job = new Job();
		job.setId(jobId);
		try {
			Statement sta = conn.createStatement();
			String sql = "SELECT * FROM linkeddataserver_job_instances WHERE job_id=" + jobId;
			ResultSet resultSet = sta.executeQuery(sql);
			while (resultSet.next()) {
				job.setStartDate(resultSet.getTimestamp("start_date"));
				job.setEndDate(resultSet.getTimestamp("end_date"));
				//Determine job status
				String status = job_status_idle;
				if(job.getStartDate() != null){
					status = job_status_running;
					if(job.getEndDate() != null){
						status = job_status_finished;
					}
				}
				job.setStatus(status);
			}
		} catch (SQLException exception) {
			logger.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			return null;
		}
		return job;
	}
}
