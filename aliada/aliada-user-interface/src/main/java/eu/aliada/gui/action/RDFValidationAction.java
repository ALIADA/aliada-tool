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

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.model.FileWork;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**
 * @author iosa
 * @since 1.0
 */
public class RDFValidationAction extends ActionSupport {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sparqlEndpoint;
    private String graphUri;
    private String linksGraphUri;

    private final Log logger = new Log(RDFValidationAction.class);
    /**
     * File validation process.
     * @return String
     * @see
     * @since 1.0
     */
    public String execute() {
        if (ServletActionContext.getRequest().getSession().getAttribute("importedFile") != null) {
            FileWork importedFile = (FileWork) ServletActionContext.getRequest().getSession().getAttribute("importedFile"); 
            Connection connection;
            connection = new DBConnectionManager().getConnection();
            Statement statement;
            try {
                statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT public_sparql_endpoint_uri, graph_uri, links_graph_uri FROM aliada.organisation o INNER JOIN aliada.dataset d "
                		+ "ON o.organisationId=d.organisationId INNER JOIN aliada.subset s ON d.datasetId=s.datasetId WHERE graph_uri='" + importedFile.getGraph() + "'");
                if (rs.next()) {
                    setSparqlEndpoint(rs.getString("public_sparql_endpoint_uri"));
                    setGraphUri(rs.getString("graph_uri"));
                    setLinksGraphUri(rs.getString("links_graph_uri"));
                }
                rs.close();
                statement.close();
                connection.close();
                return SUCCESS;
            } catch (SQLException e) {
                logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
                return ERROR;
            }
        } else {
            logger.error(MessageCatalog._00033_CONVERSION_ERROR_NO_FILE_IMPORTED);
            return ERROR;
        }        
    }
    
    /**
     * @return Returns the sparqlEndpoint.
     * @exception
     * @since 1.0
     */
    public String getSparqlEndpoint() {
        return sparqlEndpoint;
    }
    /**
     * @param sparqlEndpoint The sparqlEndpoint to set.
     * @exception
     * @since 1.0
     */
    public void setSparqlEndpoint(final String sparqlEndpoint) {
        this.sparqlEndpoint = sparqlEndpoint;
    }
    /**
     * @return Returns the graphUri.
     * @exception
     * @since 1.0
     */
    public String getGraphUri() {
        return graphUri;
    }
    /**
     * @param graphUri The graphUri to set.
     * @exception
     * @since 1.0
     */
    public void setGraphUri(final String graphUri) {
        this.graphUri = graphUri;
    }
    /**
     * @return Returns the linksGraphUri.
     * @exception
     * @since 1.0
     */
	public String getLinksGraphUri() {
		return linksGraphUri;
	}
	/**
     * @param linksGraphUri The linksGraphUri to set.
     * @exception
     * @since 1.0
     */
	public void setLinksGraphUri(final String linksGraphUri) {
		this.linksGraphUri = linksGraphUri;
	}
    
}
