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
    private String sparql_endpoint;
    private String graph_uri;

    private final Log logger = new Log(InstitutionConfigurationAction.class);
    
    public String execute(){
        FileWork importedFile = (FileWork) ServletActionContext.getRequest().getSession().getAttribute("importedFile"); 
        Connection connection;
        connection = new DBConnectionManager().getConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT public_sparql_endpoint_uri, graph_uri FROM organisation o INNER JOIN graph g ON o.organisationId=g.organisationId WHERE graph_uri='"+importedFile.getGraph()+"'");
            if (rs.next() ) {
                setSparql_endpoint(rs.getString("public_sparql_endpoint_uri"));
                setGraph_uri(rs.getString("graph_uri"));
            }
            rs.close();
            statement.close();
            connection.close();
            return SUCCESS;
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION,e);
            return ERROR;
        }
        
    }
    
    /**
     * @return Returns the sparql_endpoint.
     * @exception
     * @since 1.0
     */
    public String getSparql_endpoint() {
        return sparql_endpoint;
    }
    /**
     * @param sparql_endpoint The sparql_endpoint to set.
     * @exception
     * @since 1.0
     */
    public void setSparql_endpoint(String sparql_endpoint) {
        this.sparql_endpoint = sparql_endpoint;
    }
    /**
     * @return Returns the graph_uri.
     * @exception
     * @since 1.0
     */
    public String getGraph_uri() {
        return graph_uri;
    }
    /**
     * @param graph_uri The graph_uri to set.
     * @exception
     * @since 1.0
     */
    public void setGraph_uri(String graph_uri) {
        this.graph_uri = graph_uri;
    }
}
