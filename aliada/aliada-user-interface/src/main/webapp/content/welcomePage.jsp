<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>

<h2><html:text name="welcomeAliada"/></h2>
<html:form id="welcomePages" cssClass="welcomeLinks bold">
	<html:submit action="configure" cssClass="button menuButton" key="menu.configure"/>
	<html:submit action="manage" cssClass="button menuButton" key="menu.import"/>
	<html:submit id="conversionMenu" action="conversion" cssClass="button menuButton" key="menu.conversion" disabled="true"/>
	<html:submit id="linkingMenu" action="linking" cssClass="menuButton button" key="menu.discovery" disabled="true"/>
	<html:submit id="ldsMenu" action="ldsInfo" cssClass="menuButton button" key="menu.lds" disabled="true"/>
	<html:submit  action="logout" cssClass="menuButton button" key="logOut"/>
</html:form>
