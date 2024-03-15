package com.kiber.comparemaster

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.kiber.comparemaster.action.ShowDiffAction
import com.kiber.comparemaster.config.PluginConfigurationProcessor
import com.kiber.comparemaster.config.SideMenuManager
import com.kiber.comparemaster.config.TopMenuManager
import com.kiber.comparemaster.ui.IconManager

const val PLUGIN_NAME = "C-Master"

class EditorsToolWindowFactory: ToolWindowFactory {

    override fun init(toolWindow: ToolWindow) {
        toolWindow.setIcon(IconManager.toolWindowIcon)
        super.init(toolWindow)
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        PluginConfigurationProcessor().loadAnnotated()

        TopMenuManager.sealed = true
        SideMenuManager.sealed = true

        val content = ContentFactory.getInstance().createContent(ToolWindowPanel(project), "Main", false)
        toolWindow.contentManager.addContent(content)

        toolWindow.setTitleActions(mutableListOf(ShowDiffAction()))
    }
}