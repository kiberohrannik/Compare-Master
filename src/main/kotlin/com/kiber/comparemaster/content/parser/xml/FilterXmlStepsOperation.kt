package com.kiber.comparemaster.content.parser.xml

import org.w3c.dom.Document

object FilterXmlStepsOperation {

    fun filterOnlyPresentValues(): (Collection<XmlPatchStep>, Document) -> XmlStepsOperation =
        { patchSteps, sourceNode ->
            XmlStepsOperation(
                patchSteps.filter {
                    it.op == XmlPatchOperations.OP_REPLACE_TEXT || it.op == XmlPatchOperations.OP_REPLACE_ATTR
                },
                sourceNode
            )
        }

    fun filterOnlyAbsentValues(): (Collection<XmlPatchStep>, Document) -> XmlStepsOperation =
        { patchSteps, sourceNode ->
            XmlStepsOperation(
                patchSteps.filter {
                    it.op == XmlPatchOperations.OP_ADD_NODE
                },
                sourceNode
            )
        }
}
