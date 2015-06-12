<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>

<link type="text/css" rel="stylesheet" href="<html:url value="css/tasks.css" />" />

<script>

function confirmBox(){
	var answer = window.confirm("<html:text name='delete.message'/>");
	if (answer == true) {
		console.log("Remove jobs");
		$("#pendingFiles").submit();
	} else {
		console.log("Remove jobs cancel");
	}
return false;
}

$(function(){
	var arePendingFiles = $("#arePendingFiles").val();
	if (arePendingFiles != "true"){
		$("#taskTable").hide();
		$("#emptyTable").show();
		$("#deleteAllButton").prop( "disabled", true);
	}
	$("#pendingFiles :radio").on("change",function(){
		if($("#pendingFiles :radio:checked")){
			$("#nextButton").prop( "disabled", false);
			$("#deleteButton").prop( "disabled", false);
		}
		else{
			$("#nextButton").prop( "disabled", true);
			$("#deleteButton").prop( "disabled", true);
		}
	}); 
});
</script>
<html:hidden id="arePendingFiles" name="arePendingFiles" value="%{arePendingFiles}" />

<ul class="breadcrumb">
	<span><html:text name="home"/></span>
	<li><span class="activeGreen"><html:text name="controlPanel"/></span></li>
</ul>

<html:form id="pendingFiles" action="/deleteAllFiles.action">

	<div id="emptyTable" class="displayNo">
		<table class="table">
			<tr class="backgroundGreen center">
				<th><label class="bold"><html:text name="filename"/></label></th>
				<th><label class="bold"><html:text name="profile"/></label></th>
				<th><label class="bold"><html:text name="status"/></label></th>
			</tr>
			<tr>
				<td><label class="bold"><html:text name="empty"/></label></td>
				<td><label class="bold"><html:text name="empty"/></label></td>
				<td><label class="bold"><html:text name="empty"/></label></td>
			</tr>
		</table>
	</div>

	<div id="taskTable">
		<table class="table">
			<tr class="backgroundGreen center">
				<th><label class="bold"><html:text name="filename"/></label></th>
				<th><label class="bold"><html:text name="profile"/></label></th>
				<th><label class="bold"><html:text name="status"/></label></th>
			</tr>
			<html:iterator value="pendingFiles" var="dato">
				<tr>
					<td class="radio"><html:radio name="selectedPendingFile" list="filename"/></td>
					<td><html:property value="profile" /></td>
					<td><html:property value="status" /></td>
				</tr>
			</html:iterator>
		</table>
	</div>
	
	<html:actionmessage />
	<html:actionerror/>
	
	<div id=pendingFilesButtons class="display buttons row pBottom">
		<html:a action="manage" cssClass="fleft"><img alt="help" src="images/back.png"></img></html:a>
		<html:submit id="nextButton" action="setRecoverdFile" disabled="true" cssClass="fright mediumButton button"
			key="next" />
		<html:submit id="deleteButton" action="deleteFile" disabled="true" cssClass="fright mediumButton button"
			key="delete" />
		<html:submit id="deleteAllButton" cssClass="fright mediumButton button" key="deleteAll" onclick="return confirmBox();" />
	</div>
	
</html:form>






