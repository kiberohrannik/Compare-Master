package com.kiber.comparemaster.content.parser.json

import com.fasterxml.jackson.databind.JsonNode
import com.flipkart.zjsonpatch.JsonPatch

class StepsOperation(
    private val patchSteps: Collection<JsonPatchStep>,
    private var sourceNode: JsonNode
) {
    fun apply(op: (Collection<JsonPatchStep>, JsonNode) -> StepsOperation): StepsOperation =
        op.invoke(patchSteps, sourceNode)

    fun patchJson(): String {
        val patchNode = JsonPatchOperations.mapper.convertValue(patchSteps, JsonNode::class.java)
        val patched = JsonPatch.apply(patchNode, sourceNode)
        return patched.toString()
    }
}


