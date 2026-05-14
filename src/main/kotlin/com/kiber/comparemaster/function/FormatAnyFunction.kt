package com.kiber.comparemaster.function

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiManager
import com.intellij.psi.codeStyle.CodeStyleManager
import com.kiber.comparemaster.content.file.EFileTypes
import com.kiber.comparemaster.content.file.FilePair

object FormatAnyFunction : FilePairFunction {

    override fun apply(filePair: FilePair, project: Project) {
        format(filePair.left(), project)
        format(filePair.right(), project)
    }

    private fun format(file: VirtualFile, project: Project) {
        val psiFile = PsiManager.getInstance(project).findFile(file) ?: return
        val document = PsiDocumentManager.getInstance(project).getDocument(psiFile) ?: return

        WriteCommandAction.runWriteCommandAction(project) {
            val psiDocumentManager = PsiDocumentManager.getInstance(project)
            psiDocumentManager.commitDocument(document)

            CodeStyleManager.getInstance(project).reformatText(psiFile, listOf(psiFile.textRange))

            psiDocumentManager.commitDocument(document)
        }
    }

    override fun supports(fileType: EFileTypes): Boolean {
        return fileType == EFileTypes.ANY
    }
}
