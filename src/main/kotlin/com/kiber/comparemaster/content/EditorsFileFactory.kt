package com.kiber.comparemaster.content

import com.intellij.testFramework.LightVirtualFile

fun interface EditorsFileFactory {

    fun createFilePair(): FilePair
}