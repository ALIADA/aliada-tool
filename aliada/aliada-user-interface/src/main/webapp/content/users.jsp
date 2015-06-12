<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>

<link type="text/css" rel="stylesheet" href="<html:url value="css/users.css" />" />

<script>

function confirmBox(){
	var answer = window.confirm("<html:text name='delete.message'/>");
	if (answer == true) {
		console.log("Remove user");
		$("#user").submit();
	} else {
		console.log("Remove user cancel");
	}
return false;
}

function confirmEditBox(){
	var answer = window.confirm("<html:text name='edit.message'/>");
	if (answer == true) {
		console.log("Edit user");
		$("#userEditForm").submit();
	} else {
		console.log("Edit user cancel");
	}
return false;
}

$(function(){
	var showAddForm = $("#showAddForm").val();	
	var showEditForm = $("#showEditForm").val();	
	
	if(showAddForm == "true"){
		$("#addUserPanel").show("slow");
	}
	if(showEditForm == "true"){
		$("#editUserPanel").show("slow");
	}
	if((showAddForm == "true") || (showEditForm == "true")){
		$("#usersButtons").hide();
		$("#user").hide();
	}
	
	$("#user :radio").on("change",function(){
		if($("#user :radio:checked")){
			$("#editButton").prop( "disabled", false);
			$("#deleteButton").prop( "disabled", false);
		}
		else{
			$("#editButton").prop( "disabled", true);
			$("#deleteButton").prop( "disabled", true);
		}
	}); 
	
});
</script>

<html:hidden id="areUsers" name="areUsers" value="%{areUsers}" />
<html:hidden id="showAddForm" name="showAddForm" value="%{showAddForm}" />
<html:hidden id="showEditForm" name="showEditForm" value="%{showEditForm}" />

