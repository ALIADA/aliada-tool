<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="html"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<link type="text/css" rel="stylesheet" href="<html:url value="css/layout.css" />" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

	<head>
	    <title>Aliada</title>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
	    <meta http-equiv="Content-Script-Type" content="text/javascript; charset=UTF-8"/>
	    <link rel="stylesheet" href="css/aliadaStyles.css" type="text/css"/>
	    <link rel="shortcut icon" href="images/aliada.ico"/>
	    <sj:head/>	
	    <html:head/>
	</head>
	
	<body>
		<div id="settings">
			<%-- <span class="lMargin500">
				<html:url id="localeEN" namespace="/">
				   <html:param name="request_locale" >en</html:param>
				</html:url>
				<html:url id="localeES" namespace="/" >
				   <html:param name="request_locale" >es</html:param>
				</html:url>			 
				<html:a href="%{localeEN}" ><img src="images/gbFlag.png"/></html:a>
				<html:a href="%{localeES}" ><img src="images/spFlag.png"/></html:a>
			</span> --%>
			<div id="header">
				<img class="fleft" src="images/aliada-header.png"/>
				<ul class="nav fright">
					<li class="insti bold"><html:property value="%{#session['inst']}" /></li>
					<li><a> <html:text name="conf"/> <img alt="control panel" src="images/config.png"></img> </a>
						<ul>
							<li><html:a action="showInstitution"> <html:text name="ins"/> </html:a></li>
							<li><html:a action="showProfiles"> <html:text name="prof"/> </html:a></li>
							<li><html:a action="showTemplates"> <html:text name="temp"/> </html:a></li>
							<li><html:a action="showUsers"> <html:text name="us"/> </html:a></li>
							<li><html:a action="showDatasets"> <html:text name="dat"/> </html:a></li>
							<li><html:a action="showExtDatasets"> <html:text name="datrel"/> </html:a></li>
						</ul>
					</li>
					<li><html:a action="pendingFiles"> <html:text name="jobs"/> <img alt="control panel" src="images/task.png"></img></html:a></li>
					<li><html:a href="https://github.com/ALIADA/aliada-tool/wiki/User_Manual" target="_blank"> <html:text name="manual"/> <img alt="help" src="images/info.png"></img></html:a></li>
					<li><html:a action="logout"> <label class="underlined"><html:property value="%{#session['logedUser']}" /></label> <img alt="logOut" src="images/userLogout.png"/></html:a></li>
				</ul>
			</div>
		</div>
		<div class="headerContentGreenBorder">
		</div>
		<div class="content" >
				<tiles:insertAttribute name="body" />
		</div>
		<div class="footerContentGreenBorder"></div>
		<div class="copyrightPage">
			<tiles:insertAttribute name="footer" />
		</div>
	</body>

</html>