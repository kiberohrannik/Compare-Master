package com.kiber.comparemaster.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager
import com.kiber.comparemaster.PLUGIN_NAME
import com.kiber.comparemaster.ToolWindowPanel
import com.kiber.comparemaster.exception.NullComponentException

object ComponentsResolver {

    fun getToolWindowPanel(project: Project): ToolWindowPanel {
        return getToolWindow(project).contentManager.contents
            .filter { content -> content.isSelected }
            .map { c -> c.component }
            .filterIsInstance<ToolWindowPanel>()
            .first()
    }

    fun getAllToolWindowPanels(project: Project): List<ToolWindowPanel> {
        return getToolWindow(project).contentManager.contents
            .map { c -> c.component }
            .filterIsInstance<ToolWindowPanel>()
    }

    fun getToolWindow(project: Project): ToolWindow {
        return ToolWindowManager.getInstance(project).getToolWindow(PLUGIN_NAME)
            ?: throw NullComponentException(ToolWindow::class, PLUGIN_NAME)
    }

    fun addToolWindowTab(project: Project) {
        TabFactory.createTab(project, getToolWindow(project))
    }
}