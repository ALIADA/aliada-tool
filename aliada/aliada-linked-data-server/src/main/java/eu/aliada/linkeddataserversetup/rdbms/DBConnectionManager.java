// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-linked-data-server
// Responsible: ALIADA Consortium
package eu.aliada.linkeddataserversetup.rdbms;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import eu.aliada.shared.log.Log;
import eu.aliada.linkeddataserversetup.log.MessageCatalog;
import eu.aliada.linkeddataserversetup.model.Job;
import eu.aliada.linkeddataserversetup.model.JobConfiguration;
import eu.aliada.linkeddataserversetup.model.Subset;

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
		getNewConnection();
	}

	/**
	 * Returns a new DDBB connection.
	 *
	 * @return	the new DDBB connection.
	 * @since 2.0
	 */
	public void getNewConnection() {
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
		try {
			//Check first if the DB connection is still valid
			if(!this.conn.isValid(1)){
				LOGGER.debug(MessageCatalog._00026_GET_NEW_DB_CONNECTION);
				getNewConnection();
			}
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
		}
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
		JobConfiguration jobConf = null;
		int datasetId = -1;
		int organisationId = -1;
		try {
			//Get general information 
			final Statement sta = getConnection().createStatement();
			String sql = "SELECT * FROM linkeddataserver_job_instances WHERE job_id=" + jobId;
			ResultSet resultSet = sta.executeQuery(sql);
			jobConf = new JobConfiguration();
			while (resultSet.next()) {
				jobConf.setId(jobId);
				jobConf.setStoreIp(resultSet.getString("store_ip"));
				jobConf.setStoreSqlPort(resultSet.getInt("store_sql_port"));
				jobConf.setSqlLogin(resultSet.getString("sql_login"));
				jobConf.setSqlPassword(resultSet.getString("sql_password"));
				jobConf.setIsqlCommandPath(resultSet.getString("isql_command_path"));
				jobConf.setIsqlCommandsDatasetFilenameDefault(resultSet.getString("isql_commands_file_dataset_default"));
				jobConf.setIsqlCommandsSubsetFilenameDefault(resultSet.getString("isql_commands_file_subset_default"));
				jobConf.setVirtHttpServRoot(resultSet.getString("virtuoso_http_server_root"));
				jobConf.setOntologyUri(resultSet.getString("aliada_ontology"));
				datasetId = resultSet.getInt("datasetId");
				jobConf.setDatasetId(datasetId);
				organisationId = resultSet.getInt("organisationId");
				jobConf.setTmpDir(resultSet.getString("tmp_dir"));
			}
			resultSet.close();
			//Get dataset related information 
			sql = "SELECT * FROM dataset WHERE datasetId=" + datasetId;
			resultSet = sta.executeQuery(sql);
			while (resultSet.next()) {
				jobConf.setDatasetDesc(resultSet.getString("dataset_desc"));
				jobConf.setDatasetLongDesc(resultSet.getString("dataset_long_desc"));
				jobConf.setPublicSparqlEndpointUri(resultSet.getString("public_sparql_endpoint_uri"));
				jobConf.setSparqlEndpointUri(resultSet.getString("sparql_endpoint_uri"));
				jobConf.setSparqlLogin(resultSet.getString("sparql_endpoint_login"));
				jobConf.setSparqlPassword(resultSet.getString("sparql_endpoint_password"));
				jobConf.setDomainName(resultSet.getString("domain_name"));
				jobConf.setUriIdPart(resultSet.getString("uri_id_part"));
				jobConf.setUriDocPart(resultSet.getString("uri_doc_part"));
				jobConf.setUriDefPart(resultSet.getString("uri_def_part"));
				jobConf.setUriConceptPart(resultSet.getString("uri_concept_part"));
				jobConf.setUriSetPart(resultSet.getString("uri_set_part"));
				jobConf.setListeningHost(resultSet.getString("listening_host"));
				jobConf.setVirtualHost(resultSet.getString("virtual_host"));
				jobConf.setIsqlCommandsDatasetFilename(resultSet.getString("isql_commands_file_dataset"));
				jobConf.setDatasetAuthor(resultSet.getString("dataset_author"));
				jobConf.setDatasetSourceURL(resultSet.getString("dataset_source_url"));
				jobConf.setLicenseURL(resultSet.getString("license_url"));
			}
			resultSet.close();
			//Get subsets related information 
			sql = "SELECT * FROM subset WHERE datasetId=" + datasetId;
			resultSet = sta.executeQuery(sql);
			while (resultSet.next()) {
				final Subset subset = new Subset();
				subset.setUriConceptPart(resultSet.getString("uri_concept_part"));
				subset.setDescription(resultSet.getString("subset_desc"));
				subset.setIsqlCommandsSubsetFilename(resultSet.getString("isql_commands_file_subset"));
				subset.setGraph(resultSet.getString("graph_uri"));
				subset.setLinksGraph(resultSet.getString("links_graph_uri"));
				jobConf.setSubset(subset);
			}
			resultSet.close();
			//Get organisation name and LOGO from BLOB object in organisation table 
			sql = "SELECT org_name, org_logo FROM organisation WHERE organisationId=" + organisationId;
			resultSet = sta.executeQuery(sql);
            if (resultSet.next()) {
            	jobConf.setOrgName(resultSet.getString("org_name"));
            	if (resultSet.getBlob("org_logo") != null) {
            		final Blob logo = resultSet.getBlob("org_logo");
            		final int blobLength = (int) logo.length();
            		byte[] blobAsBytes = null;
            		blobAsBytes = logo.getBytes(1, blobLength);
            		//Compose initial logo file name 
            		final String orgImagePathInit = jobConf.getTmpDir() + File.separator + "orgLogo"  + "_" + System.currentTimeMillis()  + ".jpeg";
            		try {
            			final FileOutputStream fos = new FileOutputStream(orgImagePathInit);
            			fos.write(blobAsBytes);
            			fos.close();
            			jobConf.setOrgImagePath(orgImagePathInit);
            		} catch (IOException exception) {
            			LOGGER.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, orgImagePathInit);
            		}
            		//release the blob and free up memory. (since JDBC 4.0)
            		logo.free();
            	}
            }
            resultSet.close();
            sta.close();
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			jobConf = null;
		}
		return jobConf;
	}
	
	/**
	 * Updates the start_date of the job.
	 *
	 * @param jobId	the job identification.
	 * @return true if the date has been updated correctly in the DDBB. 
	 *         False otherwise.
	 * @since 1.0
	 */
	public boolean updateJobStartDate(final int jobId){
		//Update start_date of job
		boolean updated = false;
    	try {
    		PreparedStatement preparedStatement = null;		
    		preparedStatement = getConnection().prepareStatement("UPDATE linkeddataserver_job_instances SET start_date = ? WHERE job_id = ?");
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
    		preparedStatement = getConnection().prepareStatement("UPDATE linkeddataserver_job_instances SET end_date = ? WHERE job_id = ?");
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
	 * Updates dataset web page root.
	 *
	 * @param jobId			the job identification.
	 * @param webPageRoot	the dataset web page root.
	 * @return true if the dataset web page root has been updated correctly in the DDBB.
	 *         False otherwise.
	 * @since 1.0
	 */
	public boolean updateDatasetWebPageRoot(final int datasetId, final String webPageRoot){
		//Update end_date of job
		boolean updated = false;
    	try {
    		PreparedStatement preparedStatement = null;		
    		preparedStatement = getConnection().prepareStatement("UPDATE dataset SET dataset_web_page_root = ? WHERE datasetId = ?");
    		// (dataset_web_page_root, job_id)
    		// parameters start with 1
    		preparedStatement.setString(1, webPageRoot);
    		preparedStatement.setInt(2, datasetId);
    		preparedStatement.executeUpdate();
    		updated = true;
		} catch (SQLException exception) {
			LOGGER.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			updated = false;
		}
    	return updated;
	}

	/**
	 * Returns job information in the DDBB.
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
			final Statement sta = getConnection().createStatement();
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
