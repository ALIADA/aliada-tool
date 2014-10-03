<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="html" %>
<script>
 $(function(){
	 var intervalLinking = 0;
	 var intervalLDS = 0;
	 var finished=false;
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
			   	   var eDate = json.endDate;
			   	   var numLinks = json.numLinks;
			   	   var status = json.status;
			   	   if(status=="finished"){
			   		   console.log("interval linking stopped");
			   		   clearInterval(intervalLinking);
				       $("#progressBarLinking").hide();
				       $("#fineLinkingImg").show();
				       if(finished){
				    	   $("#linkingNextButton").removeClass("button");
				    	   $("#linkingNextButton").addClass("buttonGreen");
				    	   $("#linkingNextButton").prop("disabled",false);
				       }
				       finished=true;
			   	   }
	               $("#startDate").text(sDate);
	               $("#endDate").text(eDate);
	               $("#numLinks").text(numLinks);
		      },
		      error : function(jqXHR, status, error) {
		      },
		      complete : function(jqXHR, status) {
		      }
	    	});   
		};
		var checkLDS = function(){
			console.log("checking lds");
			var ldsJobId = $("#ldsJobId").val();
			var urlPath = "http://aliada.scanbit.net:8080/aliada-linked-data-server-1.0/jobs/"+ldsJobId;
		    $.ajax({
		      type: "GET",
		      url: urlPath,
		      dataType : 'json',
		      success: function(json) {
		    	  console.log(json);
			   	   var sDate = json.startDate;
			   	   var eDate = json.endDate;
			   	   var status = json.status;
			   	   if(status=="finished"){
			   		   console.log("interval LDS stopped");
			   		   clearInterval(intervalLDS);
				       $("#progressBarLDS").hide();
				       $("#fineLDSImg").show();
				       if(finished){
				    	   $("#linkingNextButton").removeClass("button");
				    	   $("#linkingNextButton").addClass("buttonGreen");
				    	   $("#linkingNextButton").prop("disabled",false);
				       }
				       finished=true;
			   	   }
	               $("#startDateLDS").text(sDate);
	               $("#endDateLDS").text(eDate);
		      },
		      error : function(jqXHR, status, error) {
		      },
		      complete : function(jqXHR, status) {
		      }
	    	});   
		};
	
	$("#checkLinkingButton").on("click",function(){
		console.log("Checking");
		$("#linkingPanel").hide();		
		$("#checkInfo").show("fast");
		$('#checkLinkingButton').hide();
		$('#progressBarLinking').show();
		intervalLinking = setInterval( checkLinking, 1000 );	
		$('#progressBarLDS').show();
		intervalLDS = setInterval( checkLDS, 1000 );
	});
}); 
</script>
<html:hidden id="linkingJobId" name="linkingJobId" value="%{#session['linkingJobId']}" />
<html:hidden id="ldsJobId" name="ldsJobId" value="%{#session['ldsJobId']}" />
<ul class="breadcrumb">
	<li><html:a action="configure" cssClass="breadcrumb"><html:text name="organisation.title"/></html:a></li>
	<li><html:a action="manage" cssClass="breadcrumb"><html:text name="manage.title"/></html:a></li>
	<li><html:a action="conversion" cssClass="breadcrumb"><html:text name="conversion.title"/></html:a></li>
	<li><html:a action="linking" cssClass="breadcrumb activeGreen"><html:text name="linking.title"/></html:a></li>
</ul>
<html:a id="rdfVal" action="rdfVal" cssClass="menuButton button fright" key="rdfVal" target="_blank"><html:text name="rdfVal"/></html:a>	
<div id="linkingPanel" class="content centered form">	
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
		<html:submit id="checkLinkingButton" disabled="true" onClick="return false;" cssClass="submitButton button" key="check"/>
		<html:if test="linkingStarted">
			<script>
				$("#startLinkingButton").removeClass("buttonGreen");
		    	$("#startLinkingButton").addClass("button");
		    	$("#checkLinkingButton").removeClass("button");
		    	$("#checkLinkingButton").addClass("buttonGreen");
				$('#checkLinkingButton').prop("disabled",false);
				$('#startLinkingButton').prop("disabled",true);
		    </script>
		</html:if>
	</div>
	</html:form>
</div>
<div id="checkInfo" class="displayNo">
	<div class="content">
		<div class="row bigLabel">
			<html:text name="linkingInfo.nameFile"/>
			<html:property value="fileToLink"/>		
		</div>
		
		<div id="linkingInfoPanel" class="content margin150 form" >
			<h3 class="bigLabel"><html:text name="linkingInfo.info"/></h3>
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
			<img id="fineLinkingImg" class="displayNo leftMargin" src="images/fine.png"/>				
			<img id="progressBarLinking" class="displayNo label" src="images/progressBar.gif" alt="" />
		</div>	
		
		
		<div id="ldsInfoPanel" class="content form" >
			<h3 class="bigLabel"><html:text name="ldsInfo.title"/></h3>
			<div class="row label">
				<html:text name="ldsInfo.sDate"/>
				<div id="startDateLDS" class="displayInline"></div>	
			</div>
			<div class="row label">
				<html:text name="ldsInfo.eDate"/>
				<div id="endDateLDS" class="displayInline"></div>		
			</div>
			<img id="progressBarLDS" class="displayNo label" src="images/progressBar.gif" alt="" />
			<img id="fineLDSImg" class="displayNo leftMargin" src="images/fine.png"/>
		</div>	
	</div>
</div>
<div class="row">
	<html:form>
		<html:submit id="linkingNextButton" disabled="true" action="manage" cssClass="submitButton button" key="next"/>
	</html:form>
</div>
