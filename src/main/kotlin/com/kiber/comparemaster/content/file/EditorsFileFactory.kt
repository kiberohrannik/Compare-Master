package com.kiber.comparemaster.content.file

import com.kiber.comparemaster.content.file.FilePair

fun interface EditorsFileFactory {

    fun createFilePair(): FilePair
}