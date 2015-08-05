package eu.aliada.rdfizer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.MarcStreamWriter;
import org.marc4j.MarcWriter;
import org.marc4j.MarcXmlWriter;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		MarcReader reader = new MarcStreamReader(new FileInputStream("/work/tmp/unina.mrc"));
		MarcWriter writer = new MarcXmlWriter(new FileOutputStream("/work/tmp/data.xml"));
		while (reader.hasNext()) {
			try {
				Record r = reader.next();
				DataField f = (DataField) r.getVariableField("700");
				if(f.getSubfield('a').getData().contains("Abbott")) {
					writer.write(r);
					break;
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		writer.close();
	}
}
