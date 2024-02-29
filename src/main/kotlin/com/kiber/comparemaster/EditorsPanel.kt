package com.kiber.comparemaster

import com.intellij.execution.runToolbar.RunToolbarMoreActionGroup
import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.GuiUtils
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.content.file.JsonEditorsFileFactory
import com.kiber.comparemaster.function.ReplaceOnlyPresentValuesFunction
import com.kiber.comparemaster.json.JsonFormatter
import com.kiber.comparemaster.old.AskQuestionAction
import com.kiber.comparemaster.ui.CompareEditorFactory
import com.kiber.comparemaster.ui.EditorPanel
import com.kiber.comparemaster.ui.EditorsButton
import com.kiber.comparemaster.ui.diff.SimpleDiffPanel
import java.awt.BorderLayout
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JSplitPane
import javax.swing.SwingConstants

class EditorsPanel(private val project: Project) : JPanel(BorderLayout()) {

    private val jsonFormatter = JsonFormatter()
    private val editorFactory = CompareEditorFactory(project)

    init {
        val editorFiles = JsonEditorsFileFactory.createFilePair()

        val leftEditor = editorFactory.createEditor(editorFiles.left())
        val beautifyButton = createBeautifyButton(editorFiles)
        val uglifyButton = createUglifyButton(editorFiles.left())

        val rightEditor = editorFactory.createEditor(editorFiles.right())
        val copyButton = createReplaceValuesButton(editorFiles)

        val diffButton = createDiffButton(editorFiles)

        val leftPanel = EditorPanel.create(leftEditor, listOf(beautifyButton, uglifyButton))
        val rightPanel = EditorPanel.create(rightEditor, listOf(copyButton, diffButton))

        val splitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel)
        add(splitPane, BorderLayout.CENTER)

        GuiUtils.replaceJSplitPaneWithIDEASplitter(this)

        WriteCommandAction.runWriteCommandAction(project) {
            editorFiles.leftDoc().insertString(0, """{"employee":{"name":"sonoo","salary":56000,"married":true}}""")
        }

        //***************************************************************
        val group = RunToolbarMoreActionGroup()
        group.add(AskQuestionAction())
        group.addSeparator()
        group.add(AskQuestionAction())

        val group2 = ActionManager.getInstance().getAction("ProblemsView.ToolWindow.Toolbar") as ActionGroup
//        val toolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.PROJECT_VIEW_TOOLBAR, group2, false);
        val toolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.PROJECT_VIEW_TOOLBAR, group, false);
        toolbar.setOrientation(SwingConstants.VERTICAL)
        toolbar.targetComponent = this

        add(BorderLayout.WEST, toolbar.component)
        //***************************************************************


    }


    private fun createReplaceValuesButton(editorFiles: FilePair): JButton {
        val action = {
            ReplaceOnlyPresentValuesFunction().apply(editorFiles, project)
            beautify(editorFiles.leftDoc())
            beautify(editorFiles.rightDoc())
        }
        return EditorsButton.createButton("REPLACE (present values)", action)
    }

    private fun createDiffButton(editorFiles: FilePair): JButton {
        val action = {
            SimpleDiffPanel.showDiff(project, editorFiles.left(), editorFiles.right())
        }
        return EditorsButton.createButton("Compare", action)
    }

    private fun createBeautifyButton(editorFiles: FilePair): JButton {
        val action = {
            beautify(editorFiles.leftDoc())
            beautify(editorFiles.rightDoc())
        }
        return EditorsButton.createButton("Beautify", action)
    }

    private fun beautify(document: Document) {
        val prettyJson = jsonFormatter.toPrettyJson(document.text)
        document.replaceString(0, document.text.length, prettyJson)
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