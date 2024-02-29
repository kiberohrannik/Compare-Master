package com.kiber.comparemaster.action

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.runUndoTransparentWriteAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.wm.ToolWindowManager
import com.kiber.comparemaster.ToolWindowPanel
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.json.JsonFormatter
import com.kiber.comparemaster.ui.EditorsButton
import com.kiber.comparemaster.ui.diff.SimpleDiffPanel
import javax.swing.JButton

class ShowDiffAction : AnAction("Show diff", "", AllIcons.Actions.Diff) {

    override fun actionPerformed(event: AnActionEvent) {
        val toolWindow = ToolWindowManager.getInstance(event.project!!).getToolWindow("ECHO Viewer")
        val toolWindowPanel = toolWindow!!.contentManager.getContent(0)!!.component as ToolWindowPanel

        SimpleDiffPanel.showDiff(event.project!!,
            toolWindowPanel.editorFiles.left(), toolWindowPanel.editorFiles.right())
    }
}