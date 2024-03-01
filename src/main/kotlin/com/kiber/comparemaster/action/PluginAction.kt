package com.kiber.comparemaster.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager
import com.kiber.comparemaster.PLUGIN_NAME
import com.kiber.comparemaster.ToolWindowPanel
import com.kiber.comparemaster.exception.NullComponentException
import javax.swing.Icon

abstract class PluginAction(
    hint: String,
    description: String = "",
    icon: Icon?
) : AnAction(hint, description, icon) {

    protected fun getToolWindowPanel(event: AnActionEvent): ToolWindowPanel {
        val toolWindow = ToolWindowManager.getInstance(getProject(event)).getToolWindow(PLUGIN_NAME)
            ?: throw NullComponentException(ToolWindow::class, PLUGIN_NAME)

        return toolWindow.contentManager.getContent(0)!!.component as ToolWindowPanel
    }

    protected fun getProject(event: AnActionEvent): Project {
        return event.project ?: throw NullComponentException(Project::class, null)
    }
}