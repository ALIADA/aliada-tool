// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.rdbms;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;

import eu.aliada.ckancreation.log.MessageCatalog;
import eu.aliada.ckancreation.model.Job;
import eu.aliada.ckancreation.model.JobConfiguration;
import eu.aliada.ckancreation.model.Subset;
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
	 * @return	the {@link eu.aliada.ckancreation.model.JobConfiguration}
	 * 			which contains the configuration of the job.
	 * @since 2.0
	 */
	public JobConfiguration getJobConfiguration(final Integer jobId) {
		JobConfiguration jobConf = null;
		int datasetId = -1;
		int organisationId = -1;
		try {
			final Statement sta = getConnection().createStatement();
			String sql = "SELECT * FROM ckancreation_job_instances WHERE job_id=" + jobId;
			ResultSet resultSet = sta.executeQuery(sql);
			jobConf = new JobConfiguration();
			while (resultSet.next()) {
				jobConf = new JobConfiguration();
				jobConf.setId(jobId);
				jobConf.setCkanApiURL(resultSet.getString("ckan_api_url"));
				jobConf.setCkanApiKey(resultSet.getString("ckan_api_key"));
				jobConf.setTmpDir(resultSet.getString("tmp_dir"));
				jobConf.setStoreIp(resultSet.getString("store_ip"));
				jobConf.setStoreSqlPort(resultSet.getInt("store_sql_port"));
				jobConf.setSqlLogin(resultSet.getString("sql_login"));
				jobConf.setSqlPassword(resultSet.getString("sql_password"));
				jobConf.setIsqlCommandPath(resultSet.getString("isql_command_path"));
				jobConf.setVirtHttpServRoot(resultSet.getString("virtuoso_http_server_root"));
				jobConf.setOntologyUri(resultSet.getString("aliada_ontology"));
				jobConf.setOrgName(resultSet.getString("org_name"));
				jobConf.setOrgTitle(resultSet.getString("org_name").toUpperCase());
				jobConf.setOrgDescription(resultSet.getString("org_description"));
				jobConf.setOrgHomePage(resultSet.getString("org_home_page"));
				jobConf.setOrgImageURL(resultSet.getString("org_image_url"));
				datasetId = resultSet.getInt("datasetId");
				organisationId = resultSet.getInt("organisationId");
		    }
			resultSet.close();
			//Get dataset related information 
			sql = "SELECT * FROM dataset WHERE datasetId=" + datasetId;
			resultSet = sta.executeQuery(sql);
			while (resultSet.next()) {
				jobConf.setDatasetAuthor(resultSet.getString("dataset_author"));
				jobConf.setCkanDatasetName(resultSet.getString("ckan_dataset_name"));
				jobConf.setDatasetDesc(resultSet.getString("dataset_desc"));
				jobConf.setDatasetLongDesc(resultSet.getString("dataset_long_desc"));
				jobConf.setDatasetSourceURL(resultSet.getString("dataset_source_url"));
				jobConf.setPublicSparqlEndpointUri(resultSet.getString("public_sparql_endpoint_uri"));
				jobConf.setLicenseCKANId(resultSet.getString("license_ckan_id"));
				jobConf.setLicenseURL(resultSet.getString("license_url"));
				jobConf.setDomainName(resultSet.getString("domain_name"));
				jobConf.setVirtualHost(resultSet.getString("virtual_host"));
				jobConf.setUriDocPart(resultSet.getString("uri_doc_part"));
				jobConf.setUriConceptPart(resultSet.getString("uri_concept_part"));
				jobConf.setUriSetPart(resultSet.getString("uri_set_part"));
			}
			resultSet.close();
			//Get subsets related information 
			sql = "SELECT * FROM subset WHERE datasetId=" + datasetId;
			resultSet = sta.executeQuery(sql);
			while (resultSet.next()) {
				Subset subset = new Subset();
				subset.setUriConceptPart(resultSet.getString("uri_concept_part"));
				subset.setDescription(resultSet.getString("subset_desc"));
				subset.setGraph(resultSet.getString("graph_uri"));
				subset.setLinksGraph(resultSet.getString("links_graph_uri"));
				subset.setDescription(resultSet.getString("subset_desc"));
				jobConf.setSubset(subset);
			}
			resultSet.close();
			//Get organisation LOGO from BLOB object in organisation table 
			sql = "SELECT organisation_logo FROM organisation WHERE organisationId=" + organisationId;
			resultSet = sta.executeQuery(sql);
            if (resultSet.next() && resultSet.getBlob("organisation_logo") != null) {
                final Blob logo = resultSet.getBlob("organisation_logo");
                final int blobLength = (int) logo.length();
                byte[] blobAsBytes = null;
                blobAsBytes = logo.getBytes(1, blobLength);
            	//Compose initial logo file name 
            	final String orgImagePathInit = jobConf.getTmpDir() + File.separator + "orgLogo"  + "_" + System.currentTimeMillis()  + ".jpeg";
                try {
	                FileOutputStream fos = new FileOutputStream(orgImagePathInit);
	                fos.write(blobAsBytes);
	                fos.close();
	                jobConf.setOrgImagePath(orgImagePathInit);
                } catch (IOException exception) {
        			LOGGER.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, orgImagePathInit);
                }
                //release the blob and free up memory. (since JDBC 4.0)
                logo.free();
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
    		preparedStatement = getConnection().prepareStatement("UPDATE ckancreation_job_instances SET ckan_org_url = ? WHERE job_id = ?");
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
    		preparedStatement = getConnection().prepareStatement("UPDATE ckancreation_job_instances SET ckan_dataset_url = ? WHERE job_id = ?");
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
	 * Updates the start_date of the job.
	 *
	 * @param jobId	the job identification.
	 * @return true if the date has been updated correctly in the DDBB. False otherwise.
	 * @since 2.0
	 */
	public boolean updateJobStartDate(final int jobId){
    	try {
    		PreparedStatement preparedStatement = null;		
    		preparedStatement = getConnection().prepareStatement("UPDATE ckancreation_job_instances SET start_date = ? WHERE job_id = ?");
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
    		preparedStatement = getConnection().prepareStatement("UPDATE ckancreation_job_instances SET end_date = ? WHERE job_id = ?");
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
			final Statement sta = getConnection().createStatement();
			String sql = "SELECT * FROM ckancreation_job_instances WHERE job_id=" + jobId;
			ResultSet resultSet = sta.executeQuery(sql);
			while (resultSet.next()) {
				job.setStartDate(resultSet.getTimestamp("start_date"));
				job.setEndDate(resultSet.getTimestamp("end_date"));
				job.setCkanOrgURL(resultSet.getString("ckan_org_url"));
				job.setCkanDatasetURL(resultSet.getString("ckan_dataset_url"));
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
