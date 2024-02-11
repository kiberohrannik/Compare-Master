package com.kiber.comparemaster

import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.GuiUtils
import com.kiber.comparemaster.content.JsonEditorsFileFactory
import com.kiber.comparemaster.json.JsonFormatter
import com.kiber.comparemaster.ui.CompareEditorFactory
import com.kiber.comparemaster.ui.EditorPanel
import com.kiber.comparemaster.ui.EditorsButton
import java.awt.BorderLayout
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JSplitPane

class EditorsPanel(project: Project) : JPanel(BorderLayout()) {

    private var echo = "ECHO\n"
    private val jsonFormatter = JsonFormatter()
    private val editorFactory = CompareEditorFactory(project)

    init {
        val editorFiles = JsonEditorsFileFactory.createFilePair()

        val leftEditor = editorFactory.createEditor(editorFiles.left())
        val beautifyButton = createBeautifyButton(editorFiles.left())
        val uglifyButton = createUglifyButton(editorFiles.left())

        val rightEditor = editorFactory.createEditor(editorFiles.right())
        val echoButton = createEchoButton(editorFiles.right())

        val leftPanel = EditorPanel.create(leftEditor, listOf(beautifyButton, uglifyButton))
        val rightPanel = EditorPanel.create(rightEditor, listOf(echoButton))

        val splitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel)
        add(splitPane, BorderLayout.CENTER)

        GuiUtils.replaceJSplitPaneWithIDEASplitter(this)
    }


    private fun createEchoButton(virtualFile: VirtualFile): JButton {
        val action = {
            val doc = getDoc(virtualFile)!!
            doc.insertString(doc.text.length, echo)
        }

        return EditorsButton.createButton("Echo", action)

    }

    private fun createBeautifyButton(virtualFile: VirtualFile): JButton {
        val action = {
                val doc = getDoc(virtualFile)!!
                val prettyJson = jsonFormatter.toPrettyJson(doc.text)
                doc.replaceString(0, doc.text.length, prettyJson)
            }

        return EditorsButton.createButton("Beautify", action)

    }

    private fun createUglifyButton(virtualFile: VirtualFile): JButton {
        val action = {
            val doc = getDoc(virtualFile)!!
            val rawJson = jsonFormatter.toRawJson(doc.text)
            doc.replaceString(0, doc.text.length, rawJson)
        }

        return EditorsButton.createButton("Uglify", action)
    }

    private fun getDoc(virtualFile: VirtualFile): Document? {
        return FileDocumentManager.getInstance().getDocument(virtualFile)
    }
}