package com.kiber.comparemaster

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.kiber.comparemaster.action.ShowDiffAction


class EditorsToolWindowFactory: ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val content = ContentFactory.getInstance().createContent(ToolWindowPanel(project), "Tab 1", false)
        toolWindow.contentManager.addContent(content)

        toolWindow.setTitleActions(mutableListOf(ShowDiffAction()))
    }
}