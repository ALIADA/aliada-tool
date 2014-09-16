<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="html" %>
<div id="menu">
	<html:form id="welcomePages" cssClass="welcomeLinks bold">
		<html:submit action="configure" cssClass="button menuButton" key="menu.configure"/>
		<html:submit action="manage" cssClass="button menuButton" key="menu.import"/>
		<html:submit id="conversionMenu" action="conversion" cssClass="button menuButton" key="menu.conversion" disabled="true"/>
		<html:submit id="linkingMenu" action="linking" cssClass="menuButton button" key="menu.discovery" disabled="true"/>
		<html:submit id="ldsMenu" action="ldsInfo" cssClass="menuButton button" key="menu.lds" disabled="true"/>
		<html:submit  action="logout" cssClass="menuButton buttonGreen" key="logOut"/>
		<html:if test="state>=1">
			<script>
		    	$("#conversionMenu").prop( "disabled", false);
		    </script>
		</html:if>
		<html:if test="state>1">
			<script>
		    	$("#linkingMenu").prop( "disabled", false);
		    	$("#ldsMenu").prop( "disabled", false);
		    </script>
		</html:if>
	</html:form>
</div>