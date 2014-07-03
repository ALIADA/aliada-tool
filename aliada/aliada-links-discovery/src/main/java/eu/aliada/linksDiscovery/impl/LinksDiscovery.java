// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.linksDiscovery.impl;

import eu.aliada.linksDiscovery.log.MessageCatalog;
import eu.aliada.shared.log.Log;
import eu.aliada.linksDiscovery.model.JobConfiguration;
import eu.aliada.linksDiscovery.model.SubjobConfiguration;
import eu.aliada.linksDiscovery.rdbms.DBConnectionManager;
import eu.aliada.linksDiscovery.model.DDBBParams;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import org.xml.sax.SAXException;

/**
 * Links discovery implementation via Silk library. 
 * @author Idoia Murua
 * @since 1.0
 */
public class LinksDiscovery {

	private final Log logger = new Log(LinksDiscovery.class);
	private final static String CRONTAB_FILENAME = "aliada_links_discovery.cron"; 
	private final static String PROPFILE_FILENAME = "linking";
	//Parameters for configuring the input datasource of ALIADA to link
	private final static String pageSize = "1000"; 
	private final static String pauseTime = "0"; 
	private final static String retryCount = "3"; 
	private final static String retryPause = "1000"; 
	private final static String queryParameters = ""; 
	private final static String entityList = ""; 
	private final static String parallel = ""; 
	private DBConnectionManager db;

	public SubjobConfiguration[] getLinkingConfigFiles(String propertiesFileName) {
		Vector<SubjobConfiguration> v = new Vector<SubjobConfiguration>();
		String fileProp = "linking.file.";
		String dsProp = "linking.ds.";
		String numThreadsProp = "linking.numthreads.";
		String reloadProp = "linking.reload.";
		String file;
		String ds;
		int idx = 1;
		try {
			InputStream propertyStream = new FileInputStream(propertiesFileName);
    		Properties p = new Properties();
    		p.load( propertyStream );
    		file = p.getProperty(fileProp + idx);
    		ds = p.getProperty(dsProp + idx);
       	 	while ((file != null) && (ds != null)){
        		SubjobConfiguration subjobConf = new SubjobConfiguration();
    			subjobConf.setLinkingXMLConfigFilename(file);
    			subjobConf.setDs(ds);
    	   		
    			if (p.getProperty(numThreadsProp + idx) != null)
    	   			subjobConf.setLinkingNumThreads(new Integer(p.getProperty(numThreadsProp + idx)));
    	   		else
        			//Default value: numthreads=1
    	   			subjobConf.setLinkingNumThreads(1);
        		
    	   		if (p.getProperty(reloadProp + idx) != null)
    	   			subjobConf.setLinkingReload(new Boolean(p.getProperty(reloadProp + idx)));
        		else
        			//Default value: reload=true
        			subjobConf.setLinkingReload(true);
    			
    	   		v.add(subjobConf);
    			idx++;
    			file = p.getProperty(fileProp + idx);
    		}

    		if (v.size() == 0) {
    			return new SubjobConfiguration[0];
    		}

    		return (SubjobConfiguration[]) v.toArray(new SubjobConfiguration[v.size()]);
    	} catch(FileNotFoundException exception) {
			logger.error(MessageCatalog._00031_CONFIG_FILE_NOT_FOUND, propertiesFileName);
			return new SubjobConfiguration[0];
    	} catch(IOException exception) {
			logger.error(MessageCatalog._00032_BAD_CONFIG_FILE, exception, propertiesFileName);
			return new SubjobConfiguration[0];
    	}
	}

