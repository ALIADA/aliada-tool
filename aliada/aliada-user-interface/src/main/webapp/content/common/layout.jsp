<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="html"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<link type="text/css" rel="stylesheet" href="<html:url value="css/layout.css" />" />

<html:hidden id="showLang" name="showLang" value="%{#session['action']}" />
<html:hidden id="userType" name="userType" value="%{#session['type']}" />

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
	
	<script>
		$(function(){
			var showLang = $("#showLang").val();			
			var type = $("#userType").val();
			
			if(type == 1) {
				$(".type").show();
			}
			
			if(showLang == "manage" 
			|| showLang == "conversion"
			|| showLang == "linking"
			|| showLang == "checkLinking"
			|| showLang == "showInstitution"
			|| showLang == "showProfiles"
			|| showLang == "showTemplates"
			|| showLang == "showUsers"
			|| showLang == "showDatasets"
			|| showLang == "showSubsets"
			|| showLang == "showExtDatasets"
			|| showLang == "pendingFiles"){
				$("#lang").show();				
			} else {
				$("#lang").hide();				
			}
			
		});
	</script>
	
	<body>
		<div id="settings">
		
			<span id="lang" class="fright lMargin500 lang">
				<html:url id="localeEN" namespace="/" action="%{#session['action']}" >
				   <html:param name="request_locale" >en</html:param>
				</html:url>
				<html:url id="localeHU" namespace="/" action="%{#session['action']}" >
					<html:param name="request_locale" >hu</html:param>
				</html:url>	
				<html:url id="localeES" namespace="/" action="%{#session['action']}" >
				   <html:param name="request_locale" >es</html:param>
				</html:url>
				<html:url id="localeITA" namespace="/" action="%{#session['action']}" >
					<html:param name="request_locale" >ita</html:param>
				</html:url>		 
				<html:a href="%{localeEN}" cssClass="langStyle" ><html:text name="eng"/></html:a>
				<html:a href="%{localeHU}" cssClass="langStyle" ><html:text name="hun"/></html:a>
				<html:a href="%{localeES}" cssClass="langStyle" ><html:text name="spa"/></html:a>
				<html:a href="%{localeITA}" cssClass="langStyle" ><html:text name="ita"/></html:a>
			</span>
			
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
							
							<li class="type displayNo"> <html:a action="showDatasets"> <html:text name="dat"/> </html:a></li>
							
							<li class="type displayNo"> <html:a action="showExtDatasets"> <html:text name="datrel"/> </html:a></li>
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