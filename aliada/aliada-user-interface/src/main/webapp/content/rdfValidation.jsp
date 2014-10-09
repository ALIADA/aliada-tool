<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="html"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
    <title>Aliada</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta http-equiv="Content-Script-Type" content="text/javascript; charset=UTF-8"/>
    <link rel="stylesheet" href="css/aliadaStyles.css" type="text/css"/>
    <link rel="shortcut icon" href="images/aliada.ico"/>
    <sj:head/>	
    <html:head/>
    
    <script type="text/javascript" charset="UTF-8">
	    var getResults = function(query){
			var form = $("#formGetResults");
			console.log(form);
			form.get(0).query.value = query;
		    var postData = form.serializeArray();
		    console.log(postData);
		    var formURL = form.attr("action");
		    console.log(formURL);
		      $.ajax(
		    {
		        url : formURL,
		        type: "GET",
		        data : postData,
				dataType: 'html', 
			    success:function(data, textStatus, jqXHR) 
		        {
					$("#results").empty();
					$("#results").append(data);
		        },
		        error: function(jqXHR, textStatus, errorThrown) 
		        {
		            console.log("Error");    
		        },
		        comepleted: function(jqXHR, textStatus, errorThrown) 
		        {
		            console.log("Ajax called");   
		        }
		    });  
		}
		$(function(){
			var queryAuthors = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX ecrm:   <http://erlangen-crm.org/current/> PREFIX efrbroo: <http://erlangen-crm.org/efrbroo/> select ?actor ?name where { {?actor rdf:type ecrm:E39_Actor} UNION {?actor rdf:type ecrm:E21_Person} UNION {?actor rdf:type efrbroo:F10_Person} . ?actor ecrm:P131_is_identified_by ?apel. ?apel ecrm:P3_has_note ?name }";
			var queryObjects = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX ecrm:   <http://erlangen-crm.org/current/> PREFIX efrbroo: <http://erlangen-crm.org/efrbroo/> select ?object ?name where { ?object rdf:type ecrm:E22_Man-Made_Object . ?object ecrm:P1_is_identified_by ?apel. ?apel ecrm:P3_has_note ?name }";
			var queryManifestations = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX ecrm:   <http://erlangen-crm.org/current/> PREFIX efrbroo: <http://erlangen-crm.org/efrbroo/> select ?manif ?name where { ?manif rdf:type efrbroo:F3_Manifestation_Product_Type . ?manif ecrm:P102_has_title ?apel. ?apel ecrm:P3_has_note ?name }";
			var queryWorks = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX ecrm:   <http://erlangen-crm.org/current/> PREFIX efrbroo: <http://erlangen-crm.org/efrbroo/> select ?work ?expr ?manif ?title ?dimensions ?extension ?author ?place_publication ?date_publication ?edition where { ?work rdf:type efrbroo:F1_Work . ?work efrbroo:R40_has_representative_expression ?expr . ?expr efrbroo:R4_carriers_provided_by ?manif . ?manif ecrm:P102_has_title ?apel . ?apel ecrm:P3_has_note ?title . OPTIONAL { ?manif ecrm:CLP43_should_have_dimension ?dim . ?dim ecrm:P2_has_type <http://aliada-project.eu/2014/aliada-ontology/id/resource/Concept/MARC/6> . ?dim ecrm:P3_has_note ?dimensions . } OPTIONAL { ?manif ecrm:CLP43_should_have_dimension ?ext . ?ext ecrm:P2_has_type <http://aliada-project.eu/2014/aliada-ontology/id/resource/Concept/MARC/5> . ?ext ecrm:P3_has_note ?extension . } OPTIONAL { ?expr ecrm:P148_has_component ?lingobj1. ?lingobj1 ecrm:P2_has_type <http://aliada-project.eu/2014/aliada-ontology/id/resource/Concept/MARC/1> . ?lingobj1 ecrm:P3_has_note ?author . } ?publexpr efrbroo:R14_incorporates ?expr . ?publexpr ecrm:P106_is_composed_of ?lingobj . OPTIONAL { ?lingobj ecrm:P2_has_type <http://aliada-project.eu/2014/aliada-ontology/id/resource/Concept/MARC/3> . ?lingobj ecrm:P3_has_note ?place_publication . } OPTIONAL { ?lingobj ecrm:P2_has_type <http://aliada-project.eu/2014/aliada-ontology/id/resource/Concept/MARC/4> . ?lingobj ecrm:P3_has_note ?date_publication . } OPTIONAL { ?lingobj ecrm:P2_has_type <http://aliada-project.eu/2014/aliada-ontology/id/resource/Concept/MARC/2> . ?lingobj ecrm:P3_has_note ?edition . } }";
			var querySameAs = "PREFIX owl: <http://www.w3.org/2002/07/owl#> select ?aliada_dataset ?external_dataset where { ?aliada_dataset owl:sameAs ?external_dataset }";
			
			$("#queryAuthors").on("click",function(e){
				getResults(queryAuthors);
			});	
			$("#queryObjects").on("click",function(e){
				getResults(queryObjects);
			});
			$("#queryManifestations").on("click",function(e){
				getResults(queryManifestations);
			});
			$("#queryWorks").on("click",function(e){
				getResults(queryWorks);
			});				
			$("#querySameAs").on("click",function(e){
				getResults(querySameAs);
			});	
		});
	</script>
</head>

<body class="whitebackground">
	<div id="aliadaHeader">
		<img src="images/aliada-header.png"/>	
	</div>	
	<div class="headerContentGreenBorder"></div>
	<div class="content" >
		<h2 class="pageTitle"><html:text name="rdfVal.title"/></h2>
		
		<ul>
		<li><a id="queryAuthors" href="#"><html:text name="rdfVal.authors"/></a></li>
		<li><a id="queryObjects" href="#"><html:text name="rdfVal.objects"/></a></li>
		<li><a id="queryManifestations" href="#"><html:text name="rdfVal.manifestations"/></a></li>
		<li><a id="queryWorks" href="#"><html:text name="rdfVal.all"/></a></li>
		<li><a id="querySameAs" href="#"><html:text name="rdfVal.links"/></a></li>
		</ul>
		<form id="formGetResults" name="formGetResults" action=<html:property value="sparql_endpoint"/> method="get" >
				<input type="hidden" name="default-graph-uri" id="default-graph-uri" value="<html:property value="graph_uri"/>"/>
				<input type="hidden" name="query" id="query" value=""/>
				<input type="hidden" name="format" id="format" value="text/html">
				<input type="hidden" name="timeout" id="timeout" value="0" /> 
				<input type="hidden" name="debug" id="debug" value="1"/>
		</form>	
	</div>
	<div id="results" class="content scrollify">
	</div>
	<div id="collapseCloud"></div>
	<div class="headerContentGreenBorder"></div>
	<div class="copyrightPage">
		<tiles:insertAttribute name="footer" />
	</div>
</body>
</html>
