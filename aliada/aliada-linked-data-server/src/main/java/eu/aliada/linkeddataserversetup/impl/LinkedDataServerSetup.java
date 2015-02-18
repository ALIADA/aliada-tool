// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-linked-data-server
// Responsible: ALIADA Consortium
package eu.aliada.linkeddataserversetup.impl;

import eu.aliada.shared.log.Log;
import eu.aliada.linkeddataserversetup.log.MessageCatalog;
import eu.aliada.linkeddataserversetup.model.Job;
import eu.aliada.linkeddataserversetup.model.JobConfiguration;
import eu.aliada.linkeddataserversetup.model.Subset;
import eu.aliada.linkeddataserversetup.rdbms.DBConnectionManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Linked Data Server setup implementation. 
 * It setups the rewrite rules in Virtuoso for de-referencing the dataset URI-s.
 *  
 * @author Idoia Murua
 * @since 1.0
 */
public class LinkedDataServerSetup {
	/** For logging. */
	private static final Log LOGGER = new Log(LinkedDataServerSetup.class);
	/** Format for the ISQL command to execute */
	private static final String ISQL_COMMAND_FORMAT = "%s %s:%d %s %s %s -u lhost=%s vhost=%s uri_id=%s uri_doc_slash=%s uri_def=%s graphs_select_encoded=%s graphs_encoded=%s domain_name_encoded=%s rules_suffix=%s uri_doc_concept=%s dataset_page=%s aliada_ont_encoded=%s uri_id_encoded=%s";
	/** Dataset Index HTML Page*/
	private static final String DATASET_INDEX_PAGE = "dataset.html"; 
	/* Input parameters for URL rewrite rules */
	/** Suffix to be added to URL rewrite rules names. */
	private String rulesNamesSuffix = "";
	/** The URI Document section. */
	private String uriDocPart;
	/** The URI Identifier section. */
	private String uriIdPart;
	/** The URI Ontology section. */
	private String uriDefPart;
	/** The dataset graphs encoded with the FROM clause, URL encoded. */
	private String graphsSelectEncoded;
	/** The dataset graphs encoded with the & clause, URL encoded. */
	private String graphsEncoded;
	/** The dataset Domain name, URL encoded. */
	private String domainNameEncoded;
	/** The URI Identifier section, URL encoded. */
	private String uriIdPartEncoded;
	/** Aliada Ontology URI, URL encoded. */
	private String ontologyEncoded;
	/** The URI Document section + Dataset Concept section. */
	private String uriDocConcept;

	/**
	 * Remove leading and trailing slashes of a given path.
	 *
	 * @param path	path to remove leading and trailing slashes.
	 * @return the path without leading and trailing slashes.
	 * @since 2.0
	 */
	protected String removeLeadingTralingSlashes(final String path){
		String cleanPath = "";
		//Remove leading slashes
		cleanPath = path.replaceFirst("^/+", "");
		//Remove trailing slashes
		cleanPath = path.replaceFirst("/+$", "");
		return cleanPath;
	}

