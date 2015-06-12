<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<link type="text/css" rel="stylesheet" href="<html:url value="css/subsets.css" />" />

<script>

function confirmBox(){
	var answer = window.confirm("<html:text name='delete.message'/>");
	if (answer == true) {
		console.log("Remove subset");
		$("#subsets").submit();
	} else {
		console.log("Remove subset cancel");
	}
return false;
}

function confirmEditBox(){
	var answer = window.confirm("<html:text name='edit.message'/>");
	if (answer == true) {
		console.log("Edit subset");
		$("#subsetEditForm").submit();
	} else {
		console.log("Edit subset cancel");
	}
return false;
}

$(function(){
	var showTheSubset = $("#showTheSubset").val();
	var showAddSubsetForm = $("#showAddSubsetForm").val();	
	var showEditSubsetForm = $("#showEditSubsetForm").val();	
	
	if(showAddSubsetForm == "true"){
		$("#addSubsetPanel").show();
	}
	if(showEditSubsetForm == "true"){
		$("#editSubsetPanel").show();
	}
	if(showTheSubset == "true"){
		$("#theSubsetPanel").show();
	}
	if((showAddSubsetForm == "true") || (showEditSubsetForm == "true") || (showTheSubset == "true")){
		$("#subsetsPanel").hide();		
	}
	
	$("#subsets :radio").on("change",function(){
		if($("#subsets :radio:checked")){
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

$.subscribe('closeDialog', function(event,data) { 
	setTimeout(function () { $('#haveCharConcept').dialog("close"); }, 5000);
});

</script>
<html:hidden id="areSubsets" name="areSubsets" value="%{areSubsets}" />
<html:hidden id="showTheSubset" name="showTheSubset" value="%{showTheSubset}" />
<html:hidden id="showAddSubsetForm" name="showAddSubsetForm" value="%{showAddSubsetForm}" />
<html:hidden id="showEditSubsetForm" name="showEditSubsetForm" value="%{showEditSubsetForm}" />

<div id="subsetsPage">

<ul class="breadcrumb">
	<span class="breadCrumb"><html:text name="home"/></span>
	<li><span class="breadcrumb activeGreen"><html:text name="confsubsets"/></span></li>
</ul>

<div class="subsetsPage">

<div id="subsetsPanel" class="display row">
	<html:form id="subsets" action="/deleteSubset.action">
		<div id="list" class="lMargin45p">
			<html:iterator value="subsets">
				<html:radio key="selectedSubset" cssClass="bold lPad10" value="{key}" list="{value}"/><br/>
			</html:iterator>
		</div>
		
		<html:actionmessage />
		<html:actionerror/>
			
		<div id="areSubsetsButtons" class="buttons">
			<html:a action="showDatasets" cssClass="fleft"><img alt="help" src="images/back.png"></img></html:a>
			<html:submit action="showAddSubsetForm" cssClass="submitButton button"
					key="add" />
			<html:submit id="seeButton" disabled="true" action="showTheSubset" cssClass="submitButton button"
					key="see" />
			<html:submit id="editButton" disabled="true" action="showEditSubsetForm" cssClass="submitButton button"
					key="edit" />
			<html:submit id="deleteButton" disabled="true" cssClass="submitButton button" key="delete" onclick="return confirmBox();"/>
		</div>
	</html:form>	
</div>


<html:form id="showSubsetForm" class="row">
	<div id="theSubsetPanel" class="displayNo">
		<table class="lMargin25p">
			<tr>
				<td class="label"><html:text name="datasetIDForm"/></td>
				<td><html:property value="datasetNameForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="subsetDescForm"/></td>		
				<td><html:property value="subsetDescForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="uriConceptPartForm"/></td>
				<td><html:property value="uriConceptPartForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="graphUriForm"/></td>
				<td><html:property value="graphUriForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="linksGraphUriForm"/></td>
				<td><html:property value="linksGraphUriForm" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="isqlCommandsFileSubsetForm"/></td>
				<td><html:property value="isqlCommandsFileSubsetForm" /></td>
			</tr>
		</table>
		<div class="buttons row pBottom20">
			<html:a action="showSubsets" cssClass="fleft"><img alt="help" src="images/back.png"></img></html:a>
		</div>
	</div>
</html:form>

<div class="displayNo">
	<sj:dialog 
    	id="haveCharConcept" 
    	openTopics="haveCharConcept"
	   	onOpenTopics="closeDialog"
	   	position="['center', 'top']"
	    autoOpen="false" 
	    title="%{title}">
	    <html:text name="message"></html:text>
	</sj:dialog>
</div>	
	
<html:form id="subsetAddForm" class="row">
	<div id="addSubsetPanel" class="displayNo">
		<table class="lMargin25p">
			<tr>
				<td class="label"><html:text name="subsetDescForm"/></td>
				<td><html:textfield key="subsetDescForm" maxLength="128"  cssClass="inputForm frigth input"/></td>
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
				<td class="label"><html:text name="graphUriForm"/></td>
				<td><html:textfield key="graphUriForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="isqlCommandsFileSubsetForm"/></td>
				<td><html:textfield key="isqlCommandsFileSubsetForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
		</table>
		<html:actionerror/>
		<div class="buttons row">
			<html:submit action="addSubset" cssClass="submitButton button"
				key="save" />
			<html:submit action="showSubsets" cssClass="submitButton button"
				key="cancel" />
		</div>
	</div>
</html:form>

<html:form id="subsetEditForm" class="row" action="/editSubset.action">
	<div id="editSubsetPanel" class="displayNo">
		<table class="lMargin25p">
			<tr>
				<td class="label"><html:text name="datasetIDForm"/></td>
				<td><html:select key="dataID" cssClass="inputForm frigth input" list="datasets" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="subsetDescForm"/></td>
				<td><html:textfield key="subsetDescForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="uriConceptPartForm"/></td>
				<td><html:textfield key="uriConceptPartForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="graphUriForm"/></td>
				<td><html:textfield key="graphUriForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
			<tr>
				<td class="label"><html:text name="isqlCommandsFileSubsetForm"/></td>
				<td><html:textfield key="isqlCommandsFileSubsetForm" maxLength="128" cssClass="inputForm frigth input" /></td>
			</tr>
		</table>
		<html:actionerror/>
		<div class="buttons row">
			<html:submit cssClass="submitButton button" key="save" onclick="return confirmEditBox();"/>
			<html:submit action="showSubsets" cssClass="submitButton button"
				key="cancel" />
		</div>
	</div>
</html:form>

	</div>
</div>