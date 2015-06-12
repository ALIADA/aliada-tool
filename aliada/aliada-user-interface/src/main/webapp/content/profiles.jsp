<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>

<link type="text/css" rel="stylesheet" href="<html:url value="css/profiles.css" />" />

<script>

function confirmBox(){
	var answer = window.confirm("<html:text name='delete.message'/>");
	if (answer == true) {
		console.log("Remove profile");
		$("#profiles").submit();
	} else {
		console.log("Remove profile cancel");
	}
return false;
}

function confirmEditBox(){
	var answer = window.confirm("<html:text name='edit.message'/>");
	if (answer == true) {
		console.log("Edit profile");
		$("#profileEditForm").submit();
	} else {
		console.log("Edit profile cancel");
	}
return false;
}

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
	
	$("#profiles :radio").on("change",function(){
		if($("#profiles :radio:checked")){
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

<html:hidden id="areProfiles" name="areProfiles" value="%{areProfiles}" />
<html:hidden id="showTheProfile" name="showTheProfile" value="%{showTheProfile}" />
<html:hidden id="showAddProfileForm" name="showAddProfileForm" value="%{showAddProfileForm}" />
<html:hidden id="showEditProfileForm" name="showEditProfileForm" value="%{showEditProfileForm}" />

<div id="profilesPage">

<ul class="breadcrumb">
	<span class="breadCrumb"><html:text name="home"/></span>
	<li><span class="breadcrumb activeGreen"><html:text name="profiles.title"/></span></li>
</ul>

	<div class="profilesPage">
	
		<div id="profilesPanel" class="display row">
			<html:form id="profiles" action="/deleteProfile.action">
				<div class="lMargin45p">
					<html:iterator value="profiles">
						<html:radio key="selectedProfile" cssClass="bold lPad10" value="{key}" list="{value}"/><br/>
					</html:iterator>
				</div>

				<html:actionmessage/>
				<html:actionerror/>
				
				<div id="areProfilesButtons" class="buttons">
						<html:a action="manage" cssClass="fleft"><img alt="help" src="images/back.png"></img></html:a>
						<html:submit action="showAddProfileForm" cssClass="submitButton button"
							key="add" />
						<html:submit id="seeButton" disabled="true" action="showTheProfile" cssClass="submitButton button"
							key="see" />
						<html:submit id="editButton" disabled="true" action="showEditProfileForm" cssClass="submitButton button"
							key="edit" />
						<html:submit id="deleteButton" disabled="true" cssClass="submitButton button" key="delete" onclick="return confirmBox();"/>
				</div>
			</html:form>
		</div>
		
	
		<html:form id="showProfileForm" class="row">
			<div id="theProfilePanel" class="displayNo">
				<table class="lMargin40p">
					<tr>
						<td><label class="label"><html:text name="nameForm"/></label></td>
						<td><html:property value="nameForm" /></td>
					</tr>
					<tr>
						<td><label class="label"><html:text name="descriptionForm"/></label></td>			
						<td><html:property value="descriptionForm" /></td>
					</tr>
					<tr>
						<td><label class="label"><html:text name="profileTypeForm"/></label></td>
						<td><html:property value="profileTypeNameForm" /></td>
					</tr>
					<tr>
						<td><label class="label"><html:text name="schemeForm"/></label></td>
						<td><html:property value="schemeNameForm" /></td>
					</tr>
					<tr>
						<td><label class="label"><html:text name="fileTypeForm"/></label></td>
						<td><html:property value="fileTypeNameForm" /></td>
					</tr>
					<tr>
						<td><label class="label"><html:text name="fileFormatForm"/></label></td>
						<td><html:property value="fileFormatNameForm" /></td>
					</tr>
					<tr>
						<td><label class="label"><html:text name="characterSetForm"/></label></td>
						<td><html:property value="characterSetNameForm" /></td>
					</tr>
				</table>
				<div class="buttons row pBottom20">
					<html:a action="showProfiles" cssClass="fleft"><img alt="help" src="images/back.png"></img></html:a>
				</div>
			</div>
		</html:form>
	
		<html:form id="profileAddForm" class="row">
			<div id="addProfilePanel" class="displayNo">
				<table class="lMargin25p">
						<tr>
							<td class="label"><html:text name="nameForm"/></td>
							<td><html:textfield key="nameForm" maxLength="32" cssClass="inputForm frigth input"/></td>
							<td><span class="red"><html:property value="fieldErrors.nameForm" /></span></td>
						</tr>
						<tr>
							<td class="label"><html:text name="descriptionForm"/></td>
							<td><html:textfield key="descriptionForm" maxLength="128"  cssClass="inputForm frigth input"/></td>
						</tr>
						<tr>
							<td><html:select theme="xhtml" key="profileTypeForm"
									cssClass="inputForm input" list="profileTypes" /></td>
						</tr>
						<tr>
							<td><html:select theme="xhtml" key="schemeForm"
									cssClass="inputForm input" list="schemes" /></td>
						</tr>
						<tr>
							<td><html:select theme="xhtml" key="fileTypeForm"
									cssClass="inputForm input" list="types" /></td>
						</tr>
						<tr>
							<td><html:select theme="xhtml" key="fileFormatForm"
									cssClass="inputForm input" list="formats" /> </td>
						</tr>
						<tr>
							<td><html:select theme="xhtml" key="characterSetForm"
									cssClass="inputForm input" list="characterSets" /> </td>
						</tr>
				</table>
				<div class="buttons row">
					<html:submit action="addProfile" cssClass="submitButton button"
							key="save" />
					<html:submit action="showProfiles" cssClass="submitButton button"
							key="cancel" />	
				</div>
			</div>
		</html:form>
	
		<html:form id="profileEditForm" class="row" action="/editProfile.action">
			<div id="editProfilePanel" class="displayNo">
				<table class="lMargin25p">
					<tr>
						<td class="label"><html:text name="nameForm"/></td>
						<td><html:textfield key="nameForm" cssClass="inputForm frigth input disabled" readonly="true"/></td>
					</tr>
					<tr>				
						<td class="label"><html:text name="descriptionForm"/></td>
						<td><html:textfield key="descriptionForm" maxLength="128" cssClass="inputForm frigth input" /></td>
					</tr>
					<tr>	
						<td><html:select theme="xhtml" key="profileTypeForm"
								cssClass="inputForm input" list="profileTypes" /></td>
					</tr>
					<tr>
						<td><html:select theme="xhtml" key="schemeForm"
								cssClass="inputForm input" list="schemes" /></td>
					</tr>
					<tr>		
						<td><html:select theme="xhtml" key="fileTypeForm"
								cssClass="inputForm input" list="types" /></td>
					</tr>
					<tr>
						<td><html:select theme="xhtml" key="fileFormatForm"
								cssClass="inputForm input" list="formats" /></td>
					</tr>
					<tr>
						<td><html:select theme="xhtml" key="characterSetForm"
								cssClass="inputForm input" list="characterSets" /></td>
					</tr>
				</table>
				<div class="buttons row">
					<html:submit cssClass="submitButton button" key="save" onclick="return confirmEditBox();"/>
					<html:submit action="showProfiles" cssClass="submitButton button"
						key="cancel" />
				</div>
			</div>
		</html:form>
	</div>

</div>



