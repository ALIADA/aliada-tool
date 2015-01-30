// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.linksdiscovery.impl;

import eu.aliada.shared.log.Log;
import eu.aliada.linksdiscovery.log.MessageCatalog;
import eu.aliada.linksdiscovery.model.DDBBParams;
import eu.aliada.linksdiscovery.model.Job;
import eu.aliada.linksdiscovery.model.JobConfiguration;
import eu.aliada.linksdiscovery.model.SubjobConfiguration;
import eu.aliada.linksdiscovery.rdbms.DBConnectionManager;

import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
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
 * Links Discovery implementation. 
 * It programs via crontab the subjobs (Linking Processes) that uses SILK library.
 *  
 * @author Idoia Murua
 * @since 1.0
 */
public class LinksDiscovery {
	/** For logging. */
	private static final Log LOGGER  = new Log(LinksDiscovery.class);
	/** Crontab file name. */
	private final static String CRONTAB_FILENAME = "aliada_links_discovery"; 
	/** Crontab file extension. */
	private final static String CRONTAB_EXT = ".cron"; 
	/** Properties file name prefix. */
	private final static String PROPFILE_FILENAME = "linking";
	/** Name of the linking process to program with crontab, that executes 
	eu.aliada.linksDiscovery.impl.LinkingProcess java application */
	private final static String LINKING_PROCESS_NAME = "links-discovery-task-runner.sh";
	/* Parameters for configuring the input datasource of ALIADA from where
	to link */ 
	/** PAGESIZE parameter for the SPARQL endpoint of ALIADA input datasource. */
	private final static String PAGESIZE = "1000"; 
	/** PAUSETIME parameter for the SPARQL endpoint of ALIADA input datasource. */
	private final static String PAUSETIME = "0"; 
	/** RETRYCOUNT parameter for the SPARQL endpoint of ALIADA input datasource. */
	private final static String RETRYCOUNT = "3"; 
	/** RETRYPAUSE parameter for the SPARQL endpoint of ALIADA input datasource. */
	private final static String RETRYPAUSE = "1000"; 
	/** QUERYPARAMETERS parameter for the SPARQL endpoint of ALIADA input datasource. */
	private final static String QUERYPARAMETERS = ""; 
	/** ENTITYLIST parameter for the SPARQL endpoint of ALIADA input datasource. */
	private final static String ENTITYLIST = ""; 
	/** PARALLEL parameter for the SPARQL endpoint of ALIADA input datasource. */
	private final static String PARALLEL = "true"; 
	/** Crontab List command. */
	private final static String CRONTAB_LIST_COMMAND = "sudo -u %s crontab -l";
	/** Crontab command. */
	private final static String CRONTAB_COMMAND = "sudo -u %s crontab %s";
	/** Crontab line format.*/
	private static final String CRONTAB_LINE = "%d %d %d %d * %s %d %d %s";


