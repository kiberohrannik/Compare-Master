package com.kiber.comparemaster

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.kiber.comparemaster.action.ShowDiffAction
import com.kiber.comparemaster.config.SideMenuManager
import com.kiber.comparemaster.config.TopMenuManager

const val PLUGIN_NAME = "Compare Master"

class EditorsToolWindowFactory: ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        TopMenuManager.sealed = true
        SideMenuManager.sealed = true

        val content = ContentFactory.getInstance().createContent(ToolWindowPanel(project), "Main", false)
        toolWindow.contentManager.addContent(content)

        toolWindow.setTitleActions(mutableListOf(ShowDiffAction()))
    }
}