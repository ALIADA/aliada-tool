<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>
<script type="text/javascript" charset="UTF-8">	

</script>

	<div id="institutionPage">
			<ul class="breadcrumb">
				<span class="breadCrumb"><html:text name="home"/></span>
				<li><span class="breadcrumb activeGreen"><html:text name="organisation.title"/></span></li>
			</ul>
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
								<td colspan="2"><html:file theme="xhtml" cssClass="inputPage" key="organisationLogo" /></td>
							</tr>
							<tr>
								<td colspan="2"><html:textfield theme="xhtml"
										key="organisationCatalogUrl"
										cssClass="inputPage" maxLength="128" /></td>
							</tr>
						</table>
						
						<html:if test="tab==0">
						      <html:actionmessage/>
						</html:if>
						<html:if test="tab==0">
						        <html:actionerror/>
						</html:if>
						
						<html:submit action="editInstitution" property="saveButton"
							cssClass="lMargin40 submitButton buttonGreen" key="save" />
					</div>	
				</html:form>
			</div>