package com.kiber.comparemaster

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.openapi.wm.ex.ToolWindowEx
import com.kiber.comparemaster.action.CreateNewTabAction
import com.kiber.comparemaster.action.filetypes.JsonFileTypeAction
import com.kiber.comparemaster.action.filetypes.XmlFileTypeAction
import com.kiber.comparemaster.config.PluginConfigurationProcessor
import com.kiber.comparemaster.config.SideMenuManager
import com.kiber.comparemaster.config.TopMenuManager
import com.kiber.comparemaster.ui.IconManager
import com.kiber.comparemaster.ui.TabFactory

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




        toolWindow.setTitleActions(mutableListOf(
            XmlFileTypeAction(toolWindow),
            JsonFileTypeAction(toolWindow)
        ))



        TabFactory.createTab(project, toolWindow)

        (toolWindow as ToolWindowEx).setTabActions(CreateNewTabAction())
    }
}