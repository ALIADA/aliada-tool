<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="html" %>

<link type="text/css" rel="stylesheet" href="<html:url value="css/manage.css" />" />

<script>
$(function(){
	var areFiles = $("#areFiles").val();
	var enableErrorLogButton= $("#enableErrorLogButton").val();
	if(enableErrorLogButton==1){
		$("#importFileButton").removeClass("buttonGreen");
		$("#importFileButton").addClass("button");
		$("#errorLog").removeClass("button");
		$("#errorLog").addClass("buttonGreen");
		$("#errorLog").prop( "disabled", false);	
	}
	else{
		$("#errorLog").removeClass("buttonGreen");
		$("#errorLog").addClass("button");
		$("#errorLog").prop( "disabled", true);
	}

	if(areFiles > 0){
		$("#importedFilesTable").show();
		$("#nextButton").show();
	}
	else {
		$(".table").hide();
	}
	
	$("#importedFilesTable :radio").on("change",function(){
		if($("#importedFilesTable :radio:checked")){
			$("#nextButton").show();				
		} else {
			$("#nextButton").hide();				
		}
	}); 
});
</script>

<!-- Si se guarda en session -->
<html:hidden id="areFiles" name="areFiles" value="%{#session['importedFiles'].size()}" />
<html:hidden id="enableErrorLogButton" name="enableErrorLogButton" value="%{enableErrorLogButton}" />

<ul class="breadcrumb">
	<span><html:text name="home"/></span>
	<li><span class="activeGreen"><html:text name="manage.title"/></span></li>
	<li><span ><html:text name="conversion.title"/></span></li>
	<li><span ><html:text name="linking.title"/></span></li>
	<html:a id="rdfVal" action="getAuthors" cssClass="menuButton button fright" key="rdfVal" target="_blank"><html:text name="rdfVal"/></html:a>
</ul>

<div id=import>
	<div id="importDividedPanel" class="clearfixImport">
	
		<div id="importPanel" class="fleft" >
			<div id="importedFil" class="filesContent">
				<html:form id="managing" method="post" enctype="multipart/form-data">
					<label class="label row center"><html:text name="manage.process"/></label>
					<div class="row center">
						<html:select name="profileSelected" cssClass="inputForm"
							list="profiles" />
						<html:submit action="showProfiles"
							cssClass="submitButton button" key="profilesSubmit" />
					</div>
					<div class="label row center">
						<html:text name="importFile"/>
					</div>
					<div class="label row center">
						<html:file key="importFile" />
					</div>
					<html:fielderror fieldName="importFile" />
					<html:actionerror/>
					<%--<html:actionmessage />--%>
					<div id="managingButtons" class="buttons row">
						<img id=loader class="displayNo lMargin40 rMargin20" src="images/loader.gif" alt="" />
						<html:submit id="importFileButton" action="importXML" cssClass="mediumButton buttonGreen"
							key="import" onClick="$('#loader').show();
													$('#importFileButton').hide();" />
						<html:submit id="errorLog" action="errorLog" disabled="true" cssClass="mediumButton button"
							key="errorLog" />
					</div>	
				</html:form>
			</div>
		</div>	

<html:form>		
		<div id="listPanel" class="fleft" >
			<div id="importedFilesTable" class="filesContent displayNo">
				<label class="label row center"><html:text name="manage.selectFile"/></label>
				<div id="importFiles" class="contentTable scrollifyAuto">
					<table id="files" class="tableFiles">
						<tr class="backgroundGreen center">
							<!-- <th></th> -->
							<th><label class="bold"><html:text name="filename"/></label></th>
							<th><label class="bold"><html:text name="profile"/></label></th>
							<th><label class="bold"><html:text name="status"/></label></th>
						</tr>
						<html:iterator value="importedFiles" var="dato">
							<tr>
								<%-- <td><html:checkbox name="fileChecked" value="fileChecked"/></td> --%>
								<%-- <td><html:property value="filename" /></td> --%>
								<td class="radio"><html:radio key="selectedFile" list="filename" checked="checked"/></td>
								<html:if test="profile == 1">
									<td><html:text name="MARC BIB"/></td>
								</html:if>
								<html:elseif test="profile == 2">
									<td><html:text name="MARC AUT"/></td>
								</html:elseif>
								<html:elseif test="profile == 3">
									<td><html:text name="LIDO"/></td>
								</html:elseif>
								<html:elseif test="profile == 4">
									<td><html:text name="DC"/></td>
								</html:elseif>
								<td class="center">
									<html:if test="status.equals('idle')">
										<img class="displayNo" src="images/fine.png"/>
										<img src="images/clock.png"/>
									</html:if>
									<html:else>
										<img class="displayNo" src="images/clock.png"/>
										<img src="images/fine.png"/>
									</html:else>
								</td>
							</tr>
						</html:iterator>
					</table>
				</div>
			</div>
		</div>
				
	</div>
	<div id="submitButtons" class="butt row">
		<html:submit id="nextButton" action="saveFilesToConversion" cssClass="displayNo fright mediumButton buttonGreen" key="next" />
	</div>
</div>
</html:form>



