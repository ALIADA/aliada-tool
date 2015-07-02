<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<link type="text/css" rel="stylesheet" href="<html:url value="css/datasets.css" />" />

<script>

function confirmBox(){
	var answer = window.confirm("<html:text name='delete.message'/>");
	if (answer == true) {
		$("#datasets").submit();
	} 
return false;
}

function confirmEditBox(){
	var answer = window.confirm("<html:text name='edit.message'/>");
	if (answer == true) {
		$("#datasetEditForm").submit();
	}
return false;
}

$(function(){
	var showTheDataset = $("#showTheDataset").val();
	var showAddDatasetForm = $("#showAddDatasetForm").val();	
	var showEditDatasetForm = $("#showEditDatasetForm").val();	
	
	if(showAddDatasetForm == "true"){
		$("#addDatasetPanel").show();
	}
	if(showEditDatasetForm == "true"){
		$("#editDatasetPanel").show();
	}
	if(showTheDataset == "true"){
		$("#theDatasetPanel").show();
	}
	if((showAddDatasetForm == "true") || (showEditDatasetForm == "true") || (showTheDataset == "true")){
		$("#datasetsPanel").hide();		
	}
	
	$.subscribe('closeDialogDomName', function(event,data) { 
		setTimeout(function () { $('#domName').dialog("close"); }, 5000);
	});
	
	$.subscribe('closeDialogListHost', function(event,data) { 
		setTimeout(function () { $('#listHost').dialog("close"); }, 5000);
	});
	
	$.subscribe('closeDialogVirtHost', function(event,data) { 
		setTimeout(function () { $('#virtHost').dialog("close"); }, 5000);
	});
	
	$.subscribe('closeDialogNoHaveChar', function(event,data) { 
		setTimeout(function () { $('#noHaveChar').dialog("close"); }, 5000);
	});
	
	$.subscribe('closeDialogNoHaveCharDoc', function(event,data) { 
		setTimeout(function () { $('#noHaveCharDoc').dialog("close"); }, 5000);
	});
	
	$.subscribe('closeDialogHaveCharConcept', function(event,data) { 
		setTimeout(function () { $('#haveCharConcept').dialog("close"); }, 5000);
	});
	
	$.subscribe('closeDialogCkanId', function(event,data) { 
		setTimeout(function () { $('#ckanId').dialog("close"); }, 5000);
	});
	
	$.subscribe('closeDialogLicenseUrl', function(event,data) { 
		setTimeout(function () { $('#licenseUrl').dialog("close"); }, 5000);
	});
	
	$("#datasets :radio").on("change",function(){
		if($("#datasets :radio:checked")){
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
<html:hidden id="areDatasets" name="areDatasets" value="%{areDatasets}" />
<html:hidden id="showTheDataset" name="showTheDataset" value="%{showTheDataset}" />
<html:hidden id="showAddDatasetForm" name="showAddDatasetForm" value="%{showAddDatasetForm}" />
<html:hidden id="showEditDatasetForm" name="showEditDatasetForm" value="%{showEditDatasetForm}" />

<div id="datasetsPage">

<ul class="breadcrumb">
	<span class="breadCrumb"><html:text name="home"/></span>
	<li><span class="breadcrumb activeGreen"><html:text name="confdatasets"/></span></li>
</ul>

<div class="datasetsPage">
	<div id="datasetsPanel" class="display row">
		<html:form id="datasets" action="/deleteDataset.action">
			<div class="lMargin45p">
				<html:iterator value="datasets">
					<html:radio key="selectedDataset" cssClass="bold lPad10" value="{key}" list="{value}"/><br/>
				</html:iterator>
			</div>
			
			<html:actionmessage />
			<html:actionerror/>
			
			<div id="areDatasetsButtons" class="buttons">
				<html:a action="manage" cssClass="fleft"><img alt="help" src="images/back.png"></img></html:a>
				<html:submit action="showAddDatasetForm" cssClass="submitButton button"
						key="add" />
				<html:submit id="seeButton" disabled="true" action="showTheDataset" cssClass="submitButton button"
						key="see" />
				<html:submit id="editButton" disabled="true" action="showEditDatasetForm" cssClass="submitButton button"
						key="edit" />
				<html:submit id="deleteButton" disabled="true" cssClass="submitButton button" key="delete" onclick="return confirmBox();"/>
			</div>
			
		</html:form>
	</div>


<html:form id="showDatasetForm" class="row">
	<div id="theDatasetPanel" class="displayNo">
	<html:submit id="sub" action="showSubsets" cssClass="menuButton button fright" key="subsets" target="_blank"></html:submit>
		<table class="lMargin25p">
			<tr>
				<td class="label"><html:text name="datasetDescForm"/></td>
				<td><html:property value="datasetDescForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="domainNameForm"/></td>
				<td><html:property value="domainNameForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="uriIdPartForm"/></td>
				<td><html:property value="uriIdPartForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="uriDocPartForm"/></td>
				<td><html:property value="uriDocPartForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="uriDefPartForm"/></td>
				<td><html:property value="uriDefPartForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="uriConceptPartForm"/></td>
				<td><html:property value="uriConceptPartForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="uriSetPartForm"/></td>
				<td><html:property value="uriSetPartForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="listeningHostForm"/></td>
				<td><html:property value="listeningHostForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="virtualHostForm"/></td>
				<td><html:property value="virtualHostForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="sparqlEndpointURIForm"/></td>
				<td><html:property value="sparqlEndpointURIForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="sparqlEndpointLoginForm"/></td>
				<td><html:property value="sparqlEndpointLoginForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="sparqlEndpointPasswordForm"/></td>
				<td><html:property value="sparqlEndpointPasswordForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="publicSparqlEndpointURIForm"/></td>
				<td><html:property value="publicSparqlEndpointURIForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="datasetAuthorForm"/></td>
				<td><html:property value="datasetAuthorForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="ckanDatasetNameForm"/></td>
				<td><html:property value="ckanDatasetNameForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="datasetLongDescForm"/></td>
				<td><html:property value="datasetLongDescForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="datasetSourceURLForm"/></td>
				<td><html:property value="datasetSourceURLForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="licenseCkanIdForm"/></td>
				<td><html:property value="licenseCkanIdForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="licenseURLForm"/></td>
				<td><html:property value="licenseURLForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="isqlCommandsFileDatasetForm"/></td>
				<td><html:property value="isqlCommandsFileDatasetForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="datasetWebPageRootForm"/></td>
				<td><html:property value="datasetWebPageRootForm" /></td>
			</tr>
		</table>
		<div class="buttons row pBottom20">
			<html:a action="showDatasets" cssClass="fleft"><img alt="help" src="images/back.png"></img></html:a>
		</div>
	</div>
</html:form>

<div class="displayNo">

		<sj:dialog 
	    	id="domName" 
	    	openTopics="domName"
	    	onOpenTopics="closeDialogDomName"
	    	position="['center', 'top']"
	    	autoOpen="false" 
	    	title="%{title}">
	    		<html:text name="message1"></html:text>
	    </sj:dialog>
	    
	    <sj:dialog 
	    	id="listHost" 
	    	openTopics="listHost"
	    	onOpenTopics="closeDialogListHost"
	    	position="['center', 'top']"
	    	autoOpen="false" 
	    	title="%{title}">
	    		<html:text name="message2"></html:text>
	    </sj:dialog>
		
		<sj:dialog 
	    	id="virtHost" 
	    	openTopics="virtHost"
	    	onOpenTopics="closeDialogVirtHost"
	    	position="['center', 'top']"
	    	autoOpen="false" 
	    	title="%{title}">
	    		<html:text name="message3"></html:text>
	    </sj:dialog>
	    
	    <sj:dialog 
	    	id="noHaveChar" 
	    	openTopics="noHaveChar"
	    	onOpenTopics="closeDialogNoHaveChar"
	    	position="['center', 'top']"
	    	autoOpen="false" 
	    	title="%{title}">
	    		<html:text name="message4"></html:text>
	    </sj:dialog>
	    
	    <sj:dialog 
	    	id="noHaveCharDoc" 
	    	openTopics="noHaveCharDoc"
	    	onOpenTopics="closeDialogNoHaveCharDoc"
	    	position="['center', 'top']"
	    	autoOpen="false" 
	    	title="%{title}">
	    		<html:text name="message5"></html:text>
	    </sj:dialog>
	    
	    <sj:dialog 
	    	id="haveCharConcept" 
	    	openTopics="haveCharConcept"
	    	onOpenTopics="closeDialogHaveCharConcept"
	    	position="['center', 'top']"
	    	autoOpen="false" 
	    	title="%{title}">
	    		<html:text name="message6"></html:text>
	    </sj:dialog>
		
		<sj:dialog 
	    	id="ckanId" 
	    	openTopics="ckanId"
	    	onOpenTopics="closeDialogCkanId"
	    	position="['center', 'top']"
	    	autoOpen="false" 
	    	title="%{title}">
	    		<html:text name="message7"></html:text>  <html:a href="http://opendefinition.org/licenses/" target="_blank"><img alt="help" src="images/link.png"></img></html:a>	
	    </sj:dialog>
	    
	    <sj:dialog 
	    	id="licenseUrl" 
	    	openTopics="licenseUrl"
	    	onOpenTopics="closeDialogLicenseUrl"
	    	position="['center', 'top']"
	    	autoOpen="false" 
	    	title="%{title}">
	    		<html:text name="message8"></html:text>  <html:a href="https://creativecommons.org/licenses/" target="_blank"><img alt="help" src="images/link.png"></img></html:a>  	
	    </sj:dialog>
</div>
	
<html:form id="datasetAddForm" class="row">
	<div id="addDatasetPanel" class="displayNo">
		<table class="lMargin25p">
			<tr>
				<td class="label"><html:text name="datasetDescForm"/></td>
				<td><html:textfield key="datasetDescForm" maxLength="128" cssClass="inputForm frigth input"/></td>
			</tr>
			<tr>
				<td class="label">
					<sj:a id="domainNameDialoglink" onClickTopics="domName">
			    		<img alt="help" src="images/info.png"></img>
		    		</sj:a>
		    		<html:text name="domainNameForm"/>
		    	</td>
				<td><html:textfield key="domainNameForm" maxLength="128"  cssClass="inputForm frigth input"/></td>
			</tr>
			<tr>
				<td class="label">
					<sj:a id="idDialoglink"
				     	onClickTopics="noHaveChar">
				    		<img alt="help" src="images/info.png"></img>
			    	</sj:a>
					<html:text name="uriIdPartForm"/>
				</td>
				<td><html:textfield key="uriIdPartForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label">
					<sj:a id="docDialoglink"
				     	onClickTopics="noHaveCharDoc">
				    		<img alt="help" src="images/info.png"></img>
			    	</sj:a>
					<html:text name="uriDocPartForm"/>
				</td>
				<td><html:textfield key="uriDocPartForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label">
					<sj:a id="defDialoglink"
				     	onClickTopics="noHaveChar">
				    		<img alt="help" src="images/info.png"></img>
			    	</sj:a>
					<html:text name="uriDefPartForm"/>
				</td>
				<td><html:textfield key="uriDefPartForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label">
					<sj:a id="conceptDialoglink"
				     	onClickTopics="haveCharConcept">
				    		<img alt="help" src="images/info.png"></img>
			    	</sj:a>
					<html:text name="uriConceptPartForm"/>
				</td>
				<td><html:textfield key="uriConceptPartForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label">
					<sj:a id="setDialoglink"
				     	onClickTopics="noHaveChar">
				    		<img alt="help" src="images/info.png"></img>
			    	</sj:a>
					<html:text name="uriSetPartForm"/>
				</td>
				<td><html:textfield key="uriSetPartForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label">
					<sj:a id="listeningHostDialoglink"
				     	onClickTopics="listHost">
				    		<img alt="help" src="images/info.png"></img>
			    	</sj:a>
					<html:text name="listeningHostForm"/>
				</td>
				<td><html:textfield key="listeningHostForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label">
					<sj:a id="virtualHostDialoglink"
				     	onClickTopics="virtHost">
				    		<img alt="help" src="images/info.png"></img>
			    	</sj:a>
					<html:text name="virtualHostForm"/>
				</td>
				<td><html:textfield key="virtualHostForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="sparqlEndpointLoginForm"/>
				<td><html:textfield key="sparqlEndpointLoginForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="sparqlEndpointPasswordForm"/>
				<td><html:textfield key="sparqlEndpointPasswordForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="ckanDatasetNameForm"/>
				<td><html:textfield key="ckanDatasetNameForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="datasetLongDescForm"/>
				<td><html:textfield key="datasetLongDescForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="datasetSourceURLForm"/>
				<td><html:textfield key="datasetSourceURLForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label">
					<sj:a id="licenseCkanDialoglink"
				     	onClickTopics="ckanId">
				    		<img alt="help" src="images/info.png"></img>
			    	</sj:a>
			    	<html:text name="licenseCkanIdForm"/>
			    </td>
				<td><html:textfield key="licenseCkanIdForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label">
					<sj:a id="licenseUrlDialoglink"
				     	onClickTopics="licenseUrl">
				    		<img alt="help" src="images/info.png"></img>
			    	</sj:a>
			    	<html:text name="licenseURLForm"/>
				<td><html:textfield key="licenseURLForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="isqlCommandsFileDatasetForm"/></td>
				<td><html:textfield key="isqlCommandsFileDatasetForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
		</table>
		<html:actionerror/>
		<div class="buttons row">
			<html:submit action="addDataset" cssClass="submitButton button"
				key="save" />
			<html:submit action="showDatasets" cssClass="submitButton button"
				key="cancel" />
		</div>
	</div>
</html:form>

<html:form id="datasetEditForm" class="row" action="/editDataset.action">
	<div id="editDatasetPanel" class="displayNo">
		<table class="lMargin25p">
			<tr>
				<td class="label"><html:text name="datasetDescForm"/></td>
				<td><html:textfield key="datasetDescForm" maxLength="128" cssClass="inputForm frigth input"/></td>
			</tr>
			<tr>
				<td class="label"><html:text name="domainNameForm"/></td>
				<td><html:textfield key="domainNameForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="uriIdPartForm"/></td>
				<td><html:textfield key="uriIdPartForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="uriDocPartForm"/></td>
				<td><html:textfield key="uriDocPartForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="uriDefPartForm"/></td>
				<td><html:textfield key="uriDefPartForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="uriConceptPartForm"/></td>
				<td><html:textfield key="uriConceptPartForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="uriSetPartForm"/></td>
				<td><html:textfield key="uriSetPartForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="listeningHostForm"/></td>
				<td><html:textfield key="listeningHostForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="virtualHostForm"/></td>
				<td><html:textfield key="virtualHostForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="sparqlEndpointLoginForm"/></td>
				<td><html:textfield key="sparqlEndpointLoginForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="sparqlEndpointPasswordForm"/></td>
				<td><html:textfield key="sparqlEndpointPasswordForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="ckanDatasetNameForm"/></td>
				<td><html:textfield key="ckanDatasetNameForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="datasetLongDescForm"/></td>
				<td><html:textfield key="datasetLongDescForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="datasetSourceURLForm"/></td>
				<td><html:textfield key="datasetSourceURLForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="licenseCkanIdForm"/></td>
				<td><html:textfield key="licenseCkanIdForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="licenseURLForm"/></td>
				<td><html:textfield key="licenseURLForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="isqlCommandsFileDatasetForm"/></td>
				<td><html:textfield key="isqlCommandsFileDatasetForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
		</table>
		<html:actionerror/>
		<div class="buttons row">
			<html:submit cssClass="submitButton button" key="save" onclick="return confirmEditBox();"/>
			<html:submit action="showDatasets" cssClass="submitButton button"
				key="cancel" />
		</div>
	</div>
</html:form>

	</div>
</div>