package com.kiber.comparemaster.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.wm.ToolWindowManager
import com.kiber.comparemaster.EditorsPanel
import com.kiber.comparemaster.function.FilePairFunction
import javax.swing.Icon

class FilePairAction(
    hint: String,
    description: String = "",
    icon: Icon,
    private val function: FilePairFunction,
) : AnAction(hint, description, icon) {

    override fun actionPerformed(event: AnActionEvent) {
        //TODO null handling
        val toolWindow = ToolWindowManager.getInstance(event.project!!).getToolWindow("ECHO Viewer")
        val editorsPanel = toolWindow!!.contentManager.getContent(0)!!.component as EditorsPanel

        function.apply(editorsPanel.editorFiles, event.project!!)
    }
}