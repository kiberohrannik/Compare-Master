package com.kiber.comparemaster.content.parser

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.flipkart.zjsonpatch.JsonDiff

object JsonPatchMapper {

    private val mapper = ObjectMapper()

    fun toJsonPatch(json1: String, json2: String): Set<JsonPatchOperation> {
        val sourceNode = mapper.readTree(json1)
        val targetNode = mapper.readTree(json2)

        val patchJson = JsonDiff.asJson(sourceNode, targetNode)

        return mapper
            .reader()
            .forType(object : TypeReference<Set<JsonPatchOperation>>() {})
            .readValue<Set<JsonPatchOperation>>(patchJson)
    }
}