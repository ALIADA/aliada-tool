<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>

<link type="text/css" rel="stylesheet" href="<html:url value="css/templates.css" />" />

<script>

function confirmBox(){
	var answer = window.confirm("<html:text name='delete.message'/>");
	if (answer == true) {
		console.log("Remove template");
		$("#templates").submit();
	} else {
		console.log("Remove template cancel");
	}
return false;
}

function confirmEditBox(){
	var answer = window.confirm("<html:text name='edit.message'/>");
	if (answer == true) {
		console.log("Edit template");
		$("#templateEditForm").submit();
	} else {
		console.log("Edit template cancel");
	}
return false;
}

$(function(){
	var areTemplates = $("#areTemplates").val();	
	var showTheTemplate = $("#showTheTemplate").val();
	var showAddTemplateForm = $("#showAddTemplateForm").val();	
	var showEditTemplateForm = $("#showEditTemplateForm").val();	
	if(areTemplates != "true"){
		$("#areTemplatesButtons").hide();
	}
	if(showAddTemplateForm == "true"){
		$("#addTemplatePanel").show("slow");
	}
	if(showEditTemplateForm == "true"){
		$("#editTemplatePanel").show("slow");
	}
	if(showTheTemplate == "true"){
		$("#theTemplatePanel").show("slow");
	}
	if((showAddTemplateForm == "true") || (showEditTemplateForm == "true") || (showTheTemplate == "true")){
		$("#templatesPanel").hide();		
	}
	
	$("#templates :radio").on("change",function(){
		if($("#templates :radio:checked")){
			$("#seeButton").prop( "disabled", false);
			$("#editButton").prop( "disabled", false);
			$("#deleteButton").prop( "disabled", false);
		}
		else{
			$("#seeButton").prop( "disabled", true);
			$("#editButton").prop( "disabled", true);
			$("#deleteButton").prop( "disabled", true);
		}
	}); 
	
});
</script>
<html:hidden id="areTemplates" name="areTemplates" value="%{areTemplates}" />
<html:hidden id="showTheTemplate" name="showTheTemplate" value="%{showTheTemplate}" />
<html:hidden id="showAddTemplateForm" name="showAddTemplateForm" value="%{showAddTemplateForm}" />
<html:hidden id="showEditTemplateForm" name="showEditTemplateForm" value="%{showEditTemplateForm}" />

