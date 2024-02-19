package com.kiber.comparemaster

import com.intellij.diff.editor.DiffVirtualFile
import com.intellij.diff.editor.SimpleDiffVirtualFile
import com.intellij.diff.tools.util.DiffDataKeys
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.DumbAwareAction

class OpenDiffInEditorAction : AnAction() {
    override fun update(e: AnActionEvent) {
        val project = e.getData(CommonDataKeys.PROJECT)
        val request = e.getData(DiffDataKeys.DIFF_REQUEST)

        e.presentation.isEnabledAndVisible = project != null && request != null
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.getRequiredData(CommonDataKeys.PROJECT)
        val request = e.getRequiredData(DiffDataKeys.DIFF_REQUEST)

        val file: DiffVirtualFile = SimpleDiffVirtualFile(request)
        FileEditorManager.getInstance(project).openFile(file, true)
    }
}