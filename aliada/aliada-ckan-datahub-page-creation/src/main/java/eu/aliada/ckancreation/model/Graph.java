// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-ckan-datahub-page-creation
// Responsible: ALIADA Consortium
package eu.aliada.ckancreation.model;

import java.util.ArrayList;


/**
 * Graph info.
 * 
 * @author Idoia Murua
 * @since 2.0
 */
public class Graph {
	/** Graph identification number. */
	private int id;
	/** Organization identification number in ALIADA DB. */
	private int orgId;
	/** Graph URI. */
	private String uri;
	/** Graph description. */
	private String description;
	/** Dump files of the graph. */
	private ArrayList<DumpFileInfo> dumpFilesInfo = new ArrayList<DumpFileInfo>();
	/** Dump files format **/
	private String dumpFileFormat;

    
	/**
	 * Returns the identifier of this graph.
	 * 
	 * @return The identifier of this graph.
	 * @since 2.0
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * Sets the identifier of this graph.
	 * 
	 * @param id The identifier of this graph.
	 * @since 2.0
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * Returns the identifier of the organization in ALIADA DB.
	 * 
	 * @return The identifier of the organization in ALIADA DB.
	 * @since 2.0
	 */
	public Integer getOrgId() {
		return orgId;
	}
	/**
	 * Sets the identifier of the organization in ALIADA DB.
	 * 
	 * @param id The identifier of the organization in ALIADA DB.
	 * @since 2.0
	 */
	public void setOrgId(final Integer orgId) {
		this.orgId = orgId;
	}

	/**
	 * Returns the graph URI.
	 * 
	 * @return The graph URI.
	 * @since 2.0
	 */
	public String getUri() {
		return uri;
	}
	/**
	 * Sets the graph URI.
	 * 
	 * @param uri The graph URI.
	 * @since 2.0
	 */
	public void setUri(final String uri) {
		this.uri = uri;
	}

	/**
	 * Returns the graph description.
	 * 
	 * @return The graph description.
	 * @since 2.0
	 */
	public String getDescription() {
		return this.description;
	}
	/**
	 * Sets the graph description.
	 * 
	 * @param description The graph description.
	 * @since 2.0
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the dump files of the graph.
	 * 
	 * @return The dump files of the graph.
	 * @since 2.0
	 */
	public DumpFileInfo[] getDumpFiles() {
		return this.dumpFilesInfo.toArray(new DumpFileInfo[]{});
	}
	/**
	 * Adds a dump file to the graph.
	 * 
	 * @param dumpFile The dump file to add to the graph.
	 * @since 2.0
	 */
	public void addDumpFile(final DumpFileInfo dumpFileInfo) {
		dumpFilesInfo.add(dumpFileInfo);
	}

	/**
	 * Returns the dump file format.
	 * 
	 * @return The dump file format.
	 * @since 2.0
	 */
	public String getDumpFileFormat() {
		return this.dumpFileFormat;
	}
	/**
	 * Sets the dump file format.
	 * 
	 * @param dumpFileFormat The dump file format.
	 * @since 2.0
	 */
	public void setDumpFileFormat(String dumpFileFormat) {
		this.dumpFileFormat = dumpFileFormat;
	}

}
