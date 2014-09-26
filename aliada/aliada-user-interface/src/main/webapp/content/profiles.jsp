<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>

<h2 class="pageTitle"><html:text name="profiles.title"/></h2>
<div class="content">
<html:form id="profiles">
	<div <html:if test="showAddProfileForm || showEditProfileForm">class="displayNo"</html:if>
		<html:else>
		    class="display buttons row"
		</html:else>>
		<div class="fieldsNoBorder">
			<html:iterator value="profiles">
				<html:radio key="selectedProfile" cssClass="label" list="{value}"/><br>
			</html:iterator>
		</div>
		<html:actionmessage />
		<html:actionerror/>
		<html:submit action="showAddProfileForm" cssClass="submitButton button"
			key="add" />
		<div <html:if test="areProfiles">class="displayInline"</html:if>
			<html:else>
			    class="displayNo"
			</html:else>>	
				<html:submit action="showEditProfileForm" cssClass="submitButton button"
					key="edit" />
				<html:submit action="deleteProfile" cssClass="submitButton button"
					key="delete" />
		</div>
		<html:submit action="manage" cssClass="submitButton button fright"
			key="back" />
	</div>
</html:form>

<html:form id="profileAddForm" class="row">
	<div <html:if test="showAddProfileForm">class="display"</html:if>
		<html:else>
		    class="displayNo"
		</html:else>>
		<div class="row label">
			<html:text name="nameForm"/>
			<html:textfield key="nameForm" cssClass="inputForm frigth input" size="30" />
			<span class="red"><html:property value="fieldErrors.nameForm" /></span>	
		</div>
		<div class="row label">
			<html:text name="descriptionForm"/>
			<html:textfield key="descriptionForm" cssClass="inputForm frigth input" size="30" />
		</div>
		<div class="row">
			<html:select theme="xhtml" key="profileTypeForm"
						cssClass="inputForm input" list="profileTypes" />
		</div>
		<div class="row">
			<html:select theme="xhtml" key="schemeForm"
						cssClass="inputForm input" list="schemes" />
		</div>
		<div class="row">
			<html:select theme="xhtml" key="fileTypeForm"
						cssClass="inputForm input" list="types" />
		</div>
		<div class="row">
			<html:select theme="xhtml" key="fileFormatForm"
						cssClass="inputForm input" list="formats" />
		</div>
		<div class="row">
			<html:select theme="xhtml" key="characterSetForm"
						cssClass="inputForm input" list="characterSets" />
		</div>
		<div class="buttons row">
			<html:submit action="addProfile" cssClass="submitButton button"
				key="save" />
			<html:submit action="showProfiles" cssClass="submitButton button"
				key="cancel" />
		</div>
	</div>
</html:form>

<html:form id="profileEditForm" class="row">
	<div <html:if test="showEditProfileForm">class="display"</html:if>
		<html:else>
		    class="displayNo"
		</html:else>>
		<div class="row label">
			<html:text name="nameForm"/>
			<html:textfield key="nameForm" cssClass="inputForm frigth input disabled" size="30" readonly="true"/>	
		</div>
		<div class="row label">
			<html:text name="descriptionForm"/>
			<html:textfield key="descriptionForm" cssClass="inputForm frigth input" size="30" />
		</div>
		<div class="row">
			<html:select theme="xhtml" key="profileTypeForm"
						cssClass="inputForm input" list="profileTypes" />
		</div>
		<div class="row">
			<html:select theme="xhtml" key="schemeForm"
						cssClass="inputForm input" list="schemes" />
		</div>
		<div class="row">
			<html:select theme="xhtml" key="fileTypeForm"
						cssClass="inputForm input" list="types" />
		</div>
		<div class="row">
			<html:select theme="xhtml" key="fileFormatForm"
						cssClass="inputForm input" list="formats" />
		</div>
		<div class="row">
			<html:select theme="xhtml" key="characterSetForm"
						cssClass="inputForm input" list="characterSets" />
		</div>
		<div class="buttons row">
			<html:submit action="editProfile" cssClass="submitButton button"
				key="save" />
			<html:submit action="showProfiles" cssClass="submitButton button"
				key="cancel" />
		</div>
	</div>
</html:form>
</div>



