// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.impl;

import java.io.*;
import java.nio.file.Files;
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

import eu.aliada.ckancreation.model.Organization;
import eu.aliada.ckancreation.model.CKANOrgResponse;
import eu.aliada.ckancreation.model.Dataset;
import eu.aliada.ckancreation.model.CKANDatasetResponse;
import eu.aliada.ckancreation.model.Resource;
import eu.aliada.ckancreation.model.CKANResourceResponse;
import eu.aliada.ckancreation.model.Job;
import eu.aliada.ckancreation.model.JobConfiguration;
import eu.aliada.ckancreation.model.DumpFileInfo;
import eu.aliada.ckancreation.model.Subset;
import eu.aliada.ckancreation.log.MessageCatalog;
import eu.aliada.ckancreation.rdbms.DBConnectionManager;
import eu.aliada.ckancreation.impl.DataDump;
import eu.aliada.ckancreation.impl.DatasetDescFile;
import eu.aliada.shared.log.Log;
import eu.aliada.shared.rdfstore.RDFStoreDAO;

/**
 * CKAN Creation implementation. 
 * It creates a new Dataset in CKAN Datahub. 
 * Removes it first if it already exists.
 * It also updates the organization information in CKAN Datahub.   
 * 
 * @author Idoia Murua
 * @since 2.0
 */
