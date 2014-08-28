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
	<div id="managingButtons" class="buttons row">
		<html:form id="managingButtonsForm">
			<html:submit action="errorLog" cssClass="submitButton button"
				key="errorLog" />
			<html:submit action="importXML" cssClass="submitButton button"
				key="import" />
			<div <html:if test="showNextButton">class="displayInline"</html:if>
				<html:else>
				    class="displayNo"
				</html:else>>
				<html:submit action="conversion" cssClass="submitButton button"
					key="next" />
			</div>
		</html:form>
	</div>
</html:form>





