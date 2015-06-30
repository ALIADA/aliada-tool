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
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**@author xabi
 * @version $Revision: 1.1 $, $Date: 2015/03/23 15:20:54 $
 * @since 1.0 */
public class ExtDatasetsAction extends ActionSupport {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<Integer, String> externalDatasets;
	private List<Integer> selectedExternalDatasets;
	private List<String> val;
	
	private final Log logger = new Log(ExtDatasetsAction.class);
	private ResourceBundle defaults = ResourceBundle.getBundle("defaultValues", getLocale());
	
	/** Show the external datasets selected in the UI.
     * @return String */
	public String showExtDatasets() {
		
		ServletActionContext.getRequest().getSession().setAttribute("action", defaults.getString("lang.showExtDatasets"));
		
    	selectedExternalDatasets = new ArrayList<>();
        try {
        	Connection connection = new DBConnectionManager().getConnection();
			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("select * from aliada.t_external_dataset where language = '" + getLocale().getISO3Language() + "'");
			
	        externalDatasets = new HashMap<Integer, String>();
	        while (rs.next()) {
	            externalDatasets.put(rs.getInt("external_dataset_code"), rs.getString("external_dataset_name"));
	            selectedExternalDatasets.add(rs.getInt("external_dataset_linkingreloadtarget"));
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
    
    /** Reload the external datasets selected in the UI.
     * @return String */
    public String reloadExternalDatasets() {
    	Methods m = new Methods();
    	m.setLang(getLocale().getISO3Language());
    	m.cleanReloadDataset();
    	Connection connection = null;
    		if (val != null) {
    			for (int i = 0; i < val.size(); i++) {
    				String extDataset = val.get(i);
    				try {
    		            connection = new DBConnectionManager().getConnection();
    		            Statement updateStatement = connection.createStatement();
                        updateStatement.executeUpdate("UPDATE aliada.t_external_dataset set external_dataset_linkingreloadtarget='1' "
                        		+ "where external_dataset_name='" + extDataset + "'");
                        updateStatement.close();
    		            connection.close();
    		        } catch (SQLException e) {
    		            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
    		            return ERROR;
    		        }
    			}
    		}
    	addActionMessage(getText("datasets.reload.ok"));
    	return showExtDatasets();
    }
	
	/** @return Returns the externalDatasets. */
	public HashMap<Integer, String> getExternalDatasets() {
		return externalDatasets;
	}
	/** @param externalDatasets The externalDatasets to set. */
	public void setExternalDatasets(final HashMap<Integer, String> externalDatasets) {
		this.externalDatasets = externalDatasets;
	}
	/** @return Returns the selectedExternalDatasets. */
	public List<Integer> getSelectedExternalDatasets() {
		return selectedExternalDatasets;
	}
	/** @param selectedExternalDatasets The selectedExternalDatasets to set. */
	public void setSelectedExternalDatasets(final List<Integer> selectedExternalDatasets) {
		this.selectedExternalDatasets = selectedExternalDatasets;
	}
	/** @return Returns the val. */
	public List<String> getVal() {
		return val;
	}
	/** @param val The val to set. */
	public void setVal(final List<String> val) {
		this.val = val;
	}
	
}
