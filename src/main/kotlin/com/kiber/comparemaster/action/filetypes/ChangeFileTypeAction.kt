package com.kiber.comparemaster.action.filetypes

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.JBSplitter
import com.kiber.comparemaster.ToolWindowPanel
import com.kiber.comparemaster.action.PluginAction
import com.kiber.comparemaster.content.file.DefaultEditorsFileManager.changeFileType
import com.kiber.comparemaster.content.file.DefaultEditorsFileManager.getFilePair
import com.kiber.comparemaster.content.file.EFileTypes
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.ui.DefaultEditorManager.createEditor
import com.kiber.comparemaster.ui.EditorPanel
import javax.swing.Icon
import javax.swing.JPanel

abstract class ChangeFileTypeAction(
    hint: String,
    icon: Icon,
    protected val type2Change: EFileTypes,
    protected val toolWindow: ToolWindow
) :
    PluginAction(hint, "", icon) {

    override fun actionPerformed(event: AnActionEvent) {
        val selectedTab = toolWindow.contentManager.selectedContent

        if (selectedTab != null && selectedTab.component is ToolWindowPanel) {
            val filesPrefix = selectedTab.component.name.toLong()
            changeFileType(filesPrefix, type2Change, getProject(event))

            val jPanel = (selectedTab.component.components.find { it is JBSplitter } as JPanel)

            val oldLeftEditor = jPanel.components.find { it.name == EditorPanel.LEFT_PANEL_NAME }
            val oldRightEditor = jPanel.components.find { it.name == EditorPanel.RIGHT_PANEL_NAME }

            val filePair: FilePair = getFilePair(filesPrefix)!!
            val newLeftEditor = createEditor(filePair.left(), getProject(event))
            val newRightEditor = createEditor(filePair.right(), getProject(event))

            jPanel.remove(oldLeftEditor)
            jPanel.remove(oldRightEditor)

            jPanel.add(EditorPanel.createLeft(newLeftEditor))
            jPanel.add(EditorPanel.createRight(newRightEditor))

            jPanel.revalidate()
            jPanel.repaint()
        }
    }
}