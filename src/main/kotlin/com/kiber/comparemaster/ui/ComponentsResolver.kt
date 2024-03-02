package com.kiber.comparemaster.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager
import com.kiber.comparemaster.PLUGIN_NAME
import com.kiber.comparemaster.ToolWindowPanel
import com.kiber.comparemaster.exception.NullComponentException

object ComponentsResolver {

    fun getToolWindowPanel(project: Project): ToolWindowPanel {
        return getToolWindow(project).contentManager.getContent(0)!!.component as ToolWindowPanel
    }

    fun getToolWindow(project: Project): ToolWindow {
        return ToolWindowManager.getInstance(project).getToolWindow(PLUGIN_NAME)
            ?: throw NullComponentException(ToolWindow::class, PLUGIN_NAME)
    }
}