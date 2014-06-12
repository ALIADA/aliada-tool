// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.domain;

/**
 * A piece of record, together with a correlation identifier.
 * Sometimes, depending on the input data format, records can be split in sub-parts (i.e. chunks) and 
 * those parts can be processed in parallel. This obviously leads to a high conversion throughput in terms of 
 * triples emitted.
 * However, as said, the specific format and the corresponding conversion rules must be aware that each chunk will
 * be processed autonomously and independent from the other chunks, without knowning what is happening to the other
 * parts of the record.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 * @param <K> the correlation identifier kind.
 * @param <C> the kind of the record fragment.
 */
public class RecordChunk<K, C> {
	
	// CHECKSTYLE:OFF
	public final K correlationId;
	public final C fragment;
	// CHECKSTYLE:ON
	
	/**
	 * Builds a new chunk with the given data.
	 * 
	 * @param correlationId the correlation identifier.
	 * @param fragment the record fragment, that is, this chunk content.
	 */
	public RecordChunk(final K correlationId, final C fragment) {
		this.correlationId = correlationId;
		this.fragment = fragment;
	}
}
