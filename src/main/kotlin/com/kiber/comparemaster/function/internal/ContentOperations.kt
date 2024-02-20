package com.kiber.comparemaster.function.internal

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.findDocument

object ContentOperations {

    fun setText(text: String, virtualFile: VirtualFile, project: Project) {
        WriteCommandAction.runWriteCommandAction(project) {
            virtualFile.findDocument()!!.setText(text)
        }
    }
}