	/**
	 * Encodes the parameters to pass to the ISQL commands.
	 * URLEncode and replace % by %%, for Virtuoso Rewrite Rules.
	 *
	 * @param jobConf	the {@link eu.aliada.linkeddataserversetup.model.JobConfiguration}
	 *               	that contains the ISQL commands parameters.  
	 * @return true if the encoding has been carried out correctly.
	 * @since 1.0
	 */
	public boolean encodeParams(final JobConfiguration jobConf){
		boolean encoded = false;
		try{
			//Add leading slash to URI Document section
			if(jobConf.getUriDocPart() != null) {
				uriDocPart = "/" + removeLeadingTralingSlashes(jobConf.getUriDocPart());
			} else {
				uriDocPart = "/";
			}
			//Check URI identifier section is correct
			if(jobConf.getUriIdPart() != null) {
				uriIdPart = "/" + removeLeadingTralingSlashes(jobConf.getUriIdPart());
			} 
			//Check URI Ontology section is correct
			if(jobConf.getUriDefPart() != null) {
				uriDefPart = "/" + removeLeadingTralingSlashes(jobConf.getUriDefPart());
			} 
			//Encode dataset graphs
			graphsSelectEncoded = "";
			graphsEncoded = "";
			for (Iterator<Subset> iterSubsets = jobConf.getSubsets().iterator(); iterSubsets.hasNext();  ) {
				Subset subset = iterSubsets.next();
				String graphSelectEncoded = URLEncoder.encode(" FROM <" + subset.getGraph() + ">", "UTF-8");
				String linksGraphSelectEncoded = URLEncoder.encode(" FROM <" + subset.getLinksGraph() + ">", "UTF-8");
				graphsSelectEncoded = graphsSelectEncoded + graphSelectEncoded + linksGraphSelectEncoded;
				String graphEncoded = URLEncoder.encode("&graph=" + subset.getGraph(), "UTF-8");
				String linksGraphEncoded = URLEncoder.encode("&graph=" + subset.getLinksGraph(), "UTF-8");
				graphsEncoded = graphsEncoded + graphEncoded + linksGraphEncoded;
			}
			graphsSelectEncoded = graphsSelectEncoded.replace("%", "%%");
			graphsEncoded = graphsEncoded.replace("%", "%%");
			//Encode domain name
			domainNameEncoded = URLEncoder.encode(jobConf.getDomainName(), "UTF-8");
			domainNameEncoded = domainNameEncoded.replace("%", "%%");
			//Encode URI Identifier part
			uriIdPartEncoded = URLEncoder.encode(uriIdPart, "UTF-8");
			uriIdPartEncoded = uriIdPartEncoded.replace("%", "%%");
			//Encode Ontology URI 
			ontologyEncoded = URLEncoder.encode(jobConf.getOntologyUri(), "UTF-8");
			ontologyEncoded = ontologyEncoded.replace("%", "%%");
			//Compose URI document part + URI Concept part
			if(jobConf.getUriDocPart() != null) {
				uriDocConcept = removeLeadingTralingSlashes(jobConf.getUriDocPart());
			} 
			if(jobConf.getUriConceptPart() != null) {
				if(uriDocConcept.length() > 0){
					uriDocConcept = uriDocConcept + "/" + removeLeadingTralingSlashes(jobConf.getUriConceptPart());
				} else {
					uriDocConcept = removeLeadingTralingSlashes(jobConf.getUriConceptPart());
				}
			}
			//Compose rules name suffix
			rulesNamesSuffix = "";
			rulesNamesSuffix = jobConf.getDomainName().replace("http", ""); 
			rulesNamesSuffix = rulesNamesSuffix.replace(":", "");
			rulesNamesSuffix = rulesNamesSuffix.replace("/", "");
			rulesNamesSuffix = rulesNamesSuffix.replace(".", "");
			if((uriIdPart != null) && (uriDefPart!= null) && (graphsEncoded.length() > 0) && (domainNameEncoded != null) &&
					(ontologyEncoded != null) && (uriDocConcept != null) && (rulesNamesSuffix.length() > 0)) {
				if((ontologyEncoded.length() > 0) && (ontologyEncoded.length() > 0) && (ontologyEncoded.length() > 0) && (uriDocConcept.length() > 0) && (domainNameEncoded.length() > 0)) {
					encoded = true;
				}
			}
		} catch (UnsupportedEncodingException exception){
			LOGGER.error(MessageCatalog._00038_ENCODING_ERROR, exception);
		}
		return encoded;
	}

