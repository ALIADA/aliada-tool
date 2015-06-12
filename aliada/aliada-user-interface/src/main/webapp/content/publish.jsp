<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="html" %>

<link type="text/css" rel="stylesheet" href="<html:url value="css/publish.css" />" />

<script>

</script>

<ul class="breadcrumb">
	<span class="breadCrumb"><html:text name="home"/></span>
	<li><span class="breadcrumb activeGreen"><html:text name="publish.title"/></span></li>
</ul>

<div class="publishPage">
	
	<div class="row label">
		<span><html:text name="publish.message"/></span> <a href="<html:property value="ckanDatasetUrl" />" target="_blank"><html:text name="publish.dataset"/></a>
	</div>
	
	<div id="anotherFile" class="buttons row">
			<html:form>
				<html:submit id="linkingNextButton" action="addAnotherFileWork" cssClass="fleft mediumButton buttonGreen" key="linking.addNew"/>
			</html:form>
	</div>

</div>