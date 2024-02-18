package com.kiber.comparemaster.ui.highlight

import com.intellij.openapi.editor.colors.CodeInsightColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.markup.HighlighterTargetArea
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.vfs.VirtualFile

class HighlightText(
    private val project: Project,
    private val hType: TextAttributesKey = CodeInsightColors.MATCHED_BRACE_ATTRIBUTES
) {

    private val hPriority = 10

    fun highlight(file: VirtualFile, textRange: TextRange) {
        FileEditorManager.getInstance(project).getEditors(file)
            .filterIsInstance<TextEditor>()
            .forEach {
                it.editor.markupModel.addRangeHighlighter(
                    hType, textRange.startOffset, textRange.endOffset, hPriority, HighlighterTargetArea.EXACT_RANGE
                )
            }
    }
}