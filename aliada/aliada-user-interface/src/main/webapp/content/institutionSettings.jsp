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

<%--<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
  <head>
    <sj:head />
  </head>
  <body>
    <sj:tabbedpanel id="localtabs" cssClass="list">
                             <sj:tab id="tab1" target="tone" label="Local Tab One"/>
                             <sj:tab id="tab2" target="submitButtons" label="Buttons"/>
                             <sj:tab id="tab3" target="tthree" label="Local Tab Three"/>
                             <sj:tab id="tab4" target="tfour" label="Local Tab Four"/>
                             <div id="tone">Mauris mauris ante, blandit et, ultrices a, suscipit eget, quam. Integer ut neque. Vivamus nisi
                                 metus, molestie vel, gravida in, condimentum sit amet, nunc. Nam a nibh. Donec suscipit eros. Nam mi. Proin
                                 viverra leo ut odio. Curabitur malesuada. Vestibulum a velit eu ante scelerisque vulputate.
                             </div>
                             <div id="submitButtons" class="buttons row">
    							
								<html:form id="form" action="showProfiles">
								<sj:submit
									cssClass="submitButton button" key="profilesSubmit" />
								</html:form>
								
								<html:submit action="showTemplates" cssClass="submitButton button"
									key="templates" />
								<html:submit action="showUsers" property="usersButton"
									cssClass="submitButton button" key="users" />
								<html:submit action="showDatasets" cssClass="submitButton button"
									key="datasets" />
								<html:submit action="showExternalDatasets" cssClass="submitButton button"
									key="datasetsReload" />
								<html:submit action="manage" cssClass="submitButton button fright"
									key="back" />
							</div>
                             <div id="tthree">Nam enim risus, molestie et, porta ac, aliquam ac, risus. Quisque lobortis. Phasellus pellentesque
                                 purus in massa. Aenean in pede. Phasellus ac libero ac tellus pellentesque semper. Sed ac felis. Sed commodo,
                                 magna quis lacinia ornare, quam ante aliquam nisi, eu iaculis leo purus venenatis dui.
                             </div>
                             <div id="tfour">Cras dictum. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis
                                 egestas. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aenean lacinia
                                 mauris vel est. Suspendisse eu nisl. Nullam ut libero. Integer dignissim consequat lectus. Class aptent taciti
                                 sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.
                             </div>
                         </sj:tabbedpanel>
  </body>
</html>--%>

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
		<html:submit action="showDatasets" cssClass="submitButton button"
			key="datasets" />
		<html:submit action="showExternalDatasets" cssClass="submitButton button"
			key="datasetsReload" />
		<html:submit action="manage" cssClass="submitButton button fright"
			key="back" />
	</div>
	</html:form>
</div>

