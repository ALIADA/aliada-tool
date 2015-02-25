<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>
<script>
$(function(){
	var areDatasets = $("#areDatasets").val();	
	var showTheDataset = $("#showTheDataset").val();
	var showAddDatasetForm = $("#showAddDatasetForm").val();	
	var showEditDatasetForm = $("#showEditDatasetForm").val();	
	if(areDatasets != "true"){
		$("#areDatasetsButtons").hide();
	}
	if(showAddDatasetForm == "true"){
		$("#addDatasetPanel").show("slow");
	}
	if(showEditDatasetForm == "true"){
		$("#editDatasetPanel").show("slow");
	}
	if(showTheDataset == "true"){
		$("#theDatasetPanel").show("slow");
	}
	if((showAddDatasetForm == "true") || (showEditDatasetForm == "true") || (showTheDataset == "true")){
		$("#datasetsPanel").hide();		
	}
});
</script>
<html:hidden id="areDatasets" name="areDatasets" value="%{areDatasets}" />
<html:hidden id="showTheDataset" name="showTheDataset" value="%{showTheDataset}" />
<html:hidden id="showAddDatasetForm" name="showAddDatasetForm" value="%{showAddDatasetForm}" />
<html:hidden id="showEditDatasetForm" name="showEditDatasetForm" value="%{showEditDatasetForm}" />

<ul class="breadcrumb">
	<span class="breadCrumb"><html:text name="home"/></span>
	<li><span class="breadcrumb activeGreen"><html:text name="confdatasets"/></span></li>
</ul>

<div class="content">
<html:form id="datasets">
	<div id="datasetsPanel" class="display buttons row">
		<div class="fieldsNoBorder">
			<html:iterator value="datasets">
				<html:radio key="selectedDataset" cssClass="bold lPad10" list="{value}"/><br/>
			</html:iterator>
		</div>
		<html:actionmessage />
		<html:actionerror/>
		<html:submit action="showAddDatasetForm" cssClass="submitButton button"
			key="add" />
		<div id="areDatasetsButtons" class="displayInline">	
			<html:submit action="showTheDataset" cssClass="submitButton button"
				key="see" />
			<html:submit action="showEditDatasetForm" cssClass="submitButton button"
				key="edit" />
			<html:submit action="deleteDataset" cssClass="submitButton button"
				key="delete" />
		</div>
		<html:submit action="configure" cssClass="submitButton button fright"
			key="back" />
	</div>
</html:form>

<html:form id="showDatasetForm" class="row">
	<div id="theDatasetPanel" class="displayNo">
	<html:submit id="linksVal" action="showSubsets" cssClass="menuButton button fright" key="subsets" target="_blank"></html:submit>
		<div class="row">
			<label class="label"><html:text name="datasetDescForm"/></label>
			<html:property value="datasetDescForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="domainNameForm"/></label>
			<html:property value="domainNameForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="uriIdPartForm"/></label>
			<html:property value="uriIdPartForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="uriDocPartForm"/></label>
			<html:property value="uriDocPartForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="uriDefPartForm"/></label>
			<html:property value="uriDefPartForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="uriConceptPartForm"/></label>
			<html:property value="uriConceptPartForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="listeningHostForm"/></label>
			<html:property value="listeningHostForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="virtualHostForm"/></label>
			<html:property value="virtualHostForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="sparqlEndpointURIForm"/></label>
			<html:property value="sparqlEndpointURIForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="sparqlEndpointLoginForm"/></label>
			<html:property value="sparqlEndpointLoginForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="sparqlEndpointPasswordForm"/></label>
			<html:property value="sparqlEndpointPasswordForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="publicSparqlEndpointURIForm"/></label>
			<html:property value="publicSparqlEndpointURIForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="datasetAuthorForm"/></label>
			<html:property value="datasetAuthorForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="ckanDatasetNameForm"/></label>
			<html:property value="ckanDatasetNameForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="datasetLongDescForm"/></label>
			<html:property value="datasetLongDescForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="datasetSourceURLForm"/></label>
			<html:property value="datasetSourceURLForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="licenseCkanIdForm"/></label>
			<html:property value="licenseCkanIdForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="licenseURLForm"/></label>
			<html:property value="licenseURLForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="isqlCommandsFileGlobalForm"/></label>
			<html:property value="isqlCommandsFileGlobalForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="isqlCommandsFileSubsetDefaultForm"/></label>
			<html:property value="isqlCommandsFileSubsetDefaultForm" />
		</div>
		<div class="buttons row">
			<html:submit action="showDatasets" cssClass="submitButton button"
				key="back" />
		</div>
	</div>
