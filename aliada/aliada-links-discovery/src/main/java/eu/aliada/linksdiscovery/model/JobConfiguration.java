// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.linksdiscovery.model;

/**
 * Links discovery job configuration.
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class JobConfiguration {
	/** Job identification number. */
	private Integer id;
	/** The URI of the SPARQL endpoint of Aliada. */
	private String inputURI;
	/** The login of the SPARQL endpoint indicated by the input_uri field. */
	private String inputLogin;
	/** The password of the SPARQL endpoint indicated by the input_uri field. */
	private String inputPassword;
	/**  The URI of the dataset graph to be accessed through the SPARQL endpoint
	 *  indicated by the input_uri field. */
	private String inputGraph;
	/** The URI of the SPARQL endpoint where to store the generated links of 
	 * Aliada. This field will have the same value as the input_uri field. */
	private String outputURI;
	/** The login of the SPARQL endpoint indicated by the output_uri field. */
	private String outputLogin;
	/** The password of the SPARQL endpoint indicated by the output_uri field. */
	private String outputPassword;
	/** The URI of the dataset graph to be accessed through the SPARQL 
	 * endpoint indicated by the output_uri field. */
	private String outputGraph;
	/** Temporary folder name where to create temporary files. */
	private String tmpDir;
	/** The name of the folder where the links-discovery-task-runner.sh shell script 
	 * has been installed. */
	private String clientAppBinDir;
	/** The machine´s user to execute the links-discovery-task-runner.sh shell script. */
	private String clientAppBinUser;
    
	/**
	 * Returns the identifier of this job configuration.
	 * 
	 * @return The identifier of this job configuration.
	 * @since 1.0
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * Sets the identifier of this job configuration.
	 * 
	 * @param id The identifier of this job configuration.
	 * @since 1.0
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * Returns the URI of the SPARQL/Update endpoint of the source dataset 
	 * from where we want to generate links.
	 * 
	 * @return The URI of the SPARQL/Update endpoint of the source dataset 
	 *         from where we want to generate links.
	 * @since 1.0
	 */
	public String getInputURI() {
		return inputURI;
	}
	/**
	 * Sets the URI of the SPARQL/Update endpoint of the source dataset from 
	 * where we want to generate links.
	 * 
	 * @param inputURI The URI of the SPARQL/Update endpoint of the source 
	 *        dataset from where we want to generate links.
	 * @since 1.0
	 */
	public void setInputURI(final String inputURI) {
		this.inputURI = inputURI;
	}

	/**
	 * Returns the login required for authentication in the SPARQL endpoint.
	 * 
	 * @return The login required for authentication in the SPARQL endpoint.
	 * @since 1.0
	 */
	public String getInputLogin() {
		return inputLogin;
	}
	/**
	 * Sets the login required for authentication in the SPARQL endpoint.
	 * 
	 * @param inputLogin The login required for authentication in the SPARQL 
	 *        endpoint.
	 * @since 1.0
	 */
	public void setInputLogin(final String inputLogin) {
		this.inputLogin = inputLogin;
	}
	
	/**
	 * Returns the password required for authentication in the SPARQL endpoint.
	 * 
	 * @return The password required for authentication in the SPARQL endpoint.
	 * @since 1.0
	 */
	public String getInputPassword() {
		return inputPassword;
	}
	/**
	 * Sets the password required for authentication in the SPARQL endpoint.
	 * 
	 * @param inputPassword Password required for authentication in the SPARQL 
	 *        endpoint.
	 * @since 1.0
	 */
	public void setInputPassword(final String inputPassword) {
		this.inputPassword = inputPassword;
	}	
	
	/**
	 * Returns graph in that SPARQL endpoint from which to retrieve instances. 
	 * If not specified, the query will not be restricted to any specific graph.
	 * 
	 * @return Graph in that SPARQL endpoint from which to retrieve instances. 
	 * @since 1.0
	 */
	public String getInputGraph() {
		return inputGraph;
	}
	/**
	 * Sets graph in that SPARQL endpoint from which to retrieve instances. 
	 * If not specified, the query will not be restricted to any specific graph.
	 * 
	 * @param inputGraph Graph in that SPARQL endpoint from which to retrieve 
	 *        instances.
	 * @since 1.0
	 */
	public void setInputGraph(final String inputGraph) {
		this.inputGraph = inputGraph;
	}		

	/**
	 * Returns the URI of the SPARQL/Update endpoint of the dataset where to store 
	 * the generated links. If omitted, the input URI will be used.
	 * 
	 * @return The URI of the SPARQL/Update endpoint of the dataset where to store 
	 *         the generated links. If omitted, the input URI will be used.
	 * @since 1.0
	 */
	public String getOutputURI() {
		return outputURI;
	}
	/**
	 * Sets the URI of the SPARQL/Update endpoint of the dataset where to store 
	 * the generated links. If omitted, the input URI will be used.
	 * 
	 * @param outputURI The URI of the SPARQL/Update endpoint of the dataset 
	 *        where to store the generated links. If omitted, the input URI 
	 *        will be used.
	 * @since 1.0
	 */
	public void setOutputURI(final String outputURI) {
		this.outputURI = outputURI;
	}

	/**
	 * Returns the login required for authentication in the SPARQL endpoint.
	 * 
	 * @return The login required for authentication in the SPARQL endpoint.
	 * @since 1.0
	 */
	public String getOutputLogin() {
		return outputLogin;
	}
	/**
	 * Sets the login required for authentication in the SPARQL endpoint.
	 * 
	 * @param outputLogin The login required for authentication in the 
	 *        SPARQL endpoint.
	 * @since 1.0
	 */
	public void setOutputLogin(final String outputLogin) {
		this.outputLogin = outputLogin;
	}
	
	/**
	 * Returns the password required for authentication in the SPARQL endpoint.
	 * 
	 * @return The password required for authentication in the SPARQL endpoint.
	 * @since 1.0
	 */
	public String getOutputPassword() {
		return outputPassword;
	}
	/**
	 * Sets the password required for authentication in the SPARQL endpoint.
	 * 
	 * @param outputPassword Password required for authentication in the 
	 *        SPARQL endpoint.
	 * @since 1.0
	 */
	public void setOutputPassword(final String outputPassword) {
		this.outputPassword = outputPassword;
	}	
	
	/**
	 * Returns the URI of the graph where to put the discovered links. 
	 * If not specified, no graph will be used for the update.
	 * 
	 * @return The URI of the graph where to put the discovered links. 
	 * @since 1.0
	 */
	public String getOutputGraph() {
		return outputGraph;
	}
	/**
	 * Sets the URI of the graph where to put the discovered links. 
	 * If not specified, no graph will be used for the update.
	 * 
	 * @param outputGraph The URI of the graph where to put the discovered links. 
	 * @since 1.0
	 */
	public void setOutputGraph(final String outputGraph) {
		this.outputGraph = outputGraph;
	}		

	/**
	 * Returns the path of the temporary directory for creating temporary files.
	 * 
	 * @return The path of the temporary directory for creating temporary files.
	 * @since 1.0
	 */
	public String getTmpDir() {
		return tmpDir;
	}
	/**
	 * Sets the path of the temporary directory for creating temporary files.
	 * 
	 * @param tmpDir The path of the temporary directory for creating temporary files.
	 * @since 1.0
	 */
	public void setTmpDir(final String tmpDir) {
		this.tmpDir = tmpDir;
	}		

	/**
	 * Returns the path of the Links Discovery client application binary directory.
	 * 
	 * @return The path of the Links Discovery client application binary directory.
	 * @since 1.0
	 */
	public String getClientAppBinDir() {
		return clientAppBinDir;
	}
	/**
	 * Sets the path of the Links Discovery client application binary directory.
	 * 
	 * @param clientAppBinDir The Links Discovery client application binary directory.
	 * @since 1.0
	 */
	public void setClientAppBinDir(final String clientAppBinDir) {
		this.clientAppBinDir = clientAppBinDir;
	}		

	/**
	 * Returns the machine´s user to execute the Links Discovery client application.
	 * 
	 * @return The machine´s user to execute the Links Discovery client application.
	 * @since 1.0
	 */
	public String getClientAppBinUser() {
		return clientAppBinUser;
	}
	/**
	 * Sets the machine´s user to execute the Links Discovery client application.
	 * 
	 * @param clientAppBinUser The machine´s user to execute the Links Discovery client application.
	 * @since 1.0
	 */
	public void setClientAppBinUser(final String clientAppBinUser) {
		this.clientAppBinUser = clientAppBinUser;
	}		
}
