<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>
<script type="text/javascript" charset="UTF-8">	
	$(function(){
		$("#extdatasetsPanel :checkbox").on("change",function(){
			$("#removeButton").prop( "disabled", false);
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
				<html:checkbox id="%{value}" fieldValue="%{value}" name="val" value="%{selectedExternalDatasets.get(key)}"/><html:property value="value"/><br/>
			</html:iterator>
		</div>
		
		<html:if test="tab==5">
		      <html:actionmessage/>
		</html:if>
		<html:if test="tab==5">
		        <html:actionerror/>
		</html:if>
		
		<div id="areExternalDatasetsButtons" class="displayInline">
			<html:submit id="removeButton" action="reloadExternalDatasets" cssClass="submitButton button"
				key="reload" disabled="true"/>
		</div>
	</div>
</html:form>
</div>