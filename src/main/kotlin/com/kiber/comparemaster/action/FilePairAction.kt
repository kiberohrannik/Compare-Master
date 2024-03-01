package com.kiber.comparemaster.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.wm.ToolWindowManager
import com.kiber.comparemaster.ToolWindowPanel
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.function.FilePairFunction
import javax.swing.Icon

class FilePairAction(
    hint: String,
    description: String = "",
    icon: Icon?,
    private val function: FilePairFunction,
    private val applyFinally: ((filePair: FilePair) -> Unit)? = null
) : AnAction(hint, description, icon) {

    override fun actionPerformed(event: AnActionEvent) {
        //TODO null handling
        val toolWindow = ToolWindowManager.getInstance(event.project!!).getToolWindow("ECHO Viewer")
        val toolWindowPanel = toolWindow!!.contentManager.getContent(0)!!.component as ToolWindowPanel

        function.apply(toolWindowPanel.editorFiles, event.project!!)

        applyFinally?.invoke(toolWindowPanel.editorFiles)
    }
}