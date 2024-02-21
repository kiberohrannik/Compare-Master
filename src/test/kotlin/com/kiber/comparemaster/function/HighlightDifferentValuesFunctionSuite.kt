package com.kiber.comparemaster.function

import com.fasterxml.jackson.databind.ObjectMapper
import com.kiber.comparemaster.content.parser.json.FilterStepsOperation
import com.kiber.comparemaster.content.parser.json.JsonPatchOperations
import org.junit.Test


class HighlightDifferentValuesFunctionSuite {

    private val mapper = ObjectMapper()


    @Test
    fun `replace only values`() {
        val json1 = "{\n" +
                "  \"employee\": {\n" +
                "    \"name\": \"sonoo1\",\n" +
                "    \"salary\": 56000,\n" +
                "    \"married\": true,\n" +
                "    \"married_1\": true,\n" +
                "    \"married_23\": true,\n" +
                "    \"trash\": [1, 2, 3]\n" +
                "  }\n" +
                "}"
        val json2 = "{\"employee\":{\"name\":\"sonoo\",\"salary\":56000,\"married\":true, \"trash\": [1, 2]}}"

        val replaced = JsonPatchOperations.toJsonPatch(json1, json2)
            .apply(FilterStepsOperation.filterOnlyPresentValues())
            .patchJson(json1)
        println("replaced = $replaced")
    }
}