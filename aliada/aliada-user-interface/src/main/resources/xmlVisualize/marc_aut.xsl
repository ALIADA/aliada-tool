<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xsl:stylesheet [ <!ENTITY euro "&#8364;"><!ENTITY nbsp "&#160;"><!ENTITY oacute "&#243;"><!ENTITY eacute "&#233;"><!ENTITY iacute "&#237;"><!ENTITY uacute "&#250;"> ]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim"
				xmlns:cont="eu.aliada.inputValidation.CheckImportError">

    <xsl:template match="marc:collection">
    	<xsl:value-of select="cont:putAuthorityType()"/>
    	<xsl:variable name="total" select="count(marc:record)" />
    	<table class="table">
			<tr class="backgroundGreen center">
				<th><xsl:value-of select="cont:getLocaleText('message.errorId')"/></th>
				<th><xsl:value-of select="cont:getLocaleText('message.errorType')"/></th>
				<th><xsl:value-of select="cont:getLocaleText('message.errorCode')"/></th>
				<th><xsl:value-of select="cont:getLocaleText('message.errorInfo')"/></th>
			</tr>
			<xsl:for-each select="marc:record">
				<xsl:variable name="authorityNumber" select="marc:controlfield[@tag='001']"/>
				<xsl:variable name="recordType" select="substring(marc:leader,7,1)"/>
				<xsl:variable name="title" select="marc:datafield[@tag='100'] |
													marc:datafield[@tag='110'] |
													marc:datafield[@tag='111'] |
													marc:datafield[@tag='130'] |
													marc:datafield[@tag='150'] |
													marc:datafield[@tag='151'] |
													marc:datafield[@tag='153'] |
													marc:datafield[@tag='155'] |
													marc:datafield[@tag='180'] "/>
				<tr>
					<td><xsl:value-of select="position()" />/<xsl:value-of select="$total"/></td>
					<td><xsl:value-of select="$recordType"/><xsl:value-of select="cont:getFileType($recordType)"/></td>
					<td>
						<xsl:if test="$authorityNumber">
							<xsl:value-of select='$authorityNumber' />
						</xsl:if>
						<xsl:if test="not($authorityNumber)">
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
