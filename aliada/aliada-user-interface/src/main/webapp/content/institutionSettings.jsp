<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>

<script>

function confirmBox(){
	var answer = window.confirm("<html:text name='¿editInstitution guardar?'/>");
	if (answer == true){
		
	}
	else{
		console.log("Don´t save");
		window.location = "${pageContext.request.contextPath}/editInstitution.action";
	}
    return false;
}

</script>

<ul class="breadcrumb">
	<span class="breadCrumb"><html:text name="home"/></span>
	<li><span class="breadcrumb activeGreen"><html:text name="organisation.title"/></span></li>
</ul>
<div id="institutionPage">
	<html:form id="institutionConfiguration" action="/editInstitution.action"
		enctype="multipart/form-data">
	<div class="content form">
		<table class="pad10">
			<tr>
				<td colspan="2"><html:textfield theme="xhtml"
						key="organisationName"
						cssClass="inputPage disabled" readonly="true"/></td>
			</tr>
			<tr>
				<td><html:file theme="xhtml" key="organisationLogo" /></td>
			</tr>
			<tr>
				<td colspan="2"><html:textfield theme="xhtml"
						key="organisationCatalogUrl"
						cssClass="inputPage" maxLength="128" /></td>
			</tr>
		</table>
		<html:actionmessage/>
		<html:submit action="editInstitution" property="saveButton"
			cssClass="lMargin40 submitButton buttonGreen" key="save" /> 
		<%-- <html:submit onclick="return confirmBox();" property="saveButton"
			cssClass="lMargin40 submitButton buttonGreen" key="save" /> --%>
	</div>	
	<div id="submitButtons" class="buttons row">
		<html:submit action="showProfiles"
			cssClass="submitButton button" key="profilesSubmit" />
		<html:submit action="showTemplates" cssClass="submitButton button"
			key="templates" />
		<html:submit action="showUsers" property="usersButton"
			cssClass="submitButton button" key="users" />
		<%-- <html:submit action="manage" cssClass="fright submitButton button"
			key="next" /> --%>
		<html:submit action="manage" cssClass="submitButton button fright"
			key="back" />
	</div>
	</html:form>
</div>

