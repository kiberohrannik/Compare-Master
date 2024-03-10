package com.kiber.comparemaster.content.parser.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.kiber.comparemaster.util.JsonGenerator
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldNotBeEqual

class FilterStepsOperationTest : FunSpec({

    val mapper = ObjectMapper()

    test("should replace only existing values") {
        val sourceObj = JsonGenerator.TestObject4()
        val targetObj = JsonGenerator.TestObject2()

        val sourceJson = mapper.writeValueAsString(sourceObj)
        val targetJson = mapper.writeValueAsString(targetObj)

        val resultJson: String = JsonPatchOperations.toJsonPatch(sourceJson, targetJson)
            .apply(FilterStepsOperation.filterOnlyPresentValues())
            .patchJson()

        val resultObj = mapper.readValue(resultJson, JsonGenerator.TestObject4::class.java)

        targetObj.field1 shouldBeEqual resultObj.field1
        targetObj.field2 shouldBeEqual resultObj.field2
        targetObj.fieldBool shouldBeEqual resultObj.fieldBool
        targetObj.fieldLong shouldBeEqual resultObj.fieldLong
        targetObj.fieldList shouldBeEqual resultObj.fieldList

        sourceObj.field3 shouldBeEqual resultObj.field3
        sourceObj.field4 shouldBeEqual resultObj.field4
    }

    test("should add absent values") {
        val sourceObj = JsonGenerator.TestObject4()
        val targetObj = JsonGenerator.TestObject2()

        val sourceJson = mapper.writeValueAsString(sourceObj)
        val targetJson = mapper.writeValueAsString(targetObj)

        val resultJson: String = JsonPatchOperations.toJsonPatch(sourceJson, targetJson)
            .apply(FilterStepsOperation.filterOnlyAbsentValues())
            .patchJson()

        val resultObj = mapper.readValue(resultJson, JsonGenerator.TestObject4::class.java)

        targetObj.field1 shouldNotBeEqual resultObj.field1
        targetObj.field2 shouldNotBeEqual resultObj.field2

        targetObj.fieldLong shouldNotBeEqual resultObj.fieldLong
        targetObj.fieldList shouldNotBeEqual resultObj.fieldList

        sourceObj.field3 shouldBeEqual  resultObj.field3
        sourceObj.field4 shouldBeEqual resultObj.field4
    }
})