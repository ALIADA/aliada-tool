// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.datasource.rdfstore;

import org.springframework.stereotype.Repository;

import com.hp.hpl.jena.query.ARQ;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

@Repository
public class AliadaRDFStoreDAO {
	
	private String CRM_TO_ALIADA_CLASS_P1 = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " 
			+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> " 
			+ "SELECT * FROM <http://aliada-project.eu/2014/aliada-ontology#> " 
			+ "WHERE " 
			+ "{ " 
			+ " ?class_name rdf:type owl:Class . " 
			+ " FILTER regex(?class_name, \"";
			
	private String CRM_TO_ALIADA_CLASS_P2 = "\")" + "}";
	
	public String crm2AliadaClass(final String crmClass) {
		final Query query = QueryFactory.create(CRM_TO_ALIADA_CLASS_P1 + crmClass + CRM_TO_ALIADA_CLASS_P2);
		ARQ.getContext().setTrue(ARQ.useSAX);
        
		QueryExecution execution = null;
		try {
			execution = QueryExecutionFactory.sparqlService("http://172.25.5.15:8890/sparql", query);
			execution.setTimeout(2000, 5000);
			final ResultSet results = execution.execSelect();
			//Iterating over the SPARQL Query results
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				//Printing DBpedia entries' abstract.
				System.out.println(soln.get("?abstract"));   
				return soln.get("?abstract").asResource().getURI();
			}
			return "NULL";
        } finally {
        	try {
        		execution.close();
			} catch (Exception exception) {
				// TODO: handle exception
			}
        }

	}
	
	public static void main(String[] args) {
		new AliadaRDFStoreDAO().crm2AliadaClass("E22");
	}
}