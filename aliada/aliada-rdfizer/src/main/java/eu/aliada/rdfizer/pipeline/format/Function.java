package eu.aliada.rdfizer.pipeline.format;

import eu.aliada.shared.ID;
import eu.aliada.shared.Strings;

public class Function {
	public long getId() {
		return ID.get();
	}
	
	public String normalize(final String value) {
		return Strings.toURILocalName(value);
	}
}
