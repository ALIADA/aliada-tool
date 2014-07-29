<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="html" %>
<div id="menu">
	<html:form id="menuForm">
		<html:submit  action="configure" cssClass="menuButton button" value="Configure your institution"/>
		<html:submit  action="manage" cssClass="menuButton button" value="Add Files to publish in the LDC"/>
		<html:submit  action="configure" cssClass="menuButton button" value="Update published dataset"/>
		<html:submit  action="linking" cssClass="menuButton button" value="Add links to external dataset"/>
	</html:form>
</div>