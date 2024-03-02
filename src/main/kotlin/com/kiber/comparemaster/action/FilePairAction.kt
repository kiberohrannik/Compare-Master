package com.kiber.comparemaster.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.function.FilePairFunction
import com.kiber.comparemaster.ui.ComponentsResolver
import javax.swing.Icon

class FilePairAction(
    hint: String,
    description: String = "",
    icon: Icon?,
    private val function: FilePairFunction,
    private val applyFinally: ((filePair: FilePair) -> Unit)? = null
) : PluginAction(hint, description, icon) {

    override fun actionPerformed(event: AnActionEvent) {
        val toolWindowPanel = ComponentsResolver.getToolWindowPanel(getProject(event))

        function.apply(toolWindowPanel.editorFiles, getProject(event))

        applyFinally?.invoke(toolWindowPanel.editorFiles)
    }
}