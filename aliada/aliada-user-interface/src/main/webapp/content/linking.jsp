<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="html" %>

<h2 class="pageTitle"><html:text name="linking.title"/></h2>
	<div id="form">	
		<div class="content">	
			<html:form>	
			<div <html:if test="notFiles">class="displayNo"</html:if>
				<html:else>
				    class="display"
				</html:else>>
				<h3 class="bigLabel"><html:text name="linking.importedFile"/></h3>
				<html:property value="fileToLink"/>	
			</div>
			<div <html:if test="notFiles">class="display"</html:if>
				<html:else>
				    class="displayNo"
				</html:else>>
				<h3 class="bigLabel"><html:text name="linking.notFiles"/></h3>
			</div>
			<h3 class="mediumLabel"><html:text name="linking.datasets"/></h3>		
			<html:iterator value="datasets" var="data">
	          <li><html:property value="value"/></li>
	       </html:iterator> 
			<html:actionerror/>
			<div class="row">
				<html:submit id="startLinkingButton" action="startLinking" cssClass="submitButton buttonGreen" key="linkSubmit"/>
				<div <html:if test="showCheckButton">class="displayInline"</html:if>
					<html:else>
					    class="displayNo"
					</html:else>>
					<html:submit id="checkButton" action="linkingInfo" cssClass="submitButton buttonGreen" key="check"/>
				</div>
				<html:if test="linkingStarted">
					<script>
				    	$("#startLinkingButton").hide();
				    </script>
				</html:if>
				<html:if test="state==3 || state==5">
					<script>
				    	$("#checkButton").hide();
				    </script>
				</html:if>
			</div>
			</html:form>
		</div>	
	</div>