	public String createCrontabFile(String tmpDir){
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
	    	/*
		    try {
		    	String s = null;
	  	    	Process crontabList = Runtime.getRuntime().exec(command);
		    	BufferedReader stdInput = new BufferedReader(new InputStreamReader(crontabList.getInputStream()));
		        while ((s = stdInput.readLine()) != null) {
		        	out.write(s);
		        	out.newLine();
	            }
		    } catch (IOException exception) {
		    	crontabFilename = null;
				logger.error(MessageCatalog._00033_EXTERNAL_PROCESS_START_FAILURE, exception, command);
		    }*/
			out.close();
		} catch (IOException exception) {
			logger.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, crontabFilename);
	    	crontabFilename = null;
		}
		return crontabFilename;
	}

	public 	void appendChildParam(Document doc, Element parentElem, String paramName, String paramValue){
		//Create new <Param> element
		Element paramElem = doc.createElement("Param"); 
		//set attributes to Param element
		Attr attrName = doc.createAttribute("name"); 
		attrName.setValue(paramName);
		paramElem.setAttributeNode(attrName);
		Attr attrValue = doc.createAttribute("value"); 
		attrValue.setValue(paramValue);
		paramElem.setAttributeNode(attrValue);
		//Add new <Param> element   
		parentElem.appendChild(paramElem);
		return;
	}

	public String createLinkingXMLConfigFile(String linkingFile, String ds, JobConfiguration job){
		// Read XML file and create a new one with SPARQL enpoints referring information (input & output)
		File inputXMLFile = new File(linkingFile);
		String inputFileName = inputXMLFile.getName();
		//Compose new file name
		String inputFileNameNoExt;
		int index = inputFileName.lastIndexOf(".");
		if(index != -1)
			inputFileNameNoExt = inputFileName.substring(0, index);
		else
			inputFileNameNoExt = inputFileName;
		String linkingXMLConfigFilename = job.getTmpDir() + File.separator + inputFileNameNoExt + System.currentTimeMillis() + ".xml";
		//Replace Windows file separator by "/" Java file separator
		linkingXMLConfigFilename = linkingXMLConfigFilename.replace("\\", "/");

		try {
			//Read XML file
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputXMLFile);		 
			doc.getDocumentElement().normalize();
	
			//Create input datasource element <DataSource> 
			//Get <DataSources>
			NodeList nList = doc.getElementsByTagName("DataSources");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element datasourcesElem = (Element) nNode;
					//Create new <DataSource> element
					Element datasourceElem = doc.createElement("DataSource"); 
					//set attributes to datasource element
					Attr attrId = doc.createAttribute("id"); 
					attrId.setValue(ds);
					datasourceElem.setAttributeNode(attrId);
					Attr attrType = doc.createAttribute("type"); 
					attrType.setValue("sparqlEndpoint");
					datasourceElem.setAttributeNode(attrType);
					//Add child elements to datasource element
					appendChildParam(doc, datasourceElem, "pageSize", pageSize);
					appendChildParam(doc, datasourceElem, "pauseTime", pauseTime);
					appendChildParam(doc, datasourceElem, "retryCount", retryCount);
					appendChildParam(doc, datasourceElem, "endpointURI", job.getInputURI());
					appendChildParam(doc, datasourceElem, "retryPause", retryPause);
					appendChildParam(doc, datasourceElem, "graph", job.getInputGraph());
					appendChildParam(doc, datasourceElem, "queryParameters", queryParameters);
					appendChildParam(doc, datasourceElem, "login", job.getInputLogin());
					appendChildParam(doc, datasourceElem, "entityList", entityList);
					appendChildParam(doc, datasourceElem, "parallel", parallel);
					appendChildParam(doc, datasourceElem, "password", job.getInputPassword());
					//Add new <Datasource> element with new Input datasource to <DataSources>  
					datasourcesElem.appendChild(datasourceElem);
				}
			}
	
			//Create output element <Output> 
			//Get <Outputs>
			nList = doc.getElementsByTagName("Outputs");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element outputsElem = (Element) nNode;
					//Create new <Output> element
					Element outputElem = doc.createElement("Output"); 
					//set attributes to Output element
					Attr attrType = doc.createAttribute("type"); 
					attrType.setValue("sparul");
					outputElem.setAttributeNode(attrType);
					//Add child elements to Output element
					appendChildParam(doc, outputElem, "uri", job.getOutputURI());
					appendChildParam(doc, outputElem, "graphUri", job.getOutputGraph());
					appendChildParam(doc, outputElem, "login", job.getOutputLogin());
					appendChildParam(doc, outputElem, "password", job.getOutputPassword());
					//Add new <Output> element to <Outputs>  
					outputsElem.appendChild(outputElem);
				}
			}
	
			//write the content into new XML file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(linkingXMLConfigFilename));
			//Pretty Print XML
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(source, result);
		} catch (ParserConfigurationException exception) {
			logger.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, linkingXMLConfigFilename);
			linkingXMLConfigFilename = null;
		} catch (SAXException exception) {
			logger.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, linkingXMLConfigFilename);
			linkingXMLConfigFilename = null;
		} catch (IOException exception) {
			logger.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, linkingXMLConfigFilename);
			linkingXMLConfigFilename = null;
		} catch (TransformerException exception) {
			logger.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, linkingXMLConfigFilename);
			linkingXMLConfigFilename = null;
		}
		return linkingXMLConfigFilename;
	}
	
	public String createLinkingPropConfigFile(String linkingXMLConfigFilename, SubjobConfiguration subjobConf, String tmpDir, DDBBParams ddbbParams){
		// Create properties file for linking process to be scheduled with crontab
		//Compose new file name
		String linkingConfigFilename = tmpDir + File.separator + PROPFILE_FILENAME + System.currentTimeMillis() + ".properties";
		//Replace Windows file separator by "/" Java file separator
		linkingConfigFilename = linkingConfigFilename.replace("\\", "/");
		try {
			FileWriter fstream = new FileWriter(linkingConfigFilename);
			BufferedWriter out = new BufferedWriter(fstream);
			//DDBB connection parameters
			String prop = "database.username=" + ddbbParams.getUsername();
        	out.write(prop);
        	out.newLine();
			prop = "database.password=" + ddbbParams.getPassword();
        	out.write(prop);
        	out.newLine();
			prop = "database.driverClassName=" + ddbbParams.getDriverClassName();
        	out.write(prop);
        	out.newLine();
			prop = "database.url=" + ddbbParams.getUrl();
        	out.write(prop);
        	out.newLine();
			prop = "linking.config.filename=" + linkingXMLConfigFilename;
        	out.write(prop);
        	out.newLine();
       		prop = "linking.numthreads=" + subjobConf.geLlinkingNumThreads();
       		out.write(prop);
        	out.newLine();
			prop = "linking.reload=" + subjobConf.getLinkingReload();
        	out.write(prop);
        	out.newLine();
			out.close();
		} catch (IOException exception) {
			logger.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, linkingConfigFilename);
			linkingConfigFilename = null;
		}
		return linkingConfigFilename;
	}

	public void insertLinkingProcessInCrontabFile(String linkingPropConfigFilename, String crontabFilename, int jobId, int subjobId)
	{
		if(linkingPropConfigFilename == null)
			return;
		
		try {
			FileWriter fstream = new FileWriter(crontabFilename,true);
			BufferedWriter out = new BufferedWriter(fstream);
			Calendar calendar = Calendar.getInstance();
			//We add an hour to start the programmed processes for every subjob
			calendar.add(Calendar.HOUR_OF_DAY, subjobId);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int minute = calendar.get(Calendar.MINUTE);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int month = calendar.get(Calendar.MONTH) + 1;
			String cronTabLine = "%d %d %d %d * %s %d %d %s";
			//TODO: Place log configuration options correctly
			String linkingJob = "java -Dlog4j.configuration='log4j.xml' eu.aliada.linksDiscovery.impl.LinkingProcess";
			String linkingProcessCronJob = String.format(cronTabLine, hour, minute, day, month, linkingJob, jobId, subjobId, linkingPropConfigFilename);
        	out.write(linkingProcessCronJob);
        	out.newLine();
			out.close();
			//Insert job-subjob in DDBB
			logger.info(MessageCatalog._00042_INSERT_SUBJOB_DDBB, jobId, subjobId);
			db.insertSubJobToDDBB(jobId, subjobId);
		} catch (IOException exception) {
			logger.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, crontabFilename);
		}
		return;
	}

	/**
	 * Links discovery implementation.
	 *
     * @param configurationFile	The configuration file.  
     * @param numThreads		The number of threads to be used for matching. 
     * @param reload			Specifies if the entity cache is to be reloaded 
     *
     * @return 					
     * @since 1.0
	 */
	public void programLinkingProcesses(JobConfiguration job, DBConnectionManager db, DDBBParams ddbbParams) {
		logger.info(MessageCatalog._00030_STARTING);
		this.db = db;
		//Update job´s start-date in DDBB
		db.updateJobStartDate(job.getId());
		//Get files and other parameters to generate linking processes (subjobs)
		logger.info(MessageCatalog._00035_GET_lINKING_CONFIG_FILES);
		SubjobConfiguration[] subjobConf = getLinkingConfigFiles(job.getConfigFile());
		//Generate initial crontab file with previous scheduled jobs
		logger.info(MessageCatalog._00036_CREATE_CRONTAB_FILE);
		String crontabFilename  = createCrontabFile(job.getTmpDir());
		if (crontabFilename != null){
			//For each linking process to schedule with crontab
			for (int i=0; i<subjobConf.length;i++){
				int subjobId = i + 1 ;
				//Generate XML config file for SILK
				logger.info(MessageCatalog._00037_CREATE_lINKING_XML_CONFIG_FILE, subjobId);
				String linkingXMLConfigFilename = createLinkingXMLConfigFile(subjobConf[i].getLinkingXMLConfigFilename(), subjobConf[i].getDs(), job);
				if(linkingXMLConfigFilename != null){
					//Generate properties file for job to schedule
					logger.info(MessageCatalog._00038_CREATE_lINKING_PROP_FILE, subjobId);
					String linkingPropConfigFilename = createLinkingPropConfigFile(linkingXMLConfigFilename, subjobConf[i], job.getTmpDir(), ddbbParams);
					if (linkingPropConfigFilename != null)
						logger.info(MessageCatalog._00039_INSERT_lINKING_CRONTAB_FILE, subjobId);
						insertLinkingProcessInCrontabFile(linkingPropConfigFilename, crontabFilename, job.getId(), subjobId);
				}
			}
			// Execute system command "crontab contrabfilename"
	    	String command = "crontab " + crontabFilename;
		    try {
				logger.info(MessageCatalog._00040_EXECUTING_CRONTAB);
		    	Runtime.getRuntime().exec(command);
		    } catch (IOException exception) {
				logger.error(MessageCatalog._00033_EXTERNAL_PROCESS_START_FAILURE, exception, command);
		    }
		}
		logger.info(MessageCatalog._00041_STOPPED);
    	return;
	}

}
