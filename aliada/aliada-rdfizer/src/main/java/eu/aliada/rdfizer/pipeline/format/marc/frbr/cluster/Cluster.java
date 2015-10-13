package eu.aliada.rdfizer.pipeline.format.marc.frbr.cluster;

import java.util.ArrayList;
import java.util.List;

/**
 * A (transient) cluster object representation.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class Cluster {
	private final int id;
	
	private List<ClusterEntry> entries = new ArrayList<ClusterEntry>();
	
	/**
	 * Builds a new cluster with the given identifier.
	 * 
	 * @param id the identifier.
	 */
	public Cluster(final int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void addEntry(final ClusterEntry entry) {
		getEntries().add(entry);
	}
	
	public List<ClusterEntry> getEntries() {
		if (entries == null) {
			entries = new ArrayList<ClusterEntry>();
		}
		return entries;
	}
	
	public boolean isDummy() {
		return false;
	}

	public int size() {
		return entries != null ? entries.size() : 0;
	}
}