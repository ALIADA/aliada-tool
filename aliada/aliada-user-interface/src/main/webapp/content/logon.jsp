<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>
<html:form action="/logon.action" method="post" >
<html:hidden name="loginAttempt" value="%{'1'}" />
	<div class="row">
		<div class="namePassword label"><html:text name="inputUser" /></div>
		<div>
			<html:textfield name="inputUser" theme="xhtml" cssClass="input" id="inputUser"/>
		</div>
	</div>
	<div class="row">
		<div class="namePassword label">
			<span><html:text name="userPassword" /></span>
		</div>
		<div>
			<html:password name="inputPassword" theme="xhtml" id="inputPassword" cssClass="input"/>
		</div>
	</div>
	<html:actionerror/>
	<div class="row">
		<div id="changeLocale">
			<html:submit property="enterButton" cssClass="loginButton button" key="submit" />
		</div>
	</div>
	<%-- <html:select
		list="#{'en':'English', 'es':'Spanish','ita':'Italian','hun':'Hungarian'}"
		value="locale" /> --%>
	<html:url id="localeEN" namespace="/" action="locale" >
	   <html:param name="request_locale" >en</html:param>
	</html:url>
	<html:url id="localeES" namespace="/" action="locale" >
	   <html:param name="request_locale" >es</html:param>
	</html:url>			 
	<html:a href="%{localeEN}" ><img src="images/gbFlag.png"/></html:a>
	<html:a href="%{localeES}" ><img src="images/spFlag.png"/></html:a>
</html:form>
