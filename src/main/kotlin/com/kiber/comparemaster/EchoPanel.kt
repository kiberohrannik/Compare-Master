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
    private var docLinesCounter = 0

    init {
        val editor = EditorFactory.getInstance().createEditor(DocumentImpl(""), project)

        val echoButton = JButton("Echo!")
        echoButton.addActionListener { showEcho(editor) }

        val echoContentPanel = JPanel()
            .apply {
                layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
                add(editor.component)
                add(echoButton)
            }

        add(echoContentPanel, BorderLayout.CENTER)
    }


    private fun showEcho(editor: Editor) {
        runUndoTransparentWriteAction {
            editor.document.insertString(docLinesCounter, echo)
            docLinesCounter += echo.length
        }
    }
}