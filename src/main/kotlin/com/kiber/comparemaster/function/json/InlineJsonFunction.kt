package com.kiber.comparemaster.function.json

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.findDocument
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.function.internal.ContentOperations
import com.kiber.comparemaster.json.JsonFormatter

object InlineJsonFunction: JsonFilePairFunction {

    override fun apply(filePair: FilePair, project: Project) {
        inline(filePair.left(), project)
        inline(filePair.right(), project)
    }


    private fun inline(file: VirtualFile, project: Project) {
        //TODO null handling
        val prettyJson = JsonFormatter.toRawJson(file.findDocument()!!.text)
        ContentOperations.setText(prettyJson, file, project)
    }
}