<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:jw="http://jw.nju.edu.cn/schema" version="1.0">

    <xsl:template match="/">


            <课程成绩列表 xmlns="http://jw.nju.edu.cn/schema" xmlns:xsi="http://www.w3.org/1999/XSL/Transform">
                <xsl:for-each select="//jw:课程成绩">

                    <xsl:sort select="@课程编号"/>
                    <xsl:sort select="@成绩性质"/>
                    <xsl:sort select="./jw:成绩/jw:得分" data-type="number"/>
                    <xsl:copy-of select="."/>


                </xsl:for-each>
            </课程成绩列表>




    </xsl:template>

</xsl:stylesheet>