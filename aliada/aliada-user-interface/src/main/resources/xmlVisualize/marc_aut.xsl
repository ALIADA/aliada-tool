<?xml version="1.0" encoding="ISO-8859-15"?>
<!DOCTYPE xsl:stylesheet [ <!ENTITY euro "&#8364;"><!ENTITY nbsp "&#160;"><!ENTITY oacute "&#243;"><!ENTITY eacute "&#233;"><!ENTITY iacute "&#237;"><!ENTITY uacute "&#250;"> ]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">

    <xsl:template match="marc:collection">
    <xsl:variable name="total" select="count(marc:record)" />
    	<html>
        	<body>        
				<table border="1" cellpadding="0" cellspacing="0" width="100%">
					<xsl:for-each select="marc:record">
						<xsl:variable name="amicusNumber" select="marc:controlfield[@tag='001']"/>
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
						<td>
							<xsl:if test="$amicusNumber">
								<xsl:value-of select='$amicusNumber' />
							</xsl:if>
							<xsl:if test="not($amicusNumber)">
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
