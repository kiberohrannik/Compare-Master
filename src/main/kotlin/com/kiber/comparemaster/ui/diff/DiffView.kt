package com.kiber.comparemaster.ui.diff

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

fun interface DiffView {

    val diffViewTitle: String
        get() = "Difference comparator"

    fun showDiff(project: Project, left: VirtualFile, right: VirtualFile)
}