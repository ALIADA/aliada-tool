<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="html" %>
<div id="menu">
	<html:form id="welcomePages" cssClass="welcomeLinks bold">
		<html:submit action="configure" cssClass="button menuButton" key="menu.configure"/>
		<html:submit id="importMenu" action="manage" cssClass="button menuButton" key="menu.import"/>
		<html:submit id="conversionMenu" action="conversion" cssClass="button menuButton" key="menu.conversion" disabled="true"/>
		<html:a id="rdfValMenu" action="rdfVal" cssClass="displayNo" key="menu.rdfVal" target="_blank" disabled="true"><html:text name="menu.rdfVal"/></html:a>
		<html:submit id="linkingMenu" action="linking" cssClass="menuButton button" key="menu.discovery" disabled="true"/>
		<html:submit id="ldsMenu" action="ldsInfo" cssClass="menuButton button" key="menu.lds" disabled="true"/>
		<html:submit  action="logout" cssClass="menuButton buttonWhite" key="logOut"/>
		<html:if test="state>=1">
			<script>
		    	$("#conversionMenu").prop( "disabled", false);
		    </script>
		</html:if>	
		<html:if test="state>1">
			<script>
	    		$("#rdfValMenu").prop( "disabled", false);				
		    	$("#linkingMenu").prop( "disabled", false);
		    	$("#ldsMenu").prop( "disabled", false);
		    </script>
		</html:if>
		<html:if test="state==0">
			<script>
		    	$("#importMenu").removeClass("button");
		    	$("#importMenu").addClass("buttonGreen");
		    </script>
		</html:if>	
		<html:if test="state==1">
			<script>
		    	$("#conversionMenu").removeClass("button");
		    	$("#conversionMenu").addClass("buttonGreen");
		    </script>
		</html:if>	
		<html:if test="state==2">
			<script>
		    	$("#linkingMenu").removeClass("button");
		    	$("#linkingMenu").addClass("buttonGreen");
		    	$("#ldsMenu").removeClass("button");
		    	$("#ldsMenu").addClass("buttonGreen");
		    </script>
		</html:if>	
		<html:if test="state==3">
			<script>
		    	$("#ldsMenu").removeClass("button");
		    	$("#ldsMenu").addClass("buttonGreen");
		    </script>
		</html:if>			
		<html:if test="state==4">
			<script>
		    	$("#linkingMenu").removeClass("button");
		    	$("#linkingMenu").addClass("buttonGreen");
		    </script>
		</html:if>	
	</html:form>
</div>