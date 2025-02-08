package com.kiber.comparemaster.ui

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

interface FileEditorManager {

    fun createEditor(virtualFile: VirtualFile, project: Project): FileEditor

    fun releaseEditor(fileEditor: FileEditor)
}