<xsl:stylesheet version="1.0" xmlns="http://www.w3.org/2000/svg" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xlink= "http://www.w3.org/1999/xlink">

    <xsl:param name="width">506</xsl:param>
    <xsl:param name="height">253</xsl:param>
    <xsl:param name="animationDuration">1</xsl:param>

    <xsl:template match="/">

        <svg width="{$width}" height="{$height}" viewBox="0 0 {$width} {$height}">
            <g>
                <text font-size="{$height div 10}" x="{$width div 2}" y="{$height div 2}" text-anchor="middle">

                    <animateTransform attributeName="transform"
                                      attributeType="XML"
                                      type="translate"
                                      from="0 {-$height div 3}"
                                      to="0 {$height div 4}"
                                      dur="{$animationDuration}s"
                                      additive="replace"
                                      fill="freeze"/>
                    <animate attributeName="fill"
                         from="black"
                         to="white"
                         dur="{$animationDuration}s"
                         additive="replace"
                         fill="freeze"/>
                    <xsl:value-of select="twitter4j.StatusJSONImpl/text"/>
                </text>
            </g>
        </svg>

    </xsl:template>

</xsl:stylesheet>