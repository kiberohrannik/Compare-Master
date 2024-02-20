package com.kiber.comparemaster.old

import com.intellij.diff.DiffContentFactory
import com.intellij.diff.DiffDialogHints
import com.intellij.diff.DiffManager
import com.intellij.diff.chains.SimpleDiffRequestChain
import com.intellij.diff.contents.DocumentContentImpl
import com.intellij.diff.editor.DiffVirtualFile
import com.intellij.diff.editor.SimpleDiffVirtualFile
import com.intellij.diff.impl.DiffWindow
import com.intellij.diff.requests.SimpleDiffRequest
import com.intellij.diff.tools.simple.SimpleDiffTool
import com.intellij.diff.util.DiffUserDataKeysEx
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.impl.FileDocumentManagerBase
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.WindowWrapper
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.findDocument
import org.apache.commons.lang3.tuple.MutablePair
import javax.swing.JComponent

class ShowDiffFunction(private val project: Project) {

    fun apply(
        left: VirtualFile,
        right: VirtualFile,
        componentParent: JComponent,
        editors: MutablePair<FileEditor, FileEditor>
    ) {
        val request = SimpleDiffRequest(
            "Diff",
            DocumentContentImpl(left.findDocument()!!),
            DocumentContentImpl(right.findDocument()!!), "file1", "file2"
        )

        val diffPanel = DiffManager.getInstance().createRequestPanel(project, {
            //May do something meaningful here
            println("Dispose!")
        }, null)

        diffPanel.setRequest(request)
        diffPanel.putContextHints(DiffUserDataKeysEx.FORCE_DIFF_TOOL, SimpleDiffTool.INSTANCE)

        DiffManager.getInstance().showDiff(project, request, DiffDialogHints(WindowWrapper.Mode.MODAL, componentParent))

        val diffContent1 = DiffContentFactory.getInstance().create(project, left)
        val diffContent2 = DiffContentFactory.getInstance().create(project, right)

        val diffreq = SimpleDiffRequest("compare", diffContent1, diffContent2, "first", "second")

        DiffWindow(project, SimpleDiffRequestChain(diffreq), DiffDialogHints.NON_MODAL)
    }
}