<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>
<script>
$(function(){
	var areUsers = $("#areUsers").val();	
	var showAddForm = $("#showAddForm").val();	
	var showEditForm = $("#showEditForm").val();	
	if(areUsers != "true"){
		$("#areUsersButtons").hide();
	}
	if(showAddForm == "true"){
		$("#addUserPanel").show("slow");
	}
	if(showEditForm == "true"){
		$("#editUserPanel").show("slow");
	}
	if((showAddForm == "true") || (showEditForm == "true")){
		$("#usersButtons").hide();		
	}
});
</script>
<html:hidden id="areUsers" name="areUsers" value="%{areUsers}" />
<html:hidden id="showAddForm" name="showAddForm" value="%{showAddForm}" />
<html:hidden id="showEditForm" name="showEditForm" value="%{showEditForm}" />
<h2 class="pageTitle"><html:text name="registeredUsers"/></h2>
<html:form id="user">
	<table class="table">
		<tr class="backgroundGreen center">
			<th><label class="bold"><html:text name="username"/></label></th>
			<th><label class="bold"><html:text name="email"/></label></th>
			<th><label class="bold"><html:text name="role"/></label></th>
			<th><label class="bold"><html:text name="type"/></label></th>
			<th><label class="bold"><html:text name="organisationName"/></label></th>
		</tr>
		<html:iterator value="users" var="dato">
			<tr>
				<td><html:radio name="selectedUser" list="username" /></td>
				<td><html:property value="email" /></td>
				<td><html:property value="role" /></td>
				<td><html:property value="type" /></td>
				<td><html:property value="organisation" /></td>
			</tr>
		</html:iterator>
	</table>
	<html:actionmessage />
	<html:actionerror/>

	<div id=usersButtons class="display buttons row">
		<html:submit action="showAddForm" cssClass="submitButton button"
			key="add" />
		<div id="areUserButtons" class="displayInline">	
			<html:submit action="showEditForm" cssClass="submitButton button"
				key="edit" />
			<html:submit action="deleteUser" cssClass="submitButton button"
				key="delete" />
		</div>
		<html:submit action="configure" cssClass="submitButton button fright"
			key="back" />
	</div>
</html:form>

<html:form id="userAddForm" class="row">
	<div id="addUserPanel" class="displayNo fields">
		<h2 class="pageSubtitle"><html:text name="newUser"/></h2>
		<table class="table">
			<tr class="backgroundGreen center">
				<th><label for="usernameForm" class="bold"><html:text name="username"/></label></th>
				<th><label for="passwordForm" class="bold"><html:text name="userPassword"/></label></th>
				<th><label for="emailForm" class="bold"><html:text name="email"/></label></th>
				<th><label for="roleForm" class="bold"><html:text name="role"/></label></th>
				<th><label for="typeForm" class="bold"><html:text name="type"/></label></th>
				<th><label for="organisationForm" class="bold"><html:text name="organisationName"/></label></th>
			</tr>
			<tr>
				<td><html:textfield key="usernameForm"
						cssClass="tableInput input" /></td>
				<td><html:password key="passwordForm" showPassword="true"
						cssClass="tableInput input" /></td>
				<td><html:textfield key="emailForm"
						cssClass="tableInput input" /></td>
				<td><html:select key="roleForm"
						cssClass="tableInput" list="roles" /></td>
				<td><html:select key="typeForm"
						cssClass="tableInput" list="types" /></td>
				<td><html:select key="organisationForm"
						cssClass="tableInput" list="organisations" /></td>
			</tr> 
			<tr>
				<td><html:fielderror fieldName="usernameForm"/> </td>		
				<td><html:fielderror fieldName="passwordForm"/> </td>
				<td><html:fielderror fieldName="emailForm"/></td>
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

<html:form id="userEditForm" class="row">
	<div id="editUserPanel" class="displayNo fields">
		<h2 class="pageSubtitle"><html:text name="editUser"/></h2>
		<table class="table">
			<tr class="backgroundGreen center">
				<th><label for="usernameForm" class="bold"><html:text name="username"/></label></th>
				<th><label for="passwordForm" class="bold"><html:text name="userPassword"/></label></th>
				<th><label for="emailForm" class="bold"><html:text name="email"/></label></th>
				<th><label for="roleForm" class="bold"><html:text name="role"/></label></th>
				<th><label for="typeForm" class="bold"><html:text name="type"/></label></th>
				<th><label for="organisationForm" class="bold"><html:text name="organisationName"/></label></th>
			</tr>
			<tr>
				<td><html:textfield key="usernameForm"
						cssClass="tableInput input disabled" size="30" readonly="true" /></td>
				<td><html:password key="passwordForm" showPassword="true"
						cssClass="tableInput input" size="20"/></td>
				<td><html:textfield key="emailForm"
						cssClass="tableInput input" size="30" /></td>
				<td><html:select key="roleForm"
						cssClass="tableInput" list="roles" /></td>
				<td><html:select key="typeForm"
						cssClass="tableInput" list="types" /></td>
				<td><html:select key="organisationForm"
						cssClass="tableInput" list="organisations" /></td>
			</tr>
			<tr>
				<td><html:fielderror fieldName="usernameForm"/> </td>		
				<td><html:fielderror fieldName="passwordForm"/> </td>
				<td><html:fielderror fieldName="emailForm"/></td>
			</tr>
		</table>
		<div class="buttons row">
			<html:submit action="editUser" cssClass="submitButton button"
				key="save" />
			<html:submit action="showUsers" cssClass="submitButton button"
				key="cancel" />
		</div>
	</div>
</html:form>



