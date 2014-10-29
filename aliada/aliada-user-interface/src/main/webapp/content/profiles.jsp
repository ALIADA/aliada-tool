<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>
<script>
$(function(){
	var areProfiles = $("#areProfiles").val();	
	var showTheProfile = $("#showTheProfile").val();
	var showAddProfileForm = $("#showAddProfileForm").val();	
	var showEditProfileForm = $("#showEditProfileForm").val();	
	if(areProfiles != "true"){
		$("#areProfilesButtons").hide();
	}
	if(showAddProfileForm == "true"){
		$("#addProfilePanel").show("slow");
	}
	if(showEditProfileForm == "true"){
		$("#editProfilePanel").show("slow");
	}
	if(showTheProfile == "true"){
		$("#theProfilePanel").show("slow");
	}
	if((showAddProfileForm == "true") || (showEditProfileForm == "true") || (showTheProfile == "true")){
		$("#profilesPanel").hide();		
	}
});
</script>
<html:hidden id="areProfiles" name="areProfiles" value="%{areProfiles}" />
<html:hidden id="showTheProfile" name="showTheProfile" value="%{showTheProfile}" />
<html:hidden id="showAddProfileForm" name="showAddProfileForm" value="%{showAddProfileForm}" />
<html:hidden id="showEditProfileForm" name="showEditProfileForm" value="%{showEditProfileForm}" />
<h2 class="pageTitle"><html:text name="profiles.title"/></h2>
<div class="content">
<html:form id="profiles">
	<div id="profilesPanel" class="display buttons row">
		<div class="fieldsNoBorder">
			<html:iterator value="profiles">
				<html:radio key="selectedProfile" cssClass="bold lPad10" list="{value}"/><br/>
			</html:iterator>
		</div>
		<html:actionmessage />
		<html:actionerror/>
		<html:submit action="showAddProfileForm" cssClass="submitButton button"
			key="add" />
		<div id="areProfilesButtons" class="displayInline">	
			<html:submit action="showTheProfile" cssClass="submitButton button"
				key="see" />
			<html:submit action="showEditProfileForm" cssClass="submitButton button"
				key="edit" />
			<html:submit action="deleteProfile" cssClass="submitButton button"
				key="delete" />
		</div>
		<html:submit action="manage" cssClass="submitButton button fright"
			key="back" />
	</div>
</html:form>

<html:form id="showProfileForm" class="row">
	<div id="theProfilePanel" class="displayNo">
		<div class="row">
			<label class="label"><html:text name="nameForm"/></label>
			<html:property value="nameForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="descriptionForm"/></label>			
			<html:property value="descriptionForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="profileTypeForm"/></label>:
			<html:property value="profileTypeNameForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="schemeForm"/></label>:
			<html:property value="schemeNameForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="fileTypeForm"/></label>:
			<html:property value="fileTypeNameForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="fileFormatForm"/></label>: 
			<html:property value="fileFormatNameForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="characterSetForm"/></label>:
			<html:property value="characterSetNameForm" />
		</div>
		<div class="buttons row">
			<html:submit action="showProfiles" cssClass="submitButton button"
				key="back" />
		</div>
	</div>
</html:form>

<html:form id="profileAddForm" class="row">
	<div id="addProfilePanel" class="displayNo">
		<div class="row label">
			<html:text name="nameForm"/>
			<html:textfield key="nameForm" maxLength="32" cssClass="inputForm frigth input"/>
			<span class="red"><html:property value="fieldErrors.nameForm" /></span>	
		</div>
		<div class="row label">
			<html:text name="descriptionForm"/>
			<html:textfield key="descriptionForm" maxLength="128"  cssClass="inputForm frigth input"/>
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
	<div id="editProfilePanel" class="displayNo">
		<div class="row label">
			<html:text name="nameForm"/>
			<html:textfield key="nameForm" cssClass="inputForm frigth input disabled" readonly="true"/>	
		</div>
		<div class="row label">
			<html:text name="descriptionForm"/>
			<html:textfield key="descriptionForm" maxLength="128" cssClass="inputForm frigth input" />
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



