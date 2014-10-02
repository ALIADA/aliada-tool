<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="html" %>
<script>
 $(function(){
	 var intervalLinking;
	 var checkLinking = function(){
			console.log("checking Linking");
			var linkingJobId = $("#linkingJobId").val();
			var urlPath = "http://aliada.scanbit.net:8080/aliada-links-discovery-1.0/jobs/"+linkingJobId;
		    $.ajax({
		      type: "GET",
		      url: urlPath,
		      dataType : 'json',
		      success: function(json) {
			   	   var sDate = json.startDate;
	               $("#startDate").replaceWith(sDate);
			   	   var eDate = json.endDate;
	               $("#endDate").replaceWith(eDate);
			   	   var numLinks = json.numLinks;
	               $("#numLinks").replaceWith(numLinks);
			   	   var status = json.status;
	               $("#status").replaceWith(status);
			   	   if(status=="finished"){
			   		   console.log("interval stopped");
			   		   clearInterval(intervalLinking);
				       $("#progressBar").hide();
			   	   }
		      },
		      error : function(jqXHR, status, error) {
		          console.log("Error");
		      },
		      complete : function(jqXHR, status) {
		          console.log("Completed");
		      }
	    	});   
		};
	
	$("#checkLinkingButton").on("click",function(){
		$("#linkingPanel").hide();		
		$("#linkingInfoPanel").show("slow");
		$('#linkingProgressBar').show();
		$('#checkLinkingButton').hide();
		console.log("Checking");
		intervalLinking = setInterval( checkLinking, 1000 );		
	});
}); 
</script>
<html:hidden id="linkingJobId" name="linkingJobId" value="%{#session['linkingJobId']}" />
<h2 class="pageTitle"><html:text name="linking.title"/></h2>
	<div id="form">	
		<div id="linkingPanel" class="content">	
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
				<html:submit id="checkLinkingButton" disabled="true" onClick="return false;" cssClass="submitButton buttonGreen" key="check"/>
			</div>
			</html:form>
		</div>
		<div id="linkingInfoPanel" class="content" >
			<h3 class="bigLabel"><html:text name="linkingInfo.info"/></h3>
			<div class="row">
				<label class="label"><html:text name="linkingInfo.nameFile"/></label><br/>
				<html:property value="fileToLink"/>		
			</div>
			<div class="row label">
				<html:text name="linkingInfo.sDate"/>
				<label id="startDate"></label>	
			</div>
			<div class="row label">
				<html:text name="linkingInfo.eDate"/>
				<label id="endDate"></label>	
			</div>
			<%-- <div class="row">	
				<label class="label"><html:text name="linkingInfo.linksDataset"/></label>
				<ul>
				<html:iterator value="datasets" var="data">
		          <li><html:property value="key"/>: <html:property value="value"/></li>
		       </html:iterator>
				</ul>	
			</div> --%>
			<div class="row label">
				<html:text name="linkingInfo.links"/>
				<label id="numLinks"></label>	
			</div>
			<div class="row label green">
				<html:text name="linkingInfo.status"/>
				<label id="status"></label>	
			</div>
			<img id="progressBarLinking" class="displayNo" src="images/progressBar.gif" alt="" />
		</div>		
	</div>
