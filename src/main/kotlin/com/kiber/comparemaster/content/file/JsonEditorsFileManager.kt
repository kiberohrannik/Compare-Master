package com.kiber.comparemaster.content.file

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.psi.impl.PsiManagerImpl

object JsonEditorsFileManager : EditorsFileManager {

    private val FILE_1_NAME = "file-${System.currentTimeMillis()}1.json"
    private val FILE_2_NAME = "file-${System.currentTimeMillis()}2.json"
    private val file1 = JsonEVirtualFile(FILE_1_NAME)
    private val file2 = JsonEVirtualFile(FILE_2_NAME)

    override fun getFilePair(): FilePair {
        return FilePair(file1, file2)
    }

    override fun releaseFiles(project: Project) {
        WriteCommandAction.runWriteCommandAction(project) {
            PsiManagerImpl.getInstance(project).findFile(getFilePair().right())!!.clearCaches()
            PsiManagerImpl.getInstance(project).findFile(getFilePair().left())!!.clearCaches()

            getFilePair().leftDoc().setText("")
            getFilePair().rightDoc().setText("")
        }
    }
}