<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="com.hp.hpl.jena.graph.Triple"%>
<%@ page language="java" import="eu.aliada.shared.rdfstore.RDFStoreDAO"%>

<%
RDFStoreDAO rdfStoreDAO = new RDFStoreDAO();
//The following values are extracted from table `aliada`.`linksdiscovery_job_instances` and 
// the fields: `output_uri`, `output_login`, `output_password`, `output_graph`
String sparqlEndpointURI = "http://aliada.scanbit.net:8891/sparql-auth"; //the SPARQL endpoint URI.  
String user = "aliada_dev"; //the user name for the SPARQl endpoint.
String password	= "aliada_dev"; //the password for the SPARQl endpoint.
String graphName = "http://links.lido.mfab"; //the URI of the graph.
if (request.getMethod() == "POST") {
	String[] links = request.getParameterValues("links");
	String linksStr = "";
	for (int i= 0; i < links.length; i++) {
		linksStr = linksStr + links[i] + " . ";
	}
	rdfStoreDAO.executeDelete(sparqlEndpointURI, graphName, user, password, linksStr);
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aliada</title>
</head>
<body>
<form id="removeTriples" name="removeTriples" action="./linksValidation.jsp" method="POST">
Check the links to remove: 
<table border=1">
	<tbody>
		<tr>
			<th>Remove link</th>
			<th>aliada_dataset</th>
			<th>external_dataset</th>
		</tr>
<%
Triple [] discoveredLinks = rdfStoreDAO.getDiscoveredLinks(sparqlEndpointURI, graphName, user, password);
for (int i=0; i<discoveredLinks.length;i++) {
	String tripleStr = "<" + discoveredLinks[i].getSubject().getURI() + "> ";
	tripleStr = tripleStr + "<" + discoveredLinks[i].getPredicate().getURI() + "> ";
	tripleStr = tripleStr + "<" + discoveredLinks[i].getObject().getURI() + ">";
%>

		<tr>
			<td><input type="checkbox" name="links" id="links" value="<%=tripleStr%>" class="left"/></td>
			<td><%=discoveredLinks[i].getSubject().getURI()%></td>
			<td><%=discoveredLinks[i].getObject().getURI()%></td>
		</tr>

<%
}
%>
	</tbody>
</table>
<a href="#" onclick="removeTriples.submit();">Remove selected triples</a>
</form>
</body>
</html>