	/**
	 * Get the appropiate ISQL commands file for the subset.
	 * The ISQL commands are for creating rewrite rules in Virtuoso for
	 * dereferencing the dataset URI-s. 
	 *
	 * @param jobConf	the {@link eu.aliada.linkeddataserversetup.model.JobConfiguration}
	 *               	that contains the default ISQL commands file names for the subsets.  
	 * @param subset	the {@link eu.aliada.linkeddataserversetup.model.Subset}
	 *               	that contains the specific ISQL commands file name for this subset. 
	 * @return the name of the commands file to use.
	 * @since 2.0
	 */
	public String getIsqlCommandsFileSubset(final JobConfiguration jobConf, final Subset subset){
		String isqlCommandsFilename;
		isqlCommandsFilename = subset.getIsqlCommandsSubsetFilename();
		//Check if isqlCommandsFilename exists
		boolean isqlCommandsFilenameExists = false;
		if(isqlCommandsFilename != null){
			final File isqlFile = new File(isqlCommandsFilename);
			if (isqlFile.exists()){
				isqlCommandsFilenameExists = true;
			}
		}
		if (!isqlCommandsFilenameExists){
			//If there is not a ISQL command file specifically for this subset, 
			//use the default one
			isqlCommandsFilename = jobConf.getIsqlCommandsSubsetFilenameDefault();
			//Check if default isqlCommandsFilename exists
			isqlCommandsFilenameExists = false;
			if(isqlCommandsFilename != null){
				final File isqlFile = new File(isqlCommandsFilename);
				if (isqlFile.exists()){
					isqlCommandsFilenameExists = true;
				}
			}
			if (!isqlCommandsFilenameExists){
				LOGGER.error(MessageCatalog._00031_FILE_NOT_FOUND, isqlCommandsFilename);
				isqlCommandsFilename = null;
			}
		}
		return isqlCommandsFilename;
	}

	/**
	 * It executes the global ISQL commands that set the rewrite rules in Virtuoso 
	 * for dereferencing the dataset URI-s.
	 *
	 * @param jobConf		the {@link eu.aliada.linkeddataserversetup.model.JobConfiguration}
	 *						that contains information to setup the rewrite rules in Virtuoso  
	 * @return if the ISQL commands have been executed successfully.  					
	 * @since 1.0
	 */
	public boolean executeGlobalIsqlCommands(final JobConfiguration jobConf) {
		boolean success = false;
		//Get global ISQL commands file for rewriting rules in Virtuoso
		LOGGER.debug(MessageCatalog._00036_GET_ISQL_COMMANDS_FILE);
		final String isqlCommandsFilename = jobConf.getIsqlCommandsGlobalFilename();
		//Check if file exists
		boolean isqlCommandsFilenameExists = false;
		if(isqlCommandsFilename != null){
			final File isqlFile = new File(isqlCommandsFilename);
			if (isqlFile.exists()){
				isqlCommandsFilenameExists = true;
			}
		}
		if(isqlCommandsFilenameExists){
			//Compose ISQL command execution statement
			final String isqlCommand = String.format(ISQL_COMMAND_FORMAT,
					jobConf.getIsqlCommandPath(), jobConf.getStoreIp(),
					jobConf.getStoreSqlPort(), jobConf.getSqlLogin(),
					jobConf.getSqlPassword(), isqlCommandsFilename,
					jobConf.getListeningHost(), jobConf.getVirtualHost(),
					uriIdPart, uriDocPart, uriDefPart, graphsSelectEncoded,
					graphsEncoded, domainNameEncoded, rulesNamesSuffix,
					uriDocConcept, DATASET_INDEX_PAGE, ontologyEncoded, 
					uriIdPartEncoded);
			//Execute ISQL command
			try {
				LOGGER.debug(MessageCatalog._00040_EXECUTING_ISQL);
				final Process commandProcess = Runtime.getRuntime().exec(isqlCommand);
				final BufferedReader stdInput = new BufferedReader(new InputStreamReader(commandProcess.getInputStream()));
				String comOutput = "";
				while ((comOutput = stdInput.readLine()) != null) {
					LOGGER.debug(comOutput);
				}
				success = true;
			} catch (IOException exception) {
				LOGGER.error(MessageCatalog._00033_EXTERNAL_PROCESS_START_FAILURE, exception, isqlCommand);
			}
		}
		return success;
	}

