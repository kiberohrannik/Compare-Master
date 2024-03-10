package com.kiber.comparemaster.content.file

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project

object JsonEditorsFileManager : EditorsFileManager {

    private val FILE_1_NAME = "file-${System.currentTimeMillis()}1.json"
    private val FILE_2_NAME = "file-${System.currentTimeMillis()}2.json"
    private lateinit var file1: JsonEVirtualFile
    private lateinit var file2: JsonEVirtualFile


    override fun createFilePair(): FilePair {
        file1 = JsonEVirtualFile(FILE_1_NAME)
        file2 = JsonEVirtualFile(FILE_2_NAME)

        return getFilePair()
    }

    override fun getFilePair(): FilePair {
        return FilePair(file1, file2)
    }

    override fun releaseFiles(project: Project) {
        WriteCommandAction.runWriteCommandAction(project) {
            getFilePair().leftDoc().setText("")
            getFilePair().rightDoc().setText("")
        }
    }
}