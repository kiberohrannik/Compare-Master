package com.kiber.comparemaster.function.xml

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import com.kiber.comparemaster.content.file.FilePair


class XmlReplaceOnlyPresentValuesFunction : XmlFilePairFunction {

    //TODO refactor it
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