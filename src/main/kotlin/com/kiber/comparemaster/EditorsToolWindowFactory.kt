package com.kiber.comparemaster

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.GuiUtils
import com.intellij.ui.content.ContentFactory

class EditorsToolWindowFactory: ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val panel = EditorsPanel(project)
        GuiUtils.replaceJSplitPaneWithIDEASplitter(panel)

        val content = ContentFactory.getInstance().createContent(panel, "", false)

        toolWindow.contentManager.addContent(content)
    }
}