package eu.aliada.rdfizer.pipeline.format.xml;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.anakia.Escape;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.tools.generic.FieldTool;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import eu.aliada.rdfizer.pipeline.format.Function;
import eu.aliada.rdfizer.pipeline.format.templating.AliadaResourceManager;


public class LidoSample {
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		String s = "<http://www.szepmuveszeti.hu/id/resource/E22_Man-Made_Object/szepmuveszeti.hu_object_29>";
		
		Document root = null;
		Properties p = new Properties();
	    p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	    p.setProperty("resource.loader", "class");
	    p.setProperty("velocimacro.library", "VM_global_library.vm");
	    p.setProperty("directive.set.null.allowed", "true");
	    p.setProperty("resource.manager.class", AliadaResourceManager.class.getName());
	    Velocity.init(p);

		VelocityContext context = new VelocityContext();
		context.put("mainSubject", s);
		context.put("xpath", new XPath());
		context.put("function", new Function());
		context.put("configuration", new Configuration());
		Writer sw = new StringWriter();
		Writer w = new BufferedWriter(sw);
	    
		Template template = Velocity.getTemplate("lido.n3.vm");	   
		
		long begin = System.currentTimeMillis();
	    root = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( LidoSample.class.getResourceAsStream("/lidoMFAB.xml") );
	    int howMany = 1;
	    for (int i = 0; i < howMany; i++)
		{
			try
			{
			   
//			    System.out.println("DOM : " + (System.currentTimeMillis() - begin));
			    
			    		    
			    context.put ("root", root.getDocumentElement());
				template.merge(context, sw);
			    
		    System.out.println(sw);
			}
			catch( Exception e)
			{
			    e.printStackTrace();
			}
		}
//		System.out.println(((System.currentTimeMillis() - begin) / howMany));
	}
}
