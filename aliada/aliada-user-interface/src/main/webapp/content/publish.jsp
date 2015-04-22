<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="html" %>
<script>

</script>

<html:hidden id="rdfizerStatus" name="rdfizerStatus" value="%{#session['rdfizerStatus']}" />

<ul class="breadcrumb">
	<span class="breadCrumb"><html:text name="home"/></span>
	<li><span class="breadcrumb activeGreen"><html:text name="Publicación"/></span></li>
</ul>

<span>Esta es la URL del dataset publicado en CKAN: </span> <a href="<html:property value="ckanDatasetUrl" />" target="_blank">CKAN Dataset URL</a>

<div id="anotherFile">
	<div class="row">
		<html:form>
			<html:submit id="linkingNextButton" action="addAnotherFileWork" cssClass="fleft mediumButton button" key="linking.addNew"/>
		</html:form>
	</div>

</div>