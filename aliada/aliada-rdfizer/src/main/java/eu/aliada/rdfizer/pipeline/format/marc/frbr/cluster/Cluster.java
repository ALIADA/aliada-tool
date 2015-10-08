package eu.aliada.rdfizer.pipeline.format.marc.frbr.cluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cluster {
	private final int id;
	
	private List<Map<String, String>> entries = new ArrayList<Map<String, String>>();
	
	public Cluster(final int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void addEntry(final String id, final String label) {
		final Map<String, String> entry = new HashMap<String, String>();
		entry.put(id, label);
		getEntries().add(entry);
	}
	
	public List<Map<String, String>> getEntries() {
		if (entries == null) {
			entries = new ArrayList<Map<String, String>>();
		}
		return entries;
	}
	
	public boolean isDummy() {
		return false;
	}
}
