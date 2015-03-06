package eu.aliada.rdfizer.pipeline.format.xml.validation;

import info.freelibrary.util.BufferedFileReader;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class TriplesValidationTest {

	
	
	public static void main(String[] args) throws IOException {
		 Model model = ModelFactory.createDefaultModel();
		 BufferedFileReader reader = new BufferedFileReader(new File("/home/emiliano/Desktop/triples2.txt"));
		 while(reader.ready()){
			 String tripla = reader.readLine();
			 System.out.println(tripla);
			 model.read(new StringReader(tripla), "http://example.org", "N-TRIPLES");
		 }
		 reader.close();
	}
}
