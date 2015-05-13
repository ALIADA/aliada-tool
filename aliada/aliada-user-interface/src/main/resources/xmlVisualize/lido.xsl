<?xml version="1.0" encoding="ISO-8859-15"?>
<!DOCTYPE xsl:stylesheet [ <!ENTITY euro "&#8364;"><!ENTITY nbsp "&#160;"><!ENTITY oacute "&#243;"><!ENTITY eacute "&#233;"><!ENTITY iacute "&#237;"><!ENTITY uacute "&#250;"> ]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:lido="http://www.lido-schema.org"
							xmlns:cont="eu.aliada.inputValidation.CheckImportError">

    <xsl:template match="lido:lidoWrap">
    	<xsl:value-of select="cont:putLidoType()"/>
    	<xsl:variable name="total" select="count(lido:lido)" />
    	<table class="table">
			<tr class="backgroundGreen center">
				<th><xsl:value-of select="cont:getLocaleText('message.errorId')"/></th>
				<th><xsl:value-of select="cont:getLocaleText('message.errorType')"/></th>
				<th><xsl:value-of select="cont:getLocaleText('message.errorInfo')"/></th>
			</tr>
			<xsl:for-each select="lido:lido">
				<xsl:variable name="identificator" select="lido:administrativeMetadata/lido:recordWrap/lido:recordID"/>
				<xsl:variable name="lidoType" select="lido:descriptiveMetadata/lido:objectClassificationWrap/lido:objectWorkTypeWrap/lido:objectWorkType/lido:term"/>
				<xsl:variable name="title" select="lido:descriptiveMetadata/lido:objectIdentificationWrap/lido:titleWrap/lido:titleSet/lido:appellationValue"/>
				<tr>
					<td><xsl:value-of select="position()" />/<xsl:value-of select="$total"/></td>
					<td>
						<xsl:if test="$identificator">
							<xsl:value-of select='$identificator' />
						</xsl:if>
						<xsl:if test="not($identificator)">
							ERROR
							<xsl:value-of select="cont:increase()"/>
						</xsl:if>
					</td>
					<td>
						<xsl:if test="$lidoType">
							<xsl:value-of select='$lidoType' />
						</xsl:if>
						<xsl:if test="not($lidoType)">
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
