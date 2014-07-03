// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.linksDiscovery.model;

/**
 * RDF-izer Job entity.
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class JobConfiguration {
    private Integer id;
    private String inputURI;
    private String inputLogin;
    private String inputPassword;
    private String inputGraph;
    private String outputURI;
    private String outputLogin;
    private String outputPassword;
    private String outputGraph;
    private String configFile;
    private String tmpDir;
    
    /**
     * Returns the identifier of this job configuration.
     * 
     * @return The identifier of this job configuration.
     */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the identifier of this job configuration.
	 * 
	 * @param id The identifier of this job configuration.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
     * Returns the URI of the SPARQL/Update endpoint of the source dataset from where we want to generate links.
     * 
     * @return The URI of the SPARQL/Update endpoint of the source dataset from where we want to generate links.
     */
	public String getInputURI() {
		return inputURI;
	}

	/**
	 * Sets the URI of the SPARQL/Update endpoint of the source dataset from where we want to generate links.
	 * 
	 * @param inputURI The URI of the SPARQL/Update endpoint of the source dataset from where we want to generate links.
	 */
	public void setInputURI(String inputURI) {
		this.inputURI = inputURI;
	}

    /**
     * Returns the login required for authentication in the SPARQL endpoint.
     * 
     * @return The login required for authentication in the SPARQL endpoint.
     */
	public String getInputLogin() {
		return inputLogin;
	}

	/**
	 * Sets the login required for authentication in the SPARQL endpoint.
	 * 
	 * @param inputLogin The login required for authentication in the SPARQL endpoint.
	 */
	public void setInputLogin(String inputLogin) {
		this.inputLogin = inputLogin;
	}
	
    /**
     * Returns the password required for authentication in the SPARQL endpoint.
     * 
     * @return The password required for authentication in the SPARQL endpoint.
     */
	public String getInputPassword() {
		return inputPassword;
	}

	/**
	 * Sets the password required for authentication in the SPARQL endpoint.
	 * 
	 * @param inputPassword Password required for authentication in the SPARQL endpoint.
	 */
	public void setInputPassword(String inputPassword) {
		this.inputPassword = inputPassword;
	}	
	
    /**
     * Returns graph in that SPARQL endpoint from which to retrieve instances. 
     * If not specified, the query will not be restricted to any specific graph.
     * 
     * @return Graph in that SPARQL endpoint from which to retrieve instances. 
     */
	public String getInputGraph() {
		return inputGraph;
	}

	/**
	 * Sets graph in that SPARQL endpoint from which to retrieve instances. 
     * If not specified, the query will not be restricted to any specific graph.
	 * 
	 * @param inputGraph Graph in that SPARQL endpoint from which to retrieve instances.
	 */
	public void setInputGraph(String inputGraph) {
		this.inputGraph = inputGraph;
	}		

	/**
     * Returns the URI of the SPARQL/Update endpoint of the dataset where to store the generated links. If omitted, the input URI will be used.
     * 
     * @return The URI of the SPARQL/Update endpoint of the dataset where to store the generated links. If omitted, the input URI will be used.
     */
	public String getOutputURI() {
		return outputURI;
	}

	/**
	 * Sets the URI of the SPARQL/Update endpoint of the dataset where to store the generated links. If omitted, the input URI will be used.
	 * 
	 * @param outputURI The URI of the SPARQL/Update endpoint of the dataset where to store the generated links. If omitted, the input URI will be used.
	 */
	public void setOutputURI(String outputURI) {
		this.outputURI = outputURI;
	}

    /**
     * Returns the login required for authentication in the SPARQL endpoint.
     * 
     * @return The login required for authentication in the SPARQL endpoint.
     */
	public String getOutputLogin() {
		return outputLogin;
	}

	/**
	 * Sets the login required for authentication in the SPARQL endpoint.
	 * 
	 * @param outputLogin The login required for authentication in the SPARQL endpoint.
	 */
	public void setOutputLogin(String outputLogin) {
		this.outputLogin = outputLogin;
	}
	
    /**
     * Returns the password required for authentication in the SPARQL endpoint.
     * 
     * @return The password required for authentication in the SPARQL endpoint.
     */
	public String getOutputPassword() {
		return outputPassword;
	}

	/**
	 * Sets the password required for authentication in the SPARQL endpoint.
	 * 
	 * @param outputPassword Password required for authentication in the SPARQL endpoint.
	 */
	public void setOutputPassword(String outputPassword) {
		this.outputPassword = outputPassword;
	}	
	
    /**
     * Returns the URI of the graph where to put the discovered links. 
     * If not specified, no graph will be used for the update.
     * 
     * @return The URI of the graph where to put the discovered links. 
     */
	public String getOutputGraph() {
		return outputGraph;
	}

	/**
	 * Sets the URI of the graph where to put the discovered links. 
     * If not specified, no graph will be used for the update.
	 * 
	 * @param outputGraph The URI of the graph where to put the discovered links. 
	 */
	public void setOutputGraph(String outputGraph) {
		this.outputGraph = outputGraph;
	}		

	/**
     * Returns the path of the configuration file for the linking processes.
     * 
     * @return The path of the configuration file for the linking processes. 
     */
	public String getConfigFile() {
		return configFile;
	}

	/**
	 * Sets the path of the configuration file for the linking processes. 
	 * 
	 * @param configFile The path of the configuration file for the linking processes. 
	 */
	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}		

	/**
     * Returns the path of the temporary directory for creating temporary files.
     * 
     * @return The path of the temporary directory for creating temporary files.
     */
	public String getTmpDir() {
		return tmpDir;
	}

	/**
	 * Sets the path of the temporary directory for creating temporary files.
	 * 
	 * @param tmpDir The path of the temporary directory for creating temporary files.
	 */
	public void setTmpDir(String tmpDir) {
		this.tmpDir = tmpDir;
	}		
}