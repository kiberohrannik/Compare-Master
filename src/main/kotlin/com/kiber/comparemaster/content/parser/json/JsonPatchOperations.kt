package com.kiber.comparemaster.content.parser.json

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.flipkart.zjsonpatch.JsonDiff

object JsonPatchOperations {

    internal val mapper = ObjectMapper()

    fun toJsonPatch(sourceJson: String, targetJson: String): StepsOperation {
        val sourceNode = mapper.readTree(sourceJson)
        val targetNode = mapper.readTree(targetJson)

        val patchJson = JsonDiff.asJson(sourceNode, targetNode)
        val steps = mapper
            .reader()
            .forType(object : TypeReference<List<JsonPatchStep>>() {})
            .readValue<List<JsonPatchStep>>(patchJson)

        return StepsOperation(steps)
    }
}