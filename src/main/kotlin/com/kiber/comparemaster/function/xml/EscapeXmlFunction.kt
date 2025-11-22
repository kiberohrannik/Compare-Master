package com.kiber.comparemaster.function.xml

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.findDocument
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.function.internal.ContentOperations
import org.apache.commons.text.StringEscapeUtils

object EscapeXmlFunction : XmlFilePairFunction {

    override fun apply(filePair: FilePair, project: Project) {
        format(filePair.left(), project)
        format(filePair.right(), project)
    }


    private fun format(file: VirtualFile, project: Project) {
        val escapedXml = StringEscapeUtils.escapeXml11((file.findDocument()!!.text))
        ContentOperations.setText(escapedXml, file, project)
    }
}