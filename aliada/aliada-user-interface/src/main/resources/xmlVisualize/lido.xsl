<?xml version="1.0" encoding="ISO-8859-15"?>
<!DOCTYPE xsl:stylesheet [ <!ENTITY euro "&#8364;"><!ENTITY nbsp "&#160;"><!ENTITY oacute "&#243;"><!ENTITY eacute "&#233;"><!ENTITY iacute "&#237;"><!ENTITY uacute "&#250;"> ]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:lido="http://www.lido-schema.org">

    <xsl:template match="lido:lidoWrap">
    	<xsl:variable name="total" select="count(lido:lido)" />
    	<html>
        	<body>        
				<table border="1" cellpadding="0" cellspacing="0" width="100%">
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
							</xsl:if>
						</td>
						<td>
							<xsl:if test="$lidoType">
								<xsl:value-of select='$lidoType' />
							</xsl:if>
							<xsl:if test="not($lidoType)">
								ERROR
							</xsl:if>
						</td>

						<td>
							<xsl:if test="$title">
								<xsl:value-of select='$title' />
							</xsl:if>
							<xsl:if test="not($title)">
								ERROR
							</xsl:if>
						</td>
					</tr>
					</xsl:for-each>
				</table>
        	</body>
    	</html>
	</xsl:template>
</xsl:stylesheet>
