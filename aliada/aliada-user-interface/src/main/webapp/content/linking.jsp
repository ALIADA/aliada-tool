<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="html" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<link type="text/css" rel="stylesheet" href="<html:url value="css/linking.css" />" />

<script>

function seconds2time (seconds) {
    var hours   = Math.floor(seconds / 3600);
    var minutes = Math.floor((seconds - (hours * 3600)) / 60);
    var seconds = seconds - (hours * 3600) - (minutes * 60);
    var time = "";
    var sec = $("#sec").val();

    if (hours != 0) {
      time = hours+":";
    }
    if (minutes != 0 || time !== "") {
      minutes = (minutes < 10 && time !== "") ? "0"+minutes : String(minutes);
      time += minutes+":";
    }
    if (time === "") {
      time = seconds + " " +sec;
    }
    else {
      time += (seconds < 10) ? "0"+seconds : String(seconds);
    }
    return time;
}

$(function(){
	 var type = $("#userType").val();
	 var intervalLinking = 0;
	 var intervalLDS = 0;
	 var finishedLink = false;
	 var finishedCreat = false;
	 var rdfizerStatus = $("#rdfizerStatus").val();
	 
	 var checkLinking = function(){
			console.log("checking Linking");
			var linkingJobId = $("#linkingJobId").val();
			var urlPath = "/aliada-links-discovery-2.0/jobs/"+linkingJobId;
			var arrName = [];
			var arrDuration = [];
			var arrNumLinks = [];
			var arrStatus = [];
		    $.ajax({
		      type: "GET",
		      url: urlPath,
		      dataType : 'json',
		      success: function(json) {
		    	  console.log(json);
			   	   var sDate = json.startDate;
			   	   var eDate = json.endDate;
			   	   //var duration = json.durationSeconds;
			   	   var numLinks = json.numLinks;
			   	   var status = json.status;
			   	   $("#datasetsInfo").empty();
				   $.each(json.subjobs, function(idx, obj) {
					   
					   	arrName[idx] = obj.name;
					   	arrDuration[idx] = obj.durationSeconds;
					   	arrNumLinks[idx] = obj.numLinks;
					   	arrStatus[idx] = obj.status;
					   	
					   	console.log(idx);
				   		console.log(obj.name);
				   		console.log("Duración: "+obj.durationSeconds);
				   		console.log(obj.numLinks);
				   		console.log(obj.status);
				   		
				   		var d = "<ul style='list-style-type:square'>";
				   		for (var x = 0; x < arrName.length; x++) {
				   			if (arrStatus[x]=="running") {
				   					d = d + "<li>" + arrName[x] + ': ' + arrNumLinks[x] + ' <img src="images/loaderMini.gif"/> </li>';
				   			} else if (arrStatus[x]=="finished") {
				   					d = d + "<li>" + arrName[x] + ': ' + arrNumLinks[x] + " (" + seconds2time(arrDuration[x]) + ") <img src='images/fine.png'/></li>";
				   			} else {
				   					d = d + "<li>" + arrName[x] + ': ' + arrNumLinks[x] + ' <img src="images/clock.png"/> </li>';
				   			}
				   		}
				   		d = d +'</ul>';
				   		
				   		$("#datasetsInfo").html(d);
				   		
				   });
			   	   if(status=="finished"){
			   		   console.log("interval linking stopped");
			   		   clearInterval(intervalLinking);
				       $("#progressBarLinking").hide();
				       $("#links").show();
				       $("#numLinks").removeClass("displayNo");
			    	   $("#numLinks").addClass("displayInline");
				       if(finishedCreat){
				    	   if(type == 1) {
				    	   	$("#publishButton").show();
				    	   }
				    	   $("#linkingNextButton").show();
				    	   $("#pag").show();
				    	   $("#rdfVal").show();
				    	   if(type == 1) {
				    	   	$("#linksVal").show();
				    	   }
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
				       $("#fineLDSImg").show();
				       if(finishedLink){
				    	   if(type == 1) {
				    	   	$("#publishButton").show();
				    	   }
				    	   $("#linkingNextButton").show();
				    	   $("#pag").show();
				    	   $("#rdfVal").show();
				    	   if(type == 1) {
				    	   	$("#linksVal").show();
				    	   }
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
	 
	 if(rdfizerStatus == "finishedRdfizer"){
		 
		 $("#linkingPanelCheck").hide();
		 
		 $("#checkLinkingButton").removeClass("buttonGreen");
   	 	 $("#checkLinkingButton").addClass("button");
		 $('#checkLinkingButton').prop("disabled",true);
		 
		 $("#startLinkingButton").removeClass("button");
 	   	 $("#startLinkingButton").addClass("buttonGreen");
 	     $("#startLinkingButton").prop("disabled",false);
		 
	 } else if (rdfizerStatus == "runningLinking") {
		 
		 $("#linkingPanel").hide();
		 $("#linkingPanelCheck").show();
		 
 	     $("#startLinkingButtonCheck").removeClass("buttonGreen");
   	 	 $("#startLinkingButtonCheck").addClass("button");
		 $('#startLinkingButtonCheck').prop("disabled",true);
		 
		 $("#checkLinkingButtonCheck").removeClass("button");
 	   	 $("#checkLinkingButtonCheck").addClass("buttonGreen");
 	     $("#checkLinkingButtonCheck").prop("disabled",false);
 	     
	 } else if (rdfizerStatus == "finishedLinking") {
		 
		 console.log("Checking");
 	     $("#linkingPanel").hide();
		 $("#linkingPanelCheck").hide();		
	   	 $("#checkInfo").show("fast");
		 $('#checkLinkingButton').hide();
		 intervalLinking = setInterval( checkLinking, 1000 );
		 $('#fineLDSImg').hide();
		 $('#progressBarLDS').show();
		 intervalLDS = setInterval( checkLDS, 1000 );
		 
	 }
	
	$("#publishButton").on("click",function(){
		console.log("Checking publish button");
		
		//This will disable everything contained in the div
		$("#publishButton").hide();
 	   	$("#linkingNextButton").hide();
   	
		$.publish('openremotedialog');
		$("#indicator").show();
		$(".ui-dialog-titlebar-close").hide(); 
	
	});
	
	$('#select_all').change(function() {
	    var checkboxes = $(this).closest('form').find(':checkbox');
	    if($(this).is(':checked')) {
	        checkboxes.prop('checked', true);
	    } else {
	        checkboxes.prop('checked', false);
	    }
	});
	
	/*$("#sorteable :checkbox").on("change",function(){
		var checked = $("#sorteable input:checked").length > 0;
		console.log(checked);
	    if (checked){
			$("#startLinkingButtonCheck").removeClass("button");
			$("#startLinkingButtonCheck").addClass("buttonGreen");
			$("#startLinkingButtonCheck").prop( "disabled", false);
	    } else {
	    	$("#startLinkingButtonCheck").removeClass("buttonGreen");
			$("#startLinkingButtonCheck").addClass("button");
			$("#startLinkingButtonCheck").prop( "disabled", true);
	    }
	});*/
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
		   <img id="indicator" class="displayNo" src="images/progressBar.gif" alt="Loading..."/>
		</sj:dialog>

<html:hidden id="rdfizerStatus" name="rdfizerStatus" value="%{#session['rdfizerStatus']}" />
<html:hidden id="linkingJobId" name="linkingJobId" value="%{#session['linkingJobId']}" />
<html:hidden id="ldsJobId" name="ldsJobId" value="%{#session['ldsJobId']}" />
<html:hidden id="sec" name="sec" value="%{seconds}" />

<ul class="breadcrumb">
	<span class="breadCrumb"><html:text name="home"/></span>
	<li><span class="breadcrumb"><html:text name="manage.title"/></span></li>
	<li><span class="breadcrumb"><html:text name="conversion.title"/></span></li>
	<li><span class="breadcrumb activeGreen"><html:text name="linking.title"/></span></li>
</ul>

<div id="linkingPanel">
	
	<html:form>
	<div class="formCheckConv centeredCheck checkContent">
			<h3 class="bigLabel"><html:text name="linking.importedFile"/> <span class="bold"><html:property value="fileToLink.getFilename()"/></span></h3>
			<h3 class="mediumLabel center"><html:text name="linking.datasets"/></h3>
			<div id="sorteable">
				<ul>
				<html:iterator value="datasets" var="data">
			        <li> 
			         	<html:checkbox fieldValue="%{value}" name="dataset"></html:checkbox>
		     		 	<html:property value="%{value}"/>
		     		</li>
			    </html:iterator>
			    	<li>
			    		<html:checkbox id="select_all" name="all"></html:checkbox>
			    		<html:text name="select"/>
			    	</li>
			    </ul>	
			</div>
	</div>
	
	<div class="buttons row">
			<html:submit id="startLinkingButtonCheck" action="startLinking" cssClass="fright submitButton buttonGreen" key="linkSubmit"/>
	</div>
	</html:form>
	
</div>

<div id="linkingPanelCheck">

	<html:form>
	<div class="formCheckConv centeredCheck checkContent">
		<h3 class="bigLabel"><html:text name="linking.importedFile"/> <span class="bold"><html:property value="fileToLink.getFilename()"/></span></h3>
		<h3 class="mediumLabel center"><html:text name="linking.datasets"/></h3>
		<div id="sorteable">
			<ul>
				<html:iterator value="dataset" var="data">
			        <li> 
			         	<html:text name="data"/>
		     		</li>
			    </html:iterator>
			</ul>	
		</div>
	</div>
		
		<div class="buttons row">
			<%-- <html:submit id="checkLinkingButtonCheck" onClick="return false;" cssClass="fright submitButton buttonGreen" key="check"/>--%>
			<html:submit id="checkLinkingButtonCheck" action="checkLinking" cssClass="fright submitButton buttonGreen" key="check"/>
		</div>
	</html:form>
</div>

<div id="checkInfo" class="displayNo">

		<div class="row bigLabel">
			<html:text name="linkingInfo.nameFile"/>
			<html:property value="fileToLink.getFilename()"/>
		</div>
		
		<div id="linkingDividedPanel" class="clearfix">		
			<div id="linkingInfoPanel" class="fleftPanel" >
				<h3 class="bigLabel center"><html:text name="linkingInfo.info"/></h3>
				<div class="row center">
					<label class="label"><html:text name="linkingInfo.sDate"/></label>
					<div id="startDate" class="displayInline"></div>	
				</div>
				<div class="row center">
					<label class="label"><html:text name="linkingInfo.eDate"/></label>
					<div id="endDate" class="displayInline"></div>	
				</div>
				<div class="row center"><label class="label"><html:text name="linkingInfo.linksDataset"/></label></div>
				<div id="datasetsInfo" class="lMargin25 content scrollifyAuto"></div>
				<div class="row center">
					<img id="progressBarLinking" class="centerImage" src="images/progressBar.gif" alt="progress" />
					<label id="links" class="label displayNo"><html:text name="linkingInfo.links"/></label>
					<div id="numLinks" class="displayNo"></div>	
				</div>			
			</div>	
			
			<div id="ldsInfoPanel" class="fleftPanel" >
				<h3 class="bigLabel center"><html:text name="ldsInfo.title"/></h3>
				<div class="row center">
					<label class="label"><html:text name="ldsInfo.sDate"/></label>
					<div id="startDateLDS" class="displayInline"></div>	
				</div>
				<div class="row center">
					<label class="label"><html:text name="ldsInfo.eDate"/></label>
					<div id="endDateLDS" class="displayInline"></div>		
				</div>
				<div class="row center">
					<img id="progressBarLDS" class="centerImage" src="images/progressBar.gif" alt="fine"/>
					<img id="fineLDSImg" class="centerImage" src="images/fine.png" alt="progress"/>
				</div>
				<div class="buttons row">
					<html:a id="pag" href="%{datasetUrl}" cssClass="displayNo menuButton button pad10" key="datWeb" target="_blank"><html:text name="datWeb"/></html:a>
				</div>
				<div class="buttons row">
					<html:a id="rdfVal" action="getAuthors" cssClass="displayNo menuButton button pad10" key="rdfVal" target="_blank"><html:text name="rdfVal"/></html:a>
				</div>
				<div class="buttons row">
					<html:a id="linksVal" action="linksAmbVal" cssClass="displayNo menuButton button pad10" key="linksVal" target="_blank"><html:text name="linksVal"/></html:a>
				</div>
			</div>
				
		</div>
		
	<div class="buttons row">
		<html:form>
			<html:submit id="linkingNextButton" action="addAnotherFileWork" cssClass="displayNo fleft menuButton buttonGreen" key="linking.addNew"/>
			<html:submit id="publishButton" action="publish" cssClass="displayNo fright menuButton buttonGreen" key="publish"/>
		</html:form>
	</div>
</div>
