<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="html" %>
<ul class="breadcrumb">
	<li><html:a action="configure" cssClass="breadcrumb"><html:text name="organisation.title"/></html:a></li>
	<li><html:a action="manage" cssClass="breadcrumb activeGreen"><html:text name="manage.title"/></html:a></li>
</ul>
<h2 class="pageTitle center"><html:text name="manage.title"/></h2>
<div class="form centered">
	<html:form id="managing" method="post" enctype="multipart/form-data">
	<div id="managingPanel" class="content">
		<html:text name="importFile"/>
		<html:file key="importFile" />
		<html:fielderror fieldName="importFile" />
		<div class="row"></div>
		<label class="row label"><html:text name="manage.process"/></label>
		<html:select name="profilesSelect" cssClass="inputForm"
			list="profiles" />
		<html:submit action="showProfiles"
			cssClass="submitButton button" key="profilesSubmit" />
	</div>
	<html:actionerror/>
	<html:actionmessage />
	<div id="managingButtons" class="buttons row">
		<html:form id="managingButtonsForm">
			<img id=loader class="displayNo leftMargin" src="images/loader.gif" alt="" />
			<html:submit id="importFileButton" action="importXML" cssClass="submitButton buttonGreen"
				key="import" onClick="$('#loader').show();
										$('#importFileButton').hide();" />
			<html:submit id="errorLog" action="errorLog" disabled="true" cssClass="submitButton button"
				key="errorLog" />
			<html:submit id="nextButton" action="conversion" disabled="true" cssClass="submitButton button"
				key="next" />
			<html:if test=fileImported">
				<script>
			    	$("#importFileButton").removeClass("buttonGreen");
			    	$("#importFileButton").addClass("button");
			    	$("#nextButton").removeClass("button");
			    	$("#nextButton").addClass("buttonGreen");
		    		$("#nextButton").prop( "disabled", false);	
			    </script>
			</html:if>
			<html:if test="enableErrorLogButton">
				<script>
			    	$("#importFileButton").removeClass("buttonGreen");
			    	$("#importFileButton").addClass("button");
			    	$("#errorLog").removeClass("button");
			    	$("#errorLog").addClass("buttonGreen");
		    		$("#errorLog").prop( "disabled", false);	
			    	$("#errorLog").show("slow");
			    </script>
			</html:if>
		</html:form>
	</div>
	</html:form>
</div>





