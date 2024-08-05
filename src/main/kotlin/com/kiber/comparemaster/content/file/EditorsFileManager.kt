package com.kiber.comparemaster.content.file

import com.intellij.openapi.project.Project

interface EditorsFileManager {

    //TODO refactor this method to be safe from miss-usage
    fun createFilePair(): FilePair

    fun getFilePair(prefix: Long): FilePair?

    fun releaseFiles(prefix: Long, project: Project)

    fun releaseAllFiles(project: Project)
}