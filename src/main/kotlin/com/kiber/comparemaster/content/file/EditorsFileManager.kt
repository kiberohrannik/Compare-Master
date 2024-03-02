package com.kiber.comparemaster.content.file

import com.intellij.openapi.project.Project

interface EditorsFileManager {

    fun getFilePair(): FilePair

    fun releaseFiles(project: Project)
}