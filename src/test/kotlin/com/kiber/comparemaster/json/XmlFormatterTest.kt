package com.kiber.comparemaster.json

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldStartWith

class XmlFormatterTest : FunSpec({

    test("pretty print keeps the xml structure readable") {
        val input = load("/xml/01_format_inline_messy.xml")

        val result = XmlFormatter.toPrettyXml(input)

        result shouldStartWith "<?xml"
        result shouldContain "\n<root>"
        result shouldContain "\n  <user"
    }

    test("raw print removes formatting whitespace") {
        val input = load("/xml/01_format_inline_messy.xml")

        val result = XmlFormatter.toRawXml(input)

        result shouldStartWith "<?xml"
        result shouldContain "<root><user"
        result shouldContain "</meta></root>"
    }

    test("sorted xml reorders child elements by name") {
        val input = load("/xml/02_sort_children_unsorted.xml")

        val result = XmlFormatter.toSortedXml(input)

        childElementNames(result) shouldContainExactly listOf("alpha", "beta", "items", "zeta")
    }

    test("sorted xml reorders same tags by attributes") {
        val input = load("/xml/03_sort_same_tag_attr_order.xml")

        val result = XmlFormatter.toSortedXml(input)

        result.indexOf("code=\"A\"") shouldBeLessThan result.indexOf("code=\"B\"")
        result.indexOf("code=\"B\"") shouldBeLessThan result.indexOf("code=\"C\"")
    }

    test("pretty formatting is idempotent") {
        val input = """
            <food>
              <calories>900</calories>

              <description>Light Belgian waffles covered with strawberries.</description>

              <name>Strawberry Belgian Waffles</name>
              <price>$7.95</price>
            </food>
        """.trimIndent()

        val once = XmlFormatter.toPrettyXml(input)
        val twice = XmlFormatter.toPrettyXml(once)

        twice shouldContain once
        twice shouldBe once
    }
})

private fun load(path: String): String =
    requireNotNull(XmlFormatterTest::class.java.getResource(path)) { "Missing test resource: $path" }
        .readText()

private fun childElementNames(xml: String): List<String> {
    val document = com.kiber.comparemaster.content.parser.xml.XmlPatchOperations.parseXml(xml)
    val root = document.documentElement
    val names = mutableListOf<String>()
    var node = root.firstChild
    while (node != null) {
        if (node.nodeType == org.w3c.dom.Node.ELEMENT_NODE) {
            names.add(node.nodeName)
        }
        node = node.nextSibling
    }
    return names
}
