// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.impl;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import eu.aliada.ckancreation.model.Organization;
import eu.aliada.ckancreation.model.CKANOrgResponse;
import eu.aliada.ckancreation.model.Dataset;
import eu.aliada.ckancreation.model.CKANDatasetResponse;
import eu.aliada.ckancreation.model.Resource;
import eu.aliada.ckancreation.model.CKANResourceResponse;
import eu.aliada.ckancreation.model.Job;
import eu.aliada.ckancreation.model.JobConfiguration;
import eu.aliada.ckancreation.model.DumpFileInfo;
import eu.aliada.ckancreation.log.MessageCatalog;
import eu.aliada.ckancreation.rdbms.DBConnectionManager;
import eu.aliada.ckancreation.impl.DataDump;
import eu.aliada.ckancreation.impl.VoidFile;
import eu.aliada.shared.log.Log;

/**
 * CKAN Creation implementation. 
 * It creates a new Dataset in CKAN Datahub. Removes it first if it already exists.
 * It also updates the organization information in CKAN Datahub.   
 * 
 * @author Idoia Murua
 * @since 2.0
 */
public class CKANCreation {
	/** For logging. */
	private static final Log LOGGER  = new Log(CKANCreation.class);
	private final JobConfiguration jobConf;
	private final DBConnectionManager dbConn;
	
	/**
	 * Constructor. 
	 * Initializes the class variables.
	 *
	 * @param jobConf		the {@link eu.aliada.ckancreation.model.JobConfiguration}
	 *						that contains information to create the dataset in CKAN Datahub.  
	 * @param db			The DDBB connection. 
	 * @since 2.0
	 */
	public CKANCreation(final JobConfiguration jobConf, final DBConnectionManager dbConn) {
		this.jobConf = jobConf;
		this.dbConn = dbConn;
	}
	
