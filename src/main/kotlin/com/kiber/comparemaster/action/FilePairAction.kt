package com.kiber.comparemaster.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.kiber.comparemaster.content.file.EFileTypes
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

    override fun update(event: AnActionEvent) {
        val fileType = event.project?.let { project ->
            runCatching { ComponentsResolver.getToolWindowPanel(project).editorFiles.file1.eType }.getOrNull()
        }

        event.presentation.isEnabled = fileType?.let { function.supports(it) } == true
    }

    override fun actionPerformed(event: AnActionEvent) {
        val toolWindowPanel = ComponentsResolver.getToolWindowPanel(getProject(event))
        val fileType = toolWindowPanel.editorFiles.file1.eType

        if (!function.supports(fileType)) {
            return
        }

        function.apply(toolWindowPanel.editorFiles, getProject(event))

        applyFinally?.invoke(toolWindowPanel.editorFiles)
    }

    fun supports(fileType: EFileTypes): Boolean = function.supports(fileType)
}