	/**
	 * It executes the subset ISQL commands that set the rewrite rules in Virtuoso 
	 * for dereferencing the subset URI-s.
	 *
	 * @param jobConf		the {@link eu.aliada.linkeddataserversetup.model.JobConfiguration}
	 *						that contains information to setup the rewrite rules in Virtuoso  
	 * @param subset		the {@link eu.aliada.linkeddataserversetup.model.Subset}
	 *						that contains information to setup the rewrite rules in Virtuoso  
	 * @return if the ISQL commands have been executed successfully.  					
	 * @since 2.0
	 */
	public boolean executeSubsetIsqlCommands(final JobConfiguration jobConf, final Subset subset) {
		boolean success = false;
		
		//Get global ISQL commands file for rewriting rules in Virtuoso
		LOGGER.debug(MessageCatalog._00036_GET_ISQL_COMMANDS_FILE);
		final String isqlCommandsFilename = getIsqlCommandsFileSubset(jobConf, subset);

		//Compose URI document part + URI Concept part + Subset URI Concept part
		String uriDocConceptSubset = "";
		if(subset.getUriConceptPart() != null) {
			uriDocConceptSubset = removeLeadingTralingSlashes(subset.getUriConceptPart());
			if(uriDocConceptSubset.length() > 0) {
				uriDocConceptSubset = uriDocConcept + "/" + uriDocConceptSubset;
			}
		} 
		
		//If the URI Concept part of the subset is empty, its corresponding URL Rewrite rules will not be created
		if((isqlCommandsFilename != null) && (uriDocConceptSubset.length() > 0)){
			//Compose Rules Names suffix for the subset, adding the subset concept part of the URI
			String rulesNamesSuffixSubset = rulesNamesSuffix + uriDocConceptSubset;
			rulesNamesSuffixSubset = rulesNamesSuffixSubset.replace("/", "");
			//Compose ISQL command execution statement
			final String isqlCommand = String.format(ISQL_COMMAND_FORMAT,
					jobConf.getIsqlCommandPath(), jobConf.getStoreIp(),
					jobConf.getStoreSqlPort(), jobConf.getSqlLogin(),
					jobConf.getSqlPassword(), isqlCommandsFilename,
					jobConf.getListeningHost(), jobConf.getVirtualHost(),
					uriIdPart, uriDocPart, uriDefPart, graphsSelectEncoded,
					graphsEncoded, domainNameEncoded, rulesNamesSuffixSubset,
					uriDocConceptSubset, DATASET_INDEX_PAGE, ontologyEncoded, 
					uriIdPartEncoded);
			//Execute ISQL command
			try {
				LOGGER.debug(MessageCatalog._00040_EXECUTING_ISQL);
				final Process commandProcess = Runtime.getRuntime().exec(isqlCommand);
				final BufferedReader stdInput = new BufferedReader(new InputStreamReader(commandProcess.getInputStream()));
				String comOutput = "";
				while ((comOutput = stdInput.readLine()) != null) {
					LOGGER.debug(comOutput);
				}
				success = true;
			} catch (IOException exception) {
				LOGGER.error(MessageCatalog._00033_EXTERNAL_PROCESS_START_FAILURE, exception, isqlCommand);
			}
		}
		return success;
	}

