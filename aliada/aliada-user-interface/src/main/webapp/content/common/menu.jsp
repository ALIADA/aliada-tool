<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="html" %>
<div id="menu">
	<html:form id="menuForm">
		<html:submit  action="configure" cssClass="menuButton button" key="menu.configure"/>
		<html:submit  action="manage" cssClass="menuButton button" key="menu.publish"/>
		<html:submit  action="configure" cssClass="menuButton button" key="menu.update"/>
		<html:submit  action="linking" cssClass="menuButton button" key="menu.addLinks"/>
		<html:submit  action="logout" cssClass="menuButton button" key="logOut"/>
	</html:form>
</div>