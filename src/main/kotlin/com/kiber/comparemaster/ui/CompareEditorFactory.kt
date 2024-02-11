package com.kiber.comparemaster.ui

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class CompareEditorFactory(project: Project) : FileEditorFactory(project) {

    override fun createEditor(virtualFile: VirtualFile): FileEditor {
        return PsiAwareTextEditorProvider.getInstance().createEditor(project, virtualFile);
    }
}