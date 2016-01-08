package eu.aliada.rdfizer.pipeline.format.xml;

/**
 * A NullObject marker.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class NullObject {

	public static final NullObject instance = new NullObject();
	
	/**
	 * Empty and private constructor.
	 */
	private NullObject(){}
	
	@Override
	public String toString() {
		return "NULL-OBJECT";
	}
}