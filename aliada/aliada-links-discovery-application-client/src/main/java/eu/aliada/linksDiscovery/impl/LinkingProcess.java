// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.linksDiscovery.impl;

import eu.aliada.linksDiscovery.log.MessageCatalog;
import eu.aliada.shared.log.Log;
import de.fuberlin.wiwiss.silk.Silk;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Links discovery process via Silk library. 
 * @author Idoia Murua
 * @since 1.0
 */
public class LinkingProcess {
	private final Log logger = new Log(LinkingProcess.class);
	private final static String CRONTAB_FILENAME = "aliada_links_discovery.cron"; 
	//Name of the linking process to program with crontab, that executes eu.aliada.linksDiscovery.impl.LinkingProcess java application
	private final static String LINKING_PROCESS_NAME = "/bin/links-discovery-task-runner.sh";
	private String dbUsername;
	private String dbPassword;
	private String dbDriverClassName;
	private String dbURL;
	private String linkingXMLConfigFilename;
	private File linkingXMLConfigFile;
	private int linkingNumThreads;
	private boolean linkingReload;
	private String tmpDir;
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
		} catch(FileNotFoundException exception) {
			logger.error(MessageCatalog._00031_FILE_NOT_FOUND, propertiesFileName);
			return false;
    	} catch(IOException exception) {
			logger.error(MessageCatalog._00032_BAD_FILE, exception, propertiesFileName);
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

	public boolean getSubjobConfiguration(int jobid, int subjobid) {
		//Get subjob properties from DDBB
		try {
			Statement sta = conn.createStatement();
			String sql = "SELECT * FROM linksdiscovery_subjob_instances WHERE job_id=" + jobid + " AND subjob_id=" + subjobid;
			ResultSet resultSet = sta.executeQuery(sql);
			while (resultSet.next()) {
	    		linkingXMLConfigFilename = resultSet.getString("config_file");
	    		linkingNumThreads = resultSet.getInt("num_threads");
	    		linkingReload = resultSet.getBoolean("reload");
	    		tmpDir = resultSet.getString("tmp_dir");
	    		//Verify that the XML configuration file for SILK really exists
	    		try{
	    			linkingXMLConfigFile = new File(linkingXMLConfigFilename);
	    			if (!linkingXMLConfigFile.exists())
	    				return false;
	    		}catch (Exception exception) {
	    			return false;
	    		}
		    }
		} catch (SQLException exception) {
			logger.error(MessageCatalog._00024_DATA_ACCESS_FAILURE, exception);
			return false;
		}
		return true;
	}
    
	public boolean updateSubjobStartDate(int job, int subjob){
		//Update start_date of subjob
    	try {
			PreparedStatement preparedStatement = null;		
		    preparedStatement = conn.prepareStatement("UPDATE linksdiscovery_subjob_instances SET start_date = ? WHERE job_id = ? AND subjob_id = ?");
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

	public boolean updateSubjobEndDate(int job, int subjob, int numLinks){
		//Update end_date, num_links of subjob
    	try {
			PreparedStatement preparedStatement = null;		
		    preparedStatement = conn.prepareStatement("UPDATE linksdiscovery_subjob_instances SET end_date = ?, num_links = ?  WHERE job_id = ? AND subjob_id = ?");
		      // parameters start with 1
		      java.util.Date today = new java.util.Date();
		      java.sql.Timestamp todaySQL = new java.sql.Timestamp(today.getTime());
		      preparedStatement.setTimestamp(1, todaySQL);
		      preparedStatement.setInt(2, numLinks);
		      preparedStatement.setInt(3, job);
		      preparedStatement.setInt(4, subjob);
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

	public String getTriplesFilename(){
		//Obtain triples generated output file name from XML config file 
		String triplesFilename = "";
		try {
			//Read XML file
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(linkingXMLConfigFile);		 
			doc.getDocumentElement().normalize();
	
			//Get <Output> nodes
			NodeList outputList = doc.getElementsByTagName("Output");
			for (int outputInd = 0; outputInd < outputList.getLength(); outputInd++) {
				Node outputNode = outputList.item(outputInd);
				if (outputNode.getNodeType() == Node.ELEMENT_NODE) {
					Element outputElem = (Element) outputNode;
					String typeValue = outputElem.getAttribute("type");
					//Check if <Output type="file"> 
					if(typeValue.equalsIgnoreCase("file")){
						//Get <Param> nodes
						NodeList paramList = outputElem.getElementsByTagName("Param");
						for (int paramInd = 0; paramInd < paramList.getLength(); paramInd++) {
							Node paramNode = paramList.item(paramInd);
							if (paramNode.getNodeType() == Node.ELEMENT_NODE) {
								Element paramElem = (Element) paramNode;
								String nameValue = paramElem.getAttribute("name");
								//Check if <Param name="file" ...>
								if(nameValue.equalsIgnoreCase("file")){
									//Get the name of the file
									triplesFilename = paramElem.getAttribute("value");
									return triplesFilename;
								}
							}
						}
					}

				}
			}
	
		} catch (ParserConfigurationException exception) {
			logger.error(MessageCatalog._00062_XML_FILE_PARSING_FAILURE, exception, linkingXMLConfigFilename);
		} catch (SAXException exception) {
			logger.error(MessageCatalog._00062_XML_FILE_PARSING_FAILURE, exception, linkingXMLConfigFilename);
		} catch (IOException exception) {
			logger.error(MessageCatalog._00062_XML_FILE_PARSING_FAILURE, exception, linkingXMLConfigFilename);
		}
		
		return triplesFilename;
	}
	public int checkNumLinks(int job, int subjob){
		int numLinks = 0;
		String triplesFilename = getTriplesFilename();
		try{
			BufferedReader br = new BufferedReader(new FileReader(triplesFilename));
			String line;
			while ((line = br.readLine()) != null) {
			   numLinks++;
			}
			logger.info(MessageCatalog._00061_NUM_GENERATED_LINKS, numLinks);
			br.close();
		} catch(FileNotFoundException exception) {
			logger.error(MessageCatalog._00031_FILE_NOT_FOUND, triplesFilename);
			return 0;
	   	} catch(IOException exception) {
			logger.error(MessageCatalog._00032_BAD_FILE, exception, triplesFilename);
			return 0;
	    }
		return numLinks;
	}

	public String removeSubjobFromCrontab(int job, int subjob, String propertiesFileName){
		//String to supress from crontab file
		String crontabLineToSearch = String.format("%s %d %d %s", LINKING_PROCESS_NAME, job, subjob, propertiesFileName);
		//Create a new crontab file supressing the subjob already finished
		String crontabFilename = tmpDir + File.separator + CRONTAB_FILENAME;
		//Replace Windows file separator by "/" Java file separator
		crontabFilename = crontabFilename.replace("\\", "/");
		//Remove the crontab file if it already exists
	    File f = new File(crontabFilename);
	    if (f.exists())
	    	f.delete();
	    //Now, create a new one
		try {
			FileWriter fstream = new FileWriter(crontabFilename);
			BufferedWriter out = new BufferedWriter(fstream);
			// Execute system command "crontab -l"
	    	String command = "crontab -l";
	    	try {
		    	String s = null;
	  	    	Process crontabList = Runtime.getRuntime().exec(command);
		    	BufferedReader stdInput = new BufferedReader(new InputStreamReader(crontabList.getInputStream()));
		        while ((s = stdInput.readLine()) != null) {
		        	if (s.indexOf(crontabLineToSearch) < 0){
		        		//If it is not the subjob already finished, include the line again in crontab file
		        		out.write(s);
		        		out.newLine();
		        	}
	            }
		    } catch (IOException exception) {
		    	crontabFilename = null;
				logger.error(MessageCatalog._00033_EXTERNAL_PROCESS_START_FAILURE, exception, command);
		    }
			out.close();
		} catch (IOException exception) {
			logger.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, crontabFilename);
	    	crontabFilename = null;
		}
		// Execute system command "crontab contrabfilename"
    	String command = "crontab " + crontabFilename;
	    try {
			logger.info(MessageCatalog._00040_EXECUTING_CRONTAB);
	    	Runtime.getRuntime().exec(command);
	    } catch (IOException exception) {
			logger.error(MessageCatalog._00033_EXTERNAL_PROCESS_START_FAILURE, exception, command);
	    }
		return crontabFilename;
	}

	public void removeConfigFiles(String propertiesFileName, String linkingXMLConfigFilename){
		//Remove configuration files
		try {
		    File f = new File(propertiesFileName);
		    if (f.exists())
		    	f.delete();
		    f = new File(linkingXMLConfigFilename);
		    if (f.exists())
		    	f.delete();
		} catch (Exception exception) {
			logger.error(MessageCatalog._00066_FILE_REMOVING_FAILURE, exception);
		}
		return;
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
			//Get subjob properties from DDBB
			lProcess.logger.info(MessageCatalog._00064_GET_PROPERTIES_FROM_DDBB, job, subjob);
			if(!lProcess.getSubjobConfiguration(job, subjob)){
				lProcess.logger.info(MessageCatalog._00059_STOPPING);
				System.exit(2);
			}
			//Update subjob start_date in DDBB
			lProcess.logger.info(MessageCatalog._00056_UPDATING_SUBJOB_DDBB, job, subjob);
			if(!lProcess.updateSubjobStartDate(job, subjob)){
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
			//Check the number of generated links
			lProcess.logger.info(MessageCatalog._00060_VALIDATING_NUM_GENERATED_LINKS, lProcess.linkingXMLConfigFile, lProcess.linkingNumThreads, lProcess.linkingReload);
			int numLinks = lProcess.checkNumLinks(job, subjob);
			//Update subjob end_date, num_links of DDBB
			lProcess.logger.info(MessageCatalog._00056_UPDATING_SUBJOB_DDBB, job, subjob);
			lProcess.updateSubjobEndDate(job, subjob, numLinks);
			//Remove from crontab the programmed process (subjob)
			lProcess.logger.info(MessageCatalog._00063_REMOVING_SUBJOB_FROM_CRONTAB, job, subjob);
			lProcess.removeSubjobFromCrontab(job, subjob, propertiesFileName);
			//Remove config files
			lProcess.logger.info(MessageCatalog._00065_REMOVING_CONFIG_FILES);
			lProcess.removeConfigFiles(propertiesFileName, lProcess.linkingXMLConfigFilename);
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
