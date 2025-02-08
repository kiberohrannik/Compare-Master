package com.kiber.comparemaster.function.xml

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.findDocument
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.function.internal.ContentOperations
import com.kiber.comparemaster.json.XmlFormatter

object FormatXmlFunction : XmlFilePairFunction {

    override fun apply(filePair: FilePair, project: Project) {
        format(filePair.left(), project)
        format(filePair.right(), project)
    }


    private fun format(file: VirtualFile, project: Project) {
        val prettyXml = XmlFormatter.toPrettyXml(file.findDocument()!!.text)
        ContentOperations.setText(prettyXml, file, project)
    }
}