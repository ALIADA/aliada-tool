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
import eu.aliada.shared.rdfstore.AmbiguousLink;
import eu.aliada.shared.rdfstore.RDFStoreDAO;

/**
 * @author xabi
 * @since 1.0
 */

public class DiscLinksValidationAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Triples> discovLinks;
	
	private List<Triples> discovLinksAmbiguous;
	
	private List<String> val;
	
	private int numLinks;
	private int numAmb;
	
	//Buttons
	private String l;
	private String a;
	
	private boolean li;
	private boolean amb;

	private String valueForm;
	private String subjectForm;
	private String objectForm;
	
	private String sparqlEndpointURI;
	private String graphName;
	private String user;
	private String password;
	
	int calls;
	int rest;
	int offset;
	int limit = 8000;
	
	final RDFStoreDAO rdfstoreDAO = new RDFStoreDAO();
	
	private final Log logger = new Log(DiscLinksValidationAction.class);
	
	 /**
     * Load the links in the UI.
     * @return String
     * @see
     * @since 1.0
     */
    public String execute() {
    	
    	loadParameters();
    	
    	setLi(false);
    	setAmb(true);
    	
    	numAmb = rdfstoreDAO.getNumAmbiguousDiscoveredLinks(sparqlEndpointURI, graphName, user, password);
    	String num = " (" + String.valueOf(numAmb) + ")";
    	setA(getA() + num);
    	numLinks = rdfstoreDAO.getNumDiscoveredLinks(sparqlEndpointURI, graphName, user, password);
    	num = " (" + String.valueOf(numLinks) + ")";
    	setL(getL() + num);
    	    	
    	calls = numAmb / limit;
    	rest = numAmb % limit;
    	
    	if (rest != 0) {
    		calls++;
    	}
    	
    	AmbiguousLink [] ambiguousLinks = null;
    	discovLinksAmbiguous = new ArrayList<Triples>();
    	Triples tr = new Triples();
    	
    	for (int i = 1; i <= calls; i++) {
    		offset = (i * limit) - limit;
    		ambiguousLinks = rdfstoreDAO.getAmbiguousDiscoveredLinks(sparqlEndpointURI, graphName, user, password, offset, limit);
    		for (int x = 0; x < ambiguousLinks.length; x++) {
    				// List with the ambiguous
    			for (int t = 0; t < ambiguousLinks[x].getLinks().length; t++) {
    				tr = new Triples("<" + ambiguousLinks[x].getLinks()[t].getSubject().getURI() + "> <" 
    		    	    	+ ambiguousLinks[x].getLinks()[t].getPredicate().getURI() + "> <" 
    		    	    	+ ambiguousLinks[x].getLinks()[t].getObject().getURI() + ">",
    		    	    	ambiguousLinks[x].getLinks()[t].getSubject().getURI(), 
    		    	    	ambiguousLinks[x].getLinks()[t].getObject().getURI());
    	    		discovLinksAmbiguous.add(tr);
    	    	}
    	    }
    	}
    	
    	logger.debug("Ambiguous links:" + Integer.toString(discovLinksAmbiguous.size()));
   
		return SUCCESS;
    }
    
	 /**
     * Load the links in the UI.
     * @return String
     * @see
     * @since 1.0
     */
    public String getAllLinks() {
    	
    	loadParameters();
    	
    	setLi(true);
    	setAmb(false);
    	
    	numLinks = rdfstoreDAO.getNumDiscoveredLinks(sparqlEndpointURI, graphName, user, password);
    	String num = " (" + String.valueOf(numLinks) + ")";
    	setL(getL() + num);
    	numAmb = rdfstoreDAO.getNumAmbiguousDiscoveredLinks(sparqlEndpointURI, graphName, user, password);
    	num = " (" + String.valueOf(numAmb) + ")";
    	setA(getA() + num);
    	
    	calls = numLinks / limit;
    	rest = numLinks % limit;
    	
    	if (rest != 0) {
    		calls++;
    	}
    	
    	Triple [] discoveredLinks = null;
    	discovLinks = new ArrayList<Triples>();
    	Triples t = new Triples();
    	
    	for (int i = 1; i <= calls; i++) {
    		offset = (i * limit) - limit;
    		discoveredLinks = rdfstoreDAO.getDiscoveredLinks(sparqlEndpointURI, graphName, user, password, offset, limit);
    		for (int a = 0; a < discoveredLinks.length; a++) {
    			// List with the triples
    	    	t = new Triples("<" + discoveredLinks[a].getSubject().getURI() + "> <" 
    	    	+ discoveredLinks[a].getPredicate().getURI() + "> <" 
    	    	+ discoveredLinks[a].getObject().getURI() + ">",
    	   		discoveredLinks[a].getSubject().getURI(), 
    	   		discoveredLinks[a].getObject().getURI());
    	   		discovLinks.add(t);
        	}
    	}
    	
    	logger.debug("All the links: " + Integer.toString(discovLinks.size()));
    	
    	
   
		return SUCCESS;
    }
	
    /**
     * The following values are extracted from table `aliada`.`linksdiscovery_job_instances` and 
     * the fields: `output_uri`, `output_login`, `output_password`, `output_graph`.
     * @see
     * @since 1.0
     */
	 public void loadParameters() {
		 
		 l = getText("links.title");
		 a = getText("ambiguous.title");
		
		 Connection connection = null;
	        try {
	            connection = new DBConnectionManager().getConnection();
	            Statement statement = connection.createStatement();
	            ResultSet rs = statement
	                    .executeQuery("SELECT output_uri, output_graph, output_login, output_password FROM aliada.linksdiscovery_job_instances ORDER BY job_id DESC LIMIT 1");
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
    	
    	loadParameters();
    	
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
				addActionError(getText("links.not.delete"));
			}
    	}
    	
    	setLi(true);
    	setAmb(false);
    	
    	numLinks = rdfstoreDAO.getNumDiscoveredLinks(sparqlEndpointURI, graphName, user, password);
    	String num = " (" + String.valueOf(numLinks) + ")";
    	setL(getL() + num);
    	numAmb = rdfstoreDAO.getNumAmbiguousDiscoveredLinks(sparqlEndpointURI, graphName, user, password);
    	num = " (" + String.valueOf(numAmb) + ")";
    	setA(getA() + num);
    	
    	calls = numLinks / limit;
    	rest = numLinks % limit;
    	
    	if (rest != 0) {
    		calls++;
    	}
    	
    	Triple [] discoveredLinks = null;
    	discovLinks = new ArrayList<Triples>();
    	Triples t = new Triples();
    	
    	for (int i = 1; i <= calls; i++) {
    		offset = (i * limit) - limit;
    		discoveredLinks = rdfstoreDAO.getDiscoveredLinks(sparqlEndpointURI, graphName, user, password, offset, limit);
    		for (int a = 0; a < discoveredLinks.length; a++) {
    			// List with the triples
    	    	t = new Triples("<" + discoveredLinks[a].getSubject().getURI() + "> <" 
    	    	+ discoveredLinks[a].getPredicate().getURI() + "> <" 
    	    	+ discoveredLinks[a].getObject().getURI() + ">",
    	   		discoveredLinks[a].getSubject().getURI(), 
    	   		discoveredLinks[a].getObject().getURI());
    	   		discovLinks.add(t);
        	}
    	}
    	
    	addActionMessage(getText("links.delete.ok"));
    	return SUCCESS;
    }
    
    /**
     * Remove the links selected in the UI.
     * @return String
     * @see
     * @since 1.0
     */
    public String removeLinksAmbiguous() {
    	
    	loadParameters();
    	
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
				addActionError(getText("links.not.delete"));
			}
    	}
    	
    	setLi(false);
    	setAmb(true);
    	
    	numLinks = rdfstoreDAO.getNumDiscoveredLinks(sparqlEndpointURI, graphName, user, password);
    	String num = " (" + String.valueOf(numLinks) + ")";
    	setL(getL() + num);
    	numAmb = rdfstoreDAO.getNumAmbiguousDiscoveredLinks(sparqlEndpointURI, graphName, user, password);
    	num = " (" + String.valueOf(numAmb) + ")";
    	setA(getA() + num);
    	
    	calls = numAmb / limit;
    	rest = numAmb % limit;
    	
    	if (rest != 0) {
    		calls++;
    	}
    	
    	AmbiguousLink [] ambiguousLinks = null;
    	discovLinksAmbiguous = new ArrayList<Triples>();
    	Triples tr = new Triples();
    	
    	for (int i = 1; i <= calls; i++) {
    		offset = (i * limit) - limit;
    		ambiguousLinks = rdfstoreDAO.getAmbiguousDiscoveredLinks(sparqlEndpointURI, graphName, user, password, offset, limit);
    		for (int x = 0; x < ambiguousLinks.length; x++) {
    				// List with the ambiguous
    			for (int t = 0; t < ambiguousLinks[x].getLinks().length; t++) {
    				tr = new Triples("<" + ambiguousLinks[x].getLinks()[t].getSubject().getURI() + "> <" 
    		    	    	+ ambiguousLinks[x].getLinks()[t].getPredicate().getURI() + "> <" 
    		    	    	+ ambiguousLinks[x].getLinks()[t].getObject().getURI() + ">",
    		    	    	ambiguousLinks[x].getLinks()[t].getSubject().getURI(), 
    		    	    	ambiguousLinks[x].getLinks()[t].getObject().getURI());
    	    		discovLinksAmbiguous.add(tr);
    	    	}
    	    }
    	}
    	
    	addActionMessage(getText("links.delete.ok"));
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
     * @return Returns the discovLinksAmbiguous.
     * @exception
     * @since 1.0
     */
	public List<Triples> getDiscovLinksAmbiguous() {
		return discovLinksAmbiguous;
	}
	/**
     * @param discovLinksAmbiguous The discovLinksAmbiguous to set.
     * @exception
     * @since 1.0
     */
	public void setDiscovLinksAmbiguous(final List<Triples> discovLinksAmbiguous) {
		this.discovLinksAmbiguous = discovLinksAmbiguous;
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
	/**
     * @return Returns the numLinks.
     * @exception
     * @since 1.0
     */
	public int getNumLinks() {
		return numLinks;
	}
	/**
     * @param numLinks
     *            The numLinks to set.
     * @exception
     * @since 1.0
     */
	public void setNumLinks(final int numLinks) {
		this.numLinks = numLinks;
	}
	/**
     * @return Returns the numAmb.
     * @exception
     * @since 1.0
     */
	public int getNumAmb() {
		return numAmb;
	}
	/**
     * @param numAmb
     *            The numAmb to set.
     * @exception
     * @since 1.0
     */
	public void setNumAmb(final int numAmb) {
		this.numAmb = numAmb;
	}
	/**
     * @return Returns the li.
     * @exception
     * @since 1.0
     */
	public boolean isLi() {
		return li;
	}
	/**
     * @param li
     *            The li to set.
     * @exception
     * @since 1.0
     */
	public void setLi(final boolean li) {
		this.li = li;
	}
	/**
     * @return Returns the amb.
     * @exception
     * @since 1.0
     */
	public boolean isAmb() {
		return amb;
	}
	/**
     * @param amb
     *            The amb to set.
     * @exception
     * @since 1.0
     */
	public void setAmb(final boolean amb) {
		this.amb = amb;
	}
	/**
     * @return Returns the l.
     * @exception
     * @since 1.0
     */
	public String getL() {
		return l;
	}
	/**
     * @param l
     *            The l to set.
     * @exception
     * @since 1.0
     */
	public void setL(final String l) {
		this.l = l;
	}
	/**
     * @return Returns the a.
     * @exception
     * @since 1.0
     */
	public String getA() {
		return a;
	}
	/**
     * @param a
     *            The a to set.
     * @exception
     * @since 1.0
     */
	public void setA(final String a) {
		this.a = a;
	}
	
	
	
}
