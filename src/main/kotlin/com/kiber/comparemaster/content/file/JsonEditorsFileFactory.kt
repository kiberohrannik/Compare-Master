package com.kiber.comparemaster.content.file

object JsonEditorsFileFactory : EditorsFileFactory {

    private val FILE_1_NAME = "file-${System.currentTimeMillis()}1.json"
    private val FILE_2_NAME = "file-${System.currentTimeMillis()}2.json"
    private val file1 = JsonEVirtualFile(FILE_1_NAME)
    private val file2 = JsonEVirtualFile(FILE_2_NAME)

    override fun getFilePair(): FilePair {
        return FilePair(file1, file2)
    }
}