<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="html" %>
<script>
$(function(){
	var areFiles = $("#areFiles").val();
	var enableErrorLogButton= $("#enableErrorLogButton").val();
	if(enableErrorLogButton==1){
		$("#importFileButton").removeClass("buttonGreen");
		$("#importFileButton").addClass("button");
		$("#errorLog").removeClass("button");
		$("#errorLog").addClass("buttonGreen");
		$("#errorLog").prop( "disabled", false);	
	}
	else{
		$("#errorLog").removeClass("buttonGreen");
		$("#errorLog").addClass("button");
		$("#errorLog").prop( "disabled", true);		
	}
	if(areFiles){
		$("#importedFilesTable").show();	
	}
	/* $("#importedFilesTable :checkbox").on("change",function(){
		if($("#importedFilesTable :checkbox:checked").length>0){
			$("#nextButton").removeClass("button");
			$("#nextButton").addClass("buttonGreen");
			$("#nextButton").prop( "disabled", false);				
		}
		else{
			$("#nextButton").removeClass("buttonGreen");
			$("#nextButton").addClass("button");
			$("#nextButton").prop( "disabled", true);				
		}
	}); */	
	 $("#importedFilesTable :radio").on("change",function(){
			if($("#importedFilesTable :radio:checked")){
				$("#nextButton").removeClass("button");
				$("#nextButton").addClass("buttonGreen");
				$("#nextButton").prop( "disabled", false);				
			}
			else{
				$("#nextButton").removeClass("buttonGreen");
				$("#nextButton").addClass("button");
				$("#nextButton").prop( "disabled", true);				
			}
		});
});
</script>
<html:hidden id="areFiles" name="areFiles" value="%{#session['importedFiles']}" />
<html:hidden id="enableErrorLogButton" name="enableErrorLogButton" value="%{enableErrorLogButton}" />
<ul class="breadcrumb">
	<span class="breadCrumb"><html:text name="home"/></span>
	<li><span class="breadcrumb"><html:text name="organisation.title"/></span></li>
	<li><span class="breadcrumb activeGreen"><html:text name="manage.title"/></span></li>
	<li><span class="breadcrumb"><html:text name="conversion.title"/></span></li>
	<li><span class="breadcrumb"><html:text name="linking.title"/></span></li>
</ul>
<div class="form centered">
	<div id="managingPanel" class="content">
	<html:form id="managing" method="post" enctype="multipart/form-data">
		<label class="label"><html:text name="manage.process"/></label>
		<div class="row">
			<html:select name="selectedProfile" cssClass="inputForm"
				list="profiles" />
			<html:submit action="showProfiles"
				cssClass="submitButton button" key="profilesSubmit" />
		</div>
		<div class="row">
			<html:text name="importFile"/>
			<html:file key="importFile" />
		</div>
		<html:fielderror fieldName="importFile" />
		<html:actionerror/>
		<html:actionmessage />
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
	</html:form>
	</div>
</div>
<html:form>
	<div id="importedFilesTable" class="centered displayNo">
		<label class="label"><html:text name="manage.selectFile"/></label>
		<table class="table">
			<tr class="backgroundGreen center">
				<!-- <th></th> -->
				<th><label class="bold"><html:text name="filename"/></label></th>
				<th><label class="bold"><html:text name="profile"/></label></th>
				<th><label class="bold"><html:text name="status"/></label></th>
			</tr>
			<html:iterator value="importedFiles" var="dato">
				<tr>
					<%-- <td><html:checkbox name="fileChecked" value="fileChecked"/></td> --%>
					<%-- <td><html:property value="filename" /></td> --%>
					<td><html:radio key="selectedFile" list="filename" /></td>
					<td><html:property value="profile" /></td>
					<td><html:if test="status.equalse('idle')">
							<img class="displayNo" src="images/fine.png"/>
							<img src="images/clock.png"/>
						</html:if>
						<html:else>
							<img class="displayNo" src="images/clock.png"/>
							<img src="images/fine.png"/>
						</html:else>
					</td>
				</tr>
			</html:iterator>
		</table>
	</div>	
	<div id="submitButtons" class="buttons row">
			<html:submit action="configure" cssClass="fleft submitButton button" key="back" />	
			<html:submit id="nextButton" action="saveFilesToConversion" disabled="true" cssClass="fright submitButton button"
						key="next" />
	</div>
</html:form>


