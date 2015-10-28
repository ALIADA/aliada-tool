<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="/struts-tags"%>

<link type="text/css" rel="stylesheet" href="<html:url value="css/templates.css" />" />

<script>

function confirmBox(){
	var answer = window.confirm("<html:text name='delete.message'/>");
	if (answer == true) {
		$("#templates").submit();
	}
return false;
}

function confirmEditBox(){
	var answer = window.confirm("<html:text name='edit.message'/>");
	if (answer == true) {
		$("#templateEditForm").submit();
	}
return false;
}

$(function(){
	
	// Hide the others tags (Add)
	$("#marcAuthTags").hide();
	$("#lidoTags").hide();
	$("#dcTags").hide();
	
	// Hide the others tags (Edit)
	$("#marcBibTagstoEdit").hide();
	$("#marcAuthTagstoEdit").hide();
	$("#lidoTagstoEdit").hide();
	$("#dcTagstoEdit").hide();
	
	// Hide the others tags (Show)
	$("#marcBibTagstoShow").hide();
	$("#marcAuthTagstoShow").hide();
	$("#lidoTagstoShow").hide();
	$("#dcTagstoShow").hide();
	
	var t = $("#type").val();
	
	if(t == 'B') {
		$("#marcBibTagstoEdit").show();
		$("#marcBibTagstoShow").show();
	} else if (t == 'A') {
		$("#marcAuthTagstoEdit").show();
		$("#marcAuthTagstoShow").show();
	} else if (t == 'L') {
		$("#lidoTagstoEdit").show();
		$("#lidoTagstoShow").show();
	} else if (t == 'D') {
		$("#dcTagstoEdit").show();
		$("#dcTagstoShow").show();
	}
	
	$("#tagsChange").change(function(e){
		// List id value
		var value = $(this).val();
	   	if (value == 0) {
	   		$("#marcBibTags").show();
	   		$("#marcAuthTags").hide();
	   		$("#lidoTags").hide();
	   		$("#dcTags").hide();
	   	}
	   	else if (value == 1) {
	   		$("#marcBibTags").hide();
	   		$("#marcAuthTags").show();
	   		$("#lidoTags").hide();
	   		$("#dcTags").hide();
	   	} else if (value == 2) {
	   		$("#marcBibTags").hide();
	   		$("#marcAuthTags").hide();
	   		$("#lidoTags").show();
	   		$("#dcTags").hide();
	   	} else if (value == 3) {
	   		$("#marcBibTags").hide();
	   		$("#marcAuthTags").hide();
	   		$("#lidoTags").hide();
	   		$("#dcTags").show();
	   	}
	});
	
	$("#tagsChangeEdit").change(function(e){
		// List id value
		var value = $(this).val();
	   	if (value == 0) {
	   		$("#marcBibTagstoEdit").show();
	   		$("#marcAuthTagstoEdit").hide();
	   		$("#lidoTagstoEdit").hide();
	   		$("#dcTagstoEdit").hide();
	   	}
	   	else if (value == 1) {
	   		$("#marcBibTagstoEdit").hide();
	   		$("#marcAuthTagstoEdit").show();
	   		$("#lidoTagstoEdit").hide();
	   		$("#dcTagstoEdit").hide();
	   	} else if (value == 2) {
	   		$("#marcBibTagstoEdit").hide();
	   		$("#marcAuthTagstoEdit").hide();
	   		$("#lidoTagstoEdit").show();
	   		$("#dcTagstoEdit").hide();
	   	} else if (value == 3) {
	   		$("#marcBibTagstoEdit").hide();
	   		$("#marcAuthTagstoEdit").hide();
	   		$("#lidoTagstoEdit").hide();
	   		$("#dcTagstoEdit").show();
	   	}
	});
	
	var type = $("#userType").val();
	if(type == 1) {
		$(".type").show();
	}
	
	var areTemplates = $("#areTemplates").val();	
	var showTheTemplate = $("#showTheTemplate").val();
	var showAddTemplateForm = $("#showAddTemplateForm").val();	
	var showEditTemplateForm = $("#showEditTemplateForm").val();	
	if(areTemplates != "true"){
		$("#areTemplatesButtons").hide();
	}
	if(showAddTemplateForm == "true"){
		$("#addTemplatePanel").show("slow");
	}
	if(showEditTemplateForm == "true"){
		$("#editTemplatePanel").show("slow");
	}
	if(showTheTemplate == "true"){
		$("#theTemplatePanel").show("slow");
	}
	if((showAddTemplateForm == "true") || (showEditTemplateForm == "true") || (showTheTemplate == "true")){
		$("#templatesPanel").hide();		
	}
	
	$("#templates :radio").on("change",function(){
		if($("#templates :radio:checked")){
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
<html:hidden id="areTemplates" name="areTemplates" value="%{areTemplates}" />
<html:hidden id="showTheTemplate" name="showTheTemplate" value="%{showTheTemplate}" />
<html:hidden id="showAddTemplateForm" name="showAddTemplateForm" value="%{showAddTemplateForm}" />
<html:hidden id="showEditTemplateForm" name="showEditTemplateForm" value="%{showEditTemplateForm}" />
<html:hidden id="type" name="type" value="%{t}" />

<div id="templatesPage">

	<ul class="breadcrumb">
		<span class="breadCrumb"><html:text name="home"/></span>
		<li><span class="breadcrumb activeGreen"><html:text name="templates"/></span></li>
	</ul>
	
	<div class="templatesPage">
		
		<div id="templatesPanel" class="display row">
			<html:form id="templates" action="/deleteTemplate.action">
				<div class="lMargin45p">
					<html:iterator value="templates">
						<html:radio key="selectedTemplate" cssClass="bold lPad10" list="value" listKey="key" listValue="value"/>
						<br>
					</html:iterator>
				</div>
				
				<html:actionmessage/>
				<html:actionerror/>
				
				<div id="areTemplatesButtons" class="buttons">
					<html:a action="manage" cssClass="fleft"><img alt="help" src="images/back.png"></img></html:a>
					<html:submit action="showAddTemplateForm"
							cssClass="submitButton button type displayNo" key="add" />
					<html:submit id="seeButton" disabled="true" action="showTheTemplate" cssClass="submitButton button"
							key="see" />	
					<html:submit id="editButton" disabled="true" action="showEditTemplateForm" cssClass="submitButton button type displayNo"
							key="edit" />
					<html:submit id="deleteButton" disabled="true" cssClass="submitButton button type displayNo"
							key="delete" onclick="return confirmBox();"/>
				</div>
			</html:form>
		</div>
		
		<html:form id="showTemplateForm" class="row">
			<div id="theTemplatePanel" class="displayNo">
				<table class="lMargin25p">
					<tr>
						<td><label class="label"><html:text name="templateName" /></label></td>
						<td><html:property value="templateName" /></td>
					</tr>
					<tr>
						<td><label class="label"><html:text name="templateDescription" /></label></td>
						<td><html:property value="templateDescription" /></td>
					</tr>			
					<tr>
						<td><label class="label"><html:text name="fileTypeForm"/></label></td>
						<td><html:property value="fileTypeName" /></td>
					</tr>
				</table>
				<div id="marcBibTagstoShow" class="row">
					<div class="label">
						<html:text name="templateFields" />
					</div>
					
					<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['10']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 1">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['20']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 2">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['30']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 3">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['40']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 4">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
					
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['50']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 5">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['60']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 6">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['70']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 7">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['80']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 8">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['90']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 9">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['100']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 10">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
				</div>
				<div id="marcAuthTagstoShow" class="row">
					<div class="label">
						<html:text name="templateFields" />
					</div>
					
					<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['11']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 1">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['21']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 2">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['31']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 3">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
				</div>
				<div id="lidoTagstoShow" class="row">
					<div class="label">
						<html:text name="templateFields" />
					</div>
					
					<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['12']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 1">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['22']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 2">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['32']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 3">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['42']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 4">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
					
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['52']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 5">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['62']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 6">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
				</div>
				<div id="dcTagstoShow" class="row">
					<div class="label">
						<html:text name="templateFields" />
					</div>
					
					<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['13']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 1">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['23']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 2">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['33']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 3">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['43']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 4">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
					
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['53']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 5">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['63']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 6">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['73']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 7">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['83']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 8">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['93']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 9">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['103']}"/></h3>
						
						<table class="table">
							<html:iterator value="tags" status="status">		
								<html:if test="entity == 10">			
									<html:if test="#counter % 3 == 0">
										<tr>
											<td class="td_size">
												<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
									</html:if>
									<html:else>
										<td class="td_size">
											<html:checkbox disabled="true" key="selectedTags" fieldValue="%{tagId}" value="selected" />
											<html:property value="tagDescription" /></td>
											<html:set var="counter" value="%{#counter+1}"/>
											<%-- <html:property value="#counter" /> --%>
										</td>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
				</div>
				<div class="buttons row pBottom20">
					<html:a action="showTemplates" cssClass="fleft"><img alt="help" src="images/back.png"></img></html:a>
				</div>
			</div>
		</html:form>
	
		<html:form id="templateAddForm" class="row">
			<div id="addTemplatePanel" class="displayNo">
				<table class="lMargin25p">
					<tr>
						<td class="label"><html:text name="templateName" /></td>
						<td><html:textfield key="templateName" maxLength="32" cssClass="inputForm frigth input"/></td>
						<td><span class="red"><html:property
							value="fieldErrors.templateName" /></span></td>
					</tr>
					<tr>
						<td class="label"><html:text name="templateDescription" /></td>
						<td><html:textfield key="templateDescription" maxLength="128"
							cssClass="inputForm frigth input"/></td>
					</tr>
					<tr>
						<td class="label"><html:text name="fileTypeForm"/></td>
						<td><html:select id="tagsChange" key="fileType"
								cssClass="inputForm input" list="types" /></td>
					</tr>
				</table>
				<div id="marcBibTags" class="row">
					<div class="label">
						<div class="label">
							<html:text name="templateFields" />
						</div>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['10']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcBibTags" status="status">		
								<html:if test="entity == 1">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['20']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcBibTags" status="status">		
								<html:if test="entity == 2">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['30']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcBibTags" status="status">		
								<html:if test="entity == 3">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['40']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcBibTags" status="status">		
								<html:if test="entity == 4">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['50']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcBibTags" status="status">		
								<html:if test="entity == 5">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['60']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcBibTags" status="status">		
								<html:if test="entity == 6">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['70']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcBibTags" status="status">		
								<html:if test="entity == 7">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['80']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcBibTags" status="status">		
								<html:if test="entity == 8">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['90']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcBibTags" status="status">		
								<html:if test="entity == 9">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['100']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcBibTags" status="status">		
								<html:if test="entity == 10">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
					</div>
				</div>
				<div id="marcAuthTags" class="row">
					<div class="label">
						<div class="label">
							<html:text name="templateFields" />
						</div>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['11']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcAuthTags" status="status">		
								<html:if test="entity == 1">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['21']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcAuthTags" status="status">		
								<html:if test="entity == 2">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['31']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcAuthTags" status="status">		
								<html:if test="entity == 3">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
					</div>
				</div>
				<div id="lidoTags" class="row">
					<div class="label">
						<div class="label">
							<html:text name="templateFields" />
						</div>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['12']}"/></h3>
						
						<table class="table">
							<html:iterator value="lidoTags" status="status">		
								<html:if test="entity == 1">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['22']}"/></h3>
						
						<table class="table">
							<html:iterator value="lidoTags" status="status">		
								<html:if test="entity == 2">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['32']}"/></h3>
						
						<table class="table">
							<html:iterator value="lidoTags" status="status">		
								<html:if test="entity == 3">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['42']}"/></h3>
						
						<table class="table">
							<html:iterator value="lidoTags" status="status">		
								<html:if test="entity == 4">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['52']}"/></h3>
						
						<table class="table">
							<html:iterator value="lidoTags" status="status">		
								<html:if test="entity == 5">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['62']}"/></h3>
						
						<table class="table">
							<html:iterator value="lidoTags" status="status">		
								<html:if test="entity == 6">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
					</div>
				</div>
				<div id="dcTags" class="row">
					<div class="label">
						<div class="label">
							<html:text name="templateFields" />
						</div>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['13']}"/></h3>
						
						<table class="table">
							<html:iterator value="dcTags" status="status">		
								<html:if test="entity == 1">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['23']}"/></h3>
						
						<table class="table">
							<html:iterator value="dcTags" status="status">		
								<html:if test="entity == 2">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['33']}"/></h3>
						
						<table class="table">
							<html:iterator value="dcTags" status="status">		
								<html:if test="entity == 3">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['43']}"/></h3>
						
						<table class="table">
							<html:iterator value="dcTags" status="status">		
								<html:if test="entity == 4">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['53']}"/></h3>
						
						<table class="table">
							<html:iterator value="dcTags" status="status">		
								<html:if test="entity == 5">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['63']}"/></h3>
						
						<table class="table">
							<html:iterator value="dcTags" status="status">		
								<html:if test="entity == 6">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['73']}"/></h3>
						
						<table class="table">
							<html:iterator value="dcTags" status="status">		
								<html:if test="entity == 7">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['83']}"/></h3>
						
						<table class="table">
							<html:iterator value="dcTags" status="status">		
								<html:if test="entity == 8">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['93']}"/></h3>
						
						<table class="table">
							<html:iterator value="dcTags" status="status">		
								<html:if test="entity == 9">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['103']}"/></h3>
						
						<table class="table">
							<html:iterator value="dcTags" status="status">		
								<html:if test="entity == 10">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
					</div>
				</div>
				<div class="buttons row">
					<html:submit action="addTemplate" cssClass="submitButton button"
						key="save" />
					<html:submit action="showTemplates" cssClass="submitButton button"
						key="cancel" />
				</div>
			</div>
		</html:form>
	
		<html:form id="templateEditForm" class="row" action="/editTemplate.action">
			<div id="editTemplatePanel" class="displayNo">
				<table class="lMargin25p">
					<tr>
						<td class="label"><html:text name="templateName" /></td>
						<td><html:textfield key="templateName" maxLength="128"
						 	cssClass="inputForm frigth input"/></td>
					</tr>
					<tr>
						<td class="label"><html:text name="templateDescription" /></td>
						<td><html:textfield key="templateDescription" maxLength="128"
							cssClass="inputForm frigth input"/></td>
					</tr>
					<tr>
						<td class="label"><html:text name="fileTypeForm"/></td>
						<td><html:select id="tagsChangeEdit" key="fileType"
								cssClass="inputForm input" list="types" /></td>
					</tr>
				</table>
				<div id="marcBibTagstoEdit" class="row">
					<div class="label">
						<div class="label">
							<html:text name="templateFields" />
						</div>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['10']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcBibTags" status="status">		
								<html:if test="entity == 1">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['20']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcBibTags" status="status">		
								<html:if test="entity == 2">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['30']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcBibTags" status="status">		
								<html:if test="entity == 3">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['40']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcBibTags" status="status">		
								<html:if test="entity == 4">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['50']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcBibTags" status="status">		
								<html:if test="entity == 5">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['60']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcBibTags" status="status">		
								<html:if test="entity == 6">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['70']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcBibTags" status="status">		
								<html:if test="entity == 7">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['80']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcBibTags" status="status">		
								<html:if test="entity == 8">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['90']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcBibTags" status="status">		
								<html:if test="entity == 9">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['100']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcBibTags" status="status">		
								<html:if test="entity == 10">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcBibTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
					</div>
				</div>
				<div id="marcAuthTagstoEdit" class="row">
					<div class="label">
						<div class="label">
							<html:text name="templateFields" />
						</div>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['11']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcAuthTags" status="status">		
								<html:if test="entity == 1">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['21']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcAuthTags" status="status">		
								<html:if test="entity == 2">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['31']}"/></h3>
						
						<table class="table">
							<html:iterator value="marcAuthTags" status="status">		
								<html:if test="entity == 3">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedMarcAuthTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
					</div>
				</div>
				<div id="lidoTagstoEdit" class="row">
					<div class="label">
						<div class="label">
							<html:text name="templateFields" />
						</div>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['12']}"/></h3>
						
						<table class="table">
							<html:iterator value="lidoTags" status="status">		
								<html:if test="entity == 1">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['22']}"/></h3>
						
						<table class="table">
							<html:iterator value="lidoTags" status="status">		
								<html:if test="entity == 2">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['32']}"/></h3>
						
						<table class="table">
							<html:iterator value="lidoTags" status="status">		
								<html:if test="entity == 3">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['42']}"/></h3>
						
						<table class="table">
							<html:iterator value="lidoTags" status="status">		
								<html:if test="entity == 4">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['52']}"/></h3>
						
						<table class="table">
							<html:iterator value="lidoTags" status="status">		
								<html:if test="entity == 5">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['62']}"/></h3>
						
						<table class="table">
							<html:iterator value="lidoTags" status="status">		
								<html:if test="entity == 6">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedLidoTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>						
					</div>
				</div>
				<div id="dcTagstoEdit" class="row">
					<div class="label">
						<div class="label">
							<html:text name="templateFields" />
						</div>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['13']}"/></h3>
						
						<table class="table">
							<html:iterator value="dcTags" status="status">		
								<html:if test="entity == 1">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['23']}"/></h3>
						
						<table class="table">
							<html:iterator value="dcTags" status="status">		
								<html:if test="entity == 2">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['33']}"/></h3>
						
						<table class="table">
							<html:iterator value="dcTags" status="status">		
								<html:if test="entity == 3">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['43']}"/></h3>
						
						<table class="table">
							<html:iterator value="dcTags" status="status">		
								<html:if test="entity == 4">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['53']}"/></h3>
						
						<table class="table">
							<html:iterator value="dcTags" status="status">		
								<html:if test="entity == 5">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['63']}"/></h3>
						
						<table class="table">
							<html:iterator value="dcTags" status="status">		
								<html:if test="entity == 6">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['73']}"/></h3>
						
						<table class="table">
							<html:iterator value="dcTags" status="status">		
								<html:if test="entity == 7">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['83']}"/></h3>
						
						<table class="table">
							<html:iterator value="dcTags" status="status">		
								<html:if test="entity == 8">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['93']}"/></h3>
						
						<table class="table">
							<html:iterator value="dcTags" status="status">		
								<html:if test="entity == 9">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
						<html:set var="counter" value="0" />
						<h3><html:property value="%{entities['103']}"/></h3>
						
						<table class="table">
							<html:iterator value="dcTags" status="status">		
								<html:if test="entity == 10">			
									<html:if test="#counter % 3 == 0">
										<tr>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" />
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:if>
									<html:else>
										<html:if test="mandatory == 1">
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" onclick = "this.checked = !this.checked"/>
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:if>
										<html:else>
											<td class="td_size">
												<html:checkbox key="selectedDcTags" fieldValue="%{tagId}" value="selected" />
												<html:property value="tagDescription" /></td>
												<html:set var="counter" value="%{#counter+1}"/>
												<%-- <html:property value="#counter" /> --%>
											</td>
										</html:else>
									</html:else>
								</html:if>
							</html:iterator>
						</table>
						
					</div>
				</div>				
				<div class="buttons row">
					<html:submit cssClass="submitButton button" key="save" onclick="return confirmEditBox();"/>
					<html:submit action="showTemplates" cssClass="submitButton button"
						key="cancel" />
				</div>
			</div>
		</html:form>
	</div>
	
</div>



