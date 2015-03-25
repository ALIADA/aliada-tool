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
		$(function(){
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
	<div id="aliadaHeader">
		<img src="images/aliada-header.png"/>	
	</div>	
	<div class="headerContentGreenBorder"></div>
	<div id="linksTable" class="content" >
		<h2 class="pageTitle"><html:text name="linksVal.title"/></h2>
	<html:form id="discovLinks">
	<table class="table">
		<tr class="backgroundGreen center">
			<th><label class="bold"><html:text name="Remove link"/></label></th>
			<th><label class="bold"><html:text name="aliada_dataset"/></label></th>
			<th><label class="bold"><html:text name="external_dataset"/></label></th>
		</tr>
		<html:iterator value="discovLinks" var="dato">
			<tr>
				<td><html:checkbox id="%{value}" fieldValue="%{value}" name="val" value="false"/></td>
				<td><html:a href="%{subject}" target="_blank"><html:property value="subject" /></html:a></td>
				<td><html:a href="%{object}" target="_blank"><html:property value="object" /></html:a></td>
			</tr>
		</html:iterator>
	</table>
	<html:actionmessage />
	<html:actionerror/>
	<div id="submitButtons" class="buttons row">
		<html:submit id="removeButton" action="removeLinks" cssClass="fright submitButton button"
			key="delete" disabled="true"/>
	</div>
	</html:form>
	</div>
</body>
</html>