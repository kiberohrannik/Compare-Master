package com.kiber.comparemaster.function.internal

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.findDocument

object ContentOperations {

    fun setAndFormat(
        testToSet: String,
        formatFunc: (a: String) -> String,
        virtualFile: VirtualFile,
        project: Project
    ) {
        val formattedResult = formatFunc.invoke(testToSet)
        setText(formattedResult, virtualFile, project)
    }

    fun setText(text: String, virtualFile: VirtualFile, project: Project) {
        WriteCommandAction.runWriteCommandAction(project) {
            virtualFile.findDocument()!!.setText(text)
        }
    }
}