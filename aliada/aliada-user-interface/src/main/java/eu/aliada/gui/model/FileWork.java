// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.model;

import java.io.File;

/**
 * @author iosa
 * @since 1.0
 */
public class FileWork {
    private File file;
    private String profile;
    private String template;
    private String graph;
    private String status;
    
    public String getFilename(){
        return file.getName();
    }
    /**
     * @return Returns the file.
     * @exception
     * @since 1.0
     */
    public File getFile() {
        return file;
    }
    /**
     * @param file The file to set.
     * @exception
     * @since 1.0
     */
    public void setFile(File file) {
        this.file = file;
    }
    /**
     * @return Returns the profile.
     * @exception
     * @since 1.0
     */
    public String getProfile() {
        return profile;
    }
    /**
     * @param profile The profile to set.
     * @exception
     * @since 1.0
     */
    public void setProfile(String profile) {
        this.profile = profile;
    }
    /**
     * @return Returns the template.
     * @exception
     * @since 1.0
     */
    public String getTemplate() {
        return template;
    }
    /**
     * @param template The template to set.
     * @exception
     * @since 1.0
     */
    public void setTemplate(String template) {
        this.template = template;
    }
    /**
     * @return Returns the graph.
     * @exception
     * @since 1.0
     */
    public String getGraph() {
        return graph;
    }
    /**
     * @param graph The graph to set.
     * @exception
     * @since 1.0
     */
    public void setGraph(String graph) {
        this.graph = graph;
    }
    /**
     * @return Returns the status.
     * @exception
     * @since 1.0
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status The status to set.
     * @exception
     * @since 1.0
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
