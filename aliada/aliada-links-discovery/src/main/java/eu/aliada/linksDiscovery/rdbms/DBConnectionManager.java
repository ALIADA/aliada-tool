package eu.aliada.linksDiscovery.rdbms;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import eu.aliada.linksDiscovery.impl.LinksDiscovery;
import eu.aliada.linksDiscovery.model.JobConfiguration;
import eu.aliada.linksDiscovery.model.SubjobConfiguration;
import eu.aliada.linksDiscovery.model.Job;
import eu.aliada.linksDiscovery.model.Subjob;
import eu.aliada.linksDiscovery.log.MessageCatalog;
import eu.aliada.shared.log.Log;

public class DBConnectionManager {
	private final Log logger = new Log(LinksDiscovery.class);
	private final static String job_status_idle = "idle"; 
	private final static String job_status_running = "running"; 
	private final static String job_status_finished = "finished"; 

	private Connection conn = null;
	 
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

	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException exception) {
			logger.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
		}
	}
	
	public Connection getConnection() {
		return this.conn;
	}
 
	public JobConfiguration getJobConfiguration(Integer id) {
		JobConfiguration job = null;
		try {
			Statement sta = conn.createStatement();
			String sql = "SELECT * FROM linksdiscovery_job_instances WHERE job_id=" + id;
			ResultSet resultSet = sta.executeQuery(sql);
			while (resultSet.next()) {
				job = new JobConfiguration();
				job.setId(id);
				job.setInputURI(resultSet.getString("input_uri"));
				job.setInputLogin(resultSet.getString("input_login"));
				job.setInputPassword(resultSet.getString("input_password"));
				job.setInputGraph(resultSet.getString("input_graph"));
				job.setOutputURI(resultSet.getString("output_uri"));
				job.setOutputLogin(resultSet.getString("output_login"));
				job.setOutputPassword(resultSet.getString("output_password"));
				job.setOutputGraph(resultSet.getString("output_graph"));
				job.setConfigFile(resultSet.getString("config_file"));
				job.setTmpDir(resultSet.getString("tmp_dir"));
		    }
		} catch (SQLException exception) {
			logger.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			return null;
		}
		return job;
	}
	
	public boolean updateJobStartDate(int job){
		//Update start_date of job
    	try {
			PreparedStatement preparedStatement = null;		
		    preparedStatement = conn.prepareStatement("UPDATE linksdiscovery_job_instances SET start_date = ? WHERE job_id = ?");
		      // (job_id, start_date)
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
  		return true;
	}

	public boolean updateJobEndDate(int job){
		//Update end_date of job
    	try {
			PreparedStatement preparedStatement = null;		
		    preparedStatement = conn.prepareStatement("UPDATE linksdiscovery_job_instances SET end_date = ? WHERE job_id = ?");
		      // (job_id, end_date)
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
  		return true;
	}

	public boolean insertSubjobToDDBB(int job, int subjob, SubjobConfiguration subjobConf, String linkingXMLConfigFilename, String tmpDir){
    	try {
			PreparedStatement preparedStatement = null;		
		    preparedStatement = conn.prepareStatement("INSERT INTO  linksdiscovery_subjob_instances VALUES (?, ?, ?, ?, ?, ?, ?, default, default, default)");
		      // (job_id, subjob_id, name, config_file, num_threads, reload, tmp_dir, num_links, start_date, end_date)
		      // parameters start with 1
		      preparedStatement.setInt(1, job);
		      preparedStatement.setInt(2, subjob);
		      preparedStatement.setString(3, subjobConf.getName());
		      preparedStatement.setString(4, linkingXMLConfigFilename);
		      preparedStatement.setInt(5, subjobConf.geLinkingNumThreads());
		      preparedStatement.setBoolean(6, subjobConf.getLinkingReload());
		      preparedStatement.setString(7, tmpDir);
		      preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			logger.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
    		return false;
		}
  		return true;
	}
	
	public Job getJob(int id) {
		//Returns job and its corresponding subjobs info in DDBB
		Job job = new Job();
		job.setId(id);
		try {
			Statement sta = conn.createStatement();
			String sql = "SELECT * FROM linksdiscovery_job_instances WHERE job_id=" + id;
			ResultSet resultSet = sta.executeQuery(sql);
			while (resultSet.next()) {
				job.setStartDate(resultSet.getDate("start_date"));
				job.setEndDate(resultSet.getDate("end_date"));
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
			int totalNumLinks = 0;
			//Get subjobs of this job
			sql = "SELECT * FROM linksdiscovery_subjob_instances WHERE job_id=" + id;
			resultSet = sta.executeQuery(sql);
			while (resultSet.next()) {
				Subjob subjob = new Subjob();
				subjob.setId(resultSet.getInt("subjob_id"));
				subjob.setName(resultSet.getString("name"));
				subjob.setStartDate(resultSet.getDate("start_date"));
				subjob.setEndDate(resultSet.getDate("end_date"));
				//Determine subjob status
				String status = job_status_idle;
				if(subjob.getStartDate() != null){
					status = job_status_running;
					if(subjob.getEndDate() != null){
						status = job_status_finished;
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
		} catch (SQLException exception) {
			logger.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			return null;
		}
		return job;
	}
}
