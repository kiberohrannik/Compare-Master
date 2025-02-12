package com.kiber.comparemaster.content.file

import com.intellij.ide.highlighter.XmlFileType
import com.intellij.json.JsonFileType
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.testFramework.LightVirtualFile
import com.kiber.comparemaster.function.internal.ContentOperations

object DefaultEditorsFileManager : EditorsFileManager {

    private var filePairMap: MutableMap<Long, FilePair> = mutableMapOf()


    override fun createFilePair(fileType: EFileTypes): Long {
        val prefix = System.currentTimeMillis()
        val file1Name = "file1-$prefix"
        val file2Name = "file2-$prefix"

        val file1: EVirtualFile
        val file2: EVirtualFile

        when (fileType) {
            EFileTypes.JSON -> {
                file1 = JsonEVirtualFile(file1Name)
                file2 = JsonEVirtualFile(file2Name)
            }

            EFileTypes.XML -> {
                file1 = XmlEVirtualFile(file1Name)
                file2 = XmlEVirtualFile(file2Name)
            }
        }

        val pair = FilePair(prefix, file1, file2)
        filePairMap[prefix] = pair

        return prefix
    }

    override fun getFilePairType(prefix: Long): EFileTypes? = filePairMap[prefix]?.file1?.eType

    override fun getFilePair(prefix: Long): FilePair? = filePairMap[prefix]

    override fun changeFileType(prefix: Long, newType: EFileTypes, project: Project) {
        if (filePairMap[prefix] == null) {
            return
        }

        if (filePairMap[prefix]!!.file1.eType != newType) {

            filePairMap[prefix]!!.file1.eType = newType
            filePairMap[prefix]!!.file2.eType = newType

            val oldName1 = filePairMap[prefix]?.left()!!.name
            val oldName2 = filePairMap[prefix]?.right()!!.name

            val type = when (newType) {
                EFileTypes.JSON -> JsonFileType.INSTANCE
                EFileTypes.XML -> XmlFileType.INSTANCE
            }

            val oldLeftContent = filePairMap[prefix]?.leftText(false)
            val oldRightContent = filePairMap[prefix]?.rightText(false)

            releaseFiles(prefix, project)

            filePairMap[prefix]?.file1?.internalFile = LightVirtualFile(oldName1, type, "")
            filePairMap[prefix]?.file2?.internalFile = LightVirtualFile(oldName2, type, "")

            ContentOperations.setText(
                text = oldLeftContent ?: "",
                filePairMap[prefix]?.left()!!,
                project
            )

            ContentOperations.setText(
                text = oldRightContent ?: "",
                filePairMap[prefix]?.right()!!,
                project
            )

            PsiDocumentManager.getInstance(project).commitAllDocuments()
        }
    }

    override fun releaseFiles(prefix: Long, project: Project) {
        val pair = filePairMap[prefix]
        if (pair != null) {
            WriteCommandAction.runWriteCommandAction(project) {
                if (!project.isDisposed) {
                    pair.leftDoc().setText("")
                    pair.rightDoc().setText("")
                }
            }
        }
    }

    override fun releaseAllFiles(project: Project) {
        filePairMap.forEach() { (_, value) ->
            WriteCommandAction.runWriteCommandAction(project) {
                val indicator = ProgressManager.getInstance().progressIndicator
                if (indicator?.isCanceled == false) {
                    if (!project.isDisposed) {
                        value.leftDoc().setText("")
                        value.rightDoc().setText("")
                    }
                }
            }
        }
    }
}