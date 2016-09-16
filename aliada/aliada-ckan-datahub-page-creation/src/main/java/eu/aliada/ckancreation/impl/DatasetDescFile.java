// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.impl;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.text.SimpleDateFormat;

import eu.aliada.ckancreation.model.DumpFileInfo;
import eu.aliada.ckancreation.model.JobConfiguration;
import eu.aliada.ckancreation.model.Subset;
import eu.aliada.ckancreation.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * Dataset Description File generation implementation. 
 * 
 * @author Idoia Murua
 * @since 2.0
 */
public class DatasetDescFile {
	/** For logging. */
	private static final Log LOGGER  = new Log(DataDump.class);
	/** Description file format **/
//	static final String FILE_FORMAT = "text/turtle"; 
	static final String FILE_FORMAT = "meta/void"; 
	/** Description file extension **/
	static final String FILE_EXTENSION = ".ttl"; 
	//Properties names
	/** Job Configuration which contains required parameters. **/
	private final JobConfiguration jobConf;
	/** The URL of the dataset in CKAN **/
	private final String ckanDatasetUrl;
	/** Array of info objects about the files containing the graph dump**/
	private final DumpFileInfo[] datasetDumpFileInfoList;
	/** Number of triples in the dataset **/
	private int numTriples;
	/** Void file name **/
	private String name;
	/** Void file path **/
	private String path;
	/** Void file URL **/
	private String url;
	/** The URI Document section + Dataset Concept section. */
	private String uriDocConcept;
	
	/**
	 * Constructor. 
	 * Initializes the class variables.
	 *
	 * @param jobConf			the {@link eu.aliada.ckancreation.model.JobConfiguration}
	 *							that contains information to create the Void file.  
	 * @param ckanDatasetUrl	the URL of the dataset in CKAN.
	 * @param numTriples		the number of triples of the dataset.
	 * @param dataFolderName	the folder name where the graph dump is to be stored.
	 * @param dataFolderURL		the URL of the folder.
	 * @since 2.0
	 */
	public DatasetDescFile(final JobConfiguration jobConf, final String ckanDatasetUrl, 
			final ArrayList<DumpFileInfo> datasetDumpFileInfoList, final int numTriples, final String dataFolderName,
			final String dataFolderURL) {
		this.jobConf = jobConf;
		this.ckanDatasetUrl = ckanDatasetUrl;
		this.datasetDumpFileInfoList = datasetDumpFileInfoList.toArray(new DumpFileInfo[]{});
		this.numTriples = numTriples;
		//Compose dataset description file name from the domain name
		String fileName = "";
		fileName = jobConf.getDomainName().replace("http", ""); 
		fileName = fileName.replace(":", "");
		fileName = fileName.replace("/", "");
		fileName = fileName.replace(".", "")  + FILE_EXTENSION;
		this.name = fileName;
		//Compose dataset description file path
		String filePath = dataFolderName + File.separator + fileName;
		//Replace Windows file separator by "/" Java file separator
		filePath = filePath.replace("\\", "/");
		this.path = filePath;

		//Compose dataset description file URL
		this.url = dataFolderURL  + "/" + this.name;
		
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
	}

	/**
	 * Returns the description file name.
	 * 
	 * @return The description file name.
	 * @since 2.0
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the description file name.
	 * 
	 * @param name The description file name.
	 * @since 2.0
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Returns the description file path.
	 * 
	 * @return The description file path.
	 * @since 2.0
	 */
	public String getPath() {
		return path;
	}
	/**
	 * Sets the description file path.
	 * 
	 * @param url The description file path.
	 * @since 2.0
	 */
	public void setPath(final String path) {
		this.path = path;
	}

	/**
	 * Returns the description file URL.
	 * 
	 * @return The description file URL.
	 * @since 2.0
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * Sets the description file URL.
	 * 
	 * @param url The description file URL.
	 * @since 2.0
	 */
	public void setUrl(final String url) {
		this.url = url;
	}
	
