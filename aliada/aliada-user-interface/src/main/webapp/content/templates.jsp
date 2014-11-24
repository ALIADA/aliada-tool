<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>
<script>
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
});
</script>
<html:hidden id="areTemplates" name="areTemplates" value="%{areTemplates}" />
<html:hidden id="showTheTemplate" name="showTheTemplate" value="%{showTheTemplate}" />
<html:hidden id="showAddTemplateForm" name="showAddTemplateForm" value="%{showAddTemplateForm}" />
<html:hidden id="showEditTemplateForm" name="showEditTemplateForm" value="%{showEditTemplateForm}" />
<h2 class="pageTitle">
	<html:text name="templates" />
</h2>
<div class="content">
	<html:form id="templates">
		<div id="templatesPanel" class="display buttons row">
			<div class="fieldsNoBorder">
				<html:iterator value="templates">
					<html:radio key="selectedTemplate" cssClass="bold lPad10" value="{key}"
						list="{value}" />
					<br>
				</html:iterator>
			</div>
			<html:actionerror/>
			<html:actionmessage/>
			<html:submit action="showAddTemplateForm"
				cssClass="submitButton button" key="add" />
			<div id="areTemplatesButtons" class="displayInline">
				<html:submit action="showTheTemplate" cssClass="submitButton button"
					key="see" />	
				<html:submit action="showEditTemplateForm" cssClass="submitButton button"
					key="edit" />
				<html:submit action="deleteTemplate" cssClass="submitButton button"
					key="delete" />
			</div>	
			<html:submit action="conversion" cssClass="submitButton button fright"
				key="back" />
		</div>
	</html:form>
	
	<html:form id="showTemplateForm" class="row">
		<div id="theTemplatePanel" class="displayNo">
			<div class="row">
				<label class="label"><html:text name="templateName" /></label>
				<html:property value="templateName" />
			</div>
			<div class="row">
				<label class="label"><html:text name="templateDescription" /></label>
				<html:property value="templateDescription" />
			</div>			
			<div class="row">
				<label class="label"><html:text name="fileTypeForm"/></label>:
				<html:property value="fileTypeName" />
			</div>
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
			<div class="buttons row">
				<html:submit action="showTemplates" cssClass="submitButton button"
					key="back" />
			</div>
		</div>
	</html:form>

	<html:form id="templateAddForm" class="row">
		<div id="addTemplatePanel" class="displayNo">
			<div class="row label">
				<html:text name="templateName" />
				<html:textfield key="templateName" maxLength="32" cssClass="inputForm frigth input"/>
				<span class="red"><html:property
						value="fieldErrors.templateName" /></span>
			</div>
			<div class="row label">
				<html:text name="templateDescription" />
				<html:textfield key="templateDescription" maxLength="128"
					cssClass="inputForm frigth input"/>
			</div>
			<div class="row">
				<label class="label"><html:text name="fileTypeForm"/></label>:
				<html:select theme="xhtml" key="fileType"
							cssClass="inputForm input" list="types" />
			</div>
			<div class="row">
				<label class="label"><html:text name="templateFields" /></label>
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
			<div class="buttons row">
				<html:submit action="addTemplate" cssClass="submitButton button"
					key="save" />
				<html:submit action="showTemplates" cssClass="submitButton button"
					key="cancel" />
			</div>
		</div>
	</html:form>

	<html:form id="templateEditForm" class="row">
		<div id="editTemplatePanel" class="displayNo">
			<div class="row label">
				<html:text name="templateName" />
				<html:textfield key="templateName" cssClass="inputForm frigth input disabled" 
					readonly="true" />
			</div>
			<div class="row label">
				<html:text name="templateDescription" />
				<html:textfield key="templateDescription" maxLength="128"
					cssClass="inputForm frigth input"/>
			</div>			
			<div class="row">
				<label class="label"><html:text name="fileTypeForm"/></label>:
				<html:select theme="xhtml" key="fileType"
							cssClass="inputForm input" list="types" />
			</div>
			<div class="row">
				<label class="label"><html:text name="templateFields" /></label>
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
			<div class="buttons row">
				<html:submit action="editTemplate" cssClass="submitButton button"
					key="save" />
				<html:submit action="showTemplates" cssClass="submitButton button"
					key="cancel" />
			</div>
		</div>
	</html:form>
</div>



