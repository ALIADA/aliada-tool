package eu.aliada.rdfizer;

import java.util.Iterator;

import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.reasoner.ValidityReport;

public class Main {
	
	public static void main(String[] args) { 
		Model schema = RDFDataMgr.loadModel("file://home/agazzarini/Desktop/Ontology/aliada-ontology.owl") ;
        RDFDataMgr.read(schema, "file:///home/agazzarini/Desktop/Ontology/efrbroo.owl") ;
        RDFDataMgr.read(schema, "file:///home/agazzarini/Desktop/Ontology/current.owl") ;
        RDFDataMgr.read(schema, "file:///home/agazzarini/Desktop/Ontology/wgs84_pos.owl");
        RDFDataMgr.read(schema, "file:///home/agazzarini/Desktop/Ontology/time.owl");
        RDFDataMgr.read(schema, "file:///home/agazzarini/Desktop/Ontology/skos-xl.owl");
        RDFDataMgr.read(schema, "file:///home/agazzarini/Desktop/Ontology/0.1.owl");

         //Load generated RDF
         Model data = RDFDataMgr.loadModel("file:///home/agazzarini/Desktop/Ontology/invalid.nt", Lang.NT) ;

         //Apply reasoner
         //Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
         Reasoner reasoner = ReasonerRegistry.getOWLMicroReasoner();
         //reasoner.setParameter(ReasonerVocabulary.PROPsetRDFSLevel,
           //           ReasonerVocabulary.RDFS_FULL);
         reasoner = reasoner.bindSchema(schema);
         InfModel infmodel = ModelFactory.createInfModel(reasoner, data);
         ValidityReport validity = infmodel.validate();
         if (validity.isClean()) {
             System.out.println("OK"); 
         } else {
             System.out.println("Conflicts");
             for (Iterator i = validity.getReports(); i.hasNext(); ) {
                 ValidityReport.Report report = (ValidityReport.Report)i.next();
                 
                 System.out.println("Description: " + report.description);
                 System.out.println("Type: " + report.type);
             }
         }      
	}
}
