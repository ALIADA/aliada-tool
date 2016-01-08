package eu.aliada.rdfizer.pipeline.format.marc.frbr.cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A (transient) cluster object representation.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class Cluster {
	private final int id;
	
	private List<ClusterEntry> entries;
	private List<Integer> parents = new ArrayList<Integer>();
	
	/**
	 * Builds a new cluster with the given identifier.
	 * 
	 * @param id the identifier.
	 */
	public Cluster(final int id) {
		this.id = id;
	}
	
	/**
	 * Returns the identifier associated with this entry.
	 * 
	 * @return the identifier associated with this entry.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Adds an entry to this cluster.
	 * 
	 * @param entry an entry to this cluster.
	 */
	public void addEntry(final ClusterEntry entry) {
		getEntries().add(entry);
	}
	
	/**
	 * Returns the entries belonging to this cluster.
	 * 
	 * @return the entries belonging to this cluster.
	 */
	public List<ClusterEntry> getEntries() {
		if (entries == null) {
			entries = new ArrayList<ClusterEntry>();
		}
		return entries;
	}
	
	/**
	 * Returns true if this is a "fake" cluster.
	 * 
	 * @return true if this is a "fake" cluster.
	 */
	public boolean isDummy() {
		return false;
	}

	/**
	 * Returns the size of the cluster. 
	 * 
	 * @return the size of the cluster.
	 */
	public int size() {
		return entries != null ? entries.size() : 0;
	}

	/**
	 * Returns the parents of this cluster.
	 * 
	 * @return the parents of this cluster.
	 */
	public List<Integer> parents(){
		return parents;
	}
	
	/**
	 * Adds a new parent to this cluster.
	 * 
	 * @param parentId the parent identifier.
	 */
	public void addParent(final Integer parentId) {
		parents.add(parentId);
	}
	
	/**
	 * Returns true if this (title) cluster contains at least one of the given parents.
	 * 
	 * @param titlesClusters the set of clusters identifiers to be checked.
	 * @return true if this (title) cluster contains at least one of the given parents.
	 */
	public boolean doesContainOneOfTheseParents(final Set<Cluster> titlesClusters) {
		return titlesClusters.stream().filter(cluster -> parents.contains(cluster.id)).findFirst().isPresent();
	}
}