<div id="usersPage">

	<ul class="breadcrumb">
		<span class="breadCrumb"><html:text name="home"/></span>
		<li><span class="breadcrumb activeGreen"><html:text name="registeredUsers"/></span></li>
	</ul>
	
	<div class ="usersPage">
	
	<html:form id="user" action="/deleteUser.action">
		<table id="u" class="tableUsers">
			<tr class="backgroundGreen center">
				<th><label class="bold"><html:text name="username"/></label></th>
				<th><label class="bold"><html:text name="email"/></label></th>
				<th><label class="bold"><html:text name="role"/></label></th>
				<th><label class="bold"><html:text name="type"/></label></th>
				<th><label class="bold"><html:text name="organisationName"/></label></th>
			</tr>
			<html:iterator value="users" var="dato">
				<tr class="center">
					<td class="radio"><html:radio name="selectedUser" value="{key}" list="username" /></td>
					<td><html:property value="email" /></td>
					<td><html:property value="role" /></td>
					<td><html:property value="type" /></td>
					<td><html:property value="organisation" /></td>
				</tr>
			</html:iterator>
		</table>
		
		<html:actionmessage/>
		<html:actionerror/>
	
		<div id="usersButtons" class="display buttons row">
			<html:a action="manage" cssClass="fleft"><img alt="help" src="images/back.png"></img></html:a>
			<html:submit action="showAddFormStack" cssClass="submitButton button"
				key="add" />
			<div id="areUserButtons" class="displayInline">	
				<html:submit id="editButton" disabled="true" action="showEditForm" cssClass="submitButton button"
					key="edit" />
				<html:submit id="deleteButton" disabled="true" cssClass="submitButton button" key="delete" onclick="return confirmBox();"/>
			</div>
		</div>
	</html:form>
	
	<html:form id="userAddForm" class="row">
		<div id="addUserPanel" class="displayNo">
			<h2 class="pageSubtitle center"><html:text name="newUser"/></h2>
			<table class="lMargin25p">
				<tr>
					<th class="backgroundGreen center"><label for="usernameForm" class="bold"><html:text name="username"/></label></th>
					<td><html:textfield key="usernameForm" maxLength="20"
							cssClass="tableInput input center" /></td>
					<td><span class="red"><html:property value="fieldErrors.usernameForm" /></span></td>
				</tr>
				<tr>
					<th class="backgroundGreen center"><label for="passwordForm" class="bold"><html:text name="userPassword"/></label></th>
					<td><html:password key="passwordForm" showPassword="true"
							cssClass="tableInput input center" /></td>
					<td><span class="red"><html:property value="fieldErrors.passwordForm" /></span></td>
				</tr>
				<tr>
					<th class="backgroundGreen center"><label for="repeatPasswordForm" class="bold"><html:text name="userRepeatPassword"/></label></th>
					<td><html:password key="repeatPasswordForm" showPassword="true"
							cssClass="tableInput input center" /></td>
					<td><span class="red"><html:property value="fieldErrors.repeatPasswordForm" /></span></td>
				</tr>
				<tr>			
					<th class="backgroundGreen center"><label for="emailForm" class="bold"><html:text name="email"/></label></th>		
					<td><html:textfield key="emailForm" maxLength="128"
							cssClass="tableInput input center" /></td>
					<td><span class="red"><html:property value="fieldErrors.emailForm" /></span></td>
				</tr>
				<tr>			
					<th class="backgroundGreen center"><label for="roleForm" class="bold"><html:text name="role"/></label></th>		
					<td><html:select key="roleForm"
							cssClass="tableInput center" list="roles" /></td>
				</tr>
				<tr>			
					<th class="backgroundGreen center"><label for="typeForm" class="bold"><html:text name="type"/></label></th>	
					<td><html:select key="typeForm"
							cssClass="tableInput center" list="userTypes" /></td>
				</tr>
				<tr>		
					<th class="backgroundGreen center"><label for="organisationForm" class="bold"><html:text name="organisationName"/></label></th>		
					<td><html:select key="organisationForm"
							cssClass="tableInput center" list="organisations" /></td>
				</tr> 
			</table>	
			
			<div class="buttons row">
				<html:submit action="addUser" cssClass="submitButton button"
					key="save" />
				<html:submit action="showUsers" cssClass="submitButton button"
					key="cancel" />
			</div>
		</div>
	</html:form>
	
	<html:form id="userEditForm" class="row" action="/editUser.action">
		<div id="editUserPanel" class="displayNo">
			<h2 class="pageSubtitle center"><html:text name="editUser"/></h2>
			<table class="lMargin25p">
				<tr>
						<th class="backgroundGreen center"><label for="usernameForm" class="bold"><html:text name="username"/></label></th>
						<td><html:textfield key="usernameForm" maxLength="20"
								cssClass="tableInput input center disabled" readonly="true"/></td>
					</tr>
					<tr>
						<th class="backgroundGreen center"><label for="passwordForm" class="bold"><html:text name="currPassword"/></label></th>
						<td><html:password key="passwordForm" showPassword="true"
								cssClass="tableInput input center" /></td>
						<td><html:fielderror fieldName="passwordForm"/> </td>
					</tr>
					<tr>
						<th class="backgroundGreen center"><label for="newPasswordForm" class="bold"><html:text name="newPasswordForm"/></label></th>
						<td><html:password key="newPasswordForm" showPassword="true"
								cssClass="tableInput input center" /></td>
						<td><html:fielderror fieldName="newPasswordForm"/> </td>
					</tr>
					<tr>
						<th class="backgroundGreen center"><label for="repeatNewPasswordForm" class="bold"><html:text name="repeatNewPasswordForm"/></label></th>
						<td><html:password key="repeatNewPasswordForm" showPassword="true"
								cssClass="tableInput input center" /></td>
						<td><html:fielderror fieldName="repeatNewPasswordForm"/> </td>
					</tr>
					<tr>			
						<th class="backgroundGreen center"><label for="emailForm" class="bold"><html:text name="email"/></label></th>		
						<td><html:textfield key="emailForm" maxLength="128"
								cssClass="tableInput input center" /></td>
						<td><html:fielderror fieldName="emailForm"/></td>
					</tr>
					<tr>			
						<th class="backgroundGreen center"><label for="roleForm" class="bold"><html:text name="role"/></label></th>		
						<td><html:select key="roleForm"
								cssClass="tableInput center" list="roles" /></td>
					</tr>
					<tr>			
						<th class="backgroundGreen center"><label for="typeForm" class="bold"><html:text name="type"/></label></th>	
						<td><html:select key="typeForm"
								cssClass="tableInput center" list="userTypes" /></td>
					</tr>
					<tr>		
						<th class="backgroundGreen center"><label for="organisationForm" class="bold"><html:text name="organisationName"/></label></th>		
						<td><html:select key="organisationForm"
								cssClass="tableInput center" list="organisations" /></td>
				</tr> 
			</table>
			<div class="buttons row">
				<html:submit cssClass="submitButton button" key="save" onclick="return confirmEditBox();"/>
				<html:submit action="showUsers" cssClass="submitButton button"
					key="cancel" />
			</div>
		</div>
	</html:form>
	</div>

</div>



