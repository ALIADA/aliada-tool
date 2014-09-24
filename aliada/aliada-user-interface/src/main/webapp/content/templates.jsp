<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>

<h2 class="pageTitle">
	<html:text name="templates" />
</h2>
<div class="content">
	<html:form id="templates">
		<div
			<html:if test="showAddTemplateForm || showEditTemplateForm">class="displayNo"</html:if>
			<html:else>
		    class="display buttons row"
		</html:else>>
			<div class="fieldsNoBorder">
				<html:iterator value="templates">
					<html:radio key="selectedTemplate" cssClass="label" value="{key}"
						list="{value}" />
					<br>
				</html:iterator>
			</div>
			<html:actionerror/>
			<html:actionmessage/>
			<html:submit action="showAddTemplateForm"
				cssClass="submitButton button" key="add" />
			<div <html:if test="areTemplates">class="displayInline"</html:if>
				<html:else>
				    class="displayNo"
				</html:else>>	
					<html:submit action="showEditTemplateForm" cssClass="submitButton button"
						key="edit" />
					<html:submit action="deleteTemplate" cssClass="submitButton button"
						key="delete" />
			</div>	
			<html:submit action="conversion" cssClass="submitButton button fright"
				key="back" />
		</div>
	</html:form>

	<html:form id="templateAddForm" class="row">
		<div <html:if test="showAddTemplateForm">class="display"</html:if>
			<html:else>
		    class="displayNo"
		</html:else>>
			<div class="row label">
				<html:text name="templateName" />
				<html:textfield key="templateName" cssClass="inputForm frigth input"
					size="30" />
				<span class="red"><html:property
						value="fieldErrors.templateName" /></span>
			</div>
			<div class="row label">
				<html:text name="templateDescription" />
				<html:textfield key="templateDescription"
					cssClass="inputForm frigth input" size="30" />
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
		<div <html:if test="showEditTemplateForm">class="display"</html:if>
			<html:else>
		    class="displayNo"
		</html:else>>
			<div class="row label">
				<html:text name="templateName" />
				<html:textfield key="templateName" cssClass="inputForm frigth input" disabled="true"
					size="30" readonly="true" />
			</div>
			<div class="row label">
				<html:text name="templateDescription" />
				<html:textfield key="templateDescription"
					cssClass="inputForm frigth input" size="30" />
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
					key="edit" />
				<html:submit action="showTemplates" cssClass="submitButton button"
					key="cancel" />
			</div>
		</div>
	</html:form>
</div>



