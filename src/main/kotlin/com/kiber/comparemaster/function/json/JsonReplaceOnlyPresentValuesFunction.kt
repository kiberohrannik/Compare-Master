package com.kiber.comparemaster.function.json

import com.intellij.openapi.project.Project
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.content.parser.json.FilterStepsOperation
import com.kiber.comparemaster.content.parser.json.JsonPatchOperations
import com.kiber.comparemaster.function.internal.ContentOperations
import com.kiber.comparemaster.json.JsonFormatter

/*
 * Replaces different values of present fields to "source" (right editor)
 * from "target" a.k.a. "expected result" (left editor)
 *
 * Examples:
 *
 * ----------------------------------------------    -------------------------------------------------
 * |     Left editor        |   Right editor    |    |     Left editor        |   Right editor       |
 * |--------------------------------------------| => |---------------------------------------------- |
 * | "v1": "aaa"            | "v1": "bbb"       |    | "v1": "aaa"            | "v1": "aaa"          |
 * | "v2": "aaa"            | "v3": "bbb"       |    | "v2": "aaa"            | "v3": "bbb"          |
 * | "v3": [2, 3, 4, 5]     | "v3": [3, 4, 5]   |    | "v3": [2, 3, 4, 5]     | "v3": [2, 3, 4, 5]   |
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
class JsonReplaceOnlyPresentValuesFunction : JsonFilePairFunction {

    override fun apply(filePair: FilePair, project: Project) {
        //left is target
        //right is a source

        val source = filePair.rightText()
        val target = filePair.leftText()

        val result = JsonPatchOperations.toJsonPatch(source, target)
            .apply(FilterStepsOperation.filterOnlyPresentValues())
            .patchJson()

        val formatFunc = { text: String -> JsonFormatter.toPrettyJson(text) }

        ContentOperations.setAndFormat(result, formatFunc, filePair.right(), project)
        ContentOperations.setAndFormat(target, formatFunc, filePair.left(), project)
    }
}