<div id="templatesPage">

	<ul class="breadcrumb">
		<span class="breadCrumb"><html:text name="home"/></span>
		<li><span class="breadcrumb activeGreen"><html:text name="templates"/></span></li>
	</ul>
	
	<div class="templatesPage">
		
		<div id="templatesPanel" class="display row">
			<html:form id="templates" action="/deleteTemplate.action">
				<div class="lMargin45p">
					<html:iterator value="templates">
						<html:radio key="selectedTemplate" cssClass="bold lPad10" value="{key}"
							list="{value}" />
						<br>
					</html:iterator>
				</div>
				
				<html:actionmessage/>
				<html:actionerror/>
				
				<div id="areTemplatesButtons" class="buttons">
					<html:a action="manage" cssClass="fleft"><img alt="help" src="images/back.png"></img></html:a>
					<html:submit action="showAddTemplateForm"
							cssClass="submitButton button" key="add" />
					<html:submit id="seeButton" disabled="true" action="showTheTemplate" cssClass="submitButton button"
							key="see" />	
					<html:submit id="editButton" disabled="true" action="showEditTemplateForm" cssClass="submitButton button"
							key="edit" />
					<html:submit id="deleteButton" disabled="true" cssClass="submitButton button"
							key="delete" onclick="return confirmBox();"/>
				</div>
			</html:form>
		</div>
		
		<html:form id="showTemplateForm" class="row">
			<div id="theTemplatePanel" class="displayNo">
				<table class="lMargin25p">
					<tr>
						<td><label class="label"><html:text name="templateName" /></label></td>
						<td><html:property value="templateName" /></td>
					</tr>
					<tr>
						<td><label class="label"><html:text name="templateDescription" /></label></td>
						<td><html:property value="templateDescription" /></td>
					</tr>			
					<tr>
						<td><label class="label"><html:text name="fileTypeForm"/></label></td>
						<td><html:property value="fileTypeName" /></td>
					</tr>
				</table>
				<div class="row">
					<label class="label"><html:text name="templateFields" /></label>
					<table class="table">
						<html:iterator value="tags" status="status">
							<html:if test="%{#status.index == 0}">
								<tr>
									<td><html:checkbox disabled="true" key="selectedTags" fieldValue="%{key}"
											value="%{value}" />
										<html:property value="%{key}" /></td>
							</html:if>
							<html:elseif test="%{#status.index%12 == 0}">
								<tr>
									<td><html:checkbox disabled="true" key="selectedTags" fieldValue="%{key}"
											value="%{value}" />
										<html:property value="%{key}" /></td>
							</html:elseif>
							<html:else>
								<td><html:checkbox disabled="true" key="selectedTags" fieldValue="%{key}"
										value="%{value}" />
									<html:property value="%{key}" /></td>
							</html:else>
						</html:iterator>
					</table>
				</div>
				<div class="buttons row pBottom20">
					<html:a action="showTemplates" cssClass="fleft"><img alt="help" src="images/back.png"></img></html:a>
				</div>
			</div>
		</html:form>
	
		<html:form id="templateAddForm" class="row">
			<div id="addTemplatePanel" class="displayNo">
				<table class="lMargin25p">
					<tr>
						<td class="label"><html:text name="templateName" /></td>
						<td><html:textfield key="templateName" maxLength="32" cssClass="inputForm frigth input"/></td>
						<td><span class="red"><html:property
							value="fieldErrors.templateName" /></span></td>
					</tr>
					<tr>
						<td class="label"><html:text name="templateDescription" /></td>
						<td><html:textfield key="templateDescription" maxLength="128"
							cssClass="inputForm frigth input"/></td>
					</tr>
					<tr>
						<td class="label"><html:text name="fileTypeForm"/></td>
						<td><html:select key="fileType"
								cssClass="inputForm input" list="types" /></td>
					</tr>
				</table>
				<div class="row">
					<div class="label">
						<html:text name="templateFields" />
						<table class="table">
							<html:iterator value="tags" status="status">
								<html:if test="%{#status.index == 0}">
									<tr>
										<td><html:checkbox key="selectedTags" fieldValue="%{key}"
												value="%{value}" />
											<html:property value="%{key}" /></td>
								</html:if>
								<html:elseif test="%{#status.index%12 == 0}">
									<tr>
										<td><html:checkbox key="selectedTags" fieldValue="%{key}"
												value="%{value}" />
											<html:property value="%{key}" /></td>
								</html:elseif>
								<html:else>
									<td><html:checkbox key="selectedTags" fieldValue="%{key}"
											value="%{value}" />
										<html:property value="%{key}" /></td>
								</html:else>
							</html:iterator>
						</table>
					</div>
				</div>
				<div class="buttons row">
					<html:submit action="addTemplate" cssClass="submitButton button"
						key="save" />
					<html:submit action="showTemplates" cssClass="submitButton button"
						key="cancel" />
				</div>
			</div>
		</html:form>
	
		<html:form id="templateEditForm" class="row" action="/editTemplate.action">
			<div id="editTemplatePanel" class="displayNo">
				<table class="lMargin25p">
					<tr>
						<td class="label"><html:text name="templateName" /></td>
						<td><html:textfield key="templateName" cssClass="inputForm frigth input disabled" 
							readonly="true" /></td>
					</tr>
					<tr>
						<td class="label"><html:text name="templateDescription" /></td>
						<td><html:textfield key="templateDescription" maxLength="128"
							cssClass="inputForm frigth input"/></td>
					</tr>
					<tr>
						<td class="label"><html:text name="fileTypeForm"/></td>
						<td><html:select key="fileType"
								cssClass="inputForm input" list="types" /></td>
					</tr>
				</table>
				<div class="row">
					<div class="label">
						<html:text name="templateFields" />
						<table class="table">
							<html:iterator value="tags" status="status">
								<html:if test="%{#status.index == 0}">
									<tr>
										<td><html:checkbox key="selectedTags" fieldValue="%{key}"
												value="%{value}" />
											<html:property value="%{key}" /></td>
								</html:if>
								<html:elseif test="%{#status.index%12 == 0}">
									<tr>
										<td><html:checkbox key="selectedTags" fieldValue="%{key}"
												value="%{value}" />
											<html:property value="%{key}" /></td>
								</html:elseif>
								<html:else>
									<td><html:checkbox key="selectedTags" fieldValue="%{key}"
											value="%{value}" />
										<html:property value="%{key}" /></td>
								</html:else>
							</html:iterator>
						</table>
					</div>
				</div>
				<div class="buttons row">
					<html:submit cssClass="submitButton button" key="save" onclick="return confirmEditBox();"/>
					<html:submit action="showTemplates" cssClass="submitButton button"
						key="cancel" />
				</div>
			</div>
		</html:form>
	</div>
	
</div>



