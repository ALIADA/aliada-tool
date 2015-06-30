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
    
    <link type="text/css" rel="stylesheet" href="<html:url value="css/linksValidation.css" />" />
    
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
			//console.log(loc);
			if (loc == "spa") {
			    $("#links").dataTable( {
			    	"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
			        }
			    } );
			    $("#linksAmbiguous").dataTable( {
			    	"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
			        }
			    } );
			} else if (loc == "ita") {
				$("#links").dataTable( {
					"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Italian.json"
			        }
			    } );
			    $("#linksAmbiguous").dataTable( {
			    	"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Italian.json"
			        }
			    } );
			} else if (loc == "hun") {
				$("#links").dataTable( {
					"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Hungarian.json"
			        }
			    } );
			    $("#linksAmbiguous").dataTable( {
			    	"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Hungarian.json"
			        }
			    } );
			} else if (loc == "eng") {
				$("#links").dataTable( {
					"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/English.json"
			        }
			    } );
			    $("#linksAmbiguous").dataTable( {
			    	"iDisplayLength": 100,
			    	"aLengthMenu": [[100, 250, 500], [100, 250, 500]],
			        "language": {
			            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/English.json"
			        }
			    } );
			}
		}
		
		function ambiguous(){
			$("#discovLinksAmbiguous").submit();
			return false;
		}
		
		function allLinks(){
			$("#discovLinks").submit();
			return false;
		}
		
		$(function(){
			
			var isLinks = $("#li").val();	
			var isAmb = $("#amb").val();
			
			console.log(isLinks);
			
			console.log(isAmb);
			
			if(isLinks == "true"){
				$("#linksPage").show();
				$("#linksTable").show();
			}
			
			if(isAmb == "true"){
				$("#linksPage").show();
				$("#linksAmbiguousTable").show();
			}
			
			$("#linksAmbiguousTable :checkbox").on("change",function(){
				if($("#linksAmbiguousTable :checkbox:checked:checked").length == 0){
					$("#removeButtonAmb").prop( "disabled", true);				
				} else {
					$("#removeButtonAmb").prop( "disabled", false);
				}
			});
			
			$("#linksTable :checkbox").on("change",function(){
				if($("#linksTable :checkbox:checked:checked").length == 0){
					$("#removeButton").prop( "disabled", true);				
				} else {
					$("#removeButton").prop( "disabled", false);
				}
			});
			
		});
	</script>
	
</head>

<body class="whitebackground">

	<html:hidden id="loc" name="loc" value="%{getLocale().getISO3Language()}" />
	<html:hidden id="li" name="li" value="%{li}" />
	<html:hidden id="amb" name="amb" value="%{amb}" />

	<div id="aliadaHeader">
		<img src="images/aliada-header.png"/>	
	</div>	
	<div class="headerContentGreenBorder"></div>
	
	<div class="contentLinksButtons center" >
	
		<html:submit cssClass="linksButton button" key="%{a}" onclick="return ambiguous();"/>
		<html:submit cssClass="linksButton button" key="%{l}" onclick="return allLinks();"/>

	</div>
	
	<div id="linksPage" class="scrollifyAuto displayNo">
	
		<div id="linksAmbiguousTable" class="displayNo" >
			<h3 class="center"><html:text name="ambiguous.title"></html:text></h3>
			<html:form id="discovLinksAmbiguous" action="/linksAmbVal.action">
				<table id="linksAmbiguous" class="display compact">
					<thead>
						<tr>
							<th></th>
							<th><html:text name="aliada_dataset"/></th>
							<th><html:text name="external_dataset"/></th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<th></th>
							<th><html:text name="aliada_dataset"/></th>
							<th><html:text name="external_dataset"/></th>
						</tr>
					</tfoot>
					<tbody>
						<html:iterator value="discovLinksAmbiguous" var="dato">
							<tr>
								<td><html:checkbox id="%{value}" fieldValue="%{value}" name="val" value="false"/></td>
								<td><html:a href="%{subject}" target="_blank"><html:property value="subject" /></html:a></td>
								<td><html:a href="%{object}" target="_blank"><html:property value="object" /></html:a></td>
							</tr>
						</html:iterator>
					</tbody>
				</table>
			<html:actionmessage />
			<html:actionerror/>
			<div id="submitButtons" class="buttons row">
				<html:submit id="removeButtonAmb" action="removeLinksAmbiguous" cssClass="fright submitButton button" key="delete" disabled="true"/>
			</div>
			</html:form>
		</div>
		
		<div id="linksTable" class="displayNo" >
			<h3 class="center"><html:text name="links.title"></html:text></h3>
			<html:form id="discovLinks" action="/linksVal.action">
				<table id="links" class="display compact">
					<thead>
						<tr>
							<th></th>
							<th><html:text name="aliada_dataset"/></th>
							<th><html:text name="external_dataset"/></th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<th></th>
							<th><html:text name="aliada_dataset"/></th>
							<th><html:text name="external_dataset"/></th>
						</tr>
					</tfoot>
					<tbody>
						<html:iterator value="discovLinks" var="dato">
							<tr>
								<td><html:checkbox id="%{value}" fieldValue="%{value}" name="val" value="false"/></td>
								<td><html:a href="%{subject}" target="_blank"><html:property value="subject" /></html:a></td>
								<td><html:a href="%{object}" target="_blank"><html:property value="object" /></html:a></td>
							</tr>
						</html:iterator>
					</tbody>
				</table>
			<html:actionmessage />
			<html:actionerror/>
			<div id="submitButtons" class="buttons row">
				<html:submit id="removeButton" action="removeLinks" cssClass="fright submitButton button" key="delete" disabled="true"/>
			</div>
			</html:form>
		</div>
	</div>
</body>
</html>