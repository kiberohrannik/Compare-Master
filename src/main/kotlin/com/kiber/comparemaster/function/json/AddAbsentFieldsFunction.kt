package com.kiber.comparemaster.function.json

import com.intellij.openapi.project.Project
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.content.parser.json.FilterStepsOperation
import com.kiber.comparemaster.content.parser.json.JsonPatchOperations
import com.kiber.comparemaster.function.internal.ContentOperations
import com.kiber.comparemaster.json.JsonFormatter

/*
 * Adds absent list items to "source" (right editor)
 * from "target" a.k.a. "expected result" (left editor)
 *
 * Examples:
 *
 * 1.
 * ----------------------------------------------    -------------------------------------------------
 * |     Left editor        |   Right editor    |    |     Left editor        |   Right editor       |
 * |--------------------------------------------| => |---------------------------------------------- |
 * | "list": [1, 2, 3, 4]   | "list": [2, 4]    |    | "list": [1, 2, 3, 4]   | "list": [1, 2, 3, 4] |
 * ----------------------------------------------    -------------------------------------------------
 *
 * 2.
 *  ----------------------------------------------    -------------------------------------------------
 *  |     Left editor        |   Right editor    |    |     Left editor        |   Right editor       |
 *  |--------------------------------------------|    |---------------------------------------------- |
 *  | "list": [              | "list": [         |    | "list": [              | "list": [            |
 *  |    {"a": "1"},         |    {"b": "2"}     | => |    {"a": "1"},         |    {"a": "1"},       |
 *  |    {"c": "3"},         |  ]                |    |    {"c": "3"},         |    {"c": "3"},       |
 *  |    {"b": "2"}          |                   |    |    {"b": "2"}          |    {"b": "2"}        |
 *  |  ]                     |                   |    |  ]                     |  ]                   |
 *  * --------------------------------------------    -------------------------------------------------
 */
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