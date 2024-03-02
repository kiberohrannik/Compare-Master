package com.kiber.comparemaster.content.parser.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.kiber.comparemaster.config.ActionsLoader
import com.kiber.comparemaster.config.PluginConfigurationProcessor
import com.kiber.comparemaster.util.JsonGenerator
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.lang.instrument.Instrumentation
import kotlin.reflect.full.functions

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

        targetObj.field1 shouldBe resultObj.field1
        targetObj.field2 shouldBe resultObj.field2
        targetObj.fieldBool shouldBe resultObj.fieldBool
        targetObj.fieldLong shouldBe resultObj.fieldLong
        targetObj.fieldList shouldBe resultObj.fieldList

        sourceObj.field3 shouldBe resultObj.field3
        sourceObj.field4 shouldBe resultObj.field4
    }
})