<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>

<h2 class="pageTitle"><html:text name="registeredUsers"/></h2>
<html:form id="user">
	<table class="table">
		<tr class="backgroundGreen center">
			<th><label class="bold"><html:text name="username"/></label></th>
			<th><label class="bold"><html:text name="userPassword"/></label></th>
			<th><label class="bold"><html:text name="email"/></label></th>
			<th><label class="bold"><html:text name="role"/></label></th>
			<th><label class="bold"><html:text name="type"/></label></th>
		</tr>
		<html:iterator value="users" var="dato">
			<tr>
				<td><html:radio name="selectedUser" list="username" /></td>
				<td><html:property value="password" /></td>
				<td><html:property value="email" /></td>
				<td><html:property value="role" /></td>
				<td><html:property value="type" /></td>
			</tr>
		</html:iterator>
	</table>
	<html:actionmessage />
	<html:actionerror/>

	<div <html:if test="showAddForm || showEditForm">class="displayNo"</html:if>
		<html:else>
		    class="display buttons row"
		</html:else>>
		<html:submit action="showAddForm" cssClass="submitButton button"
			key="add" />
		<div <html:if test="areUsers">class="displayInline"</html:if>
		<html:else>
		    class="displayNo"
		</html:else>>	
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
	<div <html:if test="showAddForm">class="display fields"</html:if>
		<html:else>
		    class="displayNo"
		</html:else>>
		<h2 class="pageSubtitle"><html:text name="newUser"/></h2>
		<table class="table">
			<tr class="backgroundGreen center">
				<th><label for="usernameForm" class="bold"><html:text name="username"/></label></th>
				<th><label for="passwordForm" class="bold"><html:text name="userPassword"/></label></th>
				<th><label for="emailForm" class="bold"><html:text name="email"/></label></th>
				<th><label for="roleForm" class="bold"><html:text name="role"/></label></th>
				<th><label for="typeForm" class="bold"><html:text name="type"/></label></th>
			</tr>
			<tr>
				<td><html:textfield key="usernameForm"
						cssClass="inputForm fright input" size="30" required="true" /></td>
				<td><html:password key="passwordForm" showPassword="true"
						cssClass="inputForm fright input" size="20" required="true" /></td>
				<td><html:textfield key="emailForm"
						cssClass="inputForm fright input" size="30" /></td>
				<td><html:select key="roleForm"
						cssClass="inputForm fright" list="roles" /></td>
				<td><html:select key="typeForm"
						cssClass="inputForm fright" list="types" /></td>
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
	<div <html:if test="showEditForm">class="display fields"</html:if>
		<html:else>
		    class="displayNo"
		</html:else>>
		<h2 class="pageSubtitle"><html:text name="editUser"/></h2>
		<table class="table">
			<tr class="backgroundGreen center">
				<th><label for="usernameForm" class="bold"><html:text name="username"/></label></th>
				<th><label for="passwordForm" class="bold"><html:text name="userPassword"/></label></th>
				<th><label for="emailForm" class="bold"><html:text name="email"/></label></th>
				<th><label for="roleForm" class="bold"><html:text name="role"/></label></th>
				<th><label for="typeForm" class="bold"><html:text name="type"/></label></th>
			</tr>
			<tr>
				<td><html:textfield key="usernameForm"
						cssClass="inputForm fright input disabled" size="30" required="true" readonly="true" /></td>
				<td><html:password key="passwordForm" showPassword="true"
						cssClass="inputForm fright input" size="20" required="true" /></td>
				<td><html:textfield key="emailForm"
						cssClass="inputForm fright input" size="30" /></td>
				<td><html:select key="roleForm"
						cssClass="inputForm fright" list="roles" /></td>
				<td><html:select key="typeForm"
						cssClass="inputForm fright" list="types" /></td>
			</tr>
			<tr>
				<td><html:fielderror fieldName="usernameForm"/> </td>		
				<td><html:fielderror fieldName="passwordForm"/> </td>
				<td><html:fielderror fieldName="emailForm"/></td>
			</tr>
		</table>
		<div class="buttons row">
			<html:submit action="editUser" cssClass="submitButton button"
				key="edit" />
			<html:submit action="showUsers" cssClass="submitButton button"
				key="cancel" />
		</div>
	</div>
</html:form>



