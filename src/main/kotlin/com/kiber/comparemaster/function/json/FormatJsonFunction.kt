package com.kiber.comparemaster.function.json

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.findDocument
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.function.internal.ContentOperations
import com.kiber.comparemaster.json.JsonFormatter

object FormatJsonFunction: JsonFilePairFunction {

    override fun apply(filePair: FilePair, project: Project) {
        format(filePair.left(), project)
        format(filePair.right(), project)
    }


    private fun format(file: VirtualFile, project: Project) {
        val prettyJson = JsonFormatter.toPrettyJson(file.findDocument()!!.text)
        ContentOperations.setText(prettyJson, file, project)
    }
}