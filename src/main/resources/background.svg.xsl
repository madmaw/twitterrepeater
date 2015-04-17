<xsl:stylesheet version="1.0" xmlns="http://www.w3.org/2000/svg" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xlink= "http://www.w3.org/1999/xlink">

    <xsl:param name="width">506</xsl:param>
    <xsl:param name="height">253</xsl:param>
    <xsl:param name="animationDuration">1</xsl:param>

    <xsl:template match="/">

        <xsl:variable name="image-width" select="$height div 4"/>

        <svg width="{$width}" height="{$height}" viewBox="0 0 {$width} {$height}">
            <g>
                <rect x="0" y="0" width="{$width}" height="{$height}" fill="black"/>
                <image x="{$image-width div 2}" y="{$image-width div 2}" width="{$image-width}" height="{$image-width}" xlink:href="{twitter4j.StatusJSONImpl/user/profileImageUrl}"/>
                <text font-size="{$height div 20}" x="{$image-width * 2}" y="{$image-width}" fill="white">
                    <xsl:value-of select="twitter4j.StatusJSONImpl/user/name"/>
                </text>
            </g>
        </svg>

    </xsl:template>

</xsl:stylesheet>