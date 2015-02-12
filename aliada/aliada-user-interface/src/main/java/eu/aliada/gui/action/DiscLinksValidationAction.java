// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.graph.Triple;
import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.model.Triples;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;
import eu.aliada.shared.rdfstore.RDFStoreDAO;

/**
 * @author xabi
 * @since 1.0
 */

public class DiscLinksValidationAction extends ActionSupport{
	
	private List<Triples> discovLinks;
	private List<String> val;

	private String valueForm;
	private String subjectForm;
	private String objectForm;
	
	private String sparqlEndpointURI;
	private String graphName;
	private String user;
	private String password;
	
	private final Log logger = new Log(DiscLinksValidationAction.class);
	
	 /**
     * Load the links in the UI.
     * @return String
     * @see
     * @since 1.0
     */
    public String execute() {
    	
    	RDFStoreDAO rdfStoreDAO = new RDFStoreDAO();
    	
    	loadParameters();
    	
    	Triple [] discoveredLinks = rdfStoreDAO.getDiscoveredLinks(sparqlEndpointURI, graphName, user, password);
    	
    	discovLinks = new ArrayList<Triples>();
    	Triples t = new Triples();
    	
    	for (int i = 0; i < discoveredLinks.length; i++) {
    		// List with the triples
    		t = new Triples("<" + discoveredLinks[i].getSubject().getURI() + "> <" 
    				+ discoveredLinks[i].getPredicate().getURI() + "> <" 
    				+ discoveredLinks[i].getObject().getURI() + ">",
    				discoveredLinks[i].getSubject().getURI(), 
    				discoveredLinks[i].getObject().getURI());
    		discovLinks.add(t);
    	}
    	
		return SUCCESS;
    }
	
    /**
     * The following values are extracted from table `aliada`.`linksdiscovery_job_instances` and 
     * the fields: `output_uri`, `output_login`, `output_password`, `output_graph`.
     * @see
     * @since 1.0
     */
	 public void loadParameters() {
		
		 Connection connection = null;
	        try {
	            connection = new DBConnectionManager().getConnection();
	            Statement statement = connection.createStatement();
	            ResultSet rs = statement
	                    .executeQuery("SELECT * FROM linksdiscovery_job_instances ORDER BY job_id DESC LIMIT 1");
	            if (rs.next()) {
	            	this.sparqlEndpointURI = rs.getString("output_uri");
	                this.graphName = rs.getString("output_graph");
	                this.user = rs.getString("output_login");
	                this.password = rs.getString("output_password");
	                statement.close();
	                rs.close();
	                connection.close();
	            } 
	        } catch (SQLException e) {
	            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
	        }
		
	}

	/**
     * Remove the links selected in the UI.
     * @return String
     * @see
     * @since 1.0
     */
    public String removeLinks() {
    	
    	RDFStoreDAO rdfStoreDAO = new RDFStoreDAO();
    	
    	if (val != null) {
    		String str = "";
    		for (int j = 0; j < val.size(); j++) {
    			str = str + val.get(j) + " . ";
    		}
    		loadParameters();
    		try {
				rdfStoreDAO.executeDelete(sparqlEndpointURI, graphName, user, password, str);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	
    	Triple [] discoveredLinks = rdfStoreDAO.getDiscoveredLinks(sparqlEndpointURI, graphName, user, password);
    	
    	discovLinks = new ArrayList<Triples>();
    	Triples t = new Triples();
    	
    	for (int i = 0; i < discoveredLinks.length; i++) {
    		// List with the triples
    		t = new Triples("<" + discoveredLinks[i].getSubject().getURI() + "> <" 
    				+ discoveredLinks[i].getPredicate().getURI() + "> <" 
    				+ discoveredLinks[i].getObject().getURI() + ">",
    				discoveredLinks[i].getSubject().getURI(), 
    				discoveredLinks[i].getObject().getURI());
    		discovLinks.add(t);
    	}
    	
    	return SUCCESS;
    }
    
    /**
     * @return Returns the discovLinks.
     * @exception
     * @since 1.0
     */
	public List<Triples> getDiscovLinks() {
		return discovLinks;
	}

	/**
     * @param discovLinks The discovLinks to set.
     * @exception
     * @since 1.0
     */
	public void setDiscovLinks(final List<Triples> discovLinks) {
		this.discovLinks = discovLinks;
	}
	
	/**
     * @return Returns the val.
     * @exception
     * @since 1.0
     */
	public List<String> getVal() {
		return val;
	}

	/**
     * @param val The val to set.
     * @exception
     * @since 1.0
     */
	public void setVal(final List<String> val) {
		this.val = val;
	}
    
	/**
     * @return Returns the valueForm.
     * @exception
     * @since 1.0
     */
	public String getValueForm() {
		return valueForm;
	}
	
	/**
     * @param valueForm
     *            The valueForm to set.
     * @exception
     * @since 1.0
     */
	public void setValueForm(final String valueForm) {
		this.valueForm = valueForm;
	}

	/**
     * @return Returns the subjectForm.
     * @exception
     * @since 1.0
     */
	public String getSubjectForm() {
		return subjectForm;
	}
	
	/**
     * @param subjectForm
     *            The subjectForm to set.
     * @exception
     * @since 1.0
     */
	public void setSubjectForm(final String subjectForm) {
		this.subjectForm = subjectForm;
	}

	/**
     * @return Returns the objectForm.
     * @exception
     * @since 1.0
     */
	public String getObjectForm() {
		return objectForm;
	}

	/**
     * @param objectForm
     *            The objectForm to set.
     * @exception
     * @since 1.0
     */
	public void setObjectForm(final String objectForm) {
		this.objectForm = objectForm;
	}
	
}
