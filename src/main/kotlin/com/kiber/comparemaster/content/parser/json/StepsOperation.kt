package com.kiber.comparemaster.content.parser.json

import com.fasterxml.jackson.databind.JsonNode
import com.flipkart.zjsonpatch.JsonPatch

open class StepsOperation(protected val patchSteps: Collection<JsonPatchStep>) {

    //TODO or create supplier & make operations lazy like streams
    fun apply(op: StepsOperation): StepsOperation = op

    fun patchJson(initialJson: String): String {
        val initialNode = JsonPatchOperations.mapper.readTree(initialJson)
        val patchNode = JsonPatchOperations.mapper.convertValue(patchSteps, JsonNode::class.java)
        val patched = JsonPatch.apply(patchNode, initialNode)
        return patched.toString()
    }
}