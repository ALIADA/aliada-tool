<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="html" %>
<h2 class="pageTitle"><html:text name="manage.title"/></h2>
<html:form id="managing" method="post" enctype="multipart/form-data">
	<div class="content">
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
			<div <html:if test="enableErrorLogButton">class="displayInline"</html:if>
				<html:else>
				    class="displayNo"
				</html:else>>
				<html:submit action="errorLog" cssClass="submitButton button"
					key="errorLog" />
			</div>	
			<html:submit id="nextButton" action="conversion" cssClass="displayNo submitButton button"
				key="next" />
			<html:if test="state>=1">
				<script>
			    	$("#importFileButton").removeClass("buttonGreen");
			    	$("#importFileButton").addClass("button");
			    	$("#nextButton").removeClass("button");
			    	$("#nextButton").addClass("buttonGreen");
			    	$("#nextButton").show("slow");
			    </script>
			</html:if>
		</html:form>
	</div>
</html:form>





