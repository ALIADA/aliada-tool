// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.rdbms;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import eu.aliada.ckancreation.log.MessageCatalog;
import eu.aliada.ckancreation.model.Job;
import eu.aliada.ckancreation.model.JobConfiguration;
import eu.aliada.shared.log.Log;

/**
 * DDBB connection manager. 
 * It contains all the related functions with the DDBB: open, close connection,
 * execute SQL-s.
 *  
 * @author Idoia Murua
 * @since 2.0
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
	 * @since 2.0
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
	 * @since 2.0
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
	 * @since 2.0
	 */
	public Connection getConnection() {
		return this.conn;
	}
 
	/**
	 * Gets the job configuration from the DDBB.
	 *
	 * @param jobId	the job identification.
	 * @return	the {@link eu.aliada.ckancreation.model.JobConfiguration}
	 * 			which contains the configuration of the job.
	 * @since 2.0
	 */
	public JobConfiguration getJobConfiguration(final Integer jobId) {
		JobConfiguration jobConf = null;
		try {
			final Statement sta = conn.createStatement();
			final String sql = "SELECT * FROM ckancreation_job_instances WHERE job_id=" + jobId;
			final ResultSet resultSet = sta.executeQuery(sql);
			while (resultSet.next()) {
				jobConf = new JobConfiguration();
				jobConf.setId(jobId);
				jobConf.setStoreIp(resultSet.getString("store_ip"));
				jobConf.setStoreSqlPort(resultSet.getInt("store_sql_port"));
				jobConf.setSqlLogin(resultSet.getString("sql_login"));
				jobConf.setSqlPassword(resultSet.getString("sql_password"));
				jobConf.setIsqlCommandPath(resultSet.getString("isql_command_path"));
				jobConf.setDumpPath(resultSet.getString("dump_path"));
				jobConf.setDumpUrl(resultSet.getString("dump_url"));
				jobConf.setCkanApiURL(resultSet.getString("ckan_api_url"));
				jobConf.setCkanApiKey(resultSet.getString("ckan_api_key"));
				jobConf.setOrgTitle(resultSet.getString("org_title"));
				jobConf.setOrgName(resultSet.getString("org_name"));
				jobConf.setOrgDescription(resultSet.getString("org_description"));
				jobConf.setOrgHomePage(resultSet.getString("org_home_page"));
				jobConf.setOrgImageURL(resultSet.getString("org_image_url"));
				jobConf.setDatasetAuthor(resultSet.getString("dataset_author"));
				jobConf.setDatasetName(resultSet.getString("dataset_name"));
				jobConf.setDatasetNotes(resultSet.getString("dataset_notes"));
				jobConf.setDatasetSourceURL(resultSet.getString("dataset_source_url"));
				jobConf.setSPARQLEndpoint(resultSet.getString("sparql_endpoint"));
				jobConf.setLicenseId(resultSet.getString("license_id"));
				jobConf.setGraphURI(resultSet.getString("graph_uri"));
		    }
			resultSet.close();
			sta.close();
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			return null;
		}
		return jobConf;
	}
	
	/**
	 * Updates the ckan_org_url of the job.
	 *
	 * @param jobId			the job identification.
	 * @param ckanOrgUrl	the URL of the organization in CKAN.
	 * @return true if the URL has been updated correctly in the DDBB. False otherwise.
	 * @since 2.0
	 */
	public boolean updateCkanOrgUrl(final int jobId, final String ckanOrgUrl){
    	try {
    		PreparedStatement preparedStatement = null;		
    		preparedStatement = conn.prepareStatement("UPDATE ckancreation_job_instances SET ckan_org_url = ? WHERE job_id = ?");
    		// (job_id, ckan_org_url)
    		// parameters start with 1
    		preparedStatement.setString(1, ckanOrgUrl);
    		preparedStatement.setInt(2, jobId);
    		preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			return false;
		}
  		return true;
	}

	/**
	 * Updates the ckan_dataset_url of the job.
	 *
	 * @param jobId				the job identification.
	 * @param ckanDatasetUrl	the URL of the dataset in CKAN.
	 * @return true if the URL has been updated correctly in the DDBB. False otherwise.
	 * @since 2.0
	 */
	public boolean updateCkanDatasetUrl(final int jobId, final String ckanDatasetUrl){
    	try {
    		PreparedStatement preparedStatement = null;		
    		preparedStatement = conn.prepareStatement("UPDATE ckancreation_job_instances SET ckan_dataset_url = ? WHERE job_id = ?");
    		// (job_id, ckan_org_url)
    		// parameters start with 1
    		preparedStatement.setString(1, ckanDatasetUrl);
    		preparedStatement.setInt(2, jobId);
    		preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			return false;
		}
  		return true;
	}

	/**
	 * Updates the Void file related info of the job.
	 *
	 * @param jobId			the job identification.
	 * @param voidFilePath	the Void file path.
	 * @param voidFileUrl	the Void file URL.
	 * @return true if the Void file related info has been updated correctly in the DDBB. False otherwise.
	 * @since 2.0
	 */
	public boolean updateVoidFile(final int jobId, final String voidFilePath, String voidFileUrl){
    	try {
    		PreparedStatement preparedStatement = null;		
    		preparedStatement = conn.prepareStatement("UPDATE ckancreation_job_instances SET void_file_path = ? , void_file_url = ? WHERE job_id = ?");
    		// (job_id, void_file_path)
    		// parameters start with 1
    		preparedStatement.setString(1, voidFilePath);
    		preparedStatement.setString(2, voidFileUrl);
    		preparedStatement.setInt(3, jobId);
    		preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			return false;
		}
  		return true;
	}

	/**
	 * Updates the start_date of the job.
	 *
	 * @param jobId	the job identification.
	 * @return true if the date has been updated correctly in the DDBB. False otherwise.
	 * @since 2.0
	 */
	public boolean updateJobStartDate(final int jobId){
    	try {
    		PreparedStatement preparedStatement = null;		
    		preparedStatement = conn.prepareStatement("UPDATE ckancreation_job_instances SET start_date = ? WHERE job_id = ?");
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
	 * @since 2.0
	 */
	public boolean updateJobEndDate(final int jobId){
    	try {
    		PreparedStatement preparedStatement = null;		
    		preparedStatement = conn.prepareStatement("UPDATE ckancreation_job_instances SET end_date = ? WHERE job_id = ?");
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
	 * Returns job information in the DDBB.
	 *
	 * @param jobId	the job identification.
	 * @return	the {@link eu.aliada.ckancreation.model.Job}
	 * 			which contains the job information.
	 * @since 2.0
	 */
	public Job getJob(final int jobId) {
		//Get the job information from the DDBB
		final Job job = new Job();
		job.setId(jobId);
		try {
			final Statement sta = conn.createStatement();
			String sql = "SELECT * FROM ckancreation_job_instances WHERE job_id=" + jobId;
			ResultSet resultSet = sta.executeQuery(sql);
			while (resultSet.next()) {
				job.setStartDate(resultSet.getTimestamp("start_date"));
				job.setEndDate(resultSet.getTimestamp("end_date"));
				job.setCkanOrgUrl(resultSet.getString("ckan_org_url"));
				job.setCkanDatasetUrl(resultSet.getString("ckan_dataset_url"));
				job.setVoidFilePath(resultSet.getString("void_file_path"));
				job.setVoidFileUrl(resultSet.getString("void_file_url"));
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
			return null;
		}
		return job;
	}

}
