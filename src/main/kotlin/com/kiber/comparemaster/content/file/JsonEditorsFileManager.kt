package com.kiber.comparemaster.content.file

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project

object JsonEditorsFileManager : EditorsFileManager {

//    private val FILE_1_NAME = "file1-${System.currentTimeMillis()}.json"
//    private val FILE_2_NAME = "file2-${System.currentTimeMillis()}.json"

    private var filePairMap: Map<Long, FilePair> = mutableMapOf()
//    private lateinit var file1: JsonEVirtualFile
//    private lateinit var file2: JsonEVirtualFile


    override fun createFilePair(): FilePair {
        val prefix = System.currentTimeMillis()
        val file1Name = "file1-$prefix.json"
        val file2Name = "file2-$prefix.json"

        val file1 = JsonEVirtualFile(file1Name)
        val file2 = JsonEVirtualFile(file2Name)

        val pair = FilePair(prefix, file1, file2)
        filePairMap.plus(prefix to pair)

        return pair
    }

    override fun getFilePair(prefix: Long): FilePair? = filePairMap[prefix]

    override fun releaseFiles(prefix: Long, project: Project) {
        val pair = filePairMap[prefix]
        if(pair != null) {
            WriteCommandAction.runWriteCommandAction(project) {
                pair.leftDoc().setText("")
                pair.rightDoc().setText("")
            }
        }
    }

    override fun releaseAllFiles(project: Project) {
        filePairMap.forEach() { (_, value) ->
            WriteCommandAction.runWriteCommandAction(project) {
                value.leftDoc().setText("")
                value.rightDoc().setText("")
            }
        }
    }
}