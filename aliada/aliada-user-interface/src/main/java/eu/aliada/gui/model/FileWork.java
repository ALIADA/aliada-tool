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
    private boolean fileChecked;
    private File file;
    private String filename;
    private int profile;
    private int template;
    private String dataset;
    private String graph;
    private String status;
    private int jobId;
    private int ldJobId;
    private int ldsJobId;
    
    
    /**
     * @return Returns the fileChecked.
     * @exception
     * @since 1.0
     */
    public boolean isFileChecked() {
        return fileChecked;
    }
    /**
     * @param fileChecked The fileChecked to set.
     * @exception
     * @since 1.0
     */
    public void setFileChecked(final boolean fileChecked) {
        this.fileChecked = fileChecked;
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
    public void setFile(final File file) {
        this.file = file;
    }
    
    /**
     * @return Returns the filename.
     * @exception
     * @since 1.0
     */
    public String getFilename() {
        return filename;
    }
    /**
     * @param filename The filename to set.
     * @exception
     * @since 1.0
     */
    public void setFilename(final String filename) {
        this.filename = filename;
    }
    /**
     * @return Returns the profile.
     * @exception
     * @since 1.0
     */
    public int getProfile() {
        return profile;
    }
    /**
     * @param profile The profile to set.
     * @exception
     * @since 1.0
     */
    public void setProfile(final int profile) {
        this.profile = profile;
    }
    /**
     * @return Returns the template.
     * @exception
     * @since 1.0
     */
    public int getTemplate() {
        return template;
    }
    /**
     * @param template The template to set.
     * @exception
     * @since 1.0
     */
    public void setTemplate(final int template) {
        this.template = template;
    }
    /**
     * @return Returns the dataset.
     * @exception
     * @since 1.0
     */
    public String getDataset() {
		return dataset;
	}
    /**
     * @param dataset The dataset to set.
     * @exception
     * @since 1.0
     */
	public void setDataset(final String dataset) {
		this.dataset = dataset;
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
    public void setGraph(final String graph) {
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
    public void setStatus(final String status) {
        this.status = status;
	}
    /**
     * @return Returns the jobId.
     * @exception
     * @since 1.0
     */
    public int getJobId() {
        return jobId;
    }
    /**
     * @param jobId The jobId to set.
     * @exception
     * @since 1.0
     */
    public void setJobId(final int jobId) {
        this.jobId = jobId;
    }
    /**
     * @return Returns the ldJobId.
     * @exception
     * @since 1.0
     */
    public int getLdJobId() {
        return ldJobId;
    }
    /**
     * @param ldJobId The ldJobId to set.
     * @exception
     * @since 1.0
     */
    public void setLdJobId(final int ldJobId) {
        this.ldJobId = ldJobId;
    }
    /**
     * @return Returns the ldsJobId.
     * @exception
     * @since 1.0
     */
    public int getLdsJobId() {
        return ldsJobId;
    }
    /**
     * @param ldsJobId The ldsJobId to set.
     * @exception
     * @since 1.0
     */
    public void setLdsJobId(final int ldsJobId) {
        this.ldsJobId = ldsJobId;
    }

}
