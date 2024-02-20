package com.kiber.comparemaster.content.parser

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.flipkart.zjsonpatch.JsonDiff
import com.flipkart.zjsonpatch.JsonPatch

object JsonPatchOperations {

    private val mapper = ObjectMapper()

    //TODO Impl builder pattern here

    fun patchJson(initialJson: JsonNode, patchSteps: List<JsonPatchStep>): JsonNode {
        val patchNode = mapper.convertValue(patchSteps, JsonNode::class.java);
        return JsonPatch.apply(patchNode, initialJson)
    }

    fun toJsonPatch(sourceJson: JsonNode, targetJson: JsonNode): List<JsonPatchStep> {
        val patchJson = JsonDiff.asJson(sourceJson, targetJson)
        return mapper
            .reader()
            .forType(object : TypeReference<List<JsonPatchStep>>() {})
            .readValue<List<JsonPatchStep>>(patchJson)
    }

    fun filterOnlyPresentValues(patchSteps: List<JsonPatchStep>): List<JsonPatchStep> {
        //TODO check also that json does not contain node with ".*/\\d$" pattern like "salary/1":2456

        return patchSteps.filter {
            it.op == "replace" || it.path.matches(Regex(".*/\\d$"))
        }
    }

    class PatchBuilder {

    }
}