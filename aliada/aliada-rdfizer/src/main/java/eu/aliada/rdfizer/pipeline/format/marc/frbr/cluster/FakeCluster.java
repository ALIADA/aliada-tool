package eu.aliada.rdfizer.pipeline.format.marc.frbr.cluster;

import java.util.UUID;

/**
 * A NullObject Cluster implementation.
 * 
 * @author Andrea Gazzarini
 * @since 2.0
 */
public class FakeCluster extends Cluster {

	private final String headingAsId;
	
	/**
	 * Builds a new {@link FakeCluster} for the given heading.
	 * 
	 * @param heading the heading.
	 */
	public FakeCluster(final String heading) {
		super(Integer.MAX_VALUE);
		headingAsId = UUID.nameUUIDFromBytes(heading.getBytes()).toString();
	}
	
	@Override
	public boolean isDummy() {
		return true;
	}

	/**
	 * Returns the heading that owns this cluster.
	 * 
	 * @return the heading that owns this cluster.
	 */
	public String getHeadingAsId() {
		return headingAsId;
	}
}