package com.kiber.comparemaster.function.json

import com.intellij.openapi.project.Project
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.function.internal.ContentOperations
import com.kiber.comparemaster.json.JsonFormatter

object SortJsonFunction : JsonFilePairFunction {

    override fun apply(filePair: FilePair, project: Project) {
        val formatFunc = { text: String -> JsonFormatter.toPrettyJson(text) }

        ContentOperations.setAndFormat(
            JsonFormatter.toSortedJson(filePair.leftText()),
            formatFunc,
            filePair.left(),
            project
        )

        ContentOperations.setAndFormat(
            JsonFormatter.toSortedJson(filePair.rightText()),
            formatFunc,
            filePair.right(),
            project
        )
    }
}