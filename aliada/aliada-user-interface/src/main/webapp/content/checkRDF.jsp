<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>

<h2 class="pageTitle"><html:text name="rdf.title"/></h2>
<html:form id="checkRDF">
	<div class="content">
		<label class="row label"><html:text name="rdf.fileTo"/></label>		
		<html:property value="importFile"/>
		<div class="row">
			<label class="label green"><html:text name="rdf.status"/>
				<html:property value="status"/>
			</label>
		</div>
		<div class="row">
			<label class="label"><html:text name="rdf.format"/></label>
			<html:property value="format"/>			
		</div>
		<div class="row">
			<label class="label"><html:text name="rdf.records"/></label>
			<html:property value="recordNum"/>			
		</div>
		<div class="row">
			<label class="label"><html:text name="rdf.processed"/></label>
			<html:property value="processedNum"/>			
		</div>
		<div class="row">
			<label class="label"><html:text name="rdf.emitted"/></label>
			<html:property value="statementsNum"/>			
		</div>
		<div class="row">
			<label class="label"><html:text name="rdf.recordThroughput"/></label>
			<html:property value="processingThroughput"/> <html:text name="rdf.recordsSec"/>			
		</div>
		<div class="row">
			<label class="label"><html:text name="rdf.triplesThroughput"/></label>
			<html:property value="triplesThroughput"/> <html:text name="rdf.triplesSec"/>		
		</div>
	</div>
			
	<div id="checkRDFButtons" class="buttons row">
		<html:form id="checkRDFButtonsForm">
			<html:submit action="conversion" cssClass="submitButton button"
				key="back" />
			<html:submit id="checkRDFButton" action="checkRDFizer" cssClass="submitButton buttonGreen"
				key="check" />
			<html:submit id="nextButton" action="linking" cssClass="displayNo submitButton buttonGreen"
				key="next" />
			<html:if test="state>1">
				<script>
			    	$("#nextButton").show("slow");
			    	$("#checkRDFButton").hide();
			    </script>	
			</html:if>
		</html:form>
	</div>	
</html:form>





