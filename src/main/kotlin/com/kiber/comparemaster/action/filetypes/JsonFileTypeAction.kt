package com.kiber.comparemaster.action.filetypes

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.wm.ToolWindow
import com.kiber.comparemaster.ToolWindowPanel
import com.kiber.comparemaster.action.PluginAction
import com.kiber.comparemaster.content.file.DefaultEditorsFileManager
import com.kiber.comparemaster.content.file.EFileTypes

class JsonFileTypeAction(private val toolWindow: ToolWindow) :
    PluginAction("Use JSON format", "", AllIcons.FileTypes.Json) {

    override fun actionPerformed(event: AnActionEvent) {
        val selectedTab = toolWindow.contentManager.selectedContent

        if (selectedTab != null) {
            if (selectedTab.component is ToolWindowPanel) {
                val filesPrefix = selectedTab.component.name.toLong()
                DefaultEditorsFileManager.changeFileType(filesPrefix, EFileTypes.JSON, getProject(event))
            }
        }
    }
}