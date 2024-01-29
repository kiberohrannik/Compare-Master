package com.kiber.comparemaster

import com.intellij.openapi.application.runUndoTransparentWriteAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.impl.DocumentImpl
import com.intellij.openapi.project.Project
import java.awt.BorderLayout
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JPanel

class EchoPanel(project: Project) : JPanel(BorderLayout()) {

    private var echo = "ECHO\n"

    init {
        val leftEditor = createEmptyEditor(project)
        val rightEditor = createEmptyEditor(project)

        val leftPanel = createEditorPanel(leftEditor)
        val rightPanel = createEditorPanel(rightEditor)

        val splitPane = EchoSplitPaneFactory().createSplitPane(leftPanel, rightPanel)

        add(splitPane, BorderLayout.CENTER)
    }


    private fun createEditorPanel(editor: Editor): JPanel {
        val button = JButton("Echo!")
        button.addActionListener { showEcho(editor) }

        val panel = JPanel()
            .apply {
                layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
                add(editor.component)
                add(button)
            }

        return panel
    }

    private fun showEcho(editor: Editor) {
        runUndoTransparentWriteAction {
            editor.document.insertString(editor.document.text.length, echo)
        }
    }

    private fun createEmptyEditor(project: Project): Editor {
        return EditorFactory.getInstance().createEditor(DocumentImpl(""), project)
    }
}