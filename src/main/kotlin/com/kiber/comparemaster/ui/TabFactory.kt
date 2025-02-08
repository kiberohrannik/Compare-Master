package com.kiber.comparemaster.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.content.ContentFactory
import com.kiber.comparemaster.ToolWindowPanel

object TabFactory {

    private const val MAX_TAB_NUMBER = 9

    private var counterMap: MutableMap<String, Int> = mutableMapOf()


    fun createTab(project: Project, toolWindow: ToolWindow) {
        counterMap.putIfAbsent(project.name, 1)

        if (canAddNewTab(toolWindow)) {
            val tabNumber = counterMap[project.name]!!
            val tabName = "TAB-$tabNumber"
            counterMap[project.name] = tabNumber + 1

            val panel = ToolWindowPanel(project)
            val content = ContentFactory.getInstance().createContent(panel, tabName, false)
            content.isCloseable = true

            toolWindow.contentManager.addContent(content)
            toolWindow.contentManager.setSelectedContent(content)
        }
    }

    fun releaseTabs(project: Project) {
        counterMap.remove(project.name)
    }


    private fun canAddNewTab(toolWindow: ToolWindow) =
        toolWindow.contentManager.contents
            .map { c -> c.component }
            .filterIsInstance<ToolWindowPanel>()
            .count() < MAX_TAB_NUMBER
}