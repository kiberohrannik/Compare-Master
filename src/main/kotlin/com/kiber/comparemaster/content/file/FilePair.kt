package com.kiber.comparemaster.content.file

import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.impl.DocumentImpl
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.findDocument

data class FilePair(private val file1: EVirtualFile, private val file2: EVirtualFile) {

    fun left(): VirtualFile {
        return file1.internalFile
    }

    fun right(): VirtualFile {
        return file2.internalFile
    }

    fun leftDoc(): Document {
        return left().findDocument() ?: DocumentImpl("")
    }

    fun rightDoc(): Document {
        return right().findDocument() ?: DocumentImpl("")
    }
}
