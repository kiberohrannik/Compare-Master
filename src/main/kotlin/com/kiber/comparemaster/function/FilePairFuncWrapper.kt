package com.kiber.comparemaster.function

import com.intellij.openapi.project.Project
import com.kiber.comparemaster.content.file.EFileTypes
import com.kiber.comparemaster.content.file.FilePair

class FilePairFuncWrapper(private vararg val functions: FilePairFunction) : FilePairFunction {

    override fun supports(fileType: EFileTypes): Boolean {
        return functions.any { it.supports(fileType) }
    }

    override fun apply(filePair: FilePair, project: Project) {
        functions.find { it.supports(filePair.file1.eType) }?.apply(filePair, project)
    }
}