<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="html"%>

<head>
    <title>Aliada</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta http-equiv="Content-Script-Type" content="text/javascript; charset=UTF-8"/>
    <link rel="stylesheet" href="css/aliadaStyles.css" type="text/css"/>
    <link rel="shortcut icon" href="images/aliada.ico"/>
    
 	<link type="text/css" rel="stylesheet" href="<html:url value="css/rdfValidation.css" />" />
    
    <!-- DataTables CSS -->
	<link rel="stylesheet" type="text/css" href="css/dataTables.css">
  
	<!-- jQuery -->
	<script type="text/javascript" charset="utf8" src="js/jquery-1.10.2.min.js"></script>
	  
	<!-- DataTables -->
	<script type="text/javascript" charset="utf8" src="js/jquery-dataTables.js"></script>

    <html:head/>
	<script type="text/javascript" charset="UTF-8">
	
		$(document).ready(function() {
		    changeLocaleDataTable();
		} );
		
		function changeLocaleDataTable(){
			var loc = $("#loc").val();
			if (loc == "spa") {
			    $("#datAuthors").dataTable( {
			    	"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "json/Spanish.json"
			        }
			    } );
			    $("#dataObjects").dataTable( {
			    	"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "json/Spanish.json"
			        }
			    } );
			    $("#dataManifs").dataTable( {
			    	"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "json/Spanish.json"
			        }
			    } );
			    $("#dataWorks").dataTable( {
			    	"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "json/Spanish.json"
			        }
			    } );
			} else if (loc == "ita") {
				$("#datAuthors").dataTable( {
					"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "json/Italian.json"
			        }
			    } );
			    $("#dataObjects").dataTable( {
			    	"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "json/Italian.json"
			        }
			    } );
			    $("#dataManifs").dataTable( {
			    	"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "json/Italian.json"
			        }
			    } );
			    $("#dataWorks").dataTable( {
			    	"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "json/Italian.json"
			        }
			    } );
			} else if (loc == "hun") {
				$("#datAuthors").dataTable( {
					"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "json/Hungarian.json"
			        }
			    } );
			    $("#dataObjects").dataTable( {
			    	"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "json/Hungarian.json"
			        }
			    } );
			    $("#dataManifs").dataTable( {
			    	"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "json/Hungarian.json"
			        }
			    } );
			    $("#dataWorks").dataTable( {
			    	"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "json/Hungarian.json"
			        }
			    } );
			} else if (loc == "eng") {
				$("#datAuthors").dataTable( {
					"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "json/English.json"
			        }
			    } );
			    $("#dataObjects").dataTable( {
			    	"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "json/English.json"
			        }
			    } );
			    $("#dataManifs").dataTable( {
			    	"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "json/English.json"
			        }
			    } );
			    $("#dataWorks").dataTable( {
			    	"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "json/English.json"
			        }
			    } );
			}
		}
		
		function authors(){
			$("#aut").submit();
			return false;
		}
		
		function objects(){
			$("#ob").submit();
			return false;
		}
		
		function manifestations(){
			$("#ma").submit();
			return false;
		}
		
		function works(){
			$("#wo").submit();
			return false;
		}
		
		$(function(){
			
			var isAuth = $("#auth").val();	
			var isObj = $("#obj").val();
			var isManifs = $("#mani").val();	
			var isWorks = $("#wor").val();	
			var q = $("#queries").val();
			
			if(isAuth == "true" && q == "true"){
				$("#queriesButt").show();
				$("#query").show();
				$("#authors").show();
			} else if (q == "false") {
				$("#graphs").show();
			}
			
			if(isObj == "true" && q == "true"){
				$("#queriesButt").show();
				$("#query").show();
				$("#objects").show();
			} else if (q == "false") {
				$("#graphs").show();
			}
			
			if(isManifs == "true" && q == "true"){
				$("#queriesButt").show();
				$("#query").show();
				$("#manifestations").show();
			} else if (q == "false") {
				$("#graphs").show();
			}
			
			if(isWorks == "true" && q == "true"){
				$("#queriesButt").show();
				$("#query").show();
				$("#works").show();
			} else if (q == "false") {
				$("#graphs").show();
			}
			
			var c = $("#change").val();
			if(c == "true") {
				$("#changeGraph").show();
			}
			
		});
		
	</script>
	
</head>

