package com.kiber.comparemaster

import com.intellij.execution.runToolbar.RunToolbarMoreActionGroup
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.ui.GuiUtils
import com.kiber.comparemaster.action.FilePairAction
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.content.file.JsonEditorsFileFactory
import com.kiber.comparemaster.function.CopyContentFunction
import com.kiber.comparemaster.function.FormatJsonFunction
import com.kiber.comparemaster.function.InlineJsonFunction
import com.kiber.comparemaster.function.ReplaceOnlyPresentValuesFunction
import com.kiber.comparemaster.ui.CompareEditorFactory
import com.kiber.comparemaster.ui.EditorPanel
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.JSplitPane
import javax.swing.SwingConstants

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

        //***************************************************************
        val copyAction = FilePairAction(
            hint = "Copy to right editor",
            icon = AllIcons.Actions.Copy,
            function = CopyContentFunction()
        )

        val replaceOnlyValuesAction = FilePairAction(
            hint = "Replace existing values from left to right",
            icon = AllIcons.Nodes.Alias,
            function = ReplaceOnlyPresentValuesFunction(),
            applyFinally = { filePair -> FormatJsonFunction.apply(filePair, project) }
        )

        val formatJsonFunction = FilePairAction(
            hint = "Format",
            icon = AllIcons.Actions.EnableNewUi,
            function = FormatJsonFunction,
        )

        val inlineJsonFunction = FilePairAction(
            hint = "Inline",
            icon = AllIcons.Actions.EnableNewUiSelected,
            function = InlineJsonFunction,
        )

        val group = RunToolbarMoreActionGroup()
        group.add(copyAction)
        group.addSeparator()
        group.add(formatJsonFunction)
        group.addSeparator()
        group.add(inlineJsonFunction)
        group.addSeparator()
        group.add(replaceOnlyValuesAction)

        val toolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.PROJECT_VIEW_TOOLBAR, group, false)
        toolbar.setOrientation(SwingConstants.VERTICAL)
        toolbar.targetComponent = this

        add(BorderLayout.WEST, toolbar.component)
        //***************************************************************
    }
}