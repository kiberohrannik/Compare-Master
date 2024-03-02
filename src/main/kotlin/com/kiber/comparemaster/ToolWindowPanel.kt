package com.kiber.comparemaster

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.project.Project
import com.intellij.ui.GuiUtils
import com.kiber.comparemaster.action.PluginAction
import com.kiber.comparemaster.config.SideMenuManager
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.content.file.JsonEditorsFileManager
import com.kiber.comparemaster.ui.DefaultEditorManager
import com.kiber.comparemaster.ui.EditorPanel
import com.kiber.comparemaster.ui.EditorsToolbarFactory
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.JSplitPane

class ToolWindowPanel(project: Project) : JPanel(BorderLayout()) {

    internal val editorFactory = DefaultEditorManager(project)

    internal val editorFiles: FilePair = JsonEditorsFileManager.getFilePair()
    internal val leftEditor: FileEditor = editorFactory.createEditor(editorFiles.left())
    internal val rightEditor: FileEditor = editorFactory.createEditor(editorFiles.right())

    init {
        val leftPanel = EditorPanel.create(leftEditor)
        val rightPanel = EditorPanel.create(rightEditor)

        val splitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel)
        add(splitPane, BorderLayout.CENTER)

        GuiUtils.replaceJSplitPaneWithIDEASplitter(this)

        val actionList: MutableList<PluginAction> = mutableListOf()
        actionList.addAll(SideMenuManager.getActions())
        actionList.addAll(SideMenuManager.getPopups())

        val toolbar = EditorsToolbarFactory.createToolbar(actionList, this)
        add(BorderLayout.WEST, toolbar.component)
    }
}