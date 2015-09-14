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
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.model.FileWork;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;
import eu.aliada.shared.rdfstore.RDFStoreDAO;
import eu.aliada.shared.rdfstore.RetrievedResource;
import eu.aliada.shared.rdfstore.RetrievedWork;

/**
 * @author xabi
 * @since 1.0
 */
public class RDFValidationAction extends ActionSupport {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ResourceBundle defaults = ResourceBundle.getBundle("defaultValues", getLocale());
	
	private List<RetrievedResource> authors;
	private List<RetrievedResource> ob;
	private List<RetrievedResource> man;
	private List<RetrievedWork> w;
	
	private int numAuthors;
	private int numObjects;
	private int numManifs;
	private int numWorks;
	
	//Buttons
	private String a;
	private String o;
	private String m;
	private String wo;
	
	private boolean auth;
	private boolean objs;
	private boolean mani;
	private boolean works;
	
	private String sparqlEndpointURI;
    private String graphName;
    private String user;
    private String password;
    
    int calls;
    int rest;
    int offset;
    int limit = Integer.valueOf(defaults.getString("limit"));
    
    private boolean queries;
    private String selectedGraph;
    private HashMap<Integer, String> graphs;
    private boolean change;
    
    final RDFStoreDAO rdfstoreDAO = new RDFStoreDAO();

