package com.kiber.comparemaster.content.file

import com.intellij.openapi.project.Project

interface EditorsFileManager {

    /**
     * Returns file prefix (file pair ID)
     */
    fun createFilePair(fileType: EFileTypes): Long

    fun getFilePairType(prefix: Long): EFileTypes?

    fun getFilePair(prefix: Long): FilePair?

    /**
     * Copy file name, content but change the type
     */
    fun changeFileType(prefix: Long, newType: EFileTypes, project: Project)

    fun releaseFiles(prefix: Long, project: Project)

    fun releaseAllFiles(project: Project)
}