<body class="whitebackground">

	<html:hidden id="loc" name="loc" value="%{getLocale().getISO3Language()}" />
	<html:hidden id="auth" name="authors" value="%{auth}" />
	<html:hidden id="obj" name="objects" value="%{obj}" />
	<html:hidden id="mani" name="mani" value="%{mani}" />
	<html:hidden id="wor" name="wor" value="%{works}" />
	<html:hidden id="queries" name="queries" value="%{queries}" />
	<html:hidden id="change" name="change" value="%{change}" />

	<div id="aliadaHeader">
		<img src="images/aliada-header.png"/>
		<html:a id="changeGraph" action="changeGraph" cssClass="displayNo menuButton buttonGreen margin20 fright" ><html:text name="graph.change"/></html:a>	
	</div>	
	<div class="headerContentGreenBorder"></div>
	<div id="queriesButt" class="displayNo contentQueriesButtons center" >
	
		<html:submit cssClass="queriesButton button" key="%{a}" onclick="return authors();"/>
		<html:submit cssClass="queriesButton button" key="%{o}" onclick="return objects();"/>
		<html:submit cssClass="queriesButton button" key="%{m}" onclick="return manifestations();"/>
		<html:submit cssClass="queriesButton button" key="%{wo}" onclick="return works();"/>

	</div>
	<div id="query" class="scrollifyAuto displayNo">
		<div id="authors" class="displayNo">
			<h3 class="center"><html:text name="authors.title"></html:text></h3>
			<table id="datAuthors" class="display compact">
				<html:form id="aut" action="/getAuthors.action">
				<thead>
					<tr>
						<th><html:text name="name"/></th>
						<th><html:text name="uri"/></th>
					</tr>
				</thead>
				
				<tfoot>
					<tr>
						<th><html:text name="name"/></th>
						<th><html:text name="uri"/></th>
					</tr>
				</tfoot>
				
				<tbody>
					<html:iterator value="authors" var="dato">
						<tr>
							<td><html:property value="name" /></td>
							<td><html:a href="%{resourceURI}" target="_blank"><html:property value="resourceURI" /></html:a></td>
						</tr>
					</html:iterator>
				</tbody>
				</html:form>
			</table>
		</div>
		
		<div id="objects" class="displayNo">
			<h3 class="center"><html:text name="objects.title"></html:text></h3>
			<table id="dataObjects" class="display compact">
				<html:form id="ob" action="/getObj.action">
				<thead>
					<tr>
						<th><html:text name="name"/></th>
						<th><html:text name="uri"/></th>
					</tr>
				</thead>
				
				<tfoot>
					<tr>
						<th><html:text name="name"/></th>
						<th><html:text name="uri"/></th>
					</tr>
				</tfoot>
				
				<tbody>
					<html:iterator value="ob" var="dato">
						<tr>
							<td><html:property value="name" /></td>
							<td><html:a href="%{resourceURI}" target="_blank"><html:property value="resourceURI" /></html:a></td>
						</tr>
					</html:iterator>
				</tbody>
				</html:form>
			</table>
		</div>
		
		<div id="manifestations" class="displayNo">
			<h3 class="center"><html:text name="manifs.title"></html:text></h3>
			<table id="dataManifs" class="display compact">
				<html:form id="ma" action="/getManifs.action">
				<thead>
						<tr>
							<th><html:text name="name"/></th>
							<th><html:text name="uri"/></th>
						</tr>
				</thead>
					
				<tfoot>
						<tr>
							<th><html:text name="name"/></th>
							<th><html:text name="uri"/></th>
						</tr>
				</tfoot>
				
				<tbody>
					<html:iterator value="man" var="dato">
						<tr>
							<td><html:property value="name" /></td>
							<td><html:a href="%{resourceURI}" target="_blank"><html:property value="resourceURI" /></html:a></td>
						</tr>
					</html:iterator>
				</tbody>
				</html:form>
			</table>
		</div>
		
		<div id="works" class="displayNo">
			<h3 class="center"><html:text name="works.title"></html:text></h3>
			<table id="dataWorks" class="display compact">
				<html:form id="wo" action="/getWor.action">
				<thead>
					<tr>
						<th><html:text name="work"/></th>
						<th><html:text name="expr"/></th>
						<th><html:text name="manif"/></th>
						<th><html:text name="tit"/></th>
						<th><html:text name="dimen"/></th>
						<th><html:text name="ext"/></th>
						<th><html:text name="author"/></th>
						<th><html:text name="place"/></th>
						<th><html:text name="date"/></th>
						<th><html:text name="edition"/></th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<th><html:text name="work"/></th>
						<th><html:text name="expr"/></th>
						<th><html:text name="manif"/></th>
						<th><html:text name="tit"/></th>
						<th><html:text name="dimen"/></th>
						<th><html:text name="ext"/></th>
						<th><html:text name="author"/></th>
						<th><html:text name="place"/></th>
						<th><html:text name="date"/></th>
						<th><html:text name="edition"/></th>
					</tr>
				</tfoot>
				<tbody>
					<html:iterator value="w" var="dato">
						<tr>
							<td><html:a href="%{workURI}" target="_blank"><html:property value="workURI" /></html:a></td>
							<td><html:a href="%{exprURI}" target="_blank"><html:property value="exprURI" /></html:a></td>
							<td><html:a href="%{manifURI}" target="_blank"><html:property value="manifURI" /></html:a></td>
							<td><html:property value="title" /></td>
							<td><html:property value="dimensions" /></td>
							<td><html:property value="extension" /></td>
							<td><html:property value="author" /></td>
							<td><html:property value="publicPlace" /></td>
							<td><html:property value="publicDate" /></td>
							<td><html:property value="edition" /></td>
						</tr>
					</html:iterator>
				</tbody>
				</html:form>
			</table>
		</div>
	</div>
	<div id="graphs" class="displayNo">
		<html:form id="gQuery">
			<label class="row label center"><html:text name="graph.select"/></label>
			<div class="buttons row">
				<html:select id="graphToQuery" name="selectedGraph" cssClass="inputFormLarge" list="graphs" />
			</div>
			<div class="buttons row">
				 <html:submit id="rdfVal" action="getAuthors" cssClass="menuButton button" key="rdfVal"/> 
			</div>
		</html:form>
	</div>
	<div id="collapseCloud"></div>
	<div class="headerContentGreenBorder"></div>
	<div class="copyrightPage">
		<tiles:insertAttribute name="footer" />
	</div>
</body>
</html>