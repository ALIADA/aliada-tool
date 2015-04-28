package eu.aliada.rdfizer.pipeline.format.marc.frbr;

import eu.aliada.rdfizer.pipeline.format.marc.frbr.model.FrbrDocument;

/**
 * An FRBR entities detector that doesn't throw any exception in case there are no detected entities.
 * 
 * @author Andrea Gazzarini
 * @since 2.0
 */
public class LenientFrbrEntitiesDetector extends FrbrEntitiesDetector {
	@Override
	protected boolean isValid(final FrbrDocument entitiesDocument) {
		return true;
	}
}