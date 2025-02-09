package com.kiber.comparemaster.json

import com.jetbrains.rd.util.first
import groovy.util.Node
import groovy.util.NodeList
import groovy.xml.XmlParser
import groovy.xml.XmlUtil

object XmlFormatter {

    //TODO add support for <!DOCTYPE
    private val parser = XmlParser(false, false)

    fun toPrettyXml(xml: String): String {
        validateXmlType(xml)
        return if (xml.isBlank()) xml else prettyPrint(parser.parseText(xml))
    }

    fun toRawXml(xml: String): String {
        validateXmlType(xml)
        return if (xml.isBlank()) xml else rawPrint(parser.parseText(xml))
    }

    fun toSortedXml(xml: String): String {
        validateXmlType(xml)
        return if (xml.isBlank()) {
            xml
        } else {
            val parsed = parser.parseText(xml)

            sortChildren(parsed)

            return prettyPrint(parsed)
        }
    }

    private fun sortChildren(node: Node) {
        node.children()
            .sortedBy { sortByName(it) }
            .forEach { child ->
                if (child is Node) {
                    sortChildren(child)
                }
            }

        // Sort the current node's children and update the node

        val groupedByTag = node.children()
            //Group by tag name and sort
            .groupBy { groupByName(it) }
            .toSortedMap()

        val withSortedAttrs = groupedByTag.map { entry ->
            entry.key to entry.value

                //Group by attribute name and sort (when have same tag name)
                .groupBy { groupByAttribute(it) }
                .toSortedMap()

                //Sort by attribute value (when have same attribute name)
                .flatMap { attrEntry ->
                    attrEntry.value.sortedBy { child ->
                        sortByAttribute(child)
                    }
                }
        }

        val sortedChildren = withSortedAttrs.flatMap { entry ->
            entry.second.sortedBy { sortByTagValue(it) }
        }

        node.children().clear()
        node.children().addAll(sortedChildren)
    }

    private fun validateXmlType(xml: String) {
        if (xml.contains("<!DOCTYPE")) {
            throw Exception("DOCTYPEs are not supported yet !!")
        }
    }

    private fun prettyPrint(parsed: Node) = XmlUtil.serialize(parsed)
        .replace(Regex("\\n\\s*\\n"), "\n")
        .replace("?>", "?>\n")

    private fun rawPrint(parsed: Node) = XmlUtil.serialize(parsed)
        .replace(Regex("\\n\\s*\\n"), "")
        .replace(Regex(">\\s*<"), "><")


    private fun sortByName(value: Any?): String =
        if (value is Node) {
            value.name() as String
        } else {
            ""
        }

    private fun sortByAttribute(value: Any?): String =
        if (value is Node) {
            value.attributes().first().value as String
        } else {
            value.hashCode().toString()
        }

    private fun sortByTagValue(value: Any?): String {
        var value4Sorting = ""
        //If tag has no attributes, we sort by tag value
        if (value is Node && value.attributes().isEmpty() && value.value() is NodeList) {
            val nodeList = (value.value() as NodeList)
            if (nodeList.isNotEmpty()) {
                value4Sorting = nodeList.first() as String
            }
        }
        return value4Sorting
    }


    private fun groupByName(value: Any?): String =
        if (value is Node) {
            value.name() as String
        } else {
            value.hashCode().toString()
        }

    private fun groupByAttribute(value: Any?): String =
        if (value is Node && value.attributes().isNotEmpty()) {
            value.attributes().first().key as String
        } else {
            value.hashCode().toString()
        }
}