<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="html"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<script>

</script>
 
<html:text id="tab0Name" name="ins"></html:text>
<html:text id="tab1Name" name="prof"></html:text>
<html:text id="tab2Name" name="temp"></html:text>
<html:text id="tab3Name" name="us"></html:text>
<html:text id="tab4Name" name="dat"></html:text>
<html:text id="tab5Name" name="datrel"></html:text>
 
 <sj:tabbedpanel id="tabs" selectedTab="%{#session['ConfOpc']}">
        <sj:tab id="tab1" target="div1" label="%{#tab0Name}"/>
        <sj:tab id="tab2" target="div2" label="%{#tab1Name}"/>
        <sj:tab id="tab3" target="div3" label="%{#tab2Name}"/>
        <sj:tab id="tab4" target="div4" label="%{#tab3Name}"/>
        <sj:tab id="tab5" target="div5" label="%{#tab4Name}"/>
        <sj:tab id="tab6" target="div6" label="%{#tab5Name}"/>
        <div id="div1" class="displayNo">
			<html:include value="institution.jsp" />
        </div>
        <div id="div2" class="displayNo">
         	<html:include value="profiles.jsp" />
        </div>
        <div id="div3" class="displayNo">
        	<html:include value="templates.jsp" />
        </div>
        <div id="div4" class="displayNo">
        	<html:include value="users.jsp" />
        </div>
        <div id="div5" class="displayNo">
       		<%-- <html:include value="datasets.jsp" /> --%>
       		<html:form>
	       		<ul class="breadcrumb">
					<span class="breadCrumb"><html:text name="home"/></span>
					<li><span class="breadcrumb activeGreen"><html:text name="confdatasets"/></span></li>
				</ul>
			</html:form>
			<div class="content">
				<html:form>
					<div id="datasetConf" class="display buttons row">
						<html:text name="descriptionDataset"></html:text>
						<html:a action="showDatasets" cssClass="fright"><img alt="help" src="images/arrow.png"></img></html:a>
						<br/>
					</div>
	       		</html:form>
	       	</div>
        </div>
        <div id="div6" class="displayNo">
        	<html:include value="externalDatasets.jsp" />
        </div>
 </sj:tabbedpanel>
 <html:form>
 	<br/>
 	<html:submit action="manage" cssClass="submitButton button fright"
			key="back" />
	<br/>
 </html:form>

