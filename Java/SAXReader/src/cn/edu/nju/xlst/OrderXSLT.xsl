<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:template match="/">

        <xsl:element name="课程成绩列表">


            <xsl:for-each select="//课程成绩">
                <xsl:sort select="@课程编号"/>
                <xsl:sort select="@成绩性质"/>
                <xsl:sort select="./成绩/得分"/>
                <xsl:copy-of select="."/>


            </xsl:for-each>


        </xsl:element>
    </xsl:template>

</xsl:stylesheet>