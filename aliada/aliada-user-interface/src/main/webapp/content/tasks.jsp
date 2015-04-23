<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>
<script>
$(function(){
	var arePendingFiles = $("#arePendingFiles").val();
	if (arePendingFiles != "true"){
		$("#taskTable").hide();
		$("#emptyTable").show();
	}
	$("#pendingFiles :radio").on("change",function(){
		if($("#pendingFiles :radio:checked")){
			$("#nextButton").prop( "disabled", false);
			$("#deleteButton").prop( "disabled", false);
		}
		else{
			$("#nextButton").prop( "disabled", true);
			$("#deleteButton").prop( "disabled", false);
		}
	}); 
});
</script>
<html:hidden id="arePendingFiles" name="arePendingFiles" value="%{arePendingFiles}" />

<ul class="breadcrumb">
	<span class="breadCrumb"><html:text name="home"/></span>
	<li><span class="breadcrumb activeGreen"><html:text name="controlPanel"/></span></li>
</ul>
<html:form id="pendingFiles">
	<div id="taskTable">
		<table class="table">
			<tr class="backgroundGreen center">
				<th><label class="bold"><html:text name="filename"/></label></th>
				<th><label class="bold"><html:text name="profile"/></label></th>
				<th><label class="bold"><html:text name="status"/></label></th>
			</tr>
			<html:iterator value="pendingFiles" var="dato">
				<tr>
					<td><html:radio name="selectedPendingFile" list="filename"/></td>
					<td><html:property value="profile" /></td>
					<td><html:property value="status" /></td>
				</tr>
			</html:iterator>
		</table>
	</div>
	<div id="emptyTable" class="displayNo">
		<table class="table">
			<tr class="backgroundGreen center">
				<th><label class="bold"><html:text name="filename"/></label></th>
				<th><label class="bold"><html:text name="profile"/></label></th>
				<th><label class="bold"><html:text name="status"/></label></th>
			</tr>
			<tr>
				<th><label class="bold"><html:text name="empty"/></label></th>
				<th><label class="bold"><html:text name="empty"/></label></th>
				<th><label class="bold"><html:text name="empty"/></label></th>
			</tr>
		</table>
	</div>
	<html:actionmessage />
	<html:actionerror/>

	<div id=pendingFilesButtons class="display buttons row">
		<html:submit  id="backButton" action="manage" cssClass="fleft submitButton button"
			key="back" />
		<html:submit id="nextButton" action="setRecoverdFile" disabled="true" cssClass="fright submitButton button"
			key="next" />
		<html:submit id="deleteButton" action="deleteFile" disabled="true" cssClass="fright submitButton button"
			key="delete" />
	</div>
</html:form>







