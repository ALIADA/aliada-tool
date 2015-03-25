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

<ul class="breadcrumb">
	<span class="breadCrumb"><html:text name="home"/></span>
	<li><span class="breadcrumb activeGreen"><html:text name="registeredUsers"/></span></li>
</ul>

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
			<tr class="center">
				<td class="fleft"><html:radio name="selectedUser" list="username" /></td>
				<td><html:property value="email" /></td>
				<td><html:property value="role" /></td>
				<td><html:property value="type" /></td>
				<td><html:property value="organisation" /></td>
			</tr>
		</html:iterator>
	</table>
	
	<html:if test="tab==3">
		<html:actionmessage/>
	</html:if>
	<html:if test="tab==3">
		<html:actionerror/>
	</html:if>

	<div id=usersButtons class="display buttons row">
		<html:submit action="showAddForm" cssClass="submitButton button"
			key="add" />
		<div id="areUserButtons" class="displayInline">	
			<html:submit action="showEditForm" cssClass="submitButton button"
				key="edit" />
			<html:submit action="deleteUser" cssClass="submitButton button"
				key="delete" />
		</div>
	</div>
</html:form>

<html:form id="userAddForm" class="row">
	<div id="addUserPanel" class="displayNo fields">
		<h2 class="pageSubtitle"><html:text name="newUser"/></h2>
		<table class="table">
			<tr>
				<th class="backgroundGreen center"><label for="usernameForm" class="bold"><html:text name="username"/></label></th>
				<td><html:textfield key="usernameForm" maxLength="20"
						cssClass="tableInput input center" /></td>
				<td><span class="red"><html:property
						value="fieldErrors.usernameForm" /></span></td>
			</tr>
			<tr>
				<th class="backgroundGreen center"><label for="passwordForm" class="bold"><html:text name="userPassword"/></label></th>
				<td><html:password key="passwordForm" showPassword="true"
						cssClass="tableInput input center" /></td>
				<td><span class="red"><html:property
						value="fieldErrors.passwordForm" /></span></td>
			</tr>
			<tr>			
				<th class="backgroundGreen center"><label for="emailForm" class="bold"><html:text name="email"/></label></th>		
				<td><html:textfield key="emailForm" maxLength="128"
						cssClass="tableInput input center" /></td>
				<td><span class="red"><html:property
						value="fieldErrors.emailForm" /></span></td>
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

<html:form id="userEditForm" class="row">
	<div id="editUserPanel" class="displayNo fields">
		<h2 class="pageSubtitle"><html:text name="editUser"/></h2>
		<table class="table">
			<tr>
					<th class="backgroundGreen center"><label for="usernameForm" class="bold"><html:text name="username"/></label></th>
					<td><html:textfield key="usernameForm" maxLength="20"
							cssClass="tableInput input center disabled" readonly="true"/></td>
				</tr>
				<tr>
					<th class="backgroundGreen center"><label for="passwordForm" class="bold"><html:text name="userPassword"/></label></th>
					<td><html:password key="passwordForm" showPassword="true"
							cssClass="tableInput input center" /></td>
					<td><html:fielderror fieldName="passwordForm"/> </td>
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
			<html:submit action="editUser" cssClass="submitButton button"
				key="save" />
			<html:submit action="showUsers" cssClass="submitButton button"
				key="cancel" />
		</div>
	</div>
</html:form>



