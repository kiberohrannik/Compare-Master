package com.kiber.comparemaster

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.GuiUtils
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.content.file.JsonEditorsFileFactory
import com.kiber.comparemaster.function.CopyContentFunction
import com.kiber.comparemaster.json.JsonFormatter
import com.kiber.comparemaster.ui.CompareEditorFactory
import com.kiber.comparemaster.ui.EditorPanel
import com.kiber.comparemaster.ui.EditorsButton
import com.kiber.comparemaster.ui.diff.SimpleDiffPanel
import java.awt.BorderLayout
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JSplitPane

class EditorsPanel(private val project: Project) : JPanel(BorderLayout()) {

    private val jsonFormatter = JsonFormatter()
    private val editorFactory = CompareEditorFactory(project)

    init {
        val editorFiles = JsonEditorsFileFactory.createFilePair()

        val leftEditor = editorFactory.createEditor(editorFiles.left())
        val beautifyButton = createBeautifyButton(editorFiles.left())
        val uglifyButton = createUglifyButton(editorFiles.left())

        val rightEditor = editorFactory.createEditor(editorFiles.right())
        val copyButton = createCopyButton(editorFiles)

        val diffButton = createDiffButton(editorFiles)

        val leftPanel = EditorPanel.create(leftEditor, listOf(beautifyButton, uglifyButton))
        val rightPanel = EditorPanel.create(rightEditor, listOf(copyButton, diffButton))

        val splitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel)
        add(splitPane, BorderLayout.CENTER)

        GuiUtils.replaceJSplitPaneWithIDEASplitter(this)

        WriteCommandAction.runWriteCommandAction(project) {
            editorFiles.leftDoc().insertString(0, """{"employee":{"name":"sonoo","salary":56000,"married":true}}""")
        }
    }


    private fun createCopyButton(editorFiles: FilePair): JButton {
        val action = {
            CopyContentFunction().apply(editorFiles, project)
        }
        return EditorsButton.createButton("COPY", action)
    }

    private fun createDiffButton(editorFiles: FilePair): JButton {
        val action = {
            SimpleDiffPanel.showDiff(project, editorFiles.left(), editorFiles.right())
        }
        return EditorsButton.createButton("Compare", action)
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