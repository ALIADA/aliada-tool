<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>
<script>
 $(function(){
	var checkRDF = function(){
		console.log("checking RDF");
		var rdfizerJobId = $("#rdfizerJobId").val();
		var urlPath = "http://aliada.scanbit.net:8080/aliada-rdfizer-1.0/jobs/"+rdfizerJobId;
	    $.ajax({
	      type: "GET",
	      url: urlPath,
	      dataType : 'xml',
	      success: function(xml) {
               var completed = $(xml).find("completed").text();
               if(completed=="true"){
            	   $("#status").replaceWith("Completed");
		   		   console.log("interval stopped");
		   		   clearInterval(interval);
               }
               else{
            	   $("#status").append("Running");            	   
               }
               var format = $(xml).find("format").text();
               $("#format").replaceWith(format);
               var recordNum = $(xml).find("total-records-count").text();
               $("#recordNum").replaceWith(recordNum);
               var processedNum = $(xml).find("processed-records-count").text();
               $("#processedNum").replaceWith(processedNum);
               var statementsNum = $(xml).find("output-statements-count").text();
               $("#statementsNum").replaceWith(statementsNum);
               var processingThroughput = $(xml).find("records-throughput").text();
               $("#processingThroughput").replaceWith(processingThroughput);
               var triplesThroughput = $(xml).find("triples-throughput").text();
               $("#triplesThroughput").replaceWith(triplesThroughput);
               console.log(completed);
	    	  console.log(xml);
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
		$("#checkInfo").show("slow");
		$('#checkRDFButton').prop("disabled",true);
		console.log("Checking");
		var interval = setInterval( checkRDF, 1000 );		
	});
}); 
</script>
<html:hidden id="rdfizerJobId" name="rdfizerJobId" value="%{#session['rdfizerJobId']}" />
<ul class="breadcrumb">
	<li><html:a action="configure" cssClass="breadcrumb"><html:text name="organisation.title"/></html:a></li>
	<li><html:a action="manage" cssClass="breadcrumb"><html:text name="manage.title"/></html:a></li>
	<li><html:a action="conversion" cssClass="breadcrumb activeGreen"><html:text name="conversion.title"/></html:a></li>
</ul>
<h2 class="pageTitle"><html:text name="conversion.title"/></h2>
<html:form id="conversion">
	<div class="content">
		<label class="row label"><html:text name="conversion.filesTo"/></label>
		<table class="table">
			<tr class="backgroundGreen center">
				<th><label class="bold"><html:text name="conversion.input"/></label></th>
				<th><label class="bold"><html:text name="conversion.template"/></label></th>
			</tr>
			<tr>
				<td>
					<div <html:if test="!showRdfizerButton && !showCheckButton">class="displayInline"</html:if>
						<html:else>
						    class="displayNo"
						</html:else>>
						<html:text name="conversion.not.file"/>
					</div>
					<div <html:if test="!showRdfizerButton && !showCheckButton">class="displayNo"</html:if>
						<html:else>
						    class="displayInline"
						</html:else>>
						<html:property value="importFile.getName()" />
					</div>
				</td>
				<td><html:select name="templatesSelect"
						cssClass="inputForm" list="templates" />					
				</td>
				<td><html:submit action="showTemplates" cssClass="submitButton button"
						key="templates" />
				</td>
			</tr>			
		</table>
		<html:submit id="rdfizeButton" action="RDFize" disabled="true" cssClass="submitButton button"
			key="RDF-ize"/>
	</div>
	<div id="checkInfo" class="displayNo content fright">
		<label class="row label"><html:text name="rdf.fileTo"/></label>		
		<html:property value="importFile"/>
		<div class="row label green">
			<html:text name="rdf.status"/>
			<label id="status"></label>
		</div>
		<div class="row label">
			<html:text name="rdf.format"/>
			<label id="format"></label>	
		</div>
		<div class="row label">
			<html:text name="rdf.records"/>	
			<label id="recordNum"></label>	
		</div>
		<div class="row label">
			<html:text name="rdf.processed"/>
			<label id="processedNum"></label>		
		</div>
		<div class="row label">
			<html:text name="rdf.emitted"/>
			<label id="statementsNum"></label>	
		</div>
		<div class="row label">
			<html:text name="rdf.recordThroughput"/>
			<label id="processingThroughput"></label>
			<html:text name="rdf.recordsSec"/>			
		</div>
		<div class="row label">
			<html:text name="rdf.triplesThroughput"/>
			<label id="triplesThroughput"></label>
			<html:text name="rdf.triplesSec"/>		
		</div>
	</div>
	<div id="conversionButtons" class="buttons row">
			<html:submit id="checkRDFButton" disabled="true" cssClass="submitButton button"
				key="check" />
			<html:submit id="nextButton" disabled="true" action="linking" cssClass="submitButton button"
				key="next" />
			<html:if test="state>1">
				<script>
					$('#checkRDFButton').prop("disabled",true);
			    	$("#nextButton").removeClass("button");
			    	$("#nextButton").addClass("buttonGreen")
					$('#nextButton').prop("disabled",false);
			    	$("#nextButton").show("slow");
			    </script>				
			</html:if>
			<html:if test="showRdfizerButton">
				<script>
			    	$("#rdfizeButton").removeClass("button");
			    	$("#rdfizeButton").addClass("buttonGreen");
					$('#rdfizeButton').prop("disabled",false);
			    	$("#rdfizeButton").show("slow");
			    </script>				
			</html:if>
			<html:if test="showCheckButton">
				<script>
			    	$("#checkRDFButton").removeClass("button");
			    	$("#checkRDFButton").addClass("buttonGreen");
					$('#checkRDFButton').prop("disabled",false);
			    	$("#checkRDFButton").show("slow");
			    </script>				
			</html:if>
	</div>	
</html:form>





