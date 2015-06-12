<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>

<link type="text/css" rel="stylesheet" href="<html:url value="css/externalDatasets.css" />" />

<script type="text/javascript" charset="UTF-8">	
	$(function(){
		$("#extdatasetsPanel :checkbox").on("change",function(){
			$("#removeButton").prop( "disabled", false);
		});
	});
</script>

<div id="extDatasetsPage">

	<ul class="breadcrumb">
		<span class="breadCrumb"><html:text name="home"/></span>
		<li><span class="breadcrumb activeGreen"><html:text name="confExternalDatasets"/></span></li>
	</ul>
	
	<div class="extDatasetsPage">
	<html:form id="extDatasets">
		<div id="extdatasetsPanel">
		<html:text name="description"/><br/>
			<div class="fieldsNoBorder lMargin40p">
				<html:iterator value="externalDatasets">
					<html:checkbox id="%{value}" fieldValue="%{value}" name="val" value="%{selectedExternalDatasets.get(key)}"/><html:property value="value"/><br/>
				</html:iterator>
			</div>
			
			<html:actionmessage/>
			<html:actionerror/>
			
			<div id="areExternalDatasetsButtons" class="buttons row">
				<html:a action="manage" cssClass="fleft"><img alt="help" src="images/back.png"></img></html:a>
				<html:submit id="removeButton" action="reloadExternalDatasets" cssClass="submitButton button"
					key="reload" disabled="true"/>
			</div>
		</div>
	</html:form>
	</div>
	
</div>