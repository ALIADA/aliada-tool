package eu.aliada.rdfizer.pipeline.format.marc.frbr;

public final class FrbrExpression {
	public final String lang;
	public final boolean orphan;

	public FrbrExpression(final String lang, final boolean orphan) {
		this.lang = lang;
		this.orphan = orphan;
	}

	public String getLang() {
		return lang;
	}

	public boolean isOrphan() {
		return orphan;
	}
}
