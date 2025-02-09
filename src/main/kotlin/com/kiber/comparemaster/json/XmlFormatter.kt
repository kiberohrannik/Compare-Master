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
        return if (xml.isBlank()) {
            xml
        } else {
            prettyPrint(parser.parseText(xml))
        }
    }

    fun toRawXml(xml: String): String {
        validateXmlType(xml)
        return if (xml.isBlank()) {
            xml
        } else {
            val parsed = parser.parseText(xml)
            return XmlUtil.serialize(parsed)
                .replace(Regex("\\n\\s*\\n"), "")
                .replace(Regex(">\\s*<"), "><")
        }
    }

    //TODO add sorting by attribute values
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

    //TODO refactor it
    private fun sortChildren(node: Node) {
        node.children()
            .sortedBy { ch ->
                if (ch is Node) {
                    ch.name() as String
                } else {
                    ""
                }
            }
            .forEach { child ->
                if (child is Node) {
                    sortChildren(child)
                }
            }

        // Sort the current node's children and update the node
        var sortedChildren = node.children()
            .sortedBy { ch ->
                if (ch is Node) {
                    ch.name() as String
                } else {
                    ""
                }
            }

        val sortedChildren1 = sortedChildren
            .groupBy { ch ->
                if (ch is Node) {
                    ch.name() as String
                } else {
                    ch.hashCode().toString()
                }
            }.toSortedMap()
            .map { entry ->

               entry.key to entry.value
                    .groupBy { ch ->
                    if (ch is Node && ch.attributes().isNotEmpty()) {
                        ch.attributes().first().key as String
                    } else {
                        ch.hashCode().toString()
                    }

                }.toSortedMap()
                    .flatMap { attrEntry ->
                        attrEntry.value.sortedBy { ch ->
                            if (ch is Node) {
                                ch.attributes().first().value as String
                            } else {
                                ch.hashCode().toString()
                                    .also { println("Hashcode $it") }
                            }
                        }
                    }

            }.flatMap { entry -> entry.second
                .sortedBy { tag ->
                    if(tag is Node) {
//                        if(tag.attributes().isEmpty() && tag.value() !is Node && tag.value() !is NodeList) {
                        if(tag.attributes().isEmpty() && tag.value() is NodeList) {
                            val nodeList = (tag.value() as NodeList)
                                    if(nodeList.isNotEmpty()) {
                                        nodeList.first() as String
                                    } else {
                                        ""
                                    }

                        } else {
                            ""
                        }
                    } else {
                        ""
                    }
                }

            }

        node.children().clear()
        node.children().addAll(sortedChildren1)
    }

    private fun validateXmlType(xml: String) {
        if (xml.contains("<!DOCTYPE")) {
            throw Exception("DOCTYPEs are not supported yet !!")
        }
    }

    private fun prettyPrint(parsed: Node) = XmlUtil.serialize(parsed)
        .replace(Regex("\\n\\s*\\n"), "\n")
        .replace("?>", "?>\n")
}