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
                this.linksGraphUriForm = rs.getString("links_graph_uri");
                this.isqlCommandsFileSubsetForm = rs.getString("isql_commands_file_subset");
                statement.close();
                rs.close();
                connection.close();
                session.setAttribute("SubsetId", subsetId);
                showSubsets();
                this.showEditSubsetForm = true;
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
     * The method to edit the subset selected.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String editSubset() {
    	HttpSession session = ServletActionContext.getRequest().getSession();
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE aliada.subset set datasetId='"
                    + this.dataID + "',subset_desc='"
                    + this.subsetDescForm + "',uri_concept_part='"
                    + this.uriConceptPartForm + "',graph_uri='"
                    + this.graphUriForm + "',links_graph_uri='"
                    + this.linksGraphUriForm + "',isql_commands_file_subset='"
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
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
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
    	 Connection connection = null;
    	 HttpSession session = ServletActionContext.getRequest().getSession();
     	 int datasetId = (int) session.getAttribute("DatasetId");
         try {
        	 connection = new DBConnectionManager().getConnection();
             
             Statement statement = connection.createStatement();
             statement.executeUpdate("INSERT INTO aliada.subset (datasetId, subset_desc, uri_concept_part, "
             		+ "graph_uri, links_graph_uri, isql_commands_file_subset) VALUES (" + datasetId + ",'"
                     + this.subsetDescForm + "', '" + this.uriConceptPartForm + "', '"
                     + this.graphUriForm + "', '" + this.linksGraphUriForm + "', '"
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
