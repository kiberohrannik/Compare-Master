package com.kiber.comparemaster.content.parser.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.kiber.comparemaster.util.JsonGenerator
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldNotBeEqual
import io.kotest.matchers.shouldNotBe

//We are changing "source" json to "target" json
class FilterStepsOperationTest : FunSpec({

    val mapper = ObjectMapper()

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