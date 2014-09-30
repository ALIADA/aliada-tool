<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>
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
	<div id="conversionButtons" class="buttons row">
			<html:submit id="checkRDFButton" disabled="true" action="checkRDFizer" cssClass="submitButton button"
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





