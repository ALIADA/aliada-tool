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

	public List<Integer> parents(){
		return parents;
	}
	
	public void addParent(final Integer parentId) {
		parents.add(parentId);
	}
	
	public boolean doesContainOneOfTheseParents(final Set<Cluster> titlesClusters) {
		return titlesClusters.stream().filter(cluster -> parents.contains(cluster.id)).findFirst().isPresent();
	}
}