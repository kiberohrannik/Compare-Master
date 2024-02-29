package com.kiber.comparemaster

import com.intellij.icons.AllIcons
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.ui.GuiUtils
import com.kiber.comparemaster.action.FilePairAction
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.content.file.JsonEditorsFileFactory
import com.kiber.comparemaster.function.CopyContentFunction
import com.kiber.comparemaster.function.SwapFilesFunction
import com.kiber.comparemaster.function.json.AddAbsentFieldsFunction
import com.kiber.comparemaster.function.json.FormatJsonFunction
import com.kiber.comparemaster.function.json.InlineJsonFunction
import com.kiber.comparemaster.function.json.ReplaceOnlyPresentValuesFunction
import com.kiber.comparemaster.ui.CompareEditorFactory
import com.kiber.comparemaster.ui.EditorPanel
import com.kiber.comparemaster.ui.EditorsToolbarFactory
import com.kiber.comparemaster.ui.IconManager
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

        //***************************************************************
        //TODO replace it with XML configuration

        val copyAction = FilePairAction(
            hint = "Copy to right editor",
            icon = AllIcons.Actions.Copy,
            function = CopyContentFunction()
        )

        val replaceOnlyValuesAction = FilePairAction(
            hint = "Replace existing values from left to right",
            icon = IconManager.replaceOnlyValues,
            function = ReplaceOnlyPresentValuesFunction(),
            applyFinally = { filePair -> FormatJsonFunction.apply(filePair, project) }
        )

        val addAbsentValuesAction = FilePairAction(
            hint = "Add absent values from left to right",
            icon = AllIcons.ToolbarDecorator.AddPattern,
            function = AddAbsentFieldsFunction(),
            applyFinally = { filePair -> FormatJsonFunction.apply(filePair, project) }
        )

        val formatJsonFunction = FilePairAction(
            hint = "Format",
            icon = IconManager.formatText,
            function = FormatJsonFunction,
        )

        val inlineJsonFunction = FilePairAction(
            hint = "Inline",
            icon = IconManager.inlineText,
            function = InlineJsonFunction,
        )

        val swapAction = FilePairAction(
            hint = "Swap",
            icon = IconManager.swapFiles,
            function = SwapFilesFunction
        )

        val actionList = listOf(
            copyAction,
            formatJsonFunction,
            inlineJsonFunction,
            replaceOnlyValuesAction,
            addAbsentValuesAction,
            swapAction
        )

        val toolbar = EditorsToolbarFactory.createToolbar(actionList, this)
        add(BorderLayout.WEST, toolbar.component)
        //***************************************************************
    }
}