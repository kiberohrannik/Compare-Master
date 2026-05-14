package com.kiber.comparemaster.action.filetypes

import com.intellij.openapi.project.Project
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.wm.ToolWindow
import com.intellij.testFramework.LightVirtualFile
import com.intellij.ui.JBSplitter
import com.kiber.comparemaster.ToolWindowPanel
import com.kiber.comparemaster.content.file.DefaultEditorsFileManager.changeFileType
import com.kiber.comparemaster.content.file.DefaultEditorsFileManager.getFilePair
import com.kiber.comparemaster.content.file.EFileTypes
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.ui.DefaultEditorManager.createEditor
import com.kiber.comparemaster.ui.EditorPanel
import javax.swing.JPanel

object FileTypeSwitcher {

    fun apply(toolWindow: ToolWindow, newType: EFileTypes, project: Project, anyFileType: FileType? = null) {
        val selectedTab = toolWindow.contentManager.selectedContent

        if (selectedTab != null && selectedTab.component is ToolWindowPanel) {
            val filesPrefix = selectedTab.component.name.toLong()
            changeFileType(filesPrefix, newType, project)

            val jPanel = (selectedTab.component.components.find { it is JBSplitter } as JPanel)

            val oldLeftEditor = jPanel.components.find { it.name == EditorPanel.LEFT_PANEL_NAME }
            val oldRightEditor = jPanel.components.find { it.name == EditorPanel.RIGHT_PANEL_NAME }

            val filePair: FilePair = getFilePair(filesPrefix)!!
            if (newType == EFileTypes.ANY && anyFileType != null) {
                (filePair.file1.internalFile as? LightVirtualFile)?.setFileType(anyFileType)
                (filePair.file2.internalFile as? LightVirtualFile)?.setFileType(anyFileType)
            }
            val newLeftEditor = createEditor(filePair.left(), project)
            val newRightEditor = createEditor(filePair.right(), project)

            jPanel.remove(oldLeftEditor)
            jPanel.remove(oldRightEditor)

            jPanel.add(EditorPanel.createLeft(newLeftEditor))
            jPanel.add(EditorPanel.createRight(newRightEditor))

            jPanel.revalidate()
            jPanel.repaint()
        }
    }
}