public class CKANCreation {
	/** For logging. */
	private static final Log LOGGER  = new Log(CKANCreation.class);
	/** Folder where to store the dataset sump files **/
	static final String DUMPS_FOLDER = "datadumps"; 
	/** Job Configuration which contains required parameters. **/
	private final JobConfiguration jobConf;
	/** Database connection **/
	private final DBConnectionManager dbConn;
	/** The folder name where the dataset dumps and the 
	 * description file will reside **/
	private String dataFolderName;
	/** The folder URL where the dataset dumps and the description file will reside **/
	private final String dataFolderURL; 
	/** the URL of the organization in CKAN. **/
	private String ckanOrgUrl;	
	/** the URL of the dataset in CKAN. **/
	private String ckanDatasetUrl;
	/** the info of the dataset dump files. */
	private final ArrayList<DumpFileInfo> datasetDumpFileInfoList = new ArrayList<DumpFileInfo>(); 

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
		//Create the folder where the data dumps 
		//and the dataset description file will reside
		dataFolderName = jobConf.getDomainName().replace("http", ""); 
		dataFolderName = dataFolderName.replace(":", "");
		dataFolderName = dataFolderName.replace("/", "");
		dataFolderName = dataFolderName.replace(".", "");
		dataFolderName = jobConf.getVirtHttpServRoot() + File.separator + dataFolderName +
				File.separator + DUMPS_FOLDER;
		final File fFolder = new File(dataFolderName);
		if (!fFolder.exists()) {
			fFolder.mkdir();
		}
		dataFolderURL = "http://" + jobConf.getDomainName() + "/" + DUMPS_FOLDER;
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
		//Update also the users dictionary(including "capacity" attribute), 
		//otherwise we lose rights for updating the organization
		for (final Iterator<Map<String, String>> iterUsers = lUsers.iterator(); iterUsers.hasNext();  ) {
			final Map<String, String> user = iterUsers.next();
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
	 * The package_delete method of CKAN only sets the "state" field 
	 * of the dataset to the "deleted" value.
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
	 * @param dataset	the {@link eu.aliada.ckancreation.model.Dataset} object to create.
	 * @return	the {@link eu.aliada.ckancreation.model.CKANDatasetResponse}
	 * 			which contains the information of the dataset in CKAN.
	 * @since 2.0
	 */
	public CKANDatasetResponse createDatasetCKAN(final Dataset dataset){
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client.target(jobConf.getCkanApiURL());
		
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
	 * @param dataset	the {@link eu.aliada.ckancreation.model.Dataset} object to update.
	 * @return	the {@link eu.aliada.ckancreation.model.CKANDatasetResponse}
	 * 			which contains the information of the dataset in CKAN.
	 * @since 2.0
	 */
	public CKANDatasetResponse updateDatasetCKAN(final Dataset dataset){
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client.target(jobConf.getCkanApiURL());
		
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
			LOGGER.debug("name=" + resource.getName() + 
					", packageId=" + resource.getPackageId() + 
					", description=" + resource.getDescription() + 
					", format=" + resource.getFormat() + 
					", url=" +  resource.getFormat() + 
					", type=" + resource.getType());
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
			LOGGER.debug("name=" + resource.getName() + 
					", packageId=" + resource.getPackageId() + 
					", description=" + resource.getDescription() + 
					", format=" + resource.getFormat() + 
					", url=" +  resource.getFormat() + 
					", type=" + resource.getType());
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
		if(cOrgResponse.getSuccess().equals("false")) {
			LOGGER.error(MessageCatalog._00036_GET_ORGANIZATION_CKAN_FAILURE, cOrgResponse.getError().getMessage());
			return cOrgResponse;
		}
		//Obtain the organization ID in CKAN
		final String orgId = cOrgResponse.getResult().getId();
		final ArrayList<Map<String, String>> users = cOrgResponse.getResult().getUsers();
		//Update organization data
		cOrgResponse = updateOrganizationCKAN(orgId, jobConf.getOrgName(), 
				jobConf.getOrgTitle(), jobConf.getOrgDescription(), 
				jobConf.getOrgImageURL(), jobConf.getOrgHomePage(), users);
		if(cOrgResponse.getSuccess().equals("true")) {
			//Update DB with ckan_org_url
			this.ckanOrgUrl = "http://datahub.io/organization/" + cOrgResponse.getResult().getName();
			dbConn.updateCkanOrgUrl(jobConf.getId(), this.ckanOrgUrl);
			LOGGER.debug(MessageCatalog._00042_CKAN_ORG_UPDATED);
		} else {
			LOGGER.error(MessageCatalog._00037_UPDATE_ORGANIZATION_CKAN_FAILURE, cOrgResponse.getError().getMessage());
		}
		return cOrgResponse;
	}
	
	/**
	 * Create the dataset in CKAN Datahub.
	 *
	 * @param cOrgResponse	the {@link eu.aliada.ckancreation.model.CKANOrgResponse}
	 * 						which contains the information of the organization in CKAN.
	 * @param numTriples	the number of triples of the dataset.
	 * @return	the {@link eu.aliada.ckancreation.model.CKANDatasetResponse}
	 * 			which contains the information of the dataset in CKAN.
	 * @since 2.0
	 */
	public CKANDatasetResponse createDataset(final CKANOrgResponse cOrgResponse, final int numTriples){
		//Create a Java Object with the dataset data
		final Dataset dataset = new Dataset(jobConf.getCkanDatasetName(), null,
				jobConf.getDatasetAuthor(),	jobConf.getDatasetLongDesc(), 
				jobConf.getDatasetSourceURL(), cOrgResponse.getResult().getId(),
				jobConf.getLicenseCKANId(), "active", numTriples);

		//Check if dataset already exists. If it exists, update it. 
		final CKANDatasetResponse cGetDataResponse = getDatasetCKAN(jobConf.getCkanDatasetName());
		CKANDatasetResponse cDataResponse;
		if(cGetDataResponse.getSuccess().equals("true")) {
			//Update dataset. (http://datahub.io/api/action/package_update)
			//When updating the dataset, the resources already 
			//associated to it are removed automatically
			cDataResponse = updateDatasetCKAN(dataset);
		} else {
			//Create dataset. (http://datahub.io/api/action/package_create)
			cDataResponse = createDatasetCKAN(dataset);
		}
			
		if(cDataResponse.getSuccess().equals("true")) {
			//Update DB with ckan_dataset_url
			this.ckanDatasetUrl = "http://datahub.io/dataset/" + cDataResponse.getResult().getName();
			dbConn.updateCkanDatasetUrl(jobConf.getId(), this.ckanDatasetUrl);
			LOGGER.debug(MessageCatalog._00043_CKAN_DATASET_CREATED);
		} else {
			final String message;
			if((cDataResponse.getError().getName() != null ) && (cDataResponse.getError().getName().length > 0)){
				message = cDataResponse.getError().getName()[0];
			}else {
				message = cDataResponse.getError().getMessage();
			}
			LOGGER.error(MessageCatalog._00038_CREATE_DATASET_CKAN_FAILURE, message);
		}
		return cDataResponse;
	}

	/**
	 * Create a graph dump resource in CKAN.
	 *
	 * @param datasetCkanId		the datset Id in CKAN.
	 * @param graphDesc			the graph description.
	 * @param graphURI			the graph URI.
	 * @return	the {@link eu.aliada.ckancreation.model.CKANResourceResponse}
	 * 			which contains the information of the resource in CKAN.
	 * @since 2.0
	 */
	public CKANResourceResponse createGraphDumpResource(final String datasetCkanId, final String graphDesc, 
			final String graphURI){
		//A graph may contain several file dumps because of its big size
		CKANResourceResponse cResourceResponse = null;
		final DataDump dataDump = new DataDump(jobConf);
		final ArrayList<DumpFileInfo> dumpFileInfos = dataDump.createGraphDump(graphURI, 
				dataFolderName,  dataFolderURL);
		//Add the generated dump files to the global list
		if(dumpFileInfos.size() > 0){
			datasetDumpFileInfoList.addAll(dumpFileInfos);
		}
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
    			final String resourceName = "Dataset dump of " + graphDesc + " in N-Triples format" + fileIndex;
    			cResourceResponse = createResourceCKAN(datasetCkanId, 
						resourceName,
						"Dataset dump in N-Triples format.",
						dumpFileInfo.getDumpFileFormat(), 
						dumpFileInfo.getDumpFileUrl(),
						"file");
				if(cResourceResponse.getSuccess().equals("false")) {
					LOGGER.error(MessageCatalog._00039_CREATE_RESOURCE_CKAN_FAILURE, cResourceResponse.getError().getMessage());
				} else {
					LOGGER.debug(MessageCatalog._00044_CKAN_RESOURCE_CREATED, resourceName);
				}
    		}
		}
		return cResourceResponse;
	}

