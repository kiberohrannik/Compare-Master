package com.kiber.comparemaster

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.project.Project
import com.intellij.ui.GuiUtils
import com.kiber.comparemaster.action.PluginAction
import com.kiber.comparemaster.config.SideMenuManager
import com.kiber.comparemaster.content.file.DefaultEditorsFileManager.getFilePair
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.ui.DefaultEditorManager.createEditor
import com.kiber.comparemaster.ui.EditorPanel
import com.kiber.comparemaster.ui.EditorsToolbarFactory
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.JSplitPane

class ToolWindowPanel(project: Project, filesPrefix: Long) : JPanel(BorderLayout()) {

    internal val editorFiles: FilePair = getFilePair(filesPrefix)!!
    internal val leftEditor: FileEditor = createEditor(editorFiles.left(), project)
    internal val rightEditor: FileEditor = createEditor(editorFiles.right(), project)

    init {
        val leftPanel = EditorPanel.createLeft(leftEditor)
        val rightPanel = EditorPanel.createRight(rightEditor)

        val splitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel)
        add(splitPane, BorderLayout.CENTER)

        GuiUtils.replaceJSplitPaneWithIDEASplitter(this)

        val actionList: MutableList<PluginAction> = mutableListOf()
        actionList.addAll(SideMenuManager.getActions())
        actionList.addAll(SideMenuManager.getPopups())
        actionList.addAll(SideMenuManager.getOtherActions())

        val toolbar = EditorsToolbarFactory.createToolbar(actionList, this)
        add(BorderLayout.WEST, toolbar.component)
    }
}