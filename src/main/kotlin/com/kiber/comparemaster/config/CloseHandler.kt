package com.kiber.comparemaster.config

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectCloseHandler
import com.kiber.comparemaster.content.file.JsonEditorsFileManager
import com.kiber.comparemaster.ui.ComponentsResolver

class CloseHandler : ProjectCloseHandler {

    override fun canClose(project: Project): Boolean {
        JsonEditorsFileManager.releaseFiles(project)

        val toolWindow = ComponentsResolver.getToolWindow(project)
        toolWindow.hide()

        val panel = ComponentsResolver.getToolWindowPanel(project)
        panel.editorFactory.releaseEditor(panel.leftEditor)
        panel.editorFactory.releaseEditor(panel.rightEditor)

//        TopMenuManager.sealed = false
//        SideMenuManager.sealed = false

        return true
    }
}