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
		    	  console.log(json);
			   	   var sDate = json.startDate;
			   	   console.log(sDate);
			   	   var eDate = json.endDate;
			   	   console.log(eDate);
			   	   var numLinks = json.numLinks;
			   	   console.log(numLinks);
			   	   var status = json.status;
			   	   console.log(status);
			   	   if(status=="finished"){
			   		   console.log("interval stopped");
			   		   clearInterval(intervalLinking);
				       $("#progressBarLinking").hide();
			   	   }
	               $("#startDate").text(sDate);
	               $("#endDate").text(eDate);
	               $("#numLinks").text(numLinks);
	               $("#status").text(status);
		      },
		      error : function(jqXHR, status, error) {
		      },
		      complete : function(jqXHR, status) {
		      }
	    	});   
		};
	
	$("#checkLinkingButton").on("click",function(){
		$("#linkingPanel").hide();		
		$("#linkingInfoPanel").show("slow");
		$('#progressBarLinking').show();
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
				<html:if test="linkingStarted">
					<script>
						$('#checkLinkingButton').prop("disabled",false);
						$('#startLinkingButton').prop("disabled",true);
				    </script>
				</html:if>
			</div>
			</html:form>
		</div>
		<div id="linkingInfoPanel" class="displayNo content" >
			<h3 class="bigLabel"><html:text name="linkingInfo.info"/></h3>
			<div class="row">
				<label class="label"><html:text name="linkingInfo.nameFile"/></label><br/>
				<html:property value="fileToLink"/>		
			</div>
			<div class="row label">
				<html:text name="linkingInfo.sDate"/>
				<div id="startDate" class="displayInline"></div>	
			</div>
			<div class="row label">
				<html:text name="linkingInfo.eDate"/>
				<div id="endDate" class="displayInline"></div>	
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
				<div id="numLinks" class="displayInline"></div>	
			</div>
			<div class="row label green">
				<html:text name="linkingInfo.status"/>
				<div id="status" class="displayInline"></div>	
			</div>
			<img id="progressBarLinking" class="displayNo label" src="images/progressBar.gif" alt="" />
		</div>		
	</div>
