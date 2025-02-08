package com.kiber.comparemaster.content.file

import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.impl.DocumentImpl
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.findDocument

data class FilePair(val prefix: Long,
//                    internal val file1: EVirtualFile, internal val file2: EVirtualFile) {
                    internal val file1: EVirtualFile, internal val file2: EVirtualFile) {

    fun left(): VirtualFile = file1.internalFile
    fun right(): VirtualFile = file2.internalFile

    fun leftDoc(): Document = left().findDocument() ?: DocumentImpl("")
    fun rightDoc(): Document = right().findDocument() ?: DocumentImpl("")

    fun leftText(removeLineBreaks: Boolean = true): String =
        if (removeLineBreaks) removeLineBreaks(leftDoc().text) else leftDoc().text

    fun rightText(removeLineBreaks: Boolean = true): String =
        if (removeLineBreaks) removeLineBreaks(rightDoc().text) else rightDoc().text


    private fun removeLineBreaks(text: String): String = text.replace(System.lineSeparator(), "")
}
