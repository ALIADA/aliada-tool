// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.impl;

import java.io.*;
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
import eu.aliada.ckancreation.model.CKANListOrgResponse;
import eu.aliada.ckancreation.model.CKANOrgResponse;
import eu.aliada.ckancreation.model.Dataset;
import eu.aliada.ckancreation.model.CKANDatasetResponse;
import eu.aliada.ckancreation.model.Resource;
import eu.aliada.ckancreation.model.CKANResourceResponse;
import eu.aliada.ckancreation.model.Job;
import eu.aliada.ckancreation.model.JobConfiguration;
import eu.aliada.ckancreation.log.MessageCatalog;
import eu.aliada.ckancreation.rdbms.DBConnectionManager;
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
    public CKANOrgResponse getOrganizationCKAN(String orgName){
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(jobConf.getCkanApiURL());
		
		//GET 
		WebTarget resourceWebTarget = webTarget.path("/organization_show");
		Response getResponse = resourceWebTarget
				.queryParam("id", orgName)
				.queryParam("include_datasets", "True")
				.request().header("Authorization", jobConf.getCkanApiKey()).get();
		String orgShowResp = getResponse.readEntity(String.class);
		//Convert JSON representation to Java Object with JACKSON libraries
		CKANOrgResponse cResponse = new CKANOrgResponse();
		ObjectMapper mapper = new ObjectMapper();
		try {
			cResponse = mapper.readValue(orgShowResp, CKANOrgResponse.class);
    	} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
	public CKANOrgResponse updateOrganizationCKAN(String orgId, String orgName, String orgTitle, String orgDescription, String orgImageURL, String orgHomePage, ArrayList<Map<String, String>> lUsers){
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(jobConf.getCkanApiURL());
		
		//Create a Java Object with the organization data
		Organization org = new Organization(orgName, orgId, orgTitle, 
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
		ObjectMapper mapper = new ObjectMapper();
		try {
			orgJSON = mapper.writeValueAsString(org);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
        } 

		//POST 
		WebTarget resourceWebTarget = webTarget.path("/organization_update");
		Response postResponse = resourceWebTarget.request(MediaType.APPLICATION_XML_TYPE).header("Authorization", jobConf.getCkanApiKey()).post(Entity.json(orgJSON));
		String orgUpdateResp = postResponse.readEntity(String.class);
		//Convert JSON representation to Java Object with JACKSON libraries
		CKANOrgResponse cResponse = new CKANOrgResponse();
		try {
			cResponse = mapper.readValue(orgUpdateResp, CKANOrgResponse.class);
    	} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
    public CKANDatasetResponse getDatasetCKAN(String datasetName){
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(jobConf.getCkanApiURL());
		
		//GET 
		WebTarget resourceWebTarget = webTarget.path("/package_show");
		Response getResponse = resourceWebTarget
				.queryParam("id", datasetName)
				.request().header("Authorization", jobConf.getCkanApiKey()).get();
		String datasetShowResp = getResponse.readEntity(String.class);
		//Convert JSON representation to Java Object with JACKSON libraries
		CKANDatasetResponse cResponse = new CKANDatasetResponse();
		ObjectMapper mapper = new ObjectMapper();
		try {
			cResponse = mapper.readValue(datasetShowResp, CKANDatasetResponse.class);
    	} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cResponse;
	}

	/**
	 * Delete the dataset from CKAN Datahub.
	 *
	 * @param packId  the package/dataset id to delete.
	 * @return	
	 * @since 2.0
	 */
	public String deleteDatasetCKAN(String packId){
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(jobConf.getCkanApiURL());
		
		//GET 
		WebTarget resourceWebTarget = webTarget.path("/package_delete");
		Response getResponse = resourceWebTarget
				.queryParam("id", packId)
				.request().header("Authorization", jobConf.getCkanApiKey()).delete();
		String orgDeleteResp = getResponse.readEntity(String.class);
		return orgDeleteResp;	
	}

	/**
	 * Create the dataset in CKAN Datahub.
	 *
	 * @param datasetName  		the dataset name.
	 * @param datasetAuthor		the dataset author.
	 * @param datasetNotes		the dataset description.
	 * @param datasetURL		the dataset URL.
	 * @param ownerOrg			the id of the dataset’s owning organization.
	 * @return	the {@link eu.aliada.ckancreation.model.CKANDatasetResponse}
	 * 			which contains the information of the dataset in CKAN.
	 * @since 2.0
	 */
	public CKANDatasetResponse createDatasetCKAN(String datasetName, String datasetAuthor, String datasetNotes, String datasetURL, String ownerOrg){
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(jobConf.getCkanApiURL());
		
		//Create a Java Object with the dataset data
		Dataset dataset = new Dataset(datasetName, null, datasetAuthor, datasetNotes, 
				datasetURL, ownerOrg);

		//Convert Java Object to JSON representation with JACKSON libraries
		String datasetJSON = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			datasetJSON = mapper.writeValueAsString(dataset);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
        } 

		//POST 
		WebTarget resourceWebTarget = webTarget.path("/package_create");
		Response postResponse = resourceWebTarget.request(MediaType.APPLICATION_XML_TYPE).header("Authorization", jobConf.getCkanApiKey()).post(Entity.json(datasetJSON));
		String datasetCreateResp = postResponse.readEntity(String.class);
		//Convert JSON representation to Java Object with JACKSON libraries
		CKANDatasetResponse cResponse = new CKANDatasetResponse();
		try {
			cResponse = mapper.readValue(datasetCreateResp, CKANDatasetResponse.class);
    	} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cResponse;
	}
	
	/**
	 * Update the dataset in CKAN Datahub.
	 *
	 * @param datasetName  		the dataset name.
	 * @param datasetAuthor		the dataset author.
	 * @param datasetNotes		the dataset description.
	 * @param datasetURL		the dataset URL.
	 * @param ownerOrg			the id of the dataset’s owning organization.
	 * @return	the {@link eu.aliada.ckancreation.model.CKANDatasetResponse}
	 * 			which contains the information of the dataset in CKAN.
	 * @since 2.0
	 */
	public CKANDatasetResponse updateDatasetCKAN(String datasetName, String datasetAuthor, String datasetNotes, String datasetURL, String ownerOrg){
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(jobConf.getCkanApiURL());
		
		//Create a Java Object with the dataset data
		Dataset dataset = new Dataset(datasetName, null, datasetAuthor, datasetNotes, 
				datasetURL, ownerOrg);

		//Convert Java Object to JSON representation with JACKSON libraries
		String datasetJSON = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			datasetJSON = mapper.writeValueAsString(dataset);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
        } 

		//POST 
		WebTarget resourceWebTarget = webTarget.path("/package_update");
		Response postResponse = resourceWebTarget.request(MediaType.APPLICATION_XML_TYPE).header("Authorization", jobConf.getCkanApiKey()).post(Entity.json(datasetJSON));
		String datasetCreateResp = postResponse.readEntity(String.class);
		//Convert JSON representation to Java Object with JACKSON libraries
		CKANDatasetResponse cResponse = new CKANDatasetResponse();
		try {
			cResponse = mapper.readValue(datasetCreateResp, CKANDatasetResponse.class);
    	} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
	public CKANResourceResponse createResourceCKAN(String datasetId, String resName, String resDescription, String resFormat, String resUrl, String resType){
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(jobConf.getCkanApiURL());
		
		//Create a Java Object with the resource data
		Resource resource = new Resource(datasetId, resName, 
				resDescription, resFormat, resUrl, resType);

		//Convert Java Object to JSON representation with JACKSON libraries
		String resourceJSON = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			resourceJSON = mapper.writeValueAsString(resource);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
        } 

		//POST 
		WebTarget resourceWebTarget = webTarget.path("/resource_create");
		Response postResponse = resourceWebTarget.request(MediaType.APPLICATION_XML_TYPE).header("Authorization", jobConf.getCkanApiKey()).post(Entity.json(resourceJSON));
		String datasetCreateResp = postResponse.readEntity(String.class);
		//Convert JSON representation to Java Object with JACKSON libraries
		CKANResourceResponse cResponse = new CKANResourceResponse();
		try {
			cResponse = mapper.readValue(datasetCreateResp, CKANResourceResponse.class);
    	} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cResponse;
	}
	
	/**
	 * Check if the dataset already exists in CKAN Datahub.
	 *
	 * @param packages  the existing packages/datasets in CKAN Datahub.
	 * @param packName  the new package/dataset name.
	 * @return true if the dataset already exists in CKAN Datahub. 					
	 * @since 2.0
	 */
	public boolean ckeckDatasetExist( ArrayList<Map<String, String>> packages, String packName) {
		//Convert the package name to lower case, as CKAN does not allow capital letters in the package name
		packName = packName.toLowerCase();
		boolean found = false;
		//Look for a package with the same name
		for (Iterator<Map<String, String>> iterPackages = packages.iterator(); iterPackages.hasNext();  ) {
			Map<String, String> pack = iterPackages.next();
			if(pack.get("name").equals(packName)) {
				found = true;
			}
		}
		return found;
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
		String orgId = cOrgResponse.getResult().getId();
		ArrayList<Map<String, String>> users = cOrgResponse.getResult().getUsers();
		//Update organization data
		cOrgResponse = updateOrganizationCKAN(orgId, jobConf.getOrgName(), 
				jobConf.getOrgTitle(), jobConf.getOrgDescription(), 
				jobConf.getOrgImageURL(), jobConf.getOrgHomePage(), users);
		if(cOrgResponse.getSuccess() == "true") {
			//Update DB with ckan_org_url
			String ckanOrgUrl = "http://datahub.io/organization/" + cOrgResponse.getResult().getName();
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
	public CKANDatasetResponse createDataset(CKANOrgResponse cOrgResponse){
		//Check if dataset already exists. If it exists, remove it first. 
		/*if(ckeckDatasetExist(cOrgResponse.getResult().getPackages(), jobConf.getDatasetName())) {
			deleteDatasetCKAN(jobConf.getDatasetName());
		}*/

		//Check if dataset already exists. If it exists, update it. 
		CKANDatasetResponse cGetDataResponse = getDatasetCKAN(jobConf.getDatasetName());
		CKANDatasetResponse cDataResponse;
		if(cGetDataResponse.getSuccess() == "true") {
			//deleteDatasetCKAN(cGetDataResponse.getResult().getId());
			cDataResponse = updateDatasetCKAN(jobConf.getDatasetName(), 
						jobConf.getDatasetAuthor(),	jobConf.getDatasetNotes(), jobConf.getDatasetURL(), cOrgResponse.getResult().getId());
		} else {
			//Create dataset. (http://datahub.io/api/action/package_create)
			cDataResponse = createDatasetCKAN(jobConf.getDatasetName(), 
					jobConf.getDatasetAuthor(),	jobConf.getDatasetNotes(), jobConf.getDatasetURL(), cOrgResponse.getResult().getId());
		}
		if(cDataResponse.getSuccess() == "true") {
			//Update DB with ckan_dataset_url
			String ckanDatasetUrl = "http://datahub.io/dataset/" + cDataResponse.getResult().getName();
			dbConn.updateCkanOrgUrl(jobConf.getId(), ckanDatasetUrl);
		} else {
			String message;
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
		//Create resource Void file.
/*		String voidFilePath = createVoidFile();
		final File voidFile = new File(voidFilePath);
		final String voidFileName = voidFile.getName();
		cResourceResponse = createResource(cDataResponse.getResult().getId(), 
				voidFileName,
				"Void file describing the main features of the " + cDataResponse.getResult().getName() + " dataset",
				"application/octet-stream", 
				voidFilePath,
				"file");*/

		//Create resource dataset dumps.
		//Un dump por graph existente => añadir graph_name en tabla graphs
		//????Si MARC: Authority data Bibliographic data
/*		String[] dumpFilePath = createDatasetDumps();
		for (int i=0; i<dumpFilePath.length;i++) {
			final File dumpFile = new File(dumpFilePath[i]);
			final String dumpFileName = dumpFile.getName();
			cResourceResponse = createResource(cDataResponse.getResult().getId(), 
					"Datadump of ",
					"Datadump of the authority data of the " + cDataResponse.getResult().getName() + " dataset",
					"application/x-bzip2", 
					dumpFilePath[i],
					"file");
		}*/
		
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
		CKANOrgResponse cOrgResponse = updateOrganization();
		//DATASET
		if(cOrgResponse.getSuccess() == "true"){
			CKANDatasetResponse cDataResponse = createDataset(cOrgResponse);
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
