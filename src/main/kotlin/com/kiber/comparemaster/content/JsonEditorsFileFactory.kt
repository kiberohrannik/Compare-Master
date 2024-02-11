package com.kiber.comparemaster.content

object JsonEditorsFileFactory : EditorsFileFactory {

    private const val FILE_1_NAME = "file1.json"
    private const val FILE_2_NAME = "file2.json"
    private val file1 = JsonEVirtualFile(FILE_1_NAME);
    private val file2 = JsonEVirtualFile(FILE_2_NAME);

    override fun createFilePair(): FilePair {
        return FilePair(file1, file2)
    }
}