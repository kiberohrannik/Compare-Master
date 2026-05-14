package com.kiber.comparemaster.content.parser.xml

import org.w3c.dom.Document
import org.w3c.dom.Node
import org.xmlunit.builder.DiffBuilder
import org.xmlunit.builder.Input
import org.xmlunit.diff.ComparisonType
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

object XmlPatchOperations {

    const val OP_REPLACE_TEXT = "replaceText"
    const val OP_REPLACE_ATTR = "replaceAttr"
    const val OP_ADD_NODE = "addNode"

    fun toXmlPatch(sourceXml: String, targetXml: String): XmlStepsOperation {
        val sourceDoc = parseXml(sourceXml)
        val targetDoc = parseXml(targetXml)

        val diff = DiffBuilder
            .compare(Input.fromDocument(sourceDoc))
            .withTest(Input.fromDocument(targetDoc))
            .ignoreWhitespace()
            .normalizeWhitespace()
            .build()

        val steps = mutableListOf<XmlPatchStep>()
        val addedPaths = mutableSetOf<String>()

        diff.differences.forEach { difference ->
            val comparison = difference.comparison
            val control = comparison.controlDetails
            val test = comparison.testDetails

            when (comparison.type) {
                ComparisonType.TEXT_VALUE -> {
                    if (
                        control.target != null &&
                        test.target != null &&
                        control.xPath != null &&
                        test.xPath != null &&
                        sameTextNodePath(control.xPath, test.xPath)
                    ) {
                        steps.add(
                            XmlPatchStep(
                                op = OP_REPLACE_TEXT,
                                path = control.xPath,
                                value = test.value?.toString() ?: ""
                            )
                        )
                    }
                }

                ComparisonType.ATTR_VALUE -> {
                    if (
                        control.target != null &&
                        test.target != null &&
                        control.xPath != null &&
                        test.xPath != null &&
                        control.xPath == test.xPath
                    ) {
                        steps.add(
                            XmlPatchStep(
                                op = OP_REPLACE_ATTR,
                                path = control.xPath,
                                value = test.value?.toString() ?: ""
                            )
                        )
                    }
                }

                ComparisonType.CHILD_LOOKUP -> {
                    val testPath = test.xPath
                    if (
                        control.target == null &&
                        test.target is Node &&
                        test.target.nodeType == Node.ELEMENT_NODE &&
                        testPath != null &&
                        addedPaths.add(testPath)
                    ) {
                        val (parentPath, nodeName, nodeIndex) = splitPath(testPath)
                        val xml = nodeToString(test.target)

                        if (parentPath != null && nodeName != null && xml != null) {
                            steps.add(
                                XmlPatchStep(
                                    op = OP_ADD_NODE,
                                    path = testPath,
                                    parentPath = parentPath,
                                    nodeXml = xml,
                                    nodeName = nodeName,
                                    nodeIndex = nodeIndex
                                )
                            )
                        }
                    }
                }

                else -> Unit
            }
        }

        return XmlStepsOperation(steps, sourceDoc)
    }

    internal fun parseXml(xml: String): Document {
        val factory = DocumentBuilderFactory.newInstance()
        factory.isNamespaceAware = true
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false)
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false)

        val builder = factory.newDocumentBuilder()
        return builder.parse(xml.byteInputStream()).also { it.documentElement.normalize() }
    }

    private fun nodeToString(node: Node?): String? {
        if (node == null) {
            return null
        }

        val transformer = TransformerFactory.newInstance().newTransformer().apply {
            setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes")
        }
        val writer = java.io.StringWriter()
        transformer.transform(DOMSource(node), StreamResult(writer))
        return writer.toString()
    }

    private fun splitPath(path: String): Triple<String?, String?, Int?> {
        val regex = Regex("^(.*)/([^/\\[]+)\\[(\\d+)]$")
        val result = regex.find(path) ?: return Triple(null, null, null)
        val parentPath = result.groupValues[1]
        val nodeName = result.groupValues[2]
        val index = result.groupValues[3].toIntOrNull()
        return Triple(parentPath, nodeName, index)
    }

    private fun sameTextNodePath(controlPath: String, testPath: String): Boolean {
        val suffix = Regex("/text\\(\\)\\[\\d+]\$")
        return controlPath.replace(suffix, "") == testPath.replace(suffix, "")
    }
}
