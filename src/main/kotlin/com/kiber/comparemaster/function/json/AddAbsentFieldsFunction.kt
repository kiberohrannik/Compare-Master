package com.kiber.comparemaster.function.json

import com.intellij.openapi.project.Project
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.content.parser.json.FilterStepsOperation
import com.kiber.comparemaster.content.parser.json.JsonPatchOperations
import com.kiber.comparemaster.function.internal.ContentOperations
import com.kiber.comparemaster.json.JsonFormatter

class AddAbsentFieldsFunction: JsonFilePairFunction {

    override fun apply(filePair: FilePair, project: Project) {
        //left is target
        //right is a source

        val source = filePair.rightText()
        val target = filePair.leftText()

        val result = JsonPatchOperations.toJsonPatch(source, target)
            .apply(FilterStepsOperation.filterOnlyAbsentValues())
            .patchJson()

        val formatFunc = { text: String -> JsonFormatter.toPrettyJson(text) }

        ContentOperations.setAndFormat(result, formatFunc, filePair.right(), project)
        ContentOperations.setAndFormat(target, formatFunc, filePair.left(), project)
    }
}