<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="html"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
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

	<body class="whitebackground">
		<img class="leftText" src="images/aliada-header.png"/>
		<span class="fright topPad20">
			<label class="underlined"><html:property value="%{#session['logedUser']}" /></label>
			<html:a href="https://github.com/ALIADA/aliada-tool/wiki/User_Manual" target="_blank"><img alt="help" src="images/info.png"></img></html:a>
			<html:a action="logout"><img alt="logOut" src="images/userLogout.png"/></html:a>
		</span>	
		<div class="headerContentGreenBorder"></div>
		<div class="content" >
				<tiles:insertAttribute name="body" />
		</div>
		<div id="collapseCloud"></div>
		<div class="headerContentGreenBorder"></div>
		<div class="copyrightPage">
			<tiles:insertAttribute name="footer" />
		</div>
	</body>

</html>