	/**
	 * Create the dataset in CKAN Datahub.
	 *
	 * @param cDataResponse	the {@link eu.aliada.ckancreation.model.CKANDatasetResponse}
	 * 						which contains the information of the dataset in CKAN.
	 * @param numTriples	the number of triples of the dataset.
	 * @return	the {@link eu.aliada.ckancreation.model.CKANResourceResponse}
	 * 			which contains the information of the resource in CKAN.
	 * @since 2.0
	 */
	public CKANResourceResponse createResources(final CKANDatasetResponse cDataResponse, final int numTriples){
		//Add new resources to the dataset (http://datahub.io/api/action/resource_create)
		//Create resource: SPARQL endpoint. 
		String resourceName = "Sparql Endpoint";
		CKANResourceResponse cResourceResponse = createResourceCKAN(cDataResponse.getResult().getId(), 
				resourceName,
				"SPARQL endpoint for " + cDataResponse.getResult().getName() + " dataset",
				"api/sparql", 
				jobConf.getPublicSparqlEndpointUri(),
				"api");
		
		if(cResourceResponse.getSuccess().equals("false")) {
			LOGGER.error(MessageCatalog._00039_CREATE_RESOURCE_CKAN_FAILURE, cResourceResponse.getError().getMessage());
		} else {
			LOGGER.debug(MessageCatalog._00044_CKAN_RESOURCE_CREATED, resourceName);
		}

		//Create resource: the dataset dumps.
		//Get subset graphs and create a resource for each
		for (final Iterator<Subset> iterSubsets = jobConf.getSubsets().iterator(); iterSubsets.hasNext();  ) {
			final Subset subset = iterSubsets.next();
			//Create dump of graph
			createGraphDumpResource(cDataResponse.getResult().getId(), subset.getDescription(), subset.getGraph());
			//Create dump of links graph
			final String linksGraphDesc = "links to external datasets of " + subset.getDescription();
			createGraphDumpResource(cDataResponse.getResult().getId(), linksGraphDesc, subset.getLinksGraph());
		}

		//Create resource: dataset description file.
		final DatasetDescFile datasetDescFile = new DatasetDescFile(jobConf, this.ckanDatasetUrl, 
				datasetDumpFileInfoList, numTriples, dataFolderName, dataFolderURL);
		if(datasetDescFile.createFile()) {
			resourceName = datasetDescFile.getName();
			cResourceResponse = createResourceCKAN(cDataResponse.getResult().getId(), 
					resourceName,
					"Void file describing the main features of the " + cDataResponse.getResult().getName() + " dataset",
					datasetDescFile.FILE_FORMAT, 
					datasetDescFile.getUrl(),
					"file");
			if(cResourceResponse.getSuccess().equals("true")) {
				LOGGER.debug(MessageCatalog._00044_CKAN_RESOURCE_CREATED, resourceName);
			} else {
				LOGGER.error(MessageCatalog._00039_CREATE_RESOURCE_CKAN_FAILURE, cResourceResponse.getError().getMessage());
			}
		}

		return cResourceResponse;
	}

