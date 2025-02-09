package com.kiber.comparemaster.function.xml

import com.intellij.openapi.project.Project
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.function.internal.ContentOperations
import com.kiber.comparemaster.json.JsonFormatter
import com.kiber.comparemaster.json.XmlFormatter

object SortXmlFunction : XmlFilePairFunction {

    override fun apply(filePair: FilePair, project: Project) {
        val formatFunc = { text: String -> XmlFormatter.toPrettyXml(text) }

        ContentOperations.setAndFormat(
            XmlFormatter.toSortedXml(filePair.leftText()),
            formatFunc,
            filePair.left(),
            project
        )

        ContentOperations.setAndFormat(
            XmlFormatter.toSortedXml(filePair.rightText()),
            formatFunc,
            filePair.right(),
            project
        )
    }
}