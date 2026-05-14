package com.kiber.comparemaster.content.parser.xml

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

class FilterXmlStepsOperationTest : FunSpec({

    test("should replace only existing XML values") {
        val source = """
            <root>
              <id>OLD</id>
              <status active="false">inactive</status>
              <keep>same</keep>
            </root>
        """.trimIndent()

        val target = """
            <root>
              <id>NEW</id>
              <status active="true">active</status>
              <added>mustNotAppear</added>
            </root>
        """.trimIndent()

        val result = XmlPatchOperations.toXmlPatch(source, target)
            .apply(FilterXmlStepsOperation.filterOnlyPresentValues())
            .patchXml()

        xpath("/root/id/text()", result) shouldBeEqual "NEW"
        xpath("/root/status/@active", result) shouldBeEqual "true"
        xpath("/root/status/text()", result) shouldBeEqual "active"
        xpath("/root/keep/text()", result) shouldBeEqual "same"
        xpath("count(/root/added)", result) shouldBeEqual "0"
    }

    test("should add absent XML list items only") {
        val source = """
            <root>
              <list>
                <item>1</item>
              </list>
              <value>source</value>
            </root>
        """.trimIndent()

        val target = """
            <root>
              <list>
                <item>1</item>
                <item>2</item>
                <item>3</item>
              </list>
              <value>target</value>
            </root>
        """.trimIndent()

        val result = XmlPatchOperations.toXmlPatch(source, target)
            .apply(FilterXmlStepsOperation.filterOnlyAbsentValues())
            .patchXml()

        xpath("count(/root/list/item)", result) shouldBeEqual "3"
        xpath("/root/list/item[2]/text()", result) shouldBeEqual "2"
        xpath("/root/list/item[3]/text()", result) shouldBeEqual "3"
        xpath("/root/value/text()", result) shouldBeEqual "source"
    }
})

private fun xpath(expression: String, xml: String): String {
    val doc = XmlPatchOperations.parseXml(xml)

    val xpath = XPathFactory.newInstance().newXPath()
    return xpath.evaluate(expression, doc, XPathConstants.STRING).toString()
}
