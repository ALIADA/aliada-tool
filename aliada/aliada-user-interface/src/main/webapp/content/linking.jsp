<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="html" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<script>
 $(function(){
	 var intervalLinking = 0;
	 var intervalLDS = 0;
	 var finishedLink = false;
	 var finishedCreat = false;
	 
	 var rdfizerStatus = $("#rdfizerStatus").val();
	 
	 if(rdfizerStatus == "finishedRdfizer"){
		 
		 $("#linkingPanelCheck").hide();
		 
		 $("#checkLinkingButton").removeClass("buttonGreen");
    	 $("#checkLinkingButton").addClass("button");
		 $('#checkLinkingButton').prop("disabled",true);
		 
		 $("#startLinkingButton").removeClass("button");
  	   	 $("#startLinkingButton").addClass("buttonGreen");
  	     $("#startLinkingButton").prop("disabled",false);
		 
	 } else if (rdfizerStatus == "finishedLinking") {
		 
		 $("#linkingPanel").hide();
		 $("#linkingPanelCheck").show();
		 
  	     $("#startLinkingButtonCheck").removeClass("buttonGreen");
    	 $("#startLinkingButtonCheck").addClass("button");
		 $('#startLinkingButtonCheck').prop("disabled",true);
		 
		 $("#checkLinkingButtonCheck").removeClass("button");
  	   	 $("#checkLinkingButtonCheck").addClass("buttonGreen");
  	     $("#checkLinkingButtonCheck").prop("disabled",false);
	 }
	 
	 var checkLinking = function(){
			console.log("checking Linking");
			var linkingJobId = $("#linkingJobId").val();
			var urlPath = "/aliada-links-discovery-2.0/jobs/"+linkingJobId;
			var arrName = [];
			var arrNumLinks = [];
			var arrStatus = [];
		    $.ajax({
		      type: "GET",
		      url: urlPath,
		      dataType : 'json',
		      success: function(json) {
		    	  console.log(json);
			   	   var sDate = json.startDate;
			   	   //sDate= new Date(sDate);
			   	   var eDate = json.endDate;
			   	   var numLinks = json.numLinks;
			   	   var status = json.status;
			   	   $("#datasetsInfo").empty();
				   $.each(json.subjobs, function(idx, obj) {
					   
					   	arrName[idx] = obj.name;
					   	arrNumLinks[idx] = obj.numLinks;
					   	arrStatus[idx] = obj.status;
					   	
					   	console.log(idx);
				   		console.log(obj.name);
				   		console.log(obj.numLinks);
				   		console.log(obj.status);
				   		
				   		var d = "<table>";
				   		for (var x = 0; x < arrName.length; x++) {
				   			console.log("Result: "+(x+1));
				   			if (arrStatus[x]=="running") {
				   				if((x+1)%2 == 0){
				   					d = d + '<td>' + arrName[x]+': '+arrNumLinks[x]+' <img src="images/loaderMini.gif"/></td></tr>';
				   				} else {
				   					d = d + '<tr><td>' + arrName[x]+': '+arrNumLinks[x]+' <img src="images/loaderMini.gif"/></td>';
				   				}
				   			} else if (arrStatus[x]=="finished") {
				   				if((x+1)%2 == 0){
				   					d = d + '<td>' + arrName[x]+': '+arrNumLinks[x]+' <img src="images/fine.png"/></td></tr>';
				   				} else {
				   					d = d + '<tr><td>' + arrName[x]+': '+arrNumLinks[x]+' <img src="images/fine.png"/></td>';
				   				}
				   			} else {
								if((x+1)%2 == 0){
									d = d + '<td>' + arrName[x]+': '+arrNumLinks[x]+' <img src="images/clock.png"/></td></tr>';
				   				} else {
				   					d = d + '<tr><td>' + arrName[x]+': '+arrNumLinks[x]+' <img src="images/clock.png"/></td>';
				   				}
				   			}
				   		}
				   		d = d +'</table>';
				   		
				   		$("#datasetsInfo").html(d);
				   		
				   });
			   	   if(status=="finished"){
			   		   console.log("interval linking stopped");
			   		   clearInterval(intervalLinking);
				       $("#progressBarLinking").hide();
				       $("#fineLinkingImg").addClass("centerImage");
				       $("#fineLinkingImg").show();
				       if(finishedCreat){
				    	   $("#publishButton").removeClass("button");
				    	   $("#publishButton").addClass("buttonGreen");
				    	   $("#publishButton").prop("disabled",false);
				    	   $("#linkingNextButton").removeClass("button");
				    	   $("#linkingNextButton").addClass("buttonGreen");
				    	   $("#linkingNextButton").prop("disabled",false);
				    	   $("#pag").show();
				       }
				       finishedLink = true;
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
			var urlPath = "/aliada-linked-data-server-2.0/jobs/"+ldsJobId;
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
				       $("#fineLDSImg").addClass("centerImage");
				       $("#fineLDSImg").show();
				       if(finishedLink){
				    	   $("#publishButton").removeClass("button");
				    	   $("#publishButton").addClass("buttonGreen");
				    	   $("#publishButton").prop("disabled",false);
				    	   $("#linkingNextButton").removeClass("button");
				    	   $("#linkingNextButton").addClass("buttonGreen");
				    	   $("#linkingNextButton").prop("disabled",false);
				    	   $("#pag").show();
				       }
				       finishedCreat = true;
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
	
	$("#checkLinkingButtonCheck").on("click",function(){
		console.log("Checking");
		$("#linkingPanelCheck").hide();		
		$("#checkInfo").show("fast");
		$('#checkLinkingButton').hide();
		$('#progressBarLinking').show();
		intervalLinking = setInterval( checkLinking, 1000 );	
		$('#progressBarLDS').show();
		intervalLDS = setInterval( checkLDS, 1000 );
	});
	
	$("#publishButton").on("click",function(){
		console.log("Checking publish button");
		
		//This will disable everything contained in the div
//    	$("#rdfVal").hide();
//    	$("#linksVal").hide();
 		$(".topPad20").hide();
 		
		$("#linkingNextButton").removeClass("buttonGreen");
        $("#linkingNextButton").addClass("button");
    	$('#linkingNextButton').prop("disabled",true);
    	
		$.publish('openremotedialog');
		$(".ui-dialog-titlebar-close").hide(); 
 	
	});
	
}); 
</script>
		
		<sj:dialog id="myremotedialog"
		    openTopics="openremotedialog" 
		    autoOpen="false" 
		    closeOnEscape="false"
		    modal="false"
		    height="100"
		    width="175"
		    title="%{title}"> 			
		   <img id="indicator" src="images/progressBar.gif" alt="Loading..."/>
		</sj:dialog>

<html:hidden id="rdfizerStatus" name="rdfizerStatus" value="%{#session['rdfizerStatus']}" />
<html:hidden id="linkingJobId" name="linkingJobId" value="%{#session['linkingJobId']}" />
<html:hidden id="ldsJobId" name="ldsJobId" value="%{#session['ldsJobId']}" />
<ul class="breadcrumb">
	<span class="breadCrumb"><html:text name="home"/></span>
	<li><span class="breadcrumb"><html:text name="manage.title"/></span></li>
	<li><span class="breadcrumb"><html:text name="conversion.title"/></span></li>
	<li><span class="breadcrumb activeGreen"><html:text name="linking.title"/></span></li>
</ul>
<html:a id="rdfVal" action="rdfVal" cssClass="menuButton button fleft" key="rdfVal" target="_blank"><html:text name="rdfVal"/></html:a>
<html:a id="linksVal" action="linksVal" cssClass="menuButton button fright" key="linksVal" target="_blank"><html:text name="linksVal"/></html:a>	
<div id="linkingPanel" class="content centered form">	
	<html:form>
		<h3 class="bigLabel"><html:text name="linking.importedFile"/></h3>
		<html:property value="fileToLink.getFilename()"/>
		<h3 class="mediumLabel"><html:text name="linking.datasets"/></h3>		
		<html:iterator value="datasets" var="data">
	         <ul><html:checkbox fieldValue="%{value}" name="dataset" value="false">
     		</html:checkbox>
     		<html:property value="%{value}"/></ul>
	      </html:iterator> 
		<html:actionerror/>
		<div class="row">
			<html:submit id="startLinkingButton" action="startLinking" cssClass="submitButton buttonGreen" key="linkSubmit"/>
			<html:submit id="checkLinkingButton" onClick="return false;" cssClass="submitButton button" key="check"/>
		</div>
	</html:form>
</div>
<div id="linkingPanelCheck" class="content centered form">	
	<html:form>
		<h3 class="bigLabel"><html:text name="linking.importedFile"/></h3>
		<html:property value="fileToLink.getFilename()"/>
		<h3 class="mediumLabel"><html:text name="linking.datasets"/></h3>		
			<html:iterator value="dataset" var="data">
	         	<ul><html:text name="data"/></ul>
	      	</html:iterator> 
		<html:actionerror/>
		<div class="row">
			<html:submit id="startLinkingButtonCheck" action="startLinking" cssClass="submitButton buttonGreen" key="linkSubmit"/>
			<html:submit id="checkLinkingButtonCheck" onClick="return false;" cssClass="submitButton button" key="check"/>
		</div>
	</html:form>
</div>
<div id="checkInfo" class="displayNo">
	<div class="content">
		<div class="row bigLabel">
			<html:text name="linkingInfo.nameFile"/>
			<html:property value="fileToLink.getFilename()"/>
			<div id="pag" class="displayNo">
				<br/>
				<a href="<html:property value="datasetUrl" />" target="_blank"><img alt="dataset" src="images/dataset.png"></img></a>
			</div>	
		</div>
		<div id="linkingDividedPanel" class="clearfix">		
			<div id="linkingInfoPanel" class="fleft" >
				<h3 class="bigLabel"><html:text name="linkingInfo.info"/></h3>
				<div class="row">
					<label class="label"><html:text name="linkingInfo.sDate"/></label>
					<div id="startDate" class="displayInline"></div>	
				</div>
				<div class="row">
					<label class="label"><html:text name="linkingInfo.eDate"/></label>
					<div id="endDate" class="displayInline"></div>	
				</div>
				<div class="row"><label class="label"><html:text name="linkingInfo.linksDataset"/></label></div>
				<div id="datasetsInfo" class="lMargin40"></div>
				<div class="row">
					<label class="label"><html:text name="linkingInfo.links"/></label>
					<div id="numLinks" class="displayInline"></div>	
				</div>
				<img id="fineLinkingImg" class="displayNo" src="images/fine.png" alt="fine"/>				
				<img id="progressBarLinking" class="displayNo centerImage" src="images/progressBar.gif" alt="progress" />
			</div>	
			
			<div id="ldsInfoPanel" class="fleft" >
				<h3 class="bigLabel"><html:text name="ldsInfo.title"/></h3>
				<div class="row">
					<label class="label"><html:text name="ldsInfo.sDate"/></label>
					<div id="startDateLDS" class="displayInline"></div>	
				</div>
				<div class="row">
					<label class="label"><html:text name="ldsInfo.eDate"/></label>
					<div id="endDateLDS" class="displayInline"></div>		
				</div>
				<img id="progressBarLDS" class="displayNo centerImage" src="images/progressBar.gif" alt="fine"/>
				<img id="fineLDSImg" class="displayNo" src="images/fine.png" alt="progress"/>
			</div>	
		</div>
	</div>
	<div class="row">
		<html:form>
			<html:submit id="linkingNextButton" disabled="true" action="addAnotherFileWork" cssClass="fleft mediumButton button" key="linking.addNew"/>
			<html:submit id="publishButton" disabled="true" action="publish" cssClass="fright submitButton button" key="publish"/>
		</html:form>
	</div>
</div>

