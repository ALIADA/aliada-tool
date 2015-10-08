package eu.aliada.rdfizer.pipeline.format.marc.frbr.cluster;

import java.util.UUID;

public class FakeCluster extends Cluster {

	private final String headingAsId;
	
	public FakeCluster(final String heading) {
		super(Integer.MAX_VALUE);
		headingAsId = UUID.nameUUIDFromBytes(heading.getBytes()).toString();
	}
	
	@Override
	public boolean isDummy() {
		return true;
	}

	public String getHeadingAsId() {
		return headingAsId;
	}
}
