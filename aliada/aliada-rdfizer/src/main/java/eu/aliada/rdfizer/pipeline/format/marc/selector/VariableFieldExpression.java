// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.marc.selector;

import java.util.List;

import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

/**
 * A selector expression interpreter for MARC control fields.
 * It accepts these kind of specs:
 * 
 * <ul>
 * 	<li>245t: selects the first not null subfield 't' of field 245;</li>
 * 	<li>245[(<indicators pattern>)]t: selects the first not null subfield 't' of field 245 that has a matching indicator pattern;</li>
 * </ul>
 * 
 * The indicator patterns is an optional 2 chars pattern between parenthesis where each position can be:
 * 
 * <ul>
 * 	<li>a number, therefore a literal value that has to be matched;</li>
 * 	<li>a pound (#), indicating an empty value.</li>
 * 	<li>a question mark: in this case the selector doesn't consider this position (i.e. all values).</li>
 * </ul>
 * 
 * Some examples:
 * 
 * <ul>
 * 	<li>245(#2)a</li>
 * 	<li>856(42)u</li>
 * 	<li>856u = 856(??)u</li>
 * 	<li>856(#4)u</li>
 * </ul>
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class VariableFieldExpression implements Expression<String> {
	static final String MISSING_PARENTHESIS_ERR_MESSAGE = "Invalid specs (%s): a parenthesis is missing.";
	static final String BAD_INDICATORS_PATTERN_ERR_MESSAGE = "Invalid specs (%s): bad indicators pattern.";
	
	private final String fieldNumber;
	private final char subfieldCode;
	
	private final boolean hasIndicatorPattern;
	private char firstIndicatorPattern;
	private char secondIndicatorPattern;
	
	/**
	 * Builds a new expression with the given specs.
	 * 
	 * @param specs the expression specs.
	 */
	public VariableFieldExpression(final String specs) {
		if (specs == null || specs.trim().length() == 0) {
			throw new IllegalArgumentException(NULL_SPECS_ERR_MESSAGE);
		}
		
		final int indexOfOpeningParenthesis = specs.indexOf("(");
		final int indexOfClosingParenthesis = specs.indexOf(")", indexOfOpeningParenthesis);
		
		if ((indexOfOpeningParenthesis != -1 && indexOfClosingParenthesis == -1)
			||  
			(indexOfOpeningParenthesis == -1 && indexOfClosingParenthesis != -1)) {
			throw new IllegalArgumentException(String.format(MISSING_PARENTHESIS_ERR_MESSAGE, specs));	
		}
		
		if (indexOfOpeningParenthesis != -1 && indexOfClosingParenthesis != -1) {
			final String pattern = specs.substring(indexOfOpeningParenthesis + 1, indexOfClosingParenthesis).trim();
			if (pattern.length() != 2) {				
				throw new IllegalArgumentException(String.format(BAD_INDICATORS_PATTERN_ERR_MESSAGE, specs));		
			}
			
			firstIndicatorPattern = pattern.charAt(0);
			secondIndicatorPattern = pattern.charAt(1);
			hasIndicatorPattern = true;
			
			subfieldCode = specs.charAt(specs.trim().length() - 1);
			fieldNumber = specs.substring(0, indexOfOpeningParenthesis).trim();
		} else {
			fieldNumber = specs.substring(0, 3);
			subfieldCode = specs.charAt(specs.trim().length() - 1);			
			hasIndicatorPattern = false;
		}
	}
	
	@Override
	public String evaluate(final Record target) {
		final List<VariableField> fields = target.getVariableFields(fieldNumber);
		if (fields == null || fields.isEmpty()) {
			return null;
		}
		
		for (final VariableField field : fields) {
			final DataField dataField = (DataField) field;
			if (hasIndicatorPattern) {
				if (!(matches(firstIndicatorPattern, dataField.getIndicator1()) && matches(secondIndicatorPattern, dataField.getIndicator2())))
				{
					continue;
				}
			}
			
			final List<Subfield> subfields = dataField.getSubfields(subfieldCode);
			if (subfields == null || subfields.isEmpty()) {
				continue;
			}
			
			for (final Subfield subfield : subfields) {
				final String data = subfield.getData();
				if (data != null) {
					return data;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Returns true if the given pattern matches the given indicator.
	 * 
	 * @param pattern the pattern (see specs in class comments).
	 * @param indicator the indicator value.
	 * @return true if the given pattern matches the given indicator.
	 */
	boolean matches(final char pattern, final char indicator) {
		switch(pattern) {
		case '?':
			return true;
		case '#':
			return indicator == '#' || indicator == ' ';
		default:
			return indicator == pattern;
		}
	}
}