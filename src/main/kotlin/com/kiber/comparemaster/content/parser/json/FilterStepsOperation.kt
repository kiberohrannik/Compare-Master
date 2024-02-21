package com.kiber.comparemaster.content.parser.json

object FilterStepsOperation : StepsOperation(listOf()) {
    fun filterOnlyPresentValues(): StepsOperation =
        StepsOperation(
            patchSteps.filter {
                //TODO check also that json does not contain node with ".*/\\d$" pattern like "salary/1":2456
                it.op == "replace" || it.path.matches(Regex(".*/\\d$"))
            }
        )
}