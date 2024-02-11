package com.kiber.comparemaster.old

import com.intellij.json.JsonFileType
import com.intellij.openapi.application.runUndoTransparentWriteAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.impl.DocumentImpl
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.testFramework.LightVirtualFile
import com.kiber.comparemaster.json.JsonFormatter
import java.awt.BorderLayout
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JPanel


class EchoPanel(project: Project) : JPanel(BorderLayout()) {

    private var echo = "ECHO\n"

    init {
        val jsonFormatter = JsonFormatter()

        val json = """{"menu":{"header":"SVG Viewer","items":[{"id":"Open"},{"id":"OpenNew","label":"Open New"}]}}"""

        val virtualFile = LightVirtualFile("Foo.json", JsonFileType.INSTANCE, json);
        val leftEditor = PsiAwareTextEditorProvider.getInstance().createEditor(project, virtualFile);

        val beautifyButton = JButton("Beautify")
        beautifyButton.addActionListener {
            runUndoTransparentWriteAction {
                val doc = FileDocumentManager.getInstance().getDocument(virtualFile)!!
                val prettyJson = jsonFormatter.toPrettyJson(doc.text)
                doc.replaceString(0, doc.text.length, prettyJson)
            }
        }

        val uglifyButton = JButton("Uglify")
        uglifyButton.addActionListener {
            runUndoTransparentWriteAction {
                val doc = FileDocumentManager.getInstance().getDocument(virtualFile)!!
                val rawJson = jsonFormatter.toRawJson(doc.text)
                doc.replaceString(0, doc.text.length, rawJson)
            }
        }

        val leftPanel = JPanel()
            .apply {
                layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
                add(leftEditor.component)
                    add(beautifyButton)
                    add(uglifyButton)
            }

        val rightEditor = createEmptyEditor(project)
        val echoButton = JButton("Echo!")
        echoButton.addActionListener { showEcho(rightEditor) }

        val rightPanel = createEditorPanel(rightEditor, listOf(echoButton))

        val splitPane = CompareEditorsSplitPaneFactory().createSplitPane(leftPanel, rightPanel)

        add(splitPane, BorderLayout.CENTER)
    }

    private fun createEditorPanel(editor: Editor, buttons: List<JButton>): JPanel {
        val panel = JPanel()
            .apply {
                layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
                add(editor.component)
                for (button in buttons) {
                    add(button)
                }
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