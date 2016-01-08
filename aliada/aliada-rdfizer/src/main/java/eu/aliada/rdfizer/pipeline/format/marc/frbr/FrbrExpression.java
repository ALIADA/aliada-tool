package eu.aliada.rdfizer.pipeline.format.marc.frbr;

/**
 * FRBR Expression value object implementation.
 * 
 * @author Andrea Gazzarini
 * @since 2.0
 */
public final class FrbrExpression {
	public final String lang;
	public final boolean orphan;

	/**
	 * Builds a new expression with the given data.
	 * 
	 * @param lang the language.
	 * @param orphan true if the expression is associated with an orphan result.
	 */
	public FrbrExpression(final String lang, final boolean orphan) {
		this.lang = lang;
		this.orphan = orphan;
	}

	/**
	 * Returns the language associated with the outcoming expression.
	 * 
	 * @return the language associated with the outcoming expression.
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * Returns true if the outcoming expression is orphan.
	 * An expression is supposed to be orphan if it doesn't have any child manifestation.
	 * 
	 * @return true if the outcoming expression is orphan.
	 */
	public boolean isOrphan() {
		return orphan;
	}
}