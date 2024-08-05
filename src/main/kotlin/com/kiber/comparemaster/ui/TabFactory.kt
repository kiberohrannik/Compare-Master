package com.kiber.comparemaster.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.content.ContentFactory
import com.kiber.comparemaster.ToolWindowPanel
import org.jetbrains.kotlin.analysis.api.impl.barebone.annotations.NotThreadSafe

@NotThreadSafe
object TabFactory {

    private const val MAX_TAB_NUMBER = 9
    private var counter = 0

    fun createTab(project: Project, toolWindow: ToolWindow) {

        if(canAddNewTab(toolWindow)) {
            val tabName = "TAB-${++counter}"
            val panel = ToolWindowPanel(project)
            val content = ContentFactory.getInstance().createContent(panel, tabName, false)
            content.isCloseable = true

            toolWindow.contentManager.addContent(content)
            toolWindow.contentManager.setSelectedContent(content)
        }
    }


    private fun canAddNewTab(toolWindow: ToolWindow) =
        toolWindow.contentManager.contents
            .map { c -> c.component }
            .filterIsInstance<ToolWindowPanel>()
            .count() < MAX_TAB_NUMBER
}