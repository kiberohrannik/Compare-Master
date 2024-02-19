package com.kiber.comparemaster.function

import com.intellij.diff.DiffManager
import com.intellij.diff.contents.DocumentContentImpl
import com.intellij.diff.requests.SimpleDiffRequest
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.Project


class CopyContentFunction(private val project: Project): FilePairFunction {

    override fun apply(left: Document, right: Document) {
        WriteCommandAction.runWriteCommandAction(project) {
            right.setText(left.text)
        }
    }
}