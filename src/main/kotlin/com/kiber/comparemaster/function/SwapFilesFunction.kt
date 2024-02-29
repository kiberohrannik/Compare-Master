package com.kiber.comparemaster.function

import com.intellij.openapi.project.Project
import com.kiber.comparemaster.content.file.FilePair
import com.kiber.comparemaster.function.internal.ContentOperations

object SwapFilesFunction: FilePairFunction {

    override fun apply(filePair: FilePair, project: Project) {
        val leftText = filePair.leftDoc().text
        val rightText = filePair.rightDoc().text

        ContentOperations.setText(rightText, filePair.left(), project)
        ContentOperations.setText(leftText, filePair.right(), project)
    }
}