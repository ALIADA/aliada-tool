<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="html" %>

<script>
var checkLinking = function(){
	console.log("checkLinking");
	var rdfizerJobId = $("#rdfizerJobId").val();
     $.ajax({
       type: "GET",
       url: "http://aliada.scanbit.net:8080/aliada-links-discovery-1.0/jobs/"+linkingJobId,
       dataType : 'json',
       success: function(json) {
    	   console.log(json);
    	   var status = json.status;
    	   console.log(status);
       },
       error : function(jqXHR, status, error) {
           console.log("Error");
       },
       complete : function(jqXHR, status) {
           console.log("Completed");
       }
    });   
}
$(function() {
	setInterval( "checkLinking()", 1000 );
});	
</script>
<html:hidden id="linkingJobId" name="linkingJobId" value="#session['linkingJobId']" />
<h2 class="pageTitle"><html:text name="linkingInfo.title"/></h2>
	<div id="form" >
		<div class="content" >
			<h3 class="bigLabel"><html:text name="linkingInfo.info"/></h3>
			<div class="row">
				<label class="label"><html:text name="linkingInfo.nameFile"/></label><br/>
				<html:property value="linkingFile" />		
			</div>
			<div class="row">
				<label class="label"><html:text name="linkingInfo.sDate"/></label>
				<html:property value="startDate"/>			
			</div>
			<div class="row">
				<label class="label"><html:text name="linkingInfo.eDate"/></label>
				<html:property value="endDate"/>			
			</div>
			<div class="row">
				<label class="label green"><html:text name="linkingInfo.status"/>
					<html:property value="status"/>	
				</label>		
			</div>
			<div class="row">	
				<label class="label"><html:text name="linkingInfo.linksDataset"/></label>
				<ul>
				<html:iterator value="datasets" var="data">
		          <li><html:property value="key"/>: <html:property value="value"/></li>
		       </html:iterator>
				</ul>	
			</div>
			<div class="row">
				<label class="label"><html:text name="linkingInfo.links"/></label>
				<html:property value="numLinks"/>			
			</div>
			<div class="row">
				<html:form>
					<html:submit id="checkButton" action="linkingInfo" cssClass="submitButton buttonGreen" key="check"/>
				</html:form>		
			</div>
			<html:if test="state==3 || state==5">
				<script>
			    	$("#checkButton").hide();
			    </script>
			</html:if>
		</div>	
	</div>
		