	/**
	 * It calculates the number of triples contained in the subsets of a dataset.
	 *
	 * @param sparqlEndpoint	the SPARQL endpoint of the dataset. 
	 * @param user				the user name for the SPARQl endpoint.
	 * @param password			the password for the SPARQl endpoint.
	 * @param subsetsList		the {@link eu.aliada.ckancreation.model.Subset}
	 *							that contains information of the graphs of the subset.  
	 * @return the number of triples contained in the subsets of the dataset.  					
	 * @since 2.0
	 */
	public int calculateDatasetNumTriples(final String sparqlEndpoint, final String user, final String password, final ArrayList<Subset> subsetsList) {
		int numTriples = 0;
		//Get subset graphs and get number of triples
		for (final Iterator<Subset> iterSubsets = subsetsList.iterator(); iterSubsets.hasNext();  ) {
			final Subset subset = iterSubsets.next();
			//Get number of triples of each subgraph
			final RDFStoreDAO rdfstoreDAO = new RDFStoreDAO();
			LOGGER.debug(MessageCatalog._00045_GETTING_NUM_TRIPLES, sparqlEndpoint, subset.getGraph(), user, password);
			subset.setGraphNumTriples(rdfstoreDAO.getNumTriples(sparqlEndpoint, subset.getGraph(), user, password));
			LOGGER.debug(MessageCatalog._00045_GETTING_NUM_TRIPLES, sparqlEndpoint, subset.getLinksGraph(), user, password);
			subset.setLinksGraphNumTriples(rdfstoreDAO.getNumTriples(sparqlEndpoint, subset.getLinksGraph(), user, password));
			numTriples = numTriples+ subset.getGraphNumTriples() + subset.getLinksGraphNumTriples();
		}
		return numTriples;
	}
	
	/**
	 * It copies the organisation image file to the dataset web root.
	 *
	 * @return true if the organisation image has been copied correctly. 
	 *         False otherwise.  					
	 * @since 2.0
	 */
	public boolean copyOrgImageToWebServerPath() {
		boolean success = false;
		try {
			//Move the organization image file from TMP folder to the definitive folder
			final File orgImageInitFile= new File(jobConf.getOrgImagePath());
			final String definitiveFileName = dataFolderName + File.separator + "orgLogo.jpeg";
			final File definitiveFile = new File (definitiveFileName);
			Files.move(orgImageInitFile.toPath(), definitiveFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			jobConf.setOrgImagePath(definitiveFileName);
			final String orgImageURL = dataFolderURL + "/" + definitiveFile.getName();
			jobConf.setOrgImageURL(orgImageURL);
			success = true;
		} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00035_FILE_ACCESS_FAILURE, exception, jobConf.getOrgImagePath());
		}
		return success;
	}
	/**
	 * It creates a new Dataset in CKAN Datahub. 
	 * Removes it first if it already exists.
	 * It also updates the organization information in CKAN Datahub.
	 *
	 * @return the {@link eu.aliada.ckancreation.model.job} created.  					
	 * @since 2.0
	 */
	public Job newDataset() {
		LOGGER.debug(MessageCatalog._00030_STARTING);
		//Update job start-date in DDBB
		dbConn.updateJobStartDate(jobConf.getId());
		
		//Get the number of triples of the dataset
		final int numTriples = calculateDatasetNumTriples(jobConf.getSparqlEndpointUri(), jobConf.getSparqlLogin(), jobConf.getSparqlPassword(), jobConf.getSubsets());
		jobConf.setNumTriples(numTriples);
		//ORGANIZATION
		//Copy organisation image file to dataset web root
		copyOrgImageToWebServerPath();
		try {
			//Move the image file from TMP folder to the definitive folder
			final File orgImageInitFile= new File(jobConf.getOrgImagePath());
			final String definitiveFileName = dataFolderName + File.separator + orgImageInitFile.getName();
			final File definitiveFile = new File (definitiveFileName);
			Files.move(orgImageInitFile.toPath(), definitiveFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			jobConf.setOrgImagePath(definitiveFileName);
			final String orgImageURL = dataFolderURL + "/" + definitiveFile.getName();
			jobConf.setOrgImageURL(orgImageURL);
    	} catch (Exception exception) {
			LOGGER.error(MessageCatalog._00035_FILE_ACCESS_FAILURE, exception, jobConf.getOrgImagePath());
    	}
		
		final CKANOrgResponse cOrgResponse = updateOrganization();
		//DATASET
		if(cOrgResponse.getSuccess().equals("true")){
			final CKANDatasetResponse cDataResponse = createDataset(cOrgResponse, numTriples);
			//DATASET RESOURCES
			if(cDataResponse.getSuccess().equals("true")){
				createResources(cDataResponse, numTriples);
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
