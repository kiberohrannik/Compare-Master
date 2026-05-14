package com.kiber.comparemaster.function.xml

import com.intellij.openapi.project.Project
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.content.parser.xml.FilterXmlStepsOperation
import com.kiber.comparemaster.content.parser.xml.XmlPatchOperations
import com.kiber.comparemaster.function.internal.ContentOperations
import com.kiber.comparemaster.json.XmlFormatter


class XmlReplaceOnlyPresentValuesFunction : XmlFilePairFunction {

    override fun apply(filePair: FilePair, project: Project) {
        //left is target
        //right is source
        val source = filePair.rightText()
        val target = filePair.leftText()

        val result = XmlPatchOperations.toXmlPatch(source, target)
            .apply(FilterXmlStepsOperation.filterOnlyPresentValues())
            .patchXml()

        val formatFunc = { text: String -> XmlFormatter.toPrettyXml(text) }

        ContentOperations.setAndFormat(result, formatFunc, filePair.right(), project)
        ContentOperations.setAndFormat(target, formatFunc, filePair.left(), project)
    }
}
