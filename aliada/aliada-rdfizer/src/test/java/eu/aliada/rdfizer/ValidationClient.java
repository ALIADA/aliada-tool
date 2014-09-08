package eu.aliada.rdfizer;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class ValidationClient {
	public static void main(String[] args) throws FileNotFoundException {
		Model model = ModelFactory.createDefaultModel();
		model.read(new FileReader("???"), "http://example,org", "N3");
		
		System.out.println(model.size());
	}
}
