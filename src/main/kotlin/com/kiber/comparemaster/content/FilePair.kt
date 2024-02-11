package com.kiber.comparemaster.content

import com.intellij.openapi.vfs.VirtualFile

data class FilePair(private val file1: EVirtualFile, private val file2: EVirtualFile) {

    fun left(): VirtualFile {
        return file1.internalFile
    }

    fun right(): VirtualFile {
        return file2.internalFile
    }
}
