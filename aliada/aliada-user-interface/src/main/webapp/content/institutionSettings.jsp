<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>
<ul class="breadcrumb">
	<li><html:a action="configure" cssClass="breadcrumb activeGreen"><html:text name="organisation.title"/></html:a></li>
</ul>
<h2 class="pageTitle"><html:text name="organisation.title"/></h2>
<html:form id="institutionConfiguration" action="/addInstitution.action"
	enctype="multipart/form-data">
	<div class="content form">
		<table>
			<tr>
				<td colspan="2"><html:textfield theme="xhtml"
						key="organisation_name"
						cssClass="inputPage disabled" size="15" readonly="true"/></td>
			</tr>
			<tr>
				<td colspan="2"><html:textfield theme="xhtml"
						key="organisation_path"
						cssClass="inputPage" size="15" /></td>
			</tr>
			<tr>
				<td><html:file theme="xhtml" key="organisation_logo" /></td>
			</tr>
			<tr>
				<td colspan="2"><html:textfield theme="xhtml"
						key="organisation_catalog_url"
						cssClass="inputPage" size="15" /></td>
			</tr>
		</table>
		<html:actionmessage/>
		<html:submit action="addInstitution" property="saveButton"
			cssClass="submitButton buttonGreen" key="edit" />
	</div>	
	<div id="submitButtons" class="buttons row">
		<html:form id="submitButtonsForm">
			<html:submit action="showUsers" property="usersButton"
				cssClass="submitButton button" key="users" />
			<html:submit action="manage" cssClass="submitButton button"
				key="next" />
		</html:form>
	</div>
</html:form>

