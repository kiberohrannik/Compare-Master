package com.kiber.comparemaster.ui

import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.impl.EditorFactoryImpl
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorProvider
import com.intellij.openapi.fileEditor.impl.text.TextEditorImpl
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class DefaultEditorManager(project: Project) : FileEditorManager(project) {

    private val editorFactory = EditorFactory.getInstance()


    override fun createEditor(virtualFile: VirtualFile): FileEditor {
        return PsiAwareTextEditorProvider().createEditor(project, virtualFile)
    }

    override fun releaseEditor(fileEditor: FileEditor) {
        editorFactory.releaseEditor((fileEditor as TextEditorImpl).editor)
        fileEditor.dispose()
    }
}
