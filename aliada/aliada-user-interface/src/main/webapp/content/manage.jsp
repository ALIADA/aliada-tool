<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="html" %>
<script>
	if(enableErrorLogButton){
		$("#importFileButton").removeClass("buttonGreen");
		$("#importFileButton").addClass("button");
		$("#errorLog").removeClass("button");
		$("#errorLog").addClass("buttonGreen");
		$("#errorLog").prop( "disabled", false);	
	}
	if(importedFiles!=null){
		$("#importedFilesTable").show();	
	}
	$(function(){
		$("#importedFilesTable :checkbox").on("change",function(){
			if($("#importedFilesTable :checkbox:checked").length>0){
				$("#nextButton").removeClass("button");
				$("#nextButton").addClass("buttonGreen");
				$("#nextButton").prop( "disabled", false);				
			}
			});
	});
</script>
<html:hidden id="importedFiles" name="importedFiles" value="%{#session['importedFiles']}" />
<html:hidden id="enableErrorLogButton" name="enableErrorLogButton" value="enableErrorLogButton" />
<ul class="breadcrumb">
	<span class="breadCrumb"><html:text name="home"/></span>
	<li><span class="breadcrumb"><html:text name="organisation.title"/></span></li>
	<li><span class="breadcrumb activeGreen"><html:text name="manage.title"/></span></li>
	<li><span class="breadcrumb"><html:text name="conversion.title"/></span></li>
	<li><span class="breadcrumb"><html:text name="linking.title"/></span></li>
</ul>
<div class="form centered">
	<html:form id="managing" method="post" enctype="multipart/form-data">
	<div id="managingPanel" class="content">
		<label class="row label"><html:text name="manage.process"/></label>
		<div class="row">
			<html:select name="selectedProfile" cssClass="inputForm"
				list="profiles" />
			<html:submit action="showProfiles"
				cssClass="submitButton button" key="profilesSubmit" />
		</div>
		<html:text name="importFile"/>
		<html:file key="importFile" />
		<html:fielderror fieldName="importFile" />
		<html:actionerror/>
		<html:actionmessage />
		<table id="importedFilesTable" class="table displayNo">
			<tr class="backgroundGreen center">
				<th></th>
				<th><label class="bold"><html:text name="filename"/></label></th>
				<th><label class="bold"><html:text name="profile"/></label></th>
			</tr>
			<html:iterator value="%{#session['importedFiles']}" var="dato">
				<tr>
					<td><html:checkbox name="fileChecked" value="fileChecked"/></td>
					<td><html:property value="filename" /></td>
					<td><html:property value="profile" /></td>
				</tr>
			</html:iterator>
		</table>
		<div id="managingButtons" class="buttons row">
			<html:form id="managingButtonsForm">
				<img id=loader class="displayNo leftMargin rMargin20" src="images/loader.gif" alt="" />
				<html:submit id="importFileButton" action="importXML" cssClass="submitButton buttonGreen"
					key="import" onClick="$('#loader').show();
											$('#importFileButton').hide();" />
				<html:submit id="errorLog" action="errorLog" disabled="true" cssClass="submitButton button"
					key="errorLog" />
			</html:form>
		</div>
	</div>	
	</html:form>
</div>
<div id="submitButtons" class="buttons row">
	<html:form id="submitButtonsForm">
		<html:submit action="configure" cssClass="fleft submitButton button" key="back" />	
		<html:submit id="nextButton" action="conversion" disabled="true" cssClass="fright submitButton button"
					key="next" />
	</html:form>
</div>