    private final Log logger = new Log(RDFValidationAction.class);
    /**
     * File validation process.
     * @return String
     * @see
     * @since 1.0
     */
    public String execute() {  
    	
    	if (getSelectedGraph() == null) {
    		setSelectedGraph((String) ServletActionContext.getRequest().getSession().getAttribute("graphToQuery"));
    	}
    	
    	setAuth(false);
    	setObjs(false);
    	setMani(false);
    	setWorks(false);
    	
    	a = getText("authors.title");
    	o = getText("objects.title");
    	m = getText("manifs.title");
    	wo = getText("works.title");
    	
        if (ServletActionContext.getRequest().getSession().getAttribute("importedFile") != null) {
        	
        	setChange(false);
        	setQueries(true);
        	graphs = new HashMap<>();
        	
            FileWork importedFile = (FileWork) ServletActionContext.getRequest().getSession().getAttribute("importedFile"); 
            Connection connection;
            connection = new DBConnectionManager().getConnection();
            Statement statement;
            try {
                statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT sparql_endpoint_uri, graph_uri, sparql_endpoint_login, sparql_endpoint_password FROM aliada.organisation o "
                		+ "INNER JOIN aliada.dataset d ON o.organisationId=d.organisationId INNER JOIN aliada.subset s "
                		+ "ON d.datasetId=s.datasetId WHERE s.subsetId='" + importedFile.getGraph() + "'");
                if (rs.next()) {
                    setSparqlEndpointURI(rs.getString("sparql_endpoint_uri"));
                    setGraphName(rs.getString("graph_uri"));
                    setUser(rs.getString("sparql_endpoint_login"));
                    setPassword(rs.getString("sparql_endpoint_password"));
                }
                rs.close();
                statement.close();
                connection.close();
                
                setNumAuthors(rdfstoreDAO.getNumAuthors(sparqlEndpointURI, graphName, user, password));
                String num = " (" + String.valueOf(getNumAuthors()) + ")";
            	setA(getA() + num);               
                setNumObjects(rdfstoreDAO.getNumObjects(sparqlEndpointURI, graphName, user, password));
                num = " (" + String.valueOf(getNumObjects()) + ")";
                setO(getO() + num);   
                setNumManifs(rdfstoreDAO.getNumManifestations(sparqlEndpointURI, graphName, user, password));
                num = " (" + String.valueOf(getNumManifs()) + ")";
                setM(getM() + num);   
                setNumWorks(rdfstoreDAO.getNumWorks(sparqlEndpointURI, graphName, user, password));
                num = " (" + String.valueOf(getNumWorks()) + ")";
                setWo(getWo() + num);   
                
                return SUCCESS;
            } catch (SQLException e) {
                logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
                return ERROR;
            }
        } else if (getSelectedGraph() != null) {
        	
        	setChange(true);
        	setQueries(true);
        	graphs = new HashMap<>();
        	ServletActionContext.getRequest().getSession().setAttribute("graphToQuery", getSelectedGraph());
        	
            Connection connection;
            connection = new DBConnectionManager().getConnection();
            Statement statement;
            try {
                statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT sparql_endpoint_uri, graph_uri, sparql_endpoint_login, "
                		+ "sparql_endpoint_password FROM aliada.organisation o "
                		+ "INNER JOIN aliada.dataset d ON o.organisationId=d.organisationId "
                		+ "INNER JOIN aliada.subset s ON d.datasetId=s.datasetId "
                		+ "WHERE s.subsetId =" + getSelectedGraph() + "");
                if (rs.next()) {
                    setSparqlEndpointURI(rs.getString("sparql_endpoint_uri"));
                    setGraphName(rs.getString("graph_uri"));
                    setUser(rs.getString("sparql_endpoint_login"));
                    setPassword(rs.getString("sparql_endpoint_password"));
                }
                rs.close();
                statement.close();
                connection.close();
                
                setNumAuthors(rdfstoreDAO.getNumAuthors(sparqlEndpointURI, graphName, user, password));
                String num = " (" + String.valueOf(getNumAuthors()) + ")";
            	setA(getA() + num);               
                setNumObjects(rdfstoreDAO.getNumObjects(sparqlEndpointURI, graphName, user, password));
                num = " (" + String.valueOf(getNumObjects()) + ")";
                setO(getO() + num);   
                setNumManifs(rdfstoreDAO.getNumManifestations(sparqlEndpointURI, graphName, user, password));
                num = " (" + String.valueOf(getNumManifs()) + ")";
                setM(getM() + num);   
                setNumWorks(rdfstoreDAO.getNumWorks(sparqlEndpointURI, graphName, user, password));
                num = " (" + String.valueOf(getNumWorks()) + ")";
                setWo(getWo() + num);   
                
                return SUCCESS;
            } catch (SQLException e) {
                logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
                return ERROR;
            }
        } else {
        	
        	setChange(false);
        	setQueries(false);
        	
            Methods m = new Methods();
            m.setLang(getLocale().getISO3Language());
            graphs = m.getGraphsDb();
            return SUCCESS;
        }
    }
    /**
     * Change the graph selected in the UI.
     * @return String
     * @see
     * @since 1.0
     */
    public String changeGraph() {
    	ServletActionContext.getRequest().getSession().setAttribute("graphToQuery", null);
		execute();
		return SUCCESS;
    }
	/**
     * Get the authors triples selected in the UI.
     * @return String
     * @see
     * @since 1.0
     */
    public String getAut() {
    	
    	execute();
    	
    	setAuth(true);
    	
    	calls = numAuthors / limit;
    	
    	rest = numAuthors % limit;
    	
    	if (rest != 0) {
    		calls++;
    	}
    	
    	RetrievedResource[] aut = null;
    	authors = new ArrayList<RetrievedResource>();
    	RetrievedResource resource;
    	
    	for (int i = 1; i <= calls; i++) {
    		offset = (i * limit) - limit;
    		aut = rdfstoreDAO.getAuthors(sparqlEndpointURI, graphName, user, password, offset, limit);
    		for (int a = 0; a < aut.length; a++) {
    	   		resource = new RetrievedResource(aut[a].getResourceURI(), aut[a].getName());
    	   		authors.add(resource);
    		}
    	}

    	return SUCCESS;
    }
    
    /**
     * Get the objects triples selected in the UI.
     * @return String
     * @see
     * @since 1.0
     */
    public String getObj() {
    	
    	execute();
    	
    	setObjs(true);
    
    	calls = numObjects / limit;
    	rest = numObjects % limit;
    	
    	if (rest != 0) {
    		calls++;
    	}
    	
    	RetrievedResource[] obj = null;
    	ob = new ArrayList<RetrievedResource>();
    	RetrievedResource resource;
    	
    	for (int i = 1; i <= calls; i++) {
    		offset = (i * limit) - limit;
    		obj = rdfstoreDAO.getObjects(sparqlEndpointURI, graphName, user, password, offset, limit);
    		for (int a = 0; a < obj.length; a++) {
   				resource = new RetrievedResource(obj[a].getResourceURI(), obj[a].getName());
   	    		ob.add(resource);
   	    	}
    	}

    	return SUCCESS;
    }
    
    /**
     * Get the manifestations triples selected in the UI.
     * @return String
     * @see
     * @since 1.0
     */
    public String getManifestations() {
    	
    	execute();
    	
    	setMani(true);
    	
    	calls = numManifs / limit;
    	rest = numManifs % limit;
    	
    	if (rest != 0) {
    		calls++;
    	}
    	
    	RetrievedResource[] mani = null;
    	man = new ArrayList<RetrievedResource>();
    	RetrievedResource resource;
    	
    	for (int i = 1; i <= calls; i++) {
    		offset = (i * limit) - limit;
   			mani = rdfstoreDAO.getManifestations(sparqlEndpointURI, graphName, user, password, offset, limit);
   			for (int a = 0; a < mani.length; a++) {
   				resource = new RetrievedResource(mani[a].getResourceURI(), mani[a].getName());
   	    		man.add(resource);
        	}
    	}
	
    	return SUCCESS;
    }
    
    /**
     * Get the works triples selected in the UI.
     * @return String
     * @see
     * @since 1.0
     */
    public String getWor() {
    	
    	execute();
    	
    	setWorks(true);
    	
    	calls = numWorks / limit;
    	rest = numWorks % limit;
    	
    	if (rest != 0) {
    		calls++;
    	}
    	
    	RetrievedWork[] wor = null;
    	w = new ArrayList<RetrievedWork>();
    	RetrievedWork resource;
    	
    	for (int i = 1; i <= calls; i++) {
    		offset = (i * limit) - limit;
    		wor = rdfstoreDAO.getWorks(sparqlEndpointURI, graphName, user, password, offset, limit);
    		for (int a = 0; a < wor.length; a++) {
    			resource = new RetrievedWork();
        		resource.setWorkURI(wor[a].getWorkURI());
   	    		resource.setExprURI(wor[a].getExprURI());
   	    		resource.setManifURI(wor[a].getManifURI());
   	    		resource.setTitle(wor[a].getTitle());
   	    		resource.setDimensions(wor[a].getDimensions());
   	    		resource.setExtension(wor[a].getExtension());
   	    		resource.setAuthor(wor[a].getAuthor());
   	    		resource.setPublicPlace(wor[a].getPublicPlace());
   	    		resource.setPublicDate(wor[a].getPublicDate());
   	    		resource.setEdition(wor[a].getEdition());
   	    		w.add(resource);
   	    	}
    	}
	
    	return SUCCESS;
    }
    
    /**
     * @return Returns the sparqlEndpointURI.
     * @exception
     * @since 1.0
     */
    public String getSparqlEndpointURI() {
        return sparqlEndpointURI;
    }
    /**
     * @param sparqlEndpointURI The sparqlEndpointURI to set.
     * @exception
     * @since 1.0
     */
    public void setSparqlEndpointURI(final String sparqlEndpointURI) {
        this.sparqlEndpointURI = sparqlEndpointURI;
    }
    /**
     * @return Returns the graphName.
     * @exception
     * @since 1.0
     */
    public String getGraphName() {
        return graphName;
    }
    /**
     * @param graphName The graphName to set.
     * @exception
     * @since 1.0
     */
    public void setGraphName(final String graphName) {
        this.graphName = graphName;
    }
    /**
     * @return Returns the user.
     * @exception
     * @since 1.0
     */
	public String getUser() {
		return user;
	}
	/**
     * @param user The user to set.
     * @exception
     * @since 1.0
     */
	public void setUser(final String user) {
		this.user = user;
	}
	/**
     * @return Returns the password.
     * @exception
     * @since 1.0
     */
	public String getPassword() {
		return password;
	}
	/**
     * @param password The password to set.
     * @exception
     * @since 1.0
     */
	public void setPassword(final String password) {
		this.password = password;
	}
	/**
     * @return Returns the authors.
     * @exception
     * @since 1.0
     */
	public List<RetrievedResource> getAuthors() {
		return authors;
	}
	/**
     * @param authors The authors to set.
     * @exception
     * @since 1.0
     */
	public void setAuthors(final List<RetrievedResource> authors) {
		this.authors = authors;
	}
	/**
     * @return Returns the ob.
     * @exception
     * @since 1.0
     */
	public List<RetrievedResource> getOb() {
		return ob;
	}
	/**
     * @param ob The ob to set.
     * @exception
     * @since 1.0
     */
	public void setOb(final List<RetrievedResource> ob) {
		this.ob = ob;
	}
	/**
     * @return Returns the man.
     * @exception
     * @since 1.0
     */
	public List<RetrievedResource> getMan() {
		return man;
	}
	/**
     * @param man The man to set.
     * @exception
     * @since 1.0
     */
	public void setMan(final List<RetrievedResource> man) {
		this.man = man;
	}
	/**
     * @return Returns the w.
     * @exception
     * @since 1.0
     */
	public List<RetrievedWork> getW() {
		return w;
	}
	/**
     * @param w The w to set.
     * @exception
     * @since 1.0
     */
	public void setW(final List<RetrievedWork> w) {
		this.w = w;
	}
	 /** @return Returns the auth. */
    public boolean isAuth() {
        return auth;
    }
    /** @param auth The auth to set. */
    public void setAuth(final boolean auth) {
        this.auth = auth;
    }
    /** @param objs The objs to set. */
    public void setObjs(final boolean objs) {
        this.objs = objs;
    }
    /** @return Returns the objs. */
    public boolean isObjs() {
        return objs;
    }
    /** @return Returns the mani. */
    public boolean isMani() {
        return mani;
    }
    /** @param mani The mani to set. */
    public void setMani(final boolean mani) {
        this.mani = mani;
    }
    /** @return Returns the works. */
    public boolean isWorks() {
        return works;
    }
    /** @param works The works to set. */
    public void setWorks(final boolean works) {
        this.works = works;
    }
    /** @return Returns the NumAuthors. */
	public int getNumAuthors() {
		return numAuthors;
	}
	/** @param numAuthors The numAuthors to set. */
	public void setNumAuthors(final int numAuthors) {
		this.numAuthors = numAuthors;
	}
	/** @return Returns the numObjects. */
	public int getNumObjects() {
		return numObjects;
	}
	/** @param numObjects The numObjects to set. */
	public void setNumObjects(final int numObjects) {
		this.numObjects = numObjects;
	}
	/** @return Returns the numManifs. */
	public int getNumManifs() {
		return numManifs;
	}
	/** @param numManifs The numManifs to set. */
	public void setNumManifs(final int numManifs) {
		this.numManifs = numManifs;
	}
	/** @return Returns the numWorks. */
	public int getNumWorks() {
		return numWorks;
	}
	/** @param numWorks The numWorks to set. */
	public void setNumWorks(final int numWorks) {
		this.numWorks = numWorks;
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
	/**
     * @return Returns the o.
     * @exception
     * @since 1.0
     */
	public String getO() {
		return o;
	}
	/**
     * @param o
     *            The o to set.
     * @exception
     * @since 1.0
     */
	public void setO(final String o) {
		this.o = o;
	}
	/**
     * @return Returns the m.
     * @exception
     * @since 1.0
     */
	public String getM() {
		return m;
	}
	/**
     * @param m
     *            The m to set.
     * @exception
     * @since 1.0
     */
	public void setM(final String m) {
		this.m = m;
	}
	/**
     * @return Returns the wo.
     * @exception
     * @since 1.0
     */
	public String getWo() {
		return wo;
	}
	/**
     * @param wo
     *            The wo to set.
     * @exception
     * @since 1.0
     */
	public void setWo(final String wo) {
		this.wo = wo;
	}
	/** @return Returns the queries. */
    public boolean isQueries() {
		return queries;
	}
    /** @param queries The queries to set. */
	public void setQueries(final boolean queries) {
		this.queries = queries;
	}
	/**
     * @return Returns the selectedGraph.
     * @exception
     * @since 1.0
     */
    public String getSelectedGraph() {
        return selectedGraph;
    }
    /**
     * @param selectedGraph
     *            The selectedGraph to set.
     * @exception
     * @since 1.0
     */
    public void setSelectedGraph(final String selectedGraph) {
        this.selectedGraph = selectedGraph;
    }
    /**
     * @return Returns the graphs.
     * @exception
     * @since 1.0
     */
    public HashMap<Integer, String> getGraphs() {
        return graphs;
    }
    /**
     * @param graphs The graphs to set.
     * @exception
     * @since 1.0
     */
    public void setGraphs(final HashMap<Integer, String> graphs) {
        this.graphs = graphs;
    }
    /** @return Returns the change. */
	public boolean isChange() {
		return change;
	}
	/** @param change The change to set. */
	public void setChange(final boolean change) {
		this.change = change;
	}
    
}
