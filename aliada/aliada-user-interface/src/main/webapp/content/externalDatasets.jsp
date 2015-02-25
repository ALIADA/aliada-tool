<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>
<script type="text/javascript" charset="UTF-8">	
	$(function(){
		$("#extdatasetsPanel :checkbox").on("change",function(){
			if($("#extdatasetsPanel :checkbox:checked:checked").length == 0){
				$("#removeButton").prop( "disabled", true);				
			} else {
				$("#removeButton").prop( "disabled", false);
			}
		});
	});
</script>

<ul class="breadcrumb">
	<span class="breadCrumb"><html:text name="home"/></span>
	<li><span class="breadcrumb activeGreen"><html:text name="confExternalDatasets"/></span></li>
</ul>

<div class="content">
<html:form id="extDatasets">
	<div id="extdatasetsPanel" class="display buttons row">
	<html:text name="description"/><br/>
		<div class="fieldsNoBorder">
			<html:iterator value="externalDatasets">
				<html:checkbox id="%{value}" fieldValue="%{value}" name="val" value="false"/><html:property value="value"/><br/>
			</html:iterator>
		</div>
		<html:actionmessage />
		<html:actionerror/>
		<div id="areExternalDatasetsButtons" class="displayInline">
			<html:submit id="removeButton" action="reloadExternalDatasets" cssClass="submitButton button"
				key="reload" disabled="true"/>
		</div>
		<html:submit action="configure" cssClass="submitButton button fright"
			key="back" />
	</div>
</html:form>
</div>