	/**
	 * Gets the organization information from CKAN Datahub.
	 *
	 * @param orgName	the organization name.
	 * @return	the {@link eu.aliada.ckancreation.model.CKANOrgResponse}
	 * 			which contains the information of the organization in CKAN.
	 * @since 2.0
	 */
    public CKANOrgResponse getOrganizationCKAN(final String orgName){
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client.target(jobConf.getCkanApiURL());
		
		//GET 
		final WebTarget resourceWebTarget = webTarget.path("/organization_show");
		final Response getResponse = resourceWebTarget
				.queryParam("id", orgName)
				.queryParam("include_datasets", "True")
				.request().header("Authorization", jobConf.getCkanApiKey()).get();
		final String orgShowResp = getResponse.readEntity(String.class);
		//Convert JSON representation to Java Object with JACKSON libraries
		CKANOrgResponse cResponse = new CKANOrgResponse();
		final ObjectMapper mapper = new ObjectMapper();
		try {
			cResponse = mapper.readValue(orgShowResp, CKANOrgResponse.class);
    	} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00032_OBJECT_CONVERSION_FAILURE, exception);
		}
		return cResponse;
	}

	/**
	 * Update the organization information in CKAN Datahub.
	 *
	 * @param orgId     		the organization id.
	 * @param orgName			the organization name.
	 * @param orgTitle			the organization title.
	 * @param orgDescription	the organization description.
	 * @param orgImageURL		the organization image URL.
	 * @param orgHomePage		the organization home page.
	 * @param lUsers			the organization's users and their capacity.
	 * @return	the {@link eu.aliada.ckancreation.model.CKANOrgResponse}
	 * 			which contains the information of the organization in CKAN.
	 * @since 2.0
	 */
	public CKANOrgResponse updateOrganizationCKAN(final String orgId, final String orgName, 
			final String orgTitle, final String orgDescription, final String orgImageURL, 
			final String orgHomePage, final ArrayList<Map<String, String>> lUsers){
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client.target(jobConf.getCkanApiURL());
		
		//Create a Java Object with the organization data
		final Organization org = new Organization(orgName, orgId, orgTitle, 
											orgDescription, orgImageURL, orgHomePage);
		/////////////////////////******************///////////////////
		///VERY IMPORTANT!!!!!
		//Update also the users dictionary(including "capacity" attribute), otherwise we lose rights for updating the organization
		for (Iterator<Map<String, String>> iterUsers = lUsers.iterator(); iterUsers.hasNext();  ) {
			Map<String, String> user = iterUsers.next();
			org.setUser(user.get("name"), user.get("capacity"));
		}

		//Convert Java Object to JSON representation with JACKSON libraries
		String orgJSON = "";
		final ObjectMapper mapper = new ObjectMapper();
		try {
			orgJSON = mapper.writeValueAsString(org);
    	} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00032_OBJECT_CONVERSION_FAILURE, exception);
		}

		//POST 
		final WebTarget resourceWebTarget = webTarget.path("/organization_update");
		final Response postResponse = resourceWebTarget
				.request(MediaType.APPLICATION_XML_TYPE)
				.header("Authorization", jobConf.getCkanApiKey()).post(Entity.json(orgJSON));
		final String orgUpdateResp = postResponse.readEntity(String.class);
		//Convert JSON representation to Java Object with JACKSON libraries
		CKANOrgResponse cResponse = new CKANOrgResponse();
		try {
			cResponse = mapper.readValue(orgUpdateResp, CKANOrgResponse.class);
    	} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00032_OBJECT_CONVERSION_FAILURE, exception);
		}
		return cResponse;
	}
	
	/**
	 * Gets the organization information from CKAN Datahub.
	 *
	 * @param datasetName	the dataset name.
	 * @return	the {@link eu.aliada.ckancreation.model.CKANDatasetResponse}
	 * 			which contains the information of the dataset in CKAN.
	 * @since 2.0
	 */
    public CKANDatasetResponse getDatasetCKAN(final String datasetName){
    	final Client client = ClientBuilder.newClient();
    	final WebTarget webTarget = client.target(jobConf.getCkanApiURL());
		
		//GET 
    	final WebTarget resourceWebTarget = webTarget.path("/package_show");
    	final Response getResponse = resourceWebTarget
				.queryParam("id", datasetName)
				.request().header("Authorization", jobConf.getCkanApiKey()).get();
		final String datasetShowResp = getResponse.readEntity(String.class);
		//Convert JSON representation to Java Object with JACKSON libraries
		CKANDatasetResponse cResponse = new CKANDatasetResponse();
		final ObjectMapper mapper = new ObjectMapper();
		try {
			cResponse = mapper.readValue(datasetShowResp, CKANDatasetResponse.class);
    	} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00032_OBJECT_CONVERSION_FAILURE, exception);
		}
		return cResponse;
	}

	/**
	 * Delete the dataset from CKAN Datahub.
	 * The package_delete method of CKAN only sets the "state" field of the dataset to the "deleted" value.
	 * It does not remove it permanently.
	 *
	 * @param packId  the package/dataset id to delete.
	 * @return	the response of CKAN.
	 * @since 2.0
	 */
	public String deleteDatasetCKAN(final String packId){
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client.target(jobConf.getCkanApiURL());
		
		//Create a Java Object with the dataset data
		final Dataset dataset = new Dataset();
		dataset.setState("deleted");
		dataset.setId(packId);

		//Convert Java Object to JSON representation with JACKSON libraries
		String datasetJSON = "";
		final ObjectMapper mapper = new ObjectMapper();
		try {
			datasetJSON = mapper.writeValueAsString(dataset);
    	} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00032_OBJECT_CONVERSION_FAILURE, exception);
		}

		//POST
		//The package_delete method of CKAN only sets the "state" field of the dataset 
		//to the "deleted" value. It does not remove it permanently.
		final WebTarget resourceWebTarget = webTarget.path("/package_delete");
		final Response getResponse = resourceWebTarget
				.queryParam("id", packId)
				.request().header("Authorization", jobConf.getCkanApiKey()).post(Entity.json(datasetJSON));
		final String orgDeleteResp = getResponse.readEntity(String.class);
		return orgDeleteResp;	
	}


	/**
	 * Create the dataset in CKAN Datahub.
	 *
	 * @param datasetName  		the dataset name.
	 * @param datasetAuthor		the dataset author.
	 * @param datasetNotes		the dataset description.
	 * @param datasetSourceURL	the URL of the dataset´s source.
	 * @param ownerOrg			the id of the dataset’s owning organization.
	 * @param licenseId			the id of the dataset’s license.
	 * @return	the {@link eu.aliada.ckancreation.model.CKANDatasetResponse}
	 * 			which contains the information of the dataset in CKAN.
	 * @since 2.0
	 */
	public CKANDatasetResponse createDatasetCKAN(final String datasetName, final String datasetAuthor, 
			final String datasetNotes, final String datasetSourceURL, final String ownerOrg, final String licenseId){
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client.target(jobConf.getCkanApiURL());
		
		//Create a Java Object with the dataset data
		final Dataset dataset = new Dataset(datasetName, null, datasetAuthor, datasetNotes, 
				datasetSourceURL, ownerOrg, licenseId);

		//Convert Java Object to JSON representation with JACKSON libraries
		String datasetJSON = "";
		final ObjectMapper mapper = new ObjectMapper();
		try {
			datasetJSON = mapper.writeValueAsString(dataset);
    	} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00032_OBJECT_CONVERSION_FAILURE, exception);
		}

		//POST 
		final WebTarget resourceWebTarget = webTarget.path("/package_create");
		final Response postResponse = resourceWebTarget
				.request(MediaType.APPLICATION_XML_TYPE)
				.header("Authorization", jobConf.getCkanApiKey()).post(Entity.json(datasetJSON));
		final String datasetCreateResp = postResponse.readEntity(String.class);
		//Convert JSON representation to Java Object with JACKSON libraries
		CKANDatasetResponse cResponse = new CKANDatasetResponse();
		try {
			cResponse = mapper.readValue(datasetCreateResp, CKANDatasetResponse.class);
    	} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00032_OBJECT_CONVERSION_FAILURE, exception);
		}
		return cResponse;
	}
	
	/**
	 * Update the dataset in CKAN Datahub.
	 *
	 * @param datasetName  		the dataset name.
	 * @param datasetAuthor		the dataset author.
	 * @param datasetNotes		the dataset description.
	 * @param datasetSourceURL	the URL of the dataset´s source.
	 * @param ownerOrg			the id of the dataset’s owning organization.
	 * @param licenseId			the id of the dataset’s license.
	 * @return	the {@link eu.aliada.ckancreation.model.CKANDatasetResponse}
	 * 			which contains the information of the dataset in CKAN.
	 * @since 2.0
	 */
	public CKANDatasetResponse updateDatasetCKAN(final String datasetName, final String datasetAuthor, 
			final String datasetNotes, final String datasetSourceURL, final String ownerOrg,  final String licenseId){
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client.target(jobConf.getCkanApiURL());
		
		//Create a Java Object with the dataset data
		final Dataset dataset = new Dataset(datasetName, null, datasetAuthor, datasetNotes, 
				datasetSourceURL, ownerOrg, licenseId, "active");

		//Convert Java Object to JSON representation with JACKSON libraries
		String datasetJSON = "";
		final ObjectMapper mapper = new ObjectMapper();
		try {
			datasetJSON = mapper.writeValueAsString(dataset);
    	} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00032_OBJECT_CONVERSION_FAILURE, exception);
		}

		//POST 
		final WebTarget resourceWebTarget = webTarget.path("/package_update");
		final Response postResponse = resourceWebTarget
				.request(MediaType.APPLICATION_XML_TYPE)
				.header("Authorization", jobConf.getCkanApiKey()).post(Entity.json(datasetJSON));
		final String datasetCreateResp = postResponse.readEntity(String.class);
		//Convert JSON representation to Java Object with JACKSON libraries
		CKANDatasetResponse cResponse = new CKANDatasetResponse();
		try {
			cResponse = mapper.readValue(datasetCreateResp, CKANDatasetResponse.class);
    	} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00032_OBJECT_CONVERSION_FAILURE, exception);
		}
		return cResponse;
	}
	
	/**
	 * Add the resource to a dataset in CKAN Datahub.
	 *
	 * @param datasetId  		the dataset/package id.
	 * @param resName			the resource name.
	 * @param resDescription	the resource description.
	 * @param resFormat			the resource format.
	 * @param resUrl			the resource URL.
	 * @param resType			the resource type (file, api, example, etc.)
	 * @return	the {@link eu.aliada.ckancreation.model.CKANResourceResponse}
	 * 			which contains the information of the resource in CKAN.
	 * @since 2.0
	 */
	public CKANResourceResponse createResourceCKAN(final String datasetId, final String resName, 
			final String resDescription, final String resFormat, final String resUrl, final String resType){
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client.target(jobConf.getCkanApiURL());
		
		//Create a Java Object with the resource data
		final Resource resource = new Resource(datasetId, resName, 
				resDescription, resFormat, resUrl, resType);

		//Convert Java Object to JSON representation with JACKSON libraries
		String resourceJSON = "";
		final ObjectMapper mapper = new ObjectMapper();
		try {
			resourceJSON = mapper.writeValueAsString(resource);
    	} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00032_OBJECT_CONVERSION_FAILURE, exception);
		}

		//POST 
		final WebTarget resourceWebTarget = webTarget.path("/resource_create");
		final Response postResponse = resourceWebTarget
				.request(MediaType.APPLICATION_XML_TYPE)
				.header("Authorization", jobConf.getCkanApiKey()).post(Entity.json(resourceJSON));
		final String datasetCreateResp = postResponse.readEntity(String.class);
		//Convert JSON representation to Java Object with JACKSON libraries
		CKANResourceResponse cResponse = new CKANResourceResponse();
		try {
			cResponse = mapper.readValue(datasetCreateResp, CKANResourceResponse.class);
    	} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00032_OBJECT_CONVERSION_FAILURE, exception);
		}
		return cResponse;
	}
	
	/**
	 * Update the organization information in CKAN Datahub.
	 *
	 * @return	the {@link eu.aliada.ckancreation.model.CKANOrgResponse}
	 * 			which contains the information of the organization in CKAN.
	 * @since 2.0
	 */
	public CKANOrgResponse updateOrganization(){
		//Get the organization data first
		CKANOrgResponse cOrgResponse = getOrganizationCKAN(jobConf.getOrgName());
		if(cOrgResponse.getSuccess() == "false") {
			LOGGER.debug(MessageCatalog._00036_GET_ORGANIZATION_CKAN_FAILURE, cOrgResponse.getError().getMessage());
			return cOrgResponse;
		}
		//Obtain the organization ID in CKAN
		final String orgId = cOrgResponse.getResult().getId();
		final ArrayList<Map<String, String>> users = cOrgResponse.getResult().getUsers();
		//Update organization data
		cOrgResponse = updateOrganizationCKAN(orgId, jobConf.getOrgName(), 
				jobConf.getOrgTitle(), jobConf.getOrgDescription(), 
				jobConf.getOrgImageURL(), jobConf.getOrgHomePage(), users);
		if(cOrgResponse.getSuccess() == "true") {
			//Update DB with ckan_org_url
			final String ckanOrgUrl = "http://datahub.io/organization/" + cOrgResponse.getResult().getName();
			dbConn.updateCkanOrgUrl(jobConf.getId(), ckanOrgUrl);
		} else {
			LOGGER.debug(MessageCatalog._00037_CREATE_ORGANIZATION_CKAN_FAILURE, cOrgResponse.getError().getMessage());
		}
		return cOrgResponse;
	}
	
	/**
	 * Create the dataset in CKAN Datahub.
	 *
	 * @param cOrgResponse	the {@link eu.aliada.ckancreation.model.CKANOrgResponse}
	 * 						which contains the information of the organization in CKAN.
	 * @return	the {@link eu.aliada.ckancreation.model.CKANDatasetResponse}
	 * 			which contains the information of the dataset in CKAN.
	 * @since 2.0
	 */
	public CKANDatasetResponse createDataset(final CKANOrgResponse cOrgResponse){
		//Check if dataset already exists. If it exists, update it. 
		final CKANDatasetResponse cGetDataResponse = getDatasetCKAN(jobConf.getDatasetName());
		CKANDatasetResponse cDataResponse;
		if(cGetDataResponse.getSuccess() == "true") {
			//Update dataset. (http://datahub.io/api/action/package_update)
			//When updating the dataset, the resources already associated to it are removed automatically
			cDataResponse = updateDatasetCKAN(jobConf.getDatasetName(), 
					jobConf.getDatasetAuthor(),	jobConf.getDatasetNotes(), 
					jobConf.getDatasetSourceURL(), cOrgResponse.getResult().getId(),
					jobConf.getLicenseId());
		} else {
			//Create dataset. (http://datahub.io/api/action/package_create)
			cDataResponse = createDatasetCKAN(jobConf.getDatasetName(), 
					jobConf.getDatasetAuthor(),	jobConf.getDatasetNotes(), 
					jobConf.getDatasetSourceURL(), cOrgResponse.getResult().getId(),
					jobConf.getLicenseId());
		}
			
		if(cDataResponse.getSuccess() == "true") {
			//Update DB with ckan_dataset_url
			final String ckanDatasetUrl = "http://datahub.io/dataset/" + cDataResponse.getResult().getName();
			dbConn.updateCkanOrgUrl(jobConf.getId(), ckanDatasetUrl);
		} else {
			final String message;
			if((cDataResponse.getError().getName() != null ) && (cDataResponse.getError().getName().length > 0)){
				message = cDataResponse.getError().getName()[0];
			}else {
				message = cDataResponse.getError().getMessage();
			}
			LOGGER.debug(MessageCatalog._00038_CREATE_DATASET_CKAN_FAILURE, message);
		}
		return cDataResponse;
	}

	/**
	 * Create the dataset in CKAN Datahub.
	 *
	 * @param cDataResponse	the {@link eu.aliada.ckancreation.model.CKANDatasetResponse}
	 * 						which contains the information of the dataset in CKAN.
	 * @return	the {@link eu.aliada.ckancreation.model.CKANResourceResponse}
	 * 			which contains the information of the resource in CKAN.
	 * @since 2.0
	 */
	public CKANResourceResponse createResources(CKANDatasetResponse cDataResponse){
		//Add new resources to the dataset (http://datahub.io/api/action/resource_create)
		//Create resource SPARQL endpoint. 
		CKANResourceResponse cResourceResponse = createResourceCKAN(cDataResponse.getResult().getId(), 
				"Sparql Endpoint",
				"SPARQL endpoint for " + cDataResponse.getResult().getName() + " dataset",
				"api/sparql", 
				jobConf.getSPARQLEndpoint(),
				"api");
		
		if(cResourceResponse.getSuccess() == "false") {
			LOGGER.debug(MessageCatalog._00039_CREATE_RESOURCE_CKAN_FAILURE, cResourceResponse.getError().getMessage());
		}

		//Create the resource for the dataset dumps.
		//A graph may contain several file dumps because of its big size
		final DataDump dataDump = new DataDump(jobConf);
		final ArrayList<DumpFileInfo> dumpFileInfos = dataDump.createDatasetDump(jobConf.getGraphURI(), 
				jobConf.getDumpPath(),  jobConf.getDumpUrl());
		final DumpFileInfo[] listDumpFileInfo= dumpFileInfos.toArray(new DumpFileInfo[]{});
		if(listDumpFileInfo.length > 0) {
			//A graph may contain several file dumps because of its big size
    		for(int i=0; i < listDumpFileInfo.length; i++) {
    			final DumpFileInfo dumpFileInfo = listDumpFileInfo[i];
    			String fileIndex = "";
    			if(listDumpFileInfo.length > 1) {
    				//If there are more than one dump file per graph, number it
    				fileIndex = " " + (i+1);
    			}
				cResourceResponse = createResourceCKAN(cDataResponse.getResult().getId(), 
						"Dataset dump in N-Triples format" + fileIndex,
						"Dataset dump in N-Triples format.",
						dumpFileInfo.getDumpFileFormat(), 
						dumpFileInfo.getDumpFileUrl(),
						"file");
				if(cResourceResponse.getSuccess() == "false") {
					LOGGER.debug(MessageCatalog._00039_CREATE_RESOURCE_CKAN_FAILURE, cResourceResponse.getError().getMessage());
				}
    		}
		}
		
		//Create resource Void file.
		VoidFile voidFile = new VoidFile(jobConf, dbConn);
		final File voidFileCreated = voidFile.createVoidFile(jobConf.getGraphURI(), jobConf.getDumpPath(), 
				jobConf.getDumpUrl());
		final String voidFileUrl = jobConf.getDumpUrl()  + "/" + voidFileCreated.getName();
		String voidFilePath = null;
		try {
			voidFilePath = voidFileCreated.getCanonicalPath();
		} catch (IOException exception) {
			LOGGER.error(MessageCatalog._00034_FILE_ACCESS_FAILURE, exception, voidFileCreated.getName());
		}
		cResourceResponse = createResourceCKAN(cDataResponse.getResult().getId(), 
				voidFileCreated.getName(),
				"Void file describing the main features of the " + cDataResponse.getResult().getName() + " dataset",
				"application/octet-stream", 
				voidFileUrl,
				"file");
		if(cResourceResponse.getSuccess() == "true") {
			//Update DB with void_file_path, void_file_url
			dbConn.updateVoidFile(jobConf.getId(), voidFilePath, voidFileUrl);
		} else {
			LOGGER.debug(MessageCatalog._00039_CREATE_RESOURCE_CKAN_FAILURE, cResourceResponse.getError().getMessage());
		}

		return cResourceResponse;
	}

	/**
	 * It creates a new Dataset in CKAN Datahub. Removes it first if it already exists.
	 * It also updates the organization information in CKAN Datahub.
	 *
	 * @return the {@link eu.aliada.ckancreation.model.job} created.  					
	 * @since 2.0
	 */
	public Job newDataset() {
		LOGGER.debug(MessageCatalog._00030_STARTING);
		//Update job start-date in DDBB
		dbConn.updateJobStartDate(jobConf.getId());
		
		//ORGANIZATION
		final CKANOrgResponse cOrgResponse = updateOrganization();
		//DATASET
		if(cOrgResponse.getSuccess() == "true"){
			final CKANDatasetResponse cDataResponse = createDataset(cOrgResponse);
			//DATASET RESOURCES
			if(cDataResponse.getSuccess() == "true"){
				createResources(cDataResponse);
			}
		}

		//Update job end_date of DDBB
		LOGGER.debug(MessageCatalog._00057_UPDATING_JOB_DDBB, jobConf.getId());
		dbConn.updateJobEndDate(jobConf.getId());
		final Job job = dbConn.getJob(jobConf.getId());
		LOGGER.debug(MessageCatalog._00041_STOPPED);
		return job;
	}
}
