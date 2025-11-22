package com.kiber.comparemaster.function.json

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.findDocument
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.function.internal.ContentOperations
import org.apache.commons.text.StringEscapeUtils

object UnescapeJsonFunction: JsonFilePairFunction {

    override fun apply(filePair: FilePair, project: Project) {
        unescape(filePair.left(), project)
        unescape(filePair.right(), project)
    }


    private fun unescape(file: VirtualFile, project: Project) {
        val unescapedJson = StringEscapeUtils.unescapeJson(file.findDocument()!!.text)
        ContentOperations.setText(unescapedJson, file, project)
    }
}