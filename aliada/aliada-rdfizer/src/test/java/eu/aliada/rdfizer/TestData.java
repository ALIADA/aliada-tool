// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer;

import java.util.Random;

/**
 * Data used for tests.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public interface TestData {
	String A_CONTROL_FIELD_NAME = "008";
	String A_VARIABLE_FIELD_NAME = "245";
	String A_CONTROL_FIELD_VALUE = "791031c19789999dcuar1########0###a0eng#d";
	
	Random RANDOMIZER = new Random();
}
