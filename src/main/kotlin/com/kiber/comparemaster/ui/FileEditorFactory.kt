package com.kiber.comparemaster.ui

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileTypes.ex.FileTypeIdentifiableByVirtualFile
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.UserDataHolder
import com.intellij.openapi.vfs.VirtualFile

abstract class FileEditorFactory(val project: Project) {

    abstract fun createEditor(virtualFile: VirtualFile): FileEditor
}