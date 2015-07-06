<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<link type="text/css" rel="stylesheet" href="<html:url value="css/conversion.css" />" />

<script>

function confirmBox(){
	var answer = window.confirm("<html:text name='conversion.cleanGraph'/>");
	if (answer == true) {
		$("#clean").submit();
	}
return false;
}

$(function(){
	var showRdfizerButton = $("#showRdfizerButton").val();
	var rdfizerStatus = $("#rdfizerStatus").val();
	var interval = 0;
	var checkRDF = function(){
		var rdfizerJobId = $("#rdfizerJobId").val();
		var urlPath = "/aliada-rdfizer-2.0/jobs/"+rdfizerJobId;
		//var urlPath = "/rdfizer/jobs/"+rdfizerJobId;
	    $.ajax({
	      type: "GET",
	      url: urlPath,
	      dataType : 'xml',
	      success: function(xml) {
               var format = $(xml).find("format").text();
               $("#format").text(format);
               var recordNum = $(xml).find("total-records-count").text();
               $("#recordNum").text(recordNum);
               var processedNum = $(xml).find("processed-records-count").text();
               $("#processedNum").text(processedNum);
               var statementsNum = $(xml).find("output-statements-count").text();
               $("#statementsNum").text(statementsNum);
               var processingThroughput = $(xml).find("records-throughput").text();
               $("#processingThroughput").text(processingThroughput);
               var triplesThroughput = $(xml).find("triples-throughput").text();
               $("#triplesThroughput").text(triplesThroughput);
	    	   var completed = $(xml).find("completed").text();
               if(completed=="true"){

            	   var statusCode = $(xml).find("status-code").text();
            	   if(statusCode == 0) {
    			       $("#fineImg").show();
            	   } else if(statusCode == -1) {
    			       $("#errorImg").show();
            		   $("#validationError").show();
            		   var validationErrorMess = $(xml).find("description").text();
            		   $("ValError").text(validationErrorMess);
            	   }
            	   $("#status").text("Completed");
		   		   $("#checkRDFButton").prop("disabled",true);
			       $("#nextBut").removeClass("button");
			       $("#nextBut").addClass("buttonGreen");
			       $("#nextBut").prop("disabled",false);
			       $("#rdfVal").show("fast");
			       $("#progressBar").hide();
		   		   clearInterval(interval);
               }
               else{
            	   $("#status").text("Running");            	   
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
	
	if(rdfizerStatus =="running"){
		$("#checkInfo").hide();
		$("#checkRDFInfo").show("fast");
		$('#progressBar').show();
		$('#checkRDFButton').hide();
		interval = setInterval( checkRDF, 3000 );
	} 
	
	if(showRdfizerButton==1){
	   	$("#rdfizeButton").removeClass("button");
	   	$("#rdfizeButton").addClass("buttonGreen");
		$('#rdfizeButton').prop("disabled",false);		
	}
	else{
	   	$("#rdfizeButton").removeClass("buttonGreen");
	   	$("#rdfizeButton").addClass("button");
		$('#rdfizeButton').prop("disabled",true);			
	}
	
	$('#datSelect').find('br').remove();
	
	$("#rdfizeButton").on("click",function(){
		$("#backButton").hide();
		$("#rdfizeButton").hide();
 	   	$("#cleanButton").hide();
 	   	
		$.publish('openremotedialog');
		$("#indicator1").show();
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
		   <img id="indicator1" class="displayNo" src="images/progressBar.gif" alt="Loading..."/>
		</sj:dialog>

<html:hidden id="rdfizerStatus" name="rdfizerStatus" value="%{#session['rdfizerStatus']}" />
<html:hidden id="rdfizerJobId" name="rdfizerJobId" value="%{#session['rdfizerJobId']}" />
<html:hidden id="showRdfizerButton" name="showRdfizerButton" value="%{showRdfizerButton}" />

<ul class="breadcrumb">
	<span class="breadCrumb"><html:text name="home"/></span>
	<li><span class="breadcrumb"><html:text name="manage.title"/></span></li>
	<li><span class="breadcrumb activeGreen"><html:text name="conversion.title"/></span></li>
	<li><span class="breadcrumb"><html:text name="linking.title"/></span></li>
</ul>


<div id="checkInfo">
	<div id="conversionDividedPanel" class="clearfixConversion">
			
		<div id="cleanGraphInfoPanel" class="fleft" >
			<html:form id="clean" action="/cleanGraph.action">
				<label class="row label center"><html:text name="conversion.cleanSelect"/></label>
				<div class="buttons row">
					<html:select id="graphToClean" name="selectedGraph" cssClass="inputForm" list="graphs" />
				</div>
				<div class="buttons row">
					 <html:submit cssClass="mediumButton button" key="conversion.clean" onclick="return confirmBox();"/> 
				</div>
				<html:actionerror/>
				<html:actionmessage/>
				<div id="inf" class="mainsection">
				 <span class="bold">
				 	<html:text name="cleanGinf1"/></span><br>
				 	<html:text name="cleanGinf2"/>
				 	<span class="bold"><html:text name="cleanGinf3"/></span><br>
				 	<html:text name="cleanGinf4"/>
				</div>
			</html:form>
		</div>	
			
		<div id="graphInfoPanel" class="fleft" >
			<html:form id="conv">
				<label class="row label center"><html:text name="conversion.filesTo"/></label>
				<table class="tableConv">
						<tr>
							<th  class="backgroundGreen center"><label class="bold"><html:text name="conversion.input"/></label></th>
							<td>
								<html:property value="importedFile.getFilename()" />
							</td>
						</tr>
						<tr>
							<th class="backgroundGreen center"><label class="bold"><html:text name="conversion.template"/></label></th>
							<td>
								<html:select id="templateSelect" name="selectedTemplate"
									cssClass="inputForm" list="templates" />
								</td>
						</tr>
						<tr>
							<th id="both" class="backgroundGreen center"><label class="bold"><html:text name="conversion.select"/></label></th>
							<td>
								<div id="datSelect" class="nobr">
										<html:doubleselect cssClass="inputForm"
										name="dat" list="datasetMap.keySet()" doubleCssClass="inputFormLarge"
										doubleName="sub" doubleList="datasetMap.get(top)" />
								</div>
							</td>
						</tr>		
					</table>
					
					<div id="conversionButtons">
						<div class="mainsectionRDF mainsectionfleft">
						 	<html:text name="convInf1"/><br>
						 	<html:text name="convInf2"/>
						 	<span class="bold"><html:text name="convInf3"/></span><br>
						</div>
						<div class="buttons row">
							<html:submit id="rdfizeButton" action="RDFize" disabled="true" cssClass="mediumButton button"
								key="RDF-ize"/>
						</div>	
					</div>
				</html:form>
		</div>
				
	</div>
	<div id="submitButtons" class="buttons row">
		<html:form id="submitButtonsForm">
			<html:submit id="backButton" action="manage" cssClass="fleft mediumButton button" key="back" />
		</html:form>
	</div>
</div>

<div id="checkRDFInfo" class="displayNo">
	<div class="formCheckConv centeredCheck checkContent">
			<label class="row label center"><html:text name="conversion.title"/></label>
			<table class="tableCheck">
						<tr>
							<th  class="backgroundGreen center"><label class="label"><html:text name="rdf.fileTo"/></label></th>
							<td>
								<html:property value="importedFile.getFilename()"/>
								<img id="fineImg" class="displayNo" src="images/fine.png"/>
								<img id="errorImg" class="displayNo" src="images/error.png">
							</td>
						</tr>
						<tr>
							<th class="backgroundGreen center"><label class="label"><html:text name="rdf.format"/></label></th>
							<td>
								<div id="format" class="displayInline"></div>						
							</td>
						</tr>
						<tr>
							<th class="backgroundGreen center"><label class="label"><html:text name="rdf.records"/></label></th>
							<td>
								<div id="recordNum" class="displayInline"></div>
							</td>
						</tr>
						<tr>
							<th class="backgroundGreen center"><label class="label"><html:text name="rdf.processed"/></label></th>
							<td>
								<div id="processedNum" class="displayInline"></div>	
							</td>
						</tr>	
						<tr>
							<th class="backgroundGreen center"><label class="label"><html:text name="rdf.emitted"/></label></th>
							<td>
								<div id="statementsNum" class="displayInline"></div>	
							</td>
						</tr>	
						<tr>
							<th class="backgroundGreen center"><label class="label"><html:text name="rdf.recordThroughput"/></label></th>
							<td>
								<div id="processingThroughput" class="displayInline"></div>
								<html:text name="rdf.recordsSec"/>		
							</td>
						</tr>
						<tr>
							<th class="backgroundGreen center"><label class="label"><html:text name="rdf.triplesThroughput"/></label></th>
							<td>
								<div id="triplesThroughput" class="displayInline"></div>
								<html:text name="rdf.triplesSec"/>		
							</td>
						</tr>
						<tr id="validationError" class="displayNo">
							<th class="backgroundGreen center"><label class="label"><html:text name="rdf.validationMessage"/></label></th>
							<td>
								<div id="ValError" class="displayInline"></div>			
							</td>
						</tr>
			</table>
			
			<div id="rdfButton" class="buttons row">
					<html:form id="submitButtonsForm">
						<img id="progressBar" class="displayNo" src="images/progressBar.gif" alt="" />
						<html:a id="rdfVal" disabled="true" action="getAuthors" cssClass="displayNo menuButton button fleft" key="rdfVal" target="_blank"><html:text name="rdfVal"/></html:a>
						<html:submit id="nextBut" disabled="true" action="linking" cssClass="fright mediumButton button"
							key="next" />
					</html:form>
			</div>
	</div>

</div>
		
		





