<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>

<html:form id="errorLogForm">
	<h2 class="pageTitle"><html:text name="message.error"/></h2>
	<table class="table">
		<tr class="backgroundGreen center">
			<th><html:text name="message.errorId"/></th>
			<th><html:text name="message.errorCode"/></th>
			<th><html:text name="message.errorInfo"/></th>
		</tr>
		<html:iterator value="errorLog" status="error">
			<html:if test="%{#error.index%3 == 0}">
				<tr><td><html:property /></td>
			</html:if>
			<html:else>
				<td><html:property /></td>
			</html:else>
		</html:iterator>
	</table>
	<div id="managingButtons" class="buttons row">
		<html:form id="managingButtonsForm">
			<html:submit action="manage" cssClass="submitButton button"
				key="back" />
		</html:form>
	</div>
</html:form>

