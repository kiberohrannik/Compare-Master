package com.kiber.comparemaster.content.parser.json

import com.fasterxml.jackson.databind.JsonNode

//TODO check also that json does not contain node with ".*/\\d$" pattern like "salary/1":2456

object FilterStepsOperation {

    /**
     * This method filters JSON patch "replace" operations
     * (see https://datatracker.ietf.org/doc/html/rfc6902#section-4.3)
     */
    fun filterOnlyPresentValues(): (Collection<JsonPatchStep>, JsonNode) -> StepsOperation =
        { patchSteps, sourceNode ->
            StepsOperation(
                patchSteps.filter {
                    it.op == "replace" || it.path.matches(Regex(".*/\\d$"))
                },
                sourceNode
            )
        }

    /**
     * This method filters JSON patch "add" operations
     * (see https://datatracker.ietf.org/doc/html/rfc6902#section-4.1)
     */
    fun filterOnlyAbsentValues(): (Collection<JsonPatchStep>, JsonNode) -> StepsOperation =
        { patchSteps, sourceNode ->
            StepsOperation(
                patchSteps.filter {
                    it.op == "add" && it.path.matches(Regex(".*\\/\\d+$"))
                },
                sourceNode
            )
        }
}

