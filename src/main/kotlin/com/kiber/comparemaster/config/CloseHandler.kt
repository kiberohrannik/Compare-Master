package com.kiber.comparemaster.config

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectCloseHandler
import com.kiber.comparemaster.content.file.JsonEditorsFileManager
import com.kiber.comparemaster.ui.ComponentsResolver

class CloseHandler : ProjectCloseHandler {

    override fun canClose(project: Project): Boolean {
        JsonEditorsFileManager.releaseAllFiles(project)

        val toolWindow = ComponentsResolver.getToolWindow(project)
        toolWindow.hide()

        ComponentsResolver.getAllToolWindowPanels(project)
            .forEach {panel ->
                panel.editorFactory.releaseEditor(panel.leftEditor)
                panel.editorFactory.releaseEditor(panel.rightEditor)
            }

        return true
    }
}