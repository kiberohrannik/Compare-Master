package com.kiber.comparemaster.ui

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

object DefaultEditorManager : FileEditorManager {

    override fun createEditor(virtualFile: VirtualFile, project: Project): FileEditor {
        return PsiAwareTextEditorProvider().createEditor(project, virtualFile);
    }

    override fun releaseEditor(fileEditor: FileEditor) {
        fileEditor.dispose()
    }
}