	/**
	 * Create the dataset defult HTML page.
	 *
	 * @param jobConf		the {@link eu.aliada.linkeddataserversetup.model.JobConfiguration}
	 *						that contains information to create the dataset HTML page.  
	 * @return 
	 * @since 2.0
	 */
	public void createDatasetDefaultPage(final JobConfiguration jobConf){
		//Create the folder where the page resides, if it does not exist
		final String pageFolder = jobConf.getVirtHttpServRoot() + File.separator + rulesNamesSuffix;
		final File fFolder = new File(pageFolder);
		if (!fFolder.exists()) {
			fFolder.mkdir();
		}
		
		String pagePath = pageFolder + File.separator + DATASET_INDEX_PAGE;
		//Remove the page if it already exists
		final File fPage = new File(pagePath);
		if (fPage.exists()) {
			fPage.delete();
		}
		//Now, create a new one
		try {
			final FileWriter fstream = new FileWriter(pagePath);
			final BufferedWriter out = new BufferedWriter(fstream);
			String line = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">";
        	out.write(line);
        	out.newLine();
        	line = "<html>";
        	out.newLine();
        	line = "<head>";
        	out.newLine();
        	line = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">";
        	out.newLine();
        	line = "<title>" + jobConf.getDatasetDesc() + "</title>";
        	out.newLine();
        	line = "</head>";
        	out.newLine();
        	line = "<body>";
        	out.newLine();
        	line = jobConf.getDatasetLongDesc() + "<br>";
        	out.newLine();
        	line = "<a href=\"" + jobConf.getPublicSparqlEndpointUri() + "\">" + "SPARQL Endpoint" + "</a><br>";
        	out.newLine();
        	//List resources of dataset
        	String datasetUri = "http://" + jobConf.getDomainName() + "/" + uriDocConcept;  
        	line = "<a href=\"" + datasetUri + "\">" + "list of resources" + "</a><br>";
        	out.newLine();
			for (Iterator<Subset> iterSubsets = jobConf.getSubsets().iterator(); iterSubsets.hasNext();  ) {
				Subset subset = iterSubsets.next();
				String uriDocConceptSubset = "";
				if(subset.getUriConceptPart() != null) {
					uriDocConceptSubset = removeLeadingTralingSlashes(subset.getUriConceptPart());
				} 				
				if(uriDocConceptSubset.length() > 0) {
		        	//List resources of subset
					String subsetUri = datasetUri + "/" + uriDocConceptSubset;
		        	line = "<a href=\"" + subsetUri + "\">" + "list of resources os subset " + subset.getDescription()+ "</a><br>";
		        	out.newLine();

				}
			}
        	
        	line = "</body>";
        	out.newLine();
        	line = "</html>";
        	out.newLine();
	    	out.close();
		} catch (IOException exception) {
			LOGGER.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, pagePath);
		}
		
	}

	/**
	 * It setups the rewrite rules in Virtuoso for dereferencing the dataset URI-s.
	 *
	 * @param jobConf		the {@link eu.aliada.linkeddataserversetup.model.JobConfiguration}
	 *						that contains information to setup the rewrite rules in Virtuoso  
	 * @param db			The DDBB connection. 
	 * @return the {@link eu.aliada.linkedDataServerSetup.model.job} created.  					
	 * @since 1.0
	 */
	public Job setup(final JobConfiguration jobConf, final DBConnectionManager dbConn) {
		LOGGER.debug(MessageCatalog._00030_STARTING);
		//Update job start-date in DDBB
		dbConn.updateJobStartDate(jobConf.getId());
		//URLEncode and prepare some command parameters for Virtuoso Rewrite Rules
		LOGGER.debug(MessageCatalog._00037_ENCODE_PARAMS);
		final boolean encoded = encodeParams(jobConf);
		if(encoded) {
			//Execute global ISQL commands file for rewriting rules in Virtuoso
			boolean success = executeGlobalIsqlCommands(jobConf);
			if (success) {
				//Get subset ISQL commands file for rewriting rules in Virtuoso and execute them
				for (Iterator<Subset> iterSubsets = jobConf.getSubsets().iterator(); iterSubsets.hasNext();  ) {
					Subset subset = iterSubsets.next();
					executeSubsetIsqlCommands(jobConf, subset);
				}
			}
		}
		
		//TO DO
		createDatasetDefaultPage(jobConf);

		//Update job end_date of DDBB
		LOGGER.debug(MessageCatalog._00057_UPDATING_JOB_DDBB, jobConf.getId());
		dbConn.updateJobEndDate(jobConf.getId());
		final Job job = dbConn.getJob(jobConf.getId());
		LOGGER.debug(MessageCatalog._00041_STOPPED);
		return job;
	}

}
