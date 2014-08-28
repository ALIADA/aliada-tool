<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>

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
				<td><html:property value="importFile.getName()" /></td>
				<td><html:select name="templatesSelect"
						cssClass="inputForm" list="templates" /></td>
			</tr>
		</table>
		<div <html:if test="showRdfizerButton">class="displayInline"</html:if>
			<html:else>
			    class="displayNo"
			</html:else>>
			<html:submit action="RDFize" cssClass="submitButton button"
				key="RDF-ize" />
		</div>
	</div>
	<div id="conversionButtons" class="buttons row">
			<html:submit action="showTemplates" cssClass="submitButton button"
				key="templates" />
			<div <html:if test="showCheckButton">class="displayInline"</html:if>
				<html:else>
				    class="displayNo"
				</html:else>>
				<html:submit action="checkRDFizer" cssClass="submitButton button"
					key="check" />
			</div>
			<html:submit action="linking" cssClass="submitButton button"
			key="next" />
	</div>	
</html:form>





