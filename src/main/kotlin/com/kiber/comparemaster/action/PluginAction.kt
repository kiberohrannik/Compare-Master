package com.kiber.comparemaster.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.kiber.comparemaster.exception.NullComponentException
import javax.swing.Icon

abstract class PluginAction(
    hint: String,
    description: String = "",
    icon: Icon?
) : AnAction(hint, description, icon) {

    protected fun getProject(event: AnActionEvent): Project {
        return event.project ?: throw NullComponentException(Project::class, null)
    }
}