</html:form>

	
<html:form id="datasetAddForm" class="row">
	<div id="addDatasetPanel" class="displayNo">
		<div class="row label">
			<html:text name="datasetDescForm"/>
			<html:textfield key="datasetDescForm" maxLength="128" cssClass="inputForm frigth input"/>
		</div>
		<div class="row label">
			<html:text name="domainNameForm"/>
			<html:textfield key="domainNameForm" maxLength="128"  cssClass="inputForm frigth input"/>
		</div>
		<div class="row label">
			<html:text name="uriIdPartForm"/>
			<html:textfield key="uriIdPartForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="uriDocPartForm"/>
			<html:textfield key="uriDocPartForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="uriDefPartForm"/>
			<html:textfield key="uriDefPartForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="uriConceptPartForm"/>
			<html:textfield key="uriConceptPartForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="listeningHostForm"/>
			<html:textfield key="listeningHostForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="virtualHostForm"/>
			<html:textfield key="virtualHostForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="sparqlEndpointURIForm"/>
			<html:textfield key="sparqlEndpointURIForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="sparqlEndpointLoginForm"/>
			<html:textfield key="sparqlEndpointLoginForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="sparqlEndpointPasswordForm"/>
			<html:textfield key="sparqlEndpointPasswordForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="publicSparqlEndpointURIForm"/>
			<html:textfield key="publicSparqlEndpointURIForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="datasetAuthorForm"/>
			<html:textfield key="datasetAuthorForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="ckanDatasetNameForm"/>
			<html:textfield key="ckanDatasetNameForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="datasetLongDescForm"/>
			<html:textfield key="datasetLongDescForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="datasetSourceURLForm"/>
			<html:textfield key="datasetSourceURLForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="licenseCkanIdForm"/>
			<html:textfield key="licenseCkanIdForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="licenseURLForm"/>
			<html:textfield key="licenseURLForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="isqlCommandsFileGlobalForm"/>
			<html:textfield key="isqlCommandsFileGlobalForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="isqlCommandsFileSubsetDefaultForm"/>
			<html:textfield key="isqlCommandsFileSubsetDefaultForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="buttons row">
			<html:submit action="addDataset" cssClass="submitButton button"
				key="save" />
			<html:submit action="showDatasets" cssClass="submitButton button"
				key="cancel" />
		</div>
	</div>
</html:form>

<html:form id="datasetEditForm" class="row">
	<div id="editDatasetPanel" class="displayNo">
		<div class="row label">
			<html:text name="datasetDescForm"/>
			<html:textfield key="datasetDescForm" maxLength="128" cssClass="inputForm frigth input"/>
		</div>
		<div class="row label">
			<html:text name="domainNameForm"/>
			<html:textfield key="domainNameForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="uriIdPartForm"/>
			<html:textfield key="uriIdPartForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="uriDocPartForm"/>
			<html:textfield key="uriDocPartForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="uriDefPartForm"/>
			<html:textfield key="uriDefPartForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="uriConceptPartForm"/>
			<html:textfield key="uriConceptPartForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="listeningHostForm"/>
			<html:textfield key="listeningHostForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="virtualHostForm"/>
			<html:textfield key="virtualHostForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="sparqlEndpointURIForm"/>
			<html:textfield key="sparqlEndpointURIForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="sparqlEndpointLoginForm"/>
			<html:textfield key="sparqlEndpointLoginForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="sparqlEndpointPasswordForm"/>
			<html:textfield key="sparqlEndpointPasswordForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="publicSparqlEndpointURIForm"/>
			<html:textfield key="publicSparqlEndpointURIForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="datasetAuthorForm"/>
			<html:textfield key="datasetAuthorForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="ckanDatasetNameForm"/>
			<html:textfield key="ckanDatasetNameForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="datasetLongDescForm"/>
			<html:textfield key="datasetLongDescForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="datasetSourceURLForm"/>
			<html:textfield key="datasetSourceURLForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="licenseCkanIdForm"/>
			<html:textfield key="licenseCkanIdForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="licenseURLForm"/>
			<html:textfield key="licenseURLForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="isqlCommandsFileGlobalForm"/>
			<html:textfield key="isqlCommandsFileGlobalForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="isqlCommandsFileSubsetDefaultForm"/>
			<html:textfield key="isqlCommandsFileSubsetDefaultForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="buttons row">
			<html:submit action="editDataset" cssClass="submitButton button"
				key="save" />
			<html:submit action="showDatasets" cssClass="submitButton button"
				key="cancel" />
		</div>
	</div>
</html:form>

</div>