	/**
	 * Creates a new crontab file which contains formed programmed tasks. 
	 * This way, former tasks are not removed when programming again via crontab.
	 *
	 * @param tmpDir	the name of temporary folder where to create the new 
	 * 					crontab file.
	 * @param cronUser	the machine´s user name to execute the crontab command.
	 * @return the name of the newly created crontab file.
	 * @since 1.0
	 */
	public String createCrontabFile(final String tmpDir, final String cronUser){
		String crontabFilename = tmpDir + File.separator + CRONTAB_FILENAME +  System.currentTimeMillis() + CRONTAB_EXT;
		//Replace Windows file separator by "/" Java file separator
		crontabFilename = crontabFilename.replace("\\", "/");
		//Remove the crontab file if it already exists
		final File fCron = new File(crontabFilename);
		if (fCron.exists()) {
			fCron.delete();
		}
		//Now, create a new one
		try {
			final FileWriter fstream = new FileWriter(crontabFilename);
			final BufferedWriter out = new BufferedWriter(fstream);
			// Execute system command "crontab -l"
			final String command = String.format(CRONTAB_LIST_COMMAND, cronUser);
/*	    	try {
		    	String line = null;
		    	final Process crontabList = Runtime.getRuntime().exec(command);
		    	final BufferedReader stdInput = new BufferedReader(new InputStreamReader(crontabList.getInputStream()));
		        while ((line = stdInput.readLine()) != null) {
		        	out.write(line);
		        	out.newLine();
		        }
	    	} catch (IOException exception) {
		    	crontabFilename = null;
		    	LOGGER.error(MessageCatalog._00033_EXTERNAL_PROCESS_START_FAILURE, exception, command);
		    }*/
	    	out.close();
		} catch (IOException exception) {
			LOGGER.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, crontabFilename);
			crontabFilename = null;
		}
		return crontabFilename;
	}

	/**
	 * Appends a XML element of the following type to a parent XML element:
	 *  <Param name=<paramName> value=<paramValue> />
	 *
	 * @param doc			the XML Document element.
	 * @param parentElem	the parent XML element where to append the created
	 * 						XML element.
	 * @param paramName		the value of the "name" attribute.
	 * @param paramValue	the value of the "value" attribute.
	 * @since 1.0
	 */
	public void appendChildParam(final Document doc, final Element parentElem, final String paramName, final String paramValue){
		//Create new <Param> element
		final Element paramElem = doc.createElement("Param"); 
		//set attributes to Param element
		final Attr attrName = doc.createAttribute("name"); 
		attrName.setValue(paramName);
		paramElem.setAttributeNode(attrName);
		final Attr attrValue = doc.createAttribute("value"); 
		attrValue.setValue(paramValue);
		paramElem.setAttributeNode(attrValue);
		//Add new <Param> element   
		parentElem.appendChild(paramElem);
	}

	/**
	 * Creates a XML configuration file for the SILK process. 
	 * Reads an input XML file and creates a new one adding information
	 * regarding the inputs and outputs.
	 * This new information is provided by the {@link eu.aliada.linksdiscovery.model.JobConfiguration}
	 * input parameter.
	 *
	 * @param linkingFile	the name of the input XML file from which to start
	 * 						creating the new one.
	 * @param ds			the name of the input dataset description to be
	 * 						included in the XML file.
	 * @param subjobName	the subjob name which will be used to create the names of 
	 *                      the output files.
	 * @param jobConf		a {@link eu.aliada.linksdiscovery.model.JobConfiguration}
	 * 						containing information to be included in the XML file.
	 * @return the name of the newly created XML configuration file.
	 * @since 1.0
	 */
	public String createLinkingXMLConfigFile(final String linkingFile, final String dataSource, final String subjobName, final JobConfiguration jobConf){
		/* Read XML file and create a new one with SPARQL enpoints referring 
		information (input & output). */
		final File inputXMLFile = new File(linkingFile);
		final String inputFileName = inputXMLFile.getName();
		//Compose new file name
		String inputFileNameNoExt;
		final int index = inputFileName.lastIndexOf(".");
		if(index != -1) {
			inputFileNameNoExt = inputFileName.substring(0, index);
		}
		else {
			inputFileNameNoExt = inputFileName;
		}
		String filePath = jobConf.getTmpDir() + File.separator;
		//Replace Windows file separator by "/" Java file separator
		filePath = filePath.replace("\\", "/");
		String linkingXMLConfigFilename = filePath + inputFileNameNoExt + System.currentTimeMillis() + ".xml";

		try {
			//Read XML file
			final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			final Document doc = dBuilder.parse(inputXMLFile);		 
			doc.getDocumentElement().normalize();
	
			//Create input datasource element <DataSource> 
			//Get <DataSources>
			NodeList nList = doc.getElementsByTagName("DataSources");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				final Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					final Element datasourcesElem = (Element) nNode;
					//Create new <DataSource> element
					final Element datasourceElem = doc.createElement("DataSource"); 
					//set attributes to datasource element
					final Attr attrId = doc.createAttribute("id"); 
					attrId.setValue(dataSource);
					datasourceElem.setAttributeNode(attrId);
					final Attr attrType = doc.createAttribute("type"); 
					attrType.setValue("sparqlEndpoint");
					datasourceElem.setAttributeNode(attrType);
					//Add child elements to datasource element
					appendChildParam(doc, datasourceElem, "pageSize", PAGESIZE);
					appendChildParam(doc, datasourceElem, "pauseTime", PAUSETIME);
					appendChildParam(doc, datasourceElem, "retryCount", RETRYCOUNT);
					appendChildParam(doc, datasourceElem, "endpointURI", jobConf.getInputURI());
					appendChildParam(doc, datasourceElem, "retryPause", RETRYPAUSE);
					appendChildParam(doc, datasourceElem, "graph", jobConf.getInputGraph());
					appendChildParam(doc, datasourceElem, "queryParameters", QUERYPARAMETERS);
					appendChildParam(doc, datasourceElem, "login", jobConf.getInputLogin());
					appendChildParam(doc, datasourceElem, "entityList", ENTITYLIST);
					appendChildParam(doc, datasourceElem, "parallel", PARALLEL);
					appendChildParam(doc, datasourceElem, "password", jobConf.getInputPassword());
					//Add new <Datasource> element with new Input datasource to <DataSources>  
					datasourcesElem.appendChild(datasourceElem);
				}
			}
	
			//Create output element <Output> 
			//Get <Outputs>
			nList = doc.getElementsByTagName("Outputs");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				final Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					final Element outputsElem = (Element) nNode;
					Element outputElem; 
					Attr attrType; 

					/* Not needed for now
					//Create new <Output> element for SPARUL
					outputElem = doc.createElement("Output"); 
					//set attributes to Output element
					attrType = doc.createAttribute("type"); 
					attrType.setValue("sparul");
					outputElem.setAttributeNode(attrType);
					//Add child elements to Output element
					appendChildParam(doc, outputElem, "uri", jobConf.getOutputURI());
					appendChildParam(doc, outputElem, "graphUri", jobConf.getOutputGraph());
					appendChildParam(doc, outputElem, "login", jobConf.getOutputLogin());
					appendChildParam(doc, outputElem, "password", jobConf.getOutputPassword());
					//Add new <Output> element to <Outputs>  
					outputsElem.appendChild(outputElem);
					*/

					//Create new <Output> element for a File
					//Get InterLink id to generate output file name
					final Node interLinkNode = nNode.getParentNode();
					final Element interLinkElem = (Element) interLinkNode;
					final String interLinkId = interLinkElem.getAttribute("id");
					final String triplesGeneratedFilename = filePath + subjobName + "_" + interLinkId + "_" + System.currentTimeMillis() + "_output.n3";
					outputElem = doc.createElement("Output"); 
					//set attributes to Output element
					attrType = doc.createAttribute("type"); 
					attrType.setValue("file");
					outputElem.setAttributeNode(attrType);
					//Add child elements to Output element
					appendChildParam(doc, outputElem, "file", triplesGeneratedFilename);
					appendChildParam(doc, outputElem, "format", "ntriples");
					//Add new <Output> element to <Outputs>  
					outputsElem.appendChild(outputElem);
				}
			}
	
			//write the content into new XML file
			final TransformerFactory transformerFactory = TransformerFactory.newInstance();
			final Transformer transformer = transformerFactory.newTransformer();
			final DOMSource source = new DOMSource(doc);
			final StreamResult result = new StreamResult(new File(linkingXMLConfigFilename));
			//Pretty Print XML
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(source, result);
		} catch (ParserConfigurationException exception) {
			LOGGER.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, linkingXMLConfigFilename);
			linkingXMLConfigFilename = null;
		} catch (SAXException exception) {
			LOGGER.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, linkingXMLConfigFilename);
			linkingXMLConfigFilename = null;
		} catch (IOException exception) {
			LOGGER.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, linkingXMLConfigFilename);
			linkingXMLConfigFilename = null;
		} catch (TransformerException exception) {
			LOGGER.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, linkingXMLConfigFilename);
			linkingXMLConfigFilename = null;
		}
		return linkingXMLConfigFilename;
	}
	
	/**
	 * Creates a properties file for the process to be programmed. 
	 * This file contains the parameters to be used to connect to the DDBB.
	 *
	 * @param tmpDir		the name of the temporary directory where to create
	 *						the properties file.
	 * @param ddbbParams	the {@link eu.aliada.linksdiscovery.model.DDBBParams} 
	 * 						which contains the DDBB parameters.
	 * @return the name of the newly created properties file.
	 * @since 1.0
	 */
	public String createLinkingPropConfigFile(final String tmpDir, final DDBBParams ddbbParams){
		// Create properties file for linking process/subjob to be scheduled with crontab
		//Compose new file name
		String linkingConfigFilename = tmpDir + File.separator + PROPFILE_FILENAME + System.currentTimeMillis() + ".properties";
		//Replace Windows file separator by "/" Java file separator
		linkingConfigFilename = linkingConfigFilename.replace("\\", "/");
		try {
			final FileWriter fstream = new FileWriter(linkingConfigFilename);
			final BufferedWriter out = new BufferedWriter(fstream);
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
			out.close();
		} catch (IOException exception) {
			LOGGER.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, linkingConfigFilename);
			linkingConfigFilename = null;
		}
		return linkingConfigFilename;
	}

	/**
	 * Programs the linking processes by inserting them in the crontab file.
	 *
	 * @param crontabFilename			the name of the crontab file.
	 * @param clientAppBinDir			the folder where the process to be 
	 * 									executed resides.
	 * @param jobId						the job identifier. It will be passed
	 * 									as input parameter to the linking process
	 * @param subjobId					the subjob identifier. It will be passed
	 * 									as input parameter to the linking process
	 * @param linkingPropConfigFilename	the name of the properties file. It will be
	 * 									passed as input parameter to the linking process
	 * @param reloadTarget				if the target dataset must be reloaded
	 * @return true if the process has been inserted in the file. False otherwise.
	 * @since 1.0
	 */
	public boolean insertLinkingProcessInCrontabFile(final String crontabFilename, final String clientAppBinDir, final int jobId, final int subjobId, final String linkingPropConfigFilename, final boolean reloadTarget)
	{
		if(linkingPropConfigFilename == null) {
			return false;
		}
		
		try {
			final FileWriter fstream = new FileWriter(crontabFilename,true);
			final BufferedWriter out = new BufferedWriter(fstream);
			final Calendar calendar = Calendar.getInstance();
			if(reloadTarget) {
				//When the target dataset is to be loaded, add more time to start every process  
				if(subjobId <= 1) {
					//If it is the first subjob, add a minute for starting the programmed process
					calendar.add(Calendar.MINUTE, 1);
				} else {
					//We add an hour to start the programmed processes for every other subjob
					calendar.add(Calendar.HOUR_OF_DAY, subjobId - 1);
				}
			} else {
				//If it is the first subjob, add a minute for starting ALL the programmed process
				calendar.add(Calendar.MINUTE, 1);
			}
			
			final int hour = calendar.get(Calendar.HOUR_OF_DAY);
			final int minute = calendar.get(Calendar.MINUTE);
			final int day = calendar.get(Calendar.DAY_OF_MONTH);
			final int month = calendar.get(Calendar.MONTH) + 1;
			//Compose script name that executes eu.aliada.linksDiscovery.impl.LinkingProcess
			//java application
			final String linkingProcessPath = clientAppBinDir + "/" + LINKING_PROCESS_NAME;
			//Compose crontab line to insert
			final String linkingProcessCronJob = String.format(CRONTAB_LINE, minute, hour, day, month, linkingProcessPath, jobId, subjobId, linkingPropConfigFilename);
			out.write(linkingProcessCronJob);
			out.newLine();
			out.close();
		} catch (IOException exception) {
			LOGGER.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, crontabFilename);
			return false;
		}
		return true;
	}

	/**
	 * It programs via crontab the subjobs (Linking Processes) that uses SILK library.
	 *
	 * @param jobConf		the {@link eu.aliada.linksdiscovery.model.JobConfiguration}
	 *						that contains information for configuring the linking processes.  
	 * @param db			The DDBB connection. 
	 * @param ddbbParams	the {@link eu.aliada.linksdiscovery.model.DDBBParams} 
	 * 						which contains the DDBB parameters for the subjobs 
	 * 						to be programmed.
	 * @return the {@link eu.aliada.linksDiscovery.model.job} created.  					
	 * @since 1.0
	 */
	public Job programLinkingProcesses(final JobConfiguration jobConf, final DBConnectionManager dbConn, final DDBBParams ddbbParams) {
		LOGGER.debug(MessageCatalog._00030_STARTING);
		//Update job start-date in DDBB
		dbConn.updateJobStartDate(jobConf.getId());
		//Get files and other parameters to generate linking processes (subjobs)
		LOGGER.debug(MessageCatalog._00035_GET_LINKING_CONFIG_FILES);
		final SubjobConfiguration[] subjobConf = dbConn.getSubjobConfigurations();
		//Generate initial crontab file with previous scheduled jobs
		LOGGER.debug(MessageCatalog._00036_CREATE_CRONTAB_FILE);
		final String crontabFilename  = createCrontabFile(jobConf.getTmpDir(), jobConf.getClientAppBinUser());
		if (crontabFilename != null){
			//For each linking process to schedule with crontab
			for (int i=0; i<subjobConf.length;i++){
				final int subjobId = i + 1 ;
				//Generate XML config file for SILK
				LOGGER.debug(MessageCatalog._00037_CREATE_LINKING_XML_CONFIG_FILE, subjobId);
				final String linkingXMLConfigFilename = createLinkingXMLConfigFile(subjobConf[i].getLinkingXMLConfigFilename(), subjobConf[i].getDs(), subjobConf[i].getName(), jobConf);
				if(linkingXMLConfigFilename != null){
					//Generate properties file to be used by scheduled subjob
					LOGGER.debug(MessageCatalog._00038_CREATE_LINKING_PROP_FILE, subjobId);
					final String linkingPropConfigFilename = createLinkingPropConfigFile(jobConf.getTmpDir(), ddbbParams);
					if (linkingPropConfigFilename != null){
						LOGGER.debug(MessageCatalog._00039_INSERT_LINKING_CRONTAB_FILE, subjobId);
						if(insertLinkingProcessInCrontabFile(crontabFilename, jobConf.getClientAppBinDir(), jobConf.getId(), subjobId, linkingPropConfigFilename, subjobConf[i].isLinkingReloadTarget())){
							//Insert job-subjob in DDBB
							LOGGER.debug(MessageCatalog._00042_INSERT_SUBJOB_DDBB, jobConf.getId(), subjobId);
							dbConn.insertSubjobToDB(jobConf.getId(), subjobId, subjobConf[i], linkingXMLConfigFilename,jobConf);
						}
					}
				}
			}
			// Execute system command "crontab contrabfilename"
			final String command = String.format(CRONTAB_COMMAND, jobConf.getClientAppBinUser(), crontabFilename);
			try {
				LOGGER.debug(MessageCatalog._00040_EXECUTING_CRONTAB);
				Process proc = Runtime.getRuntime().exec(command);
				proc.waitFor();
			} catch (Exception exception) {
				LOGGER.error(MessageCatalog._00033_EXTERNAL_PROCESS_START_FAILURE, exception, command);
			}
			//Remove crontab file
			File cronFile = new File(crontabFilename);
			if (cronFile.exists()) {
				cronFile.delete();
			}
		}
		final Job job = dbConn.getJob(jobConf.getId());
		LOGGER.debug(MessageCatalog._00041_STOPPED);
		return job;
	}

}
