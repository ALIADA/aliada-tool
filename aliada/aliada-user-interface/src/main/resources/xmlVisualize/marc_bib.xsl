<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xsl:stylesheet [ <!ENTITY euro "&#8364;"><!ENTITY nbsp "&#160;"><!ENTITY oacute "&#243;"><!ENTITY eacute "&#233;"><!ENTITY iacute "&#237;"><!ENTITY uacute "&#250;"> ]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim"
		xmlns:cont="eu.aliada.inputValidation.CheckImportError">
    <xsl:variable name="errorCount" select="0"/>

	<xsl:template name="increaseErrorCount">
    	<xsl:value-of select="$errorCount+1"/>
  	</xsl:template>

    <xsl:template match="marc:collection">
    <xsl:variable name="total" select="count(marc:record)" />
		<table class="table">
			<tr class="backgroundGreen center">
				<th><xsl:value-of select="cont:getLocaleText('message.errorId')"/></th>
				<th><xsl:value-of select="cont:getLocaleText('message.errorCode')"/></th>
				<th><xsl:value-of select="cont:getLocaleText('message.errorInfo')"/></th>
			</tr>
			<xsl:for-each select="marc:record">
				<xsl:variable name="amicusNumber" select="marc:controlfield[@tag='001']"/>
				<xsl:variable name="title" select="marc:datafield[@tag='245']"/>
			<tr>
				<td><xsl:value-of select="position()" />/<xsl:value-of select="$total"/></td>
				<td>
					<xsl:if test="$amicusNumber">
						<xsl:value-of select='$amicusNumber' />
					</xsl:if>
					<xsl:if test="not($amicusNumber)">
						ERROR
						<xsl:value-of select="cont:increase()"/>
					</xsl:if>
				</td>
				<td>
					<xsl:if test="$title">
						<xsl:value-of select='$title' />
					</xsl:if>
					<xsl:if test="not($title)">
						ERROR
						<xsl:value-of select="cont:increase()"/>
					</xsl:if>
				</td>
						
			</tr>
			</xsl:for-each>
		</table>
		<xsl:value-of select="cont:getLocaleText('message.errorTotal')"/><xsl:value-of select="cont:getCount()"/>
		<br/><br/>
	</xsl:template>
</xsl:stylesheet>
