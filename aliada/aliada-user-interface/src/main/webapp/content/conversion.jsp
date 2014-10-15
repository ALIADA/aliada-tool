<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>
<script>
function confirmBox(){
	var answer = window.confirm("<html:text name='conversion.cleanGraph'/>");
	if (answer == true){
		var graphToCleanId = $("#graphToClean").val();
		console.log("Cleaning graph");
		window.location.href = "${pageContext.request.contextPath}/cleanGraph.action?graphToCleanId="+graphToCleanId;
	}
	else{
		console.log("Not cleaned");
		window.location = "${pageContext.request.contextPath}/conversion.action";
	}
    return false;
}
$(function(){
	var showCheckButton = $("#showCheckButton").val();
	var showRdfizerButton = $("#showRdfizerButton").val();
	var interval;
	var checkRDF = function(){
		console.log("checking RDF");
		var rdfizerJobId = $("#rdfizerJobId").val();
		var urlPath = "http://aliada.scanbit.net:8080/aliada-rdfizer-1.0/jobs/"+rdfizerJobId;
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
               console.log(completed);
	    	   console.log(xml);
	    	   var completed = $(xml).find("completed").text();
               if(completed=="true"){
            	   $("#status").text("Completed");
		   		   $("#checkRDFButton").prop("disabled",true);
			       $("#nextButton").removeClass("button");
			       $("#nextButton").addClass("buttonGreen");
			       $("#nextButton").prop("disabled",false);
			       $("#rdfVal").show("fast");
			       $("#progressBar").hide();
			       $("#fineImg").show();
		   		   console.log("interval stopped");
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
	$("#checkRDFButton").on("click",function(){
		$("#rdfizePanel").hide();		
		$("#checkInfo").show("fast");
		$('#progressBar').show();
		$('#checkRDFButton').hide();
		console.log("Checking");
		interval = setInterval( checkRDF, 1000 );		
	});
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
	if(showCheckButton==1){
	   	$("#checkRDFButton").removeClass("button");
	   	$("#checkRDFButton").addClass("buttonGreen");
		$('#checkRDFButton').prop("disabled",false);
		$('#backButton').prop("disabled",true);		
	}
}); 
</script>

<html:hidden id="rdfizerJobId" name="rdfizerJobId" value="%{#session['rdfizerJobId']}" />
<html:hidden id="showRdfizerButton" name="showRdfizerButton" value="%{showRdfizerButton}" />
<html:hidden id="showCheckButton" name="showCheckButton" value="%{showCheckButton}" />
<ul class="breadcrumb">
	<span class="breadCrumb"><html:text name="home"/></span>
	<li><span class="breadcrumb"><html:text name="organisation.title"/></span></li>
	<li><span class="breadcrumb"><html:text name="manage.title"/></span></li>
	<li><span class="breadcrumb activeGreen"><html:text name="conversion.title"/></span></li>
	<li><span class="breadcrumb"><html:text name="linking.title"/></span></li>
</ul>
<html:a id="rdfVal" disabled="true" action="rdfVal" cssClass="displayNo menuButton button fright" key="rdfVal" target="_blank"><html:text name="rdfVal"/></html:a>			
<div class="form centered">
	<html:form id="conversion">
		<div id="rdfizePanel" class="content">
			<label class="row label"><html:text name="conversion.filesTo"/></label>
			<table class="table">
				<tr class="backgroundGreen center">
					<th><label class="bold"><html:text name="conversion.input"/></label></th>
					<th><label class="bold"><html:text name="conversion.template"/></label></th>
					<th><label class="bold"><html:text name="conversion.graph"/></label></th>
				</tr>
				<tr>
					<td>
						<html:property value="importedFile.getFilename()" />
					</td>
					<td><html:select name="selectedTemplate"
							cssClass="inputForm" list="templates" />					
					</td>
					<td><html:select name="selectedGraph"
							cssClass="inputForm" list="graphs" />					
					</td>
				</tr>			
			</table>
			<div id="conversionButtons" class="buttons row">	
				<html:submit id="rdfizeButton" action="RDFize" disabled="true" cssClass="submitButton button"
					key="RDF-ize"/>
				<html:submit id="checkRDFButton" disabled="true" cssClass="submitButton button"
					key="check" onClick="return false;"/>
				<html:submit action="showTemplates" cssClass="submitButton button"
					key="templates" />
			</div>
			<div>
				<label class="row label"><html:text name="conversion.cleanSelect"/></label>
				<html:select id="graphToClean" cssClass="inputForm" list="graphs" />
				<html:submit onclick="return confirmBox();" cssClass="submitButton button"
					key="conversion.clean" />
				<%-- <html:submit action="cleanGraph" cssClass="submitButton button"
					key="conversion.clean" /> --%>
			</div>
			<html:actionmessage />
		</div>
		<div id="checkInfo" class="displayNo content">
			<div class="row">
				<label class="label"><html:text name="rdf.fileTo"/></label>
				<html:property value="importFile.getFilename()"/>
			</div>				
			<img id="fineImg" class="displayNo" src="images/fine.png"/>
			<div class="row">
				<label class="label"><html:text name="rdf.format"/></label>
				<div id="format" class="displayInline"></div>	
			</div>
			<div class="row">
				<label class="label"><html:text name="rdf.records"/></label>	
				<div id="recordNum" class="displayInline"></div>	
			</div>
			<div class="row">
				<label class="label"><html:text name="rdf.processed"/></label>
				<div id="processedNum" class="displayInline"></div>		
			</div>
			<div class="row">
				<label class="label"><html:text name="rdf.emitted"/></label>
				<div id="statementsNum" class="displayInline"></div>	
			</div>
			<div class="row">
				<label class="label"><html:text name="rdf.recordThroughput"/></label>
				<div id="processingThroughput" class="displayInline"></div>
				<html:text name="rdf.recordsSec"/>			
			</div>
			<div class="row">
				<label class="label"><html:text name="rdf.triplesThroughput"/></label>
				<div id="triplesThroughput" class="displayInline"></div>
				<html:text name="rdf.triplesSec"/>		
			</div>
		</div>
		<div class="buttons row">
			<img id="progressBar" class="displayNo" src="images/progressBar.gif" alt="" />
		</div>
	</html:form>
</div>
<div id="submitButtons" class="buttons row">
	<html:form id="submitButtonsForm">
		<html:submit id="backButton" action="manage" cssClass="fleft submitButton button" key="back" />	
		<html:submit id="nextButton" disabled="true" action="linking" cssClass="fright submitButton button"
			key="next" />
	</html:form>
</div>





