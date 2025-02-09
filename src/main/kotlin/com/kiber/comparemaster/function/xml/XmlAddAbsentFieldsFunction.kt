package com.kiber.comparemaster.function.xml

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.content.parser.json.FilterStepsOperation
import com.kiber.comparemaster.content.parser.json.JsonPatchOperations
import com.kiber.comparemaster.function.internal.ContentOperations
import com.kiber.comparemaster.json.JsonFormatter

class XmlAddAbsentFieldsFunction: XmlFilePairFunction {

    override fun apply(filePair: FilePair, project: Project) {
        val notification = Notification(
            "com.kiber.comparemaster",
            "Unsupported Function for XML",
            "This function is not yet supported for XML",
            NotificationType.WARNING
        )
        Notifications.Bus.notify(notification)
    }
}