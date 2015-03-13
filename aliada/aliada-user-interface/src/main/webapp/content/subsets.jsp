<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<script>
$(function(){
	var areSubsets = $("#areSubsets").val();	
	var showTheSubset = $("#showTheSubset").val();
	var showAddSubsetForm = $("#showAddSubsetForm").val();	
	var showEditSubsetForm = $("#showEditSubsetForm").val();	
	if(areSubsets != "true"){
		$("#areSubsetsButtons").hide();
	}
	if(showAddSubsetForm == "true"){
		$("#addSubsetPanel").show("slow");
	}
	if(showEditSubsetForm == "true"){
		$("#editSubsetPanel").show("slow");
	}
	if(showTheSubset == "true"){
		$("#theSubsetPanel").show("slow");
	}
	if((showAddSubsetForm == "true") || (showEditSubsetForm == "true") || (showTheSubset == "true")){
		$("#subsetsPanel").hide();		
	}
});
</script>
<html:hidden id="areSubsets" name="areSubsets" value="%{areSubsets}" />
<html:hidden id="showTheSubset" name="showTheSubset" value="%{showTheSubset}" />
<html:hidden id="showAddSubsetForm" name="showAddSubsetForm" value="%{showAddSubsetForm}" />
<html:hidden id="showEditSubsetForm" name="showEditSubsetForm" value="%{showEditSubsetForm}" />

<ul class="breadcrumb">
	<span class="breadCrumb"><html:text name="home"/></span>
	<li><span class="breadcrumb activeGreen"><html:text name="confsubsets"/></span></li>
</ul>

<div class="content">
<html:form id="subsets">
	<div id="subsetsPanel" class="display buttons row">
		<div class="fieldsNoBorder">
			<html:iterator value="subsets">
				<html:radio key="selectedSubset" cssClass="bold lPad10" list="{value}"/><br/>
			</html:iterator>
		</div>
		<html:actionmessage />
		<html:actionerror/>
		<html:submit action="showAddSubsetForm" cssClass="submitButton button"
			key="add" />
		<div id="areSubsetsButtons" class="displayInline">	
			<html:submit action="showTheSubset" cssClass="submitButton button"
				key="see" />
			<html:submit action="showEditSubsetForm" cssClass="submitButton button"
				key="edit" />
			<html:submit action="deleteSubset" cssClass="submitButton button"
				key="delete" />
		</div>
		<html:submit action="showDatasets" cssClass="submitButton button fright"
			key="back" />
	</div>
</html:form>

<html:form id="showSubsetForm" class="row">
	<div id="theSubsetPanel" class="displayNo">
		<div class="row">
			<label class="label"><html:text name="datasetIDForm"/></label>
			<html:property value="datasetNameForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="subsetDescForm"/></label>		
			<html:property value="subsetDescForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="uriConceptPartForm"/></label>
			<html:property value="uriConceptPartForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="graphUriForm"/></label>
			<html:property value="graphUriForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="linksGraphUriForm"/></label>
			<html:property value="linksGraphUriForm" />
		</div>
		<div class="row">
			<label class="label"><html:text name="isqlCommandsFileSubsetForm"/></label>
			<html:property value="isqlCommandsFileSubsetForm" />
		</div>
		<div class="buttons row">
			<html:submit action="showSubsets" cssClass="submitButton button"
				key="back" />
		</div>
	</div>
</html:form>

		<sj:dialog 
	    	id="haveCharConcept" 
	    	openTopics="haveCharConcept"
	    	autoOpen="false" 
	    	title="Debes saber que...">
	    		Puede contener "/" y puede ser un campo vacío.
	    </sj:dialog>	
	
<html:form id="subsetAddForm" class="row">
	<div id="addSubsetPanel" class="displayNo">
		<div class="row label">
			<html:text name="subsetDescForm"/>
			<html:textfield key="subsetDescForm" maxLength="128"  cssClass="inputForm frigth input"/>
		</div>
		<div class="row label">
			<html:text name="uriConceptPartForm"/>
			<sj:a id="conceptDialoglink"
		     	onClickTopics="haveCharConcept">
		    		<img alt="help" src="images/info.png"></img>
	    	</sj:a>
			<html:textfield key="uriConceptPartForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="graphUriForm"/>
			<html:textfield key="graphUriForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="isqlCommandsFileSubsetForm"/>
			<html:textfield key="isqlCommandsFileSubsetForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<html:actionerror/>
		<div class="buttons row">
			<html:submit action="addSubset" cssClass="submitButton button"
				key="save" />
			<html:submit action="showSubsets" cssClass="submitButton button"
				key="cancel" />
		</div>
	</div>
</html:form>

<html:form id="subsetEditForm" class="row">
	<div id="editSubsetPanel" class="displayNo">
		<div class="row label">
			<html:text name="datasetIDForm"/>
			<html:select key="dataID"
							cssClass="inputForm frigth input" list="datasets" />
		</div>
		<div class="row label">
			<html:text name="subsetDescForm"/>
			<html:textfield key="subsetDescForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="uriConceptPartForm"/>
			<html:textfield key="uriConceptPartForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="graphUriForm"/>
			<html:textfield key="graphUriForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<div class="row label">
			<html:text name="isqlCommandsFileSubsetForm"/>
			<html:textfield key="isqlCommandsFileSubsetForm" maxLength="128" cssClass="inputForm frigth input" />
		</div>
		<html:actionerror/>
		<div class="buttons row">
			<html:submit action="editSubset" cssClass="submitButton button"
				key="save" />
			<html:submit action="showSubsets" cssClass="submitButton button"
				key="cancel" />
		</div>
	</div>
</html:form>

</div>