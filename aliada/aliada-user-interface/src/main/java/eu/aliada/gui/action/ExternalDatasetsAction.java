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
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**
 * This class is the external datasets administration.
 * @author xabier
 * @version $Revision: 1.1 $, $Date: 2015/02/19 15:20:54 $
 * @since 1.0
 */
public class ExternalDatasetsAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<Integer, String> externalDatasets;
	private List<String> val;
	
	private final Log logger = new Log(ExternalDatasetsAction.class);

    /**
     * The method to show the external datasets list.
     * 
     * @return String
     * @see
     * @since 1.0
     */
    public String showExternalDatasets() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select * from aliada.t_external_dataset");
            externalDatasets = new HashMap<Integer, String>();
            while (rs.next()) {
                externalDatasets.put(rs.getInt("external_dataset_code"),
                        rs.getString("external_dataset_name"));
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
     * Reload the external datasets selected in the UI.
     * @return String
     * @see
     * @since 1.0
     */
    public String reloadExternalDatasets() {
    	
//    	if (val != null) {
//    		}
    		
    	return SUCCESS;
    }
    /**
     * @return Returns the externalDatasets.
     * @exception
     * @since 1.0
     */
	public HashMap<Integer, String> getExternalDatasets() {
		return externalDatasets;
	}
	/**
     * @param externalDatasets
     *            The externalDatasets to set.
     * @exception
     * @since 1.0
     */
	public void setExternalDatasets(final HashMap<Integer, String> externalDatasets) {
		this.externalDatasets = externalDatasets;
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
     * @param val
     *            The val to set.
     * @exception
     * @since 1.0
     */
	public void setVal(final List<String> val) {
		this.val = val;
	}
	
	

}
