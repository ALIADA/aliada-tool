<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>
<script>
 $(function(){
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
				       $("#nextButton").show("slow");
				       $("#progressBar").hide();
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
		$("#checkInfo").show("slow");
		$('#progressBar').show();
		$('#checkRDFButton').hide();
		console.log("Checking");
		interval = setInterval( checkRDF, 1000 );		
	});
}); 
</script>
<html:hidden id="rdfizerJobId" name="rdfizerJobId" value="%{#session['rdfizerJobId']}" />
<ul class="breadcrumb">
	<li><html:a action="configure" cssClass="breadcrumb"><html:text name="organisation.title"/></html:a></li>
	<li><html:a action="manage" cssClass="breadcrumb"><html:text name="manage.title"/></html:a></li>
	<li><html:a action="conversion" cssClass="breadcrumb activeGreen"><html:text name="conversion.title"/></html:a></li>
</ul>
<h2 class="pageTitle center"><html:text name="conversion.title"/></h2>
<div class="form centered">
	<html:form id="conversion">
		<div id="rdfizePanel" class="content">
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
		<div id="checkInfo" class="displayNo content">
			<label class="row label"><html:text name="rdf.fileTo"/></label>		
			<html:property value="importFile"/>
			<div class="row label green">
				<html:text name="rdf.status"/>
				<div id="status" class="displayInline"></div>
			</div>
			<div class="row label">
				<html:text name="rdf.format"/>
				<div id="format" class="displayInline"></div>	
			</div>
			<div class="row label">
				<html:text name="rdf.records"/>	
				<div id="recordNum" class="displayInline"></div>	
			</div>
			<div class="row label">
				<html:text name="rdf.processed"/>
				<div id="processedNum" class="displayInline"></div>		
			</div>
			<div class="row label">
				<html:text name="rdf.emitted"/>
				<div id="statementsNum" class="displayInline"></div>	
			</div>
			<div class="row label">
				<html:text name="rdf.recordThroughput"/>
				<div id="processingThroughput" class="displayInline"></div>
				<html:text name="rdf.recordsSec"/>			
			</div>
			<div class="row label">
				<html:text name="rdf.triplesThroughput"/>
				<div id="triplesThroughput" class="displayInline"></div>
				<html:text name="rdf.triplesSec"/>		
			</div>
		</div>
		<div id="conversionButtons" class="buttons row">
				<img id="progressBar" class="displayNo" src="images/progressBar.gif" alt="" />
				<html:submit id="checkRDFButton" disabled="true" cssClass="submitButton button"
					key="check" onClick="return false;"/>
				<html:submit id="nextButton" disabled="true" action="linking" cssClass="submitButton button"
					key="next" />
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
</div>





