package com.kiber.comparemaster.content.parser.xml

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import javax.xml.namespace.QName
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

class XmlStepsOperation(
    internal val patchSteps: Collection<XmlPatchStep>,
    private val sourceDocument: Document
) {

    fun apply(op: (Collection<XmlPatchStep>, Document) -> XmlStepsOperation): XmlStepsOperation =
        op.invoke(patchSteps, sourceDocument)

    fun patchXml(): String {
        patchSteps.forEach { step ->
            when (step.op) {
                XmlPatchOperations.OP_REPLACE_TEXT,
                XmlPatchOperations.OP_REPLACE_ATTR -> replaceValue(step)

                XmlPatchOperations.OP_ADD_NODE -> addNode(step)
            }
        }

        return toXmlString(sourceDocument)
    }

    private fun replaceValue(step: XmlPatchStep) {
        val node = evaluate(step.path, XPathConstants.NODE) as? Node ?: return
        node.nodeValue = step.value ?: ""
    }

    private fun addNode(step: XmlPatchStep) {
        val parentPath = step.parentPath ?: return
        val parent = evaluate(parentPath, XPathConstants.NODE) as? Node ?: return
        val newNode = parseFragment(step.nodeXml ?: return) ?: return

        val imported = sourceDocument.importNode(newNode, true)
        val sibling = findSiblingByIndex(parent, step.nodeName, step.nodeIndex)

        if (sibling != null) {
            parent.insertBefore(imported, sibling)
        } else {
            parent.appendChild(imported)
        }
    }

    private fun findSiblingByIndex(parent: Node, nodeName: String?, nodeIndex: Int?): Node? {
        if (nodeName == null || nodeIndex == null || nodeIndex <= 0) {
            return null
        }

        val sameNameChildren = mutableListOf<Node>()
        val children = parent.childNodes
        for (i in 0 until children.length) {
            val child = children.item(i)
            if (child is Element && child.nodeName == nodeName) {
                sameNameChildren.add(child)
            }
        }

        val targetIndex = nodeIndex - 1
        return if (targetIndex in sameNameChildren.indices) sameNameChildren[targetIndex] else null
    }

    private fun parseFragment(xmlFragment: String): Node? {
        val wrapped = "<root>$xmlFragment</root>"
        val factory = DocumentBuilderFactory.newInstance()
        factory.isNamespaceAware = true
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false)
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false)

        val builder = factory.newDocumentBuilder()
        val doc = builder.parse(wrapped.byteInputStream())
        val children = doc.documentElement.childNodes
        for (i in 0 until children.length) {
            val child = children.item(i)
            if (child.nodeType == Node.ELEMENT_NODE) {
                return child
            }
        }
        return null
    }

    private fun evaluate(xpathExpression: String, type: QName): Any? {
        val xpath = XPathFactory.newInstance().newXPath()
        return xpath.evaluate(xpathExpression, sourceDocument, type)
    }

    private fun toXmlString(document: Document): String {
        val writer = java.io.StringWriter()
        val transformer = TransformerFactory.newInstance().newTransformer().apply {
            setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no")
        }

        transformer.transform(DOMSource(document), StreamResult(writer))
        return writer.toString()
    }
}
