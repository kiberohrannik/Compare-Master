package com.kiber.comparemaster.json

import groovy.xml.XmlParser
import groovy.xml.XmlUtil

object XmlFormatter {

    fun toPrettyXml(xml: String): String {
        return if (xml.isBlank()) {
            xml
        } else {
            val parsed = XmlParser().parseText(xml)
            return XmlUtil.serialize(parsed).replace(Regex("\\n\\s*\\n"), "\n")
                .replace("?>", "?>\n")
        }
    }

    fun toRawXml(xml: String): String {
        return if (xml.isBlank()) {
            xml
        } else {
            val parsed = XmlParser().parseText(xml)
            return XmlUtil.serialize(parsed).replace(Regex("\\n\\s*\\n"), "")
                .replace(Regex(">\\s*<"), "><")
        }
    }
}