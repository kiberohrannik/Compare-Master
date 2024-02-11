package com.kiber.comparemaster.function

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.Project

class CopyContentFunction(private val project: Project): FilePairFunction {

    override fun apply(doc1: Document, doc2: Document) {
        WriteCommandAction.runWriteCommandAction(project) {
            doc2.setText(doc1.text)
        }
    }
}