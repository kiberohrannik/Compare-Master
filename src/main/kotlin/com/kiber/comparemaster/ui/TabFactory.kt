package com.kiber.comparemaster.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.IdeFocusManager
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.content.ContentFactory
import com.kiber.comparemaster.ToolWindowPanel

object TabFactory {

    private val TABS = arrayOf("Tab-A", "Tab-B", "Tab-C", "Tab-D", "Tab-E", "Tab-F")
    private val tabsIterator: Iterator<String> = TABS.iterator()

    fun createTab(project: Project, toolWindow: ToolWindow) {

        if(tabsIterator.hasNext()) {
            val tabName = tabsIterator.next()
            val panel = ToolWindowPanel(project)
            val content = ContentFactory.getInstance().createContent(panel, tabName, false)

            toolWindow.contentManager.addContent(content)
            toolWindow.contentManager.setSelectedContent(content)

            IdeFocusManager.getInstance(project).requestFocus(panel.leftEditor.component, true)
        }
    }
}