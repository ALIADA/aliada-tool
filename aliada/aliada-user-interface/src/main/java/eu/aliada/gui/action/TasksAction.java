// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.gui.model.FileWork;
import eu.aliada.gui.rdbms.DBConnectionManager;
import eu.aliada.shared.log.Log;

/**
 * 
 * @author amaya
 * @version $Revision: 1.1 $
 * @since 1.0
 */
public class TasksAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<FileWork> pendingFiles;
    private String selectedPendingFile;
    private boolean arePendingFiles;

	private String filenameForm;
    private String profileForm;
    private String statusForm;

	private final Log logger = new Log(TasksAction.class);
    
    /**
     * Gets the pendingFiles from the DB.
     * @return String
     * @see
     * @since 1.0
     */
    public String getPendingFilesDb() {
    	
        // HttpSession session = ServletActionContext.getRequest().getSession();		
		String usernameLogged = (String) ServletActionContext.getRequest().getSession().getAttribute("logedUser");
        try {
			Connection connection = new DBConnectionManager().getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement
					.executeQuery("select user_name,file_name,datafile,profile,template,graph,status,job_id from "
							+ "aliada.user_session where user_name ='" + usernameLogged + "'");
			if (rs != null) {
				if (pendingFiles == null) {
					pendingFiles = new ArrayList<FileWork>();
				}
				while (rs.next()) {
					File fileCreated = new File(rs.getString("datafile"), rs.getString("file_name"));
					FileWork fileWork = new FileWork();
					fileWork.setFilename(fileCreated.getName());
					fileWork.setFile(fileCreated);
					fileWork.setProfile(getProfileNameFromCode(Integer.toString(rs.getInt("profile"))));
					if (rs.getString("template") != null) {
						fileWork.setTemplate(getTemplateNameFromCode(String.valueOf(rs.getInt("template"))));
					}
					if (rs.getString("graph") != null) {
						fileWork.setGraph(getGraphUri(String.valueOf(rs.getInt("graph"))));
					}
					fileWork.setStatus(rs.getString("status"));
					
					fileWork.setJobId(rs.getInt("job_id"));
					
					pendingFiles.add(fileWork);
				}
			}
			rs.close();
			statement.close();
			connection.close();
			  if (pendingFiles.isEmpty()) {
	                setArePendingFiles(false);
	            } else {
	            	setArePendingFiles(true);
	            }
		} catch (SQLException e) {
			logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
			return ERROR;
		}
		return SUCCESS;
    }
   
    /**
     * Set recover file from the DB.
     * @return String
     * @see
     * @since 1.0
     */
    public String setRecoverdFile() {
    	String status = "ERROR";
    	
    	HttpSession session = ServletActionContext.getRequest().getSession();
    	//Get file data from database
    	Connection connection = null;
  	   
          try {
              connection = new DBConnectionManager().getConnection();
              Statement statement;
              statement = connection.createStatement();
              ResultSet rs = statement
                      .executeQuery("select user_name,file_name,datafile,profile,template,graph,status,job_id from "
                      		+ "aliada.user_session where file_name='" + selectedPendingFile + "'");
              if (rs.next()) {
                  File fileCreated = new File(rs.getString("datafile"), rs.getString("file_name"));
    			  FileWork recoveredFile = new FileWork();
    			  recoveredFile.setFilename(fileCreated.getName());
    			  recoveredFile.setFile(fileCreated);
    			  recoveredFile.setProfile(getProfileNameFromCode(Integer.toString(rs.getInt("profile"))));
    			  if (rs.getString("template") != null) {
    				recoveredFile.setTemplate(getTemplateNameFromCode(String.valueOf(rs.getInt("template"))));
    			  }
    			  if (rs.getString("graph") != null) {
    				recoveredFile.setGraph(getGraphUri(String.valueOf(rs.getInt("graph"))));
    			  }
    			  status = rs.getString("status");
    			
    			  recoveredFile.setStatus(status);
    			
    			  recoveredFile.setJobId(rs.getInt("job_id"));
    			  
    			  pendingFiles = (List<FileWork>) session.getAttribute("importedFiles");
    			  
    			  if (pendingFiles != null) {
    				  for (int i = 0; i < pendingFiles.size(); i++) {
    					  if (pendingFiles.get(i).getFilename().equals(recoveredFile.getFilename())) {
    						  pendingFiles.remove(i);
    					  }
    				  }
    				  pendingFiles.add(recoveredFile);
    				  session.setAttribute("importedFiles", pendingFiles);
    			  } else {
    				  pendingFiles = new ArrayList<FileWork>();
    				  pendingFiles.add(recoveredFile);
    				  session.setAttribute("importedFiles", pendingFiles);
    			  }
    			  
    			  session.setAttribute("importedFile", recoveredFile);


    			  if (status.equals("idle")) {
    				  session.setAttribute("rdfizerStatus", "idle");
    			  } else if (status.equals("runningRdfizer")) {
    				  session.setAttribute("rdfizerStatus", "running");
    				  session.setAttribute("rdfizerJobId", recoveredFile.getJobId());
    			  } else if (status.equals("finishedRdfizer")) { 
    				  session.setAttribute("rdfizerStatus", "finishedRdfizer");
    			  } else if (status.equals("finishedLinking")) {
    				  session.setAttribute("rdfizerStatus", "finishedLinking");
    			  }
    			  
              }
              rs.close();
              statement.close();
              connection.close(); 
          
             
          } catch (SQLException e) {
              logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
              status = "ERROR";
          }
    	
    	// System.out.println(""+status+"");
          return status;
    }
    
    /**
     * Delete file from the control panel and DB.
     * @return String
     * @see
     * @since 1.0
     */
    public String deleteFile() {
        Connection connection = null;
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            List<FileWork> jobs =  (List<FileWork>) ServletActionContext.getRequest().getSession().getAttribute("importedFiles");
            if (jobs != null) {
	            for (int i = 0; i < jobs.size(); i++) {
	            	if (jobs.get(i).getFilename().equals(selectedPendingFile)) {
	            		jobs.remove(i);
	            	}
	            }
            }
            int correct = statement.executeUpdate("DELETE FROM aliada.user_session where file_name='" + selectedPendingFile + "'");
            statement.close();
            connection.close();
            if (correct == 0) {
                clearErrorsAndMessages();
                addActionError(getText("job.not.selected"));                
            } else {
                clearErrorsAndMessages();
                addActionMessage(getText("job.delete.ok"));
            }
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            getPendingFilesDb();
            return ERROR;
        }
        return getPendingFilesDb();
    }
    
    /**
     * @return Returns the selectedUser.
     * @exception
     * @since 1.0
     */
    public String getSelectedPendingFile() {
        return selectedPendingFile;
    }


    /**
     * @return Returns the filenameForm.
     * @exception
     * @since 1.0
     */
    public String getFilenameForm() {
        return filenameForm;
    }
    /**
     * @param filenameForm The filenameForm to set.
     * @exception
     * @since 1.0
     */
    public void setFilenameForm(final String filenameForm) {
        this.filenameForm = filenameForm;
    }
    /**
     * @return Returns the profileForm.
     * @exception
     * @since 1.0
     */
    public String getProfileForm() {
        return profileForm;
    }
    /**
     * @param profileForm The roleForm to set.
     * @exception
     * @since 1.0
     */
    public void setRoleForm(final String profileForm) {
        this.profileForm = profileForm;
    }
    /**
     * @return Returns the statusForm.
     * @exception
     * @since 1.0
     */
    public String getStatusForm() {
        return statusForm;
    }
    /**
     * @param statusForm The statusForm to set.
     * @exception
     * @since 1.0
     */
    public void setStatusForm(final String statusForm) {
        this.statusForm = statusForm;
    }
    /**
     * @param selectedPendingFile The selectedPendingFile to set.
     * @exception
     * @since 1.0
     */
    public void setSelectedPendingFile(final String selectedPendingFile) {
        this.selectedPendingFile = selectedPendingFile;
    }
    
    /**
     * @return Returns the pendingFiles.
     * @exception
     * @since 1.0
     */
    public List<FileWork> getPendingFiles() {
        return pendingFiles;
    }
    /**
     * @param pendingFiles The pendingFiles to set.
     * @exception
     * @since 1.0
     */
    public void setPendingFiles(final List<FileWork> pendingFiles) {
        this.pendingFiles = pendingFiles;
    }
    

    /**
     * @return Returns the arePendingFiles.
     * @exception
     * @since 1.0
     */
    public boolean isArePendingFiles() {
        return arePendingFiles;
    }

    /**
     * @param arePendingFiles The arePendingFiles to set.
     * @exception
     * @since 1.0
     */
    public void setArePendingFiles(final boolean arePendingFiles) {
        this.arePendingFiles = arePendingFiles;
    }
    
    /**
     * Get profile name.
     * @param selectedProfile The selected profile
     * @return String
     * @see
     * @since 1.0
     */
    private String getProfileNameFromCode(final String selectedProfile) {
	    Connection connection = null;
	    String profileName = "";
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement;
            statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select profile_name from aliada.profile where profile_id=" + selectedProfile);
            if (rs.next()) {
                profileName = rs.getString("profile_name");
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return "";
        }
        return profileName;
	}
   
    /**
     * Get the name of the the template from a give template code.
     * @param selectedTemplate The selected template
     * @return String
     * @see
     * @since 1.0
     */
    private String getTemplateNameFromCode(final String selectedTemplate) {
        Connection connection = null;
        String templateName = "";
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement;
            statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select template_name from aliada.template where template_id=" + selectedTemplate);
            if (rs.next()) {
                templateName = rs.getString("template_name");
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return "";
        }
        return templateName;
    }
    
    /**
     * Gets the graph uri from a graph code.
     * @param graphCode The selected graph
     * @return String 
     * @see
     * @since 1.0
     */
    public String getGraphUri(final String graphCode) {
        Connection connection = null;
        String graphUri = "";
        try {
            connection = new DBConnectionManager().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT graph_uri FROM graph WHERE graphId='" + graphCode + "';");
            if (rs.next()) {
                graphUri = rs.getString("graph_uri");
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(MessageCatalog._00011_SQL_EXCEPTION, e);
            return "";
        }
        return graphUri;
    }
}
