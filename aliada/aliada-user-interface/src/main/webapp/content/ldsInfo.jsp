<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="html" %>


<h2 class="pageTitle"><html:text name="ldsInfo.title"/></h2>
	<div id="form" >
		<div class="content" >
			<div class="row">
				<label class="label"><html:text name="ldsInfo.nameFile"/></label><br/>
				<html:property value="importFile" />		
			</div>
			<div class="row">
				<label class="label"><html:text name="ldsInfo.sDate"/></label>
				<html:property value="startDate"/>			
			</div>
			<div class="row">
				<label class="label"><html:text name="ldsInfo.eDate"/></label>
				<html:property value="endDate"/>			
			</div>
			<div class="row">
				<label class="label green"><html:text name="ldsInfo.status"/>
					<html:property value="status"/>					
				</label>		
			</div>
			<div class="row">
				<html:form>				
					<html:submit id="ldsButton" action="lds" cssClass="submitButton buttonGreen" key="createURIs" />
					<html:submit id="ldsCheck" key="check" action="ldsInfo" cssClass="submitButton button"/>
					<html:if test="ldsStarted">
						<script>
					    	$("#ldsButton").hide();
					    </script>
					</html:if>
					<html:else>
						<script>
				    		$("#ldsCheck").hide();
						</script>
					</html:else>
					<html:if test="%{state==4 || state==5}">
						<script>
					    	$("#ldsCheck").hide();
					    </script>
					</html:if>
				</html:form>	
			</div>
		</div>	
	</div>
		