	/**
	 * Returns the number of triples of the dataset.
	 * 
	 * @return The number of triples of the dataset.
	 * @since 2.0
	 */
	public int getNumTriples(){
		return numTriples; 
	}
	/**
	 * Sets the number of triples of the dataset.
	 * 
	 * @param numTriples The number of triples of the dataset.
	 * @since 2.0
	 */
	public void setNumTriples(final int numTriples) {
		this.numTriples = numTriples;
	}

	/**
	 * Remove leading and trailing slashes of a given path.
	 *
	 * @param path	path to remove leading and trailing slashes.
	 * @return the path without leading and trailing slashes.
	 * @since 2.0
	 */
	protected String removeLeadingTralingSlashes(final String path){
		String cleanPath = "";
		//Remove leading and trailing spaces
		cleanPath = path.trim();
		//Remove leading slashes
		cleanPath = cleanPath.replaceFirst("^/+", "");
		//Remove trailing slashes
		cleanPath = cleanPath.replaceFirst("/+$", "");
		return cleanPath;
	}

	/**
	 * Returns the current date.
	 * 
	 * @return The current date.
	 * @since 2.0
	 */
	protected String getStringNow(){
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		final Date actualDate = new Date(); 
		return dateFormat.format(actualDate); 
	}
   
	/**
	 * Writes the content in the description file of the dataset.
	 * It creates a Void file.
	 *
	 * @param out	the {@link java.io.BufferedWriter} where to write the content.
	 * @since 2.0
	 */
    public void writeContent(final BufferedWriter out){
		try {
			//Write prefixes info
	    	out.write("@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .");
        	out.newLine();
	    	out.write("@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .");
        	out.newLine();
	    	out.write("@prefix foaf: <http://xmlns.com/foaf/0.1/> .");
        	out.newLine();
	    	out.write("@prefix dcterms: <http://purl.org/dc/terms/> .");
        	out.newLine();
	    	out.write("@prefix void: <http://rdfs.org/ns/void#> .");
        	out.newLine();
	    	out.write("@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .");
        	out.newLine();
        	out.newLine();
        	//
			//Start writing dataset general info
        	//
        	final String datasetUri = "http://" + jobConf.getDomainName();  
	    	out.write("<" + datasetUri + "> rdf:type void:Dataset ;");
	    	out.newLine();
	    	out.write("    foaf:homepage <" + datasetUri  + "> ;");
	    	out.newLine();
	    	out.write("    foaf:page <" + this.ckanDatasetUrl + "> ;");
	    	out.newLine();
	    	out.write("    dcterms:title \"" + jobConf.getDatasetDesc() + "\" ;");
	    	out.newLine();
	    	out.write("    dcterms:description \"" + jobConf.getDatasetLongDesc() + "\" ;");
	    	out.newLine();
	    	out.write("    dcterms:publisher \"" + jobConf.getOrgName().toUpperCase() + "\" ;");
	    	out.newLine();
	    	out.write("    dcterms:creator \"" + jobConf.getOrgName().toUpperCase() + "\" ;");
	    	out.newLine();
	    	out.write("    dcterms:rightsHolder \"" + jobConf.getOrgName().toUpperCase() + "\" ;");
	    	out.newLine();
	    	out.write("    dcterms:source <" + jobConf.getDatasetSourceURL() + "> ;");
	    	out.newLine();
	    	out.write("    dcterms:created \"" + getStringNow() + "\"^^xsd:date ;");
	    	out.newLine();
	    	out.write("    dcterms:contributor \"" + jobConf.getDatasetAuthor() + "\" ;");
	    	out.newLine();
	    	out.write("    dcterms:license <" + jobConf.getLicenseURL() + "> ;");
	    	out.newLine();
	    	out.write("    void:sparqlEndpoint <" + jobConf.getPublicSparqlEndpointUri() + "> ;");
	    	out.newLine();
			out.write("    void:vocabulary <" + jobConf.getOntologyUri() + "> ;");
	    	out.newLine();
	    	//Write number of triples
	    	out.write("    void:triples " + this.numTriples + " ;");
	    	out.newLine();    	
	    	///////////////////////
			//Enumerate dataset dump files 
	    	///////////////////////
	    	if(datasetDumpFileInfoList.length > 0) {
				//A graph may contain several file dumps because of its big size
	    		for(int i=0; i < datasetDumpFileInfoList.length; i++) {
	    			final DumpFileInfo dumpFileInfo = datasetDumpFileInfoList[i];
	    	    	out.write("    void:dataDump <" + dumpFileInfo.getDumpFileUrl() + "> ;");
	    	    	out.newLine();
	    		}
			}
	    	///////////////////////
			//Enumerate subsets
	    	///////////////////////
			for (final Iterator<Subset> iterSubsets = jobConf.getSubsets().iterator(); iterSubsets.hasNext();  ) {
				final Subset subset = iterSubsets.next();
				String uriConceptSubset = "";
				String uriConceptDataset = "";
				if(subset.getUriConceptPart() != null) {
					uriConceptSubset = removeLeadingTralingSlashes(subset.getUriConceptPart());
				} 
				
				if(uriConceptSubset.length() > 0) {
					if(jobConf.getUriConceptPart() != null) {
						uriConceptDataset = removeLeadingTralingSlashes(jobConf.getUriConceptPart());
						if(uriConceptDataset.length() > 0) {
							uriConceptSubset = uriConceptDataset + "/" + uriConceptSubset;
						}
					}
					//Compose URI set part + URI Dataset Concept part + URI Subset Concept part
					final String subsetUri = datasetUri + "/" + jobConf.getUriSetPart() + "/" + uriConceptSubset;
					//Enumerate subset
			    	out.write("    void:subset " + subsetUri + " ;");
			    	out.newLine();
				}
			}
	    	out.write("    .");
	    	out.newLine();    	
	    	//
	    	//End of dataset description
	    	//

	    	///////////////////////
			//Describe each subset
	    	///////////////////////
			for (final Iterator<Subset> iterSubsets = jobConf.getSubsets().iterator(); iterSubsets.hasNext();  ) {
				final Subset subset = iterSubsets.next();
				String uriConceptSubset = "";
				String uriConceptDataset = "";
				if(subset.getUriConceptPart() != null) {
					uriConceptSubset = removeLeadingTralingSlashes(subset.getUriConceptPart());
				} 
				
				if(uriConceptSubset.length() > 0) {
					if(jobConf.getUriConceptPart() != null) {
						uriConceptDataset = removeLeadingTralingSlashes(jobConf.getUriConceptPart());
						if(uriConceptDataset.length() > 0) {
							uriConceptSubset = uriConceptDataset + "/" + uriConceptSubset;
						}
					}
					//Compose URI set part + URI Dataset Concept part + URI Subset Concept part
					final String subsetUri = datasetUri + "/" + jobConf.getUriSetPart() + "/" + uriConceptSubset;
					//Describe subset
			    	out.write(":" + subsetUri + " rdf:type void:Dataset ;");
			    	out.newLine();
			    	out.write("    dcterms:description \"" + subset.getDescription() + "\" ;");
			    	out.newLine();
			    	//Write number of triples
			    	final int numTriples = subset.getGraphNumTriples() + subset.getLinksGraphNumTriples();
			    	out.write("    void:triples " + numTriples + " ;");
			    	out.newLine();    	
			    	out.write("    .");
			    	out.newLine();    	
				}
			}
		} catch (IOException exception) {
			LOGGER.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, this.path);
		}

	}

    /**
	 * Creates the description file of a dataset.
	 * It creates a Void file.
	 *
	 * @return	true if the file has been created correctly. False otherwise.
	 * @since 2.0
	 */
    public boolean createFile(){
    	boolean fileCreated = false;
		try {
			//Remove the dataset description file if it already exists
			File descF;
			descF = new File(this.path);
			if (descF.exists()) {
				descF.delete();
			}
			//Now, create a new one
			final FileWriter fstream = new FileWriter(this.path);
			final BufferedWriter out = new BufferedWriter(fstream);
			//Write its content
			writeContent(out);
			//Close the file
	    	out.close();
	    	fileCreated = true;
		} catch (IOException exception) {
			LOGGER.error(MessageCatalog._00034_FILE_CREATION_FAILURE, exception, this.path);
		}

		return fileCreated;
	}

}
