package com.kiber.comparemaster

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.colors.CodeInsightColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.markup.HighlighterTargetArea
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.findDocument
import com.intellij.ui.GuiUtils
import com.kiber.comparemaster.content.FilePair
import com.kiber.comparemaster.content.JsonEditorsFileFactory
import com.kiber.comparemaster.function.CopyContentFunction
import com.kiber.comparemaster.json.JsonFormatter
import com.kiber.comparemaster.ui.CompareEditorFactory
import com.kiber.comparemaster.ui.EditorPanel
import com.kiber.comparemaster.ui.EditorsButton
import java.awt.BorderLayout
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JSplitPane

class EditorsPanel(val project: Project) : JPanel(BorderLayout()) {

    private var echo = "ECHO\n"
    private val jsonFormatter = JsonFormatter()
    private val editorFactory = CompareEditorFactory(project)

    init {
        val editorFiles = JsonEditorsFileFactory.createFilePair()

        val leftEditor = editorFactory.createEditor(editorFiles.left())
        val beautifyButton = createBeautifyButton(editorFiles.left())
        val uglifyButton = createUglifyButton(editorFiles.left())

        val rightEditor = editorFactory.createEditor(editorFiles.right())
        val copyButton = createCopyButton(editorFiles)


        //***************************
//        val editors = FileEditorManager.getInstance(project).getEditors(editorFiles.left())
//        assert(editors.size == 1)
//        val hButton = createHighlightButton(editors[0] as TextEditor)
        val hButton = createHighlightButton(leftEditor as TextEditor)
        //***************************


        val leftPanel = EditorPanel.create(leftEditor, listOf(beautifyButton, uglifyButton))
        val rightPanel = EditorPanel.create(rightEditor, listOf(copyButton, hButton))

        val splitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel)
        add(splitPane, BorderLayout.CENTER)

        GuiUtils.replaceJSplitPaneWithIDEASplitter(this)


//        (rightEditor as TextEditor).editor.markupModel.addLineHighlighter(null, 2, 1)


        WriteCommandAction.runWriteCommandAction(project) {
            editorFiles.leftDoc().insertString(0, """{"employee":{"name":"sonoo","salary":56000,"married":true}}""")
        }
    }


    private fun createCopyButton(editorFiles: FilePair): JButton {
        val action = {
            CopyContentFunction(project).apply(editorFiles.leftDoc(), editorFiles.rightDoc())
        }

        return EditorsButton.createButton("COPY", action)
    }

    private fun createHighlightButton(editor: TextEditor): JButton {

        val action: () -> Unit = {
//            editor.editor.markupModel.addLineHighlighter(CodeInsightColors.ERRORS_ATTRIBUTES, 1, 1)
//            editor.editor.markupModel.addLineHighlighter(CodeInsightColors.WARNINGS_ATTRIBUTES, 2, 1) //yellow - this
//            editor.editor.markupModel.addLineHighlighter(CodeInsightColors.MATCHED_BRACE_ATTRIBUTES, 3, 1) //blue - or this to use
//            editor.editor.markupModel.addLineHighlighter(CodeInsightColors.DUPLICATE_FROM_SERVER, 4, 1) //orange

            editor.editor.markupModel.addRangeHighlighter(
                CodeInsightColors.MATCHED_BRACE_ATTRIBUTES, 3, 10, 100, HighlighterTargetArea.EXACT_RANGE)
        }

        return EditorsButton.createButton("Highlight", action)
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