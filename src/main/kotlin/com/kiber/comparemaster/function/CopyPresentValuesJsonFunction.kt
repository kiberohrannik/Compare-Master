package com.kiber.comparemaster.function

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.kiber.comparemaster.content.file.EFileTypes
import com.kiber.comparemaster.content.file.FilePair

class CopyPresentValuesJsonFunction: FilePairFunction {

    override fun apply(filePair: FilePair, project: Project) {


        WriteCommandAction.runWriteCommandAction(project) {
            filePair.rightDoc().setText(filePair.leftDoc().text)
        }
    }

    override fun supports(fileType: EFileTypes): Boolean {
        return fileType == EFileTypes.JSON
    }
}