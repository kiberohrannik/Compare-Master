package com.kiber.comparemaster.function.json

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.findDocument
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.function.internal.ContentOperations
import org.apache.commons.text.StringEscapeUtils

object EscapeJsonFunction: JsonFilePairFunction {

    override fun apply(filePair: FilePair, project: Project) {
        escape(filePair.left(), project)
        escape(filePair.right(), project)
    }


    private fun escape(file: VirtualFile, project: Project) {
        val escapedJson = StringEscapeUtils.escapeJson(file.findDocument()!!.text)
        ContentOperations.setText(escapedJson, file, project)
    }
}