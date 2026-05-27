package com.kiber.comparemaster.json

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.StringWriter
import java.nio.charset.StandardCharsets
import javax.xml.XMLConstants
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

object XmlFormatter {

    fun toPrettyXml(xml: String): String {
        validateXmlType(xml)
        if (xml.isBlank()) {
            return xml
        }

        val document = parseXml(xml)
        stripFormattingWhitespace(document.documentElement)
        return serialize(document, pretty = true)
    }

    fun toRawXml(xml: String): String {
        validateXmlType(xml)
        if (xml.isBlank()) {
            return xml
        }

        val document = parseXml(xml)
        stripFormattingWhitespace(document.documentElement)
        return serialize(document, pretty = false)
            .replace(Regex("\\r?\\n\\s*"), "")
            .replace(Regex(">\\s+<"), "><")
    }

    fun toSortedXml(xml: String): String {
        validateXmlType(xml)
        if (xml.isBlank()) {
            return xml
        }

        val document = parseXml(xml)
        stripFormattingWhitespace(document.documentElement)
        sortElementChildren(document.documentElement)
        return serialize(document, pretty = true)
    }

    private fun validateXmlType(xml: String) {
        if (xml.contains("<!DOCTYPE")) {
            throw Exception("DOCTYPEs are not supported yet !!")
        }
    }

    private fun parseXml(xml: String): Document {
        val factory = DocumentBuilderFactory.newInstance().apply {
            isNamespaceAware = true
            setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)
            setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
            setFeature("http://xml.org/sax/features/external-general-entities", false)
            setFeature("http://xml.org/sax/features/external-parameter-entities", false)
            setExpandEntityReferences(false)
        }

        val builder = factory.newDocumentBuilder()
        return builder.parse(xml.byteInputStream(StandardCharsets.UTF_8))
            .also { it.documentElement.normalize() }
    }

    private fun sortElementChildren(element: Element) {
        val children = mutableListOf<Element>()
        var canSort = true

        var child = element.firstChild
        while (child != null) {
            if (child is Element) {
                sortElementChildren(child)
                children.add(child)
            } else if (!isIgnorableWhitespace(child)) {
                canSort = false
            }
            child = child.nextSibling
        }

        if (!canSort || children.size <= 1) {
            return
        }

        val sortedChildren = children.sortedWith(elementComparator)

        while (element.firstChild != null) {
            element.removeChild(element.firstChild)
        }

        sortedChildren.forEach { element.appendChild(it) }
    }

    private fun stripFormattingWhitespace(node: Node) {
        var child = node.firstChild
        while (child != null) {
            val next = child.nextSibling
            when {
                child is Element -> stripFormattingWhitespace(child)
                child.nodeType == Node.TEXT_NODE && child.textContent.isBlank() -> node.removeChild(child)
            }
            child = next
        }
    }

    private val elementComparator = compareBy<Element>(
        { it.tagName },
        { attributeSignature(it) },
        { leafTextValue(it) },
    )

    private fun attributeSignature(element: Element): String {
        val attributes = element.attributes ?: return ""
        if (attributes.length == 0) {
            return ""
        }

        return (0 until attributes.length)
            .mapNotNull { index -> attributes.item(index) }
            .map { attr -> attr.nodeName to (attr.nodeValue ?: "") }
            .sortedWith(compareBy<Pair<String, String>>({ it.first }, { it.second }))
            .joinToString(separator = "|") { (name, value) -> "$name=$value" }
    }

    private fun leafTextValue(element: Element): String {
        if (element.childNodes.length == 1 && element.firstChild?.nodeType == Node.TEXT_NODE) {
            return element.textContent.trim()
        }

        return ""
    }

    private fun isIgnorableWhitespace(node: Node): Boolean =
        node.nodeType == Node.TEXT_NODE && node.textContent.isBlank()

    private fun serialize(document: Document, pretty: Boolean): String {
        val transformer = TransformerFactory.newInstance().newTransformer().apply {
            setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no")
            setOutputProperty(OutputKeys.ENCODING, "UTF-8")
            setOutputProperty(OutputKeys.METHOD, "xml")
            setOutputProperty(OutputKeys.INDENT, if (pretty) "yes" else "no")
        }

        if (pretty) {
            runCatching {
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
            }
        }

        val writer = StringWriter()
        transformer.transform(DOMSource(document), StreamResult(writer))
        return writer.toString()
    }
}
