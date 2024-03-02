package com.kiber.comparemaster.ui

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

abstract class FileEditorManager(val project: Project) {

    abstract fun createEditor(virtualFile: VirtualFile): FileEditor

    abstract fun releaseEditor(fileEditor: FileEditor)
}