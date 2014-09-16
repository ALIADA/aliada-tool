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
				<label class="label"><html:text name="ldsInfo.status"/></label>
				<html:property value="status"/>			
			</div>
			<div class="row">
				<html:form>				
					<html:submit action="lds" cssClass="submitButton button" key="createURIs" />
					<html:submit key="check" action="ldsInfo" cssClass="centeredButton button"/>
					<html:if test="ldsStarted">
						<script>
					    	$("#ldsInfo_createURIs").hide();
					    </script>
					</html:if>
					<html:else>
						<script>
				    		$("#ldsInfo_check").hide();
						</script>
					</html:else>
				</html:form>	
			</div>
		</div>	
	</div>
		
