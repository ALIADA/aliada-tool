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
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;
import eu.aliada.shared.rdfstore.RDFStoreDAO;

/**
 * This class is the subsets administration.
 * @author xabier
 * @version $Revision: 1.1 $, $Date: 2015/02/19 15:20:54 $
 * @since 1.0
 */
public class SubsetsAction  extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<Integer, String> subsets;
	private HashMap<Integer, String> datasets;
	
	private String selectedSubset;
	
	private int dataID;
	private String datasetNameForm;
	private String subsetDescForm;
	private String uriConceptPartForm;
	private String graphUriForm;
	private String linksGraphUriForm;
	private String isqlCommandsFileSubsetForm;
	
    private boolean showAddSubsetForm;
    private boolean showEditSubsetForm;
    private boolean showTheSubset;
    private boolean areSubsets;
	
	private final Log logger = new Log(SubsetsAction.class);

    /**
     * The method to show the subsets list.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String showSubsets() {
    	HttpSession session = ServletActionContext.getRequest().getSession();
    	int datasetId = (int) session.getAttribute("DatasetId");
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select * from aliada.subset where datasetId =" + datasetId);
            subsets = new HashMap<Integer, String>();
            while (rs.next()) {
                subsets.put(rs.getInt("subsetId"),
                        rs.getString("subset_desc"));
            }
            if (subsets.isEmpty()) {
                setAreSubsets(false);
            } else {
                setAreSubsets(true);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        getDatasetsDb();
        setShowAddSubsetForm(false);
        setShowEditSubsetForm(false);
        setShowTheSubset(false);
        return SUCCESS;
    }

    /**
     * The method to see the subset selected.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String getTheSubset() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select * from aliada.subset where subset_desc='" + this.selectedSubset + "'");
            if (rs.next()) {
                this.dataID = rs.getInt("datasetId");
                this.subsetDescForm = rs.getString("subset_desc");
                this.uriConceptPartForm = rs.getString("uri_concept_part");
                this.graphUriForm = rs.getString("graph_uri");
                this.linksGraphUriForm = rs.getString("links_graph_uri");
                this.isqlCommandsFileSubsetForm = rs.getString("isql_commands_file_subset");
                statement.close();
                rs.close();
                connection.close();
                showSubsets();
                setShowTheSubset(true);
                this.datasetNameForm = getDatasetName();
                return SUCCESS;
            } else {
                addActionError(getText("subset.not.selected"));
                statement.close();
                rs.close();
                connection.close();
                showSubsets();
                return ERROR;
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            showSubsets();
            return ERROR;
        }
    }

    /**
     * The method to show the subset form with the subset selected.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String showEditSubset() {
    	HttpSession session = ServletActionContext.getRequest().getSession();
        Connection connection = null;
        if (this.selectedSubset != null) {
	        try {
	            connection = new DBConnectionManager().getConnection();
	            Statement statement = connection.createStatement();
	            ResultSet rs = statement
	                    .executeQuery("select * from aliada.subset where subset_desc='" + this.selectedSubset + "'");
	            if (rs.next()) {
	            	int subsetId = rs.getInt("subsetId");
	            	this.dataID = rs.getInt("datasetId");
	                this.subsetDescForm = rs.getString("subset_desc");
	                this.uriConceptPartForm = rs.getString("uri_concept_part");
	                this.graphUriForm = rs.getString("graph_uri");
	                this.isqlCommandsFileSubsetForm = rs.getString("isql_commands_file_subset");
	                statement.close();
	                rs.close();
	                connection.close();
	                session.setAttribute("SubsetId", subsetId);
	                showSubsets();
	                setShowEditSubsetForm(true);
	                return SUCCESS;
	            } else {
	            	addActionError(getText("subset.not.selected"));
	                statement.close();
	                rs.close();
	                connection.close();
	                showSubsets();
	                return ERROR;
	            }
	        } catch (SQLException e) {
	            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
	            showSubsets();
	            return ERROR;
	        }
        } else if (getActionErrors().size() > 0) {
        	int SubId = (int) session.getAttribute("SubsetId");
        	try {
	            connection = new DBConnectionManager().getConnection();
	            Statement statement = connection.createStatement();
	            ResultSet rs = statement
	                    .executeQuery("select * from aliada.subset where subsetId='" + SubId + "'");
	            if (rs.next()) {
	            	this.dataID = rs.getInt("datasetId");
	                this.subsetDescForm = rs.getString("subset_desc");
	                this.uriConceptPartForm = rs.getString("uri_concept_part");
	                this.graphUriForm = rs.getString("graph_uri");
	                this.isqlCommandsFileSubsetForm = rs.getString("isql_commands_file_subset");
	                statement.close();
	                rs.close();
	                connection.close();
	            }
        	 } catch (SQLException e) {
 	            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
 	            showSubsets();
 	            return ERROR;
 	        }
            showSubsets();
            setShowEditSubsetForm(true);
        	return SUCCESS;
        } else {
        	clearErrorsAndMessages();
        	addActionError(getText("subset.not.selected"));
            showSubsets();
            return ERROR;
        }
    }

    /**
     * The method to edit the subset selected.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String editSubset() {
    	HttpSession session = ServletActionContext.getRequest().getSession();
        Connection connection = null;
        int datasetId = (int) session.getAttribute("DatasetId");
        int subsetId = (int) session.getAttribute("SubsetId");
        
        if (this.subsetDescForm.trim().isEmpty()) {
        	clearErrorsAndMessages();
       	    addActionError(getText("descS.not.null"));
            showSubsets();
            return ERROR;            	 
        }
        
        try {
            connection = new DBConnectionManager().getConnection();
            int di = 0;
            String sd = "", ucp = "", gu = "", lgu = "", icfs = "";
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select datasetId, subset_desc, uri_concept_part, graph_uri, links_graph_uri, isql_commands_file_subset "
                    		+ "from aliada.subset where subsetId='" + subsetId + "'");
            if (rs.next()) {
            	di = rs.getInt("datasetId");
            	sd = rs.getString("subset_desc");
            	ucp = rs.getString("uri_concept_part");
            	gu = rs.getString("graph_uri");
            	lgu = rs.getString("links_graph_uri");
            	icfs = rs.getString("isql_commands_file_subset");
            }
            
            if (this.dataID == di && this.subsetDescForm.equalsIgnoreCase(sd) && this.uriConceptPartForm.equalsIgnoreCase(ucp)
            		&& this.graphUriForm.equalsIgnoreCase(gu) && this.isqlCommandsFileSubsetForm.equalsIgnoreCase(icfs)) {
            	rs.close();
            	statement.close();
            	connection.close();
           	    addActionError(getText("data.not.change"));
                showSubsets();
                return ERROR; 
            }
            
            if (!gu.equalsIgnoreCase(this.graphUriForm)) {
            	String seu = "", sel = "", sep = "";
	             rs = statement
	                     .executeQuery("select sparql_endpoint_uri, sparql_endpoint_login, sparql_endpoint_password from dataset where datasetId='" + datasetId + "'");
	             if (rs.next()) {
	            	 seu = rs.getString("sparql_endpoint_uri");
	            	 sel = rs.getString("sparql_endpoint_login");
	            	 sep = rs.getString("sparql_endpoint_password");
	             }
	             rs.close();
	             RDFStoreDAO store = new RDFStoreDAO();
	             logger.debug(MessageCatalog._00074_REMOVING_A_GRAPH, gu);
	             store.removeGraphBySparql(seu, sel, sep, gu);
	             logger.debug(MessageCatalog._00074_REMOVING_A_GRAPH, lgu);
	             store.removeGraphBySparql(seu, sel, sep, lgu);
	             lgu = this.graphUriForm + "/links";
	             logger.debug(MessageCatalog._00072_CREATING_A_NEW_GRAPH, this.graphUriForm);
	             store.createGraphBySparql(seu, sel, sep, this.graphUriForm);
	             logger.debug(MessageCatalog._00073_CREATING_A_NEW_LINKS_GRAPH, lgu);
	             store.createGraphBySparql(seu, sel, sep, lgu);
            }
            
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE aliada.subset set datasetId='"
                    + this.dataID + "',subset_desc='"
                    + this.subsetDescForm + "',uri_concept_part='"
                    + this.uriConceptPartForm + "',graph_uri='"
                    + this.graphUriForm + "',links_graph_uri='"
                    + lgu + "',isql_commands_file_subset='"
                    + this.isqlCommandsFileSubsetForm + "' where subsetId='"
                    + session.getAttribute("SubsetId") + "'");
            statement.close();
            connection.close();
            addActionMessage(getText("subset.edit.ok"));
            showSubsets();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * The method to delete the subset selected.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String deleteSubset() {
    	String seu = "", sel = "", sep = "", gu = "", lgu = "";
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            
            statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select sparql_endpoint_uri, sparql_endpoint_login, sparql_endpoint_password, graph_uri, links_graph_uri"
                    		+ " from dataset d Inner JOIN subset s on d.datasetId = s.datasetId where subset_desc = '" + getSelectedSubset() + "'");
            if (rs.next()) {
           	 seu = rs.getString("sparql_endpoint_uri");
           	 sel = rs.getString("sparql_endpoint_login");
           	 sep = rs.getString("sparql_endpoint_password");
           	 gu = rs.getString("graph_uri");
           	 lgu = rs.getString("links_graph_uri");
            }
            rs.close();
            if (!gu.trim().isEmpty() && !lgu.trim().isEmpty()) {
	            RDFStoreDAO store = new RDFStoreDAO();
	            logger.debug(MessageCatalog._00074_REMOVING_A_GRAPH, gu);
	            store.removeGraphBySparql(seu, sel, sep, gu);
	            logger.debug(MessageCatalog._00074_REMOVING_A_GRAPH, lgu);
	            store.removeGraphBySparql(seu, sel, sep, lgu);
            }
            
            int correct = statement
                    .executeUpdate("DELETE FROM aliada.subset WHERE subset_desc='" + getSelectedSubset() + "'");
            statement.close();
            connection.close();
            if (correct == 0) {
                addActionError(getText("subset.not.selected"));
            } else {
                addActionMessage(getText("subset.delete.ok"));
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            showSubsets();
            return ERROR;
        }
        showSubsets();
        return SUCCESS;
    }

    /**
     * The method to add a new subset.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String addSubset() {
    	
    	 String linksGraphUri = "";
    	 if (!this.graphUriForm.isEmpty()) {
    		 linksGraphUri = this.graphUriForm + "/links";
    	 }
    	 
    	 if (this.subsetDescForm.trim().isEmpty()) {
        	 addActionError(getText("descS.not.null"));
             showSubsets();
             return ERROR;            	 
         }
    	 
    	 Connection connection = null;
    	 HttpSession session = ServletActionContext.getRequest().getSession();
     	 int datasetId = (int) session.getAttribute("DatasetId");
         try {
        	 connection = new DBConnectionManager().getConnection();
        	 Statement statement = connection.createStatement();
        	 
        	 if (!this.graphUriForm.trim().isEmpty()) {
            	 String seu = "", sel = "", sep = "";
	             ResultSet rs = statement
	                     .executeQuery("select sparql_endpoint_uri, sparql_endpoint_login, sparql_endpoint_password from dataset where datasetId='" + datasetId + "'");
	             if (rs.next()) {
	            	 seu = rs.getString("sparql_endpoint_uri");
	            	 sel = rs.getString("sparql_endpoint_login");
	            	 sep = rs.getString("sparql_endpoint_password");
	             }
	             rs.close();
	             RDFStoreDAO store = new RDFStoreDAO();
	             logger.debug(MessageCatalog._00072_CREATING_A_NEW_GRAPH, this.graphUriForm);
	             store.createGraphBySparql(seu, sel, sep, this.graphUriForm);
	             logger.debug(MessageCatalog._00073_CREATING_A_NEW_LINKS_GRAPH, linksGraphUri);
	             store.createGraphBySparql(seu, sel, sep, linksGraphUri);
             }
             
             statement = connection.createStatement();
             statement.executeUpdate("INSERT INTO aliada.subset (datasetId, subset_desc, uri_concept_part, "
             		+ "graph_uri, links_graph_uri, isql_commands_file_subset) VALUES (" + datasetId + ",'"
                     + this.subsetDescForm + "', '" + this.uriConceptPartForm + "', '"
                     + this.graphUriForm + "', '" + linksGraphUri + "', '"
                     + this.isqlCommandsFileSubsetForm + "')");
             
             statement.close();
             connection.close();
             addActionMessage(getText("subset.save.ok"));
             showSubsets();
             return SUCCESS;
         } catch (SQLException e) {
             logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
             return ERROR;
         }
         
         
         
    }

    /**
     * The method to show the subset form empty.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String showAddSubset() {
    	String isqlCommFileSubset = "";
    	Connection connection = new DBConnectionManager().getConnection();
    	String usernameLogged = (String) ServletActionContext.getRequest().getSession().getAttribute("logedUser");
     	Statement statement;
		try {
			statement = connection.createStatement();
			 ResultSet rs = statement
	                 .executeQuery("select isql_commands_file_subset_default from aliada.organisation o "
	                 		+ "INNER JOIN aliada.user u ON u.organisationId=o.organisationId "
	                 		+ "where u.user_name='" + usernameLogged + "'");
	         if (rs.next()) {
	             isqlCommFileSubset = rs.getString("isql_commands_file_subset_default");
	         }
	         rs.close();
	         statement.close();
	         connection.close();
		} catch (SQLException e) {
			logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
		}
		
		setIsqlCommandsFileSubsetForm(isqlCommFileSubset);
		
    	getDatasetsDb();
        showSubsets();
        setShowAddSubsetForm(true);
        return SUCCESS;
    }
    /**
     * Get the organisation datasets from DB
     * @return String
     * @see
     * @since 1.0
     */
    private String getDatasetsDb() {
        Connection connection;
        String usernameLogged = (String) ServletActionContext.getRequest().getSession().getAttribute("logedUser");
        this.datasets = new HashMap<Integer, String>();
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select datasetId, dataset_desc from aliada.dataset d "
            		+ "inner join aliada.user u on u.organisationId=d.organisationId "
            		+ "where u.user_name='" + usernameLogged + "'");
            while (rs.next()) {
                int code = rs.getInt("datasetId");
                String name = rs.getString("dataset_desc");
                this.datasets.put(code, name);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
        return SUCCESS;   
    }
    /**
     * Get the dataset name from DB
     * @return String
     * @see
     * @since 1.0
     */
    private String getDatasetName() {
    	String name = "";
    	Connection connection;
    	try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from aliada.dataset where datasetId=" + this.dataID);
            while (rs.next()) {
            	name = rs.getString("dataset_desc");
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return ERROR;
        }
    	return name;
    }
    /**
     * @return Returns the subsets.
     * @exception
     * @since 1.0
     */
	public HashMap<Integer, String> getSubsets() {
		return subsets;
	}
	/**
     * @param subsets
     *            The subsets to set.
     * @exception
     * @since 1.0
     */
	public void setSubsets(final HashMap<Integer, String> subsets) {
		this.subsets = subsets;
	}
	/**
     * @return Returns the datasets.
     * @exception
     * @since 1.0
     */
	public HashMap<Integer, String> getDatasets() {
		return datasets;
	}
	/**
     * @param datasets
     *            The datasets to set.
     * @exception
     * @since 1.0
     */
	public void setDatasets(final HashMap<Integer, String> datasets) {
		this.datasets = datasets;
	}
	/**
     * @return Returns the selectedSubset.
     * @exception
     * @since 1.0
     */
	public String getSelectedSubset() {
		return selectedSubset;
	}
	/**
     * @param selectedSubset
     *            The selectedSubset to set.
     * @exception
     * @since 1.0
     */
	public void setSelectedSubset(final String selectedSubset) {
		this.selectedSubset = selectedSubset;
	}
	/**
     * @return Returns the dataID.
     * @exception
     * @since 1.0
     */
	public int getDataID() {
		return dataID;
	}
	/**
     * @param dataID
     *            The dataID to set.
     * @exception
     * @since 1.0
     */
	public void setDataID(final int dataID) {
		this.dataID = dataID;
	}
	/**
     * @return Returns the datasetNameForm.
     * @exception
     * @since 1.0
     */
	public String getDatasetNameForm() {
		return datasetNameForm;
	}
	/**
     * @param datasetNameForm
     *            The datasetNameForm to set.
     * @exception
     * @since 1.0
     */
	public void setDatasetNameForm(final String datasetNameForm) {
		this.datasetNameForm = datasetNameForm;
	}
	/**
     * @return Returns the subsetDescForm.
     * @exception
     * @since 1.0
     */
	public String getSubsetDescForm() {
		return subsetDescForm;
	}
	/**
     * @param subsetDescForm
     *            The subsetDescForm to set.
     * @exception
     * @since 1.0
     */
	public void setSubsetDescForm(final String subsetDescForm) {
		this.subsetDescForm = subsetDescForm;
	}
	/**
     * @return Returns the uriConceptPartForm.
     * @exception
     * @since 1.0
     */
	public String getUriConceptPartForm() {
		return uriConceptPartForm;
	}
	/**
     * @param uriConceptPartForm
     *            The uriConceptPartForm to set.
     * @exception
     * @since 1.0
     */
	public void setUriConceptPartForm(final String uriConceptPartForm) {
		this.uriConceptPartForm = uriConceptPartForm;
	}
	/**
     * @return Returns the graphUriForm.
     * @exception
     * @since 1.0
     */
	public String getGraphUriForm() {
		return graphUriForm;
	}
	/**
     * @param graphUriForm
     *            The graphUriForm to set.
     * @exception
     * @since 1.0
     */
	public void setGraphUriForm(final String graphUriForm) {
		this.graphUriForm = graphUriForm;
	}
	/**
     * @return Returns the linksGraphUriForm.
     * @exception
     * @since 1.0
     */
	public String getLinksGraphUriForm() {
		return linksGraphUriForm;
	}
	/**
     * @param linksGraphUriForm
     *            The linksGraphUriForm to set.
     * @exception
     * @since 1.0
     */
	public void setLinksGraphUriForm(final String linksGraphUriForm) {
		this.linksGraphUriForm = linksGraphUriForm;
	}
	/**
     * @return Returns the isqlCommandsFileSubsetForm.
     * @exception
     * @since 1.0
     */
	public String getIsqlCommandsFileSubsetForm() {
		return isqlCommandsFileSubsetForm;
	}
	/**
     * @param isqlCommandsFileSubsetForm
     *            The isqlCommandsFileSubsetForm to set.
     * @exception
     * @since 1.0
     */
	public void setIsqlCommandsFileSubsetForm(final String isqlCommandsFileSubsetForm) {
		this.isqlCommandsFileSubsetForm = isqlCommandsFileSubsetForm;
	}
	/**
     * @return Returns the showAddSubsetForm.
     * @exception
     * @since 1.0
     */
	public boolean isShowAddSubsetForm() {
		return showAddSubsetForm;
	}
	/**
     * @param showAddSubsetForm
     *            The showAddSubsetForm to set.
     * @exception
     * @since 1.0
     */
	public void setShowAddSubsetForm(final boolean showAddSubsetForm) {
		this.showAddSubsetForm = showAddSubsetForm;
	}
	/**
     * @return Returns the showEditSubsetForm.
     * @exception
     * @since 1.0
     */
	public boolean isShowEditSubsetForm() {
		return showEditSubsetForm;
	}
	/**
     * @param showEditSubsetForm
     *            The showEditSubsetForm to set.
     * @exception
     * @since 1.0
     */
	public void setShowEditSubsetForm(final boolean showEditSubsetForm) {
		this.showEditSubsetForm = showEditSubsetForm;
	}
	/**
     * @return Returns the showTheSubset.
     * @exception
     * @since 1.0
     */
	public boolean isShowTheSubset() {
		return showTheSubset;
	}
	/**
     * @param showTheSubset
     *            The showTheSubset to set.
     * @exception
     * @since 1.0
     */
	public void setShowTheSubset(final boolean showTheSubset) {
		this.showTheSubset = showTheSubset;
	}
	/**
     * @return Returns the areSubsets.
     * @exception
     * @since 1.0
     */
	public boolean isAreSubsets() {
		return areSubsets;
	}
	/**
     * @param areSubsets
     *            The areSubsets to set.
     * @exception
     * @since 1.0
     */
	public void setAreSubsets(final boolean areSubsets) {
		this.areSubsets = areSubsets;
	}
    
}
