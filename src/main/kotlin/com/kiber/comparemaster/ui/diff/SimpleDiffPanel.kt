package com.kiber.comparemaster.ui.diff

import com.intellij.diff.DiffContentFactory
import com.intellij.diff.DiffDialogHints
import com.intellij.diff.DiffManager
import com.intellij.diff.chains.SimpleDiffRequestChain
import com.intellij.diff.requests.SimpleDiffRequest
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

object SimpleDiffPanel : DiffView {

    private val diffContentFactory = lazy { DiffContentFactory.getInstance() }


    override fun showDiff(project: Project, left: VirtualFile, right: VirtualFile) {

        val diffReq = SimpleDiffRequest(
            diffViewTitle,
            diffContentFactory.value.create(project, left),
            diffContentFactory.value.create(project, right), left.name, right.name
        )

        DiffManager.getInstance().showDiff(project, SimpleDiffRequestChain(diffReq), DiffDialogHints.NON_MODAL);
    }
}