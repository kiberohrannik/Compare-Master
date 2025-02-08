package com.kiber.comparemaster.content.parser.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.intellij.codeInsight.editorActions.XmlEditUtil
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.xml.util.XmlStringUtil
import com.intellij.xml.util.XmlUtil
import com.jetbrains.rd.util.string.printToString
import com.kiber.comparemaster.json.XmlFormatter
import com.kiber.comparemaster.util.JsonGenerator
import groovy.xml.XmlNodePrinter
import groovy.xml.XmlParser
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldNotBeEqual
import io.kotest.matchers.shouldNotBe
import org.codehaus.plexus.util.xml.PrettyPrintXMLWriter
import org.codehaus.plexus.util.xml.XmlWriterUtil
import java.io.ByteArrayOutputStream
import java.io.PrintWriter

//We are changing "source" json to "target" json
class FilterStepsOperationTest : FunSpec({

    val mapper = ObjectMapper()

    test("vfdvbfd") {
        val xml = "<?xml version=\"1.0\"?>\n" +
                "<customers>\n" +
                "   <customer id=\"55000\">\n" +
                "      <name>Charter Group</name>\n" +
                "      <address>\n" +
                "         <street>100 Main</street>\n" +
                "         <city>Framingham</city>\n" +
                "         <state>MA</state>\n" +
                "         <zip>01701</zip>\n" +
                "      </address>\n" +
                "      <address>\n" +
                "         <street>720 Prospect</street>\n" +
                "         <city>Framingham</city>\n" +
                "         <state>MA</state>\n" +
                "         <zip>01701</zip>\n" +
                "      </address>\n" +
                "      <address>\n" +
                "         <street>120 Ridge</street>\n" +
                "         <state>MA</state>\n" +
                "         <zip>01760</zip>\n" +
                "      </address>\n" +
                "   </customer>\n" +
                "</customers>"

        val res1 = XmlFormatter.toRawXml(xml)
        println(res1)

        println("\n\n")

        val res2 = XmlFormatter.toPrettyXml(xml)
        println(res2)
    }




    test("should replace only existing values").config(invocations = 3) {
        val sourceObj = JsonGenerator.TestObject4()
        val targetObj = JsonGenerator.TestObject2()

        val sourceJson = mapper.writeValueAsString(sourceObj)
        val targetJson = mapper.writeValueAsString(targetObj)

        val stepsOperation = JsonPatchOperations.toJsonPatch(sourceJson, targetJson)
            .apply(FilterStepsOperation.filterOnlyPresentValues())
        stepsOperation.patchSteps.size shouldNotBe 0

        val resultJson: String = stepsOperation.patchJson()

        val resultObj = mapper.readValue(resultJson, JsonGenerator.TestObject4::class.java)

        targetObj.field1 shouldBeEqual resultObj.field1
        targetObj.field2 shouldBeEqual resultObj.field2
        targetObj.fieldBool shouldBeEqual resultObj.fieldBool
        targetObj.fieldLong shouldBeEqual resultObj.fieldLong
        targetObj.fieldList shouldBeEqual resultObj.fieldList

        sourceObj.field3 shouldBeEqual resultObj.field3
        sourceObj.field4 shouldBeEqual resultObj.field4
    }

    test("should add absent values").config(invocations = 3)  {
        val targetListField = JsonGenerator.getList(10)
        val sourceListField = targetListField.subList(0, 5)

        val sourceObj = JsonGenerator.TestObject4(fieldList = sourceListField, field1 = "1234")
        val targetObj = JsonGenerator.TestObject2(fieldList = targetListField, field1 = "")

        val sourceJson = mapper.writeValueAsString(sourceObj)
        val targetJson = mapper.writeValueAsString(targetObj)

        val stepsOperation = JsonPatchOperations.toJsonPatch(sourceJson, targetJson)
            .apply(FilterStepsOperation.filterOnlyAbsentValues())
        stepsOperation.patchSteps.size shouldNotBe 0

        val resultJson: String = stepsOperation.patchJson()

        val resultObj = mapper.readValue(resultJson, JsonGenerator.TestObject4::class.java)

        //fields that have same field-names but different values should NOT be replaced
        targetObj.field1 shouldNotBeEqual resultObj.field1
        targetObj.field2 shouldNotBeEqual resultObj.field2
        targetObj.fieldLong shouldNotBeEqual resultObj.fieldLong

        //items SHOULD BE added to fieldList
        targetObj.fieldList shouldBeEqual resultObj.fieldList

        //field3 and field4 should be left untouched
        sourceObj.field3 shouldBeEqual resultObj.field3
        sourceObj.field4 shouldBeEqual resultObj.field4
    }
})