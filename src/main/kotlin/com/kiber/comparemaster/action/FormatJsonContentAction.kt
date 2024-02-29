package com.kiber.comparemaster.action

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.runUndoTransparentWriteAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.wm.ToolWindowManager
import com.kiber.comparemaster.EditorsPanel
import com.kiber.comparemaster.json.JsonFormatter

class FormatJsonContentAction : AnAction("Format", "", AllIcons.Actions.EnableNewUi) {

    override fun actionPerformed(event: AnActionEvent) {
        val toolWindow = ToolWindowManager.getInstance(event.project!!).getToolWindow("ECHO Viewer")
        val editorsPanel = toolWindow!!.contentManager.getContent(0)!!.component as EditorsPanel

        runUndoTransparentWriteAction {
            format(editorsPanel.editorFiles.leftDoc())
            format(editorsPanel.editorFiles.rightDoc())
        }
    }


    private fun format(document: Document) {
        val prettyJson = JsonFormatter.toPrettyJson(document.text)
        document.replaceString(0, document.text.length, prettyJson)
    }
}