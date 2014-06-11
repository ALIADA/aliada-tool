// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.mx;

public interface Job {
	
	boolean isRunning();
	
	String getAssociatedFormat();
	
	boolean isSplitCompleted();
	
	int getTotalRecordsCount();
	
	int getProcessedRecordsCount();
	
	int getOutputStatementsCount();
	
	double getStatementsThroughput();
}
