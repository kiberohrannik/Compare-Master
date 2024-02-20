package com.kiber.comparemaster.function

import com.intellij.openapi.project.Project
import com.kiber.comparemaster.content.file.EFileTypes
import com.kiber.comparemaster.content.file.FilePair

interface FilePairFunction {

    fun apply(filePair: FilePair, project: Project)

    fun supports(fileType: EFileTypes): Boolean = false
}