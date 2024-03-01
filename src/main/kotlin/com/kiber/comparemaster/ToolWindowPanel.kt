package com.kiber.comparemaster

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.ui.GuiUtils
import com.kiber.comparemaster.action.PluginAction
import com.kiber.comparemaster.config.SideMenuManager
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.content.file.JsonEditorsFileFactory
import com.kiber.comparemaster.ui.CompareEditorFactory
import com.kiber.comparemaster.ui.EditorPanel
import com.kiber.comparemaster.ui.EditorsToolbarFactory
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.JSplitPane

class ToolWindowPanel(project: Project) : JPanel(BorderLayout()) {

    private val editorFactory = CompareEditorFactory(project)

    val editorFiles: FilePair = JsonEditorsFileFactory.createFilePair()

    init {
        val leftEditor = editorFactory.createEditor(editorFiles.left())
        val rightEditor = editorFactory.createEditor(editorFiles.right())

        val leftPanel = EditorPanel.create(leftEditor)
        val rightPanel = EditorPanel.create(rightEditor)

        val splitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel)
        add(splitPane, BorderLayout.CENTER)

        GuiUtils.replaceJSplitPaneWithIDEASplitter(this)

        WriteCommandAction.runWriteCommandAction(project) {
            editorFiles.leftDoc().insertString(0, """{"employee":{"name":"sonoo","salary":56000,"married":true}}""")
        }

        val actionList: MutableList<PluginAction> = mutableListOf()
        actionList.addAll(SideMenuManager.getActions())
        actionList.addAll(SideMenuManager.getPopups())

        val toolbar = EditorsToolbarFactory.createToolbar(actionList, this)
        add(BorderLayout.WEST, toolbar.component)
    }
}