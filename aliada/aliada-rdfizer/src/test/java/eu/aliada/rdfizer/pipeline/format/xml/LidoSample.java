package eu.aliada.rdfizer.pipeline.format.xml;
import java.io.StringWriter;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.anakia.Escape;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.tools.generic.FieldTool;
import org.w3c.dom.Document;

import eu.aliada.rdfizer.pipeline.format.Function;


public class LidoSample {
	public static void main(String[] args) {
		String s = "http://www.szepmuveszeti.hu/id/resource/E22_Man-Made_Object/szepmuveszeti.hu_object_29";
		
		Document root = null;
		Properties p = new Properties();
	    p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	    p.setProperty("resource.loader", "class");
	    p.setProperty("velocimacro.library", "VM_global_library.vm");
	    p.setProperty("directive.set.null.allowed", "true");
	    Velocity.init(p);

		VelocityContext context = new VelocityContext();
		context.put("tls", s);
		context.put("xpath", new XPath());
		context.put("function", new Function());
		context.put ("escape", new Escape());
		context.put("configuration", new Configuration());
		StringWriter w = new StringWriter();
		
		try
		{
			long begin = System.currentTimeMillis();
		    root = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( LidoSample.class.getResourceAsStream("/lidoMFAB.xml") );
		   
		    System.out.println("DOM : " + (System.currentTimeMillis() - begin));
		    
		    context.put ("root", root.getDocumentElement());		    
		    Template template = Velocity.getTemplate("lido.n3.vm");
		    begin = System.currentTimeMillis();
		    template.merge(context, w);
		    System.out.println("VELOCITY : " + (System.currentTimeMillis() - begin));
		    begin = System.currentTimeMillis();
		    System.out.println(w);
		}
		catch( Exception e)
		{
		    e.printStackTrace